package com.consultoraestrategia.ss_portalalumno.instrumento.useCase;

import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.EncuestaUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;

import java.util.List;

public class GetInstrumentoEncuesta {

    private InstrumentoRepository instrumentoRepository;

    public GetInstrumentoEncuesta(InstrumentoRepository instrumentoRepository) {
        this.instrumentoRepository = instrumentoRepository;
    }

    public List<EncuestaUi> execute(int sesionAprendizajeId, int personaId){
        return instrumentoRepository.getInstrumentoEncuesta(sesionAprendizajeId, personaId);
    }
}
