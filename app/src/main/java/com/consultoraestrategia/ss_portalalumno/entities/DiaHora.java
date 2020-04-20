package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 15/05/2018.
 */
@Table(database = AppDatabase.class)
public class DiaHora extends BaseModel {

    @PrimaryKey
    @Column
    private int id;
    @Column
    private int horaId;
    @Column
    private int diaId;

    public DiaHora() {
    }

    public DiaHora(int id, int horaId, int diaId) {
        this.id = id;
        this.horaId = horaId;
        this.diaId = diaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHoraId() {
        return horaId;
    }

    public void setHoraId(int horaId) {
        this.horaId = horaId;
    }

    public int getDiaId() {
        return diaId;
    }

    public void setDiaId(int diaId) {
        this.diaId = diaId;
    }
}
