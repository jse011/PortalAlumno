package com.consultoraestrategia.ss_portalalumno.pregunta.principal;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.pregunta.dialog.DialogkeyBoardView;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;

public interface PreguntaPrincipalPresenter extends BasePresenter<PreguntaPrincipalView> {
    void onClickResponder(RespuestaUi respuestaUi);

    void onResponder();

    void onClickAceptarDialogkeyboard(String contenido);

    void onDismissDialogkeyboard();

    void onCreateDialogKeyBoard(DialogkeyBoardView view);

    void onClicNuevaRespuestaRespuesta(RespuestaUi respuestaUi);

    void onClicNuevaRespuesta();

    void onClickEditarSubRespuesta(SubRespuestaUi subRespuestaUi);

    void onClickEliminarSubRespuesta(SubRespuestaUi subRespuestaUi);

    void onClickEditarRespuesta(RespuestaUi respuestaUi);

    void onClickEliminarRespuesta(RespuestaUi respuestaUi);

    void onRefresh();
}
