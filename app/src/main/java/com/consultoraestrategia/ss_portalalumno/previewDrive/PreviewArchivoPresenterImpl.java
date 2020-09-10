package com.consultoraestrategia.ss_portalalumno.previewDrive;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveEvidencia;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveTarea;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreviewView;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;

public class PreviewArchivoPresenterImpl extends BasePresenterImpl<PreviewArchivoView> implements PreviewArchivoPresenter {


    private String tareaId;
    private String archivoPreview;
    private GetIdDriveTarea getIdDrive;
    private GetIdDriveEvidencia getIdDriveEvidencia;
    private DriveUi driveUiSelected;
    private long downloadID;
    private boolean dowloadProgress;
    private ArchivoPreviewView archivoPreviewView;
    private MultimediaPreviewView multimediaPreviewView;
    private RetrofitCancel cancelIdDrive;
    private Online online;
    private String driveId;
    private int sesionAprendizajeId;
    private String youtube;
    private int tipo;

    public PreviewArchivoPresenterImpl(UseCaseHandler handler, Resources res, GetIdDriveTarea getIdDrive,
                                       GetIdDriveEvidencia getIdDriveEvidencia, Online online) {
        super(handler, res);
        this.getIdDrive = getIdDrive;
        this.getIdDriveEvidencia = getIdDriveEvidencia;
        this.online = online;
    }

    @Override
    protected String getTag() {
        return "PreviewArchivoPresenterTAG";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbPreview gbPreview = iCRMEdu.variblesGlobales.getGbPreview();
        if(gbPreview!=null){
            this.tareaId = gbPreview.getTareaId();
            this.sesionAprendizajeId = gbPreview.getSesionAprendizajeId();
            this.archivoPreview = gbPreview.getNombreArchivoPreview();
            this.driveId = gbPreview.getDriveId();
            this.youtube = gbPreview.getYoutube();
            this.tipo = gbPreview.getTipo();
        }
    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(!TextUtils.isEmpty(youtube)){
            if(view!=null)view.hideButons();
            if(view!=null)view.showYoutube(youtube);
        }else {
            if(tipo == GbPreview.MULTIMEDIA){
                if(view!=null)view.showMultimendia();
            }else {
                if(view!=null)view.showDocument();
            }
        }
    }

    @Override
    public void onProgressChanged(int progress) {
            if (progress < 100) {
            if(archivoPreviewView!=null)archivoPreviewView.showProgress();
        }
        if (progress == 100) {
            if(archivoPreviewView!=null)archivoPreviewView.hideProgress();
        }
    }

    @Override
    public void onClickBtnDownload() {
        if(driveUiSelected!=null){
            if(!dowloadProgress){
                if(view!=null)view.hideDowloadProgress();
                if(view!=null)view.hideDownloadComplete();
                if(view!=null)view.dowloadArchivo(driveUiSelected.getIdDrive(), archivoPreview);
            }else {
                if(view!=null)view.showMessage("descarga en progreso");
            }
        }
    }

    @Override
    public void onClickBtnOpen() {
        if(driveUiSelected!=null){
            if(view!=null)view.openBrowser(driveUiSelected.getIdDrive());
        }

    }

    @Override
    public void onStarDownload(long downloadID) {
        dowloadProgress = true;
        this.downloadID = downloadID;
        if(view!=null)view.showDowloadProgress();
        if(view!=null)view.hideDownloadComplete();
    }

    @Override
    public void finishedDownload(long id) {
        if (downloadID == id) {
            dowloadProgress = false;
            if(view!=null)view.hideDowloadProgress();
            if(view!=null)view.showDownloadComplete();
        }
    }

    @Override
    public void canceledDownload(long id) {
        if (downloadID == id) {
            dowloadProgress = false;
            if(view!=null)view.hideDowloadProgress();
            if(view!=null)view.hideDownloadComplete();
        }
    }

    @Override
    public void onClickMsgSucess() {
        if(view!=null)view.openDownloadFile(downloadID);
    }

    @Override
    public void attachView(ArchivoPreviewView archivoPreviewView) {
        this.archivoPreviewView = archivoPreviewView;
        archivoPreviewView.showProgress();
        if(view!=null)view.hideButons();
        dowloadProgress=false;
        if(cancelIdDrive!=null)cancelIdDrive.cancel();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    if(TextUtils.isEmpty(driveId)){
                        if(!TextUtils.isEmpty(tareaId)){
                            cancelIdDrive = getIdDrive.execute(tareaId, archivoPreview, new GetIdDriveTarea.Callback() {
                                @Override
                                public void onLoad(boolean success, DriveUi driveUi) {
                                    if(success){
                                        driveUiSelected = driveUi;
                                        if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null)
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.uploadArchivo(driveUi.getIdDrive());
                                        if(view!=null)view.showButons();
                                    }else {
                                        if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null){
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.hideProgress();
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.showBtnReload();
                                        }

                                    }
                                }
                            });
                        }else {
                            cancelIdDrive = getIdDriveEvidencia.execute(sesionAprendizajeId, archivoPreview, new GetIdDriveEvidencia.Callback() {
                                @Override
                                public void onLoad(boolean success, DriveUi driveUi) {
                                    if(success){
                                        driveUiSelected = driveUi;
                                        if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null)
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.uploadArchivo(driveUi.getIdDrive());
                                        if(view!=null)view.showButons();
                                    }else {
                                        if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null){
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.hideProgress();
                                            PreviewArchivoPresenterImpl.this.archivoPreviewView.showBtnReload();
                                        }

                                    }
                                }
                            });

                        }

                    }else {
                        driveUiSelected = new DriveUi();
                        driveUiSelected.setIdDrive(driveId);
                        if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null)
                            PreviewArchivoPresenterImpl.this.archivoPreviewView.uploadArchivo(driveUiSelected.getIdDrive());
                        if(view!=null)view.showButons();
                    }
                }else {
                    if(PreviewArchivoPresenterImpl.this.archivoPreviewView!=null){
                        PreviewArchivoPresenterImpl.this.archivoPreviewView.hideProgress();
                        PreviewArchivoPresenterImpl.this.archivoPreviewView.showBtnReload();
                    }
                    if(view!=null)view.showMessage("sin conexión");
                }

            }
        });

    }

    @Override
    public void onDestroyArchivoPreviewView() {
        archivoPreviewView =null;
    }

    @Override
    public void onDestroyMultimediaPreviewView() {
        multimediaPreviewView = null;
    }

    @Override
    public void attachView(MultimediaPreviewView multimediaPreviewView) {
        this.multimediaPreviewView = multimediaPreviewView;
        multimediaPreviewView.showProgress();
        if(view!=null)view.hideButons();
        dowloadProgress=false;
        if(cancelIdDrive!=null)cancelIdDrive.cancel();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    if(TextUtils.isEmpty(driveId)){
                        if(!TextUtils.isEmpty(tareaId)){
                            cancelIdDrive = getIdDrive.execute(tareaId, archivoPreview, new GetIdDriveTarea.Callback() {
                                @Override
                                public void onLoad(boolean success, DriveUi driveUi) {
                                    if(success){
                                        driveUiSelected = driveUi;
                                        uploadPreview();
                                        if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null)
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                                        if(view!=null)view.showButons();
                                    }else {
                                        if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null){
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.showBtnReload();
                                        }

                                    }
                                }
                            });
                        }else {
                            cancelIdDrive = getIdDriveEvidencia.execute(sesionAprendizajeId, archivoPreview, new GetIdDriveEvidencia.Callback() {
                                @Override
                                public void onLoad(boolean success, DriveUi driveUi) {
                                    if(success){
                                        driveUiSelected = driveUi;
                                        uploadPreview();
                                        if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null)
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                                        if(view!=null)view.showButons();
                                    }else {
                                        if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null){
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.showBtnReload();
                                        }

                                    }
                                }
                            });
                        }

                    }else {
                        driveUiSelected = new DriveUi();
                        driveUiSelected.setIdDrive(driveId);
                        uploadPreview();
                        if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null)
                            PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                        if(view!=null)view.showButons();
                    }

                }else{
                    if(PreviewArchivoPresenterImpl.this.multimediaPreviewView!=null){
                        PreviewArchivoPresenterImpl.this.multimediaPreviewView.hideProgress();
                        PreviewArchivoPresenterImpl.this.multimediaPreviewView.showBtnReload();
                    }
                    if(view!=null)view.showMessage("sin conexión");
                }

            }
        });

    }

    private void uploadPreview() {
        String extencion = UtilsStorage.getExtencion(archivoPreview);
        if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp3") || extencion.toLowerCase().contains("ogg") || extencion.toLowerCase().contains("wav"))) {
            if(multimediaPreviewView!=null)multimediaPreviewView.uploadAudio(driveUiSelected.getIdDrive());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp4"))) {
            if(multimediaPreviewView!=null)multimediaPreviewView.uploadMp4(driveUiSelected.getIdDrive());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("flv"))) {
            if(multimediaPreviewView!=null)multimediaPreviewView.uploadFlv(driveUiSelected.getIdDrive());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mkv"))) {
            if(multimediaPreviewView!=null)multimediaPreviewView.uploadMkv(driveUiSelected.getIdDrive());
        }else {
            if(multimediaPreviewView!=null)multimediaPreviewView.uploadArchivo(driveUiSelected.getIdDrive());
        }
    }

    @Override
    public void initializePlayer() {
        if(driveUiSelected!=null){
            if(multimediaPreviewView!=null)multimediaPreviewView.resumePreview(driveUiSelected.getIdDrive());
        }

    }

    @Override
    public void onCancelPlayer() {

    }

    @Override
    public void onClickReload() {
        if(driveUiSelected!=null){
            uploadPreview();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(cancelIdDrive!=null)cancelIdDrive.cancel();
    }
}
