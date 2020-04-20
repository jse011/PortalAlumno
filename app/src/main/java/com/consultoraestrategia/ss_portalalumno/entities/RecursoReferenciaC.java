package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 18/08/2017.
 */
@Table(database = AppDatabase.class)
public class RecursoReferenciaC extends BaseModel {


    @PrimaryKey
    private int recursoReferenciaId;
    @Column
    private String recursoDidacticoId;
    @Column
    private int silaboEventoId;
    @Column
    private int unidadAprendizajeId;
    @Column
    private int sesionAprendizajeId;
    @Column
    private int actividadAprendizajeId;

    public RecursoReferenciaC() {
    }

    public int getRecursoReferenciaId() {
        return recursoReferenciaId;
    }

    public void setRecursoReferenciaId(int recursoReferenciaId) {
        this.recursoReferenciaId = recursoReferenciaId;
    }

    public String getRecursoDidacticoId() {
        return recursoDidacticoId;
    }

    public void setRecursoDidacticoId(String recursoDidacticoId) {
        this.recursoDidacticoId = recursoDidacticoId;
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


}