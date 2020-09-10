package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 27/07/2017.
 */
//Execpecion del uso del BaseRelEntity ya que esta tabla se va actualizar
@Table(database = AppDatabase.class)
public class ActividadAprendizaje extends BaseRelEntity {
    public final static int ESTADO_CREADO = 229, ESTADO_PENDIENTE = 228, ESTADO_HECHO = 247;
    @PrimaryKey
    private int actividadAprendizajeId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int tipoActividadId;
    @Column
    private int secuenciaId;
    @Column
    private int tiempo;
    @Column
    private String actividad;
    @Column
    private String descripcionActividad;
    @Column
    private int estadoId;
    @Column
    private int parentId;
    @Column
    private  int instrumentoEvalId;

    public ActividadAprendizaje() {
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getActividadAprendizajeId() {
        return actividadAprendizajeId;
    }

    public void setActividadAprendizajeId(int actividadAprendizajeId) {
        this.actividadAprendizajeId = actividadAprendizajeId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getTipoActividadId() {
        return tipoActividadId;
    }

    public void setTipoActividadId(int tipoActividadId) {
        this.tipoActividadId = tipoActividadId;
    }

    public int getSecuenciaId() {
        return secuenciaId;
    }

    public void setSecuenciaId(int secuenciaId) {
        this.secuenciaId = secuenciaId;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
    }
}
