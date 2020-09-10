package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

public class EliminarSubRespuestaFB {
    private PreguntaRepositorio preguntaRepositorio;

    public EliminarSubRespuestaFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, EliminarSubRespuestaFB.Callback callback){
        preguntaRepositorio.eliminarSubRespuestaFB(cargaCursoId, sesionAprendizajeId, preguntaId, subRespuestaUi, new PreguntaRepositorio.Callback() {
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
