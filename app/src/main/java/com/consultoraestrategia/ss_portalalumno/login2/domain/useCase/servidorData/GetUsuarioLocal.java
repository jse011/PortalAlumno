package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetUsuarioLocal implements UseCaseLoginSincrono<GetUsuarioLocal.Request, UsuarioUi> {

    private LoginDataRepository loginDataRepository;

    public GetUsuarioLocal(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public RetrofitCancel execute(Request request, final Callback<UsuarioUi> callback) {
        return loginDataRepository.getUsuarioLocal(request.getUsuarioId(), new LoginDataRepository.Callback<UsuarioUi>() {
            @Override
            public void onResponse(boolean success, UsuarioUi value) {
                callback.onResponse(success, value);
            }
        });
    }

    public static class Request{
        private int usuarioId;

        public Request(int usuarioId) {
            this.usuarioId = usuarioId;
        }

        public int getUsuarioId() {
            return usuarioId;
        }
    }

}
