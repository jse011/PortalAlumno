package com.consultoraestrategia.ss_portalalumno.pregunta.data.source;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

import java.util.List;

public interface PreguntaRepositorio {
    PreguntaUi getPregunta(String preguntaId);

    FirebaseCancel updateRespuesta(int cargaCursoId, int sesionAprendizajeId, String preguntaId, CallbackRespuesta callback);

    PreguntaUi getPersona(String preguntaPaId);

    void saveRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, String preguntaRespuestaId, String contenido, CallbackSaveRespuesta callback);

    void updateRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, RespuestaUi respuestaUi, Callback callback);

    void updateSubRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, Callback callback);

    void eiminarRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, RespuestaUi respuestaUi, Callback callback);

    void eliminarSubRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, Callback callback);

    List<RespuestaUi> getListaRespuesta(String pregunta);

    List<PreguntaUi> getListPreguntas(int sesionAprendizaje);

    FirebaseCancel updatebloquePregunta(int cargaCursoId, int sesionAprendizajeId, String preguntaId,CallbackPregunta callbackPregunta);

    interface CallbackRespuesta{

        void addRespuesta(RespuestaUi respuestaUi);

        void updateRespuesta(RespuestaUi respuestaUi);

        void removeRespuesta(String preguntaRespuestaId);
    }

    interface Callback{
        void onLoad(boolean success);
    }

    interface CallbackSaveRespuesta{
        void onPreLoad(RespuestaUi respuestaUi, SubRespuestaUi subRespuestaUi);
        void onLoad(boolean success);
    }

    interface CallbackPregunta{
        void onChangeNombre(String titulo);
        void onBloqueado();
        void onDesbloqueado();
    }
}
