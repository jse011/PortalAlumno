package com.consultoraestrategia.ss_portalalumno.pregunta.entities;

public class PreguntaUi {
    private String id;
    private String titulo;
    private String notaIcono;
    private String notaTitulo;
    private String color;
    private int bloqueado;
    private String tipoId;
    private String foto;
    private boolean offline;

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

    public String getNotaIcono() {
        return notaIcono;
    }

    public void setNotaIcono(String notaIcono) {
        this.notaIcono = notaIcono;
    }

    public String getNotaTitulo() {
        return notaTitulo;
    }

    public void setNotaTitulo(String notaTitulo) {
        this.notaTitulo = notaTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreguntaUi that = (PreguntaUi) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public boolean getOffline() {
        return offline;
    }
}
