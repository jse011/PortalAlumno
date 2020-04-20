package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetPersonaLocal implements UseCaseLoginSincrono<GetPersonaLocal.Request, PersonaUi> {

    private LoginDataRepository loginDataRepository;

    public GetPersonaLocal(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public RetrofitCancel execute(Request request, final Callback<PersonaUi> callback) {
        return loginDataRepository.getPersonaLocal(request.getUsuario(), new LoginDataRepository.Callback<PersonaUi>() {
            @Override
            public void onResponse(boolean success, PersonaUi value) {
                callback.onResponse(success, value);
            }
        });
    }

    public static class Request{
        private String usuario;

        public Request(String usuario) {
            this.usuario = usuario;
        }

        public String getUsuario() {
            return usuario;
        }
    }

}
