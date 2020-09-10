package com.consultoraestrategia.ss_portalalumno.colaborativa;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

public interface ColaborativaPresenter extends BaseFragmentPresenter<ColaborativaView> {
    void changeColaborativaList();

    void changeReunionVirtualList();

    void onClickColobarativa(ColaborativaUi colaborativaUi);
}
