package com.consultoraestrategia.ss_portalalumno.instrumento.entities;

public class EncuestaUi {

    private String nombre;
    private int cantidadPregunta;
    private int puntos;
    private boolean verResultados;
    private String urlEncuesta;
    private String urlResultado;

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

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isVerResultados() {
        return verResultados;
    }

    public void setVerResultados(boolean verResultados) {
        this.verResultados = verResultados;
    }

    public void setUrlEncuesta(String urlEncuesta) {
        this.urlEncuesta = urlEncuesta;
    }

    public String getUrlEncuesta() {
        return urlEncuesta;
    }

    public void setUrlResultado(String urlResultado) {
        this.urlResultado = urlResultado;
    }

    public String getUrlResultado() {
        return urlResultado;
    }
}
