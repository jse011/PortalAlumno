package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SCIEV on 26/07/2018.
 */

public class ParametroChangeUser extends ApiRetrofit.Parameters {

    @SerializedName("vstr_UsuarioId")
    String usuario;
    @SerializedName("vstr_Usuario")
    String usuarioLogin;
    @SerializedName("vstr_Clave")
    String passwordLogin;
    @SerializedName("id_Persona")
    int idpersona;
    @SerializedName("UsuarioId")
    int usuarioId;


    public ParametroChangeUser() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void setPasswordLogin(String passwordLogin) {
        this.passwordLogin = passwordLogin;
    }

    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "ParametroChangeUser{" +
                "usuario='" + usuario + '\'' +
                ", usuarioLogin='" + usuarioLogin + '\'' +
                ", passwordLogin='" + passwordLogin + '\'' +
                ", idpersona='"+ idpersona+'\''+
                '}';
    }
}
