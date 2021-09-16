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
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
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
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.util.JSONFirebase;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TabCursoRepositorioImpl implements TabCursoRepositorio {

    public static final String TAG =  TabCursoRepositorioImpl.class.getSimpleName();
    @Override
    public List<PeriodoUi> getPerioList(int anioAcademicoId, int cargaCursoId, int programaEduId) {
        List<PeriodoUi> periodoUiList = new ArrayList<>();
        List<CalendarioPeriodo> calendarioPeriodoList = SQLite.select(UtilsDBFlow.f_allcolumnTable(CalendarioPeriodo_Table.ALL_COLUMN_PROPERTIES))
                .from(CalendarioPeriodo.class)
                .innerJoin(CalendarioAcademico.class)
                .on(CalendarioPeriodo_Table.calendarioAcademicoId.withTable()
                        .eq(CalendarioAcademico_Table.calendarioAcademicoId.withTable()))
                .where(CalendarioAcademico_Table.idAnioAcademico.withTable().eq(anioAcademicoId))
                .and(CalendarioAcademico_Table.programaEduId.withTable().eq(programaEduId))
                .queryList();

        Log.d(TAG, "SIZEPERIODO1 : " + calendarioPeriodoList.size());

        for (CalendarioPeriodo periodo : calendarioPeriodoList) {
            Tipos tipo = SQLite.select()
                    .from(Tipos.class)
                    .where(Tipos_Table.tipoId.eq(periodo.getTipoId()))
                    .querySingle();
            if(tipo==null)continue;

            PeriodoUi periodoUi = new PeriodoUi(periodo.getCalendarioPeriodoId(), tipo.getNombre(), "", false);

            periodoUi.setFechaFin(periodo.getFechaFin());

            switch (periodo.getEstadoId()){
                case CalendarioPeriodo.CALENDARIO_PERIODO_CREADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Creado);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_VIGENTE:
                    periodoUi.setEstado(PeriodoUi.Estado.Vigente);
                    periodoUi.setVigente(true);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_CERRADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Cerrado);
                    break;
            }
            periodoUiList.add(periodoUi);
        }
        Log.d(TAG, "SIZEPERIODO1 : " + periodoUiList.size());
        return periodoUiList;
    }

    @Override
    public void updateFirebasePersonaList(int idCargaCurso, Callback callback) {
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

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getPersonaAlumno(silaboEventoId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                if(response==null){
                    callback.onLoad(false);
                }else {
                    List<Persona> personaList = new ArrayList<>();
                    List<JSONFirebase> jsonFirebaseList = JSONFirebase.d(response).getChildren();
                    for (JSONFirebase personaSnapshot: jsonFirebaseList){
                        if(UtilsFirebase.convert(personaSnapshot.child("Tipo").getValue(),0)==1){
                            Persona persona = new Persona();
                            persona.setPersonaId(UtilsFirebase.convert(personaSnapshot.child("PersonaId").getValue(),0));
                            persona.setNombres(UtilsFirebase.convert(personaSnapshot.child("Nombres").getValue(),""));
                            persona.setApellidoMaterno(UtilsFirebase.convert(personaSnapshot.child("ApellidoMaterno").getValue(),""));
                            persona.setApellidoPaterno(UtilsFirebase.convert(personaSnapshot.child("ApellidoPaterno").getValue(),""));
                            persona.setNumDoc(UtilsFirebase.convert(personaSnapshot.child("NumDoc").getValue(),""));
                            persona.setFoto(UtilsFirebase.convert(personaSnapshot.child("Foto").getValue(),""));
                            personaList.add(persona);
                        }
                    }

                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            TransaccionUtils.fastStoreListInsert(Persona.class, personaList, databaseWrapper, false);
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
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
            }
        });
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

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getUnidadAprendizajeAlumno(silaboEventoId, tipoPeriodoId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject dataSnapshot) {
                if(dataSnapshot==null){
                    callback.onLoad(false);
                }else {

                    List<UnidadAprendizaje> unidadAprendizajeRemoveList = SQLite.select()
                            .from(UnidadAprendizaje.class)
                            .queryList();

                    TransaccionUtils.deleteTable(UnidadAprendizaje.class, UnidadAprendizaje_Table.unidadAprendizajeId.eq(SQLite.select(UnidadAprendizaje_Table.unidadAprendizajeId.withTable())
                            .from(UnidadAprendizaje.class)
                            .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                            .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                                    .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()))
                            .where(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(silaboEventoId))
                            .and(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable().eq(tipoPeriodoId))));

                    TransaccionUtils.deleteTable(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class, T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.eq(tipoPeriodoId), T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.in(SQLite.select(UnidadAprendizaje_Table.unidadAprendizajeId.withTable())
                            .from(UnidadAprendizaje.class)
                            .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                            .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                                    .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()))
                            .where(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(silaboEventoId))
                            .and(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable().eq(tipoPeriodoId))));

                    List<UnidadAprendizaje> unidadAprendizajeList = new ArrayList<>();
                    List<T_GC_REL_UNIDAD_APREN_EVENTO_TIPO> t_gc_rel_unidad_apren_evento_tipos = new ArrayList<>();
                    for (JSONFirebase unidadSnapshot: JSONFirebase.d(dataSnapshot).getChildren()){
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
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
            }
        });

    }

}
