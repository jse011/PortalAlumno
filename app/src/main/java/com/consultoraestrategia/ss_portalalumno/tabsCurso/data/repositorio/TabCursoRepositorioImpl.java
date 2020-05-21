package com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TabCursoRepositorioImpl implements TabCursoRepositorio {

    public static final String TAG =  TabCursoRepositorioImpl.class.getSimpleName();
    @Override
    public List<PeriodoUi> getPerioList(int anioAcademicoId, int cargaCursoId, int programaEduId) {
        List<PeriodoUi> periodoUiList = new ArrayList<>();
        List<CalendarioPeriodo> calendarioPeriodoList = SQLite.select()
                .from(CalendarioPeriodo.class)
                .innerJoin(CalendarioAcademico.class)
                .on(CalendarioPeriodo_Table.calendarioAcademicoId.withTable()
                        .eq(CalendarioAcademico_Table.calendarioAcademicoId.withTable()))
                .where(CalendarioAcademico_Table.idAnioAcademico.withTable().eq(anioAcademicoId))
                .and(CalendarioAcademico_Table.programaEduId.withTable().eq(programaEduId))
                .queryList();

        Log.d(TAG, "SIZEPERIODO1 : " + calendarioPeriodoList.size());

        boolean seleccionado = false;
        for (CalendarioPeriodo periodo :
                calendarioPeriodoList) {
            Log.d(TAG, "COUNT : " + calendarioPeriodoList.size() + " /  periodoid : " + periodo.getCalendarioPeriodoId() + " / " + periodo.getCalendarioPeriodoId()+ " / " +periodo.getEstadoId());

            Tipos tipo = SQLite.select()
                    .from(Tipos.class)
                    .where(Tipos_Table.tipoId.eq(periodo.getTipoId()))
                    .querySingle();
            if(tipo==null)continue;
            //Log.d(TAG, "PeriodoUi : " + tipo.getTipoId() + "")

            PeriodoUi periodoUi = new PeriodoUi(periodo.getCalendarioPeriodoId(), tipo.getNombre(), "", false);

            boolean isvigente = isVigenteCalendarioCursoPeriodo(periodo.getCalendarioPeriodoId(), cargaCursoId, false);
            periodoUi.setVigente(isvigente);
            periodoUi.setFechaFin(periodo.getFechaFin());

            switch (periodo.getEstadoId()){
                case CalendarioPeriodo.CALENDARIO_PERIODO_CREADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Creado);
                    periodoUi.setEditar(true);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_VIGENTE:
                    periodoUi.setEstado(PeriodoUi.Estado.Vigente);
                    periodoUi.setEditar(isvigente);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_CERRADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Cerrado);
                    periodoUi.setEditar(isvigente);
                    break;
            }
            periodoUiList.add(periodoUi);
        }
        Log.d(TAG, "SIZEPERIODO1 : " + periodoUiList.size());
        return periodoUiList;
    }

    public boolean isVigenteCalendarioCursoPeriodo(int calendarioPeridoId, int cargaCursoId, boolean isRubroResultado) {
        boolean status = false;
        boolean statusCargaCuros = false;
        try {

            Log.d(TAG, "calendarioPeridoId :"+ calendarioPeridoId);
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.set(Calendar.MILLISECOND, 0);
            fechaActual.set(Calendar.SECOND, 0);
            fechaActual.set(Calendar.MINUTE, 0);
            fechaActual.set(Calendar.HOUR_OF_DAY, 0);

            Where<CalendarioPeriodo> calendarioPeriodoWhere = SQLite.select()
                    .from(CalendarioPeriodo.class)
                    .where(CalendarioPeriodo_Table.calendarioPeriodoId.eq(calendarioPeridoId));

            CalendarioPeriodo calendarioPeriodo = calendarioPeriodoWhere.querySingle();

            Where<CalendarioPeriodoDetalle> where = SQLite.select()
                    .from(CalendarioPeriodoDetalle.class)
                    .where(CalendarioPeriodoDetalle_Table.calendarioPeriodoId.eq(calendarioPeridoId));

            if(isRubroResultado){
                where.and(CalendarioPeriodoDetalle_Table.tipoId.eq(493));
            }else {
                where.and(CalendarioPeriodoDetalle_Table.tipoId.eq(494));
            }

            CalendarioPeriodoDetalle calendarioPeriodoDetalle =  where.querySingle();
            if (calendarioPeriodo == null||calendarioPeriodoDetalle==null){
                Log.d(TAG, "No tiene calendario detalle");
                return false;
            }
            Log.d(TAG, "calendarioPeriodo.getEstadoId() "+ calendarioPeriodo.getEstadoId());
            if(calendarioPeriodo.getEstadoId()== CalendarioPeriodo.CALENDARIO_PERIODO_VIGENTE ||
                    calendarioPeriodo.getEstadoId()== CalendarioPeriodo.CALENDARIO_PERIODO_CERRADO ) {

                Calendar fechaDetalleInicio = Calendar.getInstance();
                fechaDetalleInicio.setTimeInMillis(calendarioPeriodoDetalle.getFechaInicio());
                fechaDetalleInicio.set(Calendar.MILLISECOND, 0);
                fechaDetalleInicio.set(Calendar.SECOND, 0);
                fechaDetalleInicio.set(Calendar.MINUTE, 0);
                fechaDetalleInicio.set(Calendar.HOUR_OF_DAY, 0);

                Calendar fechaDetalleFin = Calendar.getInstance();
                fechaDetalleFin.setTimeInMillis(calendarioPeriodoDetalle.getFechaFin());
                fechaDetalleFin.set(Calendar.MILLISECOND, 0);
                fechaDetalleFin.set(Calendar.SECOND, 0);
                fechaDetalleFin.set(Calendar.MINUTE, 0);
                fechaDetalleFin.set(Calendar.HOUR_OF_DAY, 0);

                int resutDetalleInicio = fechaActual.compareTo(fechaDetalleInicio);
                int resutDetalleFin = fechaActual.compareTo(fechaDetalleFin);

                Log.d(TAG, "cargaCursoId" + cargaCursoId + " " + " calendarioPeriodoId: " + calendarioPeriodoDetalle.getCalendarioPeriodoDetId());

                if((resutDetalleInicio == 0 || resutDetalleInicio > 0) &&
                        (resutDetalleFin == 0 || resutDetalleFin < 0)
                ){
                    status = true;
                }



            }
        } catch (Exception e){
            e.printStackTrace();
            status = false;
        }finally {
        }
        return status;
    }

    @Override
    public void updateFirebaseUnidadesList(int idCargaCurso, int idCalendarioPeriodo, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(idCargaCurso))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        CalendarioPeriodo calendarioPeriodo = SQLite.select()
                .from(CalendarioPeriodo.class)
                .where(CalendarioPeriodo_Table.calendarioPeriodoId.eq(idCalendarioPeriodo))
                .querySingle();


        int tipoPeriodoId = calendarioPeriodo!=null?calendarioPeriodo.getTipoId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);

        mDatabase.child("/AV_Unidad/silid_"+silaboEventoId).orderByChild("TipoPeriodoId")
                .equalTo(tipoPeriodoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<UnidadAprendizaje> unidadAprendizajeRemoveList = SQLite.select()
                                .from(UnidadAprendizaje.class)
                                .queryList();
                        List<UnidadAprendizaje> unidadAprendizajeList = new ArrayList<>();
                        List<T_GC_REL_UNIDAD_APREN_EVENTO_TIPO> t_gc_rel_unidad_apren_evento_tipos = new ArrayList<>();
                        for (DataSnapshot unidadSnapshot: dataSnapshot.getChildren()){
                            FBUnidadAprendizaje fbUnidadAprendizaje = unidadSnapshot.getValue(FBUnidadAprendizaje.class);
                            UnidadAprendizaje unidadAprendizaje = new UnidadAprendizaje();
                            unidadAprendizaje.setUnidadAprendizajeId(fbUnidadAprendizaje.getUnidadAprendizajeId());
                            unidadAprendizaje.setTitulo(fbUnidadAprendizaje.getTitulo());
                            unidadAprendizaje.setNroHoras(fbUnidadAprendizaje.getNroHoras());
                            unidadAprendizaje.setNroSemanas(fbUnidadAprendizaje.getNroSemanas());
                            unidadAprendizaje.setSilaboEventoId(fbUnidadAprendizaje.getSilaboEventoId());
                            unidadAprendizaje.setNroUnidad(fbUnidadAprendizaje.getNroUnidad());
                            for (UnidadAprendizaje unidadAprendizajeRemove: unidadAprendizajeRemoveList){
                                if(fbUnidadAprendizaje.getUnidadAprendizajeId()==unidadAprendizajeRemove.getUnidadAprendizajeId()){
                                    unidadAprendizaje.setToogle(unidadAprendizajeRemove.isToogle());
                                    break;
                                }
                            }
                            unidadAprendizajeList.add(unidadAprendizaje);

                            T_GC_REL_UNIDAD_APREN_EVENTO_TIPO relUnidad = new T_GC_REL_UNIDAD_APREN_EVENTO_TIPO();
                            relUnidad.setUnidadaprendizajeId(fbUnidadAprendizaje.getUnidadAprendizajeId());
                            relUnidad.setTipoid(tipoPeriodoId);
                            t_gc_rel_unidad_apren_evento_tipos.add(relUnidad);
                        }

                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                TransaccionUtils.deleteTable(UnidadAprendizaje.class, UnidadAprendizaje_Table.silaboEventoId.eq(silaboEventoId));
                                TransaccionUtils.deleteTable(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class, T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.eq(tipoPeriodoId));
                                TransaccionUtils.fastStoreListInsert(UnidadAprendizaje.class, unidadAprendizajeList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class, t_gc_rel_unidad_apren_evento_tipos, databaseWrapper, false);
                            }
                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(@NonNull Transaction transaction) {
                                callback.onLoad(true);
                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                                error.printStackTrace();
                                callback.onLoad(false);
                            }
                        }).build();

                        transaction.execute();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
