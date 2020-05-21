package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo;

import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.InstrumentoEvaluacionPresenter;

import java.util.List;

public interface TipoEvaluacionView {
    void setPresenter(InstrumentoEvaluacionPresenter instrumentoEvaluacionPresenter);

    void setTitulo(String nombre);

    void showImage(String path);

    void hideImage();

    void setProgress(String progress);

    void setInputEntero();

    void setInputDecimal();

    void setInputString();

    void setListValores(List<ValorUi> valores);

    void changeButtonFinsh(String texto);

}
