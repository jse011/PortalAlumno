package com.consultoraestrategia.ss_portalalumno.pregunta.principal;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

import java.util.List;

public interface PreguntaPrincipalView extends BaseView<PreguntaPrincipalPresenter> {
    void updateRespuesta(RespuestaUi respuestaUi);
    void removePregunta(String preguntaRespuestaId);
    void setImgeAlumno(String foto);
    void setTituloPregunta(String titulo, String tipoId, String color);

    void showDialogKey();

    void addRespuesta(RespuestaUi respuestaUi);

    void addSubRespuesta(SubRespuestaUi subRespuestaUi);

    void setListRespuesta(List<RespuestaUi> listRespuesta);

    void clearListRespuesta();

    void modoOnline();

    void modoOffline(String color2);
}
