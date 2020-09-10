package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

import java.util.List;

public class InstrumentoUi {
    private int instrumentoEvalId;
    private String nombre;
    private int cantidadPregunta;
    private List<VariableUi> variables;
    private int cantidadPreguntaResueltas;
    private int catidadPreguntasSinEnviar;
    private int porcentaje;
    private String color;
    private String color2;

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

    public void setVariables(List<VariableUi> variables) {
        this.variables = variables;
    }

    public List<VariableUi> getVariables() {
        return variables;
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

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor2() {
        return color2;
    }
}
