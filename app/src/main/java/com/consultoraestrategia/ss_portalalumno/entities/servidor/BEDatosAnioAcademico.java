package com.consultoraestrategia.ss_portalalumno.entities.servidor;

import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;

import java.util.List;

public class BEDatosAnioAcademico {
    private List<CalendarioPeriodo> calendarioPeriodos;
    private List<CalendarioPeriodoDetalle> calendarioPeriodoDetalles;
    private List<CalendarioAcademico> calendarioAcademico;
    private List<Tipos> tipos;
    private List<Webconfig> bEWebConfigs;

    public List<CalendarioPeriodo> getCalendarioPeriodos() {
        return calendarioPeriodos;
    }

    public void setCalendarioPeriodos(List<CalendarioPeriodo> calendarioPeriodos) {
        this.calendarioPeriodos = calendarioPeriodos;
    }

    public List<CalendarioPeriodoDetalle> getCalendarioPeriodoDetalles() {
        return calendarioPeriodoDetalles;
    }

    public void setCalendarioPeriodoDetalles(List<CalendarioPeriodoDetalle> calendarioPeriodoDetalles) {
        this.calendarioPeriodoDetalles = calendarioPeriodoDetalles;
    }

    public List<CalendarioAcademico> getCalendarioAcademico() {
        return calendarioAcademico;
    }

    public void setCalendarioAcademico(List<CalendarioAcademico> calendarioAcademico) {
        this.calendarioAcademico = calendarioAcademico;
    }

    public List<Tipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipos> tipos) {
        this.tipos = tipos;
    }

    public List<Webconfig>  getBEWebConfigs() {
        return bEWebConfigs;
    }

    public void setBEWebConfigs(List<Webconfig>  beWebConfigs) {
        this.bEWebConfigs = beWebConfigs;
    }
}
