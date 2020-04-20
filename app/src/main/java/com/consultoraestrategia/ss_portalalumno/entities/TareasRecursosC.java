package com.consultoraestrategia.ss_portalalumno.entities;


import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;


/**
 * Created by irvinmarin on 05/12/2017.
 */
@Table(database = AppDatabase.class)
public class TareasRecursosC extends BaseRelEntity {
    @PrimaryKey
    private String tareaId;//keyTarea
    @PrimaryKey
    private String recursoDidacticoId;//keyRecursoDidactico
    @Column
    private int estadoExportado;

    public TareasRecursosC() {
    }

    public String getTareaId() {
        return tareaId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getRecursoDidacticoId() {
        return recursoDidacticoId;
    }

    public void setRecursoDidacticoId(String recursoDidacticoId) {
        this.recursoDidacticoId = recursoDidacticoId;
    }

    public int getEstadoExportado() {
        return estadoExportado;
    }

    public void setEstadoExportado(int estadoExportado) {
        this.estadoExportado = estadoExportado;
    }


}
