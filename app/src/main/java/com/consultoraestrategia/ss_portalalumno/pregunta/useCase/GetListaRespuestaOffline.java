package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;

import java.util.List;

public class GetListaRespuestaOffline {
    private PreguntaRepositorio preguntaRepositorio;

    public GetListaRespuestaOffline(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public List<RespuestaUi> execute(String pregunta){
        return preguntaRepositorio.getListaRespuesta(pregunta);
    }
}
