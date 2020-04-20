package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

public class RubroEvalProcesoUi {
    public String id;
    public String titulo;
    public TipoRubroEvalProcesoUi tipoRubroEvalProcesoUi = TipoRubroEvalProcesoUi.UNIDIMENCIONAL;
    public FormaRubroEvalProcesoUi formaRubroEvalProcesoUi = FormaRubroEvalProcesoUi.INDIVIDUAL;
    public String cantidadEvaluados;

    public RubroEvalProcesoUi() {
    }

    public String getCantidadEvaluados() {
        return cantidadEvaluados;
    }

    public void setCantidadEvaluados(String cantidadEvaluados) {
        this.cantidadEvaluados = cantidadEvaluados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoRubroEvalProcesoUi getTipoRubroEvalProcesoUi() {
        return tipoRubroEvalProcesoUi;
    }

    public void setTipoRubroEvalProcesoUi(TipoRubroEvalProcesoUi tipoRubroEvalProcesoUi) {
        this.tipoRubroEvalProcesoUi = tipoRubroEvalProcesoUi;
    }

    public FormaRubroEvalProcesoUi getFormaRubroEvalProcesoUi() {
        return formaRubroEvalProcesoUi;
    }

    public void setFormaRubroEvalProcesoUi(FormaRubroEvalProcesoUi formaRubroEvalProcesoUi) {
        this.formaRubroEvalProcesoUi = formaRubroEvalProcesoUi;
    }

    @Override
    public String toString() {
        return "RubroEvalProcesoUi{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", tipoRubroEvalProcesoUi=" + tipoRubroEvalProcesoUi +
                ", formaRubroEvalProcesoUi=" + formaRubroEvalProcesoUi +
                '}';
    }
}
