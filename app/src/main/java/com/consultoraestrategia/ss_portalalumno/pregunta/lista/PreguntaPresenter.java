package com.consultoraestrategia.ss_portalalumno.pregunta.lista;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;

public interface PreguntaPresenter extends BaseFragmentPresenter<PreguntaView> {
    void addPreguntaFirebase(String preguntaPAId);

    void updatePreguntaFirebase(String preguntaPAId);

    void removePreguntaFirebase(String preguntaPAId);

    void updatePreguntaAlumno(String preguntaPAId, int alumnoId);

    void onInitPreguntaListFirebase(boolean online);

    void onClickPregunta(PreguntaUi preguntaUi);
}
