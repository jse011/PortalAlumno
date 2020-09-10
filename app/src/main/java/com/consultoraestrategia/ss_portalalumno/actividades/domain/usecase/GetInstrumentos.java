package com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.InstrumentoUi;

import java.util.List;

public class GetInstrumentos {
    private ActividadesRepository repository;

    public GetInstrumentos(ActividadesRepository repository) {
        this.repository = repository;
    }

    public List<InstrumentoUi> execute(int sesionAprendizajeId){
        return repository.getInstrumentos(sesionAprendizajeId);
    }
}
