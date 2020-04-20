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
public class HorarioDia extends BaseModel {

    @PrimaryKey
    private int idHorarioDia;
    @Column
    private int idHorario;
    @Column
    private int idDia;

    public HorarioDia() {
    }

    public HorarioDia(int idHorarioDia, int idHorario, int idDia) {
        this.idHorarioDia = idHorarioDia;
        this.idHorario = idHorario;
        this.idDia = idDia;
    }

    public int getIdHorarioDia() {
        return idHorarioDia;
    }

    public void setIdHorarioDia(int idHorarioDia) {
        this.idHorarioDia = idHorarioDia;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    @Override
    public String toString() {
        return "HorarioDia{" +
                "idHorarioDia=" + idHorarioDia +
                ", idHorario=" + idHorario +
                ", idDia=" + idDia +
                '}';
    }
}
