package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.HabilitarAccesoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class IsHabilitado {
    private LoginDataRepository repository;

    public IsHabilitado(LoginDataRepository repository) {
        this.repository = repository;
    }

    public FirebaseCancel ishabilitadoAcceso(Callback callback){
        return repository.ishabilitadoAcceso(new LoginDataRepository.Callback<HabilitarAccesoUi>(){

            @Override
            public void onResponse(boolean success, HabilitarAccesoUi habilitarAccesoUi) {
                callback.onLoad(success, habilitarAccesoUi);
            }
        });
    }

    public interface Callback{
        void onLoad(boolean sucess, HabilitarAccesoUi habilitarAccesoUi);
    }

}
