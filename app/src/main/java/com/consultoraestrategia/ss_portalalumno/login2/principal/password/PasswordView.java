package com.consultoraestrategia.ss_portalalumno.login2.principal.password;

import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

public interface PasswordView {

    void onAttach(Login2Presenter presenter);

    void setPersona(PersonaUi personaUiSelected);

    void onInvalitedPassword(String error);

    void hideProgress();

    void showProgress();
}
