package com.consultoraestrategia.ss_portalalumno.entities;


import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class AnioAcademico extends BaseModel {
    public static final int ANIO_ACADEMICO_MATRICULA = 192;
    public static final int ANIO_ACADEMICO_ACTIVO = 193;
    public static final int ANIO_ACADEMICO_CERRADO = 194;
    public static final int ANIO_ACADEMICO_CREADO = 195;
    public static final int ANIO_ACADEMICO_ELIMINADO = 196;

    @Unique
    @PrimaryKey
    int idAnioAcademico;
    @Column
    private String nombre;
    @Column
    private String fechaInicio;
    @Column
    private String fechaFin;
    @Column
    private String denominacion;
    @Column
    private int georeferenciaId;
    @Column
    private int organigramaId;
    @Column
    private int estadoId;
    @Column
    private int tipoId;

//    "idAnioAcademico": 1,
//            "nombre": "2017",
//            "fechaInicio": "01/01/2017",
//            "fechaFin": "31/12/2017",
//            "denominacion": "Año del Buen Servicio al Ciudadano",
//            "georeferenciaId": "1",
//            "organigramaId": "58",
//            "estadoId": 192,
//            "tipoId": 305

    public AnioAcademico() {
    }

    public AnioAcademico(int idAnioAcademico, String nombre, String fechaInicio, String fechaFin, String denominacion, int georeferenciaId, int organigramaId, int estadoId, int tipoId) {
        this.idAnioAcademico = idAnioAcademico;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.denominacion = denominacion;
        this.georeferenciaId = georeferenciaId;
        this.organigramaId = organigramaId;
        this.estadoId = estadoId;
        this.tipoId = tipoId;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public int getIdAnioAcademico() {
        return idAnioAcademico;
    }

    public void setIdAnioAcademico(int idAnioAcademico) {
        this.idAnioAcademico = idAnioAcademico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getOrganigramaId() {
        return organigramaId;
    }

    public void setOrganigramaId(int organigramaId) {
        this.organigramaId = organigramaId;
    }

    public int getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(int estadoId) {
        this.estadoId = estadoId;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
