package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public class UpdateFireBaseSesionAprendizaje {
    private UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;

    public UpdateFireBaseSesionAprendizaje(UnidadAprendizajeRepositorio unidadAprendizajeRepositorio) {
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
    }

    public void execute(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId, List<UnidadAprendizajeUi> unidadAprendizajeUiList, CallBack callBack){
        unidadAprendizajeRepositorio.updateFirebaseUnidadesList(idCargaCurso, idCalendarioPeriodo, idAnioAcademico, planCursoId, unidadAprendizajeUiList,new UnidadAprendizajeRepositorio.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError("error");
                }
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
