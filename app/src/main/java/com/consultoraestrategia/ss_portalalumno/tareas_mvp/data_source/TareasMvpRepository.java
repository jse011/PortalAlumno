package com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source;


import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks.GetTareasListCallback;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.local.TareasLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote.RemoteMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public class TareasMvpRepository implements TareasMvpDataSource {
    private TareasLocalDataSource localDataSource;
    private RemoteMvpDataSource remoteDataSource;

    public TareasMvpRepository(TareasLocalDataSource localDataSource, RemoteMvpDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static TareasMvpRepository getInstace(TareasLocalDataSource localDataSource, RemoteMvpDataSource remoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new TareasMvpRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    private static TareasMvpRepository INSTANCE = null;

    @Override
    public void updateArchivosTarea(List<RepositorioFileUi> repositorioFileUis) {
        localDataSource.updateArchivosTarea(repositorioFileUis);
    }

    @Override
    public List<RecursosUI> getRecursosTarea(String tareaId) {
        return localDataSource.getRecursosTarea(tareaId);
    }

    @Override
    public List<TareaArchivoUi> getArchivoTareaAlumno(String tareaId) {
        return localDataSource.getArchivoTareaAlumno(tareaId);
    }

    @Override
    public StorageCancel uploadStorageFB(String tareaId, TareaArchivoUi tareaArchivoUi, StorageCallback<TareaArchivoUi> callbackStorage) {
        return remoteDataSource.uploadStorageFB(tareaId, tareaArchivoUi, callbackStorage);
    }

    @Override
    public void deleteStorageFB(String tareaId,  TareaArchivoUi tareaArchivoUi,CallbackSimple callbackSimple) {
        remoteDataSource.deleteStorageFB(tareaId, tareaArchivoUi,callbackSimple);
    }

    @Override
    public void publicarTareaAlumno(String tareaId, CallbackSimple callbackSimple) {
        remoteDataSource.publicarTareaAlumno(tareaId, callbackSimple);
    }

    @Override
    public TareasUI isEntregadoTareaAlumno(String tareaId) {
        return localDataSource.isEntregadoTareaAlumno(tareaId);
    }

    @Override
    public void updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, String tareaId, CallbackSimple callback) {
        remoteDataSource.updateFirebaseTarea(idCargaCurso, calendarioPeriodoId, tareaId, callback);
    }

    @Override
    public TareasUI getTarea(String tareaId) {
        return localDataSource.getTarea(tareaId);
    }

    @Override
    public void uploadLinkFB(String tareaId, TareaArchivoUi tareaArchivoUi, CallbackSimple simple) {
        remoteDataSource.uploadLinkFB(tareaId, tareaArchivoUi,simple);
    }

    @Override
    public List<HeaderTareasAprendizajeUI> getTareasUIList(int idUsuario, int idCargaCurso, int tipoTarea, int sesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId) {
        return localDataSource.getTareasUIList(idUsuario, idCargaCurso, tipoTarea, sesionAprendizajeId, calendarioPeriodoId, anioAcademicoId, planCursoId);
    }


    @Override
    public void getParametroDisenio(int parametroDisenioId, CallbackTareas callbackTareas) {
        localDataSource.getParametroDisenio(parametroDisenioId, callbackTareas);
    }

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> callback) {
        localDataSource.updateSucessDowload(archivoId, path, callback);
    }

    @Override
    public void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress) {
        localDataSource.dowloadImage(url, nombre, carpeta, stringCallbackProgress);
    }

    @Override
    public FirebaseCancel updateFirebaseTarea(int idCargaCurso, int calendarioPeriodoId, CallbackTareaAlumno callbackTareaAlumno) {
        return remoteDataSource.updateFirebaseTarea(idCargaCurso,calendarioPeriodoId, callbackTareaAlumno);
    }

    @Override
    public void updateFirebaseTareaSesion(int idCargaCurso, int calendarioPeriodoId, int SesionAprendizajeId, List<TareasUI> tareasUIList, CallbackSimple callbackSimple) {
        remoteDataSource.updateFirebaseTareaSesion(idCargaCurso, calendarioPeriodoId, SesionAprendizajeId, tareasUIList, callbackSimple);
    }

    @Override
    public void updateFirebaseTareaAlumno(String tareaId, CallbackSimple callbackSimple) {
        remoteDataSource.updateFirebaseTareaAlumno(tareaId, callbackSimple);
    }

    @Override
    public TareasUI updateEvaluacion(String tareaId) {
        return localDataSource.updateEvaluacion(tareaId);
    }

}
