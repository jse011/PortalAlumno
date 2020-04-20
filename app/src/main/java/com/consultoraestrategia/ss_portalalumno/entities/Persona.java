package com.consultoraestrategia.ss_portalalumno.entities;



import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;


/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class Persona extends BaseModel implements Serializable {

    @Column
    @PrimaryKey
    int personaId;

    @Column
    private String nombres;
    @Column
    private String apellidoPaterno;
    @Column
    private String apellidoMaterno;
    @Column
    private String celular;
    @Column
    private String telefono;
    @Column
    private String foto;
    @Column
    private String fechaNac;
    @Column
    private String genero;
    @Column
    private String estadoCivil;
    @Column
    private String numDoc;
    @Column
    private String ocupacion;
    @Column
    private int estadoId;
    @Column
    private String correo;
    @Column
    private String direccion;
    @Column
    private String path;

    public Persona() {
    }

    public Persona(int personaId) {
        this.personaId = personaId;
    }

    public Persona(int personaId, String nombres, String apellidoPaterno, String apellidoMaterno, String celular, String telefono, String foto, String fechaNac, String genero, String estadoCivil, String numDoc, String ocupacion, int estadoId, String correo) {
        this.personaId = personaId;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.celular = celular;
        this.telefono = telefono;
        this.foto = foto;
        this.fechaNac = fechaNac;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
        this.numDoc = numDoc;
        this.ocupacion = ocupacion;
        this.estadoId = estadoId;
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getNombres() {
        return nombres;
    }

    public String getFirstName() {
        return UtilsPortalAlumno.getFirstWord(nombres);
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getApellidos() {
        return UtilsPortalAlumno.capitalize(this.getApellidoPaterno()) + " " + UtilsPortalAlumno.capitalize(this.getApellidoMaterno());
    }

    public String getNombreCompleto() {
        return UtilsPortalAlumno.capitalize(this.getApellidoPaterno()) + " " + UtilsPortalAlumno.capitalize(this.getApellidoMaterno()) + ", " + UtilsPortalAlumno.capitalize(this.getNombres());
    }


    public String getUrlPicture() {

        return this.foto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Persona persona = (Persona) o;

        return personaId == persona.personaId;
    }

    @Override
    public int hashCode() {
        return personaId;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "personaId=" + personaId +
                ", nombres='" + nombres + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", celular='" + celular + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNac='" + fechaNac + '\'' +
                ", genero='" + genero + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", numDoc='" + numDoc + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                ", estadoId=" + estadoId +
                ", correo='" + correo + '\'' +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
