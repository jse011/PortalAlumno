package com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.base.UseCase;

import java.util.List;

/**
 * Created by kike on 08/02/2018.
 */

public class GetActividadesList extends UseCase<GetActividadesList.RequestValues, GetActividadesList.ResponseValue> {

    private ActividadesRepository repository;

    public GetActividadesList(ActividadesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        repository.getActividadesList(requestValues.getCargaCursoId(), requestValues.getSesionAprendizajeId(), requestValues.getBackgroundColor(), new ActividadesDataSource.CallbackActividades() {
            @Override
            public void onListeActividades(List<ActividadesUi> actividadesUiList, List<RecursosUi> recursosUiList, int status) {
                getUseCaseCallback().onSuccess(new ResponseValue(actividadesUiList, recursosUiList));
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private int cargaCursoId;
        private int sesionAprendizajeId;
        private String backgroundColor;

        public RequestValues(int cargaCursoId, int sesionAprendizajeId, String backgroundColor) {
            this.cargaCursoId = cargaCursoId;
            this.sesionAprendizajeId = sesionAprendizajeId;
            this.backgroundColor = backgroundColor;
        }

        public int getCargaCursoId() {
            return cargaCursoId;
        }

        public int getSesionAprendizajeId() {
            return sesionAprendizajeId;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        private List<ActividadesUi> actividadesUiList;
        private List<RecursosUi> recursosUiList;

        public ResponseValue(List<ActividadesUi> actividadesUiList, List<RecursosUi> recursosUiList) {
            this.actividadesUiList = actividadesUiList;
            this.recursosUiList = recursosUiList;
        }

        public List<ActividadesUi> getActividadesUiList() {
            return actividadesUiList;
        }

        public void setActividadesUiList(List<ActividadesUi> actividadesUiList) {
            this.actividadesUiList = actividadesUiList;
        }

        public List<RecursosUi> getRecursosUiList() {
            return recursosUiList;
        }

        public void setRecursosUiList(List<RecursosUi> recursosUiList) {
            this.recursosUiList = recursosUiList;
        }
    }
}
