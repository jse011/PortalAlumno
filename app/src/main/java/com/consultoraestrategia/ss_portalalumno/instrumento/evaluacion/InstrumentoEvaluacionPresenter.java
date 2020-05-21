package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion;


import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo.TipoEvaluacionView;

import java.util.HashMap;

public interface InstrumentoEvaluacionPresenter extends BasePresenter<InstrumentoEvaluacionView> {
    void attachViewList(HashMap<VariableUi, TipoEvaluacionView> tipoEvaluacionViewList);

    void attachView(TipoEvaluacionView tipoEvaluacionView);

    void changePage(int position);

    void siguientePregunta(TipoEvaluacionView tipoEvaluacionView, String respuesta);

    void onOnline();

    void onOffline();
}
