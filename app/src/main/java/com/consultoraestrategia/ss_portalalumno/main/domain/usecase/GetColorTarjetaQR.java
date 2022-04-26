package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;

public class GetColorTarjetaQR {
    private MainRepositorio mainRepositorio;

    public GetColorTarjetaQR(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
    }

    public String execute(){
        return mainRepositorio.getColorTarjetaId();
    }
}
