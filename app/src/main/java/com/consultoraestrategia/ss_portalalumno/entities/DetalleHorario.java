package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 27/07/2017.
 */

@Table(database = AppDatabase.class)
public class DetalleHorario extends BaseModel {
    public static final int TIPO_HORA_ACADEMICO = 257, TIPO_HORA_RECESO = 259;
    public static final int TIPO_TURNO_MAÑANA = 260, TIPO_TURNO_TARDE = 261, TIPO_TURNO_NOCHE = 264;


    @PrimaryKey
    private int idDetalleHorario;
    @Column
    private int idTipoHora;
    @Column
    private int idTipoTurno;
    @Column
    private String horaInicio;
    @Column
    private String horaFin;
    @Column
    private int idHorarioDia;
    @Column
    private int timeChange;


    public DetalleHorario() {
    }

    public DetalleHorario(int idDetalleHorario, int idTipoHora, int idTipoTurno, String horaInicio, String horaFin, int idHorarioDia, int timeChange) {
        this.idDetalleHorario = idDetalleHorario;
        this.idTipoHora = idTipoHora;
        this.idTipoTurno = idTipoTurno;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idHorarioDia = idHorarioDia;
        this.timeChange = timeChange;
    }

    public int getIdDetalleHorario() {
        return idDetalleHorario;
    }

    public void setIdDetalleHorario(int idDetalleHorario) {
        this.idDetalleHorario = idDetalleHorario;
    }

    public int getIdTipoHora() {
        return idTipoHora;
    }

    public void setIdTipoHora(int idTipoHora) {
        this.idTipoHora = idTipoHora;
    }

    public int getIdTipoTurno() {
        return idTipoTurno;
    }

    public void setIdTipoTurno(int idTipoTurno) {
        this.idTipoTurno = idTipoTurno;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getIdHorarioDia() {
        return idHorarioDia;
    }

    public void setIdHorarioDia(int idHorarioDia) {
        this.idHorarioDia = idHorarioDia;
    }

    public int getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(int timeChange) {
        this.timeChange = timeChange;
    }

    @Override
    public String toString() {
        return "DetalleHorario{" +
                "idDetalleHorario=" + idDetalleHorario +
                ", idTipoHora=" + idTipoHora +
                ", idTipoTurno=" + idTipoTurno +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFin='" + horaFin + '\'' +
                ", idHorarioDia=" + idHorarioDia +
                ", timeChange=" + timeChange +
                '}';
    }
}
