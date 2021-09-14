package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Aula;
import com.consultoraestrategia.ss_portalalumno.entities.Aula_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Calendario2;
import com.consultoraestrategia.ss_portalalumno.entities.Calendario2_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
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
import com.consultoraestrategia.ss_portalalumno.entities.Evento;
import com.consultoraestrategia.ss_portalalumno.entities.Evento2;
import com.consultoraestrategia.ss_portalalumno.entities.Evento2_Table;
import com.consultoraestrategia.ss_portalalumno.entities.EventoAdjunto;
import com.consultoraestrategia.ss_portalalumno.entities.EventoAdjunto_Table;
import com.consultoraestrategia.ss_portalalumno.entities.EventoTipos;
import com.consultoraestrategia.ss_portalalumno.entities.EventoTipos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Georeferencia;
import com.consultoraestrategia.ss_portalalumno.entities.Georeferencia_Table;
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
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaPA;
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo;
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RelProgramaEducativoTipoNota;
import com.consultoraestrategia.ss_portalalumno.entities.Relaciones;
import com.consultoraestrategia.ss_portalalumno.entities.Relaciones_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.queryCustom.CalendarioEventoQuery;
import com.consultoraestrategia.ss_portalalumno.entities.queryCustom.CursoCustom;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDatosAnioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEEventoAgenda;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.FamiliaUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoArchivo;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeThumbnail;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class MainRepositorioImpl implements MainRepositorio {

    private ApiRetrofit apiRetrofit;

    public MainRepositorioImpl(ApiRetrofit apiRetrofit) {
        this.apiRetrofit = apiRetrofit;
    }

    private static final String TAG = MainRepositorioImpl.class.getSimpleName();

    @Override
    public AlumnoUi getAlumno() {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        AlumnoUi alumnoUi = new AlumnoUi();
        alumnoUi.setPersonaId(sessionUser.getPersonaId());
        alumnoUi.setPersonaId(sessionUser.getPersonaId());
        alumnoUi.setUsuarioId(sessionUser.getUserId());

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlFotos"))
                .querySingle();
        String urlFoto = webconfig!=null?webconfig.getContent():"";

        Persona persona = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(sessionUser.getPersonaId()))
                .querySingle();
        int personaId = persona!=null?persona.getPersonaId():0;
        //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
        String fileName = persona!=null?persona.getFoto():"";
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        fileName = fileName.substring(p + 1);
        if(!TextUtils.isEmpty(fileName) &&"default.png".equals(fileName)){
            alumnoUi.setFoto(persona.getFoto());
        }else {
            alumnoUi.setFoto(urlFoto+personaId+"/"+fileName);
        }
        alumnoUi.setNombre(persona==null?"":UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getFirstName()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()));
        String fechaNacimientoPadre = "";
        if(persona!=null&&persona.getFechaNac()!=null&&!persona.getFechaNac().isEmpty()){
            fechaNacimientoPadre = UtilsPortalAlumno.calcularEdad(persona.getFechaNac()) + " años " +  UtilsPortalAlumno.f_fecha_letrasSinHora(persona.getFechaNac());

        }
        alumnoUi.setFechaNacimiento(fechaNacimientoPadre);
        alumnoUi.setCelular(!TextUtils.isEmpty(persona.getCelular())?persona.getCelular(): persona.getTelefono());
        alumnoUi.setCorreo(persona.getCorreo());

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

        Usuario usuario = SQLite.select()
                .from(Usuario.class)
                .where(Usuario_Table.usuarioId.eq(alumnoUi.getUsuarioId()))
                .querySingle();

        alumnoUi.setHabilitarAcceso(usuario != null && usuario.isHabilitarAcceso());

        List<Persona> familiares =  SQLite.select(UtilsDBFlow.f_allcolumnTable(Persona_Table.ALL_COLUMN_PROPERTIES))
                .from(Persona.class)
                .innerJoin(Relaciones.class)
                .on(Relaciones_Table.personaVinculadaId.withTable()
                        .eq(Persona_Table.personaId))
                .where(Relaciones_Table.personaPrincipalId.eq(personaId))
                .groupBy(Persona_Table.personaId.withTable())
                .queryList();

        List<FamiliaUi> familiaUiList = new ArrayList<>();

        for (Persona personaData: familiares){
            String fechaNacimientoHijo = "";
            if(personaData.getFechaNac()!=null&&!personaData.getFechaNac().isEmpty()){
                fechaNacimientoHijo = UtilsPortalAlumno.calcularEdad(personaData.getFechaNac()) + " años " +  UtilsPortalAlumno.f_fecha_letrasSinHora(personaData.getFechaNac());
            }

            FamiliaUi familiaUi = new FamiliaUi();
            familiaUi.setPersonaId(personaData.getPersonaId());
            familiaUi.setNombre(personaData.getNombreCompleto());
            familiaUi.setFoto(personaData.getFoto());
            familiaUi.setDocumento(personaData.getNumDoc());
            familiaUi.setCelular(TextUtils.isEmpty(personaData.getCelular())?personaData.getTelefono():personaData.getCelular());
            familiaUi.setCorreo(personaData.getCorreo());
            familiaUi.setFechaNacimiento(fechaNacimientoHijo);
            familiaUi.setFechaNacimiento2(personaData.getFechaNac());
            familiaUiList.add(familiaUi);
        }
        alumnoUi.setFamiliaUiList(familiaUiList);

        /*List<Persona> hermanos =  SQLite.select(UtilsDBFlow.f_allcolumnTable(Persona_Table.ALL_COLUMN_PROPERTIES))
                .from(Persona.class)
                .innerJoin(Relaciones.class)
                .on(Relaciones_Table.personaVinculadaId.withTable()
                        .eq(Persona_Table.personaId))
                .where(Relaciones_Table.personaPrincipalId.eq(personaId))
                .and(Relaciones_Table.tipoId.in(Relaciones.MADRE, Relaciones.PADRE, Relaciones.HERMANO))
                .groupBy(Persona_Table.personaId.withTable())
                .queryList();*/

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
            anioAcademicoUi.setGeoreferenciaId(anioAcademicoAlumno.getGeoreferenciaId());
        }
        Georeferencia georeferencia = SQLite.select()
                .from(Georeferencia.class)
                .where(Georeferencia_Table.georeferenciaId.eq(anioAcademicoUi.getGeoreferenciaId()))
                .querySingle();
        if(georeferencia!=null){
            anioAcademicoUi.setEntidadId(georeferencia.getEntidadId());
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
                SilaboEvento_Table.silaboEventoId.withTable(),
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
                .leftOuterJoin(SilaboEvento.class)
                .on(CargaCursos_Table.cargaCursoId.withTable()
                        .eq(SilaboEvento_Table.cargaCursoId.withTable()))
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
            cursosUi.setSilaboEventoId(cursoCustom.getSilaboEventoId());
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

    @Override
    public RetrofitCancel updateCalendarioPeriodo(int anioAcademico, SuccessCallback callback) {
        Call<RestApiResponse<BEDatosAnioAcademico>> responseCall = apiRetrofit.flst_getDatosCalendarioPeriodo(anioAcademico, 0);

        RetrofitCancel<BEDatosAnioAcademico> retrofitCancel = new RetrofitCancelImpl<>(responseCall);

        retrofitCancel.enqueue(new RetrofitCancel.Callback<BEDatosAnioAcademico>() {
            @Override
            public void onResponse(final BEDatosAnioAcademico response) {
                if(response == null){
                    Log.d(TAG,"response calendarioPeriodo null");
                }else {
                    Log.d(TAG,"response calendarioPeriodo true");

                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {
                            TransaccionUtils.deleteTable(Webconfig.class);

                            TransaccionUtils.fastStoreListSave(CalendarioPeriodo.class, response.getCalendarioPeriodos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListSave(CalendarioPeriodoDetalle.class, response.getCalendarioPeriodoDetalles(), databaseWrapper, true);
                            //TransaccionUtils.fastStoreListInsert(CargaCursoCalendarioPeriodo.class, response.getCargaCursoCalendarioPeriodo(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListSave(Tipos.class, response.getTipos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListSave(CalendarioAcademico.class, response.getCalendarioAcademico(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListSave(Webconfig.class, response.getBEWebConfigs(), databaseWrapper, true);
                            //

                        }
                    }).success(new Transaction.Success() {
                        @Override
                        public void onSuccess(@NonNull Transaction transaction) {
                            Log.d(TAG,"response calendarioPeriodo Transaction Success");
                            callback.onLoad(true);
                        }
                    }).error(new Transaction.Error() {
                        @Override
                        public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                            error.printStackTrace();
                            Log.d(TAG,"response calendarioPeriodo Transaction Error");
                            callback.onLoad(false);
                        }
                    }).build();

                    transaction.execute();


                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                Log.d(TAG,"response calendarioPeriodo Transaction Failure");
                callback.onLoad(false);
            }
        });

        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getEventoAgenda(int usuarioId, int alumnoId,int tipoEventoId, Callback<List<EventoUi>> callback) {

        Call<RestApiResponse<BEEventoAgenda>> responseCall = apiRetrofit.getEventoAgendaAlumno(usuarioId, alumnoId,tipoEventoId);

        RetrofitCancel<BEEventoAgenda> retrofitCancel = new RetrofitCancelImpl<>(responseCall);
        retrofitCancel.enqueue(new RetrofitCancel.Callback<BEEventoAgenda>() {
            @Override
            public void onResponse(BEEventoAgenda response) {
                if(response == null){
                    Log.d(TAG,"response getEventoAgenda null");
                    callback.onLoad(false, getEventosAgendaBaseDatos(usuarioId, alumnoId, tipoEventoId));
                }else {
                    saveEventoAgenda(response, usuarioId, alumnoId, tipoEventoId, callback);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false, getEventosAgendaBaseDatos(usuarioId, alumnoId, tipoEventoId));
            }
        });

        return retrofitCancel;
    }


    public void saveEventoAgenda(BEEventoAgenda beEventoAgenda, int usuarioId, int alumnoId, int tipoEventoId, Callback<List<EventoUi>> callback) {
        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {


                Where<Calendario2> calendarioWhere = SQLite.select(Calendario2_Table.calendarioId.withTable(), Evento2_Table.eventoId.withTable(),
                        Evento2_Table.likeCount.withTable(), Evento2_Table.like.withTable())
                        .from(Calendario2.class)
                        .innerJoin(Evento2.class)
                        .on(Calendario2_Table.calendarioId.withTable()
                                .eq(Evento2_Table.calendarioId.withTable()))
                        .where(Evento2_Table.usuarioReceptorId.withTable().eq(usuarioId));

                if(tipoEventoId>0){
                    calendarioWhere.and(Evento2_Table.tipoEventoId.withTable().eq(tipoEventoId));
                }

                List<String> calendarioDataList = new ArrayList<>();
                List<CalendarioEventoQuery> calendarioEventoQueryList = calendarioWhere.queryCustomList(CalendarioEventoQuery.class);
                List<Evento2> evento2ListDelete = new ArrayList<>();
                for(CalendarioEventoQuery calendarioEventoQuery : calendarioEventoQueryList){
                    Evento2 evento2delete = new Evento2();
                    evento2delete.setEventoId(calendarioEventoQuery.getEventoId());
                    evento2delete.setLike(calendarioEventoQuery.isLike());
                    evento2delete.setLikeCount(calendarioEventoQuery.getLikeCount());
                    evento2ListDelete.add(evento2delete);
                    SQLite.delete()
                            .from(Evento2.class)
                            .where(Evento2_Table.eventoId.eq(calendarioEventoQuery.eventoId))
                            .execute(databaseWrapper);

                    int position = calendarioDataList.indexOf(calendarioEventoQuery.getCalendarioId());
                    if (position == -1){
                        calendarioDataList.add(calendarioEventoQuery.getCalendarioId());
                    }
                }

                for (String calendarioId : calendarioDataList){
                    if(SQLite.select().from(Evento2.class).where(Evento2_Table.calendarioId.eq(calendarioId)).queryList(databaseWrapper).isEmpty()){
                        SQLite.delete().from(Calendario2.class).where(Calendario2_Table.calendarioId.eq(calendarioId)).execute(databaseWrapper);
                    }
                }

                SQLite.delete()
                        .from(EventoTipos.class)
                        .execute(databaseWrapper);

                for (Calendario2 calendario2 : beEventoAgenda.getCalendarios()!=null?beEventoAgenda.getCalendarios() : new ArrayList<Calendario2>()){
                    calendario2.setFechaAccion_(new Date(calendario2.getFechaAccion()));
                    calendario2.setFechaCreacion_(new Date(calendario2.getFechaCreacion()));
                }

                TransaccionUtils.fastStoreListSave(Calendario2.class, beEventoAgenda.getCalendarios(), databaseWrapper, false);

                for (Evento2 evento2 : beEventoAgenda.getEventos()!=null?beEventoAgenda.getEventos() : new ArrayList<Evento2>()){
                    evento2.setFechaEventoTime(UtilsPortalAlumno.convertDateTimePtBR(evento2.getFechaEvento_(), evento2.getHoraEvento()));
                    evento2.setFechaPublicacion_(new Date(evento2.getFechaPublicacion()));
                    for (Evento2 delete : evento2ListDelete){
                        if(delete.getEventoId().equals(evento2.getEventoId())){
                            evento2.setLikeCount(delete.getLikeCount());
                            evento2.setLike(delete.getLike());
                            break;
                        }
                    }
                }
                TransaccionUtils.fastStoreListSave(Evento2.class, beEventoAgenda.getEventos(), databaseWrapper, false);
                TransaccionUtils.fastStoreListSave(EventoTipos.class, beEventoAgenda.getTipos(), databaseWrapper, false);
                TransaccionUtils.fastStoreListSave(EventoAdjunto.class, beEventoAgenda.getEventoAdjuntos(), databaseWrapper, false);
            }
        }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                callback.onLoad(true, getEventosAgendaBaseDatos(usuarioId, alumnoId, tipoEventoId));
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                callback.onLoad(false, getEventosAgendaBaseDatos(usuarioId, alumnoId, tipoEventoId));
                error.printStackTrace();
            }
        }).build();

        transaction.execute();

    }


    public List<EventoUi> getEventosAgendaBaseDatos(int usuarioId, int alumnoId, int tipoEventoId) {
        List<EventoUi> eventoUiList = new ArrayList<>();

        Where<Evento2> eventoListWhere = SQLite.select(Evento2_Table.eventoId.withTable(), Evento2_Table.nombreEntidad.withTable(),
                Evento2_Table.fotoEntidad.withTable(), Evento2_Table.likeCount.withTable(), Evento2_Table.titulo.withTable(),
                Evento2_Table.descripcion.withTable(), Evento2_Table.fechaEventoTime.withTable(), Evento2_Table.pathImagen, Evento2_Table.tipoEventoId.withTable(),
                Evento2_Table.tipoEventoNombre.withTable(), Calendario2_Table.cargo.withTable(), Calendario2_Table.nUsuario.withTable(),
                Calendario2_Table.nombre.withTable().as("calendarioNombre"),
                Evento2_Table.fechaPublicacion.withTable(),
                Evento2_Table.fechaPublicacion_.withTable(),
                EventoAdjunto_Table.eventoAdjuntoId.withTable(),
                EventoAdjunto_Table.titulo.withTable().as("adjuntoTitulo"),
                EventoAdjunto_Table.tipoId.withTable().as("adjuntoTipoId"),
                EventoAdjunto_Table.driveId.withTable().as("adjuntoDriveId"),
                Calendario2_Table.nFoto.withTable()
        )
                .from(Evento2.class)
                .innerJoin(Calendario2.class)
                .on(Evento2_Table.calendarioId.withTable().eq(Calendario2_Table.calendarioId.withTable()))
                .leftOuterJoin(EventoAdjunto.class)
                .on(EventoAdjunto_Table.eventoId.withTable().eq(Evento2_Table.eventoId.withTable()))
                .where(Evento2_Table.usuarioReceptorId.withTable().eq(usuarioId));

        if(tipoEventoId>0){
            eventoListWhere.and(Evento2_Table.tipoEventoId.withTable().eq(tipoEventoId));
        }


        eventoListWhere.groupBy(Evento2_Table.eventoId.withTable(), EventoAdjunto_Table.eventoAdjuntoId);
        eventoListWhere.orderBy(Evento2_Table.fechaEventoTime.withTable(), false);
        List<CalendarioEventoQuery> calendarioEventoQueryList = eventoListWhere.queryCustomList(CalendarioEventoQuery.class);

        for (CalendarioEventoQuery calendarioEventoQuery : calendarioEventoQueryList){

            EventoUi eventoUi = new EventoUi();
            eventoUi.setId(calendarioEventoQuery.getEventoId());

            int position = eventoUiList.indexOf(eventoUi);
            if(position==-1){
                eventoUi.setNombreEntidad(calendarioEventoQuery.getNombreEntidad());
                eventoUi.setFotoEntidad(calendarioEventoQuery.getnFoto());
                eventoUi.setCantLike(calendarioEventoQuery.getLikeCount());
                eventoUi.setTitulo(calendarioEventoQuery.getTitulo());
                eventoUi.setDescripcion(calendarioEventoQuery.getDescripcion());
                eventoUi.setFecha(calendarioEventoQuery.getFechaEventoTime());
                eventoUi.setFoto(calendarioEventoQuery.getPathImagen());
                TipoEventoUi tipoEventoUi = new TipoEventoUi();
                tipoEventoUi.setId(calendarioEventoQuery.getTipoEventoId());
                tipoEventoUi.setNombre(calendarioEventoQuery.getTipoEventoNombre());
                eventoUi.setTipoEventoUi(tipoEventoUi);
                eventoUi.setRolEmisor(calendarioEventoQuery.getCargo());
                eventoUi.setNombreEmisor(calendarioEventoQuery.getnUsuario());
                eventoUi.setLike(calendarioEventoQuery.isLike());
                eventoUi.setNombreCalendario(calendarioEventoQuery.getCalendarioNombre());
                eventoUi.setFechaPublicacion(calendarioEventoQuery.getFechaPublicacion_());
                eventoUi.setFechaCreacion(calendarioEventoQuery.getFechaCreacion());
                switch(eventoUi.getTipoEventoUi().getId()){
                    case 526:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.EVENTO);
                        break;
                    case 528:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.ACTIVIDAD);
                        break;
                    case 530:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.CITA);
                        break;
                    case 529:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.TAREA);
                        break;
                    case 527:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.NOTICIA);
                        break;
                    case 620:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.AGENDA);
                        break;
                    default:
                        eventoUi.getTipoEventoUi().setTipo(TipoEventoUi.EventoIconoEnumUI.DEFAULT);
                        break;
                }
                eventoUi.setAdjuntoUiList(new ArrayList<>());
                eventoUi.setAdjuntoUiPreviewList(new ArrayList<>());
                eventoUi.setAdjuntoUiEncuestaList(new ArrayList<>());
                eventoUiList.add(eventoUi);


                EventoUi.AdjuntoUi adjuntoUi;
                /*for(int i = 0; i < 3; i++){
                    adjuntoUi = new EventoUi.AdjuntoUi();
                    if(i==0){
                        //adjuntoUi.setImagePreview(eventoUi.getFoto());
                        adjuntoUi.setImagePreview("https://img.youtube.com/vi/"+YouTubeUrlParser.getVideoId("https://www.youtube.com/watch?v=nPjpGusNrnk")+"/sddefault.jpg");
                        adjuntoUi.setDriveId("");
                        adjuntoUi.setTitulo("https://www.youtube.com/watch?v=nPjpGusNrnk");
                        adjuntoUi.setYotubeId(YouTubeUrlParser.getVideoId("https://www.youtube.com/watch?v=nPjpGusNrnk"));
                        adjuntoUi.setTipoArchivo(TipoArchivo.YOUTUBE);
                        adjuntoUi.setVideo(true);
                    }
                    if(i==1){
                        adjuntoUi.setImagePreview("https://lh3.googleusercontent.com/-ihLbfN53HIU/YMOt42lgXyI/AAAAAAAAE_g/F0XtZ2UCYFw-e-tC9HUYcUv-KlmBSD_uACJEEGAsYHg/s0/2021-06-11.png");
                        adjuntoUi.setVideo(false);
                    }
                    if(i==2){
                        adjuntoUi.setImagePreview("https://drive.google.com/uc?id=1wTmsWguiVuOw3A-MrwqxEifAJtcIeXru");
                        adjuntoUi.setVideo(false);
                    }
                    if(i==3){
                        adjuntoUi.setImagePreview("https://drive.google.com/thumbnail?id=1SR_uja8NFKoLmNpKcFlFfgzBtBsvlzg3");
                        adjuntoUi.setDriveId("1SR_uja8NFKoLmNpKcFlFfgzBtBsvlzg3");
                        adjuntoUi.setTitulo("Mi video.mp4");
                        adjuntoUi.setTipoArchivo(TipoArchivo.VIDEO);
                        adjuntoUi.setVideo(true);
                    }
                    eventoUi.getAdjuntoUiPreviewList().add(adjuntoUi);
                }

                for(int i = 0; i < 5; i++){
                    adjuntoUi = new EventoUi.AdjuntoUi();
                    if(i==0){
                        adjuntoUi.setImagePreview(eventoUi.getFoto());
                        adjuntoUi.setTitulo("Mi Imagen.docx");
                        adjuntoUi.setTipoArchivo(TipoArchivo.ENCUESTA);
                    }
                    if(i==1){
                        adjuntoUi.setImagePreview("https://lh3.googleusercontent.com/-ihLbfN53HIU/YMOt42lgXyI/AAAAAAAAE_g/F0XtZ2UCYFw-e-tC9HUYcUv-KlmBSD_uACJEEGAsYHg/s0/2021-06-11.png");
                        adjuntoUi.setVideo(false);
                        adjuntoUi.setTitulo("https://lh3.googleusercontent.com/-ihLbfN53HIU/YMOt42lgXyI/AAAAAAAAE_g/F0XtZ2UCYFw-e-tC9HUYcUv-KlmBSD_uACJEEGAsYHg/s0/2021-06-11.png");
                        adjuntoUi.setTipoArchivo(TipoArchivo.ENCUESTA);
                    }
                    if(i==2){
                        adjuntoUi.setImagePreview("https://drive.google.com/uc?id=1wTmsWguiVuOw3A-MrwqxEifAJtcIeXru");
                        adjuntoUi.setVideo(false);
                        adjuntoUi.setTitulo("https://drive.google.com/uc?id=1wTmsWguiVuOw3A-MrwqxEifAJtcIeXru");
                        adjuntoUi.setTipoArchivo(TipoArchivo.ENCUESTA);
                    }
                    if(i==3){
                        adjuntoUi.setImagePreview("https://drive.google.com/thumbnail?id=1SR_uja8NFKoLmNpKcFlFfgzBtBsvlzg3");
                        adjuntoUi.setVideo(true);
                        adjuntoUi.setTitulo("https://drive.google.com/thumbnail?id=1SR_uja8NFKoLmNpKcFlFfgzBtBsvlzg3");
                        adjuntoUi.setTipoArchivo(TipoArchivo.ENCUESTA);
                    }
                    eventoUi.getAdjuntoUiEncuestaList().add(adjuntoUi);
                }*/

            }else {
                eventoUi = eventoUiList.get(position);
            }

            EventoUi.AdjuntoUi adjuntoUi = new EventoUi.AdjuntoUi();
            adjuntoUi.setEventoAjuntoId(calendarioEventoQuery.eventoAdjuntoId);
            if(!TextUtils.isEmpty(adjuntoUi.getEventoAjuntoId())){
                adjuntoUi.setDriveId(calendarioEventoQuery.adjuntoDriveId);
                adjuntoUi.setTitulo(calendarioEventoQuery.adjuntoTitulo);
                switch (calendarioEventoQuery.adjuntoTipoId){
                    case Archivo.TIPO_VIDEO:
                        adjuntoUi.setTipoArchivo(TipoArchivo.VIDEO);
                        eventoUi.getAdjuntoUiPreviewList().add(adjuntoUi);
                        adjuntoUi.setVideo(true);
                        break;
                    case Archivo.TIPO_VINCULO:
                        adjuntoUi.setTipoArchivo(TipoArchivo.LINK);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_DOCUMENTO:
                        adjuntoUi.setTipoArchivo(TipoArchivo.DOCUMENTO);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_IMAGEN:
                        adjuntoUi.setTipoArchivo(TipoArchivo.IMAGEN);
                        eventoUi.getAdjuntoUiPreviewList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_AUDIO:
                        adjuntoUi.setTipoArchivo(TipoArchivo.AUDIO);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_HOJA_CALCULO:
                        adjuntoUi.setTipoArchivo(TipoArchivo.HOJACALCULO);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_DIAPOSITIVA:
                        adjuntoUi.setTipoArchivo(TipoArchivo.PRESENTACION);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_PDF:
                        adjuntoUi.setTipoArchivo(TipoArchivo.PDF);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                    case Archivo.TIPO_YOUTUBE:
                        adjuntoUi.setTipoArchivo(TipoArchivo.VIDEO);
                        eventoUi.getAdjuntoUiPreviewList().add(adjuntoUi);
                        adjuntoUi.setVideo(true);
                        break;
                    case Archivo.TIPO_ENCUESTA:
                        adjuntoUi.setTipoArchivo(TipoArchivo.ENCUESTA);
                        eventoUi.getAdjuntoUiEncuestaList().add(adjuntoUi);
                        break;
                    default:
                        adjuntoUi.setTipoArchivo(TipoArchivo.OTROS);
                        eventoUi.getAdjuntoUiList().add(adjuntoUi);
                        break;
                }

                if(adjuntoUi.getTipoArchivo()==TipoArchivo.VIDEO){
                    String idYoutube = YouTubeUrlParser.getVideoId(adjuntoUi.getTitulo());
                    if(TextUtils.isEmpty(idYoutube)){
                        adjuntoUi.setImagePreview("https://drive.google.com/thumbnail?id="+adjuntoUi.getDriveId());
                    }else {
                        adjuntoUi.setTipoArchivo(TipoArchivo.YOUTUBE);
                        adjuntoUi.setImagePreview(YouTubeThumbnail.getUrlFromVideoId(idYoutube,YouTubeThumbnail.Quality.DEFAULT));
                        adjuntoUi.setYotubeId(idYoutube);
                    }
                }else if(adjuntoUi.getTipoArchivo()==TipoArchivo.YOUTUBE){
                    String idYoutube = YouTubeUrlParser.getVideoId(adjuntoUi.getTitulo());
                    adjuntoUi.setTipoArchivo(TipoArchivo.YOUTUBE);
                    adjuntoUi.setImagePreview(YouTubeThumbnail.getUrlFromVideoId(idYoutube,YouTubeThumbnail.Quality.DEFAULT));
                    adjuntoUi.setYotubeId(idYoutube);
                }else if(adjuntoUi.getTipoArchivo() == TipoArchivo.IMAGEN){
                    adjuntoUi.setImagePreview("https://drive.google.com/uc?id="+adjuntoUi.getDriveId());
                }
            }

        }

        for(EventoUi eventoUi : eventoUiList){
            if (eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.NOTICIA ||
                    eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.EVENTO||(eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA && !TextUtils.isEmpty(eventoUi.getFoto()))){
                if(!TextUtils.isEmpty(eventoUi.getFoto())){
                    EventoUi.AdjuntoUi adjuntoUi = new EventoUi.AdjuntoUi();
                    adjuntoUi.setImagePreview(eventoUi.getFoto());
                    adjuntoUi.setTipoArchivo(TipoArchivo.IMAGEN);
                    adjuntoUi.setTitulo(eventoUi.getTitulo());
                    eventoUi.getAdjuntoUiPreviewList().add(adjuntoUi);
                }
            }

            if (eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.NOTICIA ||
                    eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.EVENTO||(eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA && !TextUtils.isEmpty(eventoUi.getFoto()))){

                if(TextUtils.isEmpty(eventoUi.getFoto()) && !eventoUi.getAdjuntoUiPreviewList().isEmpty()){
                    eventoUi.setFoto(eventoUi.getAdjuntoUiPreviewList().get(0).getImagePreview());
                }
            }
        }

        Collections.sort(eventoUiList, new Comparator<EventoUi>() {
            @Override
            public int compare(EventoUi o1, EventoUi o2) {

                Date fecha1_ = o1.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA? new Date(o1.getFechaCreacion()): o1.getFechaPublicacion();
                Calendar date1 = Calendar.getInstance();
                date1.setTimeInMillis(o1.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA? o1.getFechaCreacion() : o1.getFechaPublicacion().getTime());
                if(date1.get(Calendar.YEAR)<2000) fecha1_= new Date(o1.getFechaPublicacion().getTime());

                Date fecha2_ = new Date(o2.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA? o2.getFechaCreacion() : o2.getFechaPublicacion().getTime());
                Calendar date2 = Calendar.getInstance();
                date2.setTimeInMillis(o2.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA? o2.getFechaCreacion() : o2.getFechaPublicacion().getTime());
                if(date2.get(Calendar.YEAR)<2000) fecha2_ = new Date(o2.getFechaPublicacion().getTime());

                return fecha2_.compareTo(fecha1_);
            }
        });
        return eventoUiList;
    }

    @Override
    public List<TipoEventoUi> getTiposEvento() {
        List<TipoEventoUi> tipoEventoUiList = new ArrayList<>();
        List<EventoTipos> tipos = SQLite.select()
                .from(EventoTipos.class)
                .where(EventoTipos_Table.concepto.eq("TipoEvento"))
                .and(EventoTipos_Table.objeto.eq("T_CE_MOV_EVENTOS"))
                .queryList();

        for(EventoTipos item : tipos){
            TipoEventoUi tipoEventoUi = new TipoEventoUi();
            tipoEventoUi.setId(item.getTipoId());
            tipoEventoUi.setNombre(item.getNombre());
            switch(item.getTipoId()){
                case 526:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.EVENTO);
                    break;
                case 528:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.ACTIVIDAD);
                    break;
                case 530:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.CITA);
                    break;
                case 529:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.TAREA);
                    break;
                case 527:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.NOTICIA);
                    break;
                case 620:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.AGENDA);
                    break;
                default:
                    tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.DEFAULT);
                    break;
            }

            tipoEventoUiList.add(tipoEventoUi);
        }

        TipoEventoUi tipoEventoUi = new TipoEventoUi();
        tipoEventoUi.setId(0);
        tipoEventoUi.setNombre("Todos");
        tipoEventoUi.setTipo(TipoEventoUi.EventoIconoEnumUI.TODOS);
        tipoEventoUiList.add(tipoEventoUi);

        return tipoEventoUiList;
    }


    @Override
    public void getLikesSaveCountLike(EventoUi request, Callback<EventoUi> callback) {
        Log.d(TAG, "getLikesSaveCountLike " + request.getTitulo());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("padre_mentor")
                .child("icrmedu_padre")
                .child("like")
                .child(request.getId());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String cantidad = dataSnapshot.getValue(String.class);
                int resulCantidad = cantidad==null ? 0: Integer.valueOf(cantidad);
                if(resulCantidad < 0)resulCantidad = 0;
                request.setCantLike(resulCantidad);
                Log.d(TAG, "resulCantidad " +resulCantidad);
                callback.onLoad(true,request);
                Log.d(TAG, "onDataChange ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled ");
                Log.d(TAG, "Error trying to get classified ad for update " +
                        ""+databaseError);
                callback.onLoad(false,request );
            }
        });
    }

    @Override
    public void saveLikeRealTime(EventoUi eventosUi) {
        Log.d(TAG, "saveLike ");
        EventoUi eventosUiCopy = new EventoUi();
        eventosUiCopy.setId(eventosUi.getId());
        eventosUiCopy.setLike(eventosUi.isLike());
        getLikesSaveCountLike(eventosUiCopy, new Callback<EventoUi>() {
            @Override
            public void onLoad(boolean success, EventoUi item) {
                if(success){
                    int cantidad = item.getCantLike();
                    if(item.isLike()){
                        cantidad++;
                    }else {
                        cantidad--;
                    }

                    Log.d(TAG, "cantidad " + cantidad);
                    FirebaseDatabase.getInstance().getReference()
                            .child("padre_mentor")
                            .child("icrmedu_padre")
                            .child("like")
                            .child(item.getId())
                            .setValue(String.valueOf(cantidad));

                }
            }
        });

    }

    @Override
    public void saveLike(EventoUi eventosUi) {
        boolean status = false;
        try {

            SQLite.update(Evento2.class)
                    .set(Evento2_Table.like.eq(eventosUi.isLike()),
                            Evento2_Table.likeCount.eq(eventosUi.getCantLike()))
                    .where(Evento2_Table.eventoId.eq(eventosUi.getId()))
                    .execute();
            status = true;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String getIconoPortalAlumno() {
        Webconfig webConfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_icono_padre"))
                .querySingle();

        return webConfig!=null?webConfig.getContent():"";
    }

    @Override
    public void updateLike(EventoUi eventoUi) {
        try {
            SQLite.update(Evento2.class)
                    .set(Evento2_Table.likeCount.eq(eventoUi.getCantLike()))
                    .where(Evento2_Table.eventoId.eq(eventoUi.getId()))
                    .execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void pdateFirebaseTipoNota(int programaEducativoId, SuccessCallback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("AV_TipoNota/pgrid_"+programaEducativoId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<TipoNotaC> tipoNotaCList = new ArrayList<>();
                        List<ValorTipoNotaC> valorTipoNotaCList = new ArrayList<>();
                        List<RelProgramaEducativoTipoNota> programaEducativoTipoNotaList = new ArrayList<>();
                        for (DataSnapshot tipoSnapshot : dataSnapshot.getChildren()){
                            TipoNotaC tipoNotaC = new TipoNotaC();
                            tipoNotaC.setKey(UtilsFirebase.convert(tipoSnapshot.child("TipoNotaId").getValue(), ""));
                            tipoNotaC.setTipoNotaId(tipoNotaC.getKey());
                            tipoNotaC.setNombre(UtilsFirebase.convert(tipoSnapshot.child("Nombre").getValue(), ""));
                            tipoNotaC.setTipoId(UtilsFirebase.convert(tipoSnapshot.child("TipoId").getValue(), 0));
                            tipoNotaC.setValorMaximo(UtilsFirebase.convert(tipoSnapshot.child("ValorMaximo").getValue(), 0));
                            tipoNotaC.setValorMinino(UtilsFirebase.convert(tipoSnapshot.child("ValorMinimo").getValue(), 0));
                            tipoNotaC.setIntervalo(UtilsFirebase.convert(tipoSnapshot.child("Intervalo").getValue(), false));
                            tipoNotaCList.add(tipoNotaC);
                            RelProgramaEducativoTipoNota relProgramaEducativoTipoNota = new RelProgramaEducativoTipoNota();
                            relProgramaEducativoTipoNota.setTipoNotaId(tipoNotaC.getTipoNotaId());
                            relProgramaEducativoTipoNota.setProgramaEducativoId(programaEducativoId);
                            relProgramaEducativoTipoNota.setEstado(true);
                            programaEducativoTipoNotaList.add(relProgramaEducativoTipoNota);
                            if(tipoSnapshot.child("Valores").exists()){
                                for (DataSnapshot valoresDataSnapshot: tipoSnapshot.child("Valores").getChildren()){
                                    ValorTipoNotaC valorTipoNotaC = new ValorTipoNotaC();
                                    valorTipoNotaC.setKey(UtilsFirebase.convert(valoresDataSnapshot.child("ValorTipoNotaId").getValue(), ""));
                                    valorTipoNotaC.setValorTipoNotaId(valorTipoNotaC.getKey());
                                    valorTipoNotaC.setAlias(UtilsFirebase.convert(valoresDataSnapshot.child("Alias").getValue(), ""));
                                    valorTipoNotaC.setIcono(UtilsFirebase.convert(valoresDataSnapshot.child("Icono").getValue(), ""));
                                    valorTipoNotaC.setTipoNotaId(UtilsFirebase.convert(valoresDataSnapshot.child("TipoNotaId").getValue(), ""));
                                    valorTipoNotaC.setTitulo(UtilsFirebase.convert(valoresDataSnapshot.child("Titulo").getValue(), ""));
                                    valorTipoNotaC.setValorNumerico(UtilsFirebase.convert(valoresDataSnapshot.child("ValorNumerico").getValue(), 0.0D));

                                    valorTipoNotaC.setIncluidoLInferior(UtilsFirebase.convert(valoresDataSnapshot.child("IncluidoLInferior").getValue(), false));
                                    valorTipoNotaC.setIncluidoLSuperior(UtilsFirebase.convert(valoresDataSnapshot.child("IncluidoLSuperior").getValue(), false));
                                    valorTipoNotaC.setLimiteInferior(UtilsFirebase.convert(valoresDataSnapshot.child("LimiteInferior").getValue(), 0.0D));
                                    valorTipoNotaC.setLimiteSuperior(UtilsFirebase.convert(valoresDataSnapshot.child("LimiteSuperior").getValue(), 0.0D));
                                    valorTipoNotaCList.add(valorTipoNotaC);
                                }

                            }
                        }
                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {
                                TransaccionUtils.deleteTable(RelProgramaEducativoTipoNota.class);
                                TransaccionUtils.fastStoreListInsert(RelProgramaEducativoTipoNota.class, programaEducativoTipoNotaList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(TipoNotaC.class, tipoNotaCList, databaseWrapper, false);
                                TransaccionUtils.fastStoreListInsert(ValorTipoNotaC.class, valorTipoNotaCList, databaseWrapper, false);
                            }
                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(@NonNull Transaction transaction) {
                                callback.onLoad(true);
                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                                error.printStackTrace();
                                callback.onLoad(false);
                            }
                        }).build();

                        transaction.execute();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onLoad(false);
                    }
                });



    }


}
