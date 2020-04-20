package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities;

import java.io.Serializable;

public class SesionAprendizajeUi implements Serializable {
    public static final int FLAG_UPDATED = 2;
    private int sesionAprendizajeId;
    private int unidadAprendizajeId;
    private int programaEducativoId;
    private String titulo;
    private int horas;
    private String contenido;
    private int usuarioCreador;
    private int usuarioAccion;
    private int estadoId;
    private long fechaEjecucion;
    private String fechaReprogramacion;
    private String fechaPublicacion;
    private int nroSesion;
    private int rolId;
    private long fechaRealizada;
    private int estadoEjecucionId;
    private String proposito;
    public int syncFlag;
    private long cantidad_recursos;
    private String cantidadEvaluadosSesion;
    private boolean vigente;
    private boolean editar;


    public String getCantidadEvaluadosSesion() {
        return cantidadEvaluadosSesion;
    }

    public void setCantidadEvaluadosSesion(String cantidadEvaluadosSesion) {
        this.cantidadEvaluadosSesion = cantidadEvaluadosSesion;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public long getCantidad_recursos() {
        return cantidad_recursos;
    }

    public void setCantidad_recursos(long cantidad_recursos) {
        this.cantidad_recursos = cantidad_recursos;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(int usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public int getUsuarioAccion() {
        return usuarioAccion;
    }

    public void setUsuarioAccion(int usuarioAccion) {
        this.usuarioAccion = usuarioAccion;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public long getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(long fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getFechaReprogramacion() {
        return fechaReprogramacion;
    }

    public void setFechaReprogramacion(String fechaReprogramacion) {
        this.fechaReprogramacion = fechaReprogramacion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getNroSesion() {
        return nroSesion;
    }

    public void setNroSesion(int nroSesion) {
        this.nroSesion = nroSesion;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public long getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(long fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public int getEstadoEjecucionId() {
        return estadoEjecucionId;
    }

    public void setEstadoEjecucionId(int estadoEjecucionId) {
        this.estadoEjecucionId = estadoEjecucionId;
    }

    public int getProgramaEducativoId() {
        return programaEducativoId;
    }

    public void setProgramaEducativoId(int programaEducativoId) {
        this.programaEducativoId = programaEducativoId;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public int getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
    }

    @Override
    public String toString() {
        return "SesionAprendizajeUi{" +
                "sesionAprendizajeId=" + sesionAprendizajeId +
                ", unidadAprendizajeId=" + unidadAprendizajeId +
                ", titulo='" + titulo + '\'' +
                ", horas=" + horas +
                ", contenido='" + contenido + '\'' +
                ", usuarioCreador=" + usuarioCreador +
                '}';
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
