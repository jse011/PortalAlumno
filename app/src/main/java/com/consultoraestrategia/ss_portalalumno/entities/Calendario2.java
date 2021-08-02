package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;
@Table(database = AppDatabase.class)
public class Calendario2 extends BaseModel {
    public static final int ESTADO_ELIMINADO = 343 , ESTADO_ACTUALIZADO = 342, ESTADO_CREADO = 341;
    @PrimaryKey
    String calendarioId;
    @Column
    String nombre;
    @Column
    String descripcion;
    @Column
    int  estado;
    // int usuarioId;
    @Column
    int  entidadId;
    @Column
    int  georeferenciaId;
    //string fechaAccion;
//string fechaCrecion;
    @Column
    String nUsuario;
    @Column
    String cargo;
    @Column
    int  usuarioId;
    @Column
    int  cargaAcademicaId;
    @Column
    int  cargaCursoId;
    @Column
    int  estadoPublicacionCal;
    @Column
    int  rolId;
    @Column
    long fechaCreacion;
    @Column
    Date fechaCreacion_;
    @Column
    int  usuarioAccionId;
    @Column
    long fechaAccion;
    @Column
    Date fechaAccion_;
    @Column
    String nFoto;

    public Date getFechaCreacion_() {
        return fechaCreacion_;
    }

    public void setFechaCreacion_(Date fechaCreacion_) {
        this.fechaCreacion_ = fechaCreacion_;
    }

    public long getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(long fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public Date getFechaAccion_() {
        return fechaAccion_;
    }

    public void setFechaAccion_(Date fechaAccion_) {
        this.fechaAccion_ = fechaAccion_;
    }

    public String getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(String calendarioId) {
        this.calendarioId = calendarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public String getnUsuario() {
        return nUsuario;
    }

    public void setnUsuario(String nUsuario) {
        this.nUsuario = nUsuario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCargaAcademicaId() {
        return cargaAcademicaId;
    }

    public void setCargaAcademicaId(int cargaAcademicaId) {
        this.cargaAcademicaId = cargaAcademicaId;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public int getEstadoPublicacionCal() {
        return estadoPublicacionCal;
    }

    public void setEstadoPublicacionCal(int estadoPublicacionCal) {
        this.estadoPublicacionCal = estadoPublicacionCal;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public int getUsuarioAccionId() {
        return usuarioAccionId;
    }

    public void setUsuarioAccionId(int usuarioAccionId) {
        this.usuarioAccionId = usuarioAccionId;
    }

    public static int getEstadoEliminado() {
        return ESTADO_ELIMINADO;
    }

    public static int getEstadoActualizado() {
        return ESTADO_ACTUALIZADO;
    }

    public static int getEstadoCreado() {
        return ESTADO_CREADO;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getnFoto() {
        return nFoto;
    }

    public void setnFoto(String nFoto) {
        this.nFoto = nFoto;
    }
}
