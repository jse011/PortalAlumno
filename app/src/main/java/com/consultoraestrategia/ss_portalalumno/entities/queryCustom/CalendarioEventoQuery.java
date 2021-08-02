package com.consultoraestrategia.ss_portalalumno.entities.queryCustom;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.QueryModel;

import java.util.Date;

@QueryModel(database = AppDatabase.class, allFields = true)
public class CalendarioEventoQuery {
    @Column
    public String eventoId;
    @Column
    public String titulo;
    @Column
    public String descripcion;
    // public string fechaCreacion { get; set; }
    /// public int UsuarioCreacionId { get; set; }
    // public string fechaAccion { get; set; }
    // public int usuarioAccionId { get; set; }
    @Column
    public String calendarioId;
    @Column
    public int tipoEventoId;
    @Column
    public boolean estado;
    @Column
    public boolean estadoPublicacion;
    @Column
    public String nombreEntidad;
    @Column
    public String fotoEntidad;
    @Column
    public String tipoEventoNombre;

    @Column
    public int entidadId;
    @Column
    public int georeferenciaId;
    @Column
    public long fechaEvento;
    @Column
    public String horaEvento;
    @Column
    public String pathImagen;
    @Column
    public int estadoId;
    @Column
    public int likeCount;
    @Column
    public boolean like;
    @Column
    public int calendarioPeriodoId;
    @Column
    public long calendarioFechaInicio;
    @Column
    public long calendarioFechaFin;
    @Column
    public int calendarioAcademicoId;
    @Column
    public int calendarioTipoId;
    @Column
    public int calendarioEstadoId;
    @Column
    public int calendarioDiazPlazo;
    @Column
    public long fechaCreacion;
    @Column
    public String nUsuario;
    @Column
    public String nFoto;
    @Column
    public String cargo;
    @Column
    public Date fechaEventoTime;
    @Column
    public String calendarioNombre;
    @Column
    public Date fechaPublicacion_;
    @Column
    public long fechaPublicacion;
    @Column
    public String eventoAdjuntoId;
    @Column
    public String adjuntoTitulo;
    @Column
    public int adjuntoTipoId;
    @Column
    public String adjuntoDriveId;

    public CalendarioEventoQuery() {
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCalendarioId() {
        return calendarioId;
    }

    public void setCalendarioId(String calendarioId) {
        this.calendarioId = calendarioId;
    }

    public int getTipoEventoId() {
        return tipoEventoId;
    }

    public void setTipoEventoId(int tipoEventoId) {
        this.tipoEventoId = tipoEventoId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void setEstadoPublicacion(boolean estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
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

    public long getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(long fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public int getCalendarioPeriodoId() {
        return calendarioPeriodoId;
    }

    public void setCalendarioPeriodoId(int calendarioPeriodoId) {
        this.calendarioPeriodoId = calendarioPeriodoId;
    }

    public long getCalendarioFechaInicio() {
        return calendarioFechaInicio;
    }

    public void setCalendarioFechaInicio(long calendarioFechaInicio) {
        this.calendarioFechaInicio = calendarioFechaInicio;
    }

    public long getCalendarioFechaFin() {
        return calendarioFechaFin;
    }

    public void setCalendarioFechaFin(long calendarioFechaFin) {
        this.calendarioFechaFin = calendarioFechaFin;
    }

    public int getCalendarioAcademicoId() {
        return calendarioAcademicoId;
    }

    public void setCalendarioAcademicoId(int calendarioAcademicoId) {
        this.calendarioAcademicoId = calendarioAcademicoId;
    }

    public int getCalendarioTipoId() {
        return calendarioTipoId;
    }

    public void setCalendarioTipoId(int calendarioTipoId) {
        this.calendarioTipoId = calendarioTipoId;
    }

    public int getCalendarioEstadoId() {
        return calendarioEstadoId;
    }

    public void setCalendarioEstadoId(int calendarioEstadoId) {
        this.calendarioEstadoId = calendarioEstadoId;
    }

    public int getCalendarioDiazPlazo() {
        return calendarioDiazPlazo;
    }

    public void setCalendarioDiazPlazo(int calendarioDiazPlazo) {
        this.calendarioDiazPlazo = calendarioDiazPlazo;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getFotoEntidad() {
        return fotoEntidad;
    }

    public void setFotoEntidad(String fotoEntidad) {
        this.fotoEntidad = fotoEntidad;
    }

    public Date getFechaEventoTime() {
        return fechaEventoTime;
    }

    public void setFechaEventoTime(Date fechaEventoTime) {
        this.fechaEventoTime = fechaEventoTime;
    }

    public String getTipoEventoNombre() {
        return tipoEventoNombre;
    }

    public void setTipoEventoNombre(String tipoEventoNombre) {
        this.tipoEventoNombre = tipoEventoNombre;
    }

    public String getCalendarioNombre() {
        return calendarioNombre;
    }

    public void setCalendarioNombre(String calendarioNombre) {
        this.calendarioNombre = calendarioNombre;
    }

    public Date getFechaPublicacion_() {
        return fechaPublicacion_;
    }

    public void setFechaPublicacion_(Date fechaPublicacion_) {
        this.fechaPublicacion_ = fechaPublicacion_;
    }

    public long getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(long fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEventoAdjuntoId() {
        return eventoAdjuntoId;
    }

    public void setEventoAdjuntoId(String eventoAdjuntoId) {
        this.eventoAdjuntoId = eventoAdjuntoId;
    }

    public String getAdjuntoTitulo() {
        return adjuntoTitulo;
    }

    public void setAdjuntoTitulo(String adjuntoTitulo) {
        this.adjuntoTitulo = adjuntoTitulo;
    }

    public int getAdjuntoTipoId() {
        return adjuntoTipoId;
    }

    public void setAdjuntoTipoId(int adjuntoTipoId) {
        this.adjuntoTipoId = adjuntoTipoId;
    }

    public String getAdjuntoDriveId() {
        return adjuntoDriveId;
    }

    public void setAdjuntoDriveId(String adjuntoDriveId) {
        this.adjuntoDriveId = adjuntoDriveId;
    }

    public String getnFoto() {
        return nFoto;
    }

    public void setnFoto(String nFoto) {
        this.nFoto = nFoto;
    }
}
