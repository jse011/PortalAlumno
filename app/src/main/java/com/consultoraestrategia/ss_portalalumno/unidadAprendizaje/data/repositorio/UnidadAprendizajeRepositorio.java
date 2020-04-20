package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public interface UnidadAprendizajeRepositorio {
    List<UnidadAprendizajeUi> getUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId);
}
