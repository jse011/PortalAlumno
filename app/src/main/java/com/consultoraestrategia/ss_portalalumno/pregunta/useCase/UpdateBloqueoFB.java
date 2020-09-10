package com.consultoraestrategia.ss_portalalumno.pregunta.useCase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;

public class UpdateBloqueoFB {
    private PreguntaRepositorio preguntaRepositorio;

    public UpdateBloqueoFB(PreguntaRepositorio preguntaRepositorio) {
        this.preguntaRepositorio = preguntaRepositorio;
    }

    public FirebaseCancel execute(int cargaCursoId, int sesionAprendizajeId, String preguntaId, Callback callback){
        return preguntaRepositorio.updatebloquePregunta(cargaCursoId, sesionAprendizajeId, preguntaId, new PreguntaRepositorio.CallbackPregunta() {
            @Override
            public void onChangeNombre(String titulo) {
                callback.onChangeTitulo(titulo);
            }

            @Override
            public void onBloqueado() {
                callback.onBloqueado();
            }

            @Override
            public void onDesbloqueado() {
                callback.onDesbloqueado();
            }
        });
    }

    public interface Callback {
        void onBloqueado();
        void onDesbloqueado();

        void onChangeTitulo(String titulo);
    }
}
