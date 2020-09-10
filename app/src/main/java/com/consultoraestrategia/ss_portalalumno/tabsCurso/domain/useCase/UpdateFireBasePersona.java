package com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase;

import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorio;

public class UpdateFireBasePersona {
    private TabCursoRepositorio tabCursoRepositorio;

    public UpdateFireBasePersona(TabCursoRepositorio tabCursoRepositorio) {
        this.tabCursoRepositorio = tabCursoRepositorio;
    }

    public void execute(int idCargaCurso, CallBack callBack){
        tabCursoRepositorio.updateFirebasePersonaList(idCargaCurso,new TabCursoRepositorio.Callback() {
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
