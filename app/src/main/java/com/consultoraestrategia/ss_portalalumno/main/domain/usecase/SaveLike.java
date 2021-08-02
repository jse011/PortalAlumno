package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;


import com.consultoraestrategia.ss_portalalumno.entities.Evento;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;

public class SaveLike {
    MainRepositorio repository;

    public SaveLike(MainRepositorio repository) {
        this.repository = repository;
    }

    public void execute(EventoUi eventosUi){
        repository.saveLikeRealTime(eventosUi);
        repository.saveLike(eventosUi);
    }
}
