package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;

public class EntregarTareaFB {
    private TareasMvpRepository tareasMvpRepository;

    public EntregarTareaFB(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public void execute(String tareaId, boolean forzarConexion, Callback callback){
        tareasMvpRepository.publicarTareaAlumno(tareaId, forzarConexion, new TareasMvpDataSource.CallbackSimple() {
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
