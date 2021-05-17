package com.consultoraestrategia.ss_portalalumno.main;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;

public interface MainPresenter extends BasePresenter<MainView> {
    void onProgramaEducativoUIClicked(ProgramaEduactivoUI programaEduactivosUI);
    void onAccesoSelected(UsuarioAccesoUI usuarioAccesoUI);
    void onClickConfiguracion(ConfiguracionUi configuracionUi);

    void onIbtnProgramaClicked();

    void onClickBtnAcceso();

    void onClickBtnConfiguracion();

    void onClickCurso(CursosUi cursosUi);

    void onClickDialogoCerrarSesion();

    void nuevaVersionDisponible(String newVersionCode, String changes);

}
