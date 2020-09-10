package com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;

import java.util.List;

public interface TabCursoRepositorio {
    List<PeriodoUi> getPerioList(int anioAcademicoId, int cargaCursoId, int programaEduId);

    void updateFirebasePersonaList(int idCargaCurso, Callback callback);

    interface Callback{
        void onLoad(boolean success);
    }

    void updateFirebaseUnidadesList(int idCargaCurso, int idCalendarioPeriodo, Callback callback);
}
