package com.consultoraestrategia.ss_portalalumno.entities.firebase;

public class FBSesionAprendizaje {
    private int EstadoId;
    private String FechaEjecucion;
    private int Horas;
    private int NroSesion;
    private int SesionAprendizajeId;
    private String Titulo;
    private int UnidadAprendizajeId;
    private int EstadoEjecucionId;
    public int getEstadoId() {
        return EstadoId;
    }

    public void setEstadoId(int estadoId) {
        EstadoId = estadoId;
    }

    public String getFechaEjecucion() {
        return FechaEjecucion;
    }

    public void setFechaEjecucion(String fechaEjecucion) {
        FechaEjecucion = fechaEjecucion;
    }

    public int getHoras() {
        return Horas;
    }

    public void setHoras(int horas) {
        Horas = horas;
    }

    public int getNroSesion() {
        return NroSesion;
    }

    public void setNroSesion(int nroSesion) {
        NroSesion = nroSesion;
    }

    public int getSesionAprendizajeId() {
        return SesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        SesionAprendizajeId = sesionAprendizajeId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getUnidadAprendizajeId() {
        return UnidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        UnidadAprendizajeId = unidadAprendizajeId;
    }

    public int getEstadoEjecucionId() {
        return EstadoEjecucionId;
    }

    public void setEstadoEjecucionId(int estadoEjecucionId) {
        EstadoEjecucionId = estadoEjecucionId;
    }
}
