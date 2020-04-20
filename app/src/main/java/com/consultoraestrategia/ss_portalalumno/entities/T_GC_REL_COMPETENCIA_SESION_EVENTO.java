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
public class T_GC_REL_COMPETENCIA_SESION_EVENTO extends BaseModel {

    @Column
    private int competenciaId;
    @Column
    private int sesionAprendizajeId;
    @PrimaryKey
    @Column
    private int sesionCompetenciaId;

    public T_GC_REL_COMPETENCIA_SESION_EVENTO() {
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

    public int getSesionCompetenciaId() {
        return sesionCompetenciaId;
    }

    public void setSesionCompetenciaId(int sesionCompetenciaId) {
        this.sesionCompetenciaId = sesionCompetenciaId;
    }
}
