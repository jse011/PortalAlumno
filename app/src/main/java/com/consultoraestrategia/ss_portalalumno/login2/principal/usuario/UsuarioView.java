package com.consultoraestrategia.ss_portalalumno.login2.principal.usuario;

import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

public interface UsuarioView {

    void onAttach(Login2Presenter presenter);

    void onErrorPassword(String error);

    void showProgress();

    void hideProgress();

    void hideAtras();

    void showAtras();

    void onCredencialesIncorrectos(String error);

    void setUsuario(String usuario);

    void setPassword(String password);
}
