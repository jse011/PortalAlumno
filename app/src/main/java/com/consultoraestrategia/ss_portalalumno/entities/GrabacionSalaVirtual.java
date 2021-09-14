package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class GrabacionSalaVirtual extends BaseModel {
    @PrimaryKey
    private int grabacionId;
    @Column
    private String urlGrabacion;
    @Column
    private String nombreGrabacion;
    @Column
    private int sesionAprendizajeId;

    public int getGrabacionId() {
        return grabacionId;
    }

    public void setGrabacionId(int grabacionId) {
        this.grabacionId = grabacionId;
    }

    public String getUrlGrabacion() {
        return urlGrabacion;
    }

    public void setUrlGrabacion(String urlGrabacion) {
        this.urlGrabacion = urlGrabacion;
    }

    public String getNombreGrabacion() {
        return nombreGrabacion;
    }

    public void setNombreGrabacion(String nombreGrabacion) {
        this.nombreGrabacion = nombreGrabacion;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }
}
