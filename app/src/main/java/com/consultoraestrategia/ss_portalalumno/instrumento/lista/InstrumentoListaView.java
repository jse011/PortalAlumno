package com.consultoraestrategia.ss_portalalumno.instrumento.lista;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.EncuestaUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;

import java.util.List;

public interface InstrumentoListaView extends BaseView<InstrumentoListaPresenter> {


    void showListInstrumento(List<InstrumentoUi> instrumentoUiList);

    void showActivityInstrumento();

    void showListaInstrumentoEncuesta(List<EncuestaUi> encuestaUiList);
}
