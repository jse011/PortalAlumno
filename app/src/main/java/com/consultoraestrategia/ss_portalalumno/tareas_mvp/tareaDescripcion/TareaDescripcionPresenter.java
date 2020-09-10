package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import android.net.Uri;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

import java.util.ArrayList;

public interface TareaDescripcionPresenter extends BaseFragmentPresenter<TareasDecripcionView> {

    void onClickDownload(RepositorioFileUi repositorioFileUi);

    void onClickClose(RepositorioFileUi repositorioFileUi);

    void onClickArchivo(RepositorioFileUi repositorioFileUi);

    void onResultCamara(ArrayList<String> pathFiles);

    void onClickCamera();

    void onClickActionTareaArchivo(TareaArchivoUi tareaArchivoUi);

    void onClickRemoveTareaArchivo(TareaArchivoUi tareaArchivoUi);

    void onBtnEntregarClicked();

    void onClickOpenTareaArchivo(TareaArchivoUi tareaArchivoUi);

    void onClickMsgError();

    void onClickAddFile();

    void onResultDoc(Uri uri, String nombre);

    void onClickAddLink(String descripcion, String vinculo);
}
