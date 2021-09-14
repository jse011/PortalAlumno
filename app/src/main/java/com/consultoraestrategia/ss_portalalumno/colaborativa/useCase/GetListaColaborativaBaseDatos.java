package com.consultoraestrategia.ss_portalalumno.colaborativa.useCase;

import com.consultoraestrategia.ss_portalalumno.colaborativa.data.source.ColaborativaRepositorio;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.List;

public class GetListaColaborativaBaseDatos {
    private ColaborativaRepositorio colaborativaRepositorio;

    public GetListaColaborativaBaseDatos(ColaborativaRepositorio colaborativaRepositorio) {
        this.colaborativaRepositorio = colaborativaRepositorio;
    }

    public List<ColaborativaUi> execute(int sesionAprendizajeId, int entidadId, int georeferenciaId){
        return colaborativaRepositorio.geListaColobaorativaBaseDatos(sesionAprendizajeId, entidadId, georeferenciaId);
    }
}
