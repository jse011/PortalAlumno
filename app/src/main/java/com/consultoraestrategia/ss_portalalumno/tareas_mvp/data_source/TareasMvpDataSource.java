package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks.GetTareasListCallback;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.DownloadCancelUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public interface TareasMvpDataSource {

    void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis);

    List<RecursosUI> getRecursosTarea(String tareaId);

    interface CallbackTareas{
        void onParametroDisenio(ParametroDisenioUi parametroDisenioUi, int status);
    }

    interface Callback <T>{
        void onLoad(boolean success, T item);
    }

    interface CallbackSimple{
        void onLoad(boolean success);
    }

    interface CallbackProgress<T>  {
        void onProgress(int count);
        void onLoad(boolean success, T item);
        void onPreLoad(DownloadCancelUi isCancel);
    }

    void getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId, GetTareasListCallback callback);

    void getParametroDisenio(int parametroDisenioId, CallbackTareas callbackTareas);

    void updateSucessDowload(String archivoId, String path, Callback<Boolean> callback);

    void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress);

    void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, List<TareasUI> tareasUIList, CallbackSimple callbackSimple);

    void updateFirebaseTareaSesion(int idCargaCurso, int calendarioPeriodoId, int SesionAprendizajeId,List<TareasUI> tareasUIList, CallbackSimple callbackSimple);
}
