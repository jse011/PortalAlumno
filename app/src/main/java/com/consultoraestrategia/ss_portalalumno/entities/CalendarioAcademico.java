package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 03/05/2017.
 */
@Table(database = AppDatabase.class)
public class CalendarioAcademico extends BaseModel {

    public static final int CALENDARIO_ACADEMICO_CREADO = 211;
    public static final int CALENDARIO_ACADEMICO_AUTORIZADO = 212;
    public static final int CALENDARIO_ACADEMICO_ELIMINADO = 213;

    @Unique
    @PrimaryKey
    private int calendarioAcademicoId;
    @Column
    private int programaEduId;
    @Column
    private int idAnioAcademico;
    @Column
    private int estadoId;

    public CalendarioAcademico() {
    }

    public CalendarioAcademico(int calendarioAcademicoId, int programaEduId, int idAnioAcademico, int estadoId) {
        this.calendarioAcademicoId = calendarioAcademicoId;
        this.programaEduId = programaEduId;
        this.idAnioAcademico = idAnioAcademico;
        this.estadoId = estadoId;
    }

    public int getCalendarioAcademicoId() {
        return calendarioAcademicoId;
    }

    public void setCalendarioAcademicoId(int calendarioAcademicoId) {
        this.calendarioAcademicoId = calendarioAcademicoId;
    }

    public int getProgramaEduId() {
        return programaEduId;
    }

    public void setProgramaEduId(int programaEduId) {
        this.programaEduId = programaEduId;
    }

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return "CalendarioAcademico{" +
                "calendarioAcademicoId=" + calendarioAcademicoId +
                ", programaEduId=" + programaEduId +
                ", idAnioAcademico=" + idAnioAcademico +
                ", estadoId=" + estadoId +
                '}';
    }
}
