package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;

import java.util.List;

public class UpdateListRespuestaFB {
    private PreguntaRepositorio preguntaRepositorio;

    public UpdateListRespuestaFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public FirebaseCancel excute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, Callback callback){
        return preguntaRepositorio.updateRespuesta(cargaCursoId, sesionAprendizajeId, preguntaId, new PreguntaRepositorio.CallbackRespuesta(){

            @Override
            public void addRespuesta(RespuestaUi respuestaUi) {
                callback.addRespuesta(respuestaUi);
            }

            @Override
            public void updateRespuesta(RespuestaUi respuestaUi) {
                callback.updateRespuesta(respuestaUi);
            }

            @Override
            public void removeRespuesta(String preguntaRespuestaId) {
                callback.removeRespuesta(preguntaRespuestaId);
            }
        } );
    }

    public interface Callback{
        void addRespuesta(RespuestaUi respuestaUi);

        void updateRespuesta(RespuestaUi respuestaUi);

        void removeRespuesta(String preguntaRespuestaId);
    }

}
