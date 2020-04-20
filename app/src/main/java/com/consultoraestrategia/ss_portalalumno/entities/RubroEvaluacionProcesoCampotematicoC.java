package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by SCIEV on 23/01/2018.
 */
@Table(database = AppDatabase.class)
public class RubroEvaluacionProcesoCampotematicoC extends BaseRelEntity {
    @PrimaryKey
    private String rubroEvalProcesoId;
    @PrimaryKey
    private int campoTematicoId;

    public RubroEvaluacionProcesoCampotematicoC() {
    }

    public String getRubroEvalProcesoId() {
        return rubroEvalProcesoId;
    }

    public void setRubroEvalProcesoId(String rubroEvalProcesoId) {
        this.rubroEvalProcesoId = rubroEvalProcesoId;
    }

    public int getCampoTematicoId() {
        return campoTematicoId;
    }

    public void setCampoTematicoId(int campoTematicoId) {
        this.campoTematicoId = campoTematicoId;
    }
}

