package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public class GetUnidadAprendizajeList {
    private UnidadAprendizajeRepositorio unidadAprendizajeRepositorio;

    public GetUnidadAprendizajeList(UnidadAprendizajeRepositorio unidadAprendizajeRepositorio) {
        this.unidadAprendizajeRepositorio = unidadAprendizajeRepositorio;
    }

    public List<UnidadAprendizajeUi> execute(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId){
        return this.unidadAprendizajeRepositorio.getUnidadesList(idCargaCurso, idCalendarioPeriodo, idAnioAcademico, planCursoId);
    }
}
