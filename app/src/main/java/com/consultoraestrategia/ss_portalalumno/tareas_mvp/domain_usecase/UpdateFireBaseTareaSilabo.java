package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

public class UpdateFireBaseTareaSilabo {
    private TareasMvpRepository repository;

    public UpdateFireBaseTareaSilabo(TareasMvpRepository repository) {
        this.repository = repository;
    }

    public FirebaseCancel execute(int idCargaCurso, int calendarioPeriodoId, CallBack callBack){
        return repository.updateFirebaseTarea(idCargaCurso, calendarioPeriodoId, new TareasMvpDataSource.CallbackTareaAlumno() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError("error");
                }
            }

            @Override
            public void onChangeTareaAlumno(String tareaId, String nota, int tipoNotaId) {
                callBack.onChangeTarea(tareaId, nota, tipoNotaId);
            }

            @Override
            public void onChangeTareaAlumno(List<TareasUI> tareasUIList) {
                callBack.onChangeTarea(tareasUIList);
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
        void onChangeTarea(String tareaId, String nota, int tipoNotaId);
        void onChangeTarea(List<TareasUI> tareasUIList);
    }
}
