package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 03/05/2017.
 */
@Table(database = AppDatabase.class)
public class CalendarioPeriodo extends BaseModel {

    public static final int CALENDARIO_PERIODO_CREADO = 214;
    public static final int CALENDARIO_PERIODO_VIGENTE = 215;
    public static final int CALENDARIO_PERIODO_CERRADO = 217;

    @PrimaryKey
    private int calendarioPeriodoId;
    @Column
    private long fechaInicio;
    @Column
    private long fechaFin;
    @Column
    private int calendarioAcademicoId;
    @Column
    private int tipoId;
    @Column
    private int estadoId;
    @Column
    private int diazPlazo;

    public CalendarioPeriodo() {
    }


    public int getCalendarioPeriodoId() {
        return calendarioPeriodoId;
    }

    public void setCalendarioPeriodoId(int calendarioPeriodoId) {
        this.calendarioPeriodoId = calendarioPeriodoId;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(long fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCalendarioAcademicoId() {
        return calendarioAcademicoId;
    }

    public void setCalendarioAcademicoId(int calendarioAcademicoId) {
        this.calendarioAcademicoId = calendarioAcademicoId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getDiazPlazo() {
        return diazPlazo;
    }

    public void setDiazPlazo(int diazPlazo) {
        this.diazPlazo = diazPlazo;
    }

    @Override
    public String toString() {
        return "CalendarioPeriodo{" +
                "calendarioPeriodoId=" + calendarioPeriodoId +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", calendarioAcademicoId=" + calendarioAcademicoId +
                ", tipoId=" + tipoId +
                ", estadoId=" + estadoId +
                ", diazPlazo=" + diazPlazo +
                '}';
    }
}
