package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

public interface UnidadAprendizajePresenter extends BaseFragmentPresenter<UnidadAprendizajeView> {

    void onClickUnidadAprendizaje(UnidadAprendizajeUi unidadAprendizajeUi);

    void onClickSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi);

    void notifyChangeFragment(boolean finishUpdateUnidadFb);
}
