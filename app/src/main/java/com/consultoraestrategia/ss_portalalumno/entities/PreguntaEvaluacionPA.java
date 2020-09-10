package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class PreguntaEvaluacionPA extends BaseModel {
    @PrimaryKey
    private int AlumnoId;
    @PrimaryKey
    private String PreguntaId;
    @Column
    private String VariableId;

    public int getAlumnoId() {
        return AlumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        AlumnoId = alumnoId;
    }

    public String getPreguntaId() {
        return PreguntaId;
    }

    public void setPreguntaId(String preguntaId) {
        PreguntaId = preguntaId;
    }

    public String getVariableId() {
        return VariableId;
    }

    public void setVariableId(String variableId) {
        VariableId = variableId;
    }
}
