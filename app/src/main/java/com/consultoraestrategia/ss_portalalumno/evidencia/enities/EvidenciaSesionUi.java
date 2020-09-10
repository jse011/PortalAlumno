package com.consultoraestrategia.ss_portalalumno.evidencia.enities;

public class EvidenciaSesionUi {
    private boolean entregaAlumno;
    private boolean retrasoEntrega;

    public void setEntregaAlumno(boolean entregaAlumno) {
        this.entregaAlumno = entregaAlumno;
    }

    public boolean getEntregaAlumno() {
        return entregaAlumno;
    }

    public void setRetrasoEntrega(boolean retrasoEntrega) {
        this.retrasoEntrega = retrasoEntrega;
    }

    public boolean isRetrasoEntrega() {
        return retrasoEntrega;
    }
}
