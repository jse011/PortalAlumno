package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class Variable extends BaseModel {

    @PrimaryKey
    private int VariableId;
    @Column
    private String Nombre;
    @Column
    private int TipoVariableId;
    @Column
    private String Etiqueta;
    @Column
    private double Peso;
    @Column
    private int MedidaId;
    @Column
    private int InstrumentoEvalId;
    @Column
    private int DimensionId;
    @Column
    private int TipoRespuestaId;
    @Column
    private String IconoVariable;
    @Column
    private int ValorMinimo;
    @Column
    private int ValorMaximo;
    @Column
    private int LongitudPaso;
    @Column
    private String InputRespuesta;
    @Column
    private int TipoInputRespuestaId;
    @Column
    private int Puntaje;
    @Column
    private String Path;
    @Column
    private int DisenioPreguntaId;
    @Column
    private int ParentId;
    @Column
    private int BancoPreguntaId;
    @Column
    private int NivelPreguntaId;
    @Column
    private int TipoPreguntaId;
    @Column
    private String Tiempo;
    @Column
    private boolean Pertencia;

    public int getVariableId() {
        return VariableId;
    }

    public void setVariableId(int variableId) {
        VariableId = variableId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getTipoVariableId() {
        return TipoVariableId;
    }

    public void setTipoVariableId(int tipoVariableId) {
        TipoVariableId = tipoVariableId;
    }

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        Etiqueta = etiqueta;
    }

    public double getPeso() {
        return Peso;
    }

    public void setPeso(double peso) {
        Peso = peso;
    }

    public int getMedidaId() {
        return MedidaId;
    }

    public void setMedidaId(int medidaId) {
        MedidaId = medidaId;
    }

    public int getInstrumentoEvalId() {
        return InstrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        InstrumentoEvalId = instrumentoEvalId;
    }

    public int getDimensionId() {
        return DimensionId;
    }

    public void setDimensionId(int dimensionId) {
        DimensionId = dimensionId;
    }

    public int getTipoRespuestaId() {
        return TipoRespuestaId;
    }

    public void setTipoRespuestaId(int tipoRespuestaId) {
        TipoRespuestaId = tipoRespuestaId;
    }

    public String getIconoVariable() {
        return IconoVariable;
    }

    public void setIconoVariable(String iconoVariable) {
        IconoVariable = iconoVariable;
    }

    public int getValorMinimo() {
        return ValorMinimo;
    }

    public void setValorMinimo(int valorMinimo) {
        ValorMinimo = valorMinimo;
    }

    public int getValorMaximo() {
        return ValorMaximo;
    }

    public void setValorMaximo(int valorMaximo) {
        ValorMaximo = valorMaximo;
    }

    public int getLongitudPaso() {
        return LongitudPaso;
    }

    public void setLongitudPaso(int longitudPaso) {
        LongitudPaso = longitudPaso;
    }

    public String getInputRespuesta() {
        return InputRespuesta;
    }

    public void setInputRespuesta(String inputRespuesta) {
        InputRespuesta = inputRespuesta;
    }

    public int getTipoInputRespuestaId() {
        return TipoInputRespuestaId;
    }

    public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
        TipoInputRespuestaId = tipoInputRespuestaId;
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

    public int getDisenioPreguntaId() {
        return DisenioPreguntaId;
    }

    public void setDisenioPreguntaId(int disenioPreguntaId) {
        DisenioPreguntaId = disenioPreguntaId;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getBancoPreguntaId() {
        return BancoPreguntaId;
    }

    public void setBancoPreguntaId(int bancoPreguntaId) {
        BancoPreguntaId = bancoPreguntaId;
    }

    public int getNivelPreguntaId() {
        return NivelPreguntaId;
    }

    public void setNivelPreguntaId(int nivelPreguntaId) {
        NivelPreguntaId = nivelPreguntaId;
    }

    public int getTipoPreguntaId() {
        return TipoPreguntaId;
    }

    public void setTipoPreguntaId(int tipoPreguntaId) {
        TipoPreguntaId = tipoPreguntaId;
    }

    public String getTiempo() {
        return Tiempo;
    }

    public void setTiempo(String tiempo) {
        Tiempo = tiempo;
    }

    public boolean isPertencia() {
        return Pertencia;
    }

    public void setPertencia(boolean pertencia) {
        Pertencia = pertencia;
    }
}
