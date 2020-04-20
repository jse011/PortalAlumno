package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

public class ParametroDisenioUi {


    public int parametroDisenioId;

    public String objeto;

    public String concepto;

    public String nombre;

    public String path;

    public String color1;

    public String color2;

    public String color3;

    public boolean estado;


    public ParametroDisenioUi(int parametroDisenioId, String objeto, String concepto, String nombre, String path, String color1, String color2, String color3, boolean estado) {
        this.parametroDisenioId = parametroDisenioId;
        this.objeto = objeto;
        this.concepto = concepto;
        this.nombre = nombre;
        this.path = path;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.estado = estado;
    }

    public ParametroDisenioUi() {
    }

    public int getParametroDisenioId() {
        return parametroDisenioId;
    }

    public void setParametroDisenioId(int parametroDisenioId) {
        this.parametroDisenioId = parametroDisenioId;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return color3;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ParametroDisenioUi{" +
                "parametroDisenioId=" + parametroDisenioId +
                ", objeto='" + objeto + '\'' +
                ", concepto='" + concepto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", path='" + path + '\'' +
                ", color1='" + color1 + '\'' +
                ", color2='" + color2 + '\'' +
                ", color3='" + color3 + '\'' +
                ", estado=" + estado +
                '}';
    }
}
