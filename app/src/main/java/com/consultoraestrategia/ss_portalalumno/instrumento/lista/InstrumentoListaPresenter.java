package com.consultoraestrategia.ss_portalalumno.instrumento.lista;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;

public interface InstrumentoListaPresenter extends BaseFragmentPresenter<InstrumentoListaView> {

    void onClick(InstrumentoUi instrumentoUi);

    void notifyChangeFragment();
}
