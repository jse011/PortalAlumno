package com.consultoraestrategia.ss_portalalumno.main;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.NuevaVersionUi;

import java.util.ArrayList;
import java.util.List;

public interface MainView extends BaseView<MainPresenter> {
    void showMenuList(ArrayList<Object> objects);

    void showActivityLogin();

    void showListCurso(List<CursosUi> cursosUiList);

    void showTabCursoActivity();

    void changeNombreUsuario(String nombre);

    void changeFotoUsuario(String foto);

    void mostrarDialogoCerrarSesion();

    void cerrarSesion();

    void setNameAnioAcademico(String nombre);

    void changeFotoUsuarioApoderado(String fotoApoderado);

    void validateFirebase(String usuarioFirebase, String passwordFirebase);

    void showActivtyBloqueo();

    void initBloqueo();

    void showNuevaversion(NuevaVersionUi nuevaVersionUi);
}
