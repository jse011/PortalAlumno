package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class Relaciones extends BaseModel {

    public static final int PADRE= 181, MADRE=182, TIO=491, ABUELO=469 , HERMANO=183;
    @Unique
    @Column
    @PrimaryKey
    private int idRelacion;
    @Column
    private int personaPrincipalId;
    @Column
    private int personaVinculadaId;
    @Column
    private int tipoId;
    @Column
    private boolean activo;

    public Relaciones() {
    }


    public int getPersonaPrincipalId() {
        return personaPrincipalId;
    }

    public void setPersonaPrincipalId(int personaPrincipalId) {
        this.personaPrincipalId = personaPrincipalId;
    }

    public int getPersonaVinculadaId() {
        return personaVinculadaId;
    }

    public void setPersonaVinculadaId(int personaVinculadaId) {
        this.personaVinculadaId = personaVinculadaId;
    }

    public int getIdRelacion() {
        return idRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        this.idRelacion = idRelacion;
    }


    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Relaciones{" +
                "idRelacion=" + idRelacion +
                ", personaPrincipalId=" + personaPrincipalId +
                ", personaVinculadaId=" + personaVinculadaId +
                ", tipoId=" + tipoId +
                ", activo=" + activo +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relaciones)) return false;

        Relaciones that = (Relaciones) o;

        return idRelacion == that.idRelacion;
    }

    @Override
    public int hashCode() {
        return idRelacion;
    }
}
