package com.consultoraestrategia.ss_portalalumno.evidencia.data.source;

import android.net.Uri;
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
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.util.DriveUrlParser;
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
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvidenciaRepositoryImpl implements EvidenciaRepository {

    @Override
    public void updateEvidenciaSesion(int cargaCursoId, int sesionAprendizajeId, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

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

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        SQLite.delete()
                                .from(SesionAlumnoArchivos.class)
                                .where(SesionAlumnoArchivos_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                .and(SesionAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                                .execute();

                        SesionAlumno tareaAlumno = new SesionAlumno();
                        tareaAlumno.setAlumnoId(alumnoId);
                        tareaAlumno.setSesionAprendizajeId(sesionAprendizajeId);
                        tareaAlumno.setEntregado(UtilsFirebase.convert(dataSnapshot.child("Entregado").getValue(),false));
                        tareaAlumno.setFechaEntrega(UtilsFirebase.convert(dataSnapshot.child("FechaEntrega").getValue(), 0L));
                        tareaAlumno.save();

                        if(dataSnapshot.child("Adjuntos").exists()){
                            for (DataSnapshot archivosSnapshot : dataSnapshot.child("Adjuntos").getChildren()){
                                SesionAlumnoArchivos tareaAlumnoArchivos = new SesionAlumnoArchivos();
                                tareaAlumnoArchivos.setSesionAlumnoArchivoId(archivosSnapshot.getKey());
                                tareaAlumnoArchivos.setSesionAprendizajeId(sesionAprendizajeId);
                                tareaAlumnoArchivos.setAlumnoId(alumnoId);
                                tareaAlumnoArchivos.setNombre(UtilsFirebase.convert(archivosSnapshot.child("Nombre").getValue(),""));
                                tareaAlumnoArchivos.setPath(UtilsFirebase.convert(archivosSnapshot.child("Path").getValue(),""));
                                tareaAlumnoArchivos.setRepositorio(UtilsFirebase.convert(archivosSnapshot.child("Repositorio").getValue(),false));
                                tareaAlumnoArchivos.save();
                            }
                        }

                        callback.onLoad(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoad(false);
                    }
                });


    }

    @Override
    public List<ArchivoSesEvidenciaUi> getArchivoSesEvidencias(int sesionAprendiajeId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;
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
        for (SesionAlumnoArchivos sesionAlumnoArchivos : sesionAlumnoArchivosList){
            ArchivoSesEvidenciaUi archivoSesEvidenciaUi = new ArchivoSesEvidenciaUi();
            archivoSesEvidenciaUi.setId(sesionAlumnoArchivos.getSesionAlumnoArchivoId());
            archivoSesEvidenciaUi.setNombre(sesionAlumnoArchivos.getNombre());
            archivoSesEvidenciaUi.setPath(!TextUtils.isEmpty(sesionAlumnoArchivos.getPath())?sesionAlumnoArchivos.getPath():"");
            archivoSesEvidenciaUi.setEntregado(entregado);
            if(sesionAlumnoArchivos.isRepositorio()){
                archivoSesEvidenciaUi.setTipo(TareaArchivoUi.getType(archivoSesEvidenciaUi.getPath()));
                archivoSesEvidenciaUi.setDescripcion(archivoSesEvidenciaUi.getTipo().getNombre());
            }else {
                String idYoutube = YouTubeUrlParser.getVideoId(archivoSesEvidenciaUi.getPath());
                String idDrive = DriveUrlParser.getDocumentId(archivoSesEvidenciaUi.getPath());
                if(!TextUtils.isEmpty(idYoutube)){
                    archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.YOUTUBE);
                }else if(!TextUtils.isEmpty(idDrive)){
                    archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.DRIVE);
                }else {
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
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
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
    public void entregarSesEvidencia(int cargaCursoId, int sesionAprendizajeId, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

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

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase)
                .child("/AV_Evidencias_Sesion/silid_"+silaboEventoId+"/unid_"+unidadAprendizajeId+"/sesid_"+sesionAprendizajeId+"_pers_"+alumnoId);
        SesionAlumno sesionAlumno = SQLite.select()
                .from(SesionAlumno.class)
                .where(SesionAlumno_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .and(TareaAlumno_Table.alumnoId.eq(alumnoId))
                .querySingle();

        boolean entregado = !(sesionAlumno != null && sesionAlumno.isEntregado());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/FechaEntrega", ServerValue.TIMESTAMP);
        childUpdates.put("/Entregado", entregado);
        childUpdates.put("/AlumnoId",alumnoId);
        childUpdates.put("/SesionId",sesionAprendizajeId);
        mDatabase.updateChildren(childUpdates, (databaseError, databaseReference) -> {
            if(databaseError==null){
                mDatabase.child("FechaEntrega")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                try {
                                    long fechaEntrega = UtilsFirebase.convert(dataSnapshot.getValue(),0L);
                                    if(sesionAlumno!=null){
                                        sesionAlumno.setEntregado(entregado);
                                        sesionAlumno.setFechaEntrega(fechaEntrega);
                                        sesionAlumno.save();
                                    }else {
                                        SesionAlumno newSesionAlumno = new SesionAlumno();
                                        newSesionAlumno.setAlumnoId(alumnoId);
                                        newSesionAlumno.setSesionAprendizajeId(sesionAprendizajeId);
                                        newSesionAlumno.setEntregado(entregado);
                                        newSesionAlumno.setFechaEntrega(fechaEntrega);
                                        newSesionAlumno.save();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                callback.onLoad(true);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                callback.onLoad(false);
                            }
                        });
            }else {
                callback.onLoad(false);
            }
        });
    }

    @Override
    public StorageCancel uploadStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, StorageCallback<ArchivoSesEvidenciaUi> callbackStorage) {


        StorageCancelImpl storageCancel = null;

        Uri uri = null;
        File file = null;
        if(archivoSesEvidenciaUi.getFile() instanceof File){
            file = (File)archivoSesEvidenciaUi.getFile();
        }else if(archivoSesEvidenciaUi.getFile() instanceof Uri){
            uri = (Uri)archivoSesEvidenciaUi.getFile();
        }
        if(file!=null||uri!=null||!TextUtils.isEmpty(archivoSesEvidenciaUi.getNombre())){
            if(file!=null){
                uri = Uri.fromFile(file);
            }

            int validate = 0;
            try {
                InputStream inputStream = FirebaseStorage.getInstance().getApp().getApplicationContext().getContentResolver().openInputStream(uri);
                int length = (inputStream!=null?inputStream.available():0)/(1024*1024);
                if(length<=40){
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
                callbackStorage.onFinish(false, archivoSesEvidenciaUi);
            }else {
                Webconfig webconfig = SQLite.select()
                        .from(Webconfig.class)
                        .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                        .querySingle();
                String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

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


                // Create the file metadata
                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType(UtilsStorage.getMimeType(archivoSesEvidenciaUi.getNombre()))
                        .setContentDisposition("attachment")
                        .build();
                //gs://messenger-academico.appspot.com/ups/AV_Evidencias_Sesion/silid_2830/unid_16633/sesid_258063/pers_1949
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                        .child("/AV_Evidencias_Sesion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/sesid_" + sesionAprendizajeId + "/pers_" + alumnoId+"/"+archivoSesEvidenciaUi.getNombre());

                // Upload file and metadata to the path 'images/mountains.jpg'
                storageCancel = new StorageCancelImpl(storageReference.putFile(uri, metadata));

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
            }
        }else {
            callbackStorage.onFinish(false, archivoSesEvidenciaUi);
        }
        // File or Blob
        return storageCancel;

    }

    @Override
    public void uploadLinkFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

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

    @Override
    public void deleteStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

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
