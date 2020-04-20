package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by SCIEV on 21/12/2017.
 */
// No vine del servicio Elimar
@Table(database = AppDatabase.class)
public class SesionEventoDesempenioIcd extends BaseModel {
    @PrimaryKey(autoincrement = true)
    @Column
    private int sesionAprendizajeDesempenioIcdId;
    @Column
    private int desempenioicdid;
    @Column
    private int sesionaprendizajeid;

    public SesionEventoDesempenioIcd() {
    }


    public int getSesionAprendizajeDesempenioIcdId() {
        return sesionAprendizajeDesempenioIcdId;
    }

    public void setSesionAprendizajeDesempenioIcdId(int sesionAprendizajeDesempenioIcdId) {
        this.sesionAprendizajeDesempenioIcdId = sesionAprendizajeDesempenioIcdId;
    }

    public int getDesempenioicdid() {
        return desempenioicdid;
    }

    public void setDesempenioicdid(int desempenioicdid) {
        this.desempenioicdid = desempenioicdid;
    }

    public int getSesionaprendizajeid() {
        return sesionaprendizajeid;
    }

    public void setSesionaprendizajeid(int sesionaprendizajeid) {
        this.sesionaprendizajeid = sesionaprendizajeid;
    }
}

