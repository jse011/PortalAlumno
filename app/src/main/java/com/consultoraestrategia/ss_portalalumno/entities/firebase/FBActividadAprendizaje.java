package com.consultoraestrategia.ss_portalalumno.entities.firebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBActividadAprendizaje {
    private String Actividad;
    private int ActividadAprendizajeId;
    private int CantRecursos;
    private int CantSubactividades;
    private String DescripcionActividad;
    private int SesionAprendizajeId;
    private String TipoActividad;
    private int TipoActividadId;
    private HashMap<String, FBRecursos> RecursoActividad;
    private HashMap<String, FBActividadAprendizaje>  SubActividad;

    public String getActividad() {
        return Actividad;
    }

    public void setActividad(String actividad) {
        Actividad = actividad;
    }

    public int getActividadAprendizajeId() {
        return ActividadAprendizajeId;
    }

    public void setActividadAprendizajeId(int actividadAprendizajeId) {
        ActividadAprendizajeId = actividadAprendizajeId;
    }

    public int getCantRecursos() {
        return CantRecursos;
    }

    public void setCantRecursos(int cantRecursos) {
        CantRecursos = cantRecursos;
    }

    public int getCantSubactividades() {
        return CantSubactividades;
    }

    public void setCantSubactividades(int cantSubactividades) {
        CantSubactividades = cantSubactividades;
    }

    public String getDescripcionActividad() {
        return DescripcionActividad;
    }

    public void setDescripcionActividad(String descripcionActividad) {
        DescripcionActividad = descripcionActividad;
    }

    public int getSesionAprendizajeId() {
        return SesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        SesionAprendizajeId = sesionAprendizajeId;
    }

    public String getTipoActividad() {
        return TipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        TipoActividad = tipoActividad;
    }

    public int getTipoActividadId() {
        return TipoActividadId;
    }

    public void setTipoActividadId(int tipoActividadId) {
        TipoActividadId = tipoActividadId;
    }

    public HashMap<String, FBRecursos> getRecursoActividad() {
        return RecursoActividad;
    }

    public void setRecursoActividad(HashMap<String, FBRecursos> recursoActividad) {
        RecursoActividad = recursoActividad;
    }

    public HashMap<String, FBActividadAprendizaje> getSubActividad() {
        return SubActividad;
    }

    public void setSubActividad(HashMap<String, FBActividadAprendizaje> subActividad) {
        SubActividad = subActividad;
    }
}
