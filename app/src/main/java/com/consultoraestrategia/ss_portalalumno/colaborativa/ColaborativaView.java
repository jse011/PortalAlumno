package com.consultoraestrategia.ss_portalalumno.colaborativa;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.List;

public interface ColaborativaView extends BaseView<ColaborativaPresenter> {
    void setListColaborativa(List<ColaborativaUi> colaborativaUiList);

    void showVinculo(String descripcion);

    void setListGrabacionesColaborativa(List<ColaborativaUi> colaborativaUiList);

    void setListColaborativaServidor(List<ColaborativaUi> colaborativaUiList);
}
