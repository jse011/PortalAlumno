package com.consultoraestrategia.ss_portalalumno.global.entities;

public class GbTareaUi {
    private String tareaId;
    private String nombre;
    private String descripicion;
    private long fechaCreacionTarea;
    private long fechaLimite;
    private String horaEntrega;

    public String getTareaId() {
        return tareaId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripicion() {
        return descripicion;
    }

    public void setDescripicion(String descripicion) {
        this.descripicion = descripicion;
    }

    public long getFechaCreacionTarea() {
        return fechaCreacionTarea;
    }

    public void setFechaCreacionTarea(long fechaCreacionTarea) {
        this.fechaCreacionTarea = fechaCreacionTarea;
    }

    public long getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(long fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    @Override
    public String toString() {
        return "GbTareaUi{" +
                "tareaId='" + tareaId + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripicion='" + descripicion + '\'' +
                ", fechaCreacionTarea=" + fechaCreacionTarea +
                ", fechaLimite=" + fechaLimite +
                ", horaEntrega='" + horaEntrega + '\'' +
                '}';
    }
}
