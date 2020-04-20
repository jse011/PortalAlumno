package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by irvinmarin on 23/03/2017.
 */

@Table(database = AppDatabase.class)
public class PlanCursos extends BaseModel {
    @Column
    @PrimaryKey
    private int planCursoId;
    @Column
    private int cursoId;
    @Column
    private int periodoId;
    @Column
    private int planEstudiosId;

    public PlanCursos() {
    }

    public PlanCursos(int planCursoId, int cursoId, int periodoId, int planEstudiosId) {
        this.planCursoId = planCursoId;
        this.cursoId = cursoId;
        this.periodoId = periodoId;
        this.planEstudiosId = planEstudiosId;
    }

    public int getPeriodoId() {
        return periodoId;
    }

    public void setPeriodoId(int periodoId) {
        this.periodoId = periodoId;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getPlanEstudiosId() {
        return planEstudiosId;
    }

    public void setPlanEstudiosId(int planEstudiosId) {
        this.planEstudiosId = planEstudiosId;
    }

//    public void getPlanCursos(int cursoId){
//        PlanCursos planCursos = PlanCursos.find(PlanCursos.class,"curso_Id =?" , cursoId+"").get(0);
//        PlanEstudios planEstudios = PlanEstudios.findById(PlanEstudios.class, planCursos.getPlanEstudiosId());
//        CargaCursos cargaCursos = CargaCursos.find(CargaCursos.class, "planCurso_Id =?", planCursos.getPlanCursoId() + "").get(0);
//        Empleado empleado = findById(Empleado.class, cargaCursos.getEmpleadoId());
//        Persona persona = findById(Persona.class, empleado.getPersonaId());
//    }


    @Override
    public String toString() {
        return "PlanCursos{" +
                "planCursoId=" + planCursoId +
                ", cursoId=" + cursoId +
                ", planEstudiosId=" + planEstudiosId +
                '}';
    }
}
