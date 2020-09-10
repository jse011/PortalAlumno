package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

public class UpdateFirebaseBaseTarea {
    private TareasMvpRepository repository;

    public UpdateFirebaseBaseTarea(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public void execute(int idCargaCurso, int calendarioPeriodoId, String tareaId, UpdateFireBaseTareaSesion.CallBack callBack){
        repository.updateFirebaseTarea(idCargaCurso, calendarioPeriodoId, tareaId, new TareasMvpDataSource.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError("error");
                }
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
