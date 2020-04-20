package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 28/02/2018.
 */
@Table(database = AppDatabase.class)
public class T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD extends BaseModel {
    @PrimaryKey
    @Column
    private int unidadCompetenciaDesempenioIcdId;
    @Column
    private int unidadCompetenciaId;
    @Column
    private int desempenioIcdId;

    public T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD() {
    }

    public int getUnidadCompetenciaDesempenioIcdId() {
        return unidadCompetenciaDesempenioIcdId;
    }

    public void setUnidadCompetenciaDesempenioIcdId(int unidadCompetenciaDesempenioIcdId) {
        this.unidadCompetenciaDesempenioIcdId = unidadCompetenciaDesempenioIcdId;
    }

    public int getUnidadCompetenciaId() {
        return unidadCompetenciaId;
    }

    public void setUnidadCompetenciaId(int unidadCompetenciaId) {
        this.unidadCompetenciaId = unidadCompetenciaId;
    }

    public int getDesempenioIcdId() {
        return desempenioIcdId;
    }

    public void setDesempenioIcdId(int desempenioIcdId) {
        this.desempenioIcdId = desempenioIcdId;
    }
}
