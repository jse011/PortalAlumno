package com.consultoraestrategia.ss_portalalumno.login2.data.repositorio;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.ActividadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.AdminService;
import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.AnioAcademicoAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Aula;
import com.consultoraestrategia.ss_portalalumno.entities.BEListaPadre;
import com.consultoraestrategia.ss_portalalumno.entities.Calendario;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioListaUsuario;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle;
import com.consultoraestrategia.ss_portalalumno.entities.CampoTematico;
import com.consultoraestrategia.ss_portalalumno.entities.CargaAcademica;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursoDocente;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursoDocenteDet;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos;
import com.consultoraestrategia.ss_portalalumno.entities.Competencia;
import com.consultoraestrategia.ss_portalalumno.entities.Contrato;
import com.consultoraestrategia.ss_portalalumno.entities.Cursos;
import com.consultoraestrategia.ss_portalalumno.entities.CursosDetHorario;
import com.consultoraestrategia.ss_portalalumno.entities.DesempenioIcd;
import com.consultoraestrategia.ss_portalalumno.entities.DetalleContratoAcad;
import com.consultoraestrategia.ss_portalalumno.entities.DetalleHorario;
import com.consultoraestrategia.ss_portalalumno.entities.Dia;
import com.consultoraestrategia.ss_portalalumno.entities.Directivos;
import com.consultoraestrategia.ss_portalalumno.entities.Empleado;
import com.consultoraestrategia.ss_portalalumno.entities.Entidad;
import com.consultoraestrategia.ss_portalalumno.entities.Entidad_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Evento;
import com.consultoraestrategia.ss_portalalumno.entities.Georeferencia;
import com.consultoraestrategia.ss_portalalumno.entities.Georeferencia_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.Horario;
import com.consultoraestrategia.ss_portalalumno.entities.HorarioDia;
import com.consultoraestrategia.ss_portalalumno.entities.HorarioPrograma;
import com.consultoraestrategia.ss_portalalumno.entities.Icds;
import com.consultoraestrategia.ss_portalalumno.entities.NivelAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.ParametrosDisenio;
import com.consultoraestrategia.ss_portalalumno.entities.Periodo;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.PersonaGeoreferencia;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos;
import com.consultoraestrategia.ss_portalalumno.entities.PlanEstudios;
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC;
import com.consultoraestrategia.ss_portalalumno.entities.Relaciones;
import com.consultoraestrategia.ss_portalalumno.entities.Rol;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionEventoCompetenciaDesempenioIcd;
import com.consultoraestrategia.ss_portalalumno.entities.SesionEventoDesempenioIcdCampotematico;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_COMPETENCIA_SESION_EVENTO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Ubicaciones;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UsuarioAcceso;
import com.consultoraestrategia.ss_portalalumno.entities.UsuarioRolGeoreferencia;
import com.consultoraestrategia.ss_portalalumno.entities.UsuarioRolGeoreferencia_Table;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.login2.entities.DatosProgressUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioExternoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.OkHttpClient;
import retrofit2.Call;

public class LoginDataRepositoryImpl implements LoginDataRepository {
    private String TAG = LoginDataRepositoryImpl.class.getSimpleName();
    private String urlServidor;
    private ProgressListener progressDownloadingListener;
    private ProgressListener progressUploadingListener;
    public LoginDataRepositoryImpl() {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        urlServidor = TextUtils.isEmpty(apiRetrofit.getUrl())?"":apiRetrofit.getUrl().trim() + "/";
        Log.d(TAG,"urlServidor: " + urlServidor);
        ProgressManager.getInstance().addResponseListener(urlServidor, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                Log.d(TAG,"onProgress");
                if(progressDownloadingListener!=null)progressDownloadingListener.onProgress(progressInfo);
            }

            @Override
            public void onError(long id, Exception e) {
                if(progressDownloadingListener!=null)progressDownloadingListener.onError(id, e);
            }
        });
        ProgressManager.getInstance().addRequestListener(urlServidor, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                Log.d(TAG,"onProgress");
                if(progressUploadingListener!=null)progressUploadingListener.onProgress(progressInfo);
            }

            @Override
            public void onError(long id, Exception e) {
                if(progressUploadingListener!=null)progressUploadingListener.onError(id, e);
            }
        });
    }

    @Override
    public RetrofitCancel getUsuarioExterno(String urlAdminServicio, String usuario, String password, Callback<UsuarioExternoUi> callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();

        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<AdminService> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.f_BuscarUsuarioCent(1, usuario, password, "", "", urlAdminServicio));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<AdminService>() {
            @Override
            public void onResponse(AdminService response) {
                if(response == null){
                    callback.onResponse(false, null);
                    Log.d(TAG,"response usuario null");
                }else {
                    Log.d(TAG,"response usuario true");
                    UsuarioExternoUi usuarioLocalUi = new UsuarioExternoUi();
                    usuarioLocalUi.setEntidadIdLocal(response.getEntidadId());
                    usuarioLocalUi.setUrlServiceLocal(response.getUrlServiceMovil());
                    //usuarioLocalUi.setUrlServiceLocal("http://demo.consultoraestrategia.com/CRMMovil/PortalAcadMovil.ashx");
                    usuarioLocalUi.setUsuarioExternoId(response.getUsuarioId());
                    usuarioLocalUi.setUsuarioIdLocal(response.getUsuarioExternoId());
                    callback.onResponse(true, usuarioLocalUi);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResponse(false,null);
                t.printStackTrace();
                Log.d(TAG,"onFailure ");
            }
        });
        return retrofitCancel;
    }

    @Override
    public void saveUrlServidorLocal(String url) {
        GlobalSettings settings = GlobalSettings.getCurrentSettings();
        if (settings == null) {
            settings = new GlobalSettings();
        }
        settings.setUrlServer(url);
        settings.save();

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.updateServerUrl();
        OkHttpClient.Builder builder = ProgressManager.getInstance().with(new OkHttpClient.Builder());
        builder.connectTimeout(10, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(15, TimeUnit.SECONDS) // write timeout
                .readTimeout(15, TimeUnit.SECONDS);
        apiRetrofit.setOkHttpClient(builder.build());

        urlServidor = TextUtils.isEmpty(apiRetrofit.getUrl())?"":apiRetrofit.getUrl().trim() + "/";

        ProgressManager.getInstance().addResponseListener(urlServidor, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                Log.d(TAG,"onProgress");
                if(progressDownloadingListener!=null)progressDownloadingListener.onProgress(progressInfo);
            }

            @Override
            public void onError(long id, Exception e) {
                if(progressDownloadingListener!=null)progressDownloadingListener.onError(id, e);
            }
        });
        ProgressManager.getInstance().addRequestListener(urlServidor, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                Log.d(TAG,"onProgress");
                if(progressUploadingListener!=null)progressUploadingListener.onProgress(progressInfo);
            }

            @Override
            public void onError(long id, Exception e) {
                if(progressUploadingListener!=null)progressUploadingListener.onError(id, e);
            }
        });
    }

    @Override
    public RetrofitCancel getUsuarioLocalPorCorreo(String urlAdminServicio, String usuario, String password, String correo, Callback<UsuarioExternoUi> callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<AdminService> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.f_BuscarUsuarioCent(2, usuario, password, correo, "", urlAdminServicio));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<AdminService>() {
            @Override
            public void onResponse(AdminService response) {
                if(response == null){
                    callback.onResponse(false, null);
                    Log.d(TAG,"response usuario null");
                }else {
                    Log.d(TAG,"response usuario true");
                    UsuarioExternoUi usuarioLocalUi = new UsuarioExternoUi();
                    usuarioLocalUi.setEntidadIdLocal(response.getEntidadId());
                    usuarioLocalUi.setUrlServiceLocal(response.getUrlServiceMovil());
                    usuarioLocalUi.setUsuarioExternoId(response.getUsuarioId());
                    usuarioLocalUi.setUsuarioIdLocal(response.getUsuarioExternoId());
                    callback.onResponse(true, usuarioLocalUi);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResponse(false,null);
                t.printStackTrace();
                Log.d(TAG,"onFailure ");
            }
        });
        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getUsuarioLocalPorDni(String urlAdminServicio, String usuario, String password, String correo, String dni, Callback<UsuarioExternoUi> callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<AdminService> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.f_BuscarUsuarioCent(3, usuario, password, correo, dni, urlAdminServicio));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<AdminService>() {
            @Override
            public void onResponse(AdminService response) {
                if(response == null){
                    callback.onResponse(false, null);
                    Log.d(TAG,"response usuario null");
                }else {
                    Log.d(TAG,"response usuario true");
                    UsuarioExternoUi usuarioLocalUi = new UsuarioExternoUi();
                    usuarioLocalUi.setEntidadIdLocal(response.getEntidadId());
                    usuarioLocalUi.setUrlServiceLocal(response.getUrlServiceMovil());
                    usuarioLocalUi.setUsuarioExternoId(response.getUsuarioId());
                    usuarioLocalUi.setUsuarioIdLocal(response.getUsuarioExternoId());
                    callback.onResponse(true, usuarioLocalUi);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResponse(false,null);
                t.printStackTrace();
                Log.d(TAG,"onFailure ");
            }
        });
        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getDatosInicioSesion(int usuarioId, CallBackSucces<DatosProgressUi> callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();

        OkHttpClient.Builder builder = ProgressManager.getInstance().with(new OkHttpClient.Builder());
        builder.connectTimeout(10, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(30, TimeUnit.SECONDS) // write timeout
                .readTimeout(30, TimeUnit.SECONDS);

        apiRetrofit.setOkHttpClient(builder.build());


        Call<RestApiResponse<BEListaPadre>> responseCall = apiRetrofit.flst_getDatosPortalAlumno(usuarioId);

        RetrofitCancel<BEListaPadre> retrofitCancel = new RetrofitCancelImpl<>(responseCall);
        retrofitCancel.enqueue(new RetrofitCancel.Callback<BEListaPadre>() {
            @Override
            public void onResponse(final BEListaPadre response) {
                if(response == null){
                    callback.onLoad(false, null);
                    Log.d(TAG,"response usuario null");
                }else {
                    Log.d(TAG,"response usuario true");

                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                        @Override
                        public void execute(DatabaseWrapper databaseWrapper) {


                            TransaccionUtils.fastStoreListInsert(Directivos.class, response.getDirectivos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(ParametrosDisenio.class, response.getObtener_parametros_disenio(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(AnioAcademicoAlumno.class, response.getAnioAcademicosAlumno(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(AnioAcademico.class, response.getAnioAcademicos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Usuario.class, response.getUsuariosrelacionados(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CalendarioListaUsuario.class, response.getCalendarioListaUsuario(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Calendario.class, response.getCalendario(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Evento.class, response.getEvento(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Tipos.class, response.getTipos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Ubicaciones.class, response.getUbicaciones(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Persona.class, response.getPersonas(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(SilaboEvento.class, response.getSilaboEvento(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CalendarioPeriodoDetalle.class, response.getObtenerCalendarioPeriodoDetalle(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(RecursoReferenciaC.class, response.getRecursoReferencia(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(ActividadAprendizaje.class, response.getActividad(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(T_GC_REL_COMPETENCIA_SESION_EVENTO.class, response.getCompetenciaSesion(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(SesionEventoCompetenciaDesempenioIcd.class, response.getRel_sesion_evento_competencia_desempenioicd(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(SesionEventoDesempenioIcdCampotematico.class, response.getSesion_desempenio_icd_campotematico(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO.class, response.getRel_unidad_evento_competencia_desempenio_icd_campo_tematico(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD.class, response.getRel_unidad_evento_competencia_desempenio_icd(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class, response.getRel_unidad_apren_evento_tipo(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(UnidadAprendizaje.class, response.getUnidadAprendizaje(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(SesionAprendizaje.class, response.getSesiones(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CampoTematico.class, response.getCampoTematico(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Icds.class, response.getIcds(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(DesempenioIcd.class, response.getRel_desempenio_icd(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Competencia.class, response.getCompetencias(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(RecursoArchivo.class, response.getRecursoArchivo(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Archivo.class, response.getArchivo(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(RecursoDidacticoEventoC.class, response.getRecursoDidactico(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(TareasRecursosC.class, response.getTareasRecursos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(TareasC.class, response.getTareas(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(HorarioDia.class, response.getHorarioDia(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(HorarioPrograma.class, response.getHorarioPrograma(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Horario.class, response.getHorario(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Dia.class, response.getDia(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(DetalleHorario.class, response.getDetalleHorario(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CursosDetHorario.class, response.getCursosDetHorario(), databaseWrapper, true);


                            TransaccionUtils.fastStoreListInsert(CargaCursos.class, response.getCargaCursos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Aula.class, response.getAulas(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CalendarioAcademico.class, response.getCalendarioAcademicos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CalendarioPeriodo.class, response.getCalendarioPeriodos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Contrato.class, response.getContratos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(DetalleContratoAcad.class, response.getDetalleContratoAcad(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Empleado.class, response.getEmpleados(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Cursos.class, response.getCursos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(PlanCursos.class, response.getPlanCursos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(PlanEstudios.class, response.getPlanEstudios(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(ProgramasEducativo.class, response.getProgramasEducativos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CargaAcademica.class, response.getCargasAcademicas(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Seccion.class, response.getSecciones(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Periodo.class, response.getPeriodos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(NivelAcademico.class, response.getNivelesAcademicos(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CargaCursoDocente.class, response.getCargaCursoDocente(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Georeferencia.class, response.getGeoreferencias(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(CargaCursoDocenteDet.class, response.getCargaCursoDocenteDet(), databaseWrapper, true);
                            TransaccionUtils.fastStoreListInsert(Relaciones.class, response.getRelaciones(), databaseWrapper, true);



                            SessionUser sessionUser = SessionUser.getCurrentUser();
                            sessionUser.setDataImported(true);
                            sessionUser.save();
                        }
                    }).success(new Transaction.Success() {
                        @Override
                        public void onSuccess(@NonNull Transaction transaction) {

                            callback.onLoad(true, new DatosProgressUi());
                        }
                    }).error(new Transaction.Error() {
                        @Override
                        public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                            error.printStackTrace();

                            callback.onLoad(false, null);
                        }
                    }).build();

                    transaction.execute();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                ProgressManager.getInstance().notifyOnErorr(urlServidor, new Exception());
                callback.onLoad(false, null);
                t.printStackTrace();
                Log.d(TAG,"onFailure ");
            }
        });

        setupListener( callback);

        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getPersonaLocal(String usuario, Callback<PersonaUi> callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15,TimeUnit.SECONDS);
        RetrofitCancel<Persona> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.flst_ObtenerPersona(usuario));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<Persona>() {
            @Override
            public void onResponse(Persona persona) {
                if(persona==null){
                    callback.onResponse(false, null);
                }else {
                    PersonaUi personaUi = new PersonaUi();
                    personaUi.setId(persona.getPersonaId());
                    personaUi.setNombres(persona.getNombres());
                    personaUi.setApellidos(persona.getApellidos());
                    personaUi.setImagenUrl(persona.getUrlPicture());

                    try {
                        persona.save();
                        callback.onResponse(true, personaUi);
                    }catch (Exception e){
                        callback.onResponse(false, null);
                    }

                    Log.d(TAG,"isSuccessful true");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResponse(false,null);
                Log.d(TAG,"onFailure");
            }
        });
        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getUsuarioLocal(int usuarioId, Callback<UsuarioUi> callback) {
        Log.d(TAG,"isSuccessful usuarioId: " + usuarioId);
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15,TimeUnit.SECONDS);
        RetrofitCancel<Usuario> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.fobj_ObtenerUsuario(usuarioId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<Usuario>() {
            @Override
            public void onResponse(final Usuario usuario) {
                if(usuario!=null){
                    if(usuario.getUsuarioId() < 1){
                        callback.onResponse(false, null);
                        Log.d(TAG,"isSuccessful true usuarioId < 1");
                    }else {

                        final UsuarioUi usuarioUi = new UsuarioUi();
                        usuarioUi.setUsuarioId(usuario.getUsuarioId());
                        usuarioUi.setUserName(usuario.getUsuario());
                        usuarioUi.setPasswordEncrypted(usuario.getPassword());
                        usuarioUi.setPersonaId(usuario.getPersonaId());
                        usuarioUi.setPersonaImagenUrl(usuario.getFotoPersona());
                        usuarioUi.setInstitucionUrl(usuario.getFotoEntidad());
                        List<Rol> rolList = usuario.getRoles();
                        List<Integer> rolIdList = new ArrayList<>();
                        if(rolList!=null){
                            for (Rol rol : rolList){
                                Log.d(TAG,"rol "+ rol.getRolId());
                                rolIdList.add(rol.getRolId());
                            }
                        }
                        usuarioUi.setRolIdList(rolIdList);

                        DatabaseDefinition appDatabase = FlowManager.getDatabase(AppDatabase.class);
                        appDatabase.beginTransactionAsync(new ITransaction() {
                            @Override
                            public void execute(DatabaseWrapper databaseWrapper) {

                                SQLite.delete()
                                        .from(Usuario.class)
                                        .execute(databaseWrapper);

                                SQLite.delete()
                                        .from(Entidad.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(Georeferencia.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(Rol.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(UsuarioRolGeoreferencia.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(PersonaGeoreferencia.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(UsuarioAcceso.class)
                                        .execute(databaseWrapper);
                                SQLite.delete()
                                        .from(SessionUser.class)
                                        .execute(databaseWrapper);

                                usuario.save();

                                TransaccionUtils.fastStoreListSave(Entidad.class, usuario.getEntidades(), databaseWrapper, true);
                                TransaccionUtils.fastStoreListSave(Georeferencia.class, usuario.getGeoreferencias(),databaseWrapper, true);
                                TransaccionUtils.fastStoreListSave(Rol.class, usuario.getRoles(),databaseWrapper, true);
                                TransaccionUtils.fastStoreListSave(UsuarioRolGeoreferencia.class, usuario.getUsuarioRolGeoreferencias(),databaseWrapper,true);
                                TransaccionUtils.fastStoreListSave(PersonaGeoreferencia.class, usuario.getPersonaGeoreferencias(),databaseWrapper,true);
                                TransaccionUtils.fastStoreListSave(UsuarioAcceso.class, usuario.getAccesos(),databaseWrapper,false);


                                SQLite.update(SessionUser.class)
                                        .set(SessionUser_Table.state.eq(SessionUser.STATE_INACTIVE))
                                        .where(SessionUser_Table.userId.notEq(usuarioId))
                                        .execute();

                                SessionUser sessionUser = new SessionUser();
                                sessionUser.setUserId(usuario.getUsuarioId());
                                sessionUser.setPersonaId(usuario.getPersonaId());
                                sessionUser.setUsername(usuario.getUsuario());
                                sessionUser.setPasswordEncrypted(usuario.getPassword());//FIX ME!!!
                                sessionUser.setName("");//FIX ME!!!
                                sessionUser.setUrlPicture("");//FIX ME!!!F
                                sessionUser.setTimestampLogin(new Date().getTime());
                                sessionUser.setTimestampLastSeen(new Date().getTime());
                                sessionUser.setState(SessionUser.STATE_ACTIVE);
                                sessionUser.save();
                            }

                        }).success(new Transaction.Success() {
                            @Override
                            public void onSuccess(@NonNull Transaction transaction) {
                                callback.onResponse(true, usuarioUi);
                                Log.d(TAG,"isSuccessful true");
                            }
                        }).error(new Transaction.Error() {
                            @Override
                            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                                callback.onResponse(false, usuarioUi);
                                Log.d(TAG,"isSuccessful error save");
                            }
                        }).build().execute();


                    }
                }else {
                    Log.d(TAG,"isSuccessful false");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onResponse(false,null);
                Log.d(TAG,"onFailure");
            }
        });

        return retrofitCancel;
    }

    @Override
    public String getNombreColegio(int usuarioId) {
        UsuarioRolGeoreferencia rolGeoreferencia = SQLite.select()
                .from(UsuarioRolGeoreferencia.class)
                .innerJoin(Entidad.class)
                .on(UsuarioRolGeoreferencia_Table.entidadId.withTable()
                        .eq(Entidad_Table.entidadId.withTable()))
                .where(UsuarioRolGeoreferencia_Table.usuarioId
                        .is(usuarioId))
                .and(Entidad_Table.estadoId.eq(Entidad.ESTADO_AUTORIZADO))
                .querySingle();

        Georeferencia georeferencia = SQLite.select()
                .from(Georeferencia.class)
                .where(Georeferencia_Table.georeferenciaId.eq(rolGeoreferencia!=null? rolGeoreferencia.getGeoReferenciaId(): 0))
                .querySingle();

        return georeferencia!=null?georeferencia.getNombre():"";
    }

    @Override
    public String getNombreAnioActual(int anioAcademicoId) {
        return null;
    }

    @Override
    public boolean savePlanSinck(int hora, int minute) {
        boolean success=false;
        try {
            SessionUser sesion = SessionUser.getCurrentUser();
            sesion.setHourSync(hora);
            sesion.setMinuteSync(minute);
            success = sesion.save();
        }catch (Exception e){
            e.getStackTrace();
        }
        return success;
    }


    private void setupListener(final CallBackSucces callBackSucces){
        progressDownloadingListener = new ProgressListener() {
            ProgressInfo mLastDownloadingInfo;
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的下载进度用来展示,顺便展示下 id 的用法

                if (mLastDownloadingInfo == null) {
                    mLastDownloadingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                    mLastDownloadingInfo = progressInfo;
                }
                // 如果getCurrentbytes 等于 -1 说明二进制已经读取完,可能是成功下载完所有数据,也可能是遭遇了错误
                int progress = (int) ((100 * mLastDownloadingInfo.getCurrentbytes()) / mLastDownloadingInfo.getContentLength());
                Log.d(TAG, mLastDownloadingInfo.getId() + "--download--" + progress + " % " + mLastDownloadingInfo.getCurrentbytes() + "  " + mLastDownloadingInfo.getContentLength());
                callBackSucces.onResponseProgress(progress);

            }

            @Override
            public void onError(long id, Exception e) {

            }
        };

        progressUploadingListener = new ProgressListener() {
            ProgressInfo mLastUploadingingInfo;
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
                // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
                // 这里我就取最新的上传进度用来展示,顺便展示下 id 的用法

                if (mLastUploadingingInfo == null) {
                    mLastUploadingingInfo = progressInfo;
                }

                //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
                if (progressInfo.getId() < mLastUploadingingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastUploadingingInfo.getId()) {
                    mLastUploadingingInfo = progressInfo;
                }

                int progress = (int) ((100 * mLastUploadingingInfo.getCurrentbytes()) / mLastUploadingingInfo.getContentLength());
                Log.d(TAG, mLastUploadingingInfo.getId() + "--upload--" + progress + " %  " + mLastUploadingingInfo.getCurrentbytes() + "  " + mLastUploadingingInfo.getContentLength());
                callBackSucces.onRequestProgress(progress);
            }

            @Override
            public void onError(long id, Exception e) {

            }
        };
    }
}