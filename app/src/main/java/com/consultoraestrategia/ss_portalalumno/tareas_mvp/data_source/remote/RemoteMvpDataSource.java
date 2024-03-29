package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAlumnoArchivos;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumnoArchivos;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumnoArchivos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBTareaEvento;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancelImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancelImpl;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.DriveUrlParser;
import com.consultoraestrategia.ss_portalalumno.util.JSONFirebase;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public class RemoteMvpDataSource implements TareasMvpDataSource {

    private String TAG = RemoteMvpDataSource.class.getSimpleName();

    ApiRetrofit apiRetrofit;

    public RemoteMvpDataSource(ApiRetrofit apiRetrofit ) {
        this.apiRetrofit = apiRetrofit;
    }

    @Override
    public void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis) {

    }

    @Override
    public List<RecursosUI> getRecursosTarea(String tareaId) {
        return null;
    }

    @Override
    public List<TareaArchivoUi> getArchivoTareaAlumno(String tareaId) {
        return null;
    }

    @Override
    public StorageCancel uploadStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, StorageCallback<TareaArchivoUi> callbackStorage) {
        StorageCancel storageCancel = null;

        Uri uri = null;
        File file = null;
        if(tareaArchivoUi.getFile() instanceof File){
            file = (File)tareaArchivoUi.getFile();
        }else if(tareaArchivoUi.getFile() instanceof Uri){
            uri = (Uri)tareaArchivoUi.getFile();
        }

        if(file!=null||uri!=null||!TextUtils.isEmpty(tareaArchivoUi.getNombre())){
            if(file!=null){
                uri = Uri.fromFile(file);
            }

            int validate = 0;
            try {
                InputStream inputStream = FirebaseStorage.getInstance().getApp().getApplicationContext().getContentResolver().openInputStream(uri);
                int length = (inputStream!=null?inputStream.available():0)/(1024*1024);
                if(length<=200){
                    validate = 1;
                }

            } catch (IOException e) {
                e.printStackTrace();
                validate = -1;
            }

            if(validate==0){
                callbackStorage.onErrorMaxSize();
                //callbackStorage.onFinish(false, tareaArchivoUi);
            }else if(validate==-1){
                callbackStorage.onFinish(false, tareaArchivoUi);
            }else {


                TareasC tareasC = SQLite.select()
                        .from(TareasC.class)
                        .where(TareasC_Table.tareaId.eq(tareaId))
                        .querySingle();

                int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

                UnidadAprendizaje unidadAprendizaje = SQLite.select()
                        .from(UnidadAprendizaje.class)
                        .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                        .querySingle();
                int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

                SessionUser sessionUser = SessionUser.getCurrentUser();
                int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

              if(forzarConexion){
                  storageCancel = subirArchivosAlServidor(tareaArchivoUi, silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, uri, callbackStorage);
              }else {
                  storageCancel = subirArchivosAlFirebase(tareaArchivoUi, silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, uri, callbackStorage);
              }

            }
        }else {
            callbackStorage.onFinish(false, tareaArchivoUi);
        }
        // File or Blob
        return storageCancel;
    }

    StorageCancel subirArchivosAlServidor(TareaArchivoUi tareaArchivoUi, int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, Uri uri, StorageCallback<TareaArchivoUi> callbackStorage){
      
     
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        List<RetrofitCancel<String>> retrofitCancelList = new ArrayList<>();
        long size = -1;
        try {
            ContentResolver resolver =
                    FirebaseStorage.getInstance().getApp().getApplicationContext().getContentResolver();
            try {
                ParcelFileDescriptor fd = resolver.openFileDescriptor(uri, "r");
                if (fd != null) {
                    size = fd.getStatSize();
                    fd.close();
                }
            } catch (NullPointerException npe) {
                // happens under test.
                Log.w(TAG, "NullPointerException during file size calculation.", npe);
                size = -1;
            } catch (IOException checkSizeError) {
                Log.w(TAG, "could not retrieve file size for upload " + uri.toString(), checkSizeError);
            }

            InputStream inputStream = resolver.openInputStream(uri);
            if (inputStream != null) {
                if (size == -1) {
                    // If we had issues calculating the size, try stream.available -- it may still work
                    try {
                        int streamSize = inputStream.available();
                        if (streamSize >= 0) {
                            size = streamSize;
                        }
                    } catch (IOException e) {
                        // Ignore the error and continue without a size.  We document it may not be there.
                    }
                }
                inputStream = new BufferedInputStream(inputStream);
                ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
                apiRetrofit.setTime(30,30,30, TimeUnit.MINUTES);
                RetrofitCancelImpl<String> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.uploadFileTareaAlumno(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, inputStream, tareaArchivoUi.getNombre()));
                retrofitCancel.enqueue(new RetrofitCancel.Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                       if(response==null){
                           tareaArchivoUi.setState(0);
                           callbackStorage.onChange(tareaArchivoUi);
                           callbackStorage.onFinish(false,tareaArchivoUi);
                       }else {
                           tareaArchivoUi.setState(0);
                           tareaArchivoUi.setFile(null);
                           tareaArchivoUi.setProgress(0);
                           tareaArchivoUi.setPath(response);


                           TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                           tareaAlumnoArchivos.setTareaAlumnoArchivoId(response);
                           tareaAlumnoArchivos.setTareaId(tareaId);
                           tareaAlumnoArchivos.setAlumnoId(alumnoId);
                           tareaAlumnoArchivos.setPath(tareaArchivoUi.getPath());
                           tareaAlumnoArchivos.setNombre(tareaArchivoUi.getNombre());
                           tareaAlumnoArchivos.setRepositorio(true);
                           tareaAlumnoArchivos.save();

                           tareaArchivoUi.setId(response);

                           callbackStorage.onFinish(true, tareaArchivoUi);
                       }
                      
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        tareaArchivoUi.setState(0);
                        callbackStorage.onChange(tareaArchivoUi);
                        callbackStorage.onFinish(false,tareaArchivoUi);
                       
                    }
                });
                retrofitCancelList.add(retrofitCancel);

            }else{
                tareaArchivoUi.setState(0);
                callbackStorage.onChange(tareaArchivoUi);
              
                callbackStorage.onFinish(false,tareaArchivoUi);
            }



        } catch (FileNotFoundException e) {
            Log.e(TAG, "could not locate file for uploading:" + uri.toString());
            callbackStorage.onFinish(false,tareaArchivoUi);
        }


        return new StorageCancel() {
            @Override
            public void onPause() {

            }

            @Override
            public void onResume() {

            }

            @Override
            public void onCancel() {
             for (RetrofitCancel retrofitCancel : retrofitCancelList){
                 retrofitCancel.cancel();
             }
            }

            @Override
            public boolean isComplete() {
                boolean isComplete = true;
                for (RetrofitCancel retrofitCancel : retrofitCancelList){
                    isComplete = !retrofitCancel.isExecuted();
                }
                return  isComplete;
            }

            @Override
            public boolean isSuccessful() {
                boolean isSuccessful = true;
                for (RetrofitCancel retrofitCancel : retrofitCancelList){
                    isSuccessful = !retrofitCancel.isExecuted();
                }
                return  isSuccessful;
            }

            @Override
            public boolean isCanceled() {
                boolean isCanceled = false;
                for (RetrofitCancel retrofitCancel : retrofitCancelList){
                    isCanceled = retrofitCancel.isCanceled();
                }
                return  isCanceled;
            }

            @Override
            public boolean isInProgress() {
                boolean isInProgress = false;
                for (RetrofitCancel retrofitCancel : retrofitCancelList){
                    isInProgress = retrofitCancel.isExecuted();
                }
                return  isInProgress;
            }

            @Override
            public boolean isPaused() {
                return false;
            }
        };
    }

    StorageCancel subirArchivosAlFirebase(TareaArchivoUi tareaArchivoUi, int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, Uri uri,StorageCallback<TareaArchivoUi> callbackStorage){
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType(UtilsStorage.getMimeType(tareaArchivoUi.getNombre()))
                .setContentDisposition("attachment")
                .build();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Tarea_Evaluacion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/tarid_" + tareaId + "/pers_" + alumnoId + "/" + tareaArchivoUi.getNombre());

        // Upload file and metadata to the path 'images/mountains.jpg'
        StorageCancelImpl storageCancel = new StorageCancelImpl(storageReference.putFile(uri, metadata));

        // Listen for state changes, errors, and completion of the upload.
        storageCancel.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                //Para no recargar a la vista
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progress = Math.max(progress,2);
                //if ((progress == Math.floor(progress)) && !Double.isInfinite(progress)) {
                // integer type

                //}
                tareaArchivoUi.setProgress(progress);
                tareaArchivoUi.setState(1);
                callbackStorage.onChange(tareaArchivoUi);
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                tareaArchivoUi.setState(0);
                callbackStorage.onChange(tareaArchivoUi);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                // Handle unsuccessful uploads
                tareaArchivoUi.setState(0);
                callbackStorage.onChange(tareaArchivoUi);
                callbackStorage.onFinish(false,tareaArchivoUi);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                tareaArchivoUi.setState(0);
                tareaArchivoUi.setFile(null);
                tareaArchivoUi.setProgress(0);
                tareaArchivoUi.setPath(storageReference.getPath());
                saveTareaEvaluacion(nodeFirebase, silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, tareaArchivoUi, callbackStorage);
            }
        });

        return storageCancel;
    }

    @Override
    public void deleteStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, CallbackSimple callbackSimple) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();

        int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                .querySingle();
        int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

        if(!forzarConexion){
            if(tareaArchivoUi.getTipo()!= TareaArchivoUi.Tipo.LINK&&tareaArchivoUi.getTipo()!= TareaArchivoUi.Tipo.YOUTUBE&tareaArchivoUi.getTipo()!= TareaArchivoUi.Tipo.DRIVE){
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                        .child("/AV_Tarea_Evaluacion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/tarid_" + tareaId + "/pers_" + alumnoId + "/" + tareaArchivoUi.getNombre());

                // Delete the file
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        deleteTareaEvaluacion(nodeFirebase, silaboEventoId, unidadAprendizajeId, tareaId,alumnoId, tareaArchivoUi, callbackSimple);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        exception.printStackTrace();
                        deleteTareaEvaluacion(nodeFirebase, silaboEventoId, unidadAprendizajeId, tareaId,alumnoId, tareaArchivoUi, callbackSimple);
                    }
                });
            }else {
                deleteTareaEvaluacion(nodeFirebase, silaboEventoId, unidadAprendizajeId, tareaId,alumnoId, tareaArchivoUi, callbackSimple);
            }
        }else {

            RetrofitCancelImpl<Boolean> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.deleteFileTareaAlumno(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, tareaArchivoUi.getId()));
            retrofitCancel.enqueue(new RetrofitCancel.Callback<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    if(response==null){
                        callbackSimple.onLoad(false);
                    }else {
                        callbackSimple.onLoad(true);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    callbackSimple.onLoad(false);
                }
            });
        }


    }

    @Override
    public void publicarTareaAlumno(String tareaId, boolean forzarConexion, CallbackSimple callbackSimple) {



        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();

        int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                .querySingle();
        int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;


        TareaAlumno tareaAlumno = SQLite.select()
                .from(TareaAlumno.class)
                .where(TareaAlumno_Table.tareaId.eq(tareaId))
                .and(TareaAlumno_Table.alumnoId.eq(alumnoId))
                .querySingle();

        boolean entregado = !(tareaAlumno != null && tareaAlumno.isEntregado());

        if(forzarConexion){
            entregarTareaServidor(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, entregado, tareaAlumno, callbackSimple);
        }else {
            entregarTareaFirebase(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, entregado, tareaAlumno, callbackSimple);
        }




    }

    public void entregarTareaServidor (int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, boolean entregado,TareaAlumno tareaAlumno, CallbackSimple callbackSimple){
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15,TimeUnit.SECONDS);
        RetrofitCancelImpl<Object> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.entregarTareaAlumno(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, entregado));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<Object>() {
            @Override
            public void onResponse(Object response) {
                if(response==null){
                    callbackSimple.onLoad(false);;
                }else {
                    try {
                        long fechaEntrega = UtilsFirebase.convert(response,0L);
                        if(tareaAlumno!=null){
                            tareaAlumno.setEntregado(entregado);
                            tareaAlumno.setFechaEntrega(fechaEntrega);
                            tareaAlumno.save();
                        }else {
                            TareaAlumno newTareaAlumno = new TareaAlumno();
                            newTareaAlumno.setAlumnoId(alumnoId);
                            newTareaAlumno.setTareaId(tareaId);
                            newTareaAlumno.setEntregado(entregado);
                            newTareaAlumno.setFechaEntrega(fechaEntrega);
                            newTareaAlumno.save();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    callbackSimple.onLoad(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callbackSimple.onLoad(false);
            }
        });

    }
    public void entregarTareaFirebase (int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, boolean entregado,TareaAlumno tareaAlumno, CallbackSimple callbackSimple){
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Tarea_Evaluacion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/tarid_"+tareaId+"_pers_"+alumnoId);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/FechaEntrega", ServerValue.TIMESTAMP);
        childUpdates.put("/Entregado", entregado);
        childUpdates.put("/AlumnoId",alumnoId);
        childUpdates.put("/TareaId",tareaId);
        mDatabase.updateChildren(childUpdates, (databaseError, databaseReference) -> {
            if(databaseError==null){
                mDatabase.child("FechaEntrega")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    long fechaEntrega = UtilsFirebase.convert(dataSnapshot.getValue(),0L);
                                    if(tareaAlumno!=null){
                                        tareaAlumno.setEntregado(entregado);
                                        tareaAlumno.setFechaEntrega(fechaEntrega);
                                        tareaAlumno.save();
                                    }else {
                                        TareaAlumno newTareaAlumno = new TareaAlumno();
                                        newTareaAlumno.setAlumnoId(alumnoId);
                                        newTareaAlumno.setTareaId(tareaId);
                                        newTareaAlumno.setEntregado(entregado);
                                        newTareaAlumno.setFechaEntrega(fechaEntrega);
                                        newTareaAlumno.save();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                callbackSimple.onLoad(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                callbackSimple.onLoad(false);
                            }
                        });
            }else {
                callbackSimple.onLoad(false);
            }
        });
    }

    @Override
    public TareasUI isEntregadoTareaAlumno(String tareaId) {
        return null;
    }

    @Override
    public TareasUI getTarea(String tareaId) {
        return null;
    }

    @Override
    public void uploadLinkFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, CallbackSimple simple) {


        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();

        int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                .querySingle();
        int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

        if(forzarConexion){
            ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
            apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
            RetrofitCancelImpl<String> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.uploadLinkTareaAlumno(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId, tareaArchivoUi.getNombre(), tareaArchivoUi.getPath()));
            retrofitCancel.enqueue(new RetrofitCancel.Callback<String>() {
                @Override
                public void onResponse(String response) {
                    if(response!=null){
                        TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                        tareaAlumnoArchivos.setTareaAlumnoArchivoId(response);
                        tareaAlumnoArchivos.setTareaId(tareaId);
                        tareaAlumnoArchivos.setAlumnoId(alumnoId);
                        tareaAlumnoArchivos.setPath(tareaArchivoUi.getPath());
                        tareaAlumnoArchivos.setNombre(tareaArchivoUi.getNombre());
                        tareaAlumnoArchivos.setRepositorio(false);
                        tareaAlumnoArchivos.save();

                        tareaArchivoUi.setId(response);
                        simple.onLoad(true);
                    }else {
                        simple.onLoad(false);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    simple.onLoad(false);
                }
            });
        }else {

            Webconfig webconfig = SQLite.select()
                    .from(Webconfig.class)
                    .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                    .querySingle();

            String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
            HashMap<String, Object> hashMap =  new HashMap<>();
            hashMap.put("Nombre", tareaArchivoUi.getNombre());
            hashMap.put("Path", tareaArchivoUi.getDescripcion());
            hashMap.put("Repositorio", false);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                    .child("/AV_Tarea_Evaluacion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/tarid_"+tareaId+"_pers_"+alumnoId+"/Archivos")
                    .push();

            mDatabase.setValue(hashMap,new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                    tareaAlumnoArchivos.setTareaAlumnoArchivoId(mDatabase.getKey());
                    tareaAlumnoArchivos.setTareaId(tareaId);
                    tareaAlumnoArchivos.setAlumnoId(alumnoId);
                    tareaAlumnoArchivos.setPath(tareaArchivoUi.getPath());
                    tareaAlumnoArchivos.setNombre(tareaArchivoUi.getNombre());
                    tareaAlumnoArchivos.setRepositorio(false);
                    tareaAlumnoArchivos.save();

                    String idYoutube = YouTubeUrlParser.getVideoId(tareaArchivoUi.getPath());
                    String idDrive = DriveUrlParser.getDocumentId(tareaArchivoUi.getPath());
                    if(!TextUtils.isEmpty(idYoutube)){
                        tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.YOUTUBE);
                    }else if(!TextUtils.isEmpty(idDrive)){
                        tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.DRIVE);
                    }else {
                        tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.LINK);
                    }

                    tareaArchivoUi.setId(mDatabase.getKey());
                    if(databaseError!=null){
                        simple.onLoad(false);
                    }else {
                        simple.onLoad(true);
                    }
                }
            });
        }


    }

    @Override
    public List<HeaderTareasAprendizajeUI> getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId) {
        return null;
    }

    @Override
    public void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, String tareaId, CallbackSimple callback) {
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

        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();

        int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Tarea/silid_"+silaboEventoId+"/peid_"+tipoPeriodoId+"/unid_"+unidadAprendizajeId+"/tarid_"+tareaId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        TareasC tareasC = SQLite.select()
                                .from(TareasC.class)
                                .where(TareasC_Table.tareaId.eq(tareaId))
                                .querySingle();
                        int orden = tareasC!=null?tareasC.getNumero():0;//Temporal asta que se arregle el orden del firebase
                        //TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.isNull());
                        TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.eq(tareaId));
                        TransaccionUtils.deleteTable(TareasRecursosC.class, TareasRecursosC_Table.tareaId.eq(tareaId));

                        transformarTarea(dataSnapshot, unidadAprendizajeId, orden);
                        callback.onLoad(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoad(false);
                    }
                });
    }

    private void saveTareaEvaluacion(String nodeFirebase , int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, TareaArchivoUi tareaArchivoUi,  StorageCallback<TareaArchivoUi> callbackStorage){

        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("Nombre", tareaArchivoUi.getNombre());
        hashMap.put("Path", tareaArchivoUi.getPath());
        hashMap.put("Repositorio", true);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Tarea_Evaluacion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/tarid_"+tareaId+"_pers_"+alumnoId+"/Archivos")
                .push();

      mDatabase.setValue(hashMap,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                        tareaAlumnoArchivos.setTareaAlumnoArchivoId(mDatabase.getKey());
                        tareaAlumnoArchivos.setTareaId(tareaId);
                        tareaAlumnoArchivos.setAlumnoId(alumnoId);
                        tareaAlumnoArchivos.setPath(tareaArchivoUi.getPath());
                        tareaAlumnoArchivos.setNombre(tareaArchivoUi.getNombre());
                        tareaAlumnoArchivos.setRepositorio(true);
                        tareaAlumnoArchivos.save();

                        tareaArchivoUi.setId(mDatabase.getKey());
                        if(databaseError!=null){
                            callbackStorage.onFinish(false, tareaArchivoUi);
                        }else {
                            callbackStorage.onFinish(true, tareaArchivoUi);
                        }
                    }
                });
    }


    private void deleteTareaEvaluacion(String nodeFirebase , int silaboEventoId, int unidadAprendizajeId, String tareaId, int alumnoId, TareaArchivoUi tareaArchivoUi, CallbackSimple callbackSimple){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Tarea_Evaluacion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/tarid_"+tareaId+"_pers_"+alumnoId+"/Archivos/"+tareaArchivoUi.getId());
        mDatabase
                .setValue(null,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            callbackSimple.onLoad(false);
                        }else {
                            callbackSimple.onLoad(true);
                        }
                    }
                });

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
    public FirebaseCancel updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId,CallbackTareaAlumno callbackTareaAlumno) {
        List<FirebaseCancel> firebaseCancelList = new ArrayList<>();
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

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        List<RetrofitCancel> retrofitCancelList = new ArrayList<>();
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getTareasAlumno(silaboEventoId, tipoPeriodoId));
        retrofitCancelList.add(retrofitCancel);
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                Where<TareasC> tareasCWhere = SQLite.select(TareasC_Table.tareaId.withTable())
                        .from(TareasC.class)
                        .innerJoin(UnidadAprendizaje.class)
                        .on(TareasC_Table.unidadAprendizajeId.withTable()
                                .eq(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()))
                        .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                        .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                                .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()))
                        .where(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(silaboEventoId))
                        .and(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable().eq(tipoPeriodoId));

                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.isNull());
                TransaccionUtils.deleteTable(TareasC.class, TareasC_Table.tareaId.in(tareasCWhere));
                TransaccionUtils.deleteTable(TareasRecursosC.class, TareasRecursosC_Table.tareaId.in(tareasCWhere));

                if (response == null) {

                } else {
                    for (Map.Entry<String, JSONFirebase> entry : JSONFirebase.d(response).getChildren2().entrySet()) {
                        JSONFirebase unidadSnapshot = (entry.getValue());
                        String key = entry.getKey();
                        String[] keys = key != null ? key.split("unid_") : new String[]{};
                        int unidadAprendizajeId = 0;
                        if (keys.length > 1) {
                            unidadAprendizajeId = Integer.parseInt(keys[1]);
                        }
                        int orden = 0;//para no perder el orden de la lista
                        for (JSONFirebase tareaSnapshot : unidadSnapshot.getChildren()) {
                            //FBTareaEvento fbTareaEvento = tareaSnapshot.getValue(FBTareaEvento.class);
                            transformarTarea(tareaSnapshot, unidadAprendizajeId, orden);
                            orden ++;

                        }

                    }
                }

                List<UnidadAprendizaje> unidadAprendizajeList = SQLite.select()
                        .from(UnidadAprendizaje.class)
                        .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                        .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                                .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()))
                        .where(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(silaboEventoId))
                        .and(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable().eq(tipoPeriodoId))
                        .queryList();


                SessionUser sessionUser = SessionUser.getCurrentUser();
                int alumnoId = sessionUser != null ? sessionUser.getPersonaId() : 0;

                SQLite.delete()
                        .from(TareaAlumnoArchivos.class)
                        .where(TareaAlumnoArchivos_Table.tareaId.in(tareasCWhere))
                        .and(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                        .execute();

                Webconfig webconfig = SQLite.select()
                        .from(Webconfig.class)
                        .where(Webconfig_Table.nombre.eq("wstr_UrlExpresiones"))
                        .querySingle();

                String pathValores = webconfig != null ? webconfig.getContent() : "";


                List<Integer> unidadAprendizajeIdList = new ArrayList<>();
                for (UnidadAprendizaje unidadAprendizaje : unidadAprendizajeList){
                    unidadAprendizajeIdList.add(unidadAprendizaje.getUnidadAprendizajeId());

                    firebaseCancelList.add(new FirebaseCancelImpl(mDatabase.child("/AV_Tarea_Evaluacion/silid_" + silaboEventoId + "/unid_" + unidadAprendizaje.getUnidadAprendizajeId())
                            .orderByChild("AlumnoId").equalTo(alumnoId), new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            TareaAlumno tareaAlumno = new TareaAlumno();
                            tareaAlumno.setAlumnoId(UtilsFirebase.convert(dataSnapshot.child("AlumnoId").getValue(),0));
                            tareaAlumno.setTareaId(UtilsFirebase.convert(dataSnapshot.child("TareaId").getValue(),""));
                            tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(),false));
                            tareaAlumno.setValorTipoNotaId(UtilsFirebase.convert(dataSnapshot.child("ValorTipoNotaId").getValue(), ""));
                            tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                            tareaAlumno.save();

                            TipoNotaC tipoNotaC = SQLite.select()
                                    .from(TipoNotaC.class)
                                    .innerJoin(ValorTipoNotaC.class)
                                    .on(TipoNotaC_Table.tipoNotaId.withTable()
                                            .eq(ValorTipoNotaC_Table.tipoNotaId.withTable()))
                                    .where(ValorTipoNotaC_Table.valorTipoNotaId.withTable()
                                            .eq(tareaAlumno.getValorTipoNotaId()))
                                    .querySingle();

                            ValorTipoNotaC valorTipoNotaC= SQLite.select()
                                    .from(ValorTipoNotaC.class)
                                    .where(ValorTipoNotaC_Table.valorTipoNotaId.eq(tareaAlumno.getValorTipoNotaId()))
                                    .querySingle();

                            int tipoId = tipoNotaC!=null?tipoNotaC.getTipoId():0;
                            String nota = "";
                            if(tipoId==TipoNotaC.SELECTOR_ICONOS){
                                nota = valorTipoNotaC!=null?pathValores + valorTipoNotaC.getIcono():"";
                            }else{
                                nota = valorTipoNotaC!=null?valorTipoNotaC.getTitulo():"";
                            }

                            /*SQLite.delete()
                                    .from(TareaAlumnoArchivos.class)
                                    .where(TareaAlumnoArchivos_Table.tareaId.eq(tareaAlumno.getTareaId()))
                                    .and(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                                    .execute();

                            if(dataSnapshot.child("Archivos").exists()){
                                for (DataSnapshot archivosSnapshot : dataSnapshot.child("Archivos").getChildren()){
                                    TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                                    tareaAlumnoArchivos.setTareaAlumnoArchivoId(archivosSnapshot.getKey());
                                    tareaAlumnoArchivos.setTareaId(tareaAlumno.getTareaId());
                                    tareaAlumnoArchivos.setAlumnoId(alumnoId);
                                    tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(),""));
                                    tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(),""));
                                    tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(),false));
                                    tareaAlumnoArchivos.save();
                                }
                            }*/

                            callbackTareaAlumno.onChangeTareaAlumno(tareaAlumno.getTareaId(), nota, tipoId);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            TareaAlumno tareaAlumno = new TareaAlumno();
                            tareaAlumno.setAlumnoId(UtilsFirebase.convert(dataSnapshot.child("AlumnoId").getValue(),0));
                            tareaAlumno.setTareaId(UtilsFirebase.convert(dataSnapshot.child("TareaId").getValue(),""));
                            tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(),false));
                            tareaAlumno.setValorTipoNotaId(UtilsFirebase.convert(dataSnapshot.child("ValorTipoNotaId").getValue(), ""));
                            tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                            tareaAlumno.save();

                            TipoNotaC tipoNotaC = SQLite.select()
                                    .from(TipoNotaC.class)
                                    .innerJoin(ValorTipoNotaC.class)
                                    .on(TipoNotaC_Table.tipoNotaId.withTable()
                                            .eq(ValorTipoNotaC_Table.tipoNotaId.withTable()))
                                    .where(ValorTipoNotaC_Table.valorTipoNotaId.withTable()
                                            .eq(tareaAlumno.getValorTipoNotaId()))
                                    .querySingle();

                            ValorTipoNotaC valorTipoNotaC= SQLite.select()
                                    .from(ValorTipoNotaC.class)
                                    .where(ValorTipoNotaC_Table.valorTipoNotaId.eq(tareaAlumno.getValorTipoNotaId()))
                                    .querySingle();

                            int tipoId = tipoNotaC!=null?tipoNotaC.getTipoId():0;
                            String nota = "";
                            if(tipoId==TipoNotaC.SELECTOR_ICONOS){
                                nota = valorTipoNotaC!=null? pathValores + valorTipoNotaC.getIcono():"";
                            }else{
                                nota = valorTipoNotaC!=null?valorTipoNotaC.getTitulo():"";
                            }



                            /*SQLite.delete()
                                    .from(TareaAlumnoArchivos.class)
                                    .where(TareaAlumnoArchivos_Table.tareaId.eq(tareaAlumno.getTareaId()))
                                    .and(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                                    .execute();

                            if(dataSnapshot.child("Archivos").exists()){
                                for (DataSnapshot archivosSnapshot : dataSnapshot.child("Archivos").getChildren()){
                                    TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                                    tareaAlumnoArchivos.setTareaAlumnoArchivoId(archivosSnapshot.getKey());
                                    tareaAlumnoArchivos.setTareaId(tareaAlumno.getTareaId());
                                    tareaAlumnoArchivos.setAlumnoId(alumnoId);
                                    tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(),""));
                                    tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(),""));
                                    tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(),false));
                                    tareaAlumnoArchivos.save();
                                }
                            }*/

                            callbackTareaAlumno.onChangeTareaAlumno(tareaAlumno.getTareaId(), nota, tipoId);
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }));
                }

                ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
                apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
                RetrofitCancel<List<JsonObject>> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getTareasAlumnoEvaluacion2(silaboEventoId, unidadAprendizajeIdList, alumnoId));
                retrofitCancelList.add(retrofitCancel);
                retrofitCancel.enqueue(new RetrofitCancel.Callback<List<JsonObject>>() {
                    @Override
                    public void onResponse(List<JsonObject> response) {

                        if(response == null){

                        }else {
                            List<TareasUI> tareasUIList = new ArrayList<>();
                            for (JsonObject jsonObject : response){
                                for (JSONFirebase dataSnapshot : JSONFirebase.d(jsonObject).getChildren()){
                                    TareaAlumno tareaAlumno = new TareaAlumno();
                                    tareaAlumno.setAlumnoId(UtilsFirebase.convert(dataSnapshot.child("AlumnoId").getValue(),0));
                                    tareaAlumno.setTareaId(UtilsFirebase.convert(dataSnapshot.child("TareaId").getValue(),""));
                                    tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(),false));
                                    tareaAlumno.setValorTipoNotaId(UtilsFirebase.convert(dataSnapshot.child("ValorTipoNotaId").getValue(), ""));
                                    tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                                    tareaAlumno.save();

                                    TipoNotaC tipoNotaC = SQLite.select()
                                            .from(TipoNotaC.class)
                                            .innerJoin(ValorTipoNotaC.class)
                                            .on(TipoNotaC_Table.tipoNotaId.withTable()
                                                    .eq(ValorTipoNotaC_Table.tipoNotaId.withTable()))
                                            .where(ValorTipoNotaC_Table.valorTipoNotaId.withTable()
                                                    .eq(tareaAlumno.getValorTipoNotaId()))
                                            .querySingle();

                                    ValorTipoNotaC valorTipoNotaC= SQLite.select()
                                            .from(ValorTipoNotaC.class)
                                            .where(ValorTipoNotaC_Table.valorTipoNotaId.eq(tareaAlumno.getValorTipoNotaId()))
                                            .querySingle();

                                    int tipoId = tipoNotaC!=null?tipoNotaC.getTipoId():0;
                                    String nota = "";
                                    if(tipoId==TipoNotaC.SELECTOR_ICONOS){
                                        nota = valorTipoNotaC!=null?pathValores + valorTipoNotaC.getIcono():"";
                                    }else{
                                        nota = valorTipoNotaC!=null?valorTipoNotaC.getTitulo():"";
                                    }

                                    SQLite.delete()
                                            .from(TareaAlumnoArchivos.class)
                                            .where(TareaAlumnoArchivos_Table.tareaId.eq(tareaAlumno.getTareaId()))
                                            .and(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                                            .execute();

                                    if(dataSnapshot.child("Archivos").exists()){
                                        for (Map.Entry<String,JSONFirebase> entry : dataSnapshot.child("Archivos").getChildren2().entrySet()){
                                            JSONFirebase archivosSnapshot = entry.getValue();
                                            TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                                            tareaAlumnoArchivos.setTareaAlumnoArchivoId(entry.getKey());
                                            tareaAlumnoArchivos.setTareaId(tareaAlumno.getTareaId());
                                            tareaAlumnoArchivos.setAlumnoId(alumnoId);
                                            tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(),""));
                                            tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(),""));
                                            tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(),false));
                                            tareaAlumnoArchivos.save();
                                        }
                                    }

                                    TareasUI tareasUI = new TareasUI();
                                    tareasUI.setKeyTarea(tareaAlumno.getTareaId());
                                    tareasUI.setNota(nota);
                                    tareasUI.setTipoNotaId(tipoId);
                                    tareasUIList.add(tareasUI);
                                }
                            }
                            callbackTareaAlumno.onChangeTareaAlumno(tareasUIList);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });




                callbackTareaAlumno.onLoad(true);

            }

            @Override
            public void onFailure(Throwable t) {
                callbackTareaAlumno.onLoad(false);
            }
        });

        return () -> {
            for(RetrofitCancel cancel : retrofitCancelList)cancel.cancel();
            for (FirebaseCancel firebaseCancel : firebaseCancelList)firebaseCancel.cancel();
        };

    }

    private void transformarTarea(DataSnapshot tareaSnapshot, int unidadAprendizajeId, int orden) {
        TareasC tareasC = new TareasC();
        tareasC.setKey(UtilsFirebase.convert(tareaSnapshot.child("TareaId").getValue(),""));
        tareasC.setTareaId(UtilsFirebase.convert(tareaSnapshot.child("TareaId").getValue(),""));
        tareasC.setEstadoId(TareasC.ESTADO_PUBLICADO);
        tareasC.setHoraEntrega(UtilsFirebase.convert(tareaSnapshot.child("HoraEntrega").getValue(),""));
        //tareasC.setNumero(UtilsFirebase.convert(tareaSnapshot.child("Numero").getValue(),0));
        tareasC.setNumero(orden);
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date todayCalendar = simpleDateFormat1.parse(UtilsFirebase.convert(tareaSnapshot.child("FechaEntrega").getValue(),""));
            tareasC.setFechaEntrega(todayCalendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        tareasC.setInstrucciones(UtilsFirebase.convert(tareaSnapshot.child("Instrucciones").getValue(),""));
        tareasC.setTitulo(UtilsFirebase.convert(tareaSnapshot.child("Titulo").getValue(),""));
        tareasC.setUnidadAprendizajeId(unidadAprendizajeId);
        tareasC.setSesionAprendizajeId(UtilsFirebase.convert(tareaSnapshot.child("SesionAprendizajeId").getValue(),0));
        tareasC.save();

        if(tareaSnapshot.child("RecursosTarea").exists()){
            for (DataSnapshot recursoSnapshot : tareaSnapshot.child("RecursosTarea").getChildren()){
                getRecursosFirebase(tareasC.getTareaId(), recursoSnapshot);
            }
        }
    }

    private void transformarTarea(JSONFirebase tareaSnapshot, int unidadAprendizajeId, int orden) {
        TareasC tareasC = new TareasC();
        tareasC.setKey(UtilsFirebase.convert(tareaSnapshot.child("TareaId").getValue(),""));
        tareasC.setTareaId(UtilsFirebase.convert(tareaSnapshot.child("TareaId").getValue(),""));
        tareasC.setEstadoId(TareasC.ESTADO_PUBLICADO);
        tareasC.setHoraEntrega(UtilsFirebase.convert(tareaSnapshot.child("HoraEntrega").getValue(),""));
        //tareasC.setNumero(UtilsFirebase.convert(tareaSnapshot.child("Numero").getValue(),0));
        tareasC.setNumero(orden);
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Date todayCalendar = simpleDateFormat1.parse(UtilsFirebase.convert(tareaSnapshot.child("FechaEntrega").getValue(),""));
            tareasC.setFechaEntrega(todayCalendar.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        tareasC.setInstrucciones(UtilsFirebase.convert(tareaSnapshot.child("Instrucciones").getValue(),""));
        tareasC.setTitulo(UtilsFirebase.convert(tareaSnapshot.child("Titulo").getValue(),""));
        tareasC.setUnidadAprendizajeId(unidadAprendizajeId);
        tareasC.setSesionAprendizajeId(UtilsFirebase.convert(tareaSnapshot.child("SesionAprendizajeId").getValue(),0));
        tareasC.save();

        if(tareaSnapshot.child("RecursosTarea").exists()){
            for (JSONFirebase recursoSnapshot : tareaSnapshot.child("RecursosTarea").getChildren()){
                getRecursosFirebase(tareasC.getTareaId(), recursoSnapshot);
            }
        }
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

    @Override
    public void updateFirebaseTareaAlumno(String tareaId, CallbackSimple callbackSimple) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();

        int unidadAprendizajeId = tareasC!=null?tareasC.getUnidadAprendizajeId():0;

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                .querySingle();
        int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getTareaAlumnoEvaluacion(silaboEventoId, unidadAprendizajeId, tareaId, alumnoId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {

                SQLite.delete()
                        .from(TareaAlumnoArchivos.class)
                        .where(TareaAlumnoArchivos_Table.tareaId.eq(tareaId))
                        .and(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                        .execute();

                if(response==null){

                }else {
                    JSONFirebase dataSnapshot = JSONFirebase.d(response);

                    TareaAlumno tareaAlumno = new TareaAlumno();
                    tareaAlumno.setAlumnoId(alumnoId);
                    tareaAlumno.setTareaId(tareaId);
                    tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(),false));
                    tareaAlumno.setValorTipoNotaId(UtilsFirebase.convert(dataSnapshot.child("ValorTipoNotaId").getValue(), ""));
                    tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                    tareaAlumno.save();

                    if(dataSnapshot.child("Archivos").exists()){
                        for (Map.Entry<String,JSONFirebase> entry : dataSnapshot.child("Archivos").getChildren2().entrySet()){
                            JSONFirebase archivosSnapshot = entry.getValue();
                            TareaAlumnoArchivos tareaAlumnoArchivos = new TareaAlumnoArchivos();
                            tareaAlumnoArchivos.setTareaAlumnoArchivoId(entry.getKey());
                            tareaAlumnoArchivos.setTareaId(tareaId);
                            tareaAlumnoArchivos.setAlumnoId(alumnoId);
                            tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(),""));
                            tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(),""));
                            tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(),false));
                            tareaAlumnoArchivos.save();
                        }
                    }
                }

                callbackSimple.onLoad(true);

            }

            @Override
            public void onFailure(Throwable t) {
                callbackSimple.onLoad(false);
            }
        });
    }

    @Override
    public TareasUI updateEvaluacion(String tareaId) {
        return null;
    }


    private void getRecursosFirebase(String TareaId, DataSnapshot dataSnapshot){

        RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
        recursoDidacticoEventoC.setRecursoDidacticoId(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
        recursoDidacticoEventoC.setTitulo(UtilsFirebase.convert(dataSnapshot.child("Titulo").getValue(),""));
        recursoDidacticoEventoC.setKey(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
        recursoDidacticoEventoC.setTipoId(UtilsFirebase.convert(dataSnapshot.child("TipoId").getValue(),0));
        recursoDidacticoEventoC.setDescripcion(UtilsFirebase.convert(dataSnapshot.child("Descripcion").getValue(),""));
        recursoDidacticoEventoC.setUrl(UtilsFirebase.convert(dataSnapshot.child("Url").getValue(),""));
        recursoDidacticoEventoC.setEstado(1);
        recursoDidacticoEventoC.save();

        TareasRecursosC tareasRecursosC = new TareasRecursosC();
        tareasRecursosC.setTareaId(TareaId);
        tareasRecursosC.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
        tareasRecursosC.save();

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
                    .where(Archivo_Table.archivoId.eq(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),"")))
                    .querySingle();

            if(archivo==null)archivo = new Archivo();
            archivo.setKey(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            archivo.setArchivoId(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            archivo.setNombre("");
            archivo.setPath(UtilsFirebase.convert(dataSnapshot.child("DriveId").getValue(),""));
            archivo.save();

            RecursoArchivo recursoArchivo = new RecursoArchivo();
            recursoArchivo.setArchivoId(archivo.getKey());
            recursoArchivo.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
            recursoArchivo.save();

        }
    }

    private void getRecursosFirebase(String TareaId, JSONFirebase dataSnapshot){

        RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
        recursoDidacticoEventoC.setRecursoDidacticoId(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
        recursoDidacticoEventoC.setTitulo(UtilsFirebase.convert(dataSnapshot.child("Titulo").getValue(),""));
        recursoDidacticoEventoC.setKey(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
        recursoDidacticoEventoC.setTipoId(UtilsFirebase.convert(dataSnapshot.child("TipoId").getValue(),0));
        recursoDidacticoEventoC.setDescripcion(UtilsFirebase.convert(dataSnapshot.child("Descripcion").getValue(),""));
        recursoDidacticoEventoC.setUrl(UtilsFirebase.convert(dataSnapshot.child("Url").getValue(),""));
        recursoDidacticoEventoC.setEstado(1);
        recursoDidacticoEventoC.save();

        TareasRecursosC tareasRecursosC = new TareasRecursosC();
        tareasRecursosC.setTareaId(TareaId);
        tareasRecursosC.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
        tareasRecursosC.save();

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
                    .where(Archivo_Table.archivoId.eq(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),"")))
                    .querySingle();

            if(archivo==null)archivo = new Archivo();
            archivo.setKey(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            archivo.setArchivoId(UtilsFirebase.convert(dataSnapshot.child("RecursoDidacticoId").getValue(),""));
            archivo.setNombre("");
            archivo.setPath(UtilsFirebase.convert(dataSnapshot.child("DriveId").getValue(),""));
            archivo.save();

            RecursoArchivo recursoArchivo = new RecursoArchivo();
            recursoArchivo.setArchivoId(archivo.getKey());
            recursoArchivo.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
            recursoArchivo.save();

        }
    }

}
