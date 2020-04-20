package com.consultoraestrategia.ss_portalalumno.actividades.ui;

import com.consultoraestrategia.ss_portalalumno.actividades.ActividadesPresenter;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

import java.util.List;

/**
 * Created by kike on 07/02/2018.
 */

public interface ActividadesView extends BaseView<ActividadesPresenter> {

    void showProgress();

    void hideProgress();

    void showListObject(List<Object> objectList);

    void addActividades(Object item);

    void clearActividades();

    void mostrarMensaje(String mensaje);

    void showMessage();

    void hideMessage();


    void showVinculo(String url);

    void showYoutube(String url);

    void leerArchivo(String path);

    void setUpdate(RecursosUi repositorioFileUi);

    void setUpdateProgress(RecursosUi repositorioFileUi, int count);
}
