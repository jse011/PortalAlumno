package com.consultoraestrategia.ss_portalalumno.colaborativa;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaColaborativa;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaColaborativaBaseDatos;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaGrabaciones;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

import java.util.List;

public class ColaborativaPresenterImpl extends BaseFragmentPresenterImpl<ColaborativaView> implements ColaborativaPresenter {
    private GetListaGrabaciones getListaGrabaciones;
    private GetListaColaborativa getListaColaborativa;
    private GetListaColaborativaBaseDatos getListaColaborativaBaseDatos;
    private Online online;
    private int cargaCursoId;
    private int sesionAprendizajeId;
    private int georeferenciaId;
    private int entidadId;
    private int personaId;

    public ColaborativaPresenterImpl(UseCaseHandler handler, Resources res, GetListaColaborativa getListaColaborativa, GetListaColaborativaBaseDatos getListaColaborativaBaseDatos, GetListaGrabaciones getListaGrabaciones, Online online) {
        super(handler, res);
        this.getListaColaborativa = getListaColaborativa;
        this.getListaColaborativaBaseDatos = getListaColaborativaBaseDatos;
        this.getListaGrabaciones = getListaGrabaciones;
        this.online = online;
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        this.georeferenciaId = iCRMEdu.variblesGlobales.getGeoreferenciaId();
        this.personaId = iCRMEdu.variblesGlobales.getPersonaId();
        this.entidadId = iCRMEdu.variblesGlobales.getEntidadId();
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

    @Override
    public void changeGrabacionesSalaVirtualList() {
        List<ColaborativaUi> colaborativaUiList = getListaGrabaciones.execute(sesionAprendizajeId);
        if(view!=null)view.setListGrabacionesColaborativa(colaborativaUiList);
    }

    @Override
    public void changeReunionVirtualBaseDatosList() {
        Log.d(getTag(), "changeReunionVirtualBaseDatosList");
        List<ColaborativaUi> colaborativaUiList = getListaColaborativaBaseDatos.execute(sesionAprendizajeId, entidadId, georeferenciaId);
        Log.d(getTag(), "changeReunionVirtualBaseDatosList "+colaborativaUiList.size());
        if(view!=null)view.setListColaborativaServidor(colaborativaUiList);
    }

}
