package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 29/06/2017.
 */
@Table(database = AppDatabase.class)
public class RecursoDidacticoEventoC extends BaseEntity {

    public static final int TIPO_VIDEO = 379, TIPO_VINCULO = 380, TIPO_DOCUMENTO = 397, TIPO_IMAGEN = 398, TIPO_AUDIO = 399, TIPO_HOJA_CALCULO = 400, TIPO_DIAPOSITIVA = 401, TIPO_PDF = 402;

    @Column
    private String recursoDidacticoId;
    @Column
    private String titulo;
    @Column
    private String descripcion;
    @Column
    private int tipoId;
    @Column
    private int silaboEventoId;
    @Column
    private int unidadAprendizajeId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int actividadAprendizajeId;
    @Column
    private int estado;
    @Column
    private int usuarioCreador;
    @Column
    private int planCursoId;
    @Column
    private int estadoExportado;
    @Column
    private String url;
    @Column
    private String localUrl;

    public RecursoDidacticoEventoC() {
    }

    public int getEstadoExportado() {
        return estadoExportado;
    }

    public void setEstadoExportado(int estadoExportado) {
        this.estadoExportado = estadoExportado;
    }

    public String getRecursoDidacticoId() {
        return recursoDidacticoId;
    }

    public void setRecursoDidacticoId(String recursoDidacticoId) {
        this.recursoDidacticoId = recursoDidacticoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
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

    public int getActividadAprendizajeId() {
        return actividadAprendizajeId;
    }

    public void setActividadAprendizajeId(int actividadAprendizajeId) {
        this.actividadAprendizajeId = actividadAprendizajeId;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(int usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }


    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }
}
