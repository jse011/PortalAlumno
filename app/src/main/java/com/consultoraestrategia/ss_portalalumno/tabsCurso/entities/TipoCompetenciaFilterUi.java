package com.consultoraestrategia.ss_portalalumno.tabsCurso.entities;

/**
 * Created by SCIEV on 16/05/2018.
 */

public class TipoCompetenciaFilterUi  {

    public enum Origen{BASE, ENFOQUE_TRANSVERSAl }
    private Origen origen;
    String titulo;

    public TipoCompetenciaFilterUi(Origen origen, String titulo) {
        this.origen = origen;
        this.titulo = titulo;
    }

    public Origen getOrigen() {
        return origen;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
