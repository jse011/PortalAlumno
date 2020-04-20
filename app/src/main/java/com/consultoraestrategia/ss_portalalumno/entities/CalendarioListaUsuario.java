package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class CalendarioListaUsuario extends BaseModel {
    @PrimaryKey
    public String calendarioId;
    @PrimaryKey
    public int listaUsuarioId;

    public String getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(String calendarioId) {
        this.calendarioId = calendarioId;
    }

    public int getListaUsuarioId() {
        return listaUsuarioId;
    }

    public void setListaUsuarioId(int listaUsuarioId) {
        this.listaUsuarioId = listaUsuarioId;
    }
}
