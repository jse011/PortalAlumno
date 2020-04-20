package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by irvinmarin on 25/08/2017.
 */
@Table(database = AppDatabase.class)
public class UsuarioAcceso extends BaseModel {
    @PrimaryKey
    private int id;
    @Column
    private int parentId;
    @Column
    private String abreviacion;
    @Column
    private String descripcion;
    @Column
    private int item;
    @Column
    private int nivel;
//    @Column
    private String url;
    @Column
    private boolean estado;
    @Column
    private String fechaCreacion;
    @Column
    private int usuarioCreacionId;
    @Column
    private String icono;
    @Column
    private String fechaActualizacion;
    @Column
    private int usuarioActualizacionId;
    @Column
    private boolean movil;
    @Column
    private int aplicacionId;
    @Column
    private int grupoId;
    @Column
    private boolean ocultar;

    public UsuarioAcceso() {
    }

    public UsuarioAcceso(int idAcceso, int parentId, String abreviacion, String descripcion, int item, int nivel, String url, boolean estado, String fechaCreacion, int usuarioCreacionId, String icono, String fechaActualizacion, int usuarioActualizacionId, boolean movil, int aplicacionId, int grupoId, boolean ocultar) {
        this.id = idAcceso;
        this.parentId = parentId;
        this.abreviacion = abreviacion;
        this.descripcion = descripcion;
        this.item = item;
        this.nivel = nivel;
        this.url = url;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuarioCreacionId = usuarioCreacionId;
        this.icono = icono;
        this.fechaActualizacion = fechaActualizacion;
        this.usuarioActualizacionId = usuarioActualizacionId;
        this.movil = movil;
        this.aplicacionId = aplicacionId;
        this.grupoId = grupoId;
        this.ocultar = ocultar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getUsuarioCreacionId() {
        return usuarioCreacionId;
    }

    public void setUsuarioCreacionId(int usuarioCreacionId) {
        this.usuarioCreacionId = usuarioCreacionId;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getUsuarioActualizacionId() {
        return usuarioActualizacionId;
    }

    public void setUsuarioActualizacionId(int usuarioActualizacionId) {
        this.usuarioActualizacionId = usuarioActualizacionId;
    }

    public boolean isMovil() {
        return movil;
    }

    public void setMovil(boolean movil) {
        this.movil = movil;
    }

    public int getAplicacionId() {
        return aplicacionId;
    }

    public void setAplicacionId(int aplicacionId) {
        this.aplicacionId = aplicacionId;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public boolean isOcultar() {
        return ocultar;
    }

    public void setOcultar(boolean ocultar) {
        this.ocultar = ocultar;
    }

    @Override
    public String toString() {
        return "UsuarioAcceso{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", abreviacion='" + abreviacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", item=" + item +
                ", nivel=" + nivel +
                ", url='" + url + '\'' +
                ", estado=" + estado +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", usuarioCreacionId=" + usuarioCreacionId +
                ", icono='" + icono + '\'' +
                ", fechaActualizacion='" + fechaActualizacion + '\'' +
                ", usuarioActualizacionId=" + usuarioActualizacionId +
                ", movil=" + movil +
                ", aplicacionId=" + aplicacionId +
                ", grupoId=" + grupoId +
                ", ocultar=" + ocultar +
                '}';
    }
}
