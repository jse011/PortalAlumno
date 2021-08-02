package com.consultoraestrategia.ss_portalalumno.main.entities;

public class TipoEventoUi {
    String nombre;
    EventoIconoEnumUI tipo;
    boolean toogle;
    boolean disable;
    int id;

    public enum EventoIconoEnumUI{
        DEFAULT, EVENTO, NOTICIA, ACTIVIDAD, TAREA, CITA, AGENDA, TODOS
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EventoIconoEnumUI getTipo() {
        return tipo;
    }

    public void setTipo(EventoIconoEnumUI tipo) {
        this.tipo = tipo;
    }

    public boolean isToogle() {
        return toogle;
    }

    public void setToogle(boolean toogle) {
        this.toogle = toogle;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
