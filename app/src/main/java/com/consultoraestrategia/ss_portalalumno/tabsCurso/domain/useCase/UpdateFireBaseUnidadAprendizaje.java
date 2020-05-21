package com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase;

import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorio;

import java.util.List;

public class UpdateFireBaseUnidadAprendizaje {
    private TabCursoRepositorio tabCursoRepositorio;

    public UpdateFireBaseUnidadAprendizaje(TabCursoRepositorio tabCursoRepositorio) {
        this.tabCursoRepositorio = tabCursoRepositorio;
    }

    public void execute(int idCargaCurso, int idCalendarioPeriodo, CallBack callBack){
        tabCursoRepositorio.updateFirebaseUnidadesList(idCargaCurso, idCalendarioPeriodo,new TabCursoRepositorio.Callback() {
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
