package com.consultoraestrategia.ss_portalalumno.previewDrive;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

public interface PreviewArchivoView extends BaseView<PreviewArchivoPresenter> {

    void openBrowser(String idDrive);

    void showButons();

    void hideButons();

    void showDowloadProgress();

    void hideDowloadProgress();

    void showDownloadComplete();

    void dowloadArchivo(String idDrive, String archivoPreview);

    void hideDownloadComplete();

    void openDownloadFile(long downloadID);

    void showYoutube(String youtube);

    void showMultimendia();

    void showDocument();
}
