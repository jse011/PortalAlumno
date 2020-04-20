package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 28/02/2018.
 */
@Table(database = AppDatabase.class)
public class T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO extends BaseModel {
    @PrimaryKey
    private int unidadCompetenciaDesempenioIcdId;
    @PrimaryKey
    private int campoTematicoIcd;

    public T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO() {
    }

    public int getUnidadCompetenciaDesempenioIcdId() {
        return unidadCompetenciaDesempenioIcdId;
    }

    public void setUnidadCompetenciaDesempenioIcdId(int unidadCompetenciaDesempenioIcdId) {
        this.unidadCompetenciaDesempenioIcdId = unidadCompetenciaDesempenioIcdId;
    }

    public int getCampoTematicoIcd() {
        return campoTematicoIcd;
    }

    public void setCampoTematicoIcd(int campoTematicoIcd) {
        this.campoTematicoIcd = campoTematicoIcd;
    }
}
