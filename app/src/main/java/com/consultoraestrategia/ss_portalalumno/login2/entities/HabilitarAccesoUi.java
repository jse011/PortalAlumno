package com.consultoraestrategia.ss_portalalumno.login2.entities;

public class HabilitarAccesoUi {
    int usuarioId;
    boolean habilitar;
    private boolean modificado;

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

    public void setModificado(boolean modificado) {
        this.modificado = modificado;
    }

    public boolean isModifiado() {
        return modificado;
    }
}
