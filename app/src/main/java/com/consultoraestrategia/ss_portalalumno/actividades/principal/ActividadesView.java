package com.consultoraestrategia.ss_portalalumno.actividades.principal;

import com.consultoraestrategia.ss_portalalumno.actividades.principal.ActividadesPresenter;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
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

    void mostrarMensaje(String mensaje);

    void showMessage();

    void hideMessage();


    void showVinculo(String url);

    void showYoutube(String url, String nombreRecurso);

    void leerArchivo(String path);

    void setUpdate(RecursosUi repositorioFileUi);

    void setUpdateProgress(RecursosUi repositorioFileUi, int count);

    void changeNotifyList();

    void updateActividadList(ActividadesUi actividadesUi);

    void changeColorActividadList(String color);

    void showPreviewArchivo(String driveId, String nombreRecurso);

    void showMultimediaPlayer(String driveId, String nombreRecurso);
}
