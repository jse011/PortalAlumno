package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks;


import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;

import java.util.List;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public interface GetTareasListCallback {
    void onTareasListLoaded(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList);

    void onError(String error);



}
