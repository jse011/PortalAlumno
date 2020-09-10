package com.consultoraestrategia.ss_portalalumno.actividades.data.source;

import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.DownloadCancelUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;

import java.util.List;

/**
 * Created by kike on 08/02/2018.
 */

public interface ActividadesDataSource {
    void updateSucessDowload(String archivoId, String path, Callback<Boolean> booleanCallback);

    void dowloadImage(String url, String nombre, String carpeta, CallbackProgress<String> stringCallbackProgress);

    interface CallbackActividades {
        void onListeActividades(List<ActividadesUi> actividadesUiList, List<RecursosUi> recursosUiList, int status);
    }

    interface CallbackRecuros {
        void onListRecursos(List<RecursosUi> recursosUiList, int status);
    }

    interface Callback<T> {
        void onLoad(boolean success, T item);
    }

    interface CallbackSimple {
        void onLoad(boolean success);
    }

    interface CallbackProgress<T>  {
        void onProgress(int count);
        void onLoad(boolean success, T item);
        void onPreLoad(DownloadCancelUi isCancel);
    }

    void getActividadesList(int cargaCurso, int sesionAprendizajeId, CallbackActividades callbackActividades);

    void updateActividad(ActividadesUi actividadesUi, Callback<ActividadesUi> callback);

    void upadteFirebaseActividad(int cargaCurso, int sesionAprendizajeId, List<ActividadesUi> actividadesUiList, CallbackSimple callbackSimple);

    List<InstrumentoUi> getInstrumentos(int sesionAprendizajeId);
}
