package com.consultoraestrategia.ss_portalalumno.evidencia.data.source;

import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.EvidenciaSesionUi;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;

import java.util.List;

public interface EvidenciaRepository {

    void updateEvidenciaSesion(int cargaCursoId, int sesionAprendizaje, Callback callback);

    List<ArchivoSesEvidenciaUi> getArchivoSesEvidencias(int sesionAprendiajeId);

    EvidenciaSesionUi isEntregado(int sesionAprendizajeId);

    void entregarSesEvidencia(int cargaCursoId, int sesionAprendizajeId, Callback callback);

    StorageCancel uploadStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, StorageCallback<ArchivoSesEvidenciaUi> tareaArchivoUiStorageCallback);

    void uploadLinkFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback);

    void deleteStorageFB(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback);

    interface Callback{
        void onLoad(boolean success);
    }

    interface StorageCallback<T>  {
        void onChange(T item);
        void onFinish(boolean success, T item);

        void onErrorMaxSize();
    }
}
