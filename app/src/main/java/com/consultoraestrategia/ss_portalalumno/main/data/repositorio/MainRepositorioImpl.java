package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Aula;
import com.consultoraestrategia.ss_portalalumno.entities.Aula_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaAcademica;
import com.consultoraestrategia.ss_portalalumno.entities.CargaAcademica_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursoDocente;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursoDocente_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Contrato;
import com.consultoraestrategia.ss_portalalumno.entities.Contrato_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Cursos;
import com.consultoraestrategia.ss_portalalumno.entities.Cursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.DetalleContratoAcad;
import com.consultoraestrategia.ss_portalalumno.entities.DetalleContratoAcad_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Empleado;
import com.consultoraestrategia.ss_portalalumno.entities.Empleado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.NivelAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.NivelAcademico_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ParametrosDisenio;
import com.consultoraestrategia.ss_portalalumno.entities.ParametrosDisenio_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Periodo;
import com.consultoraestrategia.ss_portalalumno.entities.Periodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Persona_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PlanEstudios;
import com.consultoraestrategia.ss_portalalumno.entities.PlanEstudios_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo;
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.queryCustom.CursoCustom;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainRepositorioImpl implements MainRepositorio {


    private static final String TAG = MainRepositorioImpl.class.getSimpleName();

    @Override
    public AlumnoUi getAlumno() {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        AlumnoUi alumnoUi = new AlumnoUi();
        alumnoUi.setPersonaId(sessionUser.getPersonaId());
        alumnoUi.setPersonaId(sessionUser.getPersonaId());
        alumnoUi.setUsuarioId(sessionUser.getUserId());
        Persona persona = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(sessionUser.getPersonaId()))
                .querySingle();
        alumnoUi.setFoto(persona==null?"":persona.getFoto());
        alumnoUi.setNombre(persona==null?"":UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombreCompleto()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()));

        AnioAcademicoAlumno anioAcademicoAlumno = SQLite.select()
                .from(AnioAcademicoAlumno.class)
                .where(AnioAcademicoAlumno_Table.personaId.eq(persona==null?0:persona.getPersonaId()))
                .and(AnioAcademicoAlumno_Table.toogle.eq(true))
                .querySingle();

        Contrato contrato = SQLite.select()
                .from(Contrato.class)
                .where(Contrato_Table.personaId.eq(persona==null?0:persona.getPersonaId()))
                .and(Contrato_Table.idAnioAcademico.eq(anioAcademicoAlumno==null?0:anioAcademicoAlumno.getIdAnioAcademico()))
                .querySingle();

        Persona apoderado = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(contrato==null?0:contrato.getApoderadoId()))
                .querySingle();

        alumnoUi.setFotoApoderado(apoderado==null?"":apoderado.getFoto());

        return alumnoUi;
    }

    @Override
    public AnioAcademicoUi getAnioAcademico(int personaId) {
        AnioAcademicoAlumno anioAcademicoAlumno = SQLite.select()
                .from(AnioAcademicoAlumno.class)
                .where(AnioAcademicoAlumno_Table.personaId.eq(personaId))
                .and(AnioAcademicoAlumno_Table.toogle.eq(true))
                .querySingle();
        AnioAcademicoUi anioAcademicoUi = new AnioAcademicoUi();
        if(anioAcademicoAlumno!=null){
            anioAcademicoUi.setAnioAcademicoId(anioAcademicoAlumno.getIdAnioAcademico());
            anioAcademicoUi.setNombre(anioAcademicoAlumno.getNombre());
        }
        return anioAcademicoUi;
    }

    @Override
    public List<CursosUi> getCursos(int personaId, int anioAcademicoId) {
        List<CursoCustom> cursoCustoms = SQLite.select(
                Cursos_Table.cursoId.withTable(),
                Cursos_Table.alias.withTable(),
                Cursos_Table.color.withTable(),
                Cursos_Table.descripcion.withTable(),
                Cursos_Table.entidadId.withTable(),
                Cursos_Table.estadoId.withTable(),
                Cursos_Table.nivelAcadId.withTable(),
                Cursos_Table.nombre.withTable(),
                Cursos_Table.tipoCursoId.withTable(),
                CargaCursos_Table.cargaCursoId.withTable(),
                CargaCursos_Table.empleadoId.withTable(),
                CargaCursos_Table.complejo.withTable(),
                CargaAcademica_Table.cargaAcademicaId.withTable(),
                CargaAcademica_Table.seccionId.withTable(),
                CargaAcademica_Table.periodoId.withTable(),
                CargaAcademica_Table.aulaId.withTable(),
                CargaAcademica_Table.idPlanEstudio.withTable(),
                CargaAcademica_Table.idPlanEstudioVersion.withTable(),
                CargaAcademica_Table.idAnioAcademico.withTable(),
                Seccion_Table.nombre.withTable().as("seccion"),
                Periodo_Table.alias.withTable().as("periodo"),
                ProgramasEducativo_Table.programaEduId.withTable(),
                ProgramasEducativo_Table.nombre.withTable().as("nombrePrograma"),
                ProgramasEducativo_Table.toogle.withTable(),
                Aula_Table.numero.withTable().as("numeroAula"),
                Aula_Table.descripcion.withTable().as("descripcionAula"),
                PlanCursos_Table.planCursoId.withTable(),
                NivelAcademico_Table.nombre.withTable().as("nivelAcademico")
                )
                .from(Cursos.class)
                .innerJoin(PlanCursos.class)
                .on(Cursos_Table.cursoId.withTable()
                        .eq(PlanCursos_Table.cursoId.withTable()))
                .innerJoin(PlanEstudios.class)
                .on(PlanCursos_Table.planEstudiosId.withTable()
                        .eq(PlanEstudios_Table.planEstudiosId.withTable()))
                .innerJoin(ProgramasEducativo.class)
                .on(PlanEstudios_Table.programaEduId.withTable()
                        .eq(ProgramasEducativo_Table.programaEduId.withTable()))
                .innerJoin(CargaCursos.class)
                .on(PlanCursos_Table.planCursoId.withTable()
                        .eq(CargaCursos_Table.planCursoId.withTable()))
                //#region Detalle Curso
                .innerJoin(CargaAcademica.class)
                .on(CargaCursos_Table.cargaAcademicaId.withTable()
                        .eq(CargaAcademica_Table.cargaAcademicaId.withTable()))
                .innerJoin(Seccion.class)
                .on(Seccion_Table.seccionId.withTable()
                        .eq(CargaAcademica_Table.seccionId.withTable()))
                .innerJoin(Periodo.class)
                .on(Periodo_Table.periodoId.withTable()
                        .eq(CargaAcademica_Table.periodoId.withTable()))
                .innerJoin(DetalleContratoAcad.class)
                .on(DetalleContratoAcad_Table.cargaCursoId.withTable()
                        .eq(CargaCursos_Table.cargaCursoId.withTable()))
                .innerJoin(Contrato.class)
                .on(Contrato_Table.idContrato.withTable()
                        .eq(DetalleContratoAcad_Table.idContrato.withTable()))
                .innerJoin(Aula.class)
                .on(CargaAcademica_Table.aulaId.withTable()
                        .eq(Aula_Table.aulaId.withTable()))
                .innerJoin(NivelAcademico.class)
                .on(ProgramasEducativo_Table.nivelAcadId.withTable()
                        .eq(NivelAcademico_Table.nivelAcadId.withTable()))
                //#endregion Detalle
                .where(PlanEstudios_Table.planEstudiosId.withTable()
                        .eq(CargaAcademica_Table.idPlanEstudio.withTable()))

                .and(DetalleContratoAcad_Table.cargaAcademicaId.withTable()
                        .eq(CargaAcademica_Table.cargaAcademicaId.withTable()))
                .and(Contrato_Table.personaId.withTable().eq(personaId))
                .and(DetalleContratoAcad_Table.anioAcademicoId.withTable().eq(anioAcademicoId))
                //7: Creado, 11 = eliminado, 33: actyalizado, 78 = autorizado, 109 : termindado
                .queryCustomList(CursoCustom.class);

        Log.d(TAG, "cursoCustoms " +cursoCustoms.size());
        List<CursosUi> cursosUiList = new ArrayList<>();
        for(CursoCustom cursoCustom : cursoCustoms){
            CursosUi cursosUi = new CursosUi();
            cursosUi.setNombre(cursoCustom.getNombre());
            cursosUi.setCursoId(cursoCustom.getCursoId());
            ProgramaEduactivoUI programasEducativoUI = new ProgramaEduactivoUI();
            programasEducativoUI.setIdPrograma(cursoCustom.getProgramaEduId());
            programasEducativoUI.setNombrePrograma(cursoCustom.getNombrePrograma());
            programasEducativoUI.setSeleccionado(cursoCustom.isTooglePrograma());
            cursosUi.setProgramaEduactivoUI(programasEducativoUI);
            cursosUi.setSalon(cursoCustom.getDescripcionAula() + ": "+cursoCustom.getNumeroAula());
            cursosUi.setSeccionyperiodo(cursoCustom.getPeriodo()+" "+cursoCustom.getSeccion()+" - "+cursoCustom.getNivelAcademico());
            cursosUi.setCargaCursoId(cursoCustom.getCargaCursoId());
            cursosUi.setPlanCursoId(cursoCustom.getPlanCursoId());
            ParametrosDisenio parametrosDisenio = SQLite.select()
                    .from(ParametrosDisenio.class)
                    .innerJoin(SilaboEvento.class)
                    .on(ParametrosDisenio_Table.parametroDisenioId.withTable()
                            .eq(SilaboEvento_Table.parametroDisenioId.withTable()))
                    .where(SilaboEvento_Table.cargaCursoId.withTable()
                            .eq(cursosUi.getCargaCursoId()))
                    .and(SilaboEvento_Table.estadoId.withTable().notEq(SilaboEvento.ESTADO_CREADO))
                    .and(ParametrosDisenio_Table.estado.withTable().eq(true))
                    .querySingle();


            if(parametrosDisenio==null)parametrosDisenio = SQLite.select()
                    .from(ParametrosDisenio.class)
                    .where(ParametrosDisenio_Table.nombre.eq("default"))
                    .querySingle();


            if(parametrosDisenio!=null){
                cursosUi.setUrlBackgroundItem(parametrosDisenio.getPath());
                cursosUi.setBackgroundSolidColor(parametrosDisenio.getColor1());
                cursosUi.setBackgroundSolidColor2(parametrosDisenio.getColor2());
                cursosUi.setBackgroundSolidColor3(parametrosDisenio.getColor3());
            }

            Persona empleado = null;
            if(cursoCustom.getEmpleadoId()!=0){
                empleado = SQLite.select()
                        .from(Persona.class)
                        .innerJoin(Empleado.class)
                        .on(Persona_Table.personaId.withTable()
                                .eq(Empleado_Table.personaId.withTable()))
                        .where(Empleado_Table.empleadoId.withTable().eq(cursoCustom.getEmpleadoId()))
                        .querySingle();
            }else {
                empleado = SQLite.select()
                        .from(Persona.class)
                        .innerJoin(Empleado.class)
                        .on(Persona_Table.personaId.withTable()
                                .eq(Empleado_Table.personaId.withTable()))
                        .innerJoin(CargaCursoDocente.class)
                        .on(CargaCursoDocente_Table.docenteId.withTable().eq(Empleado_Table.empleadoId.withTable()))
                        .where(CargaCursoDocente_Table.cargaCursoId.withTable().eq(cursoCustom.getCargaCursoId()))
                        .querySingle();
            }

            if(empleado!=null){
                cursosUi.setProfesor(UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(empleado.getNombres())) +" "+ UtilsPortalAlumno.capitalize(empleado.getApellidoPaterno()));
                cursosUi.setFotoProfesor(empleado.getFoto());
            }


            cursosUiList.add(cursosUi);
        }


        return cursosUiList;
    }


}
