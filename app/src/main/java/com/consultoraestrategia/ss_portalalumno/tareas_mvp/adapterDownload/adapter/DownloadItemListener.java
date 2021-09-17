package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;

public interface DownloadItemListener {

    void onClickDownload(RepositorioFileUi repositorioFileUi);

    void onClickClose(RepositorioFileUi repositorioFileUi);

    void onClickArchivo(RepositorioFileUi repositorioFileUi);

    void onClickOpenLinkArchivo(RepositorioFileUi repositorioFileUi, String clickedLink);
}
