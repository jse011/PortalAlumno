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
public class Dia extends BaseModel {
    public static final int DOMIGO = 1, LUNES = 2, MARTES = 3, MIERCOLES = 4, JUEVES = 5, VIERNES = 6, SABADO = 7;
    @PrimaryKey
    private int diaId;
    @Column
    private String nombre;
    @Column
    private boolean estado;
    @Column
    private String alias;


    public Dia(int diaId, String nombre, boolean estado, String alias) {
        this.diaId = diaId;
        this.nombre = nombre;
        this.estado = estado;
        this.alias = alias;
    }

    public Dia() {
    }

    public int getDiaId() {
        return diaId;
    }

    public void setDiaId(int diaId) {
        this.diaId = diaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Dia{" +
                "diaId=" + diaId +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", alias='" + alias + '\'' +
                '}';
    }
}
