package com.consultoraestrategia.ss_portalalumno.evidencia;

import android.net.Uri;

import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenter;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;

import java.util.ArrayList;

interface EvidenciaPresenter extends BaseFragmentPresenter<EvidenciaView> {
    void notifyChangeFragment();

    void onBtnEntregarClicked();

    void onClickOpenArchivo(ArchivoSesEvidenciaUi evidenciaUi);

    void onClickRemoverchivo(ArchivoSesEvidenciaUi evidenciaUi);

    void onClickActionArchivo(ArchivoSesEvidenciaUi evidenciaUi);

    void onResultCamara(ArrayList<String> returnValue);

    void onResultDoc(Uri uri, String nombre);

    void onClickAddFile();

    void onClickCamera();

    void onClickAddLink(String desripcion, String vinculo);
}
