package com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;

import java.util.List;

public interface TabCursoRepositorio {
    List<PeriodoUi> getPerioList(int anioAcademicoId, int cargaCursoId, int programaEduId);
}
