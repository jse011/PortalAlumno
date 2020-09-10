package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

import java.util.List;

public class GetArchivoTareaAlumno {
    private TareasMvpRepository repository;

    public GetArchivoTareaAlumno(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public List<TareaArchivoUi> execute(String tareaId){
        return repository.getArchivoTareaAlumno(tareaId);
    }
}
