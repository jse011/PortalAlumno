package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public class UpdateFireBaseUnidadAprendizaje {
    private UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;

    public UpdateFireBaseUnidadAprendizaje(UnidadAprendizajeRepositorio unidadAprendizajeRepositorio) {
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
    }

    public RetrofitCancel execute(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId, List<UnidadAprendizajeUi> unidadAprendizajeUiList, CallBack callBack){
        return  unidadAprendizajeRepositorio.updateServidorUnidadesList(idCargaCurso, idCalendarioPeriodo, idAnioAcademico, planCursoId, unidadAprendizajeUiList, sucess -> {
            if(sucess){
                callBack.onSucces();
            }else {
                callBack.onError("error");
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
