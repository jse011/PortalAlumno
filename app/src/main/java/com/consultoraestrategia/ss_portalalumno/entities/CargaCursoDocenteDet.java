package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 6/07/2018.
 */
@Table(database = AppDatabase.class)
public class CargaCursoDocenteDet extends BaseModel {
    @PrimaryKey
    int cargaCursoDocenteId;
    @PrimaryKey
    int alumnoId;

    public CargaCursoDocenteDet() {
    }

    public int getCargaCursoDocenteId() {
        return cargaCursoDocenteId;
    }

    public void setCargaCursoDocenteId(int cargaCursoDocenteId) {
        this.cargaCursoDocenteId = cargaCursoDocenteId;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }
}
