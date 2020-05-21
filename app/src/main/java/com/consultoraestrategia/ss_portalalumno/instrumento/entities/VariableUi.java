package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

import java.util.List;

public class VariableUi {
    private int postion;
    private int variableId;
    private String Nombre;
    private int tipoInputRespuestaId;
    private int tipoRespuestaId;
    private String inputRespuesta;
    private int puntaje;
    private String variableObservadaId;
    private int puntajeObtenido;
    private List<ValorUi> valores;
    private String path;
    private int instrumentoEvalId;
    private String respuestaActual;
    private String instrumentoObservadoId;
    private boolean enviado;

    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTipoInputRespuestaId() {
        return tipoInputRespuestaId;
    }

    public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
        this.tipoInputRespuestaId = tipoInputRespuestaId;
    }

    public int getTipoRespuestaId() {
        return tipoRespuestaId;
    }

    public void setTipoRespuestaId(int tipoRespuestaId) {
        this.tipoRespuestaId = tipoRespuestaId;
    }

    public String getInputRespuesta() {
        return inputRespuesta;
    }

    public void setInputRespuesta(String inputRespuesta) {
        this.inputRespuesta = inputRespuesta;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(int puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public String getVariableObservadaId() {
        return variableObservadaId;
    }

    public void setVariableObservadaId(String variableObservadaId) {
        this.variableObservadaId = variableObservadaId;
    }

    public void setValores(List<ValorUi> valores) {
        this.valores = valores;
    }

    public List<ValorUi> getValores() {
        return valores;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
    }

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }

    public String getRespuestaActual() {
        return respuestaActual;
    }

    public void setRespuestaActual(String respuestaActual) {
        this.respuestaActual = respuestaActual;
    }

    public void setInstrumentoObservadoId(String instrumentoObservadoId) {
        this.instrumentoObservadoId = instrumentoObservadoId;
    }

    public String getInstrumentoObservadoId() {
        return instrumentoObservadoId;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public boolean getEnviado() {
        return enviado;
    }
}
