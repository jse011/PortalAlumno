package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;

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
}
