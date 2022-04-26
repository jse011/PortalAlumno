package com.consultoraestrategia.ss_portalalumno.evidencia.data.source;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.entities.SesionAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAlumnoArchivos;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAlumnoArchivos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.EvidenciaSesionUi;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancelImpl;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.util.DriveUrlParser;
import com.consultoraestrategia.ss_portalalumno.util.JSONFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EvidenciaRepositoryImpl implements EvidenciaRepository {

    @Override
    public void updateEvidenciaSesion(int cargaCursoId, int sesionAprendizajeId, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig != null ? webconfig.getContent() : "sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();
        int silaboEventoId = silaboEvento != null ? silaboEvento.getSilaboEventoId() : 0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeId = sesionAprendizaje != null ? sesionAprendizaje.getUnidadAprendizajeId() : 0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser != null ? sessionUser.getPersonaId() : 0;

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10, 15, 15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getEvidenciaAlumno(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                SQLite.delete()
                        .from(SesionAlumnoArchivos.class)
                        .where(SesionAlumnoArchivos_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                        .and(SesionAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                        .execute();


                if (response != null) {
                    JSONFirebase dataSnapshot = JSONFirebase.d(response);
                    SesionAlumno tareaAlumno = new SesionAlumno();
                    tareaAlumno.setAlumnoId(alumnoId);
                    tareaAlumno.setSesionAprendizajeId(sesionAprendizajeId);
                    tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(), false));
                    tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                    tareaAlumno.save();

                    if (dataSnapshot.child("Adjuntos").exists()) {
                        for (Map.Entry<String, JSONFirebase> map : dataSnapshot.child("Adjuntos").getChildren2().entrySet()) {
                            JSONFirebase archivosSnapshot = map.getValue();
                            SesionAlumnoArchivos tareaAlumnoArchivos = new SesionAlumnoArchivos();
                            tareaAlumnoArchivos.setSesionAlumnoArchivoId(map.getKey());
                            tareaAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                            tareaAlumnoArchivos.setAlumnoId(alumnoId);
                            tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(), ""));
                            tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(), ""));
                            tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(), false));
                            tareaAlumnoArchivos.save();
                        }
                    }
                }

                callback.onLoad(true);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
            }
        });

/*
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        callback.onLoad(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoad(false);
                    }
                });*/


    }

    @Override
    public List<ArchivoSesEvidenciaUi> getArchivoSesEvidencias(int sesionAprendiajeId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser != null ? sessionUser.getPersonaId() : 0;
        SesionAlumno sesionAlumno = SQLite.select()
                .from(SesionAlumno.class)
                .where(SesionAlumno_Table.sesionAprendizajeId.eq(sesionAprendiajeId))
                .and(TareaAlumno_Table.alumnoId.eq(alumnoId))
                .querySingle();

        boolean entregado = sesionAlumno != null && sesionAlumno.isEntregado();
        List<SesionAlumnoArchivos> sesionAlumnoArchivosList = SQLite.select()
                .from(SesionAlumnoArchivos.class)
                .where(SesionAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                .and(SesionAlumnoArchivos_Table.sesionAprendizajeId.eq(sesionAprendiajeId))
                .queryList();
        List<ArchivoSesEvidenciaUi> archivoSesEvidenciaUiList = new ArrayList<>();
        for (SesionAlumnoArchivos sesionAlumnoArchivos : sesionAlumnoArchivosList) {
            ArchivoSesEvidenciaUi archivoSesEvidenciaUi = new ArchivoSesEvidenciaUi();
            archivoSesEvidenciaUi.setId(sesionAlumnoArchivos.getSesionAlumnoArchivoId());
            archivoSesEvidenciaUi.setNombre(sesionAlumnoArchivos.getNombre());
            archivoSesEvidenciaUi.setPath(!TextUtils.isEmpty(sesionAlumnoArchivos.getPath()) ? sesionAlumnoArchivos.getPath() : "");
            archivoSesEvidenciaUi.setEntregado(entregado);
            if (sesionAlumnoArchivos.isRepositorio()) {
                archivoSesEvidenciaUi.setTipo(TareaArchivoUi.getType(archivoSesEvidenciaUi.getNombre()));
                archivoSesEvidenciaUi.setDescripcion(archivoSesEvidenciaUi.getTipo().getNombre());
            } else {
                String idYoutube = YouTubeUrlParser.getVideoId(archivoSesEvidenciaUi.getPath());
                String idDrive = DriveUrlParser.getDocumentId(archivoSesEvidenciaUi.getPath());
                if (!TextUtils.isEmpty(idYoutube)) {
                    archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.YOUTUBE);
                } else if (!TextUtils.isEmpty(idDrive)) {
                    archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.DRIVE);
                } else {
                    archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.LINK);
                }

                archivoSesEvidenciaUi.setDescripcion(archivoSesEvidenciaUi.getPath());
            }

            archivoSesEvidenciaUiList.add(archivoSesEvidenciaUi);
        }
        return archivoSesEvidenciaUiList;
    }

    @Override
    public EvidenciaSesionUi isEntregado(int sesionAprendizajeId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser != null ? sessionUser.getPersonaId() : 0;
        SesionAlumno sesionAlumno = SQLite.select()
                .from(SesionAlumno.class)
                .where(SesionAlumno_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .and(TareaAlumno_Table.alumnoId.eq(personaId))
                .querySingle();

        EvidenciaSesionUi evidenciaSesionUi = new EvidenciaSesionUi();
        evidenciaSesionUi.setEntregaAlumno(sesionAlumno != null && sesionAlumno.isEntregado());
        evidenciaSesionUi.setRetrasoEntrega(false);
        // evidenciaSesionUi.setFechaEntregaAlumno(tareaAlumno!=null?tareaAlumno.getFechaEntrega():0);
        // tareasUI.setRetrasoEntrega(comparaFechasTareaEntregada(fechaEntrega, horaEntrega, tareasUI.getFechaEntregaAlumno()));

        return evidenciaSesionUi;
    }

    @Override
    public void entregarSesEvidencia(int cargaCursoId, int sesionAprendizajeId, boolean forzarConexion, Callback callback) {


        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();
        int silaboEventoId = silaboEvento != null ? silaboEvento.getSilaboEventoId() : 0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeId = sesionAprendizaje != null ? sesionAprendizaje.getUnidadAprendizajeId() : 0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser != null ? sessionUser.getPersonaId() : 0;

        SesionAlumno sesionAlumno = SQLite.select()
                .from(SesionAlumno.class)
                .where(SesionAlumno_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .and(TareaAlumno_Table.alumnoId.eq(alumnoId))
                .querySingle();

        boolean entregado = !(sesionAlumno != null && sesionAlumno.isEntregado());

        if (forzarConexion) {
            entregarEvidenciaServidor(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, entregado, sesionAlumno, callback);
        } else {
            entregarEvidenciaFirebase(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, entregado, sesionAlumno, callback);
        }

    }

    void entregarEvidenciaServidor(int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, boolean entregado, SesionAlumno sesionAlumno, Callback callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10, 15, 15, TimeUnit.SECONDS);
        RetrofitCancelImpl<Object> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.entregarEvidenciaAlumno(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, entregado));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<Object>() {
            @Override
            public void onResponse(Object response) {
                if (response == null) {
                    callback.onLoad(false);
                    ;
                } else {
                    try {
                        long fechaEntrega = UtilsFirebase.convert(response, 0L);
                        if (sesionAlumno != null) {
                            sesionAlumno.setEntregado(entregado);
                            sesionAlumno.setFechaEntrega(fechaEntrega);
                            sesionAlumno.save();
                        } else {
                            SesionAlumno newSesionAlumno = new SesionAlumno();
                            newSesionAlumno.setAlumnoId(alumnoId);
                            newSesionAlumno.setSesionAprendizajeId(sesionAprendizajeId);
                            newSesionAlumno.setEntregado(entregado);
                            newSesionAlumno.setFechaEntrega(fechaEntrega);
                            newSesionAlumno.save();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callback.onLoad(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
            }
        });
    }

    void entregarEvidenciaFirebase(int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, boolean entregado, SesionAlumno sesionAlumno, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig != null ? webconfig.getContent() : "sinServer";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/" + nodeFirebase)
                .child("/AV_Evidencias_Sesion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/sesid_" + sesionAprendizajeId + "_pers_" + alumnoId);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/FechaEntrega", ServerValue.TIMESTAMP);
        childUpdates.put("/Entregado", entregado);
        childUpdates.put("/AlumnoId", alumnoId);
        childUpdates.put("/SesionId", sesionAprendizajeId);
        mDatabase.updateChildren(childUpdates, (databaseError, databaseReference) -> {
            if (databaseError == null) {
                mDatabase.child("FechaEntrega")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    long fechaEntrega = UtilsFirebase.convert(dataSnapshot.getValue(), 0L);
                                    if (sesionAlumno != null) {
                                        sesionAlumno.setEntregado(entregado);
                                        sesionAlumno.setFechaEntrega(fechaEntrega);
                                        sesionAlumno.save();
                                    } else {
                                        SesionAlumno newSesionAlumno = new SesionAlumno();
                                        newSesionAlumno.setAlumnoId(alumnoId);
                                        newSesionAlumno.setSesionAprendizajeId(sesionAprendizajeId);
                                        newSesionAlumno.setEntregado(entregado);
                                        newSesionAlumno.setFechaEntrega(fechaEntrega);
                                        newSesionAlumno.save();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                callback.onLoad(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                callback.onLoad(false);
                            }
                        });
            } else {
                callback.onLoad(false);
            }
        });
    }

    @Override
    public StorageCancel uploadStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, boolean forzarConexion, StorageCallback<ArchivoSesEvidenciaUi> callbackStorage) {


        StorageCancel storageCancel = null;

        Uri uri = null;
        File file = null;
        if (archivoSesEvidenciaUi.getFile() instanceof File) {
            file = (File) archivoSesEvidenciaUi.getFile();
        } else if (archivoSesEvidenciaUi.getFile() instanceof Uri) {
            uri = (Uri) archivoSesEvidenciaUi.getFile();
        }
        if (file != null || uri != null || !TextUtils.isEmpty(archivoSesEvidenciaUi.getNombre())) {
            if (file != null) {
                uri = Uri.fromFile(file);
            }

            int validate = 0;
            try {
                InputStream inputStream = FirebaseStorage.getInstance().getApp().getApplicationContext().getContentResolver().openInputStream(uri);
                int length = (inputStream != null ? inputStream.available() : 0) / (1024 * 1024);
                if (length <= 40) {
                    validate = 1;
                }

            } catch (IOException e) {
                e.printStackTrace();
                validate = -1;
            }

            if (validate == 0) {
                callbackStorage.onErrorMaxSize();
                //callbackStorage.onFinish(false, tareaArchivoUi);
            } else if (validate == -1) {
                callbackStorage.onFinish(false, archivoSesEvidenciaUi);
            } else {


                SilaboEvento silaboEvento = SQLite.select()
                        .from(SilaboEvento.class)
                        .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                        .querySingle();
                int silaboEventoId = silaboEvento != null ? silaboEvento.getSilaboEventoId() : 0;

                SesionAprendizaje sesionAprendizaje = SQLite.select()
                        .from(SesionAprendizaje.class)
                        .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                        .querySingle();

                int unidadAprendizajeId = sesionAprendizaje != null ? sesionAprendizaje.getUnidadAprendizajeId() : 0;

                SessionUser sessionUser = SessionUser.getCurrentUser();
                int alumnoId = sessionUser != null ? sessionUser.getPersonaId() : 0;

                if (forzarConexion) {
                    storageCancel = subirArchivosAlServidor(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, archivoSesEvidenciaUi, uri, callbackStorage);
                } else {
                    storageCancel = subirArchivosAlFirebase(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, archivoSesEvidenciaUi, uri, callbackStorage);
                }

            }
        } else {
            callbackStorage.onFinish(false, archivoSesEvidenciaUi);
        }
        // File or Blob
        return storageCancel;

    }


    StorageCancel subirArchivosAlServidor(int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Uri uri,  StorageCallback<ArchivoSesEvidenciaUi> callbackStorage){

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
                size = -1;
            } catch (IOException checkSizeError) {
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
                apiRetrofit.changeSetTime(30,30,30, TimeUnit.MINUTES);
                RetrofitCancelImpl<String> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.uploadFileEvidenciaAlumno(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, inputStream, archivoSesEvidenciaUi.getNombre()));
                retrofitCancel.enqueue(new RetrofitCancel.Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response==null){
                            archivoSesEvidenciaUi.setState(0);
                            callbackStorage.onChange(archivoSesEvidenciaUi);
                            callbackStorage.onFinish(false,archivoSesEvidenciaUi);
                        }else {
                            archivoSesEvidenciaUi.setState(0);
                            archivoSesEvidenciaUi.setFile(null);
                            archivoSesEvidenciaUi.setProgress(0);
                            archivoSesEvidenciaUi.setPath(response);


                            SesionAlumnoArchivos sesionAlumnoArchivos = new SesionAlumnoArchivos();
                            sesionAlumnoArchivos.setSesionAlumnoArchivoId(response);
                            sesionAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                            sesionAlumnoArchivos.setAlumnoId(alumnoId);
                            sesionAlumnoArchivos.setPath(archivoSesEvidenciaUi.getPath());
                            sesionAlumnoArchivos.setNombre(archivoSesEvidenciaUi.getNombre());
                            sesionAlumnoArchivos.setRepositorio(true);
                            sesionAlumnoArchivos.save();

                            archivoSesEvidenciaUi.setId(response);

                            callbackStorage.onFinish(true, archivoSesEvidenciaUi);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        archivoSesEvidenciaUi.setState(0);
                        callbackStorage.onChange(archivoSesEvidenciaUi);
                        callbackStorage.onFinish(false,archivoSesEvidenciaUi);
                    }
                });
                retrofitCancelList.add(retrofitCancel);

            }else{
                archivoSesEvidenciaUi.setState(0);
                callbackStorage.onChange(archivoSesEvidenciaUi);
                callbackStorage.onFinish(false,archivoSesEvidenciaUi);
            }



        } catch (FileNotFoundException e) {

            callbackStorage.onFinish(false,archivoSesEvidenciaUi);
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


    StorageCancel subirArchivosAlFirebase(int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Uri uri,  StorageCallback<ArchivoSesEvidenciaUi> callbackStorage){
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Evidencias_Sesion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/sesid_" + sesionAprendizajeId + "/pers_" + alumnoId+"/"+archivoSesEvidenciaUi.getNombre());

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType(UtilsStorage.getMimeType(archivoSesEvidenciaUi.getNombre()))
                .setContentDisposition("attachment")
                .build();
        //gs://messenger-academico.appspot.com/ups/AV_Evidencias_Sesion/silid_2830/unid_16633/sesid_258063/pers_1949

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
                archivoSesEvidenciaUi.setProgress(progress);
                archivoSesEvidenciaUi.setState(1);
                callbackStorage.onChange(archivoSesEvidenciaUi);
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                archivoSesEvidenciaUi.setState(0);
                callbackStorage.onChange(archivoSesEvidenciaUi);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                // Handle unsuccessful uploads
                archivoSesEvidenciaUi.setState(0);
                callbackStorage.onChange(archivoSesEvidenciaUi);
                callbackStorage.onFinish(false,archivoSesEvidenciaUi);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                archivoSesEvidenciaUi.setState(0);
                archivoSesEvidenciaUi.setFile(null);
                archivoSesEvidenciaUi.setProgress(0);
                archivoSesEvidenciaUi.setPath(storageReference.getPath());
                saveSesionArchivoEvidencia(nodeFirebase, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, archivoSesEvidenciaUi, callbackStorage);
            }
        });

        return  storageCancel;
    }
    @Override
    public void uploadLinkFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, boolean forzarConexion, Callback callback) {


        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();
        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeId = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

        if(forzarConexion){
            ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
            apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
            RetrofitCancelImpl<String> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.uploadLinkEvidenciaAlumno(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, archivoSesEvidenciaUi.getNombre(), archivoSesEvidenciaUi.getPath()));
            retrofitCancel.enqueue(new RetrofitCancel.Callback<String>() {
                @Override
                public void onResponse(String response) {
                    if(response!=null){
                        SesionAlumnoArchivos sesionAlumnoArchivos = new SesionAlumnoArchivos();
                        sesionAlumnoArchivos.setSesionAlumnoArchivoId(response);
                        sesionAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                        sesionAlumnoArchivos.setAlumnoId(alumnoId);
                        sesionAlumnoArchivos.setPath(archivoSesEvidenciaUi.getPath());
                        sesionAlumnoArchivos.setNombre(archivoSesEvidenciaUi.getNombre());
                        sesionAlumnoArchivos.setRepositorio(false);
                        sesionAlumnoArchivos.save();

                        archivoSesEvidenciaUi.setId(response);
                        callback.onLoad(true);
                    }else {
                        callback.onLoad(false);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    callback.onLoad(false);
                }
            });
        }else {
            Webconfig webconfig = SQLite.select()
                    .from(Webconfig.class)
                    .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                    .querySingle();
            String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

            HashMap<String, Object> hashMap =  new HashMap<>();
            hashMap.put("Nombre", archivoSesEvidenciaUi.getNombre());
            hashMap.put("Path", archivoSesEvidenciaUi.getDescripcion());
            hashMap.put("Repositorio", false);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                    .child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId+"/Adjuntos")
                    .push();

            mDatabase.setValue(hashMap,new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    SesionAlumnoArchivos sesionAlumnoArchivos = new SesionAlumnoArchivos();
                    sesionAlumnoArchivos.setSesionAlumnoArchivoId(mDatabase.getKey());
                    sesionAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                    sesionAlumnoArchivos.setAlumnoId(alumnoId);
                    sesionAlumnoArchivos.setPath(archivoSesEvidenciaUi.getPath());
                    sesionAlumnoArchivos.setNombre(archivoSesEvidenciaUi.getNombre());
                    sesionAlumnoArchivos.setRepositorio(false);
                    sesionAlumnoArchivos.save();

                    archivoSesEvidenciaUi.setId(mDatabase.getKey());
                    if(databaseError!=null){
                        callback.onLoad(false);
                    }else {
                        callback.onLoad(true);
                    }
                }
            });
        }



    }

    @Override
    public void deleteStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, boolean forzarConexion, Callback callback) {


        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();
        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeId = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

        if(forzarConexion){
            deleteStoragServidor(archivoSesEvidenciaUi, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, callback);
        }else {
            deleteStoragFirebase(archivoSesEvidenciaUi, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, callback);
        }


    }

    private void  deleteStoragServidor(ArchivoSesEvidenciaUi archivoSesEvidenciaUi, int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, Callback callback){
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10, 15, 15, TimeUnit.SECONDS);
        RetrofitCancelImpl<Boolean> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.deleteFileEvidenciaAlumno(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, archivoSesEvidenciaUi.getId()));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<Boolean>() {
            @Override
            public void onResponse(Boolean response) {
                if(response==null){
                    callback.onLoad(false);
                }else {
                    callback.onLoad(true);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
            }
        });

    }

    private void  deleteStoragFirebase(ArchivoSesEvidenciaUi archivoSesEvidenciaUi, int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, Callback callback){
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        if(archivoSesEvidenciaUi.getTipo()!= TareaArchivoUi.Tipo.LINK&&archivoSesEvidenciaUi.getTipo()!= TareaArchivoUi.Tipo.YOUTUBE&archivoSesEvidenciaUi.getTipo()!= TareaArchivoUi.Tipo.DRIVE){
            //gs://messenger-academico.appspot.com/ups/AV_Evidencias_Sesion/silid_2830/unid_16633/sesid_258063/pers_1949
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                    .child("/AV_Evidencias_Sesion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/sesid_" + sesionAprendizajeId + "/pers_" + alumnoId + "/" + archivoSesEvidenciaUi.getNombre());

            // Delete the file
            storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    deleteSesionArchivoEvidencia(nodeFirebase, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId,alumnoId, archivoSesEvidenciaUi, callback);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Uh-oh, an error occurred!
                    exception.printStackTrace();
                    deleteSesionArchivoEvidencia(nodeFirebase, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId,alumnoId, archivoSesEvidenciaUi, callback);
                }
            });
        }else {
            deleteSesionArchivoEvidencia(nodeFirebase, silaboEventoId, unidadAprendizajeId, sesionAprendizajeId,alumnoId, archivoSesEvidenciaUi, callback);
        }
    }

    private void deleteSesionArchivoEvidencia(String nodeFirebase, int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId+"/Adjuntos/"+archivoSesEvidenciaUi.getId());
        mDatabase
                .setValue(null,new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError!=null){
                            callback.onLoad(false);
                        }else {
                            callback.onLoad(true);
                        }
                    }
                });
    }

    private void saveSesionArchivoEvidencia(String nodeFirebase , int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, StorageCallback<ArchivoSesEvidenciaUi> callbackStorage){

        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("Nombre", archivoSesEvidenciaUi.getNombre());
        hashMap.put("Path", archivoSesEvidenciaUi.getPath());
        hashMap.put("Repositorio", true);
        //"/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId+"/Adjuntos")
                .push();

        mDatabase.setValue(hashMap,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                SesionAlumnoArchivos sesionAlumnoArchivos = new SesionAlumnoArchivos();
                sesionAlumnoArchivos.setSesionAlumnoArchivoId(mDatabase.getKey());
                sesionAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                sesionAlumnoArchivos.setAlumnoId(alumnoId);
                sesionAlumnoArchivos.setPath(archivoSesEvidenciaUi.getPath());
                sesionAlumnoArchivos.setNombre(archivoSesEvidenciaUi.getNombre());
                sesionAlumnoArchivos.setRepositorio(true);
                sesionAlumnoArchivos.save();

                archivoSesEvidenciaUi.setId(mDatabase.getKey());
                if(databaseError!=null){
                    callbackStorage.onFinish(false, archivoSesEvidenciaUi);
                }else {
                    callbackStorage.onFinish(true, archivoSesEvidenciaUi);
                }
            }
        });
    }

}
