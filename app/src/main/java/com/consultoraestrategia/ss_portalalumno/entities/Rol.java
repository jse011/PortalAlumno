package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by irvinmarin on 23/03/2017.
 */
@Table(database = AppDatabase.class)
public class Rol extends BaseModel {

    @Column
    @PrimaryKey
    private int rolId;
    @Column
    private String nombre;
    @Column
    private int parentId;
    @Column
    private boolean estado;

    public Rol() {
    }

    public Rol(int rolId, String nombre, int parentId, boolean estado) {
        this.rolId = rolId;
        this.nombre = nombre;
        this.parentId = parentId;
        this.estado = estado;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "rolId=" + rolId +
                ", nombre='" + nombre + '\'' +
                ", parentId=" + parentId +
                ", estado=" + estado +
                '}';
    }
}
