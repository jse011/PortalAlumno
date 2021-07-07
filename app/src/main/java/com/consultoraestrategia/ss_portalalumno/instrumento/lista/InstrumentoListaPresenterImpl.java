package com.consultoraestrategia.ss_portalalumno.instrumento.lista;

import android.content.res.Resources;
import android.os.Bundle;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumentoList;

import java.util.ArrayList;
import java.util.List;

public class InstrumentoListaPresenterImpl extends BaseFragmentPresenterImpl<InstrumentoListaView> implements InstrumentoListaPresenter {
    private GetInstrumentoList getInstrumentoList;
    private List<InstrumentoUi> instrumentoUiList = new ArrayList<>();
    private int anioAcademicoId;
    private int programaEducativoId;
    private int idCalendarioPeriodo;
    private int idCargaCurso;
    private int idCurso;
    private int planCursoId;
    private int sesionAprendizajeId;
    private String color1;
    private String color2;
    private String color3;
    private boolean initFragment;
    private Online online;

    public InstrumentoListaPresenterImpl(UseCaseHandler handler, Resources res, GetInstrumentoList getInstrumentoList, Online online) {
        super(handler, res);
        this.getInstrumentoList = getInstrumentoList;
        this.online = online;
    }

    @Override
    protected String getTag() {
        return InstrumentoListaPresenterImpl.class.getSimpleName();
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
        setData();
        showProgress();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(!success){
                    getInstrumentoList();
                }
                hideProgress();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(initFragment){
            getInstrumentoList();
        }
        initFragment=true;
    }

    private void getInstrumentoList(){
        instrumentoUiList.clear();
        for (InstrumentoUi instrumentoUi : getInstrumentoList.execute(sesionAprendizajeId)){
            instrumentoUi.setColor(color1);
            instrumentoUi.setColor2(color2);
            instrumentoUiList.add(instrumentoUi);
        }
        if(view!=null)view.showListInstrumento(instrumentoUiList);
        hideProgress();
    }

    private void setData() {
        this.anioAcademicoId = iCRMEdu.variblesGlobales.getAnioAcademicoId();
        this.programaEducativoId = iCRMEdu.variblesGlobales.getProgramEducativoId();
        GbCalendarioPerioUi gbCalendarioPerioUi = iCRMEdu.variblesGlobales.getGbCalendarioPerioUi();
        this.idCalendarioPeriodo = gbCalendarioPerioUi.getCalendarioPeriodoId();
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        this.idCargaCurso = gbCursoUi.getCargaCursoId();
        this.idCurso = gbCursoUi.getCursoId();
        this.planCursoId = gbCursoUi.getPlanCursoId();

        this.color1 = gbCursoUi.getParametroDisenioColor1();
        this.color2 = gbCursoUi.getParametroDisenioColor2();
        this.color3 = gbCursoUi.getParametroDisenioColor3();

        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();

    }

    @Override
    public void onClick(InstrumentoUi instrumentoUi) {
        if(instrumentoUi.getCantidadPregunta()
                >instrumentoUi.getCantidadPreguntaResueltas()){
            iCRMEdu.variblesGlobales.setInstrumentoId(instrumentoUi.getInstrumentoEvalId());
            if(view!=null)view.showActivityInstrumento();
        }else {
            if(view!=null)view.showMessage("La evaluación ya fue completada");
        }

    }

    @Override
    public void notifyChangeFragment() {
        setData();
        getInstrumentoList();
    }
}
