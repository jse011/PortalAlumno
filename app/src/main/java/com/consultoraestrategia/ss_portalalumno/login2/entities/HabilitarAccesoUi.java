package com.consultoraestrategia.ss_portalalumno.login2.entities;

public class HabilitarAccesoUi {
    int usuarioId;
    boolean habilitar;
    boolean modifiado;

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isHabilitar() {
        return habilitar;
    }

    public void setHabilitar(boolean habilitar) {
        this.habilitar = habilitar;
    }

    public boolean isModifiado() {
        return modifiado;
    }

    public void setModifiado(boolean modifiado) {
        this.modifiado = modifiado;
    }
}
