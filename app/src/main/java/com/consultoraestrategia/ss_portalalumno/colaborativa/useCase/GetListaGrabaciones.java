package com.consultoraestrategia.ss_portalalumno.colaborativa.useCase;

import com.consultoraestrategia.ss_portalalumno.colaborativa.data.source.ColaborativaRepositorio;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.List;

public class GetListaGrabaciones {
    private ColaborativaRepositorio colaborativaRepositorio;

    public GetListaGrabaciones(ColaborativaRepositorio colaborativaRepositorio) {
        this.colaborativaRepositorio = colaborativaRepositorio;
    }

    public List<ColaborativaUi> execute(int sesionAprendizajeId){
        return colaborativaRepositorio.getListGrabaciones(sesionAprendizajeId);
    }
}
