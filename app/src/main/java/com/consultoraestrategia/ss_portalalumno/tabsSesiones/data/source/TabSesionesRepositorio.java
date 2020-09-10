package com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source;


import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public interface TabSesionesRepositorio {

    interface CallbackSimple{
        void onLoad(boolean success);
    }
    interface PreguntaCallback{
        void onPreLoad(FirebaseCancel firebaseCancel);

        void addPregunta(String preguntaPAId);

        void updatePregunta(String preguntaPAId);

        void removePregunta(String preguntaPAId);

        void updatePreguntaAlumno(String preguntaPAId, int alumnoId);
    }
    FirebaseCancel updateFirebaseInstrumento(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple);
    void updateFirebasePregunta(int sesionAprendizajeId, int cargaCursoId, PreguntaCallback callbackSimple);
    FirebaseCancel updateFirebaseColaborativa(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple);
    FirebaseCancel updateFirebaseReunionVirtual(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple);
}
