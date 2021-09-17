package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities;

import java.util.List;

public class UnidadAprendizajeUi {


    private int unidadAprendizajeId;
    private int nroUnidad;
    private String titulo;
    private String situacionSignificativa;
    private int nroSemanas;
    private int nroHoras;
    private int nroSesiones;
    private int estadoId;
    private int silaboEventoId;
    private String situacionSignificativaComplementaria;
    private String desafio;
    private String reto;
    private boolean toogle;
    private List<SesionAprendizajeUi> objectListSesiones;

    public List<SesionAprendizajeUi> getObjectListSesiones() {
        return objectListSesiones;
    }

    public void setObjectListSesiones(List<SesionAprendizajeUi> objectListSesiones) {
        this.objectListSesiones = objectListSesiones;
    }

    public UnidadAprendizajeUi() {
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

    public boolean isToogle() {
        return toogle;
    }

    public void setToogle(boolean toogle) {
        this.toogle = toogle;
    }



}
