package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 27/07/2017.
 */
@Table(database = AppDatabase.class)
public class Horario extends BaseModel {

    @Column
    @PrimaryKey
    private int idHorario;
    @Column
    private String nombre;
    @Column
    private String descripción;
    @Column
    private String fecCreacion;
    @Column
    private String fecActualizacion;
    @Column
    private boolean estado;
    @Column
    private int idUsuario;
    @Column
    private int entidadId;
    @Column
    private int georeferenciaId;
    @Column
    private int organigramaId;



    public Horario() {
    }


    public Horario(int idHorario, String nombre, String descripción, String fecCreacion, String fecActualizacion, boolean estado, int idUsuario, int entidadId, int georeferenciaId, int organigramaId) {
        this.idHorario = idHorario;
        this.nombre = nombre;
        this.descripción = descripción;
        this.fecCreacion = fecCreacion;
        this.fecActualizacion = fecActualizacion;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.entidadId = entidadId;
        this.georeferenciaId = georeferenciaId;
        this.organigramaId = organigramaId;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getFecCreacion() {
        return fecCreacion;
    }

    public void setFecCreacion(String fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public String getFecActualizacion() {
        return fecActualizacion;
    }

    public void setFecActualizacion(String fecActualizacion) {
        this.fecActualizacion = fecActualizacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getOrganigramaId() {
        return organigramaId;
    }

    public void setOrganigramaId(int organigramaId) {
        this.organigramaId = organigramaId;
    }
}
