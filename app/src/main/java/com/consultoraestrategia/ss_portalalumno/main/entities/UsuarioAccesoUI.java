package com.consultoraestrategia.ss_portalalumno.main.entities;

import android.graphics.drawable.Drawable;

/**
 * Created by irvinmarin on 16/10/2017.
 */

public class UsuarioAccesoUI {
    private int idAcceso;
    private String nombreAcceso;
    private String urlIcono;
    private int idUsuario;
    private int idPersona;
    private Drawable icon;

    public UsuarioAccesoUI() {
    }


    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public UsuarioAccesoUI(int idAcceso, String nombreAcceso, String urlIcono, int idUsuario, int idPersona, Drawable icon) {
        this.idAcceso = idAcceso;
        this.nombreAcceso = nombreAcceso;
        this.urlIcono = urlIcono;
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.icon = icon;
    }

    public String getUrlIcono() {
        return urlIcono;
    }

    public void setUrlIcono(String urlIcono) {
        this.urlIcono = urlIcono;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
        this.idAcceso = idAcceso;
    }

    public String getNombreAcceso() {
        return nombreAcceso;
    }

    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
    }


}
