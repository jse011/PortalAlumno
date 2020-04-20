package com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.base.UseCase;

/**
 * Created by SCIEV on 17/08/2018.
 */

public class UpdateActividad extends UseCase<UpdateActividad.RequestValues, UpdateActividad.ResponseValue> {
    private ActividadesRepository actividadesRepository;

    public UpdateActividad(ActividadesRepository actividadesRepository) {
        this.actividadesRepository = actividadesRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        actividadesRepository.updateActividad(requestValues.getActividadesUi(), new ActividadesDataSource.Callback<ActividadesUi>() {
            @Override
            public void onLoad(boolean success, ActividadesUi item) {
                if (success) {
                    getUseCaseCallback().onSuccess(new ResponseValue(success, item));
                } else {
                    getUseCaseCallback().onError();
                }
            }
        });
    }

    public static class RequestValues implements UseCase.RequestValues {
        private ActividadesUi actividadesUi;

        public RequestValues(ActividadesUi actividadesUi) {
            this.actividadesUi = actividadesUi;
        }

        public ActividadesUi getActividadesUi() {
            return actividadesUi;
        }
    }

    public static class ResponseValue implements UseCase.ResponseValue {
        boolean success;
        ActividadesUi actividadesUi;

        public ResponseValue(boolean success, ActividadesUi actividadesUi) {
            this.success = success;
            this.actividadesUi = actividadesUi;
        }

        public boolean isSuccess() {
            return success;
        }

        public ActividadesUi getActividadesUi() {
            return actividadesUi;
        }
    }
}
