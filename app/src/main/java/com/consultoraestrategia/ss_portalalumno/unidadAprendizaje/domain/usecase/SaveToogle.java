package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

public class SaveToogle {
    private UnidadAprendizajeRepositorio repositorio;

    public SaveToogle(UnidadAprendizajeRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void excute(UnidadAprendizajeUi unidadAprendizajeUi){
        repositorio.saveToogleUnidad(unidadAprendizajeUi);
    }
}
