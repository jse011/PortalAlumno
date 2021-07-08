package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

import java.util.List;

public class InstrumentoUi {
    public double PuntoBase;
    public double PuntoActuales;
    public double Porcentaje;
    private int instrumentoEvalId;
    private String nombre;
    private int cantidadPregunta;
    private List<VariableUi> variables;
    private int cantidadPreguntaResueltas;
    private int catidadPreguntasSinEnviar;
    private int porcentaje;
    private String color;
    private String color2;
    private String tipoNotaId;
    private String rubroEvaluacionId;
    private List<RubroDetalleUi> rubroDetalleUiList;
    private int nota;
    private String aliasValorTipoNota;
    private String tituloValorTipoNota;
    private String iconoValorTipoNota;
    private int tipoIdTipoNota;

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

    public void setTipoNotaId(String tipoNotaId) {
        this.tipoNotaId = tipoNotaId;
    }

    public String getTipoNotaId() {
        return tipoNotaId;
    }

    public void setRubroEvaluacionId(String rubroEvaluacionId) {
        this.rubroEvaluacionId = rubroEvaluacionId;
    }

    public void setRubroDetalleUiList(List<RubroDetalleUi> rubroDetalleUiList) {
        this.rubroDetalleUiList = rubroDetalleUiList;
    }

    public List<RubroDetalleUi> getRubroDetalleUiList() {
        return rubroDetalleUiList;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    public void setAliasValorTipoNota(String aliasValorTipoNota) {
        this.aliasValorTipoNota = aliasValorTipoNota;
    }

    public String getAliasValorTipoNota() {
        return aliasValorTipoNota;
    }

    public void setTituloValorTipoNota(String tituloValorTipoNota) {
        this.tituloValorTipoNota = tituloValorTipoNota;
    }

    public String getTituloValorTipoNota() {
        return tituloValorTipoNota;
    }

    public void setIconoValorTipoNota(String iconoValorTipoNota) {
        this.iconoValorTipoNota = iconoValorTipoNota;
    }

    public String getIconoValorTipoNota() {
        return iconoValorTipoNota;
    }

    public void setTipoIdTipoNota(int tipoIdTipoNota) {
        this.tipoIdTipoNota = tipoIdTipoNota;
    }

    public int getTipoIdTipoNota() {
        return tipoIdTipoNota;
    }
}
