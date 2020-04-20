package com.consultoraestrategia.ss_portalalumno.login2.entities;

import java.util.List;

public class ProgramaEducativoUi {
    private String nombre;
    private List<ActualizarUi> actualizarUiList;
    private int uploadProgress;
    private int dowloadProgress;
    private int calendarioPeriodoId;
    private long fecha;
    private int docenteId;
    private int entidadId;
    private int georeferenciaId;
    private int success;
    private boolean encola;
    private int startTipo;
    private String bimestre;

    public ProgramaEducativoUi clonar(){
        ProgramaEducativoUi programaEducativoUi = new ProgramaEducativoUi();
        programaEducativoUi.setNombre(getNombre());
        programaEducativoUi.setActualizarUiList(getActualizarUiList());
        programaEducativoUi.setUploadProgress(getUploadProgress());
        programaEducativoUi.setDowloadProgress(getDowloadProgress());
        programaEducativoUi.setCalendarioPeriodoId(getCalendarioPeriodoId());
        programaEducativoUi.setFecha(getFecha());
        programaEducativoUi.setDocenteId(getDocenteId());
        programaEducativoUi.setEntidadId(getEntidadId());
        programaEducativoUi.setGeoreferenciaId(getGeoreferenciaId());
        programaEducativoUi.setSuccess(getSuccess());
        programaEducativoUi.setEncola(getEncola());
        programaEducativoUi.setStartTipo(getStartTipo());
        return programaEducativoUi;
    }

    public void setEncola(boolean encola) {
        this.encola = encola;
    }

    public boolean getEncola() {
        return encola;
    }
    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }
    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public long getFecha() {
        return fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setActualizarUiList(List<ActualizarUi> actualizarUiList) {
        this.actualizarUiList = actualizarUiList;
    }

    public List<ActualizarUi> getActualizarUiList() {
        return actualizarUiList;
    }

    public void setUploadProgress(int uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setDowloadProgress(int dowloadProgress) {
        this.dowloadProgress = dowloadProgress;
    }

    public int getDowloadProgress() {
        return dowloadProgress;
    }

    public int getCalendarioPeriodoId() {
        return calendarioPeriodoId;
    }

    public void setCalendarioPeriodoId(int calendarioPeriodoId) {
        this.calendarioPeriodoId = calendarioPeriodoId;
    }

    public int getStartTipo() {
        return startTipo;
    }

    public void setStartTipo(int startTipo) {
        this.startTipo = startTipo;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }

    public String getBimestre() {
        return bimestre;
    }
}
