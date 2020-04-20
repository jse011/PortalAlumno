package com.consultoraestrategia.ss_portalalumno.entities;

public class AdminService {



    public enum Tipo{USUARIO, CORREO, CORREO_UNICO, DNI, USUARIO_UNICO, USUARIO_NO_VALIDO, CORREO_NO_VALIDO, DNI_NO_VALIDO}

    int UsuarioId;
    boolean Estado;
    int EntidadId;
    int UsuarioExternoId;
    int UsuarioCreadorId;
    int UsuarioAccionId;
    int Opcion;
    int Cantidad;
    String UrlServiceMovil;
    Tipo tipo = Tipo.USUARIO;


    public AdminService() {
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public int getEntidadId() {
        return EntidadId;
    }

    public void setEntidadId(int entidadId) {
        EntidadId = entidadId;
    }

    public int getUsuarioExternoId() {
        return UsuarioExternoId;
    }

    public void setUsuarioExternoId(int usuarioExternoId) {
        UsuarioExternoId = usuarioExternoId;
    }

    public int getUsuarioCreadorId() {
        return UsuarioCreadorId;
    }

    public void setUsuarioCreadorId(int usuarioCreadorId) {
        UsuarioCreadorId = usuarioCreadorId;
    }

    public int getOpcion() {
        return Opcion;
    }

    public void setOpcion(int opcion) {
        Opcion = opcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getUrlServiceMovil() {
        return UrlServiceMovil;
    }

    public void setUrlServiceMovil(String urlServiceMovil) {
        UrlServiceMovil = urlServiceMovil;
    }

    public int getUsuarioAccionId() {
        return UsuarioAccionId;
    }

    public void setUsuarioAccionId(int usuarioAccionId) {
        UsuarioAccionId = usuarioAccionId;
    }

    @Override
    public String toString() {
        return "AdminService{" +
                "UsuarioId=" + UsuarioId +
                ", Estado=" + Estado +
                ", EntidadId=" + EntidadId +
                ", UsuarioExternoId=" + UsuarioExternoId +
                ", UsuarioCreadorId=" + UsuarioCreadorId +
                ", UsuarioAccionId=" + UsuarioAccionId +
                ", Opcion=" + Opcion +
                ", Cantidad=" + Cantidad +
                ", UrlServiceMovil='" + UrlServiceMovil + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
