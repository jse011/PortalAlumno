package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class Evento extends BaseEntity {
    public static final int ESTADO_ELIMINADO = 346 , ESTADO_ACTUALIZADO = 345, ESTADO_CREADO = 344;
    public static final int EVENTO=526, ACTIVIDAD=528, CITA=530, TAREA=529, NOTICIA=527;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getEstadoPublicacion() {
        return estadoPublicacion;
    }

    public void setEstadoPublicacion(Boolean estadoPublicacion) {
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
}
