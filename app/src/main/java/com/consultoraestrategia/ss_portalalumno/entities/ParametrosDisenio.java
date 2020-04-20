package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 16/03/2018.
 */
@Table(database = AppDatabase.class)
public class ParametrosDisenio extends BaseModel {

    @PrimaryKey
    private int parametroDisenioId;
    @Column
    private String objeto;
    @Column
    private String concepto;
    @Column
    private String nombre;
    @Column
    private String path;
    @Column
    private String color1;
    @Column
    private String color2;
    @Column
    private String color3;
    @Column
    private boolean estado;

    public ParametrosDisenio() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParametrosDisenio)) return false;

        ParametrosDisenio that = (ParametrosDisenio) o;

        return parametroDisenioId == that.parametroDisenioId;
    }

    @Override
    public int hashCode() {
        return parametroDisenioId;
    }

    @Override
    public String toString() {
        return "ParametrosDisenio{" +
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
