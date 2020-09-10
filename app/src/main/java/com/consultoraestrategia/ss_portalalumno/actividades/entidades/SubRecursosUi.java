package com.consultoraestrategia.ss_portalalumno.actividades.entidades;

import java.util.List;

/**
 * Created by kike on 09/02/2018.
 */

public class SubRecursosUi {
    private int conteoSubRecurso;
    private int actividadId;
    private String nombreSubRecurso;
    private List<RecursosUi> recursosUiList;
    private int instrumentoId;
    private InstrumentoUi instrumentoUi;
    private String color;

    public SubRecursosUi(int actividadId, int conteoSubRecurso, String nombreSubRecurso, List<RecursosUi> recursosUiList, int instrumentoId) {
        this.conteoSubRecurso = conteoSubRecurso;
        this.nombreSubRecurso = nombreSubRecurso;
        this.recursosUiList = recursosUiList;
        this.actividadId = actividadId;
        this.instrumentoId = instrumentoId;
    }

    public int getConteoSubRecurso() {
        return conteoSubRecurso;
    }

    public void setConteoSubRecurso(int conteoSubRecurso) {
        this.conteoSubRecurso = conteoSubRecurso;
    }

    public String getNombreSubRecurso() {
        return nombreSubRecurso;
    }

    public void setNombreSubRecurso(String nombreSubRecurso) {
        this.nombreSubRecurso = nombreSubRecurso;
    }

    public List<RecursosUi> getRecursosUiList() {
        return recursosUiList;
    }

    public void setRecursosUiList(List<RecursosUi> recursosUiList) {
        this.recursosUiList = recursosUiList;
    }

    public int getActividadId() {
        return actividadId;
    }

    public void setActividadId(int actividadId) {
        this.actividadId = actividadId;
    }

    public int getInstrumentoId() {
        return instrumentoId;
    }

    public void setInstrumentoId(int instrumentoId) {
        this.instrumentoId = instrumentoId;
    }

    public void setInstrumentoUi(InstrumentoUi instrumentoUi) {
        this.instrumentoUi = instrumentoUi;
    }

    public InstrumentoUi getInstrumentoUi() {
        return instrumentoUi;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
