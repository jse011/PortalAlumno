package com.consultoraestrategia.ss_portalalumno.pregunta.lista;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;

import java.util.List;

public interface PreguntaView extends BaseView<PreguntaPresenter> {
    void updatePreguntaListView(PreguntaUi preguntaUi);

    void removePreguntaListView(PreguntaUi preguntaUi);

    void clearAlumnoList();

    void showActivityPreguntaPrincial();

    void setListPregunta(List<PreguntaUi> preguntaUiList);
}
