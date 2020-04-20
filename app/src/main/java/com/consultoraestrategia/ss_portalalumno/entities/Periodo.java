package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */
@Table(database = AppDatabase.class)
public class Periodo extends BaseModel {

    @Column
    @PrimaryKey
    int periodoId;
    @Column
    private String nombre;
    @Column
    private String alias;
    @Column
    private boolean activo;
    @Column
    private int estadoId;

    public Periodo() {
    }

    public Periodo(int periodoId, String nombre, String alias, boolean activo, int estadoId) {
        this.periodoId = periodoId;
        this.nombre = nombre;
        this.alias = alias;
        this.activo = activo;
        this.estadoId = estadoId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

}
