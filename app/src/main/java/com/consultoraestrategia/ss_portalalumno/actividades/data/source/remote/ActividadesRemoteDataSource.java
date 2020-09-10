package com.consultoraestrategia.ss_portalalumno.actividades.data.source.remote;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.InstrumentoUi;
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
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
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
    public void getActividadesList(int cargaCurso, int sesionAprendizajeId, CallbackActividades callbackActividades) {

    }

    @Override
    public void updateActividad(ActividadesUi actividadesUi, Callback<ActividadesUi> callback) {

    }

    @Override
    public void upadteFirebaseActividad(int cargaCurso, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList2, CallbackSimple callbackSimple) {
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




                            ActividadAprendizaje actividadAprendizaje = new ActividadAprendizaje();
                            actividadAprendizaje.setActividad(UtilsFirebase.convert(actividadSnapshot.child("Actividad").getValue(),""));
                            actividadAprendizaje.setActividadAprendizajeId(UtilsFirebase.convert(actividadSnapshot.child("ActividadAprendizajeId").getValue(),0));
                            actividadAprendizaje.setDescripcionActividad(UtilsFirebase.convert(actividadSnapshot.child("DescripcionActividad").getValue(),""));
                            actividadAprendizaje.setSesionAprendizajeId(UtilsFirebase.convert(actividadSnapshot.child("SesionAprendizajeId").getValue(),0));
                            actividadAprendizaje.setTipoActividadId(UtilsFirebase.convert(actividadSnapshot.child("TipoActividadId").getValue(),0));
                            actividadAprendizaje.setInstrumentoEvalId(UtilsFirebase.convert(actividadSnapshot.child("InstrumentoEvalId").getValue(),0));
                            actividadAprendizajeList.add(actividadAprendizaje);
                            Tipos tipos = new Tipos();
                            tipos.setTipoId(actividadAprendizaje.getTipoActividadId());
                            tipos.setNombre(UtilsFirebase.convert(actividadSnapshot.child("TipoActividad").getValue(),""));
                            tiposList.add(tipos);

                            if(actividadSnapshot.child("RecursoActividad").exists()){
                                for (DataSnapshot recusoActividadSnapshot : actividadSnapshot.child("RecursoActividad").getChildren()){
                                    getRecursosFirebase(actividadAprendizaje.getActividadAprendizajeId(),recusoActividadSnapshot, recursoDidacticoEventoList, recursoReferenciaCList, archivoList, recursoArchivoList);
                                }
                            }

                            if(actividadSnapshot.child("SubActividad").exists()){
                                for (DataSnapshot subActividadSnapshot : actividadSnapshot.child("SubActividad").getChildren()){

                                    ActividadAprendizaje subActividad = new ActividadAprendizaje();
                                    subActividad.setActividad(UtilsFirebase.convert(subActividadSnapshot.child("Actividad").getValue(),""));
                                    subActividad.setActividadAprendizajeId(UtilsFirebase.convert(subActividadSnapshot.child("ActividadAprendizajeId").getValue(),0));
                                    subActividad.setDescripcionActividad(UtilsFirebase.convert(subActividadSnapshot.child("DescripcionActividad").getValue(),""));
                                    subActividad.setParentId(actividadAprendizaje.getActividadAprendizajeId());
                                    subActividad.setInstrumentoEvalId(UtilsFirebase.convert(subActividadSnapshot.child("InstrumentoEvalId").getValue(),0));
                                    actividadAprendizajeList.add(subActividad);

                                    if(subActividadSnapshot.child("RecursoActividad").exists()){
                                        for (DataSnapshot recusoActividadSnapshot : subActividadSnapshot.child("RecursoActividad").getChildren()){
                                            getRecursosFirebase(subActividad.getActividadAprendizajeId(),recusoActividadSnapshot, recursoDidacticoEventoList, recursoReferenciaCList, archivoList, recursoArchivoList);
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
                                for(RecursoReferenciaC recursoReferenciaC: recursoReferenciaCList){
                                    max++;
                                    recursoReferenciaC.setRecursoReferenciaId(max);
                                }

                                List<Integer> actividadAprendizajeIdList = new ArrayList<>();
                                for (ActividadAprendizaje actividadAprendizaje : actividadAprendizajeList){
                                    actividadAprendizajeIdList.add(actividadAprendizaje.getActividadAprendizajeId());
                                }

                                //TransaccionUtils.deleteTable(RecursoDidacticoEventoC.class, RecursoDidacticoEventoC_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(RecursoReferenciaC.class, RecursoReferenciaC_Table.actividadAprendizajeId.in(actividadAprendizajeIdList));
                                //TransaccionUtils.deleteTable(RecursoArchivo.class, RecursoArchivo_Table.recursoDidacticoId.in(recursoIdList));
                                TransaccionUtils.deleteTable(ActividadAprendizaje.class, ActividadAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId));

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

    @Override
    public List<InstrumentoUi> getInstrumentos(int sesionAprendizajeId) {
        return null;
    }


    private void getRecursosFirebase(int actividadAprendizajeId, DataSnapshot dataSnapshot, List<RecursoDidacticoEventoC> recursoDidacticoEventoList, List<RecursoReferenciaC> recursoReferenciaCList, List<Archivo> archivoList, List<RecursoArchivo> recursoArchivoList){

            RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
            recursoDidacticoEventoC.setRecursoDidacticoId(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            recursoDidacticoEventoC.setKey(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            recursoDidacticoEventoC.setTipoId(UtilsFirebase.convert(dataSnapshot.child("TipoId").getValue(),0));
            recursoDidacticoEventoC.setUrl(UtilsFirebase.convert(dataSnapshot.child("Descripcion").getValue(),""));
            recursoDidacticoEventoC.setTitulo(UtilsFirebase.convert(dataSnapshot.child("Titulo").getValue(),""));
            recursoDidacticoEventoC.setDescripcion(UtilsFirebase.convert(dataSnapshot.child("Descripcion").getValue(),""));
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
                        .where(Archivo_Table.archivoId.eq(recursoDidacticoEventoC.getRecursoDidacticoId()))
                        .querySingle();
                if(archivo==null)archivo = new Archivo();
                archivo.setKey(recursoDidacticoEventoC.getRecursoDidacticoId());
                archivo.setArchivoId(recursoDidacticoEventoC.getRecursoDidacticoId());
                archivo.setNombre("");
                archivo.setPath(UtilsFirebase.convert(dataSnapshot.child("DriveId").getValue(),""));
                archivoList.add(archivo);

                RecursoArchivo recursoArchivo = new RecursoArchivo();
                recursoArchivo.setArchivoId(archivo.getKey());
                recursoArchivo.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
                recursoArchivoList.add(recursoArchivo);

            }
    }
}
