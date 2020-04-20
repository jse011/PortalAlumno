package com.consultoraestrategia.ss_portalalumno.login2.entities;

import java.util.List;

public class RubroEnviarUi extends ServiceEnvioUi {
    private boolean bimestre;

    private List<String> rubroEvaluacionIdList;

    public List<String> getRubroEvaluacionIdList() {
        return rubroEvaluacionIdList;
    }

    public void setRubroEvaluacionIdList(List<String> rubroEvaluacionIdList) {
        this.rubroEvaluacionIdList = rubroEvaluacionIdList;
    }

    public boolean isBimestre() {
        return bimestre;
    }

    public void setBimestre(boolean bimestre) {
        this.bimestre = bimestre;
    }
}
