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
public class Georeferencia extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private int georeferenciaId ;
    @Column
    private String nombre ;
    @Column
    private int entidadId ;
    @Column
    private String alias;
    @Column
    private int estadoId;


    public Georeferencia() {
    }


    public Georeferencia(int georeferenciaId, String nombre, int entidadId, String alias, int estadoId) {
        this.georeferenciaId = georeferenciaId;
        this.nombre = nombre;
        this.entidadId = entidadId;
        this.alias = alias;
        this.estadoId = estadoId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "Georeferencia{" +
                "georeferenciaId=" + georeferenciaId +
                ", nombre='" + nombre + '\'' +
                ", entidadId=" + entidadId +
                ", alias='" + alias + '\'' +
                ", estadoId=" + estadoId +
                '}';
    }
}
