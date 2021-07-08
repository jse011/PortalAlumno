package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

import java.util.List;

public class RubroDetalleUi {
    int desempenioIcd;
    String tituloRubroDetalle;
    int tipoCompetenciaId;
    int tipoDecempenioId;
    List<VariableUi> variableUiList;
    private int nota;
    private int tipoNotaTipoId;
    private String aliasValorTipoNota;
    private String iconoValorTipoNota;
    private String tituloValorTipoNota;
    private int peso;

    public int getDesempenioIcd() {
        return desempenioIcd;
    }

    public void setDesempenioIcd(int desempenioIcd) {
        this.desempenioIcd = desempenioIcd;
    }

    public String getTituloRubroDetalle() {
        return tituloRubroDetalle;
    }

    public void setTituloRubroDetalle(String tituloRubroDetalle) {
        this.tituloRubroDetalle = tituloRubroDetalle;
    }

    public int getTipoCompetenciaId() {
        return tipoCompetenciaId;
    }

    public void setTipoCompetenciaId(int tipoCompetenciaId) {
        this.tipoCompetenciaId = tipoCompetenciaId;
    }

    public int getTipoDecempenioId() {
        return tipoDecempenioId;
    }

    public void setTipoDecempenioId(int tipoDecempenioId) {
        this.tipoDecempenioId = tipoDecempenioId;
    }

    public List<VariableUi> getVariableUiList() {
        return variableUiList;
    }

    public void setVariableUiList(List<VariableUi> variableUiList) {
        this.variableUiList = variableUiList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RubroDetalleUi)) return false;

        RubroDetalleUi that = (RubroDetalleUi) o;

        return desempenioIcd == that.desempenioIcd;
    }

    @Override
    public int hashCode() {
        return desempenioIcd;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }


    public void setTipoNotaTipoId(int tipoNotaTipoId) {
        this.tipoNotaTipoId = tipoNotaTipoId;
    }

    public int getTipoNotaTipoId() {
        return tipoNotaTipoId;
    }

    public void setAliasValorTipoNota(String aliasValorTipoNota) {
        this.aliasValorTipoNota = aliasValorTipoNota;
    }

    public String getAliasValorTipoNota() {
        return aliasValorTipoNota;
    }

    public void setIconoValorTipoNota(String iconoValorTipoNota) {
        this.iconoValorTipoNota = iconoValorTipoNota;
    }

    public String getIconoValorTipoNota() {
        return iconoValorTipoNota;
    }

    public void setTituloValorTipoNota(String tituloValorTipoNota) {
        this.tituloValorTipoNota = tituloValorTipoNota;
    }

    public String getTituloValorTipoNota() {
        return tituloValorTipoNota;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getPeso() {
        return peso;
    }
}
