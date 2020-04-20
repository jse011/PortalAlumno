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
public class CursosDetHorario extends BaseModel {
    @Column
    @PrimaryKey
    private int idCursosDetHorario;
    @Column
    private int idDetHorario;
    @Column
    private int idCargaCurso;

    public CursosDetHorario() {
    }

    public CursosDetHorario(int idCursosDetHorario, int idDetHorario, int idCargaCurso) {
        this.idCursosDetHorario = idCursosDetHorario;
        this.idDetHorario = idDetHorario;
        this.idCargaCurso = idCargaCurso;
    }

    public int getIdCursosDetHorario() {
        return idCursosDetHorario;
    }

    public void setIdCursosDetHorario(int idCursosDetHorario) {
        this.idCursosDetHorario = idCursosDetHorario;
    }

    public int getIdDetHorario() {
        return idDetHorario;
    }

    public void setIdDetHorario(int idDetHorario) {
        this.idDetHorario = idDetHorario;
    }

    public int getIdCargaCurso() {
        return idCargaCurso;
    }

    public void setIdCargaCurso(int idCargaCurso) {
        this.idCargaCurso = idCargaCurso;
    }

    @Override
    public String toString() {
        return "CursosDetHorario{" +
                "idCursosDetHorario=" + idCursosDetHorario +
                ", idDetHorario=" + idDetHorario +
                ", idCargaCurso=" + idCargaCurso +
                '}';
    }
}
