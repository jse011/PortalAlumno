package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class InstrumentoEncuestaEval extends BaseModel {
    @PrimaryKey
    private int instrumentoEvalId;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private String imagen;
    @Column
    private String icono;
    @Column
    private String corporativo;
    @Column
    private String fechaLanzamiento;
    @Column
    private String fechaCierre;
    @Column
    private String horaLanzamiento;
    @Column
    private String horaCierre;
    @Column
    private int cantPregunta;
    @Column
    private int tipoInstrumentoId;
    @Column
    private String tipoInstrumento;
    @Column
    private String claseColor;
    @Column
    private String instrumentoObservadoId;
    @Column
    private int prgntsResueltos;
    @Column
    private int estadoId;
    @Column
    private int puntaje;
    @Column
    private int codigoRecopilacion;
    @Column
    private String recopilacionId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int personaId;
    @Column
    private String urlEncuesta;
    @Column
    private String urlResultado;

    public int getInstrumentoEvalId() {
        return instrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        this.instrumentoEvalId = instrumentoEvalId;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getCorporativo() {
        return corporativo;
    }

    public void setCorporativo(String corporativo) {
        this.corporativo = corporativo;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getHoraLanzamiento() {
        return horaLanzamiento;
    }

    public void setHoraLanzamiento(String horaLanzamiento) {
        this.horaLanzamiento = horaLanzamiento;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public int getCantPregunta() {
        return cantPregunta;
    }

    public void setCantPregunta(int cantPregunta) {
        this.cantPregunta = cantPregunta;
    }

    public int getTipoInstrumentoId() {
        return tipoInstrumentoId;
    }

    public void setTipoInstrumentoId(int tipoInstrumentoId) {
        this.tipoInstrumentoId = tipoInstrumentoId;
    }

    public String getTipoInstrumento() {
        return tipoInstrumento;
    }

    public void setTipoInstrumento(String tipoInstrumento) {
        this.tipoInstrumento = tipoInstrumento;
    }

    public String getClaseColor() {
        return claseColor;
    }

    public void setClaseColor(String claseColor) {
        this.claseColor = claseColor;
    }

    public String getInstrumentoObservadoId() {
        return instrumentoObservadoId;
    }

    public void setInstrumentoObservadoId(String instrumentoObservadoId) {
        this.instrumentoObservadoId = instrumentoObservadoId;
    }

    public int getPrgntsResueltos() {
        return prgntsResueltos;
    }

    public void setPrgntsResueltos(int prgntsResueltos) {
        this.prgntsResueltos = prgntsResueltos;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getCodigoRecopilacion() {
        return codigoRecopilacion;
    }

    public void setCodigoRecopilacion(int codigoRecopilacion) {
        this.codigoRecopilacion = codigoRecopilacion;
    }

    public String getRecopilacionId() {
        return recopilacionId;
    }

    public void setRecopilacionId(String recopilacionId) {
        this.recopilacionId = recopilacionId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getUrlEncuesta() {
        return urlEncuesta;
    }

    public void setUrlEncuesta(String urlEncuesta) {
        this.urlEncuesta = urlEncuesta;
    }

    public String getUrlResultado() {
        return urlResultado;
    }

    public void setUrlResultado(String urlResultado) {
        this.urlResultado = urlResultado;
    }
}
