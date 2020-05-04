package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public class GetFireBaseUnidadAprendizajeList {
    private UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;

    public GetFireBaseUnidadAprendizajeList(UnidadAprendizajeRepositorio unidadAprendizajeRepositorio) {
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
    }

    public void execute(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId, CallBack callBack){
        unidadAprendizajeRepositorio.getFirebaseUnidadesList(idCargaCurso, idCalendarioPeriodo, idAnioAcademico, planCursoId, new UnidadAprendizajeRepositorio.Callback<List<UnidadAprendizajeUi>>() {
            @Override
            public void onLoad(boolean success, List<UnidadAprendizajeUi> item) {
                if(success){
                    callBack.onSucces(item);
                }else {
                    callBack.onError("error");
                }
            }
        });
    }

    public interface CallBack{
        void onSucces(List<UnidadAprendizajeUi> unidadAprendizajeUiList);
        void onError(String error);
    }
}
