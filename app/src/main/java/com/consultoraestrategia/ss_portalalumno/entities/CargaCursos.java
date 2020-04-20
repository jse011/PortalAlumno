package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class CargaCursos extends BaseModel {

    @Column
    @PrimaryKey
    private int cargaCursoId;
    @Column
    private int planCursoId;
    @Column
    private int empleadoId;
    @Column
    private int cargaAcademicaId;
    @Column
    private int complejo;
    @Column
    private int evaluable;
    @Column
    private int idempleado;
    @Column
    private int idTipoHora;
    @Column
    private String descripcion;
    @Column
    private long fechaInicio;
    @Column
    private long fechafin;
    @Column
    private String modo;
    @Column
    private int estado;
    @Column
    private int anioAcademicoId;
    @Column
    private int aulaId;
    @Column
    private int grupoId;
    @Column
    private int idPlanEstudio;
    @Column
    private int idPlanEstudioVersion;
    @Column
    private int CapacidadVacanteP;
    @Column
    private int CapacidadVacanteD;

    public CargaCursos() {
    }

    public CargaCursos(int cargaCursoId, int planCursoId, int empleadoId, int cargaAcademicaId) {
        this.cargaCursoId = cargaCursoId;
        this.planCursoId = planCursoId;
        this.empleadoId = empleadoId;
        this.cargaAcademicaId = cargaAcademicaId;
    }

    public int getComplejo() {
        return complejo;
    }

    public void setComplejo(int complejo) {
        this.complejo = complejo;
    }

    public int getEvaluable() {
        return evaluable;
    }

    public void setEvaluable(int evaluable) {
        this.evaluable = evaluable;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getCargaAcademicaId() {
        return cargaAcademicaId;
    }

    public void setCargaAcademicaId(int cargaAcademicaId) {
        this.cargaAcademicaId = cargaAcademicaId;
    }


    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdTipoHora() {
        return idTipoHora;
    }

    public void setIdTipoHora(int idTipoHora) {
        this.idTipoHora = idTipoHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(long fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getFechafin() {
        return fechafin;
    }

    public void setFechafin(long fechafin) {
        this.fechafin = fechafin;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }

    public int getAulaId() {
        return aulaId;
    }

    public void setAulaId(int aulaId) {
        this.aulaId = aulaId;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public int getIdPlanEstudio() {
        return idPlanEstudio;
    }

    public void setIdPlanEstudio(int idPlanEstudio) {
        this.idPlanEstudio = idPlanEstudio;
    }

    public int getIdPlanEstudioVersion() {
        return idPlanEstudioVersion;
    }

    public void setIdPlanEstudioVersion(int idPlanEstudioVersion) {
        this.idPlanEstudioVersion = idPlanEstudioVersion;
    }

    public int getCapacidadVacanteP() {
        return CapacidadVacanteP;
    }

    public void setCapacidadVacanteP(int capacidadVacanteP) {
        CapacidadVacanteP = capacidadVacanteP;
    }

    public int getCapacidadVacanteD() {
        return CapacidadVacanteD;
    }

    public void setCapacidadVacanteD(int capacidadVacanteD) {
        CapacidadVacanteD = capacidadVacanteD;
    }

    public static CargaCursos getCargaCurso(int id) {
        return SQLite
                .select()
                .from(CargaCursos.class)
                .where(CargaCursos_Table.cargaCursoId.eq(id))
                .querySingle();
    }

    @Override
    public String toString() {
        return "CargaCursos{" +
                "cargaCursoId=" + cargaCursoId +
                ", planCursoId=" + planCursoId +
                ", empleadoId=" + empleadoId +
                ", cargaAcademicaId=" + cargaAcademicaId +
                ", complejo=" + complejo +
                ", evaluable=" + evaluable +
                ", idempleado=" + idempleado +
                ", idTipoHora=" + idTipoHora +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechafin=" + fechafin +
                ", modo='" + modo + '\'' +
                ", estado=" + estado +
                ", anioAcademicoId=" + anioAcademicoId +
                ", aulaId=" + aulaId +
                ", grupoId=" + grupoId +
                ", idPlanEstudio=" + idPlanEstudio +
                ", idPlanEstudioVersion=" + idPlanEstudioVersion +
                ", CapacidadVacanteP=" + CapacidadVacanteP +
                ", CapacidadVacanteD=" + CapacidadVacanteD +
                '}';
    }
}
