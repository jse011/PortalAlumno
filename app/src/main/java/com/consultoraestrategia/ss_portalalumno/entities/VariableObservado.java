package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class VariableObservado extends BaseEntity{
    @Column
    private String VariableObservadaId;
    @Column
    private String RespuestaActual;
    @Column
    private int ValorId;
    @Column
    private long FechaEvaluacion;
    @Column
    private String InstrumentoObservadoId;
    @Column
    private int VariableId;
    @Column
    private String HoraInicio;
    @Column
    private String HoraFin;
    @Column
    private int Intentos;
    @Column
    private int PuntajeObtenido;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int silaboEventoId;
    @Column
    private int instrumentoEvalId;

    public String getVariableObservadaId() {
        return VariableObservadaId;
    }

    public void setVariableObservadaId(String variableObservadaId) {
        VariableObservadaId = variableObservadaId;
    }

    public String getRespuestaActual() {
        return RespuestaActual;
    }

    public void setRespuestaActual(String respuestaActual) {
        RespuestaActual = respuestaActual;
    }

    public int getValorId() {
        return ValorId;
    }

    public void setValorId(int valorId) {
        ValorId = valorId;
    }

    public long getFechaEvaluacion() {
        return FechaEvaluacion;
    }

    public void setFechaEvaluacion(long fechaEvaluacion) {
        FechaEvaluacion = fechaEvaluacion;
    }

    public String getInstrumentoObservadoId() {
        return InstrumentoObservadoId;
    }

    public void setInstrumentoObservadoId(String instrumentoObservadoId) {
        InstrumentoObservadoId = instrumentoObservadoId;
    }

    public int getVariableId() {
        return VariableId;
    }

    public void setVariableId(int variableId) {
        VariableId = variableId;
    }

    public String getHoraInicio() {
        return HoraInicio;
    }

    public void setHoraInicio(String horaInicio) {
        HoraInicio = horaInicio;
    }

    public String getHoraFin() {
        return HoraFin;
    }

    public void setHoraFin(String horaFin) {
        HoraFin = horaFin;
    }

    public int getIntentos() {
        return Intentos;
    }

    public void setIntentos(int intentos) {
        Intentos = intentos;
    }

    public int getPuntajeObtenido() {
        return PuntajeObtenido;
    }

    public void setPuntajeObtenido(int puntajeObtenido) {
        PuntajeObtenido = puntajeObtenido;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
    }

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }
}
