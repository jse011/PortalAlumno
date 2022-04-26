package com.consultoraestrategia.ss_portalalumno.main.entities;

public class AnioAcademicoUi {
    public String colorTarjetQR;
    private int anioAcademicoId;
    private String nombre;
    private int georeferenciaId;
    private int entidadId;
    private String fotoEntidad;
    private String nombreGeoref;

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setFotoEntidad(String fotoEntidad) {
        this.fotoEntidad = fotoEntidad;
    }

    public String getFotoEntidad() {
        return fotoEntidad;
    }

    public String getNombreGeoref() {
        return nombreGeoref;
    }

    public void setNombreGeoref(String nombreGeoref) {
        this.nombreGeoref = nombreGeoref;
    }


}
