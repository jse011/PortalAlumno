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
public class HorarioPrograma extends BaseModel {
    @PrimaryKey
    private int idHorarioPrograma;
    @Column
    private int idHorario;
    @Column
    private int activo;
    @Column
    private int idProgramaEducativo;
    @Column
    private int idAnioAcademico;
    @Column
    private int idUsuarioActualizacion;
    @Column
    private int idUsuarioCreacion;
    @Column
    private String fechaCreacion;
    @Column
    private String fechaActualizacion;


    public HorarioPrograma() {
    }


    public HorarioPrograma(int idHorarioPrograma, int idHorario, int idProgramaEducativo, int idAnioAcademico, int idUsuarioActualizacion, int idUsuarioCreacion, String fechaCreacion, String fechaActualizacion) {
        this.idHorarioPrograma = idHorarioPrograma;
        this.idHorario = idHorario;
        this.idProgramaEducativo = idProgramaEducativo;
        this.idAnioAcademico = idAnioAcademico;
        this.idUsuarioActualizacion = idUsuarioActualizacion;
        this.idUsuarioCreacion = idUsuarioCreacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getIdHorarioPrograma() {
        return idHorarioPrograma;
    }

    public void setIdHorarioPrograma(int idHorarioPrograma) {
        this.idHorarioPrograma = idHorarioPrograma;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdProgramaEducativo() {
        return idProgramaEducativo;
    }

    public void setIdProgramaEducativo(int idProgramaEducativo) {
        this.idProgramaEducativo = idProgramaEducativo;
    }

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public int getIdUsuarioActualizacion() {
        return idUsuarioActualizacion;
    }

    public void setIdUsuarioActualizacion(int idUsuarioActualizacion) {
        this.idUsuarioActualizacion = idUsuarioActualizacion;
    }

    public int getIdUsuarioCreacion() {
        return idUsuarioCreacion;
    }

    public void setIdUsuarioCreacion(int idUsuarioCreacion) {
        this.idUsuarioCreacion = idUsuarioCreacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
}
