package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

public interface UnidadAprendizajeRepositorio {
    public interface Callback<T>{
        void onLoad(boolean success, T item);
    }
    List<UnidadAprendizajeUi> getUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId);
    void getFirebaseUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int plancursoId, Callback<List<UnidadAprendizajeUi>> callback);
}
