package com.consultoraestrategia.ss_portalalumno.main.entities;

/**
 * Created by irvinmarin on 16/10/2017.
 */

public class ProgramaEduactivoUI {
    private int idPrograma;
    private String nombrePrograma;
    private boolean seleccionado;

    public ProgramaEduactivoUI(int idPrograma, String nombrePrograma) {
        this.idPrograma = idPrograma;
        this.nombrePrograma = nombrePrograma;
    }

    public ProgramaEduactivoUI() {
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @Override
    public String toString() {
        return "ProgramaEduactivosUI{" +
                "idPrograma=" + idPrograma +
                ", nombrePrograma='" + nombrePrograma + '\'' +
                '}';
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProgramaEduactivoUI that = (ProgramaEduactivoUI) o;

        return idPrograma == that.idPrograma;
    }

    @Override
    public int hashCode() {
        return idPrograma;
    }
}
