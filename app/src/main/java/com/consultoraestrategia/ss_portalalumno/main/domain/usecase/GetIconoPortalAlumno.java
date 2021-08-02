package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;

public class GetIconoPortalAlumno {
    private MainRepositorio repositorio;

    public GetIconoPortalAlumno(MainRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public String execute(){
        return repositorio.getIconoPortalAlumno();
    }
}
