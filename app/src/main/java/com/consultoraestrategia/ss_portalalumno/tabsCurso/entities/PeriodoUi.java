package com.consultoraestrategia.ss_portalalumno.tabsCurso.entities;

/**
 * Created by kike on 21/10/2017.
 */

public class PeriodoUi {
    private long fechaFin;

    public long getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(long fechaFin) {
        this.fechaFin = fechaFin;
    }

    public enum Estado{Creado, Vigente, Cerrado}
    private int idCalendarioPeriodo;
    private String tipoName;
    private String idPeriodo;
    private boolean status;
    private boolean vigente;
    private Estado estado = Estado.Vigente;
    private String color;
    public PeriodoUi() {

    }

    public PeriodoUi(int idCalendarioPeriodo, String tipoName, String idPeriodo, boolean status) {
        this.idCalendarioPeriodo = idCalendarioPeriodo;
        this.tipoName = tipoName;
        this.status = status;
        this.idPeriodo = idPeriodo;
    }


    public int getIdCalendarioPeriodo() {
        return idCalendarioPeriodo;
    }

    public void setIdCalendarioPeriodo(int idCalendarioPeriodo) {
        this.idCalendarioPeriodo = idCalendarioPeriodo;
    }

    public String getTipoName() {
        return tipoName;
    }

    public void setTipoName(String tipoName) {
        this.tipoName = tipoName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(String idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public boolean isVigente() {
        return vigente;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodoUi)) return false;

        PeriodoUi periodoUi = (PeriodoUi) o;

        return idCalendarioPeriodo == periodoUi.idCalendarioPeriodo;
    }

    @Override
    public int hashCode() {
        return idCalendarioPeriodo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PeriodoUi{" +
                "idCalendarioPeriodo='" + idCalendarioPeriodo + '\'' +
                ", tipoName='" + tipoName + '\'' +
                ", idPeriodo='" + idPeriodo + '\'' +
                ", status=" + status +
                ", vigente=" + vigente +
                ", color='" + color + '\'' +
                '}';
    }
}
