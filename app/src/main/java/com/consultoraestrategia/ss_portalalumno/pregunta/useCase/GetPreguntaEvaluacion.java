package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;

public class GetPreguntaEvaluacion {
    private PreguntaRepositorio preguntaRepositorio;

    public GetPreguntaEvaluacion(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public PreguntaUi execute(String preguntaId){
        return preguntaRepositorio.getPregunta(preguntaId );
    }
}
