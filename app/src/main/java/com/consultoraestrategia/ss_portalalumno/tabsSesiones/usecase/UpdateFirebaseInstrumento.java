package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateFirebaseInstrumento {
    private TabSesionesRepositorio tabSesionesRepositorio;

    public UpdateFirebaseInstrumento(TabSesionesRepositorio tabSesionesRepositorio) {
        this.tabSesionesRepositorio = tabSesionesRepositorio;
    }

    public FirebaseCancel execute(int sesionAprendizajeId, int cargaCursoId, CallBack callBack){
        return tabSesionesRepositorio.updateFirebaseInstrumento(sesionAprendizajeId, cargaCursoId, new TabSesionesRepositorio.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError("");
                }
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
