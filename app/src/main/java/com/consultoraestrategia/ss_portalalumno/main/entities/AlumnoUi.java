package com.consultoraestrategia.ss_portalalumno.main.entities;

import java.util.List;

public class AlumnoUi {
    private int personaId;
    private int usuarioId;
    private String nombre;
    private String foto;
    private String fotoApoderado;
    private boolean habilitarAcceso;
    private String fechaNacimiento;
    private String celular;
    private String correo;
    private List<FamiliaUi> familiaUiList;
    private String nombre2;

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setFotoApoderado(String fotoApoderado) {
        this.fotoApoderado = fotoApoderado;
    }

    public String getFotoApoderado() {
        return fotoApoderado;
    }

    public void setHabilitarAcceso(boolean habilitarAcceso) {
        this.habilitarAcceso = habilitarAcceso;
    }

    public boolean getHabilitarAcceso() {
        return habilitarAcceso;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<FamiliaUi> getFamiliaUiList() {
        return familiaUiList;
    }

    public void setFamiliaUiList(List<FamiliaUi> familiaUiList) {
        this.familiaUiList = familiaUiList;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre2() {
        return nombre2;
    }
}
