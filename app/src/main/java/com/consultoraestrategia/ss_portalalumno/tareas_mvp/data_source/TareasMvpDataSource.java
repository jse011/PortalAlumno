package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.DownloadCancelUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public interface TareasMvpDataSource {

    void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis);

    List<RecursosUI> getRecursosTarea(String tareaId);

    List<TareaArchivoUi> getArchivoTareaAlumno(String tareaId);

    StorageCancel uploadStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, StorageCallback<TareaArchivoUi> callbackStorage);

    void deleteStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, CallbackSimple callbackSimple);

    void publicarTareaAlumno(String tareaId, boolean forzarConexion, CallbackSimple callbackSimple);

    TareasUI isEntregadoTareaAlumno(String tareaId);

    void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, String tareaId, CallbackSimple callback);

    TareasUI getTarea(String tareaId);

    void uploadLinkFB(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, CallbackSimple simple);

    interface CallbackTareas{
        void onParametroDisenio(ParametroDisenioUi parametroDisenioUi, int status);
    }

    interface Callback <T>{
        void onLoad(boolean success, T item);
    }

    interface CallbackSimple{
        void onLoad(boolean success);
    }

    interface CallbackTareaAlumno{
        void onLoad(boolean success);
        void onChangeTareaAlumno(String tareaId, String nota, int tipoNotaId);

        void onChangeTareaAlumno(List<TareasUI> tareasUIList);
    }

    interface CallbackProgress<T>  {
        void onProgress(int count);
        void onLoad(boolean success, T item);
        void onPreLoad(DownloadCancelUi isCancel);
    }

    interface StorageCallback<T>  {
        void onChange(T item);
        void onFinish(boolean success, T item);

        void onErrorMaxSize();
    }

    List<HeaderTareasAprendizajeUI> getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId);

    void getParametroDisenio(int parametroDisenioId, CallbackTareas callbackTareas);

    void updateSucessDowload(String archivoId, String path, Callback<Boolean> callback);

    void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress);

    FirebaseCancel updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, CallbackTareaAlumno callbackTareaAlumno);

    void updateFirebaseTareaSesion(int idCargaCurso, int calendarioPeriodoId, int SesionAprendizajeId,List<TareasUI> tareasUIList, CallbackSimple callbackSimple);

    void updateFirebaseTareaAlumno(String tareaId, CallbackSimple callbackSimple);

    TareasUI updateEvaluacion(String tareaId);

}
