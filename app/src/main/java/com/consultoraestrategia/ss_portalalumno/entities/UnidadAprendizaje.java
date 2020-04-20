package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 18/08/2017.
 */
@Table(database = AppDatabase.class)
public class UnidadAprendizaje extends BaseRelEntity {
    public static final int ESTADO_CREADO = 294, ESTADO_AUTORIZADO = 301 , ESTADO_ELIMINADO = 295;
    @PrimaryKey
    private int unidadAprendizajeId;
    @Column
    private int nroUnidad;
    @Column
    private String titulo;
    @Column
    private String situacionSignificativa;
    @Column
    private int nroSemanas;
    @Column
    private int nroHoras;
    @Column
    private int nroSesiones;
    @Column
    private int estadoId;
    @Column
    private int silaboEventoId;
    @Column
    private String situacionSignificativaComplementaria;
    @Column
    private String desafio;
    @Column
    private String reto;
    @Column
    private boolean toogle;

    public UnidadAprendizaje() {
    }


    public String getSituacionSignificativaComplementaria() {
        return situacionSignificativaComplementaria;
    }

    public void setSituacionSignificativaComplementaria(String situacionSignificativaComplementaria) {
        this.situacionSignificativaComplementaria = situacionSignificativaComplementaria;
    }

    public String getDesafio() {
        return desafio;
    }

    public void setDesafio(String desafio) {
        this.desafio = desafio;
    }

    public String getReto() {
        return reto;
    }

    public void setReto(String reto) {
        this.reto = reto;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public int getNroUnidad() {
        return nroUnidad;
    }

    public void setNroUnidad(int nroUnidad) {
        this.nroUnidad = nroUnidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSituacionSignificativa() {
        return situacionSignificativa;
    }

    public void setSituacionSignificativa(String situacionSignificativa) {
        this.situacionSignificativa = situacionSignificativa;
    }

    public int getNroSemanas() {
        return nroSemanas;
    }

    public void setNroSemanas(int nroSemanas) {
        this.nroSemanas = nroSemanas;
    }

    public int getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(int nroHoras) {
        this.nroHoras = nroHoras;
    }

    public int getNroSesiones() {
        return nroSesiones;
    }

    public void setNroSesiones(int nroSesiones) {
        this.nroSesiones = nroSesiones;
    }



    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public boolean isToogle() {
        return toogle;
    }

    public void setToogle(boolean toogle) {
        this.toogle = toogle;
    }
}
