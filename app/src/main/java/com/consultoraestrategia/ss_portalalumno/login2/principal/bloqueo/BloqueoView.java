package com.consultoraestrategia.ss_portalalumno.login2.principal.bloqueo;

import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

public interface BloqueoView {
    void onAttach(Login2Presenter presenter);

    void showFoto(String imagenUrl);
}
