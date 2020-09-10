package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;

import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;

public class UpdateEvidenciaSesion {
    private EvidenciaRepository repository;

    public UpdateEvidenciaSesion(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public void  execute(int cargaCursoId, int sesionAprendizaje,  Callback callback){
        repository.updateEvidenciaSesion(cargaCursoId, sesionAprendizaje, new EvidenciaRepository.Callback() {
            @Override
            public void onLoad(boolean success) {
                callback.onLoad(success);
            }
        });
    }

    public interface Callback{
        void onLoad(boolean success);
    }
}
