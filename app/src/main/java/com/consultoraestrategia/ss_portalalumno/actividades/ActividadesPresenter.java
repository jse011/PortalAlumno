package com.consultoraestrategia.ss_portalalumno.actividades;

import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.ui.ActividadesView;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;

/**
 * Created by kike on 08/02/2018.
 */

public interface ActividadesPresenter extends BaseFragmentPresenter<ActividadesView> {

    void setExtras();

    void onResumeFragment();

    void onSelectActividad(ActividadesUi actividadesUi);

    void onClickDownload(RecursosUi repositorioFileUi);

    void onClickClose(RecursosUi repositorioFileUi);

    void onClickArchivo(RecursosUi repositorioFileUi);
}
