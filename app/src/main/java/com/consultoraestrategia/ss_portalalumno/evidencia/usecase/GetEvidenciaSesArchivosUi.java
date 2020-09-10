package com.consultoraestrategia.ss_portalalumno.evidencia.usecase;

import com.consultoraestrategia.ss_portalalumno.evidencia.data.source.EvidenciaRepository;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;

import java.util.List;

public class GetEvidenciaSesArchivosUi {
    private EvidenciaRepository repository;

    public GetEvidenciaSesArchivosUi(EvidenciaRepository repository) {
        this.repository = repository;
    }

    public List<ArchivoSesEvidenciaUi> execute(int sesionAprendiajeId){
        return repository.getArchivoSesEvidencias(sesionAprendiajeId);
    }
}
