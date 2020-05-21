package com.consultoraestrategia.ss_portalalumno.entities.firebase;

public class FBUnidadAprendizaje {
    private int NroHoras;
    private int NroSemanas;
    private int UnidadAprendizajeId;
    private int NroUnidad;
    private int TipoPeriodoId;
    private String Titulo;
    private int NroSesiones;
    private int SilaboEventoId;

    public int getNroHoras() {
        return NroHoras;
    }

    public void setNroHoras(int nroHoras) {
        NroHoras = nroHoras;
    }

    public int getNroSemanas() {
        return NroSemanas;
    }

    public void setNroSemanas(int nroSemanas) {
        NroSemanas = nroSemanas;
    }

    public int getUnidadAprendizajeId() {
        return UnidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        UnidadAprendizajeId = unidadAprendizajeId;
    }

    public int getNroUnidad() {
        return NroUnidad;
    }

    public void setNroUnidad(int nroUnidad) {
        NroUnidad = nroUnidad;
    }

    public int getTipoPeriodoId() {
        return TipoPeriodoId;
    }

    public void setTipoPeriodoId(int tipoPeriodoId) {
        TipoPeriodoId = tipoPeriodoId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getNroSesiones() {
        return NroSesiones;
    }

    public void setNroSesiones(int nroSesiones) {
        NroSesiones = nroSesiones;
    }

    public int getSilaboEventoId() {
        return SilaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        SilaboEventoId = silaboEventoId;
    }
}
