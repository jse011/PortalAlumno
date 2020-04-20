package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;

public interface TareaDescripcionPresenter extends BaseFragmentPresenter<TareasDecripcionView> {

    void onClickDownload(RepositorioFileUi repositorioFileUi);

    void onClickClose(RepositorioFileUi repositorioFileUi);

    void onClickArchivo(RepositorioFileUi repositorioFileUi);
}
