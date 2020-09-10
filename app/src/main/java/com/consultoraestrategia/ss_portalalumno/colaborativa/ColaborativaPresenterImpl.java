package com.consultoraestrategia.ss_portalalumno.colaborativa;

import android.content.res.Resources;
import android.os.Bundle;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaColaborativa;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

import java.util.List;

public class ColaborativaPresenterImpl extends BaseFragmentPresenterImpl<ColaborativaView> implements ColaborativaPresenter {
    private GetListaColaborativa getListaColaborativa;
    private Online online;
    private int cargaCursoId;
    private int sesionAprendizajeId;

    public ColaborativaPresenterImpl(UseCaseHandler handler, Resources res, GetListaColaborativa getListaColaborativa, Online online) {
        super(handler, res);
        this.getListaColaborativa = getListaColaborativa;
        this.online = online;
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(gbSesionAprendizajeUi!=null){
            this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        }
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            this.cargaCursoId = gbCursoUi.getCargaCursoId();
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        showProgress();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                hideProgress();
                getListaColaborativa();
            }
        });
    }

    @Override
    protected String getTag() {
        return "ColaborativaPresenterImpl";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void changeColaborativaList() {
        getListaColaborativa();
    }

    private void getListaColaborativa() {
        List<ColaborativaUi> colaborativaUiList = getListaColaborativa.execute(sesionAprendizajeId);
        if(view!=null)view.setListColaborativa(colaborativaUiList);
    }

    @Override
    public void changeReunionVirtualList() {
        getListaColaborativa();
    }

    @Override
    public void onClickColobarativa(ColaborativaUi colaborativaUi) {
        if (view != null) view.showVinculo(colaborativaUi.getDescripcion());
    }
}
