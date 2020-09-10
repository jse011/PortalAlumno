package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class PreguntaPA extends BaseModel {
    @PrimaryKey
    private String preguntaPAId;
    @Column
    private int bloqueado;
    @Column
    private int desempenioIcdId;
    @Column
    private String rubroEvalProcesoId;
    @Column
    private String pregunta;
    @Column
    private int countEvaluado;
    @Column
    private int countSinEvaluar;
    @Column
    private int version;
    @Column
    private int usuarioId;
    @Column
    private String tipoNotaId;
    @Column
    private String tipoId;
    @Column
    private int sesionAprendizajeId;

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public String getPreguntaPAId() {
        return preguntaPAId;
    }

    public void setPreguntaPAId(String preguntaPAId) {
        this.preguntaPAId = preguntaPAId;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getDesempenioIcdId() {
        return desempenioIcdId;
    }

    public void setDesempenioIcdId(int desempenioIcdId) {
        this.desempenioIcdId = desempenioIcdId;
    }

    public String getRubroEvalProcesoId() {
        return rubroEvalProcesoId;
    }

    public void setRubroEvalProcesoId(String rubroEvalProcesoId) {
        this.rubroEvalProcesoId = rubroEvalProcesoId;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getCountEvaluado() {
        return countEvaluado;
    }

    public void setCountEvaluado(int countEvaluado) {
        this.countEvaluado = countEvaluado;
    }

    public int getCountSinEvaluar() {
        return countSinEvaluar;
    }

    public void setCountSinEvaluar(int countSinEvaluar) {
        this.countSinEvaluar = countSinEvaluar;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipoId() {
        return tipoId;
    }

    public String getTipoNotaId() {
        return tipoNotaId;
    }

    public void setTipoNotaId(String tipoNotaId) {
        this.tipoNotaId = tipoNotaId;
    }
}
