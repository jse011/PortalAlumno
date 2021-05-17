package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Aula;
import com.consultoraestrategia.ss_portalalumno.entities.Aula_Table;
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
import com.consultoraestrategia.ss_portalalumno.entities.queryCustom.CursoCustom;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDatosAnioAcademico;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
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
