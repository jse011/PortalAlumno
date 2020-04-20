package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class UsuarioRolGeoreferencia extends BaseModel {
    @Column
    @PrimaryKey
    private int usuarioRolGeoreferenciaId;
    @Column
    private int usuarioId;
    @Column
    private int rolId;
    @Column
    private int geoReferenciaId;
    @Column
    public int entidadId;

    public UsuarioRolGeoreferencia() {
    }

    public UsuarioRolGeoreferencia(int usuarioRolGeoreferenciaId, int usuarioId, int rolId, int geoReferenciaId, int entidadId) {
        this.usuarioRolGeoreferenciaId = usuarioRolGeoreferenciaId;
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.geoReferenciaId = geoReferenciaId;
        this.entidadId = entidadId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public int getGeoReferenciaId() {
        return geoReferenciaId;
    }

    public void setGeoReferenciaId(int geoReferenciaId) {
        this.geoReferenciaId = geoReferenciaId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    @Override
    public String toString() {
        return "UsuarioRolGeoreferencia{" +
                "usuarioId=" + usuarioId +
                ", rolId=" + rolId +
                ", geoReferenciaId=" + geoReferenciaId +
                ", entidadId=" + entidadId +
                '}';
    }

    public int getUsuarioRolGeoreferenciaId() {
        return usuarioRolGeoreferenciaId;
    }

    public void setUsuarioRolGeoreferenciaId(int usuarioRolGeoreferenciaId) {
        this.usuarioRolGeoreferenciaId = usuarioRolGeoreferenciaId;
    }
}
