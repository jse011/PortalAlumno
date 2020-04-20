package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 22/03/2017.
 */
@Table(database = AppDatabase.class)
public class DetalleContratoAcad extends BaseModel {

    @Unique
    @PrimaryKey
    private int idContratoDetAcad;
    @Column
    private int idContrato;
    @Column
    private int cargaCursoId;
    @Column
    private int cargaAcademicaId;
    @Column
    private String notaPromedio;
    @Column
    private String escala;

    @Column
    private  int anioAcademicoId;
    @Column
    private  int idNivelAcademico;
    @Column
    private  int cursoId;
    @Column
    private  int grupoId;
    @Column
    private  int aulaId;

    public DetalleContratoAcad() {
    }


    public DetalleContratoAcad(int idContratoDetAcad, int idContrato, int cargaCursoId, int cargaAcademicaId, String notaPromedio, String escala) {
        this.idContratoDetAcad = idContratoDetAcad;
        this.idContrato = idContrato;
        this.cargaCursoId = cargaCursoId;
        this.cargaAcademicaId = cargaAcademicaId;
        this.notaPromedio = notaPromedio;
        this.escala = escala;
    }

    public int getIdContratoDetAcad() {
        return idContratoDetAcad;
    }

    public void setIdContratoDetAcad(int idContratoDetAcad) {
        this.idContratoDetAcad = idContratoDetAcad;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public int getCargaAcademicaId() {
        return cargaAcademicaId;
    }

    public void setCargaAcademicaId(int cargaAcademicaId) {
        this.cargaAcademicaId = cargaAcademicaId;
    }

    public String getNotaPromedio() {
        return notaPromedio;
    }

    public void setNotaPromedio(String notaPromedio) {
        this.notaPromedio = notaPromedio;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }

    public int getIdNivelAcademico() {
        return idNivelAcademico;
    }

    public void setIdNivelAcademico(int idNivelAcademico) {
        this.idNivelAcademico = idNivelAcademico;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(int grupoId) {
        this.grupoId = grupoId;
    }

    public int getAulaId() {
        return aulaId;
    }

    public void setAulaId(int aulaId) {
        this.aulaId = aulaId;
    }

    @Override
    public String toString() {
        return "DetalleContratoAcad{" +
                "idContratoDetAcad=" + idContratoDetAcad +
                ", idContrato=" + idContrato +
                ", cargaCursoId=" + cargaCursoId +
                ", cargaAcademicaId=" + cargaAcademicaId +
                ", notaPromedio=" + notaPromedio +
                ", escala='" + escala + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleContratoAcad)) return false;

        DetalleContratoAcad that = (DetalleContratoAcad) o;

        return getIdContratoDetAcad() == that.getIdContratoDetAcad();
    }

    @Override
    public int hashCode() {
        return getIdContratoDetAcad();
    }
}
