package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.google.gson.annotations.SerializedName;

public class UsuarioAdminService {

    @SerializedName("Opcion")
    int opcion;
    @SerializedName("Usuario")
    String usuario;
    @SerializedName("Passwordd")
    String passwordd;
    @SerializedName("Correo")
    String correo;
    @SerializedName("NumDoc")
    String numDoc;

    public UsuarioAdminService() {
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswordd() {
        return passwordd;
    }

    public void setPasswordd(String passwordd) {
        this.passwordd = passwordd;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
}
