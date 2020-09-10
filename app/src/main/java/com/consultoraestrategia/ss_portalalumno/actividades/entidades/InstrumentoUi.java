package com.consultoraestrategia.ss_portalalumno.actividades.entidades;

public class InstrumentoUi {
    private int instrumentoEvalId;
    private String nombre;
    private int cantidadPregunta;
    private int cantidadPreguntaResueltas;
    private int catidadPreguntasSinEnviar;
    private int porcentaje;
    private InstrumentoUi instrumentoUi;

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadPregunta() {
        return cantidadPregunta;
    }

    public void setCantidadPregunta(int cantidadPregunta) {
        this.cantidadPregunta = cantidadPregunta;
    }

    public void setCantidadPreguntaResueltas(int cantidadPreguntaResueltas) {
        this.cantidadPreguntaResueltas = cantidadPreguntaResueltas;
    }

    public int getCantidadPreguntaResueltas() {
        return cantidadPreguntaResueltas;
    }

    public void setCatidadPreguntasSinEnviar(int catidadPreguntasSinEnviar) {
        this.catidadPreguntasSinEnviar = catidadPreguntasSinEnviar;
    }

    public int getCatidadPreguntasSinEnviar() {
        return catidadPreguntasSinEnviar;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setInstrumentoUi(InstrumentoUi instrumentoUi) {
        this.instrumentoUi = instrumentoUi;
    }

    public InstrumentoUi getInstrumentoUi() {
        return instrumentoUi;
    }
}
