package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class PreguntaRespuestaPA extends BaseModel {
    @PrimaryKey
    private String preguntaRespuestaPAId;
    @Column
    private String preguntaPAId;
    @Column
    private String enunciado;
    @Column
    private long fechaHora;
    @Column
    private int personaId;
    @Column
    private String parentId;

    public String getPreguntaRespuestaPAId() {
        return preguntaRespuestaPAId;
    }

    public void setPreguntaRespuestaPAId(String preguntaRespuestaPAId) {
        this.preguntaRespuestaPAId = preguntaRespuestaPAId;
    }

    public String getPreguntaPAId() {
        return preguntaPAId;
    }

    public void setPreguntaPAId(String preguntaPAId) {
        this.preguntaPAId = preguntaPAId;
    }

    public long getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(long fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
