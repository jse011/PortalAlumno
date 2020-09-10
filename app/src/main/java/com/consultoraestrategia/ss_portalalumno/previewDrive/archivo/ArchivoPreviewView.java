package com.consultoraestrategia.ss_portalalumno.previewDrive.archivo;

import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoPresenter;

public interface ArchivoPreviewView {
    void uploadArchivo(String idDrive);

    void showProgress();

    void hideProgress();

    void setPresenter(PreviewArchivoPresenter presenter);

    void showBtnReload();

}
