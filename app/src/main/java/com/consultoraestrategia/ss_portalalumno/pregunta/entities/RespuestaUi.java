package com.consultoraestrategia.ss_portalalumno.pregunta.entities;

import java.util.List;

public class RespuestaUi {

    private String contenido;
    private String foto;
    private String fecha;
    private String nombre;
    private List<SubRespuestaUi> subrecursoList;
    private String id;
    private String foto2;
    private boolean responder;
    private String color1;
    private String color2;
    private boolean editar;
    private boolean offline;

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getContenido() {
        return contenido;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSubrecursoList(List<SubRespuestaUi> subrecursoList) {
        this.subrecursoList = subrecursoList;
    }

    public List<SubRespuestaUi> getSubrecursoList() {
        return subrecursoList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RespuestaUi that = (RespuestaUi) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto2() {
        return foto2;
    }

    public boolean isResponder() {
        return responder;
    }

    public void setResponder(boolean responder) {
        this.responder = responder;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor2() {
        return color2;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean getEditar() {
        return editar;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public boolean getOffline() {
        return offline;
    }
}
