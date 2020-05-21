package com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase;


import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.callbacks.GetTareasListCallback;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;

import java.util.List;

/**
 * Created by irvinmarin on 03/10/2017.
 */

public class GetTareasUIList {

    private TareasMvpRepository repository;

    public GetTareasUIList(TareasMvpRepository repository) {
        this.repository = repository;
    }


    public void executeUseCase(RequestValues requestValues, Callback callback) {

        repository.getTareasUIList(requestValues.getIdUsuario(), requestValues.getIdCargaCurso(), requestValues.getTipoTarea(), requestValues.getmSesionAprendizajeId(), requestValues.getCalendarioPeriodoId(),requestValues.getAnioAcademicoId(), requestValues.getPlanCursoId(),
                new GetTareasListCallback() {
                    @Override
                    public void onTareasListLoaded(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList) {
                        callback.onSuccess(new ResponseValue(headerTareasAprendizajeUIList));
                    }

                    @Override
                    public void onError(String error) {
                        callback.onError(error);
                    }
                });


    }

    public static final class RequestValues  {
        private final int idUsuario;
        private final int idCargaCurso;
        private final int tipoTarea;
        private final int mSesionAprendizajeId;
        private int calendarioPeriodoId;
        private int anioAcademicoId;
        private int planCursoId;

        public RequestValues(int idUsuario, int idCargaCurso, int tipoTarea, int mSesionAprendizajeId, int calendarioPeriodoId, int anioAcademicoId, int planCursoId) {
            this.idUsuario = idUsuario;
            this.idCargaCurso = idCargaCurso;
            this.tipoTarea = tipoTarea;
            this.mSesionAprendizajeId = mSesionAprendizajeId;
            this.calendarioPeriodoId = calendarioPeriodoId;
            this.anioAcademicoId = anioAcademicoId;
            this.planCursoId = planCursoId;
        }

        public int getmSesionAprendizajeId() {
            return mSesionAprendizajeId;
        }

        public int getIdCargaCurso() {
            return idCargaCurso;
        }

        public int getTipoTarea() {
            return tipoTarea;
        }

        public int getIdUsuario() {
            return idUsuario;
        }

        public int getCalendarioPeriodoId() {
            return calendarioPeriodoId;
        }

        public int getAnioAcademicoId() {
            return anioAcademicoId;
        }

        public void setAnioAcademicoId(int anioAcademicoId) {
            this.anioAcademicoId = anioAcademicoId;
        }

        public int getPlanCursoId() {
            return planCursoId;
        }
    }

    public static final class ResponseValue {
        private final List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList;

        public List<HeaderTareasAprendizajeUI> getHeaderTareasAprendizajeUIList() {
            return headerTareasAprendizajeUIList;
        }

        public ResponseValue(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList) {
            this.headerTareasAprendizajeUIList = headerTareasAprendizajeUIList;
        }
    }

    public interface Callback{
        void onSuccess(ResponseValue responseValue);

        void onError(String error);
    }
}
