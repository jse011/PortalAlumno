package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/06/2017.
 */
@Table(database = AppDatabase.class)
public class SilaboEvento extends BaseModel {
    public final static int ESTADO_CREADO = 243, ESTADO_AUTORIZADO = 244, ESTADO_PROCESO = 245, ESTADO_PUBLICADO = 246, ESTADO_ELIMINADO = 397;
    @Unique
    @PrimaryKey
    private int silaboEventoId;
    @Column
    private String titulo;
    @Column
    private String descripcionGeneral;
    @Column
    private int planCursoId;
    @Column
    private int entidadId;
    @Column
    private int docenteId;
    @Column
    private int seccionId;
    @Column
    private String fechaInicio;
    @Column
    private String fechaFin;
    @Column
    private int usuarioCreadorId;
    @Column
    private String fechaCreacion;
    @Column
    private int usuarioAccionId;
    @Column
    private String fechaAccion;
    @Column
    private int estadoId;
    @Column
    private int anioAcademicoId;
    @Column
    private int georeferenciaId;
    @Column
    private int silaboBaseId;
    @Column
    private int cargaCursoId;


    @Column
    private int parametroDisenioId;

    public int getParametroDisenioId() {
        return parametroDisenioId;
    }

    public void setParametroDisenioId(int parametroDisenioId) {
        this.parametroDisenioId = parametroDisenioId;
    }

    public SilaboEvento() {
    }


    public SilaboEvento(int silaboEventoId, String titulo, String descripcionGeneral, int planCursoId, int entidadId, int docenteId, int seccionId, String fechaInicio, String fechaFin, int usuarioCreadorId, String fechaCreacion, int usuarioAccionId, String fechaAccion, int estadoId, int anioAcademicoId, int georeferenciaId, int silaboBaseId) {
        this.silaboEventoId = silaboEventoId;
        this.titulo = titulo;
        this.descripcionGeneral = descripcionGeneral;
        this.planCursoId = planCursoId;
        this.entidadId = entidadId;
        this.docenteId = docenteId;
        this.seccionId = seccionId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioCreadorId = usuarioCreadorId;
        this.fechaCreacion = fechaCreacion;
        this.usuarioAccionId = usuarioAccionId;
        this.fechaAccion = fechaAccion;
        this.estadoId = estadoId;
        this.anioAcademicoId = anioAcademicoId;
        this.georeferenciaId = georeferenciaId;
        this.silaboBaseId = silaboBaseId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcionGeneral() {
        return descripcionGeneral;
    }

    public void setDescripcionGeneral(String descripcionGeneral) {
        this.descripcionGeneral = descripcionGeneral;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public int getSeccionId() {
        return seccionId;
    }

    public void setSeccionId(int seccionId) {
        this.seccionId = seccionId;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getUsuarioCreadorId() {
        return usuarioCreadorId;
    }

    public void setUsuarioCreadorId(int usuarioCreadorId) {
        this.usuarioCreadorId = usuarioCreadorId;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getUsuarioAccionId() {
        return usuarioAccionId;
    }

    public void setUsuarioAccionId(int usuarioAccionId) {
        this.usuarioAccionId = usuarioAccionId;
    }

    public String getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(String fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getSilaboBaseId() {
        return silaboBaseId;
    }

    public void setSilaboBaseId(int silaboBaseId) {
        this.silaboBaseId = silaboBaseId;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }
}
