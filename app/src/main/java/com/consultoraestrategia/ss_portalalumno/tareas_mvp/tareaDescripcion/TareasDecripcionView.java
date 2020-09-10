package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

import java.util.ArrayList;
import java.util.List;

public interface TareasDecripcionView extends BaseView<TareaDescripcionPresenter>{

    void setNombreTarea(String nombre);

    void setDescripcionTarea(String descripicion);

    void setFechaTarea(String horaEntrega);

    void showListRecursos(List<RecursosUI> recursosUIS);

    void setUpdateProgress(RepositorioFileUi repositorioFileUi, int count);

    void setUpdate(RepositorioFileUi repositorioFileUi);

    void leerArchivo(String path);

    void showVinculo(String url);

    void showYoutube(String url);

    void changeTema(String color1, String color2, String color3);

    void showListaTareaAlumno(List<TareaArchivoUi> tareasUIList);

    void openCamera(ArrayList<String> pathFiles);

    void addTareaArchivo(TareaArchivoUi tareaArchivoUi);

    void remove(TareaArchivoUi tareaArchivoUi);

    void update(TareaArchivoUi tareaArchivoUi);

    void changeFechaEntrega(boolean entregaAlumno, boolean retrasoEntrega);

    void diabledButtons();

    void showMessageTop(String message);

    void showPreviewArchivo();

    void modoOnline();

    void modoOffline();

    void showMultimediaPlayer();

    void showProgress2();

    void hideProgress2();

    void onShowPickDoc();
}
