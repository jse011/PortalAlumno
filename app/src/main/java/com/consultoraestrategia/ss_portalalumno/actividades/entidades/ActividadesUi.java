package com.consultoraestrategia.ss_portalalumno.actividades.entidades;

import java.util.List;

/**
 * Created by kike on 07/02/2018.
 */

public class ActividadesUi {
    private String backgroundColor;
    private String nombreActividad;
    private int cantidadRecursos;
    private int tiempoActividad;
    private EEstado eEstado = EEstado.Creado;
    private int estadoId; // Pendiente - Hecho
    private int tiempoActividadDetalle; // lo mismo que tiempoActividad pero en detalle
    private String nombreaActividadDetalle; // lo mismo que nombreActividad pero en detalle
    private String secuenciaNombre;
    private String descripcionActividad;
    private String tipoActividadDetalle;
    private List<RecursosUi> recursosUiList;
    private List<SubRecursosUi> subRecursosUiList;
    private ESecuencia eSecuencia = ESecuencia.Inicio;
    private int id;

    public ActividadesUi(String nombreActividad, int cantidadRecursos, int tiempoActividad, int estadoId, int tiempoActividadDetalle, String nombreaActividadDetalle, String secuenciaNombre, String descripcionActividad, String backgroundColor, List<RecursosUi> recursosUiList, List<SubRecursosUi> subRecursosUiList) {
        this.nombreActividad = nombreActividad;
        this.cantidadRecursos = cantidadRecursos;
        this.tiempoActividad = tiempoActividad;
        this.estadoId = estadoId;
        this.tiempoActividadDetalle = tiempoActividadDetalle;
        this.nombreaActividadDetalle = nombreaActividadDetalle;
        this.secuenciaNombre = secuenciaNombre;
        this.descripcionActividad = descripcionActividad;
        this.backgroundColor = backgroundColor;
        this.recursosUiList = recursosUiList;
        this.subRecursosUiList = subRecursosUiList;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public int getCantidadRecursos() {
        return cantidadRecursos;
    }

    public void setCantidadRecursos(int cantidadRecursos) {
        this.cantidadRecursos = cantidadRecursos;
    }

    public int getTiempoActividad() {
        return tiempoActividad;
    }

    public void setTiempoActividad(int tiempoActividad) {
        this.tiempoActividad = tiempoActividad;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getTiempoActividadDetalle() {
        return tiempoActividadDetalle;
    }

    public void setTiempoActividadDetalle(int tiempoActividadDetalle) {
        this.tiempoActividadDetalle = tiempoActividadDetalle;
    }

    public String getNombreaActividadDetalle() {
        return nombreaActividadDetalle;
    }

    public void setNombreaActividadDetalle(String nombreaActividadDetalle) {
        this.nombreaActividadDetalle = nombreaActividadDetalle;
    }

    public String getSecuenciaNombre() {
        return secuenciaNombre;
    }

    public void setSecuenciaNombre(String secuenciaNombre) {
        this.secuenciaNombre = secuenciaNombre;
    }

    public String getDescripcionActividad() {
        return descripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        this.descripcionActividad = descripcionActividad;
    }

    public String getTipoActividadDetalle() {
        return tipoActividadDetalle;
    }

    public void setTipoActividadDetalle(String tipoActividadDetalle) {
        this.tipoActividadDetalle = tipoActividadDetalle;
    }

    public List<RecursosUi> getRecursosUiList() {
        return recursosUiList;
    }

    public void setRecursosUiList(List<RecursosUi> recursosUiList) {
        this.recursosUiList = recursosUiList;
    }

    public List<SubRecursosUi> getSubRecursosUiList() {
        return subRecursosUiList;
    }

    public void setSubRecursosUiList(List<SubRecursosUi> subRecursosUiList) {
        this.subRecursosUiList = subRecursosUiList;
    }

    public ESecuencia geteSecuencia() {
        return eSecuencia;
    }

    public void seteSecuencia(ESecuencia eSecuencia) {
        this.eSecuencia = eSecuencia;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public EEstado geteEstado() {
        return eEstado;
    }

    public void seteEstado(EEstado eEstado) {
        this.eEstado = eEstado;
    }
}
