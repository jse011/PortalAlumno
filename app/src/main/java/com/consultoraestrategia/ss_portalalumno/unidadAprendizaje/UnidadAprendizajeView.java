package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public interface UnidadAprendizajeView extends BaseView<UnidadAprendizajePresenter> {

    void showListUnidadAprendizaje(List<UnidadAprendizajeUi> unidadAprendizajeUiList, String color);

    int getColumnasSesionesList();

    void updateItem(UnidadAprendizajeUi unidadAprendizajeUi);

    void showTabSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi);

    void showMensajeListaVacia();

    void hideMensajeListaVacia();
}
