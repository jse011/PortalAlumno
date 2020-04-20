package com.consultoraestrategia.ss_portalalumno.login2.entities;

import java.util.List;

public class CerrarCursoEnviarUi extends ServiceEnvioUi {
    private List<Integer> cargaCursoCalendarioPeriodoIdList;

    public List<Integer> getCargaCursoCalendarioPeriodoIdList() {
        return cargaCursoCalendarioPeriodoIdList;
    }

    public void setCargaCursoCalendarioPeriodoIdList(List<Integer> cargaCursoCalendarioPeriodoIdList) {
        this.cargaCursoCalendarioPeriodoIdList = cargaCursoCalendarioPeriodoIdList;
    }
}
