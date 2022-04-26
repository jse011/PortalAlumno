package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


/**
 * Created by irvinmarin on 23/03/2017.
 */
@Table(database = AppDatabase.class)
public class Usuario extends BaseModel {

    @Column
    @PrimaryKey
    public int usuarioId;
    @Column
    public int personaId;
    @Column
    public String usuario;
    @Column
    public String password;
    @Column
    public boolean estado;

    public String nombres;
    public String apellidoPaterno;
    public String apellidoMaterno;
    public String numDoc;
    @Column
    public boolean habilitarAcceso;

    public List<Entidad> entidades;

    public List<Georeferencia> georeferencias;

    public List<Rol> roles;

    public List<UsuarioRolGeoreferencia> usuarioRolGeoreferencias;

    public List<PersonaGeoreferencia> personaGeoreferencias;

    public List<UsuarioAcceso> accesos;

    public String fotoPersona;

    public String fotoEntidad;


    public Usuario() {
    }


    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFotoPersona() {
        return fotoPersona;
    }

    public void setFotoPersona(String fotoPersona) {
        this.fotoPersona = fotoPersona;
    }

    public String getFotoEntidad() {
        return fotoEntidad;
    }

    public void setFotoEntidad(String fotoEntidad) {
        this.fotoEntidad = fotoEntidad;
    }

    public List<Entidad> getEntidades() {
        return entidades;
    }

    public void setEntidades(List<Entidad> entidades) {
        this.entidades = entidades;
    }

    public List<Georeferencia> getGeoreferencias() {
        return georeferencias;
    }

    public void setGeoreferencias(List<Georeferencia> georeferencias) {
        this.georeferencias = georeferencias;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public List<UsuarioRolGeoreferencia> getUsuarioRolGeoreferencias() {
        return usuarioRolGeoreferencias;
    }

    public void setUsuarioRolGeoreferencias(List<UsuarioRolGeoreferencia> usuarioRolGeoreferencias) {
        this.usuarioRolGeoreferencias = usuarioRolGeoreferencias;
    }

    public List<PersonaGeoreferencia> getPersonaGeoreferencias() {
        return personaGeoreferencias;
    }

    public void setPersonaGeoreferencias(List<PersonaGeoreferencia> personaGeoreferencias) {
        this.personaGeoreferencias = personaGeoreferencias;
    }

    public List<UsuarioAcceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(List<UsuarioAcceso> accesos) {
        this.accesos = accesos;
    }

    public String getNombres() {
        return nombres;
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

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public boolean isHabilitarAcceso() {
        return habilitarAcceso;
    }

    public void setHabilitarAcceso(boolean habilitarAcceso) {
        this.habilitarAcceso = habilitarAcceso;
    }
}
