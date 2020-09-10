package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;

public class GetPregunta {
    private PreguntaRepositorio preguntaRepositorio;

    public GetPregunta(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public PreguntaUi execute(String preguntaPaId){
        return preguntaRepositorio.getPersona(preguntaPaId);
    }
}
