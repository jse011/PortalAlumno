package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class Archivo extends BaseEntity {

    public static final int TIPO_VIDEO = 379, TIPO_VINCULO = 380, TIPO_DOCUMENTO = 397, TIPO_IMAGEN = 398, TIPO_AUDIO = 399, TIPO_HOJA_CALCULO = 400, TIPO_DIAPOSITIVA = 401, TIPO_PDF = 402, TIPO_YOUTUBE = 581, TIPO_ENCUESTA = 630;

    @Column
    public String archivoId;
    // public int usuarioAccion { get; set; }
    @Column
    public String path; //IMG-20190206-WA0013.jpg
    @Column
    public int planCursoId;
    @Column
    public String nombre;
    @Column
    public int tipoId;
    @Column
    public String localpath;

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    @Override
    public String toString() {
        return "Archivo{" +
                "archivoId='" + archivoId + '\'' +
                ", path='" + path + '\'' +
                ", planCursoId=" + planCursoId +
                ", nombre='" + nombre + '\'' +
                ", tipoId=" + tipoId +
                ", localpath='" + localpath + '\'' +
                '}';
    }
}
