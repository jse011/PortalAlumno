package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import android.content.Context;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

import java.io.File;

import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

public class UploadArchivoStorageFB {
    private TareasMvpRepository tareasMvpRepository;
    public UploadArchivoStorageFB(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public StorageCancel execute(String tareaId, TareaArchivoUi tareaArchivoUi, boolean forzarConexion, Callback callback){
        return tareasMvpRepository.uploadStorageFB(tareaId, tareaArchivoUi, forzarConexion, new TareasMvpDataSource.StorageCallback<TareaArchivoUi>() {
            @Override
            public void onChange(TareaArchivoUi item) {
                callback.onChange(item);
            }

            @Override
            public void onFinish(boolean success, TareaArchivoUi item) {
                callback.onFinish(success, item);
            }

            @Override
            public void onErrorMaxSize() {
                callback.onErrorMaxSize();
            }
        });
    }

    public interface Callback{
        void onChange(TareaArchivoUi tareaArchivoUi);
        void onFinish(boolean success, TareaArchivoUi tareaArchivoUi);
        void onErrorMaxSize();
    }
}
