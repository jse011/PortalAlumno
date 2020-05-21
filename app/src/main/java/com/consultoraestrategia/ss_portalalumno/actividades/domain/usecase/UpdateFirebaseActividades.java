package com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;

import java.util.List;

public class UpdateFirebaseActividades {

    private ActividadesRepository actividadesRepository;

    public UpdateFirebaseActividades(ActividadesRepository actividadesRepository) {
        this.actividadesRepository = actividadesRepository;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList, CallBack callBack){
        actividadesRepository.upadteFirebaseActividad(cargaCursoId, sesionAprendizajeId, actividadesUiList, new ActividadesDataSource.CallbackSimple() {
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
