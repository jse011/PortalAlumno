package com.consultoraestrategia.ss_portalalumno.main.entities;

public class AlumnoUi {
    private int personaId;
    private int usuarioId;
    private String nombre;
    private String foto;
    private String fotoApoderado;

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
}
