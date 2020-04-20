package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

import java.util.List;

/**
 * Created by irvinmarin on 17/11/2017.
 */

public class HeaderTareasAprendizajeUI {
    //HeaderTareasAprendizajeUI

    public int idUnidadAprendizaje;
    public int idSesionAprendizaje;
    public int idSilaboEvento;
    public String tituloSesionAprendizaje;
    public boolean isDocente;
    public List<TareasUI> tareasUIList;
    public boolean calendarioEditar;
    private int nroUnidad;

    public HeaderTareasAprendizajeUI() {
    }

    public HeaderTareasAprendizajeUI(int idUnidadAprendizaje, int idSesionAprendizaje, int idSilaboEvento, String tituloSesionAprendizaje, boolean isDocente, List<TareasUI> tareasUIList) {
        this.idUnidadAprendizaje = idUnidadAprendizaje;
        this.idSesionAprendizaje = idSesionAprendizaje;
        this.idSilaboEvento = idSilaboEvento;
        this.tituloSesionAprendizaje = tituloSesionAprendizaje;
        this.isDocente = isDocente;
        this.tareasUIList = tareasUIList;
    }

    public int getIdSesionAprendizaje() {
        return idSesionAprendizaje;
    }

    public void setIdSesionAprendizaje(int idSesionAprendizaje) {
        this.idSesionAprendizaje = idSesionAprendizaje;
    }

    public boolean isDocente() {
        return isDocente;
    }

    public void setDocente(boolean docente) {
        isDocente = docente;
    }

    public int getIdSilaboEvento() {
        return idSilaboEvento;
    }

    public void setIdSilaboEvento(int idSilaboEvento) {
        this.idSilaboEvento = idSilaboEvento;
    }

    public List<TareasUI> getTareasUIList() {
        return tareasUIList;
    }

    public void setTareasUIList(List<TareasUI> tareasUIList) {
        this.tareasUIList = tareasUIList;
    }

    public int getIdUnidadAprendizaje() {
        return idUnidadAprendizaje;
    }

    public void setIdUnidadAprendizaje(int idUnidadAprendizaje) {
        this.idUnidadAprendizaje = idUnidadAprendizaje;
    }

    public String getTituloSesionAprendizaje() {
        return tituloSesionAprendizaje;
    }

    public void setTituloSesionAprendizaje(String tituloSesionAprendizaje) {
        this.tituloSesionAprendizaje = tituloSesionAprendizaje;
    }

    @Override
    public String toString() {
        return "HeaderTareasAprendizajeUI{" +
                "idUnidadAprendizaje=" + idUnidadAprendizaje +
                ", idSesionAprendizaje=" + idSesionAprendizaje +
                ", idSilaboEvento=" + idSilaboEvento +
                ", tituloSesionAprendizaje='" + tituloSesionAprendizaje + '\'' +
                ", isDocente=" + isDocente +
                ", tareasUIList=" + tareasUIList +
                '}';
    }

    public boolean isCalendarioEditar() {
        return calendarioEditar;
    }

    public void setCalendarioEditar(boolean calendarioEditar) {
        this.calendarioEditar = calendarioEditar;
    }

    public int getNroUnidad() {
        return nroUnidad;
    }

    public void setNroUnidad(int nroUnidad) {
        this.nroUnidad = nroUnidad;
    }
}
