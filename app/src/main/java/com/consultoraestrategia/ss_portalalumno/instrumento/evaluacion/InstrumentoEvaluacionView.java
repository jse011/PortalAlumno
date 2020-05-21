package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;

import java.util.List;

public interface InstrumentoEvaluacionView extends BaseView<InstrumentoEvaluacionPresenter> {
    void showListPreguntas(List<VariableUi> variableUiList);

    void setMaxProgress(int size);

    void changePage(int position);

    void setProgressPostionOffline(int position);

    void setProgressPostionOnline(int position);

    void finalizar();

    void showContenDialog();
}
