package com.consultoraestrategia.ss_portalalumno.actividades.principal;

import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetActividadesList;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetInstrumentos;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateActividad;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateFirebaseActividades;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;
import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

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
    private Online online;
    private List<ActividadesUi> actividadesUiList = new ArrayList<>();
    private GetInstrumentos getInstrumentos;
    private String color2;
    private String color3;
    private boolean onRestart;

    public ActividadesPresenterImpl(UseCaseHandler handler, GetActividadesList getActividadesList, UpdateActividad updateActividad, DowloadImageUseCase dowloadImageUseCase, UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo,
                                    UpdateFirebaseActividades updateFirebaseActividades, Online online, GetInstrumentos getInstrumentos) {
        this.handler = handler;
        this.getActividadesList = getActividadesList;
        this.updateActividad = updateActividad;
        this.dowloadImageUseCase = dowloadImageUseCase;
        this.updateSuccesDowloadArchivo = updateSuccesDowloadArchivo;
        this.online = online;
        this.updateFirebaseActividades = updateFirebaseActividades;
        this.getInstrumentos = getInstrumentos;
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
        actividadesUiList.clear();
        List<InstrumentoUi> instrumentoUiList = getInstrumentos.execute(sesionAprendizajeId);
        Log.d(TAG,"Instumentos: "+instrumentoUiList.size());
        List<InstrumentoUi> removeList = new ArrayList<>();
        GetActividadesList.ResponseValue responseValue = getActividadesList.execute(new GetActividadesList.RequestValues(cargaCursoId, sesionAprendizajeId));
        if(responseValue!=null&&responseValue.getActividadesUiList().size()!=0){
            for (ActividadesUi actividadesUi : responseValue.getActividadesUiList()){
                actividadesUi.setColor1(color1);
                actividadesUi.setColor2(color2);
                actividadesUi.setColor3(color3);

                if(actividadesUi.getSubRecursosUiList()!=null)
                    for (SubRecursosUi subRecursosUi : actividadesUi.getSubRecursosUiList()){
                        subRecursosUi.setColor(color1);
                    }

                for (InstrumentoUi instrumentoUi : instrumentoUiList){
                    if(instrumentoUi.getInstrumentoEvalId()==actividadesUi.getInstrumentoId()){
                        instrumentoUi.setInstrumentoUi(instrumentoUi);
                        removeList.add(instrumentoUi);
                        break;
                    }

                    if(actividadesUi.getSubRecursosUiList()!=null)
                        for (SubRecursosUi subRecursosUi : actividadesUi.getSubRecursosUiList()){
                            if(instrumentoUi.getInstrumentoEvalId()==subRecursosUi.getInstrumentoId()){
                                subRecursosUi.setInstrumentoUi(instrumentoUi);
                                removeList.add(instrumentoUi);
                                break;
                            }
                        }
                }
                actividadesUiList.add(actividadesUi);
            }
            List<InstrumentoUi> instrumentosUiSesion = new ArrayList<>(instrumentoUiList);
            instrumentosUiSesion.removeAll(removeList);
            Log.d(TAG,"Instumentos remove: "+removeList.size());
            Log.d(TAG,"Instumentos sesion: "+instrumentosUiSesion.size());

            List<Object> objects = new ArrayList<Object>(responseValue.getActividadesUiList());
            if (view != null) view.changeColorActividadList(color3);
            if (view != null) view.showListObject(objects);
        }else {
            if (view != null) view.showListObject(new ArrayList<>());
        }
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
        onRestart = true;
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
    String color1;

    @Override
    public void setExtras() {

    }

    private void getData(){

        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        color1 = gbCursoUi.getParametroDisenioColor1();
        color2 = gbCursoUi.getParametroDisenioColor2();
        color3 = gbCursoUi.getParametroDisenioColor3();
        this.cargaCursoId = gbCursoUi.getCargaCursoId();
    }
    @Override
    public void onResumeFragment() {

    }

    @Override
    public void onSelectActividad(ActividadesUi actividadesUi) {
        actividadesUi.setToogle(!actividadesUi.isToogle());
        if(view!=null)view.updateActividadList(actividadesUi);

        for (ActividadesUi item : actividadesUiList){
            if(!item.equals(actividadesUi)){
                if(item.isToogle()){
                    item.setToogle(false);
                    if(view!=null)view.updateActividadList(item);
                }
            }
        }

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
        GbSesionAprendizajeUi gbSesionAprendizajeUi = null;
        switch (repositorioFileUi.getTipoFileU()){
            case VINCULO:
                if(view!=null)view.showVinculo(repositorioFileUi.getUrl());
                break;
            case YOUTUBE:
                gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
                if(gbSesionAprendizajeUi!=null){
                    gbSesionAprendizajeUi.setActividadId(repositorioFileUi.getActividadId());
                    gbSesionAprendizajeUi.setRecursoId(repositorioFileUi.getRecursoId());
                }
                if(view!=null)view.showYoutube(repositorioFileUi.getUrl(), repositorioFileUi.getNombreRecurso());
                break;
            case MATERIALES:
                break;
            case DOCUMENTO:
            case HOJA_CALCULO:
            case DIAPOSITIVA:
            case IMAGEN:
            case PDF:
                if(view!=null)view.showPreviewArchivo(repositorioFileUi.getDriveId(), repositorioFileUi.getNombreRecurso());
                break;
            case VIDEO:
            case AUDIO:
                gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
                if(gbSesionAprendizajeUi!=null){
                    gbSesionAprendizajeUi.setActividadId(repositorioFileUi.getActividadId());
                    gbSesionAprendizajeUi.setRecursoId(repositorioFileUi.getRecursoId());
                }
                if(view!=null)view.showMultimediaPlayer(repositorioFileUi.getDriveId(),repositorioFileUi.getNombreRecurso());
                break;
        }
    }

    @Override
    public void notifyChangeFragment() {
        if(view!=null)view.showProgress();
        online.online(success -> {
            if(success){
                updateActividadFB();
            }else {
                if(view!=null)view.hideProgress();
            }
        });
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
        online.online(success -> {
            if(!success){
                if(view!=null)view.hideProgress();
                getActividadesList();
            }else {
                updateActividadFB();
            }
        });
    }

    private void updateActividadFB() {
        updateFirebaseActividades.execute(cargaCursoId, sesionAprendizajeId, actividadesUiList, new UpdateFirebaseActividades.CallBack() {
            @Override
            public void onSucces() {
                actividadesUiList.clear();
                if(view!=null)view.hideProgress();
                getActividadesList();

                if(actividadesUiList.isEmpty()){
                    if (view != null)view.showMessage();
                }else {
                    if (view != null)view.hideMessage();
                }
            }

            @Override
            public void onError(String error) {
                if(view!=null)view.hideProgress();
            }
        });
    }

    @Override
    public void onActivityCreated() {
        Log.d(TAG, "onActivityCreated");

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
