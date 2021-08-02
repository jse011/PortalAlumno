package com.consultoraestrategia.ss_portalalumno.main.entities;

import android.graphics.Bitmap;

public class FamiliaUi {
    int personaId;
    String nombre;
    String foto;
    String documento;
    String celular;
    String correo;
    String fechaNacimiento;
    String fechaNacimiento2;
    String relacion;
    private boolean toogle;
    boolean change;
    Bitmap fotoFile;
    String image64;


    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaNacimiento2() {
        return fechaNacimiento2;
    }

    public void setFechaNacimiento2(String fechaNacimiento2) {
        this.fechaNacimiento2 = fechaNacimiento2;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public boolean getToogle() {
        return toogle;
    }

    public void setToogle(boolean toogle) {
        this.toogle = toogle;
    }

    public boolean isToogle() {
        return toogle;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public Bitmap getFotoFile() {
        return fotoFile;
    }

    public void setFotoFile(Bitmap fotoFile) {
        this.fotoFile = fotoFile;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }
}
