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

public class GetActividadesList  {

    private ActividadesRepository repository;
    private ResponseValue responseValue = null;
    public GetActividadesList(ActividadesRepository repository) {
        this.repository = repository;
    }

    public ResponseValue execute(RequestValues requestValues) {
        repository.getActividadesList(requestValues.getCargaCursoId(), requestValues.getSesionAprendizajeId(), new ActividadesDataSource.CallbackActividades() {
            @Override
            public void onListeActividades(List<ActividadesUi> actividadesUiList, List<RecursosUi> recursosUiList, int status) {
                responseValue = new ResponseValue(actividadesUiList, recursosUiList);
            }
        });

        return responseValue;
    }

    public static class RequestValues {
        private int cargaCursoId;
        private int sesionAprendizajeId;

        public RequestValues(int cargaCursoId, int sesionAprendizajeId) {
            this.cargaCursoId = cargaCursoId;
            this.sesionAprendizajeId = sesionAprendizajeId;
        }

        public int getCargaCursoId() {
            return cargaCursoId;
        }

        public int getSesionAprendizajeId() {
            return sesionAprendizajeId;
        }
    }

    public static class ResponseValue {
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
