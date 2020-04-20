package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.database.Exclude;

/**
 * Created by CCIE on 16/10/2017.
 */
//Execpecion del uso del BaseRelEntity ya que esta tabla se va actualizar
@Table(database = AppDatabase.class)
public class Icds extends BaseRelEntity {
    public static final int TIPO_SER = 524, TIPO_HACER = 523, TIPO_SABER = 525;
    @Unique
    @PrimaryKey
    private int icdId;
    //@Exclude
    private String idIcdDevice;
    @Column
    private int desempenioId;
    @Column
    private String titulo;
    @Column
    private String descripcion;
    @Column
    private String estado;
    @Column
    private String peso;
    @Column
    private String alias;
    @Column
    private int tipoId;


    public Icds() {
    }

    public int getIcdId() {
        return icdId;
    }

    public void setIcdId(int icdId) {
        this.icdId = icdId;
    }

    public int getDesempenioId() {
        return desempenioId;
    }

    public void setDesempenioId(int desempenioId) {
        this.desempenioId = desempenioId;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    //Relaciones
    private List<CampoTematico> campoTematicoList = new ArrayList<>();

    public List<CampoTematico> getCampoTematicoList() {
        return campoTematicoList;
    }

    public void setCampoTematicoList(List<CampoTematico> campoTematicoList) {
        this.campoTematicoList.clear();
        this.campoTematicoList.addAll(campoTematicoList);
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }
}
