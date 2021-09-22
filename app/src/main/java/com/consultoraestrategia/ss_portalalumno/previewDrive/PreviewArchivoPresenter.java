package com.consultoraestrategia.ss_portalalumno.previewDrive;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreviewView;

public interface PreviewArchivoPresenter extends BasePresenter<PreviewArchivoView> {
    void onProgressChanged(int progress);

    void onClickBtnDownload();

    void onClickBtnOpen();

    void onStarDownload(long downloadID, String archivoPreview);

    void finishedDownload(long id);

    void canceledDownload(long id);

    void onClickMsgSucess();

    void attachView(ArchivoPreviewView archivoPreviewView);

    void onDestroyArchivoPreviewView();

    void onDestroyMultimediaPreviewView();

    void attachView(MultimediaPreviewView multimediaPreviewView);

    void initializePlayer();

    void onCancelPlayer();

    void onClickReload();
}
