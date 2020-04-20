package com.consultoraestrategia.ss_portalalumno.login2.data.preferent;

import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;

import java.util.List;

public interface LoginPreferentRepository {
    interface Callback<T>{
        void onLoad(boolean success, T item);
    }
    List<PersonaUi> getTodosUsuarios();

    void guardarUsuario(PersonaUi usuarioUi, Callback<Boolean> usuarioCallback);

    void eliminarUsuario(PersonaUi usuarioUi, Callback<Boolean> usuarioCallback);

    void elimarTodosUsuario(Callback<Boolean> usuarioCallback);
}
