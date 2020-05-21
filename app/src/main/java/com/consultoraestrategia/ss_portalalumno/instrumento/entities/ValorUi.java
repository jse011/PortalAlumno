package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

public class ValorUi {
    private int valorId;
    
    private int variableId;
    
    private int valor;
    
    private String etiqueta;
    
    private String descripcion;
    
    private String iconoValor;
    
    private int tipoInputRespuestaId;
    
    private String inputRespuesta;
    
    private int puntaje;
    
    private String path;

    private boolean selected;

    public int getValorId() {
        return valorId;
    }

    public void setValorId(int valorId) {
        this.valorId = valorId;
    }

    public int getVariableId() {
        return variableId;
    }

    public void setVariableId(int variableId) {
        this.variableId = variableId;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIconoValor() {
        return iconoValor;
    }

    public void setIconoValor(String iconoValor) {
        this.iconoValor = iconoValor;
    }

    public int getTipoInputRespuestaId() {
        return tipoInputRespuestaId;
    }

    public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
        this.tipoInputRespuestaId = tipoInputRespuestaId;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
