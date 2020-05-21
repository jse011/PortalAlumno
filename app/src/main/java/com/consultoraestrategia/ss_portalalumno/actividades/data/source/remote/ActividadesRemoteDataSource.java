package com.consultoraestrategia.ss_portalalumno.actividades.data.source.remote;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioTipoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;
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
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBActividadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBRecursos;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kike on 08/02/2018.
 */

public class ActividadesRemoteDataSource implements ActividadesDataSource {
    private String TAG = ActividadesRemoteDataSource.class.getSimpleName();

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> booleanCallback) {

    }

    @Override
    public void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress) {

    }

    @Override
    public void getActividadesList(int cargaCurso, int sesionAprendizajeId, String backgroundColor, CallbackActividades callbackActividades) {

    }

    @Override
    public void updateActividad(ActividadesUi actividadesUi, Callback<ActividadesUi> callback) {

    }

    @Override
    public void upadteFirebaseActividad(int cargaCurso, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList, CallbackSimple callbackSimple) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCurso))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();
        int unidadAprendizajeId = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        List<Integer> actividadesIdlist = new ArrayList<>();
        List<String> recursoIdList = new ArrayList<>();
        List<String> archivoIdList = new ArrayList<>();
        for (ActividadesUi actividadesUi : actividadesUiList){
            actividadesIdlist.add(actividadesUi.getId());
            if(actividadesUi.getRecursosUiList()!=null){
                for (RecursosUi recursosUi : actividadesUi.getRecursosUiList()){
                    recursoIdList.add(recursosUi.getRecursoId());
                    archivoIdList.add(recursosUi.getArchivoId());
                }
            }

            if(actividadesUi.getSubRecursosUiList()!=null){
                for (SubRecursosUi subRecursosUi : actividadesUi.getSubRecursosUiList()){
                    actividadesIdlist.add(subRecursosUi.getActividadId());
                    if(subRecursosUi.getRecursosUiList()!=null){
                        for (RecursosUi recursosUi : subRecursosUi.getRecursosUiList()){
                            recursoIdList.add(recursosUi.getRecursoId());
                            archivoIdList.add(recursosUi.getArchivoId());
                        }
                    }
                }
            }
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);

        mDatabase.child("/AV_Actividad/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<ActividadAprendizaje> actividadAprendizajeList = new ArrayList<>();
                        List<Tipos> tiposList = new ArrayList<>();
                        List<RecursoDidacticoEventoC> recursoDidacticoEventoList = new ArrayList<>();
                        List<RecursoReferenciaC> recursoReferenciaCList = new ArrayList<>();
                        List<Archivo> archivoList = new ArrayList<>();
                        List<RecursoArchivo> recursoArchivoList = new ArrayList<>();

                        for (DataSnapshot actividadSnapshot : dataSnapshot.getChildren()){
                           
                            FBActividadAprendizaje fbActividadAprendizaje = actividadSnapshot.getValue(FBActividadAprendizaje.class);
                            ActividadAprendizaje actividadAprendizaje = new ActividadAprendizaje();
                            actividadAprendizaje.setActividad(fbActividadAprendizaje.getActividad());
                            actividadAprendizaje.setActividadAprendizajeId(fbActividadAprendizaje.getActividadAprendizajeId());
                            actividadAprendizaje.setDescripcionActividad(fbActividadAprendizaje.getDescripcionActividad());
                            actividadAprendizaje.setSesionAprendizajeId(fbActividadAprendizaje.getSesionAprendizajeId());
                            actividadAprendizaje.setTipoActividadId(fbActividadAprendizaje.getTipoActividadId());
                            actividadAprendizajeList.add(actividadAprendizaje);
                            Tipos tipos = new Tipos();
                            tipos.setTipoId(actividadAprendizaje.getTipoActividadId());
                            tipos.setNombre(fbActividadAprendizaje.getTipoActividad());
                            tiposList.add(tipos);


                            if(fbActividadAprendizaje.getRecursoActividad()!=null){
                                for (Map.Entry<String, FBRecursos> mapFbRecursos: fbActividadAprendizaje.getRecursoActividad().entrySet()) {
                                    getRecursosFirebase(fbActividadAprendizaje.getActividadAprendizajeId(),mapFbRecursos.getValue(), recursoDidacticoEventoList, recursoReferenciaCList, archivoList, recursoArchivoList);
                                }
                            }

                            if(fbActividadAprendizaje.getSubActividad()!=null) {
                                for (Map.Entry<String, FBActividadAprendizaje> mapfbSubActividadAprendizaje : fbActividadAprendizaje.getSubActividad().entrySet()){
                                    FBActividadAprendizaje fbSubActividadAprendizaje = mapfbSubActividadAprendizaje.getValue();
                                    ActividadAprendizaje subActividad = new ActividadAprendizaje();
                                    subActividad.setActividad(fbSubActividadAprendizaje.getActividad());
                                    subActividad.setActividadAprendizajeId(fbSubActividadAprendizaje.getActividadAprendizajeId());
                                    subActividad.setDescripcionActividad(fbSubActividadAprendizaje.getDescripcionActividad());
                                    subActividad.setParentId(actividadAprendizaje.getActividadAprendizajeId());
                                    actividadAprendizajeList.add(subActividad);

                                    if(fbSubActividadAprendizaje.getRecursoActividad()!=null){
                                        for (Map.Entry<String, FBRecursos> mapFbRecursos: fbSubActividadAprendizaje.getRecursoActividad().entrySet()) {
                                            getRecursosFirebase(fbSubActividadAprendizaje.getActividadAprendizajeId(),mapFbRecursos.getValue(), recursoDidacticoEventoList, recursoReferenciaCList, archivoList, recursoArchivoList);
                                        }
                                    }
                                }

                            }

                        }

                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {

                                RecursoReferenciaC result = SQLite.select(Method.max(RecursoReferenciaC_Table.recursoReferenciaId).as("recursoReferenciaId")).from(RecursoReferenciaC.class)
                                        .querySingle();

                                int max = result!=null?result.getRecursoReferenciaId()+1:1;
                                Log.d(TAG, "max int"+ max);
                                for(RecursoReferenciaC recursoReferenciaC: recursoReferenciaCList){
                                    max++;
                                    Log.d(TAG, "max "+ max);
                                    recursoReferenciaC.setRecursoReferenciaId(max);
                                }


                                TransaccionUtils.deleteTable(RecursoDidacticoEventoC.class, RecursoDidacticoEventoC_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(RecursoReferenciaC.class, RecursoReferenciaC_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(RecursoArchivo.class, RecursoArchivo_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(ActividadAprendizaje.class, ActividadAprendizaje_Table.actividadAprendizajeId.in(actividadesIdlist));

                                TransaccionUtils.fastStoreListInsert(ActividadAprendizaje.class, actividadAprendizajeList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(Tipos.class, tiposList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(RecursoDidacticoEventoC.class, recursoDidacticoEventoList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(RecursoReferenciaC.class, recursoReferenciaCList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(Archivo.class, archivoList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(RecursoArchivo.class, recursoArchivoList, databaseWrapper, false);
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


    private void getRecursosFirebase(int actividadAprendizajeId, FBRecursos fbRecursos, List<RecursoDidacticoEventoC> recursoDidacticoEventoList, List<RecursoReferenciaC> recursoReferenciaCList, List<Archivo> archivoList, List<RecursoArchivo> recursoArchivoList){
            RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
            recursoDidacticoEventoC.setRecursoDidacticoId(fbRecursos.getRecursoDidacticoId());
            recursoDidacticoEventoC.setKey(fbRecursos.getRecursoDidacticoId());
            recursoDidacticoEventoC.setTipoId(fbRecursos.getTipoId());
            recursoDidacticoEventoC.setUrl(fbRecursos.getDescripcion());
            recursoDidacticoEventoC.setTitulo(fbRecursos.getTitulo());
            recursoDidacticoEventoC.setDescripcion(fbRecursos.getDescripcion());
            recursoDidacticoEventoList.add(recursoDidacticoEventoC);

            RecursoReferenciaC recursoReferenciaC = new RecursoReferenciaC();
            recursoReferenciaC.setActividadAprendizajeId(actividadAprendizajeId);
            recursoReferenciaC.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
            recursoReferenciaCList.add(recursoReferenciaC);

            boolean isYoutube = false;
            if(recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO){
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
