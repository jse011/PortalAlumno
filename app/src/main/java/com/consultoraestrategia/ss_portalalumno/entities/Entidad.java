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
public class Entidad extends BaseModel {
    public static final int ESTADO_CREADO = 1,
            ESTADO_ACTUALIZADO = 61,
            ESTADO_ELIMINADO = 62,
            ESTADO_TERMINADO = 220,
            ESTADO_AUTORIZADO = 221;
    @Column
    @PrimaryKey(autoincrement = true)
    private int entidadId;
    @Column
    private int tipoId;
    @Column
    private int parentId;
    @Column
    private String nombre;
    @Column
    private String ruc;
    @Column
    private String site;
    @Column
    private String telefono;
    @Column
    private String correo;
    @Column
    private String foto;
    @Column
    public int estadoId;

    public Entidad() {
    }

    public Entidad(int entidadId, int tipoId, int parentId, String nombre, String ruc, String site, String telefono, String correo, String foto, int estadoId) {
        this.entidadId = entidadId;
        this.tipoId = tipoId;
        this.parentId = parentId;
        this.nombre = nombre;
        this.ruc = ruc;
        this.site = site;
        this.telefono = telefono;
        this.correo = correo;
        this.foto = foto;
        this.estadoId = estadoId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "Entidad{" +
                "entidadId=" + entidadId +
                ", tipoId=" + tipoId +
                ", parentId=" + parentId +
                ", nombre='" + nombre + '\'' +
                ", ruc='" + ruc + '\'' +
                ", site='" + site + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", foto='" + foto + '\'' +
                ", estadoId=" + estadoId +
                '}';
    }
}











