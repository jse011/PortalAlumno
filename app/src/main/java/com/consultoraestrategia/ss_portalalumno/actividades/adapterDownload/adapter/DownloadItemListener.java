package com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter;

import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;

public interface DownloadItemListener {

    void onClickDownload(RecursosUi repositorioFileUi);

    void onClickClose(RecursosUi repositorioFileUi);

    void onClickArchivo(RecursosUi repositorioFileUi);
}
