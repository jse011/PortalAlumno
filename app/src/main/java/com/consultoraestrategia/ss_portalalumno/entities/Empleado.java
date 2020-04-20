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
public class Empleado extends BaseModel {

    @Column
    @PrimaryKey
    private int empleadoId;
    @Column
    private int personaId;
    @Column
    private String linkURL;
    @Column
    private boolean estado;

    public Empleado(int empleadoId, int personaId, String linkURL, boolean estado) {
        this.empleadoId = empleadoId;
        this.personaId = personaId;
        this.linkURL = linkURL;
        this.estado = estado;
    }

    public Empleado() {

    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "empleadoId=" + empleadoId +
                ", personaId=" + personaId +
                ", linkURL='" + linkURL + '\'' +
                ", estado=" + estado +
                '}';
    }
}
