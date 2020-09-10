package com.consultoraestrategia.ss_portalalumno.pregunta.principal;

import android.content.res.Resources;
import android.os.Bundle;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.pregunta.dialog.DialogkeyBoardView;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.EliminarRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.EliminarSubRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetListaRespuestaOffline;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetPregunta;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.SaveRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateBloqueoFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateListRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateSubRespuestaFB;

import java.util.List;

public class PreguntaPrincipalPresenterImpl extends BasePresenterImpl<PreguntaPrincipalView> implements PreguntaPrincipalPresenter {
    private UpdateListRespuestaFB updatelistRespuestaFB;
    private SaveRespuestaFB saveRespuestaFB;
    private UpdateRespuestaFB updateRespuestaFB;
    private UpdateSubRespuestaFB updateSubRespuestaFB;
    private EliminarRespuestaFB eliminarRespuestaFB;
    private EliminarSubRespuestaFB eliminarSubRespuestaFB;
    private UpdateBloqueoFB updateBloqueoFB;// Pendiente asta que lo actulizen el la web
    private GetPregunta getPregunta;
    private int cargaCursosId;
    private int sesionAprendizajeId;
    private String preguntaPaId;
    private PreguntaUi preguntaUi;
    private String color3;
    private String color1;
    private String color2;
    private DialogkeyBoardView dialogkeyBoardView;
    private String selectedPreguntaRespuestaId;
    private RespuestaUi selectedEditarRespuestaUi;
    private FirebaseCancel firebaseUpdateCancel;
    private SubRespuestaUi selectedSubEditarRespuestaUi;
    private GetListaRespuestaOffline getListaRespuestaOffline;
    private Online online;

    public PreguntaPrincipalPresenterImpl(UseCaseHandler handler, Resources res, UpdateListRespuestaFB updatelistRespuestaFB,
                                          GetPregunta getPregunta, SaveRespuestaFB saveRespuestaFB, UpdateRespuestaFB updateRespuestaFB, UpdateSubRespuestaFB updateSubRespuestaFB,
                                          EliminarRespuestaFB eliminarRespuestaFB, EliminarSubRespuestaFB eliminarSubRespuestaFB,GetListaRespuestaOffline getListaRespuestaOffline,
                                          Online online) {
        super(handler, res);
        this.updatelistRespuestaFB = updatelistRespuestaFB;
        this.getPregunta = getPregunta;
        this.saveRespuestaFB = saveRespuestaFB;
        this.updateRespuestaFB = updateRespuestaFB;
        this.updateSubRespuestaFB = updateSubRespuestaFB;
        this.eliminarRespuestaFB = eliminarRespuestaFB;
        this.eliminarSubRespuestaFB = eliminarSubRespuestaFB;
        this.getListaRespuestaOffline = getListaRespuestaOffline;
        this.online = online;
    }

    @Override
    protected String getTag() {
        return "PreguntaPrinciaplPresenterImplTAG";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(view!=null)view.setImgeAlumno(preguntaUi.getFoto());
        if(view!=null)view.setTituloPregunta(preguntaUi.getTitulo(),preguntaUi.getTipoId(), color2);

        if(firebaseUpdateCancel !=null) firebaseUpdateCancel.cancel();
        if(view!=null)view.showProgress();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                getRespuesta(success);
            }
        });

    }

    private void getRespuesta(boolean success) {

        if(success){
            if(view!=null)view.hideProgress();
            if(view!=null)view.modoOnline();
            if(view!=null)view.clearListRespuesta();
            firebaseUpdateCancel = updatelistRespuestaFB.excute(cargaCursosId, sesionAprendizajeId, preguntaPaId, new UpdateListRespuestaFB.Callback() {
                @Override
                public void addRespuesta(RespuestaUi respuestaUi) {
                    respuestaUi.setFoto2(preguntaUi.getFoto());
                    respuestaUi.setColor1(color1);
                    respuestaUi.setColor2(color2);
                    if(view!=null)view.updateRespuesta(respuestaUi);
                }

                @Override
                public void updateRespuesta(RespuestaUi respuestaUi) {
                    respuestaUi.setFoto2(preguntaUi.getFoto());
                    respuestaUi.setColor1(color1);
                    respuestaUi.setColor2(color2);
                    if(view!=null)view.updateRespuesta(respuestaUi);
                }

                @Override
                public void removeRespuesta(String preguntaRespuestaId) {
                    if(view!=null)view.removePregunta(preguntaRespuestaId);
                }
            });

        }else {
            if(view!=null)view.modoOffline(color2);
            List<RespuestaUi> respuestaUiList = getListaRespuestaOffline.execute(preguntaPaId);
            for (RespuestaUi respuestaUi : respuestaUiList){
                respuestaUi.setOffline(true);
                respuestaUi.setColor2(color2);
                respuestaUi.setColor1(color1);
            }
            if(view!=null)view.hideProgress();
            if(view!=null)view.setListRespuesta(respuestaUiList);
        }
    }



    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
          this.cargaCursosId =  gbCursoUi.getCargaCursoId();
          this.color1 = gbCursoUi.getParametroDisenioColor1();
          this.color2 = gbCursoUi.getParametroDisenioColor2();
          this.color3 = gbCursoUi.getParametroDisenioColor3();
        }
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        this.sesionAprendizajeId = gbSesionAprendizajeUi!=null?gbSesionAprendizajeUi.getSesionAprendizajeId():0;
        this.preguntaPaId = gbSesionAprendizajeUi!=null?gbSesionAprendizajeUi.getPreguntaPaId():"";
        this.preguntaUi = getPregunta.execute(preguntaPaId);
    }

    @Override
    public void onClickResponder(RespuestaUi respuestaUi) {
        if(!respuestaUi.getSubrecursoList().isEmpty()){
            respuestaUi.setResponder(true);
        }else {
            respuestaUi.setResponder(!respuestaUi.isResponder());
        }

        if(view!=null)view.updateRespuesta(respuestaUi);
    }

    @Override
    public void onResponder() {

    }

    @Override
    public void onClickAceptarDialogkeyboard(String contenido) {
        if(contenido.isEmpty()){
            if(dialogkeyBoardView!=null)dialogkeyBoardView.errorText();
            return;
        }
        if(selectedEditarRespuestaUi==null&&selectedSubEditarRespuestaUi==null){
            if(view!=null)view.showProgress();
            saveRespuestaFB.execute(cargaCursosId, sesionAprendizajeId, preguntaPaId, selectedPreguntaRespuestaId, contenido, new SaveRespuestaFB.Callback() {
                @Override
                public void onPreLoad(RespuestaUi respuestaUi) {
                    respuestaUi.setColor1(color1);
                    respuestaUi.setColor2(color2);
                    if(view!=null)view.addRespuesta(respuestaUi);
                }

                @Override
                public void onPreLoad(SubRespuestaUi subRespuestaUi) {
                    if(view!=null)view.addSubRespuesta(subRespuestaUi);
                }

                @Override
                public void onLoad(boolean success) {
                    if(view!=null)view.hideProgress();
                }
            });
        }else if(selectedEditarRespuestaUi !=null){
            if(view!=null)view.showProgress();
            selectedEditarRespuestaUi.setContenido(contenido);
            updateRespuestaFB.execute(cargaCursosId, sesionAprendizajeId,preguntaPaId, selectedEditarRespuestaUi, success -> {
                if(view!=null)view.hideProgress();                });
        }else {
            if(view!=null)view.showProgress();
            selectedSubEditarRespuestaUi.setContenido(contenido);
            updateSubRespuestaFB.execute(cargaCursosId, sesionAprendizajeId,preguntaPaId, selectedSubEditarRespuestaUi, success -> {
                if(view!=null)view.hideProgress();                });
        }

        if(dialogkeyBoardView!=null)dialogkeyBoardView.dialogDissmit();
    }

    @Override
    public void onCreateDialogKeyBoard(DialogkeyBoardView view) {
        this.dialogkeyBoardView = view;
        this.dialogkeyBoardView.setColorButons(color1, color2);
        if(selectedEditarRespuestaUi!=null){
            this.dialogkeyBoardView.setRespuesta(selectedEditarRespuestaUi.getContenido());
        }else if(selectedSubEditarRespuestaUi!=null){
            this.dialogkeyBoardView.setRespuesta(selectedSubEditarRespuestaUi.getContenido());
        }
    }

    @Override
    public void onClicNuevaRespuestaRespuesta(RespuestaUi respuestaUi) {
        selectedPreguntaRespuestaId = respuestaUi.getId();
        selectedEditarRespuestaUi = null;
        selectedSubEditarRespuestaUi= null;
        if(view!=null)view.showDialogKey();
    }

    @Override
    public void onClicNuevaRespuesta() {
        selectedPreguntaRespuestaId = null;
        selectedEditarRespuestaUi = null;
        selectedSubEditarRespuestaUi= null;
        if(view!=null)view.showDialogKey();
    }

    @Override
    public void onClickEditarSubRespuesta(SubRespuestaUi subRespuestaUi) {
        selectedPreguntaRespuestaId=null;
        selectedEditarRespuestaUi = null;
        selectedSubEditarRespuestaUi = subRespuestaUi;
        if(view!=null)view.showDialogKey();
    }

    @Override
    public void onClickEliminarSubRespuesta(SubRespuestaUi subRespuestaUi) {
        if(view!=null)view.showProgress();
        eliminarSubRespuestaFB.execute(cargaCursosId, sesionAprendizajeId,preguntaPaId, subRespuestaUi, success -> {
            if(view!=null)view.hideProgress();                });
    }

    @Override
    public void onClickEditarRespuesta(RespuestaUi respuestaUi) {
        selectedPreguntaRespuestaId=null;
        selectedEditarRespuestaUi = respuestaUi;
        selectedSubEditarRespuestaUi = null;
        if(view!=null)view.showDialogKey();
    }

    @Override
    public void onClickEliminarRespuesta(RespuestaUi respuestaUi) {
        if(view!=null)view.showProgress();
        eliminarRespuestaFB.execute(cargaCursosId, sesionAprendizajeId,preguntaPaId, respuestaUi, success -> {
            if(view!=null)view.hideProgress();                });
    }

    @Override
    public void onRefresh() {
        if(firebaseUpdateCancel !=null) firebaseUpdateCancel.cancel();
        if(view!=null)view.showProgress();
        online.restarOnline(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                getRespuesta(success);
            }
        });
    }

    @Override
    public void onDismissDialogkeyboard() {
        dialogkeyBoardView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dialogkeyBoardView = null;
        if(firebaseUpdateCancel !=null) firebaseUpdateCancel.cancel();
    }
}
