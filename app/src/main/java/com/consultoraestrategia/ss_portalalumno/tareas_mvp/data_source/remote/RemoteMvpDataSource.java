package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.ActividadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.ActividadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBTareaEvento;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks.GetTareasListCallback;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public class RemoteMvpDataSource implements TareasMvpDataSource {

    private String TAG = RemoteMvpDataSource.class.getSimpleName();

    public RemoteMvpDataSource(Context context) {

    }


    @Override
    public void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis) {

    }

    @Override
    public List<RecursosUI> getRecursosTarea(String tareaId) {
        return null;
    }

    @Override
    public void getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId,GetTareasListCallback callback) {

    }

    @Override
    public void getParametroDisenio(int parametroDisenioId, CallbackTareas callbackTareas) {

    }

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> callback) {

    }

    @Override
    public void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress) {

    }

    @Override
    public void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, List<TareasUI> tareasUIList,CallbackSimple callbackSimple) {
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
                .where(CalendarioPeriodo_Table.calendarioPeriodoId.eq(calendarioPeriodoId))
                .querySingle();

        int tipoPeriodoId = calendarioPeriodo!=null?calendarioPeriodo.getTipoId():0;

        List<String> tareaIdList = new ArrayList<>();
        List<String> recursoIdList = new ArrayList<>();
        for (TareasUI tareasUI : tareasUIList){
            tareaIdList.add(tareasUI.getKeyTarea());
            for (RecursosUI recursosUI : tareasUI.getRecursosUIList()){
                recursoIdList.add(recursosUI.getRecursoId());
            }
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Tarea/silid_"+silaboEventoId+"/peid_"+tipoPeriodoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<TareasC> tareasCList =  new ArrayList<>();
                        List<Tipos> tiposList = new ArrayList<>();
                        List<RecursoDidacticoEventoC> recursoDidacticoEventoList = new ArrayList<>();
                        List<TareasRecursosC> tareasRecursosCList = new ArrayList<>();
                        List<Archivo> archivoList = new ArrayList<>();
                        List<RecursoArchivo> recursoArchivoList = new ArrayList<>();

                        for (DataSnapshot unidadSnapshot : dataSnapshot.getChildren()){
                            String key = unidadSnapshot.getKey();
                            String[] keys = key!=null?key.split("unid_"):new String[]{};
                            int unidadAprendizajeId = 0;
                            if(keys.length>1){
                                unidadAprendizajeId = Integer.parseInt(keys[1]);
                            }

                            for (DataSnapshot tareaSnapshot : unidadSnapshot.getChildren()){
                                FBTareaEvento fbTareaEvento = tareaSnapshot.getValue(FBTareaEvento.class);
                                TareasC tareasC = new TareasC();
                                tareasC.setKey(fbTareaEvento.getTareaId());
                                tareasC.setTareaId(fbTareaEvento.getTareaId());
                                tareasC.setEstadoId(TareasC.ESTADO_PUBLICADO);
                                tareasC.setHoraEntrega(tareasC.getHoraEntrega());

                                try {
                                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                    Date todayCalendar = simpleDateFormat1.parse(fbTareaEvento.getFechaEntrega());
                                    tareasC.setFechaEntrega(todayCalendar.getTime());
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                tareasC.setInstrucciones(fbTareaEvento.getInstrucciones());
                                tareasC.setTitulo(fbTareaEvento.getTitulo());
                                tareasC.setUnidadAprendizajeId(unidadAprendizajeId);
                                tareasC.setSesionAprendizajeId(fbTareaEvento.getSesionAprendizajeId());
                                tareasCList.add(tareasC);
                                if(fbTareaEvento.getRecursosTarea()!=null)
                                for (Map.Entry<String, FBTareaEvento.FBRecursoTarea> mapFbRecursosTarea: fbTareaEvento.getRecursosTarea().entrySet()) {
                                    FBTareaEvento.FBRecursoTarea fbRecursoTarea = mapFbRecursosTarea.getValue();
                                    getRecursosFirebase(fbTareaEvento.getTareaId(), fbRecursoTarea, recursoDidacticoEventoList, tareasRecursosCList, archivoList, recursoArchivoList);
                                }

                            }

                        }

                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.isNull());
                                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.in(tareaIdList));
                                TransaccionUtils.deleteTable(TareasRecursosC.class, TareasRecursosC_Table.tareaId.in(tareaIdList));
                                TransaccionUtils.deleteTable(RecursoDidacticoEventoC.class, RecursoDidacticoEventoC_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(RecursoArchivo.class, RecursoArchivo_Table.recursoDidacticoId.in(recursoIdList));

                                TransaccionUtils.fastStoreListInsert(TareasC.class, tareasCList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(TareasRecursosC.class, tareasRecursosCList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(RecursoDidacticoEventoC.class, recursoDidacticoEventoList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(RecursoArchivo.class, recursoArchivoList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(Archivo.class, archivoList, databaseWrapper, false);
                            }
                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(@NonNull Transaction transaction) {
                                callbackSimple.onLoad(true);
                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                                error.printStackTrace();
                                callbackSimple.onLoad(false);
                            }
                        }).build();

                        transaction.execute();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbackSimple.onLoad(false);
                    }
                });


    }

    @Override
    public void updateFirebaseTareaSesion(int idCargaCurso, int calendarioPeriodoId, int SesionAprendizajeId, List<TareasUI> tareasUIList, CallbackSimple callbackSimple) {
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
                .where(CalendarioPeriodo_Table.calendarioPeriodoId.eq(calendarioPeriodoId))
                .querySingle();

        int tipoPeriodoId = calendarioPeriodo!=null?calendarioPeriodo.getTipoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(SesionAprendizajeId))
                .querySingle();
        int unidadAprendizajeId = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        List<String> tareaIdList = new ArrayList<>();
        for (TareasUI tareasUI : tareasUIList)tareaIdList.add(tareasUI.getKeyTarea());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Tarea/silid_"+silaboEventoId+"/peid_"+tipoPeriodoId+"/unid_"+unidadAprendizajeId)
                .orderByChild("SesionAprendizajeId")
                .equalTo(SesionAprendizajeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<TareasC> tareasCList =  new ArrayList<>();

                        for (DataSnapshot tareaSnapshot : dataSnapshot.getChildren()){
                            FBTareaEvento fbTareaEvento = tareaSnapshot.getValue(FBTareaEvento.class);
                            TareasC tareasC = new TareasC();
                            tareasC.setKey(fbTareaEvento.getTareaId());
                            tareasC.setTareaId(fbTareaEvento.getTareaId());
                            tareasC.setEstadoId(TareasC.ESTADO_PUBLICADO);
                            tareasC.setHoraEntrega(tareasC.getHoraEntrega());

                            try {
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                Date todayCalendar = simpleDateFormat1.parse(fbTareaEvento.getFechaEntrega());
                                tareasC.setFechaEntrega(todayCalendar.getTime());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            tareasC.setInstrucciones(fbTareaEvento.getInstrucciones());
                            tareasC.setTitulo(fbTareaEvento.getTitulo());
                            tareasC.setUnidadAprendizajeId(unidadAprendizajeId);
                            tareasC.setSesionAprendizajeId(fbTareaEvento.getSesionAprendizajeId());
                            tareasCList.add(tareasC);
                        }

                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.isNull());
                                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.in(tareaIdList));
                                TransaccionUtils.fastStoreListInsert(TareasC.class, tareasCList, databaseWrapper, false);
                            }
                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(@NonNull Transaction transaction) {
                                callbackSimple.onLoad(true);
                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                                error.printStackTrace();
                                callbackSimple.onLoad(false);
                            }
                        }).build();

                        transaction.execute();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbackSimple.onLoad(false);
                    }
                });


    }


    private void getRecursosFirebase(String TareaId, FBTareaEvento.FBRecursoTarea fbRecursos, List<RecursoDidacticoEventoC> recursoDidacticoEventoList, List<TareasRecursosC> tareasRecursosCList, List<Archivo> archivoList, List<RecursoArchivo> recursoArchivoList){
        Log.d(TAG,"getRecursosFirebase "+ fbRecursos.getRecursoDidacticoId());

        RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
        recursoDidacticoEventoC.setRecursoDidacticoId(fbRecursos.getRecursoDidacticoId());
        recursoDidacticoEventoC.setTitulo(fbRecursos.getTitulo());
        recursoDidacticoEventoC.setKey(fbRecursos.getRecursoDidacticoId());
        recursoDidacticoEventoC.setTipoId(fbRecursos.getTipoId());
        recursoDidacticoEventoC.setDescripcion(fbRecursos.getDescripcion());
        recursoDidacticoEventoC.setUrl(fbRecursos.getUrl());
        recursoDidacticoEventoC.setEstado(1);
        recursoDidacticoEventoList.add(recursoDidacticoEventoC);

        TareasRecursosC tareasRecursosC = new TareasRecursosC();
        tareasRecursosC.setTareaId(TareaId);
        tareasRecursosC.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
        tareasRecursosCList.add(tareasRecursosC);

        boolean isYoutube = false;
        if(recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VINCULO||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO){
            isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursoDidacticoEventoC.getUrl()));
        }

        if (recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_AUDIO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_DIAPOSITIVA ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_DOCUMENTO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_HOJA_CALCULO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_IMAGEN ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_PDF ||
                (recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO
                        && !isYoutube)
        ) {

            Archivo archivo = SQLite.select()
                    .from(Archivo.class)
                    .where(Archivo_Table.archivoId.eq(fbRecursos.getRecursoDidacticoId()))
                    .querySingle();
            if(archivo==null)archivo = new Archivo();
            archivo.setKey(fbRecursos.getRecursoDidacticoId());
            archivo.setArchivoId(fbRecursos.getRecursoDidacticoId());
            archivo.setNombre("");
            archivo.setPath(fbRecursos.getDescripcion());
            archivoList.add(archivo);

            RecursoArchivo recursoArchivo = new RecursoArchivo();
            recursoArchivo.setArchivoId(archivo.getKey());
            recursoArchivo.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
            recursoArchivoList.add(recursoArchivo);

        }
    }

}
