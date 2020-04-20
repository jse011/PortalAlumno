package com.consultoraestrategia.ss_portalalumno.login2.principal.dni;

import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

public interface DniView {

    void onAttach(Login2Presenter presenter);

    void onInvalitedDni(String error);

    void setDni(String dni);

    void hideProgress();

    void showProgress();
}
