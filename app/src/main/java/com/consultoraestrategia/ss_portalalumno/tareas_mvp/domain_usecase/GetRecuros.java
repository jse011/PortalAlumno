package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;

import java.util.List;

public class GetRecuros {
    private TareasMvpRepository tareasMvpRepository;

    public GetRecuros(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public List<RecursosUI> execute(String tareaId){
        return tareasMvpRepository.getRecursosTarea(tareaId);
    }
}
