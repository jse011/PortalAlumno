package com.consultoraestrategia.ss_portalalumno.pregunta.lista;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetListPregunta;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetPreguntaEvaluacion;

import java.util.List;

public class PreguntaPresenteImpl extends BaseFragmentPresenterImpl<PreguntaView> implements PreguntaPresenter{

    private GetPreguntaEvaluacion getPreguntaEvaluacion;
    private GetListPregunta getListPregunta;
    private Online online;
    private String color1;
    private String color2;
    private String color3;
    private int sesionAprendizajeId;

    public PreguntaPresenteImpl(UseCaseHandler handler, Resources res, Online online, GetPreguntaEvaluacion getPreguntaEvaluacion, GetListPregunta getListPregunta) {
        super(handler, res);
        this.getPreguntaEvaluacion = getPreguntaEvaluacion;
        this.online = online;
        this.getListPregunta = getListPregunta;
    }

    @Override
    protected String getTag() {
        return "PreguntaPresenteImplTAG";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            color1 = gbCursoUi.getParametroDisenioColor1();
            color2 = gbCursoUi.getParametroDisenioColor2();
            color3 = gbCursoUi.getParametroDisenioColor3();
        }
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(gbSesionAprendizajeUi!=null){
            this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void addPreguntaFirebase(String preguntaPAId) {
        PreguntaUi preguntaUi = getPreguntaEvaluacion.execute(preguntaPAId);
        preguntaUi.setColor(color2);
        if(view!=null)view.updatePreguntaListView(preguntaUi);
    }

    @Override
    public void updatePreguntaFirebase(String preguntaPAId) {
        PreguntaUi preguntaUi = getPreguntaEvaluacion.execute(preguntaPAId);
        preguntaUi.setColor(color2);
        if(view!=null)view.updatePreguntaListView(preguntaUi);
    }

    @Override
    public void removePreguntaFirebase(String preguntaPAId) {
        PreguntaUi preguntaUi = getPreguntaEvaluacion.execute(preguntaPAId);
        preguntaUi.setColor(color2);
        if(view!=null)view.removePreguntaListView(preguntaUi);
    }

    @Override
    public void updatePreguntaAlumno(String preguntaPAId, int alumnoId) {

    }

    @Override
    public void onInitPreguntaListFirebase(boolean online) {
        if(view!=null)view.hideProgress();
        if(online){
            if(view!=null)view.clearAlumnoList();
        }else {
            List<PreguntaUi> preguntaUiList = getListPregunta.execute(sesionAprendizajeId);
            for (PreguntaUi preguntaUi : preguntaUiList){
                preguntaUi.setColor(color2);
                preguntaUi.setOffline(true);
            }
            if(view!=null)view.setListPregunta(preguntaUiList);
        }
        Log.d(getTag(), "online :" + online);
    }

    @Override
    public void onClickPregunta(PreguntaUi preguntaUi) {
        if(preguntaUi.getBloqueado()==0){
            Log.d(getTag(), "showActivityPreguntaPrincial");
            GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
            if(gbSesionAprendizajeUi!=null){
                gbSesionAprendizajeUi.setPreguntaPaId(preguntaUi.getId());
            }
            if(view!=null)view.showActivityPreguntaPrincial();
        }else {
            if(view!=null)view.showMessage("La preguntá esta bloqueada");
        }

    }
}
