package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class TareaAlumnoArchivos extends BaseModel {
    @PrimaryKey
    private String tareaAlumnoArchivoId;
    @Column
    private String tareaId;
    @Column
    private int alumnoId;
    @Column
    private String nombre;
    @Column
    private String path;
    @Column
    private boolean repositorio;

    public String getTareaAlumnoArchivoId() {
        return tareaAlumnoArchivoId;
    }

    public void setTareaAlumnoArchivoId(String tareaAlumnoArchivoId) {
        this.tareaAlumnoArchivoId = tareaAlumnoArchivoId;
    }

    public String getTareaId() {
        return tareaId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRepositorio() {
        return repositorio;
    }

    public void setRepositorio(boolean repositorio) {
        this.repositorio = repositorio;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }
}
