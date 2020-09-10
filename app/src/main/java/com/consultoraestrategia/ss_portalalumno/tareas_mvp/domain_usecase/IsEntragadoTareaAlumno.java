package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

public class IsEntragadoTareaAlumno {
    private TareasMvpRepository tareasMvpRepository;

    public IsEntragadoTareaAlumno(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public TareasUI execute(String tareaId){
        return tareasMvpRepository.isEntregadoTareaAlumno(tareaId);
    }
}
