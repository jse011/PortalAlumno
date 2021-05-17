package com.consultoraestrategia.ss_portalalumno.entities.queryCustom;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;


@QueryModel(database = AppDatabase.class, allFields = true)
public class CursoCustom {
    
    @Column
    public int cursoId;
    @Column
    public String nombre;
    @Column
    public int estadoId;
    @Column
    public String descripcion;
    @Column
    public String alias;
    @Column
    public int cargaCursoId;
    @Column
    public int cargaAcademicaId;
    @Column
    public int seccionId;
    @Column
    public int periodoId;
    @Column
    public int aulaId;
    @Column
    public int idPlanEstudio;
    @Column
    public int idPlanEstudioVersion;
    @Column
    public int idAnioAcademico;
    @Column
    public String seccion;
    @Column
    public String periodo;
    @Column
    public String nivelAcademico;
    @Column
    public int complejo;
    @Column
    public int empleadoId;
    @Column
    public int programaEduId;
    @Column
    public String nombrePrograma;
    @Column
    public boolean tooglePrograma;
    @Column
    public String numeroAula;
    @Column
    public String descripcionAula;
    @Column
    public int planCursoId;
    @Column
    public int silaboEventoId;

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public int getComplejo() {
        return complejo;
    }

    public void setComplejo(int complejo) {
        this.complejo = complejo;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProgramaEduId() {
        return programaEduId;
    }

    public void setProgramaEduId(int programaEduId) {
        this.programaEduId = programaEduId;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public boolean isTooglePrograma() {
        return tooglePrograma;
    }

    public void setTooglePrograma(boolean tooglePrograma) {
        this.tooglePrograma = tooglePrograma;
    }

    public String getNumeroAula() {
        return numeroAula;
    }

    public void setNumeroAula(String numeroAula) {
        this.numeroAula = numeroAula;
    }

    public String getDescripcionAula() {
        return descripcionAula;
    }

    public void setDescripcionAula(String descripcionAula) {
        this.descripcionAula = descripcionAula;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }
}
