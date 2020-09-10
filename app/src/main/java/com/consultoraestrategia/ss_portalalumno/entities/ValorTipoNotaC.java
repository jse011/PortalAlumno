package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 23/06/2017.
 */
@Table(database = AppDatabase.class)
public class ValorTipoNotaC extends BaseEntity {

    public static final int PUNTUAL=285, TARDE=286, AUSENTE=287, TARDE_JDT=288, AUSENTE_JDT=289;
    @Column
    private String valorTipoNotaId;
    @Column
    private String tipoNotaId;
    @Column
    private String titulo;
    @Column
    private String alias;
    @Column
    private double limiteInferior;
    @Column
    private double limiteSuperior;
    @Column
    private double valorNumerico;
    @Column
    private String icono;
    @Column
    private int estadoId;
    @Column
    private boolean incluidoLInferior;
    @Column
    private boolean incluidoLSuperior;


    private boolean seleccionado;



    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public String getValorTipoNotaId() {
        return valorTipoNotaId;
    }

    public void setValorTipoNotaId(String valorTipoNotaId) {
        this.valorTipoNotaId = valorTipoNotaId;
    }

    public String getTipoNotaId() {
        return tipoNotaId;
    }

    public void setTipoNotaId(String tipoNotaId) {
        this.tipoNotaId = tipoNotaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public double getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(double valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public boolean isIncluidoLInferior() {
        return incluidoLInferior;
    }

    public void setIncluidoLInferior(boolean incluidoLInferior) {
        this.incluidoLInferior = incluidoLInferior;
    }

    public boolean isIncluidoLSuperior() {
        return incluidoLSuperior;
    }

    public void setIncluidoLSuperior(boolean incluidoLSuperior) {
        this.incluidoLSuperior = incluidoLSuperior;
    }

    @Override
    public String toString() {
        return "ValorTipoNota{" +
                "valorTipoNotaId=" + valorTipoNotaId +
                ", tipoNotaId=" + tipoNotaId +
                ", titulo='" + titulo + '\'' +
                ", alias='" + alias + '\'' +
                ", limiteInferior=" + limiteInferior +
                ", limiteSuperior=" + limiteSuperior +
                ", valorNumerico=" + valorNumerico +
                ", icono='" + icono + '\'' +
                '}';
    }
}
