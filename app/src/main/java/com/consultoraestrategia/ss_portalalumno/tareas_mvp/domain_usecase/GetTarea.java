package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

public class GetTarea {
    private TareasMvpRepository tareasMvpRepository;

    public GetTarea(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public TareasUI execute(String tareaId){
        return tareasMvpRepository.getTarea(tareaId);
    }
}
