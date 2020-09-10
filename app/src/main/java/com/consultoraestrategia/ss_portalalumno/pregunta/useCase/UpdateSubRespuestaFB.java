package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

public class UpdateSubRespuestaFB {
    private PreguntaRepositorio preguntaRepositorio;

    public UpdateSubRespuestaFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, UpdateSubRespuestaFB.Callback callback){
        preguntaRepositorio.updateSubRespuestaFB(cargaCursoId, sesionAprendizajeId, preguntaId, subRespuestaUi, new PreguntaRepositorio.Callback() {
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
