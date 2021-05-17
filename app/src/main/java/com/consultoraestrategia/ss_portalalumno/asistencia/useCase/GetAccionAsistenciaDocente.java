package com.consultoraestrategia.ss_portalalumno.asistencia.useCase;

import com.consultoraestrategia.ss_portalalumno.asistencia.repositorio.AsistenciaRepository;

public class GetAccionAsistenciaDocente {
    AsistenciaRepository asistenciaRepository;

    public GetAccionAsistenciaDocente(AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;
    }


}
