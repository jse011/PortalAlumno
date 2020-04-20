package com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario;

import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

import java.util.List;

public interface ListaUsuarioView {

    void onAttach(Login2Presenter presenter);

    void listaUsuarioView(List<PersonaUi> usuarioUiList, boolean quitarUsuario);

    void setTextoBtnQuitarUsuario(String texto);
}
