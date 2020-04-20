package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioExternoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetUsuarioPorCorreoLocal implements UseCaseLoginSincrono<GetUsuarioPorCorreoLocal.Request, UsuarioExternoUi> {

    private LoginDataRepository loginDataRepository;

    public GetUsuarioPorCorreoLocal(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public RetrofitCancel execute(Request request, final Callback<UsuarioExternoUi> callback) {
        return loginDataRepository.getUsuarioLocalPorCorreo(request.getUrlAdminServicio(),request.getUsuario(), request.getPassword(), request.getCorreo(), new LoginDataRepository.Callback<UsuarioExternoUi>() {
            @Override
            public void onResponse(boolean success, UsuarioExternoUi value) {
                callback.onResponse(success, value);
            }
        });
    }

    public static class Request{
        private String urlAdminServicio;
        private String usuario;
        private String password;
        private String correo;

        public Request(String urlAdminServicio, String usuario, String password, String correo) {
            this.urlAdminServicio = urlAdminServicio;
            this.usuario = usuario;
            this.password = password;
            this.correo = correo;
        }

        public String getUrlAdminServicio() {
            return urlAdminServicio;
        }

        public String getUsuario() {
            return usuario;
        }

        public String getPassword() {
            return password;
        }

        public String getCorreo() {
            return correo;
        }
    }

}
