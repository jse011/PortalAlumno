package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 27/07/2017.
 */
@Table(database = AppDatabase.class)
public class CompetenciaUnidad extends BaseModel {
    @Column
    @PrimaryKey
    private int unidadCompetenciaId;
    @Column
    private int competenciaId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int unidadAprendizajeId;

    public CompetenciaUnidad() {
    }

    public CompetenciaUnidad(int competenciaId, int sesionAprendizajeId, int unidadAprendizajeId) {
        this.competenciaId = competenciaId;
        this.sesionAprendizajeId = sesionAprendizajeId;
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public int getCompetenciaId() {
        return competenciaId;
    }

    public void setCompetenciaId(int competenciaId) {
        this.competenciaId = competenciaId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public int getUnidadCompetenciaId() {
        return unidadCompetenciaId;
    }

    public void setUnidadCompetenciaId(int unidadCompetenciaId) {
        this.unidadCompetenciaId = unidadCompetenciaId;
    }
}
