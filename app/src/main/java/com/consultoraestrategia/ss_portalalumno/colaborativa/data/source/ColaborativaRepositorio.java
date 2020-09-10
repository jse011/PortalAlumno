package com.consultoraestrategia.ss_portalalumno.colaborativa.data.source;

import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.List;

public interface ColaborativaRepositorio {
    List<ColaborativaUi> geListaColobaorativa(int sesionAprendizajeId);
}
