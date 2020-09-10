package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class SesionAlumnoArchivos extends BaseModel {
    @PrimaryKey
    private String sesionAlumnoArchivoId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int alumnoId;
    @Column
    private String nombre;
    @Column
    private String path;
    @Column
    private boolean repositorio;

    public String getSesionAlumnoArchivoId() {
        return sesionAlumnoArchivoId;
    }

    public void setSesionAlumnoArchivoId(String sesionAlumnoArchivoId) {
        this.sesionAlumnoArchivoId = sesionAlumnoArchivoId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
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
