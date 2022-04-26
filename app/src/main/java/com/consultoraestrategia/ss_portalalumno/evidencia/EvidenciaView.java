package com.consultoraestrategia.ss_portalalumno.evidencia;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;

import java.util.ArrayList;
import java.util.List;

interface EvidenciaView extends BaseView<EvidenciaPresenter> {
    void setListEvidencia(List<ArchivoSesEvidenciaUi> archivoSesEvidenciaUiList);

    void changeFechaEntrega(boolean entregaAlumno, boolean retrasoEntrega);

    void update(ArchivoSesEvidenciaUi evidenciaSesionUi);

    void diabledButtons();

    void openCamera(ArrayList<String> urls);

    void addTareaArchivo(ArchivoSesEvidenciaUi archivoSesEvidenciaUi);

    void remove(ArchivoSesEvidenciaUi tareaArchivoUi);

    void onShowPickDoc();

    void showPreviewArchivo();

    void showVinculo(String path);

    void setTema(String color1, String color2, String color3);

    void openGalery();

    void openRecordVideo();
}
