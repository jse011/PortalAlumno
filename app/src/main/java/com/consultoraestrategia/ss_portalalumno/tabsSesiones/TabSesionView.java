package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

public interface TabSesionView extends BaseView<TabSesionPresenter> {
    void setNombreSesion(String nombreApredizaje);
    void setNumeroSession(int numero);
}
