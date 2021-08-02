package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

@Table(database = AppDatabase.class)
public class EventoAdjunto extends BaseEntity {
    @PrimaryKey
    String eventoAdjuntoId;
    @Column
    String eventoId;
    @Column
    String titulo;
    @Column
    int tipoId;
    @Column
    String driveId;

    public String getEventoAdjuntoId() {
        return eventoAdjuntoId;
    }

    public void setEventoAdjuntoId(String eventoAdjuntoId) {
        this.eventoAdjuntoId = eventoAdjuntoId;
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }
}