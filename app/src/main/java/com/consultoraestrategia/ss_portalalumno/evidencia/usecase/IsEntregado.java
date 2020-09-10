package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;

import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.EvidenciaSesionUi;

public class IsEntregado {
    EvidenciaRepository repository;

    public IsEntregado(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public EvidenciaSesionUi execute(int sesionAprendizajeId){
        return repository.isEntregado(sesionAprendizajeId);
    }
}
