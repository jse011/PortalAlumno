package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;

public class UpdateFirebaseTipoNota {
    private MainRepositorio mainRepositorio;

    public UpdateFirebaseTipoNota(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
    }

    public void execute(int programaEducativoId, CallBack callBack){
        mainRepositorio.pdateFirebaseTipoNota(programaEducativoId, success -> {
            if(success){
                callBack.onSucces();
            }else {
                callBack.onError("");
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
