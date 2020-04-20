package com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners;

import android.view.View;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;

/**
 * Created by irvinmarin on 24/11/2017.
 */

public interface UnidadAprendizajeListener {
    void onBtnCrearTareaClicked(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int mIdCurso);
    void onReloadAdapter(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, HeaderTareasAprendizajeUI nuevaHeaderTareasAprendizajeUI, int opcionAccion);
    void onClickOpcion(View view, HeaderTareasAprendizajeUI headerTareasAprendizajeUI);
}
