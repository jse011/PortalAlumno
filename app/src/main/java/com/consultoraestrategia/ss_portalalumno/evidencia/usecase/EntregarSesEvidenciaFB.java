package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;

import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.EntregarTareaFB;

public class EntregarSesEvidenciaFB {
    private EvidenciaRepository repository;

    public EntregarSesEvidenciaFB(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, boolean forzarConexion, EntregarTareaFB.Callback callback){
        repository.entregarSesEvidencia(cargaCursoId, sesionAprendizajeId,forzarConexion, new EvidenciaRepository.Callback() {
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
