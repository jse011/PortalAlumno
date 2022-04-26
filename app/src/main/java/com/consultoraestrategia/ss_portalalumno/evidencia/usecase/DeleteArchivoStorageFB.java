package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;


import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;

public class DeleteArchivoStorageFB {
    private EvidenciaRepository repository;

    public DeleteArchivoStorageFB(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public void execute(int cargaCursoId, int sesionAprendizajeId, ArchivoSesEvidenciaUi archivoSesEvidenciaUi, boolean forzarConexion, Callback callback){
        repository.deleteStorageFB(cargaCursoId, sesionAprendizajeId,archivoSesEvidenciaUi, forzarConexion, new EvidenciaRepository.Callback() {
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
