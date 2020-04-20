package com.consultoraestrategia.ss_portalalumno.login2.entities;

import java.util.List;

public class SesionesEnviarUi extends ServiceEnvioUi {
    private List<Integer> sesionesIdList;

    public List<Integer> getSesionesIdList() {
        return sesionesIdList;
    }

    public void setSesionesIdList(List<Integer> sesionesIdList) {
        this.sesionesIdList = sesionesIdList;
    }
}
