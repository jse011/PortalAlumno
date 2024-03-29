package com.consultoraestrategia.ss_portalalumno.previewDrive.data.source;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.DriveArchivoLocal;
import com.consultoraestrategia.ss_portalalumno.entities.DriveArchivoLocal_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDrive;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PreviewDriveRepositoyImpl implements PreviewDriveRepository {
    private static final  String TAG = "PreviewDriveRepositoy";
    private ApiRetrofit apiRetrofit;

    public PreviewDriveRepositoyImpl(ApiRetrofit apiRetrofit) {
        this.apiRetrofit = apiRetrofit;
    }

    @Override
    public RetrofitCancel getIdDrive(String tareaId, String nombreArchivo, Callback<DriveUi> driveUiCallback) {
        List<RetrofitCancel> retrofitCancels = new ArrayList<>();

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


        retrofitCancels.add(getUrlDrive(tareaId, alumnoId, nombreArchivo, "", new Callback<DriveUi>() {
            @Override
            public void onLoad(boolean success, DriveUi item) {

                if(!success || item==null || TextUtils.isEmpty(item.getIdDrive())){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                            .child("/AV_Tarea_Evaluacion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/tarid_" + tareaId + "/pers_" + alumnoId + "/" + nombreArchivo);

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'

                            retrofitCancels.add(getUrlDrive(tareaId, alumnoId, nombreArchivo, uri.toString(), driveUiCallback));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            driveUiCallback.onLoad(false, null);
                        }
                    });
                }else {

                    driveUiCallback.onLoad(true, item);
                }

            }
        }));



        return new RetrofitCancel() {
            @Override
            public void enqueue(Callback callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {
                for (RetrofitCancel retrofitCancel : retrofitCancels)retrofitCancel.cancel();
                retrofitCancels.clear();
            }

            @Override
            public boolean isCanceled() {
                return false;
            }
        };
    }

    RetrofitCancel getUrlDrive(String tareaId, int alumnoId, String nombreArchivo, String path,  Callback<DriveUi> driveUiCallback){
        Call<RestApiResponse<BEDrive>> responseCall = apiRetrofit.f_SynckTareaAlumDrive(tareaId, alumnoId, nombreArchivo, path);

        RetrofitCancel<BEDrive> retrofitCancel = new RetrofitCancelImpl<>(responseCall);

        retrofitCancel.enqueue(new RetrofitCancel.Callback<BEDrive>() {
            @Override
            public void onResponse(final BEDrive response) {
                if(response == null){
                    Log.d(TAG,"response calendarioPeriodo null");
                }else {
                    Log.d(TAG,"response calendarioPeriodo true");
                    DriveUi driveUi = new DriveUi();
                    driveUi.setIdDrive(response.getIdDrive());
                    driveUi.setThumbnail(response.getThumbnail());
                    driveUi.setMsgError(response.getMsgError());
                    driveUi.setUrl(response.getUrl());

                    driveUiCallback.onLoad(true, driveUi);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"response calendarioPeriodo Transaction Failure");
                driveUiCallback.onLoad(false, null);
            }
        });

        return  retrofitCancel;
    }

    @Override
    public RetrofitCancel getIdDriveEvidencia(int sesionAprendizajeId, String nombreArchivo, Callback<DriveUi> driveUiCallback) {
        List<RetrofitCancel> retrofitCancels = new ArrayList<>();

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeId = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        UnidadAprendizaje unidadAprendizaje = SQLite.select()
                .from(UnidadAprendizaje.class)
                .where(UnidadAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizajeId))
                .querySingle();
        int silaboEventoId =  unidadAprendizaje!=null?unidadAprendizaje.getSilaboEventoId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;



        getUrlDriveEvidencias(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, nombreArchivo, "", new Callback<DriveUi>() {
            @Override
            public void onLoad(boolean success, DriveUi item) {
                if(!success || item==null || TextUtils.isEmpty(item.getIdDrive())){
                    // //gs://messenger-academico.appspot.com/ups/AV_Evidencias_Sesion/silid_2830/unid_16633/sesid_258063/pers_1949
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("/"+nodeFirebase)
                            .child("/AV_Evidencias_Sesion/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeId + "/sesid_" + sesionAprendizajeId + "/pers_" + alumnoId + "/" + nombreArchivo);

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            retrofitCancels.add(  getUrlDriveEvidencias(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, nombreArchivo,uri.toString(), driveUiCallback));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            driveUiCallback.onLoad(false, null);
                        }
                    });
                }else {
                    driveUiCallback.onLoad(true, item);
                }
            }
        });


        return new RetrofitCancel() {
            @Override
            public void enqueue(Callback callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {
                for (RetrofitCancel retrofitCancel : retrofitCancels)retrofitCancel.cancel();
                retrofitCancels.clear();
            }

            @Override
            public boolean isCanceled() {
                return false;
            }
        };
    }


    RetrofitCancel getUrlDriveEvidencias(int silaboEventoId, int unidadAprendizajeId, int sesionAprendizajeId, int alumnoId, String nombre, String path,  Callback<DriveUi> driveUiCallback){
        Call<RestApiResponse<BEDrive>> responseCall = apiRetrofit.f_SynckEviSesDrive(silaboEventoId, unidadAprendizajeId, sesionAprendizajeId, alumnoId, nombre, path);

        RetrofitCancel<BEDrive> retrofitCancel = new RetrofitCancelImpl<>(responseCall);

        retrofitCancel.enqueue(new RetrofitCancel.Callback<BEDrive>() {
            @Override
            public void onResponse(final BEDrive response) {
                if(response == null){
                    Log.d(TAG,"response calendarioPeriodo null");
                }else {
                    Log.d(TAG,"response calendarioPeriodo true");
                    DriveUi driveUi = new DriveUi();
                    driveUi.setIdDrive(response.getIdDrive());
                    driveUi.setThumbnail(response.getThumbnail());
                    driveUi.setMsgError(response.getMsgError());
                    driveUi.setUrl(response.getUrl());

                    driveUiCallback.onLoad(true, driveUi);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"response calendarioPeriodo Transaction Failure");
                driveUiCallback.onLoad(false, null);
            }
        });

        return  retrofitCancel;
    }

    @Override
    public DriveUi getIdDriveTemporal(String id, String nombreArchivo) {
        DriveArchivoLocal driveTareaArchivo = SQLite.select()
                .from(DriveArchivoLocal.class)
                .where(DriveArchivoLocal_Table.nombreArchivo.eq(nombreArchivo))
                .and(DriveArchivoLocal_Table.driveArchivoLocalId.eq(id))
                .querySingle();

        DriveUi driveUi = new DriveUi();

        if (driveTareaArchivo!=null){
            driveUi.setIdDrive(driveTareaArchivo.getDriveId());
            driveUi.setNombreArchivoLocal(driveTareaArchivo.getNombreLocal());
        }

        return driveUi;
    }

    @Override
    public void saveIdDriveTemporal(String id, String nombreArchivo, String nombreAchivoLocal, long idDownload, String driveId) {
        DriveArchivoLocal driveTareaArchivo = new DriveArchivoLocal();
        driveTareaArchivo.setNombreArchivo(nombreArchivo);
        driveTareaArchivo.setDriveArchivoLocalId(id);
        driveTareaArchivo.setDriveId(driveId);
        driveTareaArchivo.setNombreLocal(nombreAchivoLocal);
        driveTareaArchivo.setIdDownload(idDownload);
        driveTareaArchivo.save();

    }

    @Override
    public DriveUi getIdDriveTemporal(String driveId) {
        DriveArchivoLocal driveTareaArchivo = SQLite.select()
                .from(DriveArchivoLocal.class)
                .where(DriveArchivoLocal_Table.driveId.eq(driveId))
                .querySingle();

        DriveUi driveUi = new DriveUi();

        if (driveTareaArchivo!=null){
            driveUi.setIdDrive(driveTareaArchivo.getDriveId());
            driveUi.setNombreArchivoLocal(driveTareaArchivo.getNombreLocal());
        }

        return driveUi;
    }
}
