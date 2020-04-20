package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 27/07/2017.
 */
@Table(database = AppDatabase.class)
public class HorarioHora extends BaseModel {

    @Unique
    @PrimaryKey
    private int idHorarioHora;
    @Column
    private int horaId;
    @Column
    private int detalleHoraId;



    public HorarioHora() {
    }


    public HorarioHora(int idHorarioHora, int horaId, int detalleHoraId) {
        this.idHorarioHora = idHorarioHora;
        this.horaId = horaId;
        this.detalleHoraId = detalleHoraId;
    }

    public int getIdHorarioHora() {
        return idHorarioHora;
    }

    public void setIdHorarioHora(int idHorarioHora) {
        this.idHorarioHora = idHorarioHora;
    }

    public int getHoraId() {
        return horaId;
    }

    public void setHoraId(int horaId) {
        this.horaId = horaId;
    }

    public int getDetalleHoraId() {
        return detalleHoraId;
    }

    public void setDetalleHoraId(int detalleHoraId) {
        this.detalleHoraId = detalleHoraId;
    }
}
