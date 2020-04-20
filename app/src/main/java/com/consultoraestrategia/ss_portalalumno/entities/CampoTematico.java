package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Objects;

/**
 * Created by irvinmarin on 27/07/2017.
 */
//Execpecion del uso del BaseRelEntity ya que esta tabla se va actualizar
@Table(database = AppDatabase.class)
public class CampoTematico extends BaseRelEntity {
    @PrimaryKey
    private int campoTematicoId;
    @Column
    private String titulo;
    @Column
    private String descripcion;
    @Column
    private int silaboEventoId;
    @Column
    private int estado;
    @Column
    private int SesionAprendizajeId;
    @Column
    private int parentId;

    public CampoTematico() {
    }

    public int getCampoTematicoId() {
        return campoTematicoId;
    }

    public void setCampoTematicoId(int campoTematicoId) {
        this.campoTematicoId = campoTematicoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getSesionAprendizajeId() {
        return SesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        SesionAprendizajeId = sesionAprendizajeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CampoTematico)) return false;
        CampoTematico that = (CampoTematico) o;
        return campoTematicoId == that.campoTematicoId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(campoTematicoId);
    }

    @Override
    public String toString() {
        return "CampoTematico{" +
                "campoTematicoId=" + campoTematicoId +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", silaboEventoId=" + silaboEventoId +
                ", estado=" + estado +
                ", SesionAprendizajeId=" + SesionAprendizajeId +
                ", parentId=" + parentId +
                '}';
    }
}
