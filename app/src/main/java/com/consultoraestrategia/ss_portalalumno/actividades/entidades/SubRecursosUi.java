package com.consultoraestrategia.ss_portalalumno.actividades.entidades;

import java.util.List;

/**
 * Created by kike on 09/02/2018.
 */

public class SubRecursosUi {
    private int conteoSubRecurso;
    private String nombreSubRecurso;
    private List<RecursosUi> recursosUiList;

    public SubRecursosUi(int conteoSubRecurso, String nombreSubRecurso, List<RecursosUi> recursosUiList) {
        this.conteoSubRecurso = conteoSubRecurso;
        this.nombreSubRecurso = nombreSubRecurso;
        this.recursosUiList = recursosUiList;
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
}
