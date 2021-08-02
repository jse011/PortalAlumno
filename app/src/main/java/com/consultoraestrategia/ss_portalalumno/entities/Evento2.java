package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

@Table(database = AppDatabase.class)
public class Evento2  extends BaseModel {
    public static final int ESTADO_ELIMINADO = 346 , ESTADO_ACTUALIZADO = 345, ESTADO_CREADO = 344;
    public static final int EVENTO=526, ACTIVIDAD=528, CITA=530, TAREA=529, NOTICIA=527;
    @PrimaryKey
    String eventoId;
    @Column
    String titulo;
    @Column
    String descripcion;
    @Column
    String calendarioId;
    @Column
    int tipoEventoId;
    @Column
    int estadoId;
    @Column
    boolean estadoPublicacion;
    @Column
    int entidadId;
    @Column
    int georeferenciaId;
    @Column
    String fechaEvento_;
    @Column
    long fechaEvento;
    @Column
    String horaEvento;
    @Column
    String pathImagen;
    @Column
    boolean envioPersonalizado;
    @Column
    String tipoEventoNombre;
    @Column
    int usuarioReceptorId;
    @Column
    int likeCount;
    @Column
    String nombreEntidad;
    @Column
    String fotoEntidad;
    @Column
    String nombreEntidadSiglas;
    @Column
    Date fechaEventoTime;
    @Column
    boolean like;
    @Column
    Date fechaPublicacion_;
    @Column
    long fechaPublicacion;

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

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
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

    public String getFechaEvento_() {
        return fechaEvento_;
    }

    public void setFechaEvento_(String fechaEvento_) {
        this.fechaEvento_ = fechaEvento_;
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

    public boolean isEnvioPersonalizado() {
        return envioPersonalizado;
    }

    public void setEnvioPersonalizado(boolean envioPersonalizado) {
        this.envioPersonalizado = envioPersonalizado;
    }

    public String getTipoEventoNombre() {
        return tipoEventoNombre;
    }

    public void setTipoEventoNombre(String tipoEventoNombre) {
        this.tipoEventoNombre = tipoEventoNombre;
    }

    public int getUsuarioReceptorId() {
        return usuarioReceptorId;
    }

    public void setUsuarioReceptorId(int usuarioReceptorId) {
        this.usuarioReceptorId = usuarioReceptorId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
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

    public String getNombreEntidadSiglas() {
        return nombreEntidadSiglas;
    }

    public void setNombreEntidadSiglas(String nombreEntidadSiglas) {
        this.nombreEntidadSiglas = nombreEntidadSiglas;
    }

    public Date getFechaEventoTime() {
        return fechaEventoTime;
    }

    public void setFechaEventoTime(Date fechaEventoTime) {
        this.fechaEventoTime = fechaEventoTime;
    }

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
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
}
