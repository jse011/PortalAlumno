package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irvinmarin on 23/03/2017.
 */
//Execpecion del uso del BaseRelEntity ya que esta tabla se va actualziar
@Table(database = AppDatabase.class)
public class Competencia extends BaseRelEntity {
    public static final int COMPETENCIA_BASE = 347;
    public static final int COMPETENCIA_TRANS = 348;
    public static final int COMPETENCIA_ENFQ = 349;

    @PrimaryKey
    private int competenciaId;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private int superCompetenciaId;
    @Column
    private int tipoId;
    @Column
    private boolean toogle;

    public Competencia() {

    }

    public Competencia(int competenciaId, String nombre, String descripcion, int superCompetenciaId, int tipoId) {
        this.competenciaId = competenciaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.superCompetenciaId = superCompetenciaId;
        this.tipoId = tipoId;
    }

    public int getCompetenciaId() {
        return competenciaId;
    }

    public void setCompetenciaId(int competenciaId) {
        this.competenciaId = competenciaId;
    }

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

    public int getSuperCompetenciaId() {
        return superCompetenciaId;
    }

    public void setSuperCompetenciaId(int superCompetenciaId) {
        this.superCompetenciaId = superCompetenciaId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    @Override
    public String toString() {
        return "Competencia{" +
                "competenciaId=" + competenciaId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", superCompetenciaId=" + superCompetenciaId +
                ", tipoId=" + tipoId +
                '}';
    }


    private List<Competencia> capacidades = new ArrayList<>();
    private List<Icds> indicadores = new ArrayList<>();

    public List<Competencia> getCapacidades() {
        return capacidades;
    }

    public void setCapacidades(List<Competencia> capacidades) {
        this.capacidades.clear();
        this.capacidades.addAll(capacidades);
    }

    public List<Icds> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Icds> indicadores) {
        this.indicadores.clear();
        this.indicadores.addAll(indicadores);
    }

    public boolean isToogle() {
        return toogle;
    }

    public void setToogle(boolean toogle) {
        this.toogle = toogle;
    }
}
