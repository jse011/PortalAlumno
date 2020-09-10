package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateFirebaseColaborativa {
    private TabSesionesRepositorio tabSesionesRepositorio;

    public UpdateFirebaseColaborativa(TabSesionesRepositorio tabSesionesRepositorio) {
        this.tabSesionesRepositorio = tabSesionesRepositorio;
    }

    public FirebaseCancel execute(int sesionAprendizajeId, int cargaCursoId, CallBack callBack){
        return tabSesionesRepositorio.updateFirebaseColaborativa(sesionAprendizajeId, cargaCursoId, new TabSesionesRepositorio.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                callBack.onLoad(success);
            }
        });
    }

    public interface CallBack{
        void onLoad(boolean success);
    }
}
