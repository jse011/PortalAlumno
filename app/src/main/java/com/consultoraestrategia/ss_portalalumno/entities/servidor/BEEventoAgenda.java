package com.consultoraestrategia.ss_portalalumno.entities.servidor;


import com.consultoraestrategia.ss_portalalumno.entities.Calendario2;
import com.consultoraestrategia.ss_portalalumno.entities.Evento2;
import com.consultoraestrategia.ss_portalalumno.entities.EventoAdjunto;
import com.consultoraestrategia.ss_portalalumno.entities.EventoTipos;

import java.util.List;

public class BEEventoAgenda {
    private List<Calendario2> calendarios;
    private List<Evento2> eventos;
    private List<EventoTipos> tipos;
    private List<EventoAdjunto> eventoAdjuntos;

    public List<Calendario2> getCalendarios() {
        return calendarios;
    }

    public void setCalendarios(List<Calendario2> calendarios) {
        this.calendarios = calendarios;
    }

    public List<Evento2> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento2> eventos) {
        this.eventos = eventos;
    }

    public List<EventoTipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<EventoTipos> tipos) {
        this.tipos = tipos;
    }

    public List<EventoAdjunto> getEventoAdjuntos() {
        return eventoAdjuntos;
    }

    public void setEventoAdjuntos(List<EventoAdjunto> eventoAdjuntos) {
        this.eventoAdjuntos = eventoAdjuntos;
    }
}
