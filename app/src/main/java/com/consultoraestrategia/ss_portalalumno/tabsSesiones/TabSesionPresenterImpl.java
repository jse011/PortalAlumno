package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

public class TabSesionPresenterImpl extends BasePresenterImpl<TabSesionView> implements TabSesionPresenter {
    public TabSesionPresenterImpl(UseCaseHandler handler, Resources res) {
        super(handler, res);
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupData();
    }

    private void setupData() {
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(view!=null)view.setNombreSesion(gbSesionAprendizajeUi.getNombreApredizaje());
        if(view!=null)view.setNumeroSession(gbSesionAprendizajeUi.getNumero());
    }

    @Override
    protected String getTag() {
        return "TabSesionPresenterImpl";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }
}
