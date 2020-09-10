package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

import java.io.Serializable;

public class RepositorioFileUi implements Serializable {
    private String recursoId;
    private String archivoId;
    private String nombreRecurso;
    private String nombreArchivo;
    private String descripcion;
    private String url;
    private String path;
    private RepositorioTipoFileU tipoFileU = RepositorioTipoFileU.VINCULO;
    private RepositorioEstadoFileU estadoFileU = RepositorioEstadoFileU.SIN_DESCARGAR;
    private boolean select;
    private boolean cancel;
    private long fechaCreacionRecuros;
    private long fechaAccionArchivo;
    private String driveId;

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(String recursoId) {
        this.recursoId = recursoId;
    }

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getNombreRecurso() {
        return nombreRecurso;
    }

    public void setNombreRecurso(String nombreRecurso) {
        this.nombreRecurso = nombreRecurso;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RepositorioTipoFileU getTipoFileU() {
        return tipoFileU;
    }

    public void setTipoFileU(RepositorioTipoFileU tipoFileU) {
        this.tipoFileU = tipoFileU;
    }

    public RepositorioEstadoFileU getEstadoFileU() {
        return estadoFileU;
    }

    public void setEstadoFileU(RepositorioEstadoFileU estadoFileU) {
        this.estadoFileU = estadoFileU;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }


    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public long getFechaCreacionRecuros() {
        return fechaCreacionRecuros;
    }

    public void setFechaCreacionRecuros(long fechaCreacionRecuros) {
        this.fechaCreacionRecuros = fechaCreacionRecuros;
    }

    public long getFechaAccionArchivo() {
        return fechaAccionArchivo;
    }

    public void setFechaAccionArchivo(long fechaAccionArchivo) {
        this.fechaAccionArchivo = fechaAccionArchivo;
    }
}
