package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioExternoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetUsuarioPorDniLocal implements UseCaseLoginSincrono<GetUsuarioPorDniLocal.Request, UsuarioExternoUi> {

    private LoginDataRepository loginDataRepository;

    public GetUsuarioPorDniLocal(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public RetrofitCancel execute(Request request, final Callback<UsuarioExternoUi> callback) {
        return loginDataRepository.getUsuarioLocalPorDni(request.getUrlAdminServicio(),request.getUsuario(), request.getPassword(),request.getCorreo(), request.getDni(), new LoginDataRepository.Callback<UsuarioExternoUi>() {
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
        private String dni;

        public Request(String urlAdminServicio, String usuario, String password, String correo, String dni) {
            this.urlAdminServicio = urlAdminServicio;
            this.usuario = usuario;
            this.password = password;
            this.correo = correo;
            this.dni = dni;
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

        public String getDni() {
            return dni;
        }
    }

}
