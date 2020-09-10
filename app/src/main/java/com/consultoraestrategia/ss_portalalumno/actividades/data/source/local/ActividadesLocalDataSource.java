package com.consultoraestrategia.ss_portalalumno.actividades.data.source.local;


import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.DownloadCancelUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.EEstado;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ESecuencia;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioTipoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;
import com.consultoraestrategia.ss_portalalumno.entities.ActividadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.ActividadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.BaseEntity;
import com.consultoraestrategia.ss_portalalumno.entities.BaseRelEntity;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by kike on 08/02/2018.
 */

public class ActividadesLocalDataSource implements ActividadesDataSource {

    private static String TAG = ActividadesLocalDataSource.class.getSimpleName();

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> booleanCallback) {
        Archivo archivo = SQLite.select()
                .from(Archivo.class)
                .where(Archivo_Table.key.eq(archivoId))
                .querySingle();
        if (archivo != null) {
            archivo.setLocalpath(path);
            boolean success = archivo.save();
            booleanCallback.onLoad(success, success);
        } else {
            booleanCallback.onLoad(false, false);
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
    public void getActividadesList(int cargaCurso, int sesionAprendizajeId, CallbackActividades callbackActividades) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlArchivo"))
                .querySingle();

        String urlArchivo  = webconfig!=null?webconfig.getContent():"";
        List<ActividadAprendizaje> padreActividadAprendizajeList = SQLite.select(UtilsDBFlow.f_allcolumnTable(ActividadAprendizaje_Table.ALL_COLUMN_PROPERTIES))
                .from(ActividadAprendizaje.class)
                .where(ActividadAprendizaje_Table.sesionAprendizajeId.withTable().is(sesionAprendizajeId))
                .and(ActividadAprendizaje_Table.parentId.is(0))
                .queryList();

        Log.d(TAG, "sesionAprendizajeId: " + sesionAprendizajeId);
        Log.d(TAG, "cargaCurso: " + cargaCurso);

        List<ActividadesUi> actividadListPadre = new ArrayList<>();

        List<RecursoDidacticoEventoC> mlst_recursoDidacticoEventoPrimero = SQLite.select()
                .from(RecursoDidacticoEventoC.class)
                .innerJoin(RecursoReferenciaC.class)
                .on(RecursoReferenciaC_Table.recursoDidacticoId.withTable().eq(RecursoDidacticoEventoC_Table.key.withTable()))
                .where(RecursoReferenciaC_Table.sesionAprendizajeId.withTable().is(sesionAprendizajeId))
                .queryList();

        Log.d(TAG, "Size: " + mlst_recursoDidacticoEventoPrimero);

        List<RecursosUi> recursosUiListPrimero = new ArrayList<>();

        int countRecursoActividad = 0;
        for (ActividadAprendizaje actividadPadre : padreActividadAprendizajeList) {
            List<RecursoDidacticoEventoC> mlst_recursoDidacticoEvento = SQLite.select()
                    .from(RecursoDidacticoEventoC.class)
                    .innerJoin(RecursoReferenciaC.class)
                    .on(RecursoReferenciaC_Table.recursoDidacticoId.withTable().eq(RecursoDidacticoEventoC_Table.key.withTable()))
                    .where(RecursoReferenciaC_Table.actividadAprendizajeId.withTable().is(actividadPadre.getActividadAprendizajeId()))
                    .queryList();

            Tipos tipoActividad = SQLite.select()
                    .from(Tipos.class)
                    .where(Tipos_Table.tipoId.withTable().is(actividadPadre.getTipoActividadId()))
                    .querySingle();

            Tipos secuencia = SQLite.select()
                    .from(Tipos.class)
                    .where(Tipos_Table.tipoId.withTable().is(actividadPadre.getSecuenciaId()))
                    .querySingle();

            /*Sub Recursos*/
            List<ActividadAprendizaje> actividadAprendizajes = SQLite.select()
                    .from(ActividadAprendizaje.class)
                    .where(ActividadAprendizaje_Table.parentId.is(actividadPadre.getActividadAprendizajeId()))
                    .queryList();

            Log.d(TAG, "subActividadAprendizajes Size: " + actividadAprendizajes.size());
            List<SubRecursosUi> subRecursosUiList = new ArrayList<>();
            int i = 0;
            /*Cuano tiene Recursos Hijos - de la actividad*/
            for (ActividadAprendizaje aprendizaje : actividadAprendizajes) {
                if (aprendizaje != null) {
                    i++;
                    List<RecursoDidacticoEventoC> mlst_recursoDidacticoEvento2 = SQLite.select()
                            .from(RecursoDidacticoEventoC.class)
                            .innerJoin(RecursoReferenciaC.class)
                            .on(RecursoReferenciaC_Table.recursoDidacticoId.withTable().eq(RecursoDidacticoEventoC_Table.key.withTable()))
                            .where(RecursoReferenciaC_Table.actividadAprendizajeId.withTable().is(aprendizaje.getActividadAprendizajeId()))
                            .queryList();

                    subRecursosUiList.add(new SubRecursosUi(aprendizaje.getActividadAprendizajeId(), i, aprendizaje.getDescripcionActividad(), getListSubRecurso(mlst_recursoDidacticoEvento2, urlArchivo, actividadPadre.getActividadAprendizajeId()), aprendizaje.getInstrumentoEvalId()));
                }
            }


            ActividadesUi actividadesUi = new ActividadesUi(actividadPadre.getActividad(),
                    countRecursoActividad,
                    actividadPadre.getTiempo(),
                    actividadPadre.getEstadoId(),
                    actividadPadre.getTiempo(),
                    tipoActividad!=null?tipoActividad.getNombre():"",
                    secuencia!=null?secuencia.getNombre():"",
                    actividadPadre.getDescripcionActividad(),
                    getRecursosListActividad(mlst_recursoDidacticoEvento, urlArchivo, actividadPadre.getActividadAprendizajeId()),
                    subRecursosUiList);
            actividadesUi.setInstrumentoId(actividadPadre.getInstrumentoEvalId());
            actividadesUi.setId(actividadPadre.getActividadAprendizajeId());

            switch (secuencia!=null?secuencia.getTipoId():0) {
                case 356:
                    actividadesUi.seteSecuencia(ESecuencia.Inicio);
                    break;
                case 357:
                    actividadesUi.seteSecuencia(ESecuencia.Desarrollo);
                    break;
                case 358:
                    actividadesUi.seteSecuencia(ESecuencia.Cierre);
                    break;
                default:
                    actividadesUi.seteSecuencia(ESecuencia.Inicio);
                    break;
            }

            switch (actividadPadre.getEstadoId()) {
                case ActividadAprendizaje.ESTADO_CREADO:
                    actividadesUi.seteEstado(EEstado.Creado);
                    break;
                case ActividadAprendizaje.ESTADO_PENDIENTE:
                    actividadesUi.seteEstado(EEstado.Pendiente);
                    break;
                case ActividadAprendizaje.ESTADO_HECHO:
                    actividadesUi.seteEstado(EEstado.Hecho);
                    break;
                default:
                    actividadesUi.seteEstado(EEstado.Creado);
                    break;
            }

            actividadListPadre.add(actividadesUi);
        }

        for (RecursoDidacticoEventoC recursoDidacticoEvento : mlst_recursoDidacticoEventoPrimero) {
            recursosUiListPrimero.add(getRecursoEvento(recursoDidacticoEvento, urlArchivo, 0));
        }

        Log.d(TAG, "actividadListPadre Size: " + actividadListPadre.size());
        Log.d(TAG, "recursosUiListPrimero Size: " + recursosUiListPrimero.size());
        callbackActividades.onListeActividades(actividadListPadre, recursosUiListPrimero, 1);
    }

    @Override
    public void updateActividad(ActividadesUi actividadesUi, Callback<ActividadesUi> callback) {

        ActividadAprendizaje actividadAprendizaje = SQLite.select()
                .from(ActividadAprendizaje.class)
                .where(ActividadAprendizaje_Table.actividadAprendizajeId.eq(actividadesUi.getId()))
                .querySingle();

        if (actividadAprendizaje != null) {
            switch (actividadesUi.geteEstado()) {
                case Hecho:
                    actividadAprendizaje.setEstadoId(ActividadAprendizaje.ESTADO_HECHO);
                    break;
                case Creado:
                    actividadAprendizaje.setEstadoId(ActividadAprendizaje.ESTADO_CREADO);
                    break;
                case Pendiente:
                    actividadAprendizaje.setEstadoId(ActividadAprendizaje.ESTADO_PENDIENTE);
                    break;
            }
            actividadAprendizaje.setSyncFlag(BaseRelEntity.FLAG_UPDATED);
            actividadAprendizaje.save();
        }
        callback.onLoad(true, actividadesUi);
    }

    @Override
    public void upadteFirebaseActividad(int cargaCurso, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList, CallbackSimple callbackSimple) {

    }

    @Override
    public List<InstrumentoUi> getInstrumentos(int  sesionAprendizajeId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId =  sessionUser!=null?sessionUser.getPersonaId():0;

        List<InstrumentoUi> instrumentoUiList =  new ArrayList<>();
        List<InstrumentoEvaluacion> instrumentoEvaluacionList = SQLite.select()
                .from(InstrumentoEvaluacion.class)
                .where(InstrumentoEvaluacion_Table.SesionId.eq(sesionAprendizajeId))
                .queryList();


        for (InstrumentoEvaluacion instrumentoEvaluacion : instrumentoEvaluacionList){
            InstrumentoUi instrumentoUi = new InstrumentoUi();
            instrumentoUi.setInstrumentoEvalId(instrumentoEvaluacion.getInstrumentoEvalId());

            instrumentoUi.setNombre(instrumentoEvaluacion.getNombre());
            InstrumentoEvaluacionObservado instrumentoEvaluacionObservado = SQLite.select()
                    .from(InstrumentoEvaluacionObservado.class)
                    .where(InstrumentoEvaluacionObservado_Table.InstrumentoEvalId.eq(instrumentoEvaluacion.getInstrumentoEvalId()))
                    .and(InstrumentoEvaluacionObservado_Table.PersonaId.eq(alumnoId))
                    .querySingle();
            int countVAO = 0;
            int puntajeObtenido = 0;
            List<VariableObservado> variableObservadoList = SQLite.select()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.InstrumentoObservadoId.eq(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getInstrumentoObservadoId():""))
                    .queryList();

            for(VariableObservado variableObservado: variableObservadoList){
                countVAO++;
                puntajeObtenido += variableObservado.getPuntajeObtenido();
            }
            int countVA = 0;
            int puntaje = 0;
            List<Variable> variableList = SQLite.select()
                    .from(Variable.class)
                    .where(Variable_Table.InstrumentoEvalId.eq(instrumentoEvaluacion.getInstrumentoEvalId()))
                    .queryList();

            for (Variable variable: variableList){
                countVA++;
                puntaje += variable.getPuntaje();
            }
            long countOVASinEviar = SQLite.selectCountOf()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.InstrumentoObservadoId.eq(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getInstrumentoObservadoId():""))
                    .and(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED,BaseEntity.FLAG_UPDATED))
                    .count();
            int porcentaje = -1;
            if(countVA<=countVAO){
                porcentaje = (int)((double)(puntajeObtenido*100)/(double)puntaje);
            }

            instrumentoUi.setPorcentaje(porcentaje);
            instrumentoUi.setCantidadPregunta(countVA);
            instrumentoUi.setCantidadPreguntaResueltas(countVAO);
            instrumentoUi.setCatidadPreguntasSinEnviar((int)countOVASinEviar);
            instrumentoUiList.add(instrumentoUi);
        }

        return instrumentoUiList;
    }


    private RecursosUi getRecursoEvento(RecursoDidacticoEventoC recursoDidacticoEvento, String urlArchivo, int actividadId) {
        RecursosUi recursosUI = new RecursosUi();
        recursosUI.setActividadId(actividadId);
        recursosUI.setRecursoId(recursoDidacticoEvento.getKey());
        recursosUI.setNombreRecurso(recursoDidacticoEvento.getTitulo());
        recursosUI.setDescripcion(recursoDidacticoEvento.getDescripcion());
        recursosUI.setFechaCreacionRecuros(recursoDidacticoEvento.getFechaCreacion());
        boolean isYoutube = false;
        switch (recursoDidacticoEvento.getTipoId()) {
            case RecursoDidacticoEventoC.TIPO_AUDIO:
                recursosUI.setTipoFileU(RepositorioTipoFileU.AUDIO);
                break;
            case RecursoDidacticoEventoC.TIPO_DIAPOSITIVA:
                recursosUI.setTipoFileU(RepositorioTipoFileU.DIAPOSITIVA);
                break;
            case RecursoDidacticoEventoC.TIPO_DOCUMENTO:
                recursosUI.setTipoFileU(RepositorioTipoFileU.DOCUMENTO);
                break;
            case RecursoDidacticoEventoC.TIPO_HOJA_CALCULO:
                recursosUI.setTipoFileU(RepositorioTipoFileU.HOJA_CALCULO);
                break;
            case RecursoDidacticoEventoC.TIPO_IMAGEN:
                recursosUI.setTipoFileU(RepositorioTipoFileU.IMAGEN);
                break;
            case RecursoDidacticoEventoC.TIPO_PDF:
                recursosUI.setTipoFileU(RepositorioTipoFileU.PDF);
                break;
            case RecursoDidacticoEventoC.TIPO_VIDEO:
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
                    recursosUI.setPath(recursoDidacticoEvento.getLocalUrl());
                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                    recursosUI.setTipoFileU(RepositorioTipoFileU.YOUTUBE);
                } else {
                    recursosUI.setTipoFileU(RepositorioTipoFileU.VIDEO);
                }
                break;
            case RecursoDidacticoEventoC.TIPO_VINCULO:
                isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursoDidacticoEvento.getUrl()));
                if (isYoutube) {
                    recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                    recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                    recursosUI.setPath(recursoDidacticoEvento.getLocalUrl());
                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                    recursosUI.setTipoFileU(RepositorioTipoFileU.YOUTUBE);
                }else {
                    recursosUI.setNombreArchivo(recursoDidacticoEvento.getUrl());
                    recursosUI.setUrl(recursoDidacticoEvento.getUrl());
                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                    recursosUI.setTipoFileU(RepositorioTipoFileU.VINCULO);
                }

                break;

        }

        Log.d(TAG, "archivo:( " + recursoDidacticoEvento.getUrl());
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
                recursosUI.setFechaCreacionRecuros(archivo.getFechaCreacion());
                if (TextUtils.isEmpty(archivo.getLocalpath())) {
                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.SIN_DESCARGAR);
                } else {
                    recursosUI.setEstadoFileU(RepositorioEstadoFileU.DESCARGA_COMPLETA);
                }
                recursosUI.setDriveId(archivo.getPath());
                recursosUI.setPath(archivo.getLocalpath());
                recursosUI.setFechaAccionArchivo(archivo.getFechaAccion());
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
        return recursosUI;
    }


    private List<RecursosUi> getListSubRecurso(List<RecursoDidacticoEventoC> mlst_recursoDidacticoEvento2, String urlArchivo, int actividadId) {
        List<RecursosUi> recursosSubRecursoList = new ArrayList<>();
        for (RecursoDidacticoEventoC recursoDidacticoEvento : mlst_recursoDidacticoEvento2)
            recursosSubRecursoList.add(getRecursoEvento(recursoDidacticoEvento, urlArchivo, actividadId));
        return recursosSubRecursoList;
    }

    /*Cuando son Actividad de Recursos- Sin Hijos*/
    private List<RecursosUi> getRecursosListActividad(List<RecursoDidacticoEventoC> mlst_recursoDidacticoEvento, String urlArchivo, int actividadId) {
        List<RecursosUi> recursosUiListActividad = new ArrayList<>();
        for (RecursoDidacticoEventoC recursoDidacticoEvento : mlst_recursoDidacticoEvento) {
            recursosUiListActividad.add(getRecursoEvento(recursoDidacticoEvento, urlArchivo, actividadId));
        }
        return recursosUiListActividad;
    }


}
