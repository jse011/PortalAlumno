package com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase;

import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorio;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;

import java.util.List;

public class GetCalendarioPeriodo {
    private TabCursoRepositorio tabCursoRepositorio;

    public GetCalendarioPeriodo(TabCursoRepositorio tabCursoRepositorio) {
        this.tabCursoRepositorio = tabCursoRepositorio;
    }

    public List<PeriodoUi> execute(int anioAcademicoId, int cargaCursoId, int programaEduId){
        return tabCursoRepositorio.getPerioList(anioAcademicoId, cargaCursoId, programaEduId);
    }
}
