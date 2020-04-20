package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class GeoRefOrganigrama extends BaseModel {
    @PrimaryKey
    private int id;
    @Column
    private String alias;
    @Column
    public int geoReferenciaId;
    //public int usuarioCreadorId { get; set; }
    //public int usuarioAccionId { get; set; }
    @Column
    private int organigramaId;
    @Column
    private boolean activo;
    @Column
    private String telefono;
    @Column
    private String correo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getGeoReferenciaId() {
        return geoReferenciaId;
    }

    public void setGeoReferenciaId(int geoReferenciaId) {
        this.geoReferenciaId = geoReferenciaId;
    }

    public int getOrganigramaId() {
        return organigramaId;
    }

    public void setOrganigramaId(int organigramaId) {
        this.organigramaId = organigramaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
