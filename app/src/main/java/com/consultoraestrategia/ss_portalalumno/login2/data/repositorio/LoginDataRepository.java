package com.consultoraestrategia.ss_portalalumno.login2.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ActualizarUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.AlarmaUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.CalendarioPeriodoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.DatosProgressUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.HabilitarAccesoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ProgramaEducativoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ServiceEnvioUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioExternoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

import java.util.List;

public interface LoginDataRepository {
    RetrofitCancel getUsuarioExterno(String urlAdminServicio, String usuario, String password, Callback<UsuarioExternoUi> callback);

    void saveUrlServidorLocal(String url);

    RetrofitCancel getUsuarioLocalPorCorreo(String urlAdminServicio, String usuario, String password, String correo, Callback<UsuarioExternoUi> callback);

    RetrofitCancel getUsuarioLocalPorDni(String urlAdminServicio, String usuario, String password, String correo, String dni, Callback<UsuarioExternoUi> callback);

    RetrofitCancel getDatosInicioSesion(int usuarioId, boolean remover, CallBackSucces<DatosProgressUi> callback);

    RetrofitCancel getPersonaLocal(String usuario, Callback<PersonaUi> callback);

    RetrofitCancel getUsuarioLocal(int usuarioId, Callback<UsuarioUi> callback);

    String getNombreColegio(int usuarioId);

    String getNombreAnioActual(int anioAcademicoId);

    boolean savePlanSinck(int hora, int minute);

    FirebaseCancel ishabilitadoAcceso(Callback<HabilitarAccesoUi> booleanCallback);

    void removerDatosIncioSession(CallbackSimple callbackSimple);

    interface Callback<S>{
        void onResponse(boolean success, S value);
    }

    interface CallbackSimple{
        void onResponse(boolean success);
    }


    interface CallBackSucces<T>{
        void onLoad(boolean success, T item);
        void onRequestProgress(int progress);
        void onResponseProgress(int progress);
    }

    interface CallBackComplejo<T>{
        void onResponse(boolean success, T value);
        void onChangeRetrofit(RetrofitCancel retrofitCancel);
        
    }
}

