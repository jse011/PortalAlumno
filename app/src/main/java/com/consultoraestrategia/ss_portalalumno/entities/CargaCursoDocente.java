package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 6/07/2018.
 */
@Table(database = AppDatabase.class)
public class CargaCursoDocente extends BaseModel {
    @PrimaryKey
    int cargaCursoDocenteId;
    @Column
    int cargaCursoId;
    @Column
    int docenteId;
    @Column
    boolean responsable;

    public CargaCursoDocente() {
    }

    public int getCargaCursoDocenteId() {
        return cargaCursoDocenteId;
    }

    public void setCargaCursoDocenteId(int cargaCursoDocenteId) {
        this.cargaCursoDocenteId = cargaCursoDocenteId;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }
}
