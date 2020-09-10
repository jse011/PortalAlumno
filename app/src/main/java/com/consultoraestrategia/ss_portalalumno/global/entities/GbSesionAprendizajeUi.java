package com.consultoraestrategia.ss_portalalumno.global.entities;

public class GbSesionAprendizajeUi {
    private int sesionAprendizajeId;
    private String nombreApredizaje;
    private int numero;
    private String preguntaPaId;
    private int actividadId;
    private String recursoId;

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public String getNombreApredizaje() {
        return nombreApredizaje;
    }

    public void setNombreApredizaje(String nombreApredizaje) {
        this.nombreApredizaje = nombreApredizaje;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPreguntaPaId() {
        return preguntaPaId;
    }

    public void setPreguntaPaId(String preguntaPaId) {
        this.preguntaPaId = preguntaPaId;
    }

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    public void setRecursoId(String recursoId) {
        this.recursoId = recursoId;
    }

    public String getRecursoId() {
        return recursoId;
    }

}
