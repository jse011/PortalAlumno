package com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia;

import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoPresenter;

public interface MultimediaPreviewView {
    void uploadArchivo(String idDrive);

    void showProgress();

    void hideProgress();

    void setPresenter(PreviewArchivoPresenter presenter);

    void resumePreview(String idDrive);

    boolean onBackPressed();

    void uploadAudio(String idDrive);

    void uploadMp4(String idDrive);

    void uploadFlv(String idDrive);

    void uploadMkv(String idDrive);

    void showBtnReload();

    void dimensionRatio(int a, int b);
}
