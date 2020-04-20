package com.consultoraestrategia.ss_portalalumno.login2.principal;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.principal.correo.CorreoView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.dni.DniView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario.ListaUsuarioView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.password.PasswordView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.usuario.UsuarioView;

public interface Login2Presenter extends BasePresenter<Login2View> {
    void attachView(UsuarioView usuarioView);

    void onUsuarioViewDestroyed();

    void onCorreoViewDestroyed();

    void onDniViewDestroyed();

    void onListaUsuarioViewDestroyed();

    void attachView(CorreoView correoView);

    void attachView(DniView dniView);

    void attachView(ListaUsuarioView listaUsuarioView);

    void onPasswordViewDestroyed();

    void attachView(PasswordView passwordView);

    void onClickUsuarioSiguiente(String usuario, String password);

    void onClickUsuarioAtras();

    void onClickCorreoSiguiente(String correo);

    void onClickCorreoAtras();

    void onClickDniSiguiente(String dni);

    void onClickDniAtras();

    void onClickQuitarUsuario();

    void onClickPersona(PersonaUi personaUi);

    void onClickPasswordSiguiente(String password);

    void onClickPasswordAtras();
}
