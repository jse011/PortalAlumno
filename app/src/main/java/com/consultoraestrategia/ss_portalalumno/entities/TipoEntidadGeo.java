package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class TipoEntidadGeo extends BaseModel {
    @Column
    private int subtTipoId;
    @PrimaryKey
    private int comportamientoId;
    @PrimaryKey
    private int georeferenciaId;
    @PrimaryKey
    private int entidadId;

    public int getSubtTipoId() {
        return subtTipoId;
    }

    public void setSubtTipoId(int subtTipoId) {
        this.subtTipoId = subtTipoId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getComportamientoId() {
        return comportamientoId;
    }

    public void setComportamientoId(int comportamientoId) {
        this.comportamientoId = comportamientoId;
    }
}

