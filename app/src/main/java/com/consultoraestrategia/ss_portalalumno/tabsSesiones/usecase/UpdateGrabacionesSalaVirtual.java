package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateGrabacionesSalaVirtual {

    TabSesionesRepositorio repository;

    public UpdateGrabacionesSalaVirtual(TabSesionesRepositorio repository) {
        this.repository = repository;
    }

    public RetrofitCancel execute(int sesionAprendizajeId, Callback callback){
       return repository.getGrabacionesSalaVirtual(sesionAprendizajeId, new TabSesionesRepositorio.CallbackSimple() {
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
