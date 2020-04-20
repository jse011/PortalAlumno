package com.consultoraestrategia.ss_portalalumno.login2.principal.correo;

import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

public interface CorreoView {

    void onAttach(Login2Presenter presenter);

    void onInvalitedCorreo(String error);

    void setCorreo(String correo);

    void hideProgress();

    void showProgress();
}
