package com.consultoraestrategia.ss_portalalumno.colaborativa.useCase;

import com.consultoraestrategia.ss_portalalumno.colaborativa.data.source.ColaborativaRepositorio;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.List;

public class GetListaColaborativa {
    private ColaborativaRepositorio colaborativaRepositorio;

    public GetListaColaborativa(ColaborativaRepositorio colaborativaRepositorio) {
        this.colaborativaRepositorio = colaborativaRepositorio;
    }

    public List<ColaborativaUi> execute(int sesionAprendizajeId){
        return colaborativaRepositorio.geListaColobaorativa(sesionAprendizajeId);
    }
}
