package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.local;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaAcademica;
import com.consultoraestrategia.ss_portalalumno.entities.CargaAcademica_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos;
import com.consultoraestrategia.ss_portalalumno.entities.CargaCursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Cursos;
import com.consultoraestrategia.ss_portalalumno.entities.Cursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Periodo;
import com.consultoraestrategia.ss_portalalumno.entities.Periodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion;
import com.consultoraestrategia.ss_portalalumno.entities.Seccion_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumno;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumnoArchivos;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumnoArchivos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareaAlumno_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasRecursosC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks.GetTareasListCallback;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.DownloadCancelUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioTipoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.DriveUrlParser;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public class TareasLocalDataSource implements TareasMvpDataSource {

    private static final String TAG = TareasLocalDataSource.class.getSimpleName();

    public TareasLocalDataSource() {
    }


    @Override
    public void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis) {
        for (RepositorioFileUi repositorioFileUi: repositorioFileUis){
            SQLite.update(Archivo.class)
                    .set(Archivo_Table.localpath.eq(repositorioFileUi.getPath()))
                    .where(Archivo_Table.key.eq(repositorioFileUi.getArchivoId()))
                    .execute();
        }
    }

    @Override
    public List<RecursosUI> getRecursosTarea(String tareaId) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlArchivo"))
                .querySingle();

        String urlArchivo  = webconfig!=null?webconfig.getContent():"";
        TareasUI tareasUI = new TareasUI();
        List<TareasRecursosC> tareasRecursosList = SQLite.select()
                .from(TareasRecursosC.class)
                .where(TareasRecursosC_Table.tareaId.is(tareaId))
                .queryList();

        List<RecursosUI> recursosUIList = new ArrayList<>();
        for (TareasRecursosC tareasRecursos : tareasRecursosList) {

            RecursoDidacticoEventoC recursoDidacticoEvento = SQLite.select()
                    .from(RecursoDidacticoEventoC.class)
                    .where(RecursoDidacticoEventoC_Table.key.eq(tareasRecursos.getRecursoDidacticoId()))
                    .and(RecursoDidacticoEventoC_Table.estado.eq(1))
                    .querySingle();

            if (recursoDidacticoEvento != null) {
                RecursosUI recursosUI = new RecursosUI();
                recursosUI.setTarea(tareasUI);
                recursosUI.setRecursoId(tareasRecursos.getRecursoDidacticoId());
                recursosUI.setNombreRecurso(recursoDidacticoEvento.getTitulo());
                // recursosUI.setNombre(recursoDidacticoEvento.getTitulo());
                recursosUI.setDescripcion(recursoDidacticoEvento.getDescripcion());
                recursosUI.setFechaCreacionRecuros(recursoDidacticoEvento.getFechaCreacion());
                boolean isYoutube = false;
                switch (recursoDidacticoEvento.getTipoId()) {
                    case 379://video
                        isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursoDidacticoEvento.getUrl()));
                        if (!isYoutube) {
                            isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursosUI.getDescripcion()));
                            if (isYoutube) {
                                recursosUI.setUrl(recursosUI.getDescripcion());
                            }
                        }
                        if (isYoutube) {
                            recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                            recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                            recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                            recursosUI.setTipoFileU(RepositorioTipoFileU.YOUTUBE);
                            recursosUI.setPath(recursoDidacticoEvento.getLocalUrl());
                        } else {
                            recursosUI.setTipoFileU(RepositorioTipoFileU.VIDEO);
                        }
                        break;
                    case 380://vinculo
                        recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                        recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                        recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                        recursosUI.setTipoFileU(RepositorioTipoFileU.VINCULO);
                        break;
                    case 397://Documento de texto
                        recursosUI.setTipoFileU(RepositorioTipoFileU.DOCUMENTO);
                        break;
                    case 398://Imagen
                        recursosUI.setTipoFileU(RepositorioTipoFileU.IMAGEN);
                        break;
                    case 399://Audio
                        recursosUI.setTipoFileU(RepositorioTipoFileU.AUDIO);
                        break;
                    case 400://Hoja de Cálculo
                        recursosUI.setTipoFileU(RepositorioTipoFileU.HOJA_CALCULO);
                        break;
                    case 401://Diapositiva
                        recursosUI.setTipoFileU(RepositorioTipoFileU.DIAPOSITIVA);
                        break;
                    case 402://PDF
                        recursosUI.setTipoFileU(RepositorioTipoFileU.PDF);
                        break;
                    case 403://Materiales
                        recursosUI.setTipoFileU(RepositorioTipoFileU.MATERIALES);
                        recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                        break;
                }
                // Log.d(TAG,"archivo:( " + recursoDidacticoEvento.getUrl());
                if (recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_AUDIO ||
                        recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_DIAPOSITIVA ||
                        recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_DOCUMENTO ||
                        recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_HOJA_CALCULO ||
                        recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_IMAGEN ||
                        recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_PDF ||
                        (recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO
                                && !isYoutube)
                ) {

                    Archivo archivo = SQLite.select(UtilsDBFlow.f_allcolumnTable(Archivo_Table.ALL_COLUMN_PROPERTIES))
                            .from(Archivo.class)
                            .innerJoin(RecursoArchivo.class)
                            .on(Archivo_Table.key.withTable().eq(RecursoArchivo_Table.archivoId.withTable()))
                            .where(RecursoArchivo_Table.recursoDidacticoId.withTable().eq(recursoDidacticoEvento.getKey()))
                            .querySingle();

                    Log.d(TAG, "archivo:(");
                    if (archivo != null) {
                        Log.d(TAG, "great");
                        recursosUI.setArchivoId(archivo.getKey());
                        recursosUI.setNombreArchivo(archivo.getNombre());
                        recursosUI.setPath(archivo.getLocalpath());
                        //recursosUI.setUrl(urlArchivo+"/"+archivo.getPath());
                        recursosUI.setFechaAccionArchivo(archivo.getFechaAccion());
                        //recursosUI.setDescripcion(recursoDidacticoEvento.getDescripcion());
                        if (TextUtils.isEmpty(archivo.getLocalpath())) {
                            recursosUI.setEstadoFileU(RepositorioEstadoFileU.SIN_DESCARGAR);
                        } else {
                            recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                        }
                        recursosUI.setDriveId(archivo.getPath());
                        recursosUI.setPath(archivo.getLocalpath());
                    } else if (recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO) {
                        recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                        recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                        recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                        recursosUI.setTipoFileU(RepositorioTipoFileU.VINCULO);
                        recursosUI.setFechaAccionArchivo(recursoDidacticoEvento.getFechaCreacion());
                    }

                } else {
                    String url = recursoDidacticoEvento.getUrl();
                    if (TextUtils.isEmpty(url)) url = recursoDidacticoEvento.getDescripcion();
                    recursosUI.setUrl(url);
                    recursosUI.setFechaAccionArchivo(recursoDidacticoEvento.getFechaCreacion());
                }
                recursosUIList.add(recursosUI);

            }

        }

        return recursosUIList;
    }

    @Override
    public List<TareaArchivoUi> getArchivoTareaAlumno(String tareaId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;
        TareaAlumno tareaAlumno = SQLite.select()
                .from(TareaAlumno.class)
                .where(TareaAlumno_Table.tareaId.eq(tareaId))
                .and(TareaAlumno_Table.alumnoId.eq(alumnoId))
                .querySingle();

        boolean entregado = tareaAlumno != null && tareaAlumno.isEntregado();
        List<TareaAlumnoArchivos> tareaAlumnoArchivosList = SQLite.select()
                .from(TareaAlumnoArchivos.class)
                .where(TareaAlumnoArchivos_Table.alumnoId.eq(alumnoId))
                .and(TareaAlumnoArchivos_Table.tareaId.eq(tareaId))
                .queryList();
        List<TareaArchivoUi> tareaArchivoUiList = new ArrayList<>();
        for (TareaAlumnoArchivos tareaAlumnoArchivos : tareaAlumnoArchivosList){
            TareaArchivoUi tareaArchivoUi = new TareaArchivoUi();
            tareaArchivoUi.setId(tareaAlumnoArchivos.getTareaAlumnoArchivoId());
            tareaArchivoUi.setNombre(tareaAlumnoArchivos.getNombre());
            tareaArchivoUi.setPath(!TextUtils.isEmpty(tareaAlumnoArchivos.getPath())?tareaAlumnoArchivos.getPath():"");
            tareaArchivoUi.setEntregado(entregado);
            if(tareaAlumnoArchivos.isRepositorio()){
                tareaArchivoUi.setTipo(TareaArchivoUi.getType(tareaArchivoUi.getPath()));
                tareaArchivoUi.setDescripcion(tareaArchivoUi.getTipo().getNombre());
            }else {
                String idYoutube = YouTubeUrlParser.getVideoId(tareaArchivoUi.getPath());
                String idDrive = DriveUrlParser.getDocumentId(tareaArchivoUi.getPath());
                if(!TextUtils.isEmpty(idYoutube)){
                    tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.YOUTUBE);
                }else if(!TextUtils.isEmpty(idDrive)){
                    tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.DRIVE);
                }else {
                    tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.LINK);
                }

                tareaArchivoUi.setDescripcion(tareaArchivoUi.getPath());
            }

            tareaArchivoUiList.add(tareaArchivoUi);
        }
        return tareaArchivoUiList;
    }

    @Override
    public StorageCancel uploadStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, StorageCallback<TareaArchivoUi> callbackStorage) {
        return null;
    }

    @Override
    public void deleteStorageFB(String tareaId,  TareaArchivoUi tareaArchivoUi,CallbackSimple callbackSimple) {

    }

    @Override
    public void publicarTareaAlumno(String tareaId, CallbackSimple callbackSimple) {

    }

    @Override
    public TareasUI isEntregadoTareaAlumno(String tareaId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
        TareaAlumno tareaAlumno = SQLite.select()
                .from(TareaAlumno.class)
                .where(TareaAlumno_Table.tareaId.eq(tareaId))
                .and(TareaAlumno_Table.alumnoId.eq(personaId))
                .querySingle();
        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();
        long fechaEntrega = tareasC!=null?tareasC.getFechaEntrega():0;
        String horaEntrega = tareasC!=null?tareasC.getHoraEntrega():"";
        TareasUI tareasUI = new TareasUI();
        tareasUI.setEntregaAlumno(tareaAlumno != null && tareaAlumno.isEntregado());
        tareasUI.setFechaEntregaAlumno(tareaAlumno!=null?tareaAlumno.getFechaEntrega():0);
        tareasUI.setRetrasoEntrega(comparaFechasTareaEntregada(fechaEntrega, horaEntrega, tareasUI.getFechaEntregaAlumno()));
        return tareasUI;
    }

    @Override
    public void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, String tareaId, CallbackSimple callback) {

    }

    @Override
    public TareasUI getTarea(String tareaId) {
        TareasC tareasC = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.tareaId.eq(tareaId))
                .querySingle();
        TareasUI tareasUI = new TareasUI();
        if(tareasC!=null){
            tareasUI.setKeyTarea(tareasC.getKey());
            tareasUI.setTituloTarea(tareasC.getTitulo());
            tareasUI.setDescripcion(tareasC.getInstrucciones());
            tareasUI.setFechaCreacionTarea(tareasC.getFechaCreacion());
            tareasUI.setFechaLimite(tareasC.getFechaEntrega());
            tareasUI.setHoraEntrega(tareasC.getHoraEntrega());
            tareasUI.setIdUnidaddAprendizaje(tareasC.getUnidadAprendizajeId());
        }
        return tareasUI;
    }

    @Override
    public void uploadLinkFB(String tareaId, TareaArchivoUi tareaArchivoUi, CallbackSimple simple) {

    }


    private boolean comparaFechasTareaEntregada(long vstr_FechaEntrega, String vstr_HoraEntrega, long vlong_FechaEntregaAlumno) {

        if (!TextUtils.isEmpty(vstr_HoraEntrega)) {
            Date date = null;
            try {
                Date date1 = new Date(vstr_FechaEntrega);
                DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String output = outputFormatter.format(date1); // Output : 01/20/2012

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                date = format.parse(output + " " + vstr_HoraEntrega);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (date!=null) {
                Date alumno = new Date(vlong_FechaEntregaAlumno);
                boolean isbefore = date.before(alumno);//Validar si la tarea se entrego antes
                //return isbefore ? "Entregado con retraso" : "Tarea entregada";
                //                            true              false
                return isbefore;
            } else {
                //return "Tarea entregada";// si la fecha es erronea
                return true;
            }
        } else{
            Date m_alumno = null;
            try {
                Date date = new Date(vlong_FechaEntregaAlumno);
                DateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                String output = outputFormatter.format(date); // Output : 01/20/2012

                DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                m_alumno = format.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date m = new Date(vstr_FechaEntrega);

            if(m_alumno!=null){
                boolean isbefore = m.before(m_alumno);//Validar si la tarea se entrego antes
                //return isbefore ? "Entregado con retraso" : "Tarea entregada" ;
                //                          true                    false
                return isbefore;
            }else {
                //return "Tarea entregada";// si la fecha es erronea
                return true;
            }
        }

    }

    @Override
    public  List<HeaderTareasAprendizajeUI> getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId) {
        String nombreCurso = getNombreCurso(idCargaCurso);
        List<UnidadAprendizaje> unidadAprendizajeList = new ArrayList<>();
        List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList = new ArrayList<>();
        Log.d(TAG, "idCargaCurso: "+ idCargaCurso + " calendarioPeriodoId: " + calendarioPeriodoId);

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlExpresiones"))
                .querySingle();

        String pathValores = webconfig!=null?webconfig.getContent():"";

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
        if(tipoTarea==0){
            unidadAprendizajeList.addAll(SQLite
                    .select(UtilsDBFlow.f_allcolumnTable(UnidadAprendizaje_Table.ALL_COLUMN_PROPERTIES))
                    .from(UnidadAprendizaje.class)
                    .innerJoin(SilaboEvento.class)
                    .on(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(SilaboEvento_Table.silaboEventoId.withTable()))
                    .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                    .on(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()
                            .eq(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()))
                    .innerJoin(Tipos.class)
                    .on(Tipos_Table.tipoId.withTable()
                            .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable()))
                    .innerJoin(CalendarioPeriodo.class)
                    .on(CalendarioPeriodo_Table.tipoId.withTable()
                            .eq(Tipos_Table.tipoId.withTable()))
                    .where(SilaboEvento_Table.cargaCursoId.withTable().eq(idCargaCurso))
                    .and(CalendarioPeriodo_Table.calendarioPeriodoId.withTable().eq(calendarioPeriodoId))
                    .and(SilaboEvento_Table.anioAcademicoId.withTable().eq(anioAcademicoId))
                    .and(SilaboEvento_Table.planCursoId.withTable().eq(planCursoId))
                    .queryList());
            Log.d(TAG, "Size: "+ unidadAprendizajeList.size());

        }else {
            UnidadAprendizaje unidadAprendizaje = SQLite.select(UtilsDBFlow.f_allcolumnTable(UnidadAprendizaje_Table.ALL_COLUMN_PROPERTIES))
                    .from(UnidadAprendizaje.class)
                    .innerJoin(SesionAprendizaje.class)
                    .on(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()
                            .eq(SesionAprendizaje_Table.unidadAprendizajeId.withTable()))
                    .where(SesionAprendizaje_Table.sesionAprendizajeId.withTable().eq(sesionAprendizajeId))
                    .querySingle();

            if(unidadAprendizaje!=null)unidadAprendizajeList.add(unidadAprendizaje);
        }

        for (UnidadAprendizaje unidadAprendizaje : unidadAprendizajeList) {
            HeaderTareasAprendizajeUI headerTareasAprendizajeUI = new HeaderTareasAprendizajeUI();
            headerTareasAprendizajeUI.setIdUnidadAprendizaje(unidadAprendizaje.getUnidadAprendizajeId());
            headerTareasAprendizajeUI.setIdSesionAprendizaje(sesionAprendizajeId);
            headerTareasAprendizajeUI.setIdSilaboEvento(unidadAprendizaje.getSilaboEventoId());
            headerTareasAprendizajeUI.setTituloSesionAprendizaje(unidadAprendizaje.getTitulo());
            headerTareasAprendizajeUI.setNroUnidad(unidadAprendizaje.getNroUnidad());
            headerTareasAprendizajeUI.setDocente(true);
            List<TareasUI> tareasUIList = new ArrayList<>();
            List<TareasC> tareasList = new ArrayList<>();
            if(tipoTarea==0){
                tareasList.addAll(SQLite.select()
                        .from(TareasC.class)
                        .where(TareasC_Table.unidadAprendizajeId.is(unidadAprendizaje.getUnidadAprendizajeId()))
                        .and(TareasC_Table.estadoId.notIn(265))
                        //.and(TareasC_Table.sesionAprendizajeId.eq(0))
                        .orderBy(TareasC_Table.fechaCreacion.asc())
                        .queryList());
            }else {
                Log.d(TAG, "sesionAprendizajeId: "+ sesionAprendizajeId);
                tareasList.addAll(SQLite.select()
                        .from(TareasC.class)
                        .where(TareasC_Table.sesionAprendizajeId.is(sesionAprendizajeId))
                        .orderBy(TareasC_Table.fechaCreacion.asc())
                        .and(TareasC_Table.estadoId.notIn(265))
                        .queryList());
            }
            Log.d(TAG, "tipoTarea: "+ tipoTarea);
            Log.d(TAG, "tareasList: "+ tareasList.size());

            for (TareasC tareas : tareasList){

                TareasUI tareasUI = new TareasUI();
                if (tareas.getEstadoId() == 263) {
                    tareasUI.setEstado(TareasUI.Estado.Creado);
                }
                if (tareas.getEstadoId() == 264) {
                    tareasUI.setEstado(TareasUI.Estado.Publicado);
                }
                if (tareas.getEstadoId() == 265) {
                    tareasUI.setEstado(TareasUI.Estado.Eliminado);
                }

                tareasUI.setKeyTarea(tareas.getKey());
                tareasUI.setTituloTarea(tareas.getTitulo());
                tareasUI.setDescripcion(tareas.getInstrucciones());
                tareasUI.setFechaCreacionTarea(tareas.getFechaCreacion());
                tareasUI.setFechaLimite(tareas.getFechaEntrega());
                tareasUI.setHoraEntrega(tareas.getHoraEntrega());
                tareasUI.setIdUnidaddAprendizaje(tareas.getUnidadAprendizajeId());
                tareasUI.setNombreCurso(nombreCurso);

                TareaAlumno tareaAlumno = SQLite.select()
                        .from(TareaAlumno.class)
                        .where(TareaAlumno_Table.tareaId.eq(tareas.getKey()))
                        .and(TareaAlumno_Table.alumnoId.eq(personaId))
                        .querySingle();

                if(tareaAlumno!=null){
                    TipoNotaC tipoNotaC = SQLite.select()
                            .from(TipoNotaC.class)
                            .innerJoin(ValorTipoNotaC.class)
                            .on(TipoNotaC_Table.tipoNotaId.withTable()
                                    .eq(ValorTipoNotaC_Table.tipoNotaId.withTable()))
                            .where(ValorTipoNotaC_Table.valorTipoNotaId.withTable()
                                    .eq(tareaAlumno.getValorTipoNotaId()))
                            .querySingle();

                    ValorTipoNotaC valorTipoNotaC= SQLite.select()
                            .from(ValorTipoNotaC.class)
                            .where(ValorTipoNotaC_Table.valorTipoNotaId.eq(tareaAlumno.getValorTipoNotaId()))
                            .querySingle();

                    int tipoId = tipoNotaC!=null?tipoNotaC.getTipoId():0;
                    String nota = "";
                    if(tipoId==TipoNotaC.SELECTOR_ICONOS){
                        nota = valorTipoNotaC!=null? pathValores + valorTipoNotaC.getIcono():"";
                    }else{
                        nota = valorTipoNotaC!=null?valorTipoNotaC.getTitulo():"";
                    }

                    tareasUI.setTipoNotaId(tipoId);
                    tareasUI.setNota(nota);
                }

                List<TareasRecursosC> tareasRecursosList = SQLite.select()
                        .from(TareasRecursosC.class)
                        .where(TareasRecursosC_Table.tareaId.is(tareas.getKey()))
                        .queryList();

                List<RecursosUI> recursosUIList = new ArrayList<>();
                for (TareasRecursosC tareasRecursos : tareasRecursosList) {

                    RecursoDidacticoEventoC recursoDidacticoEvento = SQLite.select()
                            .from(RecursoDidacticoEventoC.class)
                            .where(RecursoDidacticoEventoC_Table.key.eq(tareasRecursos.getRecursoDidacticoId()))
                            .and(RecursoDidacticoEventoC_Table.estado.eq(1))
                            .querySingle();

                    if (recursoDidacticoEvento != null) {
                        RecursosUI recursosUI = new RecursosUI();
                        recursosUI.setTarea(tareasUI);
                        recursosUI.setRecursoId(tareasRecursos.getRecursoDidacticoId());
                        recursosUI.setNombreRecurso(recursoDidacticoEvento.getTitulo());
                        // recursosUI.setNombre(recursoDidacticoEvento.getTitulo());
                        recursosUI.setDescripcion(recursoDidacticoEvento.getDescripcion());
                        recursosUI.setFechaCreacionRecuros(recursoDidacticoEvento.getFechaCreacion());
                        boolean isYoutube = false;
                        switch (recursoDidacticoEvento.getTipoId()) {
                            case 379://video
                                isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursoDidacticoEvento.getUrl()));
                                if(!isYoutube){
                                    isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursosUI.getDescripcion()));
                                    if(isYoutube){
                                        recursosUI.setUrl(recursosUI.getDescripcion());
                                    }
                                }
                                if(isYoutube){
                                    recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                                    recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                                    recursosUI.setTipoFileU(RepositorioTipoFileU.YOUTUBE);
                                    recursosUI.setPath(recursoDidacticoEvento.getLocalUrl());
                                }else {
                                    recursosUI.setTipoFileU(RepositorioTipoFileU.VIDEO);
                                }
                                break;
                            case 380://vinculo
                                recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                                recursosUI.setUrl(TextUtils.isEmpty(recursoDidacticoEvento.getUrl())?
                                        recursoDidacticoEvento.getDescripcion():recursoDidacticoEvento.getUrl());
                                recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                                recursosUI.setTipoFileU(RepositorioTipoFileU.VINCULO);
                                break;
                            case 397://Documento de texto
                                recursosUI.setTipoFileU(RepositorioTipoFileU.DOCUMENTO);
                                break;
                            case 398://Imagen
                                recursosUI.setTipoFileU(RepositorioTipoFileU.IMAGEN);
                                break;
                            case 399://Audio
                                recursosUI.setTipoFileU(RepositorioTipoFileU.AUDIO);
                                break;
                            case 400://Hoja de Cálculo
                                recursosUI.setTipoFileU(RepositorioTipoFileU.HOJA_CALCULO);
                                break;
                            case 401://Diapositiva
                                recursosUI.setTipoFileU(RepositorioTipoFileU.DIAPOSITIVA);
                                break;
                            case 402://PDF
                                recursosUI.setTipoFileU(RepositorioTipoFileU.PDF);
                                break;
                            case 403://Materiales
                                recursosUI.setTipoFileU(RepositorioTipoFileU.MATERIALES);
                                recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                                break;
                        }
                        // Log.d(TAG,"archivo:( " + recursoDidacticoEvento.getUrl());
                        if (recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_AUDIO||
                                recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_DIAPOSITIVA||
                                recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_DOCUMENTO||
                                recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_HOJA_CALCULO||
                                recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_IMAGEN||
                                recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_PDF||
                                (recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO
                                        && !isYoutube)
                        ){

                            Archivo archivo = SQLite.select(UtilsDBFlow.f_allcolumnTable(Archivo_Table.ALL_COLUMN_PROPERTIES))
                                    .from(Archivo.class)
                                    .innerJoin(RecursoArchivo.class)
                                    .on(Archivo_Table.key.withTable().eq(RecursoArchivo_Table.archivoId.withTable()))
                                    .where(RecursoArchivo_Table.recursoDidacticoId.withTable().eq(recursoDidacticoEvento.getKey()))
                                    .querySingle();

                            Log.d(TAG,"archivo:(");
                            if(archivo!=null){
                                Log.d(TAG,"great");
                                recursosUI.setArchivoId(archivo.getKey());
                                recursosUI.setNombreArchivo(archivo.getNombre());
                                recursosUI.setPath(archivo.getLocalpath());

                                recursosUI.setFechaAccionArchivo(archivo.getFechaAccion());
                                //recursosUI.setDescripcion(recursoDidacticoEvento.getDescripcion());
                                if(TextUtils.isEmpty(archivo.getLocalpath())){
                                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.SIN_DESCARGAR);
                                }else {
                                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                                }
                                recursosUI.setDriveId(archivo.getPath());
                                recursosUI.setPath(archivo.getLocalpath());
                            }else if(recursoDidacticoEvento.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO){
                                recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                                recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                                recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                                recursosUI.setTipoFileU(RepositorioTipoFileU.VINCULO);
                                recursosUI.setFechaAccionArchivo(recursoDidacticoEvento.getFechaCreacion());
                            }

                        }else {
                            String url = recursoDidacticoEvento.getUrl();
                            if(TextUtils.isEmpty(url))url = recursoDidacticoEvento.getDescripcion();
                            recursosUI.setUrl(url);
                            recursosUI.setFechaAccionArchivo(recursoDidacticoEvento.getFechaCreacion());
                        }

                        recursosUIList.add(recursosUI);
                    }
                }
                tareasUI.setRecursosUIList(recursosUIList);
                tareasUIList.add(tareasUI);
            }
            Collections.reverse(tareasUIList);
            headerTareasAprendizajeUI.setTareasUIList(tareasUIList);
            headerTareasAprendizajeUIList.add(headerTareasAprendizajeUI);
        }
       return headerTareasAprendizajeUIList;
    }


    private String getNombreCurso(int idCargaCurso){

        CargaCursos cargaCurso = SQLite.select()
                .from(CargaCursos.class)
                .where(CargaCursos_Table.cargaCursoId.eq(idCargaCurso))
                .querySingle();

        int planCursoId = 0;
        int cargaAcademicaId = 0;
        if(cargaCurso!=null){
            planCursoId = cargaCurso.getPlanCursoId();
            cargaAcademicaId = cargaCurso.getCargaAcademicaId();
        }

        Cursos curso = SQLite.select(UtilsDBFlow.f_allcolumnTable(Cursos_Table.ALL_COLUMN_PROPERTIES))
                .from(Cursos.class)
                .innerJoin(PlanCursos.class)
                .on(Cursos_Table.cursoId.withTable().eq(PlanCursos_Table.cursoId.withTable()))
                .where(PlanCursos_Table.planCursoId.withTable().eq(planCursoId))
                .querySingle();


        Seccion seccion = SQLite.select()
                .from(Seccion.class)
                .innerJoin(CargaAcademica.class)
                .on(Seccion_Table.seccionId.withTable().eq(CargaAcademica_Table.seccionId.withTable()))
                .where(CargaAcademica_Table.cargaAcademicaId.eq(cargaAcademicaId))
                .querySingle();

        Periodo periodo = SQLite.select()
                .from(Periodo.class)
                .innerJoin(CargaAcademica.class)
                .on(Periodo_Table.periodoId.withTable().eq(CargaAcademica_Table.periodoId.withTable()))
                .where(CargaAcademica_Table.cargaAcademicaId.eq(cargaAcademicaId))
                .querySingle();

        String aliasPeriodo = "";
        String nombreSeccion = "";
        String cursoNombre = "";
        if(periodo != null)aliasPeriodo = periodo.getAlias();
        if(seccion != null)nombreSeccion = seccion.getNombre();
        if(curso != null)cursoNombre = curso.getNombre();
        return cursoNombre +" "+aliasPeriodo+" "+nombreSeccion;
    }

    @Override
    public void getParametroDisenio(int parametroDisenioId, CallbackTareas callbackTareas) {

    }

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> callback) {
        Archivo archivo = SQLite.select()
                .from(Archivo.class)
                .where(Archivo_Table.key.eq(archivoId))
                .querySingle();
        if(archivo!=null){
            archivo.setLocalpath(path);
            boolean success = archivo.save();
            callback.onLoad(success, success);
        }else {
            callback.onLoad(false, false);
        }
    }

    @Override
    public void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress) {
        new Thread(){
            @Override
            public void run() {
                try {
                    ApiRetrofit apiRetrofit1 = ApiRetrofit.getInstance();
                    Log.d(TAG,"url " + url);
                    ResponseBody body = apiRetrofit1.downloadFileByUrl(url);
                    int count;
                    byte data[] = new byte[1024 * 4];
                    long fileSize = body.contentLength();
                    InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);

                    String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/DescargaDm3.0"+ carpeta;
                    File dir = new File(file_path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File outputFile = new File(file_path, nombre);
                    OutputStream outputStream = new FileOutputStream(outputFile);
                    long total = 0;
                    boolean downloadComplete = false;
                    //int totalFileSize = (int) (fileSiz
                    // e / (Math.pow(1024, 2)));
                    int gloabalcount = 0;
                    DownloadCancelUi downloadCancelUi = new DownloadCancelUi();
                    stringCallbackProgress.onPreLoad(downloadCancelUi);
                    while ((count = inputStream.read(data)) != -1) {
                        if(downloadCancelUi.isCancel()){
                            stringCallbackProgress.onLoad(false, null);
                            break;
                        }
                        total += count;
                        int progress = (int) ((double) (total * 100) / (double) fileSize);
                        if(gloabalcount==progress)stringCallbackProgress.onProgress(progress);
                        gloabalcount = progress;
                        Log.d(TAG,"progress" + progress + " %");
                        outputStream.write(data, 0, count);
                        downloadComplete = true;
                    }
                    Log.d(TAG,"downloadComplete" + downloadComplete );
                    if(!downloadCancelUi.isCancel()){
                        stringCallbackProgress.onLoad(true, outputFile.getPath());
                        outputStream.flush();
                    }
                    outputStream.close();
                    inputStream.close();

                } catch (IOException e) {
                    stringCallbackProgress.onLoad(false, null);
                    e.printStackTrace();
                } catch (Exception e){
                    stringCallbackProgress.onLoad(false, null);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public FirebaseCancel updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, CallbackTareaAlumno callbackSimple) {
        return null;
    }

    @Override
    public void updateFirebaseTareaSesion(int idCargaCurso, int calendarioPeriodoId, int SesionAprendizajeId, List<TareasUI> tareasUIList, CallbackSimple callbackSimple) {

    }

    @Override
    public void updateFirebaseTareaAlumno(String tareaId, CallbackSimple callbackSimple) {

    }

    @Override
    public TareasUI updateEvaluacion(String tareaId) {


        return null;
    }
}


