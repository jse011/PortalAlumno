package com.consultoraestrategia.ss_portalalumno.tareas_mvp;

import com.consultoraestrategia.ss_portalalumno.base.BaseView;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RubroEvalProcesoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;
import java.util.Map;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public interface TareasMvpView extends BaseView<TareasMvpPresenter> {

    void showTareasUIList(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList, int idCurso, ParametroDisenioUi parametroDisenioUi);

    void hideTareasUIList();

    void showProgress();

    void hideProgress();

    void showActivityCrearTareas(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idUsuario, int idSilaboEvento, int mSesionAprendizajeId, int mIdCurso, String colorCurso, int programaEducativoId);

    void showlActivityEditTareas(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idUsuario, int idSilaboEvento, int mSesionAprendizajeId, int mIdCurso, String color, int programaEducativoId);

    void exportarTareasEliminadas(int programaEducativoId);


   void showMessage();
   void hideMessage();


    void onShowActualizarTareas();

    void showServiceExportTarea(int programaEducativoId);

    void showActivityCrearRubro(TareasUI tareasUI, int idSilaboEvento, int idCalendarioPeriodo, int programaEducativoId, int mSesionAprendizajeId, String colorCurso, int idCurso);

    void showActivityCrearRubrica(TareasUI tareasUI, int idCalendarioPeriodo, int idCargaCurso, int idCurso, int programaEducativoId, int mSesionAprendizajeId, String colorCurso);

    void showEvaluacionRubricaIndividual(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, String colorCurso);

    void showEvaluacionRubricaGrupal(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, String colorCurso);

    void showEvaluacionRubroGrupal(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, int mSesionAprendizajeId, int idCurso, String colorCurso);

    void showEvaluacionRubroIndividual(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, int mSesionAprendizajeId, int idCurso, String colorCurso);

    void onParentFabClicked();

    void setUpdateProgress(RepositorioFileUi repositorioFileUi, int count);

    void setUpdate(RepositorioFileUi repositorioFileUi);

    void leerArchivo(String path);

    void showVinculo(String url);

    void showYoutube(String url);

    void showCrearTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int idCargaCurso);

    void showTareaDescripcionActivity();

    void updateTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, TareasUI tareasUI);

    void updateTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI,  List<TareasUI> tareasUIModificadaList);

    void updateItem(HeaderTareasAprendizajeUI unidadAprendizajeUi);

}
