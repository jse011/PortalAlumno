package com.consultoraestrategia.ss_portalalumno.instrumento.useCase;

import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;

public class GetInstrumento {
    private InstrumentoRepository repository;

    public GetInstrumento(InstrumentoRepository repository) {
        this.repository = repository;
    }

    public InstrumentoUi execut(int instrumentoEvalId){
        return repository.getInstrumento(instrumentoEvalId);
    }
}
