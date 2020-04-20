package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class Tipos extends BaseModel {

    public static final int MERITO=541, DEMERITO=542 ,TIPO_ASISTENCIA=474, ENFOQUE_TRANSVERSAL=349, COMPETENCIA_TRANSVERSAL=348;
    @Unique
    @PrimaryKey
    private int tipoId;
    @Column
    private String objeto;
    @Column
    private String concepto;
    @Column
    private String nombre;
    @Column
    private String codigo;
    @Column
    private Integer parentId;

    public Tipos(int tipoId, String objeto, String concepto, String nombre, String codigo) {
        this.tipoId = tipoId;
        this.objeto = objeto;
        this.concepto = concepto;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public Tipos() {

    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getTipoId() {
        return tipoId;
    }

    public void setTipoId(int tipoId) {
        this.tipoId = tipoId;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
