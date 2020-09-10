package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

public class SaveRespuestaFB {
    private PreguntaRepositorio preguntaRepositorio;

    public SaveRespuestaFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, String preguntaRespuestaId, String contenido, Callback callback){
        preguntaRepositorio.saveRespuestaFB(cargaCursoId, sesionAprendizajeId, preguntaId, preguntaRespuestaId, contenido, new PreguntaRepositorio.CallbackSaveRespuesta() {

            @Override
            public void onPreLoad(RespuestaUi respuestaUi, SubRespuestaUi subRespuestaUi) {
                if(respuestaUi!=null){
                    callback.onPreLoad(respuestaUi);
                }else {
                    callback.onPreLoad(subRespuestaUi);
                }
            }

            @Override
            public void onLoad(boolean success) {
                callback.onLoad(success);
            }
        });
    }

    public interface Callback{
        void onPreLoad(RespuestaUi respuestaUi);
        void onPreLoad(SubRespuestaUi subRespuestaUi);
        void onLoad(boolean success);
    }
}
