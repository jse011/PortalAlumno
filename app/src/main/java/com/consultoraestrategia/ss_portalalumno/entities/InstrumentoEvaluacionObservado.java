package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class InstrumentoEvaluacionObservado extends BaseEntity {
    @Column
    private String InstrumentoObservadoId;
    @Column
    private int PersonaId;
    @Column
    private long FechaEvaluacion;
    @Column
    private int UsuarioId;
    @Column
    private int InstrumentoEvalId;
    @Column
    private String UnidadAnalisis;
    @Column
    private String Hecho;
    @Column
    private String Poblacion;
    @Column
    private int ObjetoId;
    @Column
    private String RecopilacionId;
    @Column
    private int CantidadPreguntas;
    @Column
    private int RespuestasCorrectas;
    @Column
    private int RespuestasIncorrectas;

    public String getInstrumentoObservadoId() {
        return InstrumentoObservadoId;
    }

    public void setInstrumentoObservadoId(String instrumentoObservadoId) {
        InstrumentoObservadoId = instrumentoObservadoId;
    }

    public int getPersonaId() {
        return PersonaId;
    }

    public void setPersonaId(int personaId) {
        PersonaId = personaId;
    }

    public long getFechaEvaluacion() {
        return FechaEvaluacion;
    }

    public void setFechaEvaluacion(long fechaEvaluacion) {
        FechaEvaluacion = fechaEvaluacion;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getInstrumentoEvalId() {
        return InstrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        InstrumentoEvalId = instrumentoEvalId;
    }

    public String getUnidadAnalisis() {
        return UnidadAnalisis;
    }

    public void setUnidadAnalisis(String unidadAnalisis) {
        UnidadAnalisis = unidadAnalisis;
    }

    public String getHecho() {
        return Hecho;
    }

    public void setHecho(String hecho) {
        Hecho = hecho;
    }

    public String getPoblacion() {
        return Poblacion;
    }

    public void setPoblacion(String poblacion) {
        Poblacion = poblacion;
    }

    public int getObjetoId() {
        return ObjetoId;
    }

    public void setObjetoId(int objetoId) {
        ObjetoId = objetoId;
    }

    public String getRecopilacionId() {
        return RecopilacionId;
    }

    public void setRecopilacionId(String recopilacionId) {
        RecopilacionId = recopilacionId;
    }

    public int getCantidadPreguntas() {
        return CantidadPreguntas;
    }

    public void setCantidadPreguntas(int cantidadPreguntas) {
        CantidadPreguntas = cantidadPreguntas;
    }

    public int getRespuestasCorrectas() {
        return RespuestasCorrectas;
    }

    public void setRespuestasCorrectas(int respuestasCorrectas) {
        RespuestasCorrectas = respuestasCorrectas;
    }

    public int getRespuestasIncorrectas() {
        return RespuestasIncorrectas;
    }

    public void setRespuestasIncorrectas(int respuestasIncorrectas) {
        RespuestasIncorrectas = respuestasIncorrectas;
    }
}
