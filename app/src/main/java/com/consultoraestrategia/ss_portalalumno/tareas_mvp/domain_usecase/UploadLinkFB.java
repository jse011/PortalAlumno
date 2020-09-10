package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

public class UploadLinkFB {
    private TareasMvpRepository repository;

    public UploadLinkFB(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public void execute(String tareaId, TareaArchivoUi tareaArchivoUi, Callback callback){
        repository.uploadLinkFB(tareaId, tareaArchivoUi, new TareasMvpDataSource.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                callback.onFinish(success);
            }
        });

    }

    public interface Callback{
        void onFinish(boolean success);
    }
}
