package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio;

import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
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
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBSesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UnidadAprendizajeRepositorioImpl implements UnidadAprendizajeRepositorio {

    private static final String TAG = UnidadAprendizajeRepositorioImpl.class.getSimpleName();

    public UnidadAprendizajeRepositorioImpl() {
    }

    @Override
    public List<UnidadAprendizajeUi> getUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId) {
        List<UnidadAprendizajeUi> listObject= new ArrayList<>();

        List<UnidadAprendizaje> vlst_unidadAprendizaje = SQLite
                .select(UtilsDBFlow.f_allcolumnTable(UnidadAprendizaje_Table.ALL_COLUMN_PROPERTIES))
                .from(UnidadAprendizaje.class)
                .innerJoin(SilaboEvento.class)
                .on(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(SilaboEvento_Table.silaboEventoId.withTable()))
                .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                .on(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()
                        .eq(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()))
                .innerJoin(Tipos.class)
                .on(Tipos_Table.tipoId.withTable()
                        .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable()))
                .innerJoin(CalendarioPeriodo.class)
                .on(CalendarioPeriodo_Table.tipoId.withTable()
                        .eq(Tipos_Table.tipoId.withTable()))
                .where(SilaboEvento_Table.cargaCursoId.withTable().eq(idCargaCurso))
                .and(CalendarioPeriodo_Table.calendarioPeriodoId.withTable().eq(idCalendarioPeriodo))
                .and(SilaboEvento_Table.anioAcademicoId.withTable().eq(idAnioAcademico))
                .and(SilaboEvento_Table.planCursoId.withTable().eq(planCursoId))
                .queryList();


        Date date = Calendar.getInstance().getTime();
        // Display a date in day, month, year format
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        String today = formatter.format(date);

        for(UnidadAprendizaje unidadAprendizaje:vlst_unidadAprendizaje ){
            //sesionAprendizajeUiList.clear();

            UnidadAprendizajeUi unidad= new UnidadAprendizajeUi();
            unidad.setUnidadAprendizajeId(unidadAprendizaje.getUnidadAprendizajeId());
            unidad.setNroUnidad(unidadAprendizaje.getNroUnidad());
            unidad.setTitulo(unidadAprendizaje.getTitulo());
            unidad.setSituacionSignificativa(unidadAprendizaje.getSituacionSignificativa());
            unidad.setNroHoras(unidadAprendizaje.getNroHoras());
            unidad.setNroSesiones(unidadAprendizaje.getNroSesiones());
            unidad.setNroSemanas(unidadAprendizaje.getNroSemanas());
            unidad.setEstadoId(unidadAprendizaje.getEstadoId());
            unidad.setSilaboEventoId(unidadAprendizaje.getSilaboEventoId());
            unidad.setSituacionSignificativaComplementaria(unidadAprendizaje.getSituacionSignificativaComplementaria());
            unidad.setDesafio(unidadAprendizaje.getDesafio());
            unidad.setReto(unidadAprendizaje.getReto());
            unidad.setToogle(unidadAprendizaje.isToogle());

            List<SesionAprendizaje> sesionAprendizajes = SQLite.select()
                    .from(SesionAprendizaje.class)
                    .where(SesionAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizaje.getUnidadAprendizajeId()))
                    .and(SesionAprendizaje_Table.rolId.eq(6))
                    .and(SesionAprendizaje_Table.estadoId.in(SesionAprendizaje.CREADO_ESTADO,SesionAprendizaje.AUTORIZADO_ESTADO))//297
                    .orderBy(SesionAprendizaje_Table.fechaEjecucion.desc())
                    .queryList();

            List<SesionAprendizajeUi>sesionAprendizajeUiList= new ArrayList<>();

            for(SesionAprendizaje sesion:sesionAprendizajes ){

                Calendar calendarSesion = Calendar.getInstance();
                calendarSesion.setTimeInMillis(sesion.getFechaEjecucion());
                // Display a date in day, month, year format
                String fehaSesion = formatter.format(calendarSesion.getTime());
                Log.d(TAG, fehaSesion+"="+today);
                Log.d(TAG,fehaSesion.equals(today)+"");
                SesionAprendizajeUi sesionAprendizajeUi = new SesionAprendizajeUi();
                sesionAprendizajeUi.setSesionAprendizajeId(sesion.getSesionAprendizajeId());
                sesionAprendizajeUi.setUnidadAprendizajeId(sesion.getUnidadAprendizajeId());
                sesionAprendizajeUi.setTitulo(sesion.getTitulo());
                sesionAprendizajeUi.setHoras(sesion.getHoras());
                sesionAprendizajeUi.setContenido(sesion.getContenido());
                sesionAprendizajeUi.setUsuarioCreador(sesion.getUsuarioCreador());
                sesionAprendizajeUi.setUsuarioAccion(sesion.getUsuarioAccion());
                sesionAprendizajeUi.setEstadoId(sesion.getEstadoId());
                sesionAprendizajeUi.setFechaEjecucion(sesion.getFechaEjecucion());
                sesionAprendizajeUi.setFechaReprogramacion(sesion.getFechaReprogramacion());
                sesionAprendizajeUi.setFechaPublicacion(sesion.getFechaPublicacion());
                sesionAprendizajeUi.setNroSesion(sesion.getNroSesion());
                sesionAprendizajeUi.setRolId(sesion.getRolId());
                sesionAprendizajeUi.setFechaRealizada(sesion.getFechaRealizada());
                sesionAprendizajeUi.setEstadoEjecucionId(sesion.getEstadoEjecucionId());
                sesionAprendizajeUi.setProposito(sesion.getProposito());
                sesionAprendizajeUi.setCantidad_recursos(0);
                sesionAprendizajeUi.setActual(fehaSesion.equals(today));
                sesionAprendizajeUiList.add(sesionAprendizajeUi);
            }

            unidad.setObjectListSesiones(sesionAprendizajeUiList);
            listObject.add(unidad);
        }
        return listObject;
    }

    @Override
    public void updateFirebaseUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int plancursoId, List<UnidadAprendizajeUi> unidadAprendizajeUiList, Callback callback) {

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

        mDatabase.child("/AV_Sesion/silid_"+silaboEventoId+"/peid_"+tipoPeriodoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        TransaccionUtils.deleteTable(SesionAprendizaje.class, SesionAprendizaje_Table.unidadAprendizajeId.in(SQLite.select(UnidadAprendizaje_Table.unidadAprendizajeId.withTable())
                                .from(UnidadAprendizaje.class)
                                .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                                .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                                        .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()))
                                .where(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(silaboEventoId))
                                .and(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable().eq(tipoPeriodoId))));


                        List<SesionAprendizaje> sesionAprendizajeList = new ArrayList<>();
                        for (DataSnapshot unidadSnapshot: dataSnapshot.getChildren()){
                            for (DataSnapshot sesionSnapshot: unidadSnapshot.getChildren()){
                                FBSesionAprendizaje fbSesionAprendizaje = sesionSnapshot.getValue(FBSesionAprendizaje.class);
                                SesionAprendizaje sesionAprendizaje = new SesionAprendizaje();
                                sesionAprendizaje.setSesionAprendizajeId(fbSesionAprendizaje.getSesionAprendizajeId());
                                sesionAprendizaje.setTitulo(fbSesionAprendizaje.getTitulo());
                                sesionAprendizaje.setEstadoId(fbSesionAprendizaje.getEstadoId());
                                sesionAprendizaje.setRolId(6);
                                sesionAprendizaje.setEstadoId(SesionAprendizaje.AUTORIZADO_ESTADO);
                                sesionAprendizaje.setNroSesion(fbSesionAprendizaje.getNroSesion());
                                sesionAprendizaje.setHoras(fbSesionAprendizaje.getHoras());
                                try {
                                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                    Date todayCalendar = simpleDateFormat1.parse(fbSesionAprendizaje.getFechaEjecucion());
                                    sesionAprendizaje.setFechaEjecucion(todayCalendar.getTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                sesionAprendizaje.setUnidadAprendizajeId(fbSesionAprendizaje.getUnidadAprendizajeId());
                                sesionAprendizaje.setEstadoEjecucionId(fbSesionAprendizaje.getEstadoEjecucionId());
                                sesionAprendizajeList.add(sesionAprendizaje);
                            }
                        }

                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                TransaccionUtils.fastStoreListInsert(SesionAprendizaje.class, sesionAprendizajeList, databaseWrapper, false);
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
                        callback.onLoad(false);
                    }
                });
    }

    @Override
    public void saveToogleUnidad(UnidadAprendizajeUi unidadAprendizajeUi) {

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeUi.getUnidadAprendizajeId()))
                .querySingle();
        if(unidadAprendizaje!=null) {
            unidadAprendizaje.setToogle(unidadAprendizajeUi.isToogle());
            unidadAprendizaje.save();
        }

    }

}
