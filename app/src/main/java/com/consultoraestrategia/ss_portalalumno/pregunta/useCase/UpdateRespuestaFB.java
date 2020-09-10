package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;

public class UpdateRespuestaFB {
    private PreguntaRepositorio preguntaRepositorio;

    public UpdateRespuestaFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, RespuestaUi respuestaUi, UpdateRespuestaFB.Callback callback){
        preguntaRepositorio.updateRespuestaFB(cargaCursoId, sesionAprendizajeId, preguntaId, respuestaUi, new PreguntaRepositorio.Callback() {
            @Override
            public void onLoad(boolean success) {
                callback.onLoad(success);
            }
        });
    }

    public interface Callback{
        void onLoad(boolean success);
    }
}
