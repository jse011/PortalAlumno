package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Desempenio extends BaseModel {
    @PrimaryKey
    public int desempenioId;
    @Column
    public int estandarAprendizajeId;
    @Column
    public String descripcion;
    @Column
    public int peso;
    // public int usuarioCreadorId;
    // public string fechaCreacion;
    // public int usuarioAccionId;
    //public string fechaAccion;
    @Column
    public Boolean estado;
    @Column
    public int periodoId;

    public int getDesempenioId() {
        return desempenioId;
    }

    public void setDesempenioId(int desempenioId) {
        this.desempenioId = desempenioId;
    }

    public int getEstandarAprendizajeId() {
        return estandarAprendizajeId;
    }

    public void setEstandarAprendizajeId(int estandarAprendizajeId) {
        this.estandarAprendizajeId = estandarAprendizajeId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }
}
