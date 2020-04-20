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
public class NivelAcademico extends BaseModel {

    @PrimaryKey
    private int nivelAcadId;
    @Column
    private String nombre;
    @Column
    private String activo;
    @Column
    private String entidadId;




    public NivelAcademico() {
    }

    public NivelAcademico(int nivelAcadId, String nombre, String activo, String entidadId) {
        this.nivelAcadId = nivelAcadId;
        this.nombre = nombre;
        this.activo = activo;
        this.entidadId = entidadId;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(String entidadId) {
        this.entidadId = entidadId;
    }

    public int getNivelAcadId() {
        return nivelAcadId;
    }

    public void setNivelAcadId(int nivelAcadId) {
        this.nivelAcadId = nivelAcadId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
