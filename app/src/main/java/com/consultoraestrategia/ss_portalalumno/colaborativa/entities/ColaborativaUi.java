package com.consultoraestrategia.ss_portalalumno.colaborativa.entities;

public class ColaborativaUi {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public enum Tipo{GOOGLEDRIVE, GOOGLEDOCS, JAMBOARD, KAHOOT,MEET,ZOOM, GRABACION}
    private String nombre;
    private String descripcion;
    private Tipo tipo = Tipo.ZOOM;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
