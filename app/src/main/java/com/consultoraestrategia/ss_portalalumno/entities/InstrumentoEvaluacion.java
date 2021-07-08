package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = AppDatabase.class)
public class InstrumentoEvaluacion extends BaseModel {
    @PrimaryKey
    private int InstrumentoEvalId;
    @Column
    private String Nombre;
    @Column
    private String Descripcion;
    @Column
    private int TipoInstrumentoId;
    @Column
    private int EntidadId;
    @Column
    private int GeoreferenciaId;
    @Column
    private int AreaInstrumentoId;
    @Column
    private double Confiabilidad;
    @Column
    private String Imagen;
    @Column
    private String Icono;
    @Column
    private int Encuesta;// bit Checked
    @Column
    private int Alcance;// bit Checked
    @Column
    private int Comprobacion;// bit Checked
    @Column
    private int Publicado;// bit Checked
    @Column
    private int UsuarioId;
    @Column
    private int SilaboId;
    @Column
    private int UnidadId;
    @Column
    private int SesionId;
    @Column
    private int Corporativa;// bit Checked
    @Column
    private int Estado;// bit Checked
    @Column
    private int InstrumentoEvalParentId;
    @Column
    private int CantidadPreguntas;
    @Column
    private String rubroEvaluacionId;
    @Column
    private String tipoNotaId;

    public int getInstrumentoEvalId() {
        return InstrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        InstrumentoEvalId = instrumentoEvalId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getTipoInstrumentoId() {
        return TipoInstrumentoId;
    }

    public void setTipoInstrumentoId(int tipoInstrumentoId) {
        TipoInstrumentoId = tipoInstrumentoId;
    }

    public int getEntidadId() {
        return EntidadId;
    }

    public void setEntidadId(int entidadId) {
        EntidadId = entidadId;
    }

    public int getGeoreferenciaId() {
        return GeoreferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        GeoreferenciaId = georeferenciaId;
    }

    public int getAreaInstrumentoId() {
        return AreaInstrumentoId;
    }

    public void setAreaInstrumentoId(int areaInstrumentoId) {
        AreaInstrumentoId = areaInstrumentoId;
    }

    public double getConfiabilidad() {
        return Confiabilidad;
    }

    public void setConfiabilidad(double confiabilidad) {
        Confiabilidad = confiabilidad;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getIcono() {
        return Icono;
    }

    public void setIcono(String icono) {
        Icono = icono;
    }

    public int getEncuesta() {
        return Encuesta;
    }

    public void setEncuesta(int encuesta) {
        Encuesta = encuesta;
    }

    public int getAlcance() {
        return Alcance;
    }

    public void setAlcance(int alcance) {
        Alcance = alcance;
    }

    public int getComprobacion() {
        return Comprobacion;
    }

    public void setComprobacion(int comprobacion) {
        Comprobacion = comprobacion;
    }

    public int getPublicado() {
        return Publicado;
    }

    public void setPublicado(int publicado) {
        Publicado = publicado;
    }

    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public int getSilaboId() {
        return SilaboId;
    }

    public void setSilaboId(int silaboId) {
        SilaboId = silaboId;
    }

    public int getUnidadId() {
        return UnidadId;
    }

    public void setUnidadId(int unidadId) {
        UnidadId = unidadId;
    }

    public int getSesionId() {
        return SesionId;
    }

    public void setSesionId(int sesionId) {
        SesionId = sesionId;
    }

    public int getCorporativa() {
        return Corporativa;
    }

    public void setCorporativa(int corporativa) {
        Corporativa = corporativa;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public int getInstrumentoEvalParentId() {
        return InstrumentoEvalParentId;
    }

    public void setInstrumentoEvalParentId(int instrumentoEvalParentId) {
        InstrumentoEvalParentId = instrumentoEvalParentId;
    }

    public int getCantidadPreguntas() {
        return CantidadPreguntas;
    }

    public void setCantidadPreguntas(int cantidadPreguntas) {
        CantidadPreguntas = cantidadPreguntas;
    }

    public void setRubroEvaluacionId(String rubroEvaluacionId) {
        this.rubroEvaluacionId = rubroEvaluacionId;
    }

    public String getRubroEvaluacionId() {
        return rubroEvaluacionId;
    }

    public void setTipoNotaId(String tipoNotaId) {
        this.tipoNotaId = tipoNotaId;
    }

    public String getTipoNotaId() {
        return tipoNotaId;
    }
}
