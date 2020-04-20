package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

/**
 * Created by irvinmarin on 27/07/2017.
 */

@Table(database = AppDatabase.class)
public class RecursoArchivo extends BaseRelEntity {


    @PrimaryKey
    private String recursoDidacticoId;
    @PrimaryKey
    private String archivoId;

    public RecursoArchivo() {
    }

    public String getRecursoDidacticoId() {
        return recursoDidacticoId;
    }

    public void setRecursoDidacticoId(String recursoDidacticoId) {
        this.recursoDidacticoId = recursoDidacticoId;
    }

    public String getArchivoId() {
        return archivoId;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }
}
