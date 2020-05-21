package com.consultoraestrategia.ss_portalalumno.instrumento.useCase;

import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;

import java.util.List;

public class GetInstrumentoList {
    private InstrumentoRepository instrumentoRepository;

    public GetInstrumentoList(InstrumentoRepository instrumentoRepository) {
        this.instrumentoRepository = instrumentoRepository;
    }

    public List<InstrumentoUi> execute(int sesionAprendizajeId){
        return instrumentoRepository.getInstrumentos(sesionAprendizajeId);
    }
}
