package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;

public class GetNombreServidor {
    private MainRepositorio mainRepositorio;

    public GetNombreServidor(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
    }

    public String execute(){
        return mainRepositorio.getNombreServidor();
    }
}
