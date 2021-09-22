package com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

/**
 * Created by irvinmarin on 24/11/2017.
 */

public interface TareasUIListener  {
    void onOpTareaEditClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onOpTareaDelteClicked(TareasUI tareasUI);

    void onOpNotificarTareasClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onClikEstado(TareasUI tareasUI);

    void onOpCrearRubricaClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onOpCrearRubroClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);

    void onClikRubroTarea(TareasUI tareasUI);

    void onClicTarea(TareasUI tareasUI);

    void onClickUnidadAprendizaje(HeaderTareasAprendizajeUI unidadAprendizaje);
}
