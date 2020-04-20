package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Ubicaciones extends BaseModel {
    public Ubicaciones() { }
    @PrimaryKey
    private int id;
    @Column
    private String callePasajeMz;
    @Column
    private String latitud;
    @Column
    private String longitud;
    @Column
    private String nroLote;
    @Column
    private String referencia;
    @Column
    private String sector;
    @Column
    private int geoReferenciaId;
    @Column
    private int paisId;
    @Column
    private int departamentoId;
    @Column
    private int provinciaId;
    @Column
    private int distritoId;
    @Column
    private int entidad_Id;
    @Column
    private int persona_Id;
    @Column
    public String nombreUbicacion;
    @Column
    private int tipoId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCallePasajeMz() {
        return callePasajeMz;
    }

    public void setCallePasajeMz(String callePasajeMz) {
        this.callePasajeMz = callePasajeMz;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNroLote() {
        return nroLote;
    }

    public void setNroLote(String nroLote) {
        this.nroLote = nroLote;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getGeoReferenciaId() {
        return geoReferenciaId;
    }

    public void setGeoReferenciaId(int geoReferenciaId) {
        this.geoReferenciaId = geoReferenciaId;
    }

    public int getPaisId() {
        return paisId;
    }

    public void setPaisId(int paisId) {
        this.paisId = paisId;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public int getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        this.provinciaId = provinciaId;
    }

    public int getDistritoId() {
        return distritoId;
    }

    public void setDistritoId(int distritoId) {
        this.distritoId = distritoId;
    }

    public int getEntidad_Id() {
        return entidad_Id;
    }

    public void setEntidad_Id(int entidad_Id) {
        this.entidad_Id = entidad_Id;
    }

    public int getPersona_Id() {
        return persona_Id;
    }

    public void setPersona_Id(int persona_Id) {
        this.persona_Id = persona_Id;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getNombreUbicacion() {
        return nombreUbicacion;
    }

    public void setNombreUbicacion(String nombreUbicacion) {
        this.nombreUbicacion = nombreUbicacion;
    }
}
