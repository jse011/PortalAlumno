package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class UpdateCalendarioPeriodo {
    private MainRepositorio mainRepositorio;

    public UpdateCalendarioPeriodo(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
    }

    public RetrofitCancel execute(int anioAcademico, Callback callback){
        return mainRepositorio.updateCalendarioPeriodo(anioAcademico, new MainRepositorio.SuccessCallback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callback.onSucess();
                }else {
                    callback.onError();
                }
            }
        });
    }

    public interface Callback{
        void onSucess();
        void onError();
    }
}
