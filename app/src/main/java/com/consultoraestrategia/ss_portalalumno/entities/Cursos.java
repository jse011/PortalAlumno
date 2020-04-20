package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class Cursos extends BaseModel {

    @Column
    @PrimaryKey
    private int cursoId;
    @Column
    private String nombre;
    @Column
    private int estadoId;
    @Column
    private String descripcion;
    @Column
    private String alias;
    @Column
    private int entidadId;
    @Column
    private int nivelAcadId;
    @Column
    private int tipoCursoId;
    @Column
    private int tipoConceptoId;
    @Column
    private String color;
    @Column
    public String creditos;
    @Column
    public String totalHP;
    @Column
    public String totalHT;
    @Column
    public String notaAprobatoria;
    @Column
    public String sumilla;
    @Column
    public int superId;
    @Column
    public int idServicioLaboratorio;
    @Column
    public int horasLaboratorio;
    @Column
    public boolean tipoSubcurso;
    @Column
    public String foto;
    @Column
    public String codigo;

    public Cursos() {
    }

    public Cursos(int cursoId, String nombre, int estadoId, String descripcion, String alias, int entidadId, int nivelAcadId, int tipoCursoId, String color) {
        this.cursoId = cursoId;
        this.nombre = nombre;
        this.estadoId = estadoId;
        this.descripcion = descripcion;
        this.alias = alias;
        this.entidadId = entidadId;
        this.nivelAcadId = nivelAcadId;
        this.tipoCursoId = tipoCursoId;
        this.color = color;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getNivelAcadId() {
        return nivelAcadId;
    }

    public void setNivelAcadId(int nivelAcadId) {
        this.nivelAcadId = nivelAcadId;
    }

    public int getTipoCursoId() {
        return tipoCursoId;
    }

    public void setTipoCursoId(int tipoCursoId) {
        this.tipoCursoId = tipoCursoId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Cursos{" +
                "cursoId=" + cursoId +
                ", nombre='" + nombre + '\'' +
                ", estadoId=" + estadoId +
                ", descripcion='" + descripcion + '\'' +
                ", alias='" + alias + '\'' +
                ", entidadId=" + entidadId +
                ", nivelAcadId=" + nivelAcadId +
                ", tipoCursoId=" + tipoCursoId +
                ", color='" + color + '\'' +
                '}';
    }


    public int getTipoConceptoId() {
        return tipoConceptoId;
    }

    public void setTipoConceptoId(int tipoConceptoId) {
        this.tipoConceptoId = tipoConceptoId;
    }

    public String getCreditos() {
        return creditos;
    }

    public void setCreditos(String creditos) {
        this.creditos = creditos;
    }

    public String getTotalHP() {
        return totalHP;
    }

    public void setTotalHP(String totalHP) {
        this.totalHP = totalHP;
    }

    public String getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(String totalHT) {
        this.totalHT = totalHT;
    }

    public String getNotaAprobatoria() {
        return notaAprobatoria;
    }

    public void setNotaAprobatoria(String notaAprobatoria) {
        this.notaAprobatoria = notaAprobatoria;
    }

    public String getSumilla() {
        return sumilla;
    }

    public void setSumilla(String sumilla) {
        this.sumilla = sumilla;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }

    public int getIdServicioLaboratorio() {
        return idServicioLaboratorio;
    }

    public void setIdServicioLaboratorio(int idServicioLaboratorio) {
        this.idServicioLaboratorio = idServicioLaboratorio;
    }

    public int getHorasLaboratorio() {
        return horasLaboratorio;
    }

    public void setHorasLaboratorio(int horasLaboratorio) {
        this.horasLaboratorio = horasLaboratorio;
    }

    public boolean isTipoSubcurso() {
        return tipoSubcurso;
    }

    public void setTipoSubcurso(boolean tipoSubcurso) {
        this.tipoSubcurso = tipoSubcurso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
