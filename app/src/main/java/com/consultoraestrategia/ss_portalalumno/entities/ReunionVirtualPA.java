package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class ReunionVirtualPA extends BaseModel {
    @PrimaryKey
    private String key;
    @Column
    private String correo;
    @Column
    private String descripcion;
    @Column
    private String fechaInicio;
    @Column
    private String horaInicio;
    @Column
    private String nombreReunion;
    @Column
    private String pathVideoReunion;
    @Column
    private String salaReunionId;
    @Column
    private int tiempoDuracion;
    @Column
    private int tipoCanalId;
    @Column
    private String url;
    @Column
    private String url2;
    @Column
    private String usuario;
    @Column
    private int sesionAprendizajeId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getNombreReunion() {
        return nombreReunion;
    }

    public void setNombreReunion(String nombreReunion) {
        this.nombreReunion = nombreReunion;
    }

    public String getPathVideoReunion() {
        return pathVideoReunion;
    }

    public void setPathVideoReunion(String pathVideoReunion) {
        this.pathVideoReunion = pathVideoReunion;
    }

    public String getSalaReunionId() {
        return salaReunionId;
    }

    public void setSalaReunionId(String salaReunionId) {
        this.salaReunionId = salaReunionId;
    }

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public int getTipoCanalId() {
        return tipoCanalId;
    }

    public void setTipoCanalId(int tipoCanalId) {
        this.tipoCanalId = tipoCanalId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }
}
