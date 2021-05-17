package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

public interface TabSesionView extends BaseView<TabSesionPresenter> {
    void setNombreSesion(String nombreApredizaje);
    void setNumeroSession(int numero);

    void setStatusBarColor(String color);

    void setToolbarColor(String color, String nombre);

    void setTabColor(String indicadorColor, String textColor, String selectedTextColor);

    void setDescripcionSesion(String color, String titulo, String subtitulo, String fondo);

    void modoOnline();

    void modoOffline();

    void servicePasarAsistencia(int silaboEventoId);
}
