package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public interface UnidadAprendizajeRepositorio {
    interface Callback{
        void onLoad(boolean success);
    }
    List<UnidadAprendizajeUi> getUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId);
    void updateFirebaseUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int plancursoId, List<UnidadAprendizajeUi> unidadAprendizajeUiList, Callback callback);
    void saveToogleUnidad(UnidadAprendizajeUi unidadAprendizajeUi);
}
