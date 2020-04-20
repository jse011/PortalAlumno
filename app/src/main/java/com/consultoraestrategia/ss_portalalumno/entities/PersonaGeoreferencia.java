package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by irvinmarin on 23/03/2017.
 */
@Table(database = AppDatabase.class)
public class PersonaGeoreferencia extends BaseModel {

    @PrimaryKey
    private int personaGeoreferenciaId;

    @Column
    private int personaId;

    @Column
    private int georeferenciaId;

    public PersonaGeoreferencia() {
    }

    public PersonaGeoreferencia(int personaId, int georeferenciaId) {
        this.personaId = personaId;
        this.georeferenciaId = georeferenciaId;
    }

    public int getPersonaGeoreferenciaId() {
        return personaGeoreferenciaId;
    }

    public void setPersonaGeoreferenciaId(int personaGeoreferenciaId) {
        this.personaGeoreferenciaId = personaGeoreferenciaId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

}
