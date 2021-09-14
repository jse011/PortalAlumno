package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class ReunionVirtualServidor extends BaseModel {
    @PrimaryKey
    private int reunionVirtualId;
    @Column
    private int instrumentoEvalId ;
    @Column
    private String url ;
    @Column
    private int tipoCanalId ;
    @Column
    private String codigoAnfitrion ;
    @Column
    private String nombreReunion ;
    @Column
    private int alcanceSala ;
    @Column
    private String fechaInicio ;
    @Column
    private String horaInicio ;
    @Column
    private int tiempoDuracion ;
    @Column
    private int sesionAprendizajeId ;
    @Column
    private int entidadId ;
    @Column
    private int georeferenciaId ;

    public int getReunionVirtualId() {
        return reunionVirtualId;
    }

    public void setReunionVirtualId(int reunionVirtualId) {
        this.reunionVirtualId = reunionVirtualId;
    }

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTipoCanalId() {
        return tipoCanalId;
    }

    public void setTipoCanalId(int tipoCanalId) {
        this.tipoCanalId = tipoCanalId;
    }

    public String getCodigoAnfitrion() {
        return codigoAnfitrion;
    }

    public void setCodigoAnfitrion(String codigoAnfitrion) {
        this.codigoAnfitrion = codigoAnfitrion;
    }

    public String getNombreReunion() {
        return nombreReunion;
    }

    public void setNombreReunion(String nombreReunion) {
        this.nombreReunion = nombreReunion;
    }

    public int getAlcanceSala() {
        return alcanceSala;
    }

    public void setAlcanceSala(int alcanceSala) {
        this.alcanceSala = alcanceSala;
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

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }
}
