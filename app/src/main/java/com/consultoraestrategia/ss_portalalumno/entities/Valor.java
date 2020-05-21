package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
@Table(database = AppDatabase.class)
public class Valor extends BaseModel {
    @PrimaryKey
    private int ValorId;
    @Column
    private int VariableId;
    @Column
    private int Valor;
    @Column
    private String Etiqueta;
    @Column
    private String Descripcion;
    @Column
    private String IconoValor;
    @Column
    private int TipoInputRespuestaId;
    @Column
    private String InputRespuesta;
    @Column
    private int Puntaje;
    @Column
    private String Path;
    @Column
    private String RespuestaContacto;
    @Column
    private String OrientacionRespuesta;
    @Column
    private int SecuenciaRespuestaId;

    public int getValorId() {
        return ValorId;
    }

    public void setValorId(int valorId) {
        ValorId = valorId;
    }

    public int getVariableId() {
        return VariableId;
    }

    public void setVariableId(int variableId) {
        VariableId = variableId;
    }

    public int getValor() {
        return Valor;
    }

    public void setValor(int valor) {
        Valor = valor;
    }

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        Etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getIconoValor() {
        return IconoValor;
    }

    public void setIconoValor(String iconoValor) {
        IconoValor = iconoValor;
    }

    public int getTipoInputRespuestaId() {
        return TipoInputRespuestaId;
    }

    public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
        TipoInputRespuestaId = tipoInputRespuestaId;
    }

    public String getInputRespuesta() {
        return InputRespuesta;
    }

    public void setInputRespuesta(String inputRespuesta) {
        InputRespuesta = inputRespuesta;
    }

    public int getPuntaje() {
        return Puntaje;
    }

    public void setPuntaje(int puntaje) {
        Puntaje = puntaje;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getRespuestaContacto() {
        return RespuestaContacto;
    }

    public void setRespuestaContacto(String respuestaContacto) {
        RespuestaContacto = respuestaContacto;
    }

    public String getOrientacionRespuesta() {
        return OrientacionRespuesta;
    }

    public void setOrientacionRespuesta(String orientacionRespuesta) {
        OrientacionRespuesta = orientacionRespuesta;
    }

    public int getSecuenciaRespuestaId() {
        return SecuenciaRespuestaId;
    }

    public void setSecuenciaRespuestaId(int secuenciaRespuestaId) {
        SecuenciaRespuestaId = secuenciaRespuestaId;
    }
}
