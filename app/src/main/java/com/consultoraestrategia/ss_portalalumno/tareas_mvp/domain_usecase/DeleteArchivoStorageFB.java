package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;

public class DeleteArchivoStorageFB {
    private TareasMvpRepository tareasMvpRepository;

    public DeleteArchivoStorageFB(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public void execute(String tareaId, TareaArchivoUi tareaArchivoUi, Callback callback){
        tareasMvpRepository.deleteStorageFB(tareaId, tareaArchivoUi, new TareasMvpDataSource.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                callback.onLoad(success);
            }
        });
    }

    public interface Callback{
        void onLoad(boolean success);
    }
}
