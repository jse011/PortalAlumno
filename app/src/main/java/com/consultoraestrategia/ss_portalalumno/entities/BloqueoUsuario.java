package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


/**
 * Created by irvinmarin on 23/03/2017.
 */
@Table(database = AppDatabase.class)
public class BloqueoUsuario extends BaseModel {

    @Column
    @PrimaryKey
    int usuarioId;
    @Column
    private boolean habilitarAcceso;

    public BloqueoUsuario() {
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isHabilitarAcceso() {
        return habilitarAcceso;
    }

    public void setHabilitarAcceso(boolean habilitarAcceso) {
        this.habilitarAcceso = habilitarAcceso;
    }
}
