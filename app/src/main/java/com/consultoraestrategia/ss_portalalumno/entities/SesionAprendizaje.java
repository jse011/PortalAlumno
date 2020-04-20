package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 27/07/2017.
 */
//Execpecion del uso del BaseRelEntity ya que esta tabla se va actualziar
@Table(database = AppDatabase.class)
public class SesionAprendizaje extends BaseRelEntity {
    public static final int CREADO_EJECUCION = 315;
    public static final int PROGRAMADO_EJECUCION = 316;
    public static final int HECHO_EJECUCION = 317;
    public static final int PENDIENTE_EJECUCION =318;

    public static final int CREADO_ESTADO = 296;
    public static final int AUTORIZADO_ESTADO = 297;
    public static final int PUBLICADO_ESTADO = 289;
    public static final int ELIMINADO_ESTADO = 299;

    @Column
    @PrimaryKey
    private int sesionAprendizajeId;
    @Column
    private int unidadAprendizajeId;
    @Column
    private String titulo;
    @Column
    private int horas;
    @Column
    private String contenido;
    @Column
    private int usuarioCreador;
    @Column
    private int usuarioAccion;
    @Column
    private int estadoId;
    @Column
    private long fechaEjecucion;
    @Column
    private String fechaReprogramacion;
    @Column
    private String fechaPublicacion;
    @Column
    private int nroSesion;
    @Column
    private int rolId;
    @Column
    private long fechaRealizada;
    @Column
    private int estadoEjecucionId;
    @Column
    private String proposito;


    public SesionAprendizaje() {
    }


    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public int getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(int usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public int getUsuarioAccion() {
        return usuarioAccion;
    }

    public void setUsuarioAccion(int usuarioAccion) {
        this.usuarioAccion = usuarioAccion;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }


    public String getFechaReprogramacion() {
        return fechaReprogramacion;
    }

    public void setFechaReprogramacion(String fechaReprogramacion) {
        this.fechaReprogramacion = fechaReprogramacion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getNroSesion() {
        return nroSesion;
    }

    public void setNroSesion(int nroSesion) {
        this.nroSesion = nroSesion;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    public String getProposito() {
        return proposito;
    }

    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    public long getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(long fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public long getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(long fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    public int getEstadoEjecucionId() {
        return estadoEjecucionId;
    }

    public void setEstadoEjecucionId(int estadoEjecucionId) {
        this.estadoEjecucionId = estadoEjecucionId;
    }

    @Override
    public String toString() {
        return "SesionAprendizaje{" +
                "sesionAprendizajeId=" + sesionAprendizajeId +
                ", unidadAprendizajeId=" + unidadAprendizajeId +
                ", titulo='" + titulo + '\'' +
                ", horas=" + horas +
                ", contenido='" + contenido + '\'' +
                ", usuarioCreador=" + usuarioCreador +
                ", usuarioAccion=" + usuarioAccion +
                ", estadoId=" + estadoId +
                ", fechaEjecucion=" + fechaEjecucion +
                ", fechaReprogramacion='" + fechaReprogramacion + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", nroSesion=" + nroSesion +
                ", rolId=" + rolId +
                ", fechaRealizada=" + fechaRealizada +
                ", estadoEjecucionId=" + estadoEjecucionId +
                ", proposito='" + proposito + '\'' +
                '}';
    }
}
