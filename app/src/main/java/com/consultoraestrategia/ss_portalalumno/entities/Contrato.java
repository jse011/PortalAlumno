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
public class Contrato extends BaseModel {
public static final int PRE_MATRICULA = 189, MATRICULA = 190;

    @Unique
    @PrimaryKey
    private int idContrato;
    @Column
    private int personaId;//Alum_Id
    @Column
    private int idAnioAcademico;
    @Column
    private int nroHno;
    @Column
    private int nivelAcaId;
    @Column
    private int seccionId;
    @Column
    private int periodoId;
    @Column
    private int vigente;
    @Column
    private int estadoId;
    @Column
    private int apoderadoId;


    public Contrato() {
    }

    public Contrato(int idContrato, int personaId, int idAnioAcademico, int nroHno, int nivelAcaId, int seccionId, int periodoId, int estadoId) {
        this.idContrato = idContrato;
        this.personaId = personaId;
        this.idAnioAcademico = idAnioAcademico;
        this.nroHno = nroHno;
        this.nivelAcaId = nivelAcaId;
        this.seccionId = seccionId;
        this.periodoId = periodoId;
        this.estadoId = estadoId;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public int getNroHno() {
        return nroHno;
    }

    public void setNroHno(int nroHno) {
        this.nroHno = nroHno;
    }

    public int getNivelAcaId() {
        return nivelAcaId;
    }

    public void setNivelAcaId(int nivelAcaId) {
        this.nivelAcaId = nivelAcaId;
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

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getVigente() {
        return vigente;
    }

    public void setVigente(int vigente) {
        this.vigente = vigente;
    }

    public int getApoderadoId() {
        return apoderadoId;
    }

    public void setApoderadoId(int apoderadoId) {
        this.apoderadoId = apoderadoId;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", personaId=" + personaId +
                ", idAnioAcademico=" + idAnioAcademico +
                ", nroHno=" + nroHno +
                ", nivelAcaId=" + nivelAcaId +
                ", seccionId=" + seccionId +
                ", periodoId=" + periodoId +
                ", vigente=" + vigente +
                ", estadoId=" + estadoId +
                '}';
    }
}
