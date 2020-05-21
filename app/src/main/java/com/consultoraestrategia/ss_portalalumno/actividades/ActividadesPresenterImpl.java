package com.consultoraestrategia.ss_portalalumno.actividades;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetActividadesList;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateActividad;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateFirebaseActividades;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.ui.ActividadesView;
import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.global.offline.Offline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kike on 08/02/2018.
 */

public class ActividadesPresenterImpl implements ActividadesPresenter {
    private static String TAG = ActividadesPresenterImpl.class.getSimpleName();
    private UseCaseHandler handler;
    private GetActividadesList getActividadesList;
    private UpdateActividad updateActividad;
    private ActividadesView view;
    private DowloadImageUseCase dowloadImageUseCase;
    private UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo;
    private UpdateFirebaseActividades updateFirebaseActividades;
    private Offline offline;
    private List<ActividadesUi> actividadesUiList = new ArrayList<>();

    public ActividadesPresenterImpl(UseCaseHandler handler, GetActividadesList getActividadesList, UpdateActividad updateActividad, DowloadImageUseCase dowloadImageUseCase, UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo,
                                    UpdateFirebaseActividades updateFirebaseActividades, Offline offline) {
        this.handler = handler;
        this.getActividadesList = getActividadesList;
        this.updateActividad = updateActividad;
        this.dowloadImageUseCase = dowloadImageUseCase;
        this.updateSuccesDowloadArchivo = updateSuccesDowloadArchivo;
        this.offline = offline;
        this.updateFirebaseActividades = updateFirebaseActividades;
    }

    @Override
    public void attachView(ActividadesView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    private void getActividadesList() {
        Log.d(TAG, "getActividadesList");
        if (view != null) view.hideMessage();
        actividadesUiList.clear();
        handler.execute(getActividadesList,
                new GetActividadesList.RequestValues(cargaCursoId, sesionAprendizajeId, backgroundColor),
                new UseCase.UseCaseCallback<GetActividadesList.ResponseValue>() {
                    @Override
                    public void onSuccess(GetActividadesList.ResponseValue response) {
                        Log.d(TAG, "onSuccess");
                        if (response.getActividadesUiList().size() != 0) {
                            actividadesUiList.addAll(response.getActividadesUiList());
                            List<Object> objects = new ArrayList<Object>(response.getActividadesUiList());
                            if (view != null) view.showListObject(objects);
                        } else view.showMessage();

                        if(offline.isConnect()){
                            updateFirebaseActividades.execute(cargaCursoId, sesionAprendizajeId, actividadesUiList, new UpdateFirebaseActividades.CallBack() {
                                @Override
                                public void onSucces() {
                                    actividadesUiList.clear();
                                    handler.execute(getActividadesList,
                                            new GetActividadesList.RequestValues(cargaCursoId, sesionAprendizajeId, backgroundColor), new UseCase.UseCaseCallback<GetActividadesList.ResponseValue>() {
                                                @Override
                                                public void onSuccess(GetActividadesList.ResponseValue response) {
                                                    if (response.getActividadesUiList().size() != 0) {
                                                        actividadesUiList.addAll(response.getActividadesUiList());
                                                        List<Object> objects = new ArrayList<Object>(response.getActividadesUiList());
                                                        if (view != null) view.showListObject(objects);
                                                    } else view.showMessage();
                                                }

                                                @Override
                                                public void onError() {

                                                }
                                            });

                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
    }

    int sesionAprendizajeId, cargaCursoId;
    String backgroundColor;

    @Override
    public void setExtras() {

    }

    private void getData(){

        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        backgroundColor = gbCursoUi.getParametroDisenioColor1();
        this.cargaCursoId = gbCursoUi.getCargaCursoId();
    }
    @Override
    public void onResumeFragment() {

    }

    @Override
    public void onSelectActividad(ActividadesUi actividadesUi) {
        updateActividad(actividadesUi);
    }

    @Override
    public void onClickDownload(final RecursosUi repositorioFileUi) {
        handler.execute(dowloadImageUseCase, new DowloadImageUseCase.RequestValues(repositorioFileUi),
                new UseCase.UseCaseCallback<UseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(UseCase.ResponseValue response) {
                        if (response instanceof DowloadImageUseCase.ResponseProgressValue) {
                            DowloadImageUseCase.ResponseProgressValue responseProgressValue = (DowloadImageUseCase.ResponseProgressValue) response;
                            if (view != null)
                                view.setUpdateProgress(responseProgressValue.getRepositorioFileUi(), responseProgressValue.getCount());
                            Log.d(TAG, ":( :" + repositorioFileUi.getNombreArchivo() + " = " + responseProgressValue.getRepositorioFileUi().getNombreArchivo());
                        }
                        if (response instanceof DowloadImageUseCase.ResponseSuccessValue) {
                            final DowloadImageUseCase.ResponseSuccessValue responseValue = (DowloadImageUseCase.ResponseSuccessValue) response;
                            saveRegistorRecursos(repositorioFileUi, new UpdateSuccesDowloadArchivo.Callback() {
                                @Override
                                public void onResponse(boolean success) {
                                    if(success){
                                        if(view!=null)view.setUpdate(responseValue.getRepositorioFileUi());
                                        if(view!=null)view.leerArchivo(repositorioFileUi.getPath());
                                    }else{
                                        responseValue.getRepositorioFileUi().setEstadoFileU(RepositorioEstadoFileU.ERROR_DESCARGA);
                                        Log.d(TAG,"error al actualizar archivoId: " + repositorioFileUi.getArchivoId()+ " con el pathLocal:" + responseValue.getRepositorioFileUi().getPath());
                                        if(view!=null)view.setUpdate(responseValue.getRepositorioFileUi());
                                    }
                                }
                            });
                            Log.d(TAG, "pathLocal:" + responseValue.getRepositorioFileUi().getPath());
                        }
                        if (response instanceof DowloadImageUseCase.ResponseErrorValue) {
                            DowloadImageUseCase.ResponseErrorValue responseErrorValue = (DowloadImageUseCase.ResponseErrorValue) response;
                            if (view != null)
                                view.setUpdate(responseErrorValue.getRepositorioFileUi());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void saveRegistorRecursos(RecursosUi repositorioFileUi, UpdateSuccesDowloadArchivo.Callback callback) {
        updateSuccesDowloadArchivo.execute(new UpdateSuccesDowloadArchivo.Request(repositorioFileUi.getArchivoId(), repositorioFileUi.getPath()), callback);
    }

    @Override
    public void onClickClose(RecursosUi repositorioFileUi) {
        if (view != null) repositorioFileUi.setCancel(true);
    }

    @Override
    public void onClickArchivo(RecursosUi repositorioFileUi) {
        switch (repositorioFileUi.getTipoFileU()) {
            case VINCULO:
                if (view != null) view.showVinculo(repositorioFileUi.getUrl());
                break;
            case YOUTUBE:
                if(view!=null)view.showYoutube(repositorioFileUi.getUrl());
                break;
            case MATERIALES:
                break;
            default:
                if (repositorioFileUi.getEstadoFileU() == RepositorioEstadoFileU.DESCARGA_COMPLETA)
                    if (view != null) view.leerArchivo(repositorioFileUi.getPath());
                break;
        }


    }

    @Override
    public void onAttach() {
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreateView() {
        Log.d(TAG, "onCreateView");
    }

    @Override
    public void onViewCreated() {
        Log.d(TAG, "onViewCreated");
        getData();
        if(view!=null)view.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(view!=null)view.hideProgress();
                initViewActividades();
            }
        }, 600);

    }

    @Override
    public void onActivityCreated() {
        Log.d(TAG, "onActivityCreated");

    }

    private void initViewActividades() {
        if (view != null) {
            view.clearActividades();
            getActividadesList();
        }
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach");
    }

    private void updateActividad(ActividadesUi actividadesUi) {
        handler.execute(updateActividad, new UpdateActividad.RequestValues(actividadesUi),
                new UseCase.UseCaseCallback<UpdateActividad.ResponseValue>() {
                    @Override
                    public void onSuccess(UpdateActividad.ResponseValue response) {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public void setExtras(Bundle extras) {

    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }
}
