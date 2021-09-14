package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateInstrumentoEncuestaSesion {

    TabSesionesRepositorio repository;

    public UpdateInstrumentoEncuestaSesion(TabSesionesRepositorio repository) {
        this.repository = repository;
    }

    public RetrofitCancel execute(int sesionAprendizajeId, int personaId, Callback callback){
       return repository.getInstrumentoEncuestaEval(sesionAprendizajeId, personaId, new TabSesionesRepositorio.CallbackSimple() {
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
