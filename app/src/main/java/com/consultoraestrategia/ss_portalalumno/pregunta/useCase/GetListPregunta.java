package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;

import java.util.List;

public class GetListPregunta {
    private PreguntaRepositorio preguntaRepositorio;

    public GetListPregunta(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public List<PreguntaUi> execute(int sesionAprendizaje){
        return preguntaRepositorio.getListPreguntas(sesionAprendizaje);
    }
}
