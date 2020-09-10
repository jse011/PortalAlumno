package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.GetUnidadAprendizajeList;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.SaveToogle;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.UpdateFireBaseUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.ArrayList;
import java.util.List;

public class UnidadAprendizajePresenterImpl extends BaseFragmentPresenterImpl<UnidadAprendizajeView> implements UnidadAprendizajePresenter {
    private Online online;
    private GetUnidadAprendizajeList getUnidadAprendizajeList;
    private UpdateFireBaseUnidadAprendizaje updateFireBaseUnidadAprendizaje;
    private SaveToogle saveToogle;
    private int cargaCursoId;
    private int anioAcademicoId;
    private int planCursoId;
    private int calendarioPeriodoId;
    private String color1;
    private String color2;
    private String color3;
    private List<UnidadAprendizajeUi> unidadAprendizajeUiList = new ArrayList<>();

    public UnidadAprendizajePresenterImpl(UseCaseHandler handler, Resources res, Online online, GetUnidadAprendizajeList getUnidadAprendizajeList, UpdateFireBaseUnidadAprendizaje updateFireBaseUnidadAprendizaje,
                                          SaveToogle saveToogle) {
        super(handler, res);
        this.online = online;
        this.getUnidadAprendizajeList = getUnidadAprendizajeList;
        this.updateFireBaseUnidadAprendizaje = updateFireBaseUnidadAprendizaje;
        this.saveToogle = saveToogle;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        online.online(success -> {
            if(!success) getListUnidades();
        });
    }

    private void getListUnidades(){
        unidadAprendizajeUiList.clear();
        unidadAprendizajeUiList.addAll(getUnidadAprendizajeList.execute(cargaCursoId, calendarioPeriodoId, anioAcademicoId, planCursoId));
        int columnas = 0;//3
        if(view!=null)columnas = view.getColumnasSesionesList();
        Log.d(getTag(),"columnas: " + columnas);
        for (UnidadAprendizajeUi unidadAprendizajeUi: unidadAprendizajeUiList){
            List<SesionAprendizajeUi> sesionAprendizajeUiList = unidadAprendizajeUi.getObjectListSesiones();

            if(sesionAprendizajeUiList==null||columnas >= sesionAprendizajeUiList.size()){
                unidadAprendizajeUi.setVisibleVerMas(false);
            } else {
                unidadAprendizajeUi.setVisibleVerMas(true);
            }
        }
        if(view!=null)view.hideProgress();
        if(view!=null)view.showListUnidadAprendizaje(unidadAprendizajeUiList, color1);
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        setupData();
    }

    private void setupData() {
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        this.cargaCursoId = gbCursoUi.getCargaCursoId();
        this.anioAcademicoId = iCRMEdu.variblesGlobales.getAnioAcademicoId();
        this.color1 = gbCursoUi.getParametroDisenioColor1();
        this.color2 = gbCursoUi.getParametroDisenioColor2();
        this.color3 = gbCursoUi.getParametroDisenioColor3();
        this.planCursoId = gbCursoUi.getPlanCursoId();
        GbCalendarioPerioUi gbCalendarioPerioUi = iCRMEdu.variblesGlobales.getGbCalendarioPerioUi();
        this.calendarioPeriodoId = gbCalendarioPerioUi.getCalendarioPeriodoId();
    }

    @Override
    protected String getTag() {
        return "UnidadAprendizajePresenterImpl";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onClickUnidadAprendizaje(UnidadAprendizajeUi unidadAprendizajeUi) {
        if(unidadAprendizajeUi.isToogle()){
            unidadAprendizajeUi.setToogle(false);
        }else {
            unidadAprendizajeUi.setToogle(true);
        }
        if(view !=null) view.updateItem(unidadAprendizajeUi);
        saveToogle.excute(unidadAprendizajeUi);
    }

    @Override
    public void onClickSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi) {
        GbSesionAprendizajeUi gbSesionAprendizajeUi = new GbSesionAprendizajeUi();
        gbSesionAprendizajeUi.setSesionAprendizajeId(sesionAprendizajeUi.getSesionAprendizajeId());
        gbSesionAprendizajeUi.setNombreApredizaje(sesionAprendizajeUi.getTitulo());
        gbSesionAprendizajeUi.setNumero(sesionAprendizajeUi.getNroSesion());
        iCRMEdu.variblesGlobales.setGbSesionAprendizajeUi(gbSesionAprendizajeUi);
        if(view!=null)view.showTabSesionAprendizaje(sesionAprendizajeUi);
    }

    @Override
    public void notifyChangeFragment(boolean finishUpdateUnidadFb) {
        setupData();
        showProgress();
        online.online(success -> {
            if(success&&finishUpdateUnidadFb){
                updateFireBaseUnidadAprendizaje.execute(cargaCursoId, calendarioPeriodoId, anioAcademicoId, planCursoId, unidadAprendizajeUiList,new UpdateFireBaseUnidadAprendizaje.CallBack() {
                    @Override
                    public void onSucces() {
                        hideProgress();
                        getListUnidades();
                        if(unidadAprendizajeUiList.size()==0){
                            if (view!=null)view.showMensajeListaVacia();
                        }else {
                            if (view!=null)view.hideMensajeListaVacia();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        hideProgress();
                    }
                });
            }else {
                hideProgress();
                getListUnidades();
                if(unidadAprendizajeUiList.size()==0){
                    if (view!=null)view.showMensajeListaVacia();
                }else {
                    if (view!=null)view.hideMensajeListaVacia();
                }
            }
        });
    }
}
