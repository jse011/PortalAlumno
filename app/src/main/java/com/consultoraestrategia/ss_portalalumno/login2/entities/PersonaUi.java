package com.consultoraestrategia.ss_portalalumno.login2.entities;

public class PersonaUi {
    private int id;
    private String nombres;
    private String apellidos;
    private String imagenUrl;
    private String usuario;
    private boolean usarOtraCuenta;
    private boolean eliminar;
    private String urlServiceMovil;
    private String correo;
    private String dni;
    private int opcion;
    private String institucionUrl;
    private String numDoc;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNombres() {
        return nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isUsarOtraCuenta() {
        return usarOtraCuenta;
    }

    public void setUsarOtraCuenta(boolean usarOtraCuenta) {
        this.usarOtraCuenta = usarOtraCuenta;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public String getUrlServiceMovil() {
        return urlServiceMovil;
    }

    public void setUrlServiceMovil(String urlServiceMovil) {
        this.urlServiceMovil = urlServiceMovil;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonaUi personaUi = (PersonaUi) o;

        return id == personaUi.id;
    }

    @Override
    public int hashCode() {
        return id;
    }


    @Override
    public String toString() {
        return "PersonaUi{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", usuario='" + usuario + '\'' +
                ", usarOtraCuenta=" + usarOtraCuenta +
                ", eliminar=" + eliminar +
                ", urlServiceMovil='" + urlServiceMovil + '\'' +
                ", correo='" + correo + '\'' +
                ", dni='" + dni + '\'' +
                ", opcion=" + opcion +
                '}';
    }

    public void setInstitucionUrl(String institucionUrl) {
        this.institucionUrl = institucionUrl;
    }

    public String getInstitucionUrl() {
        return institucionUrl;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getNumDoc() {
        return numDoc;
    }
}
