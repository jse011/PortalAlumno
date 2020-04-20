package com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData;

import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.entities.DatosProgressUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetDatosInicioSesion implements UseCaseLoginSincrono<GetDatosInicioSesion.Request, GetDatosInicioSesion.Response> {

    private LoginDataRepository loginDataRepository;

    public GetDatosInicioSesion(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }



    @Override
    public RetrofitCancel execute(Request request, final Callback<Response> callback) {
        return loginDataRepository.getDatosInicioSesion(request.getUsuarioId(), new LoginDataRepository.CallBackSucces<DatosProgressUi>() {
            @Override
            public void onLoad(boolean success, DatosProgressUi item) {
                if(success){
                    callback.onResponse(true, new Response(item.getAnioAcademicoId()));
                }else {
                    callback.onResponse(false, null);
                }
            }

            @Override
            public void onRequestProgress(int progress) {

            }

            @Override
            public void onResponseProgress(int progress) {

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

    public class Response{
        private int anioAcademicoId;

        public Response(int anioAcademicoId) {
            this.anioAcademicoId = anioAcademicoId;
        }

        public int getAnioAcademicoId() {
            return anioAcademicoId;
        }
    }

    public class RequestRetrofitCancel extends Response{
        private  RetrofitCancel retrofitCancel;

        public RequestRetrofitCancel( RetrofitCancel retrofitCancel) {
            super(0);
            this.retrofitCancel = retrofitCancel;
        }

        public RetrofitCancel getRetrofitCancel() {
            return retrofitCancel;
        }
    }

}
