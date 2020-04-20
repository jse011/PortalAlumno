package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 22/03/2017.
 */
@Table(database = AppDatabase.class)
public class CargaAcademica extends BaseModel {

    @Column
    @PrimaryKey
    private int cargaAcademicaId;
    @Column
    private int seccionId;// IdGrupo base de datos del servidor
    @Column
    private int periodoId;
    @Column
    private int aulaId;
    @Column
    private int idPlanEstudio;
    @Column
    private int idPlanEstudioVersion;
    @Column
    private int idAnioAcademico;
    @Column
    private int idEmpleadoTutor;
    @Column
    public int estadoId;
    @Column
    public int idPeriodoAcad;
    @Column
    public int idGrupo;
    @Column
    public int capacidadVacante;
    @Column
    public int capacidadVacanteD;

    public CargaAcademica(){
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

    public int getCargaAcademicaId() {
        return cargaAcademicaId;
    }

    public void setCargaAcademicaId(int cargaAcademicaId) {
        this.cargaAcademicaId = cargaAcademicaId;
    }

    public int getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(int seccionId) {
        this.seccionId = seccionId;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    public int getAulaId() {
        return aulaId;
    }

    public void setAulaId(int aulaId) {
        this.aulaId = aulaId;
    }

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public int getIdEmpleadoTutor() {
        return idEmpleadoTutor;
    }

    public void setIdEmpleadoTutor(int idEmpleadoTutor) {
        this.idEmpleadoTutor = idEmpleadoTutor;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getIdPeriodoAcad() {
        return idPeriodoAcad;
    }

    public void setIdPeriodoAcad(int idPeriodoAcad) {
        this.idPeriodoAcad = idPeriodoAcad;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getCapacidadVacante() {
        return capacidadVacante;
    }

    public void setCapacidadVacante(int capacidadVacante) {
        this.capacidadVacante = capacidadVacante;
    }

    public int getCapacidadVacanteD() {
        return capacidadVacanteD;
    }

    public void setCapacidadVacanteD(int capacidadVacanteD) {
        this.capacidadVacanteD = capacidadVacanteD;
    }

    @Override
    public String toString() {
        return "CargaAcademica{" +
                "cargaAcademicaId=" + cargaAcademicaId +
                ", seccionId=" + seccionId +
                ", periodoId=" + periodoId +
                ", aulaId=" + aulaId +
                ", idAnioAcademico=" + idAnioAcademico +
                '}';
    }

}
