package com.consultoraestrategia.ss_portalalumno.main.entities;

import java.util.Date;
import java.util.List;

public class EventoUi {
    String id;
    String nombreEmisor;
    String rolEmisor;
    String fotoEntidad;
    String nombreEntidad;
    Date fecha;
    String nombreFecha;
    String titulo;

    String descripcion;
    String foto;
    TipoEventoUi tipoEventoUi;
    int cantLike;
    private boolean like;
    private boolean datosfirefase;
    private String nombreCalendario;
    private List<AdjuntoUi> adjuntoUiList;
    private List<AdjuntoUi> adjuntoUiPreviewList;
    private List<AdjuntoUi> adjuntoUiEncuestaList;

    private Date fechaPublicacion;
    private String nombreFechaPublicion;
    private long fechaCreacion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getRolEmisor() {
        return rolEmisor;
    }

    public void setRolEmisor(String rolEmisor) {
        this.rolEmisor = rolEmisor;
    }

    public String getFotoEntidad() {
        return fotoEntidad;
    }

    public void setFotoEntidad(String fotoEntidad) {
        this.fotoEntidad = fotoEntidad;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public Date getFecha() {
        return this.fecha == null?new Date(0):this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreFecha() {
        return nombreFecha;
    }

    public void setNombreFecha(String nombreFecha) {
        this.nombreFecha = nombreFecha;
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

    public TipoEventoUi getTipoEventoUi() {
        return tipoEventoUi;
    }

    public void setTipoEventoUi(TipoEventoUi tipoEventoUi) {
        this.tipoEventoUi = tipoEventoUi;
    }

    public int getCantLike() {
        return cantLike;
    }

    public void setCantLike(int cantLike) {
        this.cantLike = cantLike;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public List<AdjuntoUi> getAdjuntoUiList() {
        return adjuntoUiList;
    }

    public void setAdjuntoUiList(List<AdjuntoUi> adjuntoUiList) {
        this.adjuntoUiList = adjuntoUiList;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<AdjuntoUi> getAdjuntoUiPreviewList() {
        return adjuntoUiPreviewList;
    }

    public void setAdjuntoUiPreviewList(List<AdjuntoUi> adjuntoUiPreviewList) {
        this.adjuntoUiPreviewList = adjuntoUiPreviewList;
    }

    public void setNombreCalendario(String nombreCalendario) {
        this.nombreCalendario = nombreCalendario;
    }

    public String getNombreCalendario() {
        return nombreCalendario;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

       EventoUi eventoUi = (EventoUi) o;

        return id != null ? id.equals(eventoUi.id) : eventoUi.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setNombreFechaPublicion(String nombreFechaPublicion) {
        this.nombreFechaPublicion = nombreFechaPublicion;
    }

    public String getNombreFechaPublicion() {
        return nombreFechaPublicion;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<AdjuntoUi> getAdjuntoUiEncuestaList() {
        return adjuntoUiEncuestaList;
    }

    public void setAdjuntoUiEncuestaList(List<AdjuntoUi> adjuntoUiEncuestaList) {
        this.adjuntoUiEncuestaList = adjuntoUiEncuestaList;
    }

    public static class AdjuntoUi{
        private String eventoAjuntoId;
        private String titulo;
        private TipoArchivo tipoArchivo = TipoArchivo.LINK;
        private String driveId;
        private String yotubeId;
        private String imagePreview;
        private boolean isVideo;
        public String getEventoAjuntoId() {
            return eventoAjuntoId;
        }

        public void setEventoAjuntoId(String eventoAjuntoId) {
            this.eventoAjuntoId = eventoAjuntoId;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public TipoArchivo getTipoArchivo() {
            return tipoArchivo;
        }

        public void setTipoArchivo(TipoArchivo tipoArchivo) {
            this.tipoArchivo = tipoArchivo;
        }

        public String getDriveId() {
            return driveId;
        }

        public void setDriveId(String driveId) {
            this.driveId = driveId;
        }

        public String getImagePreview() {
            return imagePreview;
        }

        public void setImagePreview(String imagePreview) {
            this.imagePreview = imagePreview;
        }

        public boolean isVideo() {
            return isVideo;
        }

        public void setVideo(boolean video) {
            isVideo = video;
        }

        public String getYotubeId() {
            return yotubeId;
        }

        public void setYotubeId(String yotubeId) {
            this.yotubeId = yotubeId;
        }
    }
}

