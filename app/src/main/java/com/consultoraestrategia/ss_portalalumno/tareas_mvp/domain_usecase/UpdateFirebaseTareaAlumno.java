package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;

public class UpdateFirebaseTareaAlumno {
    private TareasMvpRepository tareasMvpRepository;

    public UpdateFirebaseTareaAlumno(TareasMvpRepository tareasMvpRepository) {
        this.tareasMvpRepository = tareasMvpRepository;
    }

    public void execute(String tareaId, CallBack callBack){
        tareasMvpRepository.updateFirebaseTareaAlumno(tareaId, new TareasMvpDataSource.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError();
                }
            }
        });
    }
    public interface CallBack{
        void onSucces();
        void onError();
    }
}
