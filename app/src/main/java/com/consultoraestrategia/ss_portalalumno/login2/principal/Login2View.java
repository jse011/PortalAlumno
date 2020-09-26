package com.consultoraestrategia.ss_portalalumno.login2.principal;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

public interface Login2View  extends BaseView<Login2Presenter> {
    void showUser(boolean backStack);

    void showCorreo(boolean backStack);

    void showDni(boolean backStack);

    void showListaUsuario(boolean backStack);

    void showDialogProgress();

    void showPassword();

    void disabledOnClick();

    void enableddOnClick();

    void clearFragment();

    void onBackPressed();

    void goToActivity(int idUsuario);

    void showBloqueo(boolean backStack);

    void onShowPagoEnLinea(int personaId, String numDoc);
}
