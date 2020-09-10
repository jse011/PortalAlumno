package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateFirebaseReunionVirtual {
    private TabSesionesRepositorio tabSesionesRepositorio;

    public UpdateFirebaseReunionVirtual(TabSesionesRepositorio tabSesionesRepositorio) {
        this.tabSesionesRepositorio = tabSesionesRepositorio;
    }

    public FirebaseCancel execute(int sesionAprendizajeId, int cargaCursoId, CallBack callBack){
        return tabSesionesRepositorio.updateFirebaseReunionVirtual(sesionAprendizajeId, cargaCursoId, new TabSesionesRepositorio.CallbackSimple() {
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
