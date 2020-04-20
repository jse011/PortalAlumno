package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 21/12/2017.
 */
@Table(database = AppDatabase.class)
public class SesionEventoCompetenciaDesempenioIcd extends BaseModel {
    @PrimaryKey
    @Column
    private int sesionCompetenciaDesempenioIcdId;
    @Column
    private int sesionCompetenciaId;
    @Column
    private int desempenioIcdId;
    @Column
    private int sesionAprendizajeId;

    public SesionEventoCompetenciaDesempenioIcd() {
    }


    public int getSesionCompetenciaDesempenioIcdId() {
        return sesionCompetenciaDesempenioIcdId;
    }

    public void setSesionCompetenciaDesempenioIcdId(int sesionCompetenciaDesempenioIcdId) {
        this.sesionCompetenciaDesempenioIcdId = sesionCompetenciaDesempenioIcdId;
    }

    public int getSesionCompetenciaId() {
        return sesionCompetenciaId;
    }

    public void setSesionCompetenciaId(int sesionCompetenciaId) {
        this.sesionCompetenciaId = sesionCompetenciaId;
    }

    public int getDesempenioIcdId() {
        return desempenioIcdId;
    }

    public void setDesempenioIcdId(int desempenioIcdId) {
        this.desempenioIcdId = desempenioIcdId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }
}

