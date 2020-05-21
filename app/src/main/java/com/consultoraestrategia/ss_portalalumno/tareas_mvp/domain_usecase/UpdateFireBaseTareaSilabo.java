package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

public class UpdateFireBaseTareaSilabo {
    private TareasMvpRepository repository;

    public UpdateFireBaseTareaSilabo(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public void execute(int idCargaCurso, int calendarioPeriodoId, List<TareasUI> tareasUIList, CallBack callBack){
        repository.updateFirebaseTarea(idCargaCurso, calendarioPeriodoId, tareasUIList, new TareasMvpDataSource.CallbackSimple() {
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
