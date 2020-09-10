package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;


import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;

public class UploadLinkFB {
    private EvidenciaRepository repository;

    public UploadLinkFB(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Callback callback){
        repository.uploadLinkFB(cargaCursoId, sesionAprendizajeId, archivoSesEvidenciaUi, new EvidenciaRepository.Callback() {
            @Override
            public void onLoad(boolean success) {
                callback.onFinish(success);
            }
        });

    }

    public interface Callback{
        void onFinish(boolean success);
    }
}
