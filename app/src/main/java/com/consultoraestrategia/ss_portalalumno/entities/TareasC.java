package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by irvinmarin on 10/11/2017.
 */
@Table(database = AppDatabase.class)
public class TareasC extends BaseEntity {
    public final static int ESTADO_CREADO = 263, ESTADO_ELIMINADO = 265 , ESTADO_PUBLICADO = 264;
    @Column
    private String tareaId;
    @Column
    private String titulo;
    @Column
    private String instrucciones;
    @Column
    private int estadoId;
    @Column
    private int unidadAprendizajeId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private long fechaEntrega;
    @Column
    private String horaEntrega;
    @Column
    private int estadoExportado;

    private List<RecursoDidacticoEventoC> tareasRecursosList;

    public TareasC() {
    }

    public String getTareaId() {
        return tareaId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public long getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(long fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public int getEstadoExportado() {
        return estadoExportado;
    }

    public void setEstadoExportado(int estadoExportado) {
        this.estadoExportado = estadoExportado;
    }

    public List<RecursoDidacticoEventoC> getTareasRecursosList() {
        return tareasRecursosList;
    }

    public void setTareasRecursosList(List<RecursoDidacticoEventoC> tareasRecursosList) {
        this.tareasRecursosList = tareasRecursosList;
    }
}
