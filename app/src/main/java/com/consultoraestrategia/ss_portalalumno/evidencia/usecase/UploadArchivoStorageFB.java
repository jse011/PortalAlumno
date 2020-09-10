package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;

import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

public class UploadArchivoStorageFB {
    private EvidenciaRepository repository;

    public UploadArchivoStorageFB(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public StorageCancel execute(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback){
        return repository.uploadStorageFB(cargaCursoId, sesionAprendizajeId, archivoSesEvidenciaUi, new EvidenciaRepository.StorageCallback<ArchivoSesEvidenciaUi>() {
            @Override
            public void onChange(ArchivoSesEvidenciaUi item) {
                callback.onChange(item);
            }

            @Override
            public void onFinish(boolean success, ArchivoSesEvidenciaUi item) {
                callback.onFinish(success, item);
            }

            @Override
            public void onErrorMaxSize() {
                callback.onErrorMaxSize();
            }
        });
    }

    public interface Callback{
        void onChange(ArchivoSesEvidenciaUi archivoSesEvidenciaUi);
        void onFinish(boolean success, ArchivoSesEvidenciaUi archivoSesEvidenciaUi);
        void onErrorMaxSize();
    }
}
