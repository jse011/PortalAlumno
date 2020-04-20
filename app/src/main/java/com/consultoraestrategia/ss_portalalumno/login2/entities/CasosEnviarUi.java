package com.consultoraestrategia.ss_portalalumno.login2.entities;

import java.util.List;

public class CasosEnviarUi extends ServiceEnvioUi {
    private List<String> casosIdList;

    public List<String> getCasosIdList() {
        return casosIdList;
    }

    public void setCasosIdList(List<String> casosIdList) {
        this.casosIdList = casosIdList;
    }
}

