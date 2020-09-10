package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateFirebasePreguntas {
    private TabSesionesRepositorio tabSesionesRepositorio;

    public UpdateFirebasePreguntas(TabSesionesRepositorio tabSesionesRepositorio) {
        this.tabSesionesRepositorio = tabSesionesRepositorio;
    }

    public void execute(int sesionAprendizajeId, int cargaCursoId, CallBack callBack){
        tabSesionesRepositorio.updateFirebasePregunta(sesionAprendizajeId, cargaCursoId, new TabSesionesRepositorio.PreguntaCallback() {
            @Override
            public void onPreLoad(FirebaseCancel firebaseCancel) {
                callBack.onPreLoad(firebaseCancel);
            }

            @Override
            public void addPregunta(String preguntaPAId) {
                callBack.addPregunta(preguntaPAId);
            }

            @Override
            public void updatePregunta(String preguntaPAId) {
                callBack.updatePregunta(preguntaPAId);
            }

            @Override
            public void removePregunta(String preguntaPAId) {
                callBack.removePregunta(preguntaPAId);
            }

            @Override
            public void updatePreguntaAlumno(String preguntaPAId, int alumnoId) {
                callBack.updatePreguntaAlumno(preguntaPAId, alumnoId);
            }
        });
    }

    public interface CallBack{
        void onPreLoad(FirebaseCancel firebaseCancel);

        void addPregunta(String preguntaPAId);

        void updatePregunta(String preguntaPAId);

        void removePregunta(String preguntaPAId);

        void updatePreguntaAlumno(String preguntaPAId, int alumnoId);
    }
}
