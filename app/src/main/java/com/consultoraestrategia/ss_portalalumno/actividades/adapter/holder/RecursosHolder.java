package com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder;

import android.view.View;

import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.holder.DownloadHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;

/**
 * Created by SCIEV on 20/08/2017.
 */

public class RecursosHolder extends DownloadHolder {


    public RecursosHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bind(RecursosUi repositorioFileUi, DownloadItemListener repositorioItemListener) {
        super.bind(repositorioFileUi, repositorioItemListener);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void count(int count) {
        super.count(count);
    }

    @Override
    public RecursosUi getRepositorioFileUi() {
        return super.getRepositorioFileUi();
    }


}
