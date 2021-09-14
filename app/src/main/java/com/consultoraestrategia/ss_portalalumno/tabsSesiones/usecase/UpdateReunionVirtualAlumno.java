package com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;

public class UpdateReunionVirtualAlumno {

    TabSesionesRepositorio repository;

    public UpdateReunionVirtualAlumno(TabSesionesRepositorio repository) {
        this.repository = repository;
    }

    public RetrofitCancel execute(int sesionAprendizajeId, int entidadId, int georeferenciaId, Callback callback){
        Log.d(getClass().getSimpleName(), "tabSesionColaborativaView");
       return repository.getReunionVirtualAlumno(sesionAprendizajeId, entidadId, georeferenciaId,new TabSesionesRepositorio.CallbackSimple() {
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
