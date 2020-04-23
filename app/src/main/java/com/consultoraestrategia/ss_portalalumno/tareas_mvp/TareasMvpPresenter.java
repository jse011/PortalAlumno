package com.consultoraestrategia.ss_portalalumno.tareas_mvp;

import android.os.Bundle;

import com.consultoraestrategia.ss_portalalumno.base.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public interface TareasMvpPresenter extends BasePresenter<TareasMvpView> {
    void setExtras(Bundle extras);

    void deleteTarea(TareasUI tareasUI);

    void onResumeFragment();

    void onClickedCrearTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int mIdCurso);

    void onClickedOpTareaEdit(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);


    void onActualizarTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onChangeEstado(TareasUI tareasUI);

    void onCrearRubro(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onCrearRubrica(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onClikRubroTarea(TareasUI tareasUI);
    void actualizarTareasPeriodo(String idCalendarioPeriodo);

    void onClickDownload(RepositorioFileUi repositorioFileUi);

    void onClickClose(RepositorioFileUi repositorioFileUi);

    void onClickArchivo(RepositorioFileUi repositorioFileUi);

    void onClickParentFabClicked();

    void onDestroyView();

    void onClickTarea(TareasUI tareasUI);

    void notifyChangeFragment();

}
