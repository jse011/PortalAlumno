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
public class SesionEventoDesempenioIcdCampotematico extends BaseModel {
    @PrimaryKey
    @Column
    private int sesionCompetenciaDesempenioIcdId;
    @PrimaryKey
    @Column
    private int campoTematicoId;

    public SesionEventoDesempenioIcdCampotematico() {
    }

    public int getSesionCompetenciaDesempenioIcdId() {
        return sesionCompetenciaDesempenioIcdId;
    }

    public void setSesionCompetenciaDesempenioIcdId(int sesionCompetenciaDesempenioIcdId) {
        this.sesionCompetenciaDesempenioIcdId = sesionCompetenciaDesempenioIcdId;
    }

    public int getCampoTematicoId() {
        return campoTematicoId;
    }

    public void setCampoTematicoId(int campoTematicoId) {
        this.campoTematicoId = campoTematicoId;
    }
}

