package com.consultoraestrategia.ss_portalalumno.actividades.data.source;


import com.consultoraestrategia.ss_portalalumno.actividades.data.source.local.ActividadesLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.remote.ActividadesRemoteDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;

import java.util.List;

/**
 * Created by kike on 08/02/2018.
 */

public class ActividadesRepository implements ActividadesDataSource {
    private ActividadesLocalDataSource localDataSource;
    private ActividadesRemoteDataSource remoteDataSource;

    public ActividadesRepository(ActividadesLocalDataSource localDataSource, ActividadesRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void updateSucessDowload(String archivoId, String path, Callback<Boolean> booleanCallback) {
        localDataSource.updateSucessDowload(archivoId, path, booleanCallback);
    }

    @Override
    public void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress) {
        localDataSource.dowloadImage(url, nombre, carpeta, stringCallbackProgress);
    }

    @Override
    public void getActividadesList(int cargaCurso, int sesionAprendizajeId, String backgroundColor, CallbackActividades callbackActividades) {
        localDataSource.getActividadesList(cargaCurso, sesionAprendizajeId, backgroundColor, callbackActividades);
    }

    @Override
    public void updateActividad(ActividadesUi actividadesUi, Callback<ActividadesUi> callback) {
        localDataSource.updateActividad(actividadesUi, callback);
    }

    @Override
    public void upadteFirebaseActividad(int cargaCurso, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList, CallbackSimple callbackSimple) {
        remoteDataSource.upadteFirebaseActividad(cargaCurso, sesionAprendizajeId, actividadesUiList, callbackSimple);
    }

}
