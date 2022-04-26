package com.consultoraestrategia.ss_portalalumno.tareas_mvp;

import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetTareasUIList;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFireBaseTareaSesion;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFireBaseTareaSilabo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RubroEvalProcesoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public class TareasMvpPresenterImpl implements TareasMvpPresenter {
    private static final String TAG = TareasMvpPresenterImpl.class.getSimpleName();
    private TareasMvpView view;
    private UseCaseHandler handler;
    private GetTareasUIList getTareasUIList;
    int idCargaCurso = 0;
    int idCurso;
    private int idCalendarioPeriodo;
    private int programaEducativoId;
    private ParametroDisenioUi parametroDisenioUi;
    private DowloadImageUseCase dowloadImageUseCase;
    private UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo;
    private MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea;
    private List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList = new ArrayList<>();
    private int anioAcademicoId;
    private int planCursoId;
    private Online online;
    private UpdateFireBaseTareaSilabo updateFireBaseTareaSilabo;
    private UpdateFireBaseTareaSesion updateFireBaseTareaSesion;
    private FirebaseCancel firebasTarea;

    public TareasMvpPresenterImpl(UseCaseHandler handler, GetTareasUIList getTareasUIList,
                                  DowloadImageUseCase dowloadImageUseCase,
                                  UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo,
                                  MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea,
                                  UpdateFireBaseTareaSilabo updateFireBaseTareaSilabo,
                                  UpdateFireBaseTareaSesion updateFireBaseTareaSesion,
                                  Online online) {
        this.handler = handler;
        this.getTareasUIList = getTareasUIList;
        this.dowloadImageUseCase = dowloadImageUseCase;
        this.updateSuccesDowloadArchivo = updateSuccesDowloadArchivo;
        this.moverArchivosAlaCarpetaTarea = moverArchivosAlaCarpetaTarea;
        this.updateFireBaseTareaSilabo = updateFireBaseTareaSilabo;
        this.online = online;
        this.updateFireBaseTareaSesion = updateFireBaseTareaSesion;
    }

    @Override
    public void attachView(TareasMvpView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    private void getTareasCurso(int idCargaCurso) {
        if (view!=null)view.hideMessage();
        if (view!=null)view.showProgress();
        int calendarioPeriodoId = 0;
        if(idCalendarioPeriodo != 0)calendarioPeriodoId = idCalendarioPeriodo;
        List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList = getTareasUIList.execute(new GetTareasUIList.RequestValues(0, idCargaCurso, 0, 0, calendarioPeriodoId, anioAcademicoId, planCursoId));
        try {
            for(HeaderTareasAprendizajeUI newheaderTareasAprendizajeUI: headerTareasAprendizajeUIList){
                newheaderTareasAprendizajeUI.setParametroDisenioUi(parametroDisenioUi);
                newheaderTareasAprendizajeUI.setCantidadUnidades(headerTareasAprendizajeUIList.size());
                int position = 0;
                for (TareasUI newtareasUI : newheaderTareasAprendizajeUI.getTareasUIList()){
                    newtareasUI.setPosition(newheaderTareasAprendizajeUI.getTareasUIList().size() - position);
                    newtareasUI.setParametroDisenioUi(parametroDisenioUi);
                    position++;

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        this.headerTareasAprendizajeUIList.clear();
        this.headerTareasAprendizajeUIList.addAll(headerTareasAprendizajeUIList);
        Collections.sort(this.headerTareasAprendizajeUIList, new Comparator<HeaderTareasAprendizajeUI>() {
            @Override
            public int compare(HeaderTareasAprendizajeUI o1, HeaderTareasAprendizajeUI o2) {
                return Integer.compare(o2.getNroUnidad(),o1.getNroUnidad());
            }
        });
        if (view!=null)view.showTareasUIList(this.headerTareasAprendizajeUIList, idCurso, parametroDisenioUi);
        if(view!=null)view.hideProgress();


    }


    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        this.onResumeFragment();
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
        if(firebasTarea!=null)firebasTarea.cancel();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed   ");
    }

    @Override
    public void setExtras(Bundle extras) {
        setData();
        /*online.online(success -> {
            if(!success){
                getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
            }
        });*/
    }

    private void setData() {
        this.anioAcademicoId = iCRMEdu.variblesGlobales.getAnioAcademicoId();
        this.programaEducativoId = iCRMEdu.variblesGlobales.getProgramEducativoId();
        GbCalendarioPerioUi gbCalendarioPerioUi = iCRMEdu.variblesGlobales.getGbCalendarioPerioUi();
        this.idCalendarioPeriodo = gbCalendarioPerioUi.getCalendarioPeriodoId();
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        this.idCargaCurso = gbCursoUi.getCargaCursoId();
        this.idCurso = gbCursoUi.getCursoId();
        this.planCursoId = gbCursoUi.getPlanCursoId();

        this.parametroDisenioUi = new ParametroDisenioUi();
        this.parametroDisenioUi.setColor1(gbCursoUi.getParametroDisenioColor1());
        this.parametroDisenioUi.setColor2(gbCursoUi.getParametroDisenioColor2());
        this.parametroDisenioUi.setColor3(gbCursoUi.getParametroDisenioColor3());
    }



    @Override
    public void deleteTarea(TareasUI tareasUI) {

    }



    @Override
    public void onResumeFragment() {
        //getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
    }

    @Override
    public void onClickedCrearTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int mIdCurso) {

        if(view!=null)view.showActivityCrearTareas(headerTareasAprendizajeUI,0,idSilaboEvento,0, idCurso, parametroDisenioUi.getColor1(), programaEducativoId);
    }

    @Override
    public void onClickedOpTareaEdit(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        if(view!=null)view.showlActivityEditTareas(tareasUI,headerTareasAprendizajeUI, 0,headerTareasAprendizajeUI.getIdSilaboEvento(),0, idCurso, parametroDisenioUi.getColor1(), programaEducativoId);
    }
    @Override
    public void onActualizarTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        if(view!=null)view.onShowActualizarTareas();
    }

    @Override
    public void onChangeEstado(TareasUI tareasUI) {
        switch (tareasUI.getEstado()){
            case Creado:
                tareasUI.setEstado(TareasUI.Estado.Publicado);
                updateTareaEstado(tareasUI);
                break;
            case Publicado:
                tareasUI.setEstado(TareasUI.Estado.Creado);
                updateTareaEstado(tareasUI);
                break;
        }

    }

    @Override
    public void onCrearRubro(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {

    }

    @Override
    public void onCrearRubrica(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {

    }

    @Override
    public void onClikRubroTarea(TareasUI tareasUI) {
        RubroEvalProcesoUi rubroEvalProcesoUi = tareasUI.getRubroEvalProcesoUi();
        if(rubroEvalProcesoUi == null)return;
        switch (rubroEvalProcesoUi.getTipoRubroEvalProcesoUi()){
            case BIDIMENCIONAL:
                showEvaluacionRubrica(tareasUI,rubroEvalProcesoUi);
                break;
            case UNIDIMENCIONAL:
                showEvaluacionRubro(tareasUI, rubroEvalProcesoUi);
                break;
        }
    }



    private void showEvaluacionRubro(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi) {
        switch (rubroEvalProcesoUi.getFormaRubroEvalProcesoUi()){
            case GRUPAL:

                break;
            case INDIVIDUAL:

                break;
        }
    }

    private void showEvaluacionRubrica(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi) {
        switch (rubroEvalProcesoUi.getFormaRubroEvalProcesoUi()){
            case GRUPAL:
                if(view !=null)view.showEvaluacionRubricaGrupal(tareasUI,rubroEvalProcesoUi,idCargaCurso, parametroDisenioUi.getColor1());
                break;
            case INDIVIDUAL:
                if(view !=null)view.showEvaluacionRubricaIndividual(tareasUI,rubroEvalProcesoUi, idCargaCurso, parametroDisenioUi.getColor1());
                break;
        }
    }


    private void updateTareaEstado(final TareasUI tareasUI) {

        try {
            new Thread(){
                @Override
                public void run() {
                    TareasC tarea = SQLite.select()
                            .from(TareasC.class)
                            .where(TareasC_Table.key.eq(tareasUI.getKeyTarea()))
                            .querySingle();
                    int estadoId = 0;
                    switch (tareasUI.getEstado()){
                        case Creado:
                            estadoId = 263;
                            break;
                        case Publicado:
                            estadoId = 264;
                            break;
                        case Eliminado:
                            estadoId = 265;
                            break;
                    }

                    tarea.setEstadoId(estadoId);
                    tarea.setSyncFlag(TareasC.FLAG_UPDATED);
                    tarea.save();
                    if(view!=null)view.showServiceExportTarea(programaEducativoId);
                }
            }.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void actualizarTareasPeriodo(String idCalendarioPeriodo) {
     this.idCalendarioPeriodo= Integer.parseInt(idCalendarioPeriodo);
     onResumeFragment();
    }

    @Override
    public void onClickDownload(final RepositorioFileUi repositorioFileUi) {
        handler.execute(dowloadImageUseCase, new DowloadImageUseCase.RequestValues(repositorioFileUi),
                new UseCase.UseCaseCallback<UseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(UseCase.ResponseValue response) {
                        if(response instanceof DowloadImageUseCase.ResponseProgressValue){
                            DowloadImageUseCase.ResponseProgressValue responseProgressValue = (DowloadImageUseCase.ResponseProgressValue) response;
                            if(view!=null)view.setUpdateProgress(responseProgressValue.getRepositorioFileUi(), responseProgressValue.getCount());
                            Log.d(TAG,":( :" + repositorioFileUi.getNombreArchivo() +" = " + responseProgressValue.getRepositorioFileUi().getNombreArchivo());
                        }
                        if(response instanceof DowloadImageUseCase.ResponseSuccessValue){
                            final DowloadImageUseCase.ResponseSuccessValue responseValue = (DowloadImageUseCase.ResponseSuccessValue) response;
                            saveRegistorRecursos(repositorioFileUi, new UpdateSuccesDowloadArchivo.Callback() {
                                @Override
                                public void onResponse(boolean success) {
                                    if(success){
                                        if(view!=null)view.setUpdate(responseValue.getRepositorioFileUi());
                                        if(view!=null)view.leerArchivo(repositorioFileUi.getPath());

                                        moverArchivosAlaCarpetaTarea(repositorioFileUi);
                                    }else{
                                        responseValue.getRepositorioFileUi().setEstadoFileU(RepositorioEstadoFileU.ERROR_DESCARGA);
                                        Log.d(TAG,"error al actualizar archivoId: " + repositorioFileUi.getArchivoId()+ " con el pathLocal:" + responseValue.getRepositorioFileUi().getPath());
                                        if(view!=null)view.setUpdate(responseValue.getRepositorioFileUi());
                                    }
                                }
                            });

                            Log.d(TAG,"pathLocal:" + responseValue.getRepositorioFileUi().getPath());
                        }
                        if(response instanceof DowloadImageUseCase.ResponseErrorValue){
                            DowloadImageUseCase.ResponseErrorValue responseErrorValue = (DowloadImageUseCase.ResponseErrorValue) response;
                            if(view!=null)view.setUpdate(responseErrorValue.getRepositorioFileUi());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                }
        );
    }

    private void moverArchivosAlaCarpetaTarea(RepositorioFileUi repositorioFileUi) {
        List<RepositorioFileUi> repositorioFileUiList = new ArrayList<>();
        repositorioFileUiList.add(repositorioFileUi);

        String nombreCurso = "";
        String titulo = "";
        if(repositorioFileUi instanceof RecursosUI){
            TareasUI tareasUI = ((RecursosUI)repositorioFileUi).getTarea();
            if(tareasUI!=null){
                titulo = tareasUI.getTituloTarea();
                nombreCurso=tareasUI.getNombreCurso();
            }
        }
        moverArchivosAlaCarpetaTarea.execute(nombreCurso,titulo, repositorioFileUiList );
    }

    private void saveRegistorRecursos(RepositorioFileUi repositorioFileUi,UpdateSuccesDowloadArchivo.Callback callback ) {
        updateSuccesDowloadArchivo.execute(new UpdateSuccesDowloadArchivo.Request(repositorioFileUi.getArchivoId(), repositorioFileUi.getPath()), callback);
    }

    @Override
    public void onClickClose(RepositorioFileUi repositorioFileUi) {
        repositorioFileUi.setCancel(true);
    }

    @Override
    public void onClickArchivo(RepositorioFileUi repositorioFileUi) {
        switch (repositorioFileUi.getTipoFileU()){
            case VINCULO:
                if(view!=null)view.showVinculo(repositorioFileUi.getUrl());
                break;
            case YOUTUBE:
                if(view!=null)view.showYoutube(repositorioFileUi.getUrl());
                break;
            case MATERIALES:
                break;
            default:
                if(repositorioFileUi.getEstadoFileU()== RepositorioEstadoFileU.DESCARGA_COMPLETA){
                    if(view!=null)view.leerArchivo(repositorioFileUi.getPath());
                }
                break;
        }


    }

    @Override
    public void onClickParentFabClicked() {
        if(!headerTareasAprendizajeUIList.isEmpty()){
            HeaderTareasAprendizajeUI headerTareasAprendizajeUI = headerTareasAprendizajeUIList.get(0);
            if(view!=null)view.showCrearTarea(headerTareasAprendizajeUI, headerTareasAprendizajeUI.getIdSilaboEvento(), idCargaCurso);
        }
    }

    @Override
    public void onDestroyView() {
        view = null;
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onClickTarea(TareasUI tareasUI) {
        GbTareaUi gbTareaUi = new GbTareaUi();
        gbTareaUi.setTareaId(tareasUI.getKeyTarea());
        gbTareaUi.setNombre(tareasUI.getTituloTarea());
        gbTareaUi.setDescripicion(tareasUI.getDescripcion());
        gbTareaUi.setFechaLimite(tareasUI.getFechaLimite());
        gbTareaUi.setFechaCreacionTarea(tareasUI.getFechaCreacionTarea());
        gbTareaUi.setHoraEntrega(tareasUI.getHoraEntrega());
        iCRMEdu.variblesGlobales.setGbTareaUi(gbTareaUi);
        if(view!=null)view.showTareaDescripcionActivity();
        Log.d(TAG, gbTareaUi.toString());
    }

    @Override
    public void notifyChangeFragment(boolean finishUpdateUnidadFb) {
        Log.d(TAG,"notifyChangeFragment tarea");
        setData();
        if(view!=null)view.showProgress();
        if(firebasTarea!=null)firebasTarea.cancel();
        online.online(success -> {
            if(success&&finishUpdateUnidadFb){

                firebasTarea = updateFireBaseTareaSilabo.execute(idCargaCurso, idCalendarioPeriodo, new UpdateFireBaseTareaSilabo.CallBack() {
                    @Override
                    public void onSucces() {
                        getTareasCurso(idCargaCurso);
                    }

                    @Override
                    public void onError(String error) {
                        if(view!=null)view.hideProgress();
                    }

                    @Override
                    public void onChangeTarea(String tareaId, String nota, int tipoNotaId) {
                        //Aqui
                        for (HeaderTareasAprendizajeUI headerTareasAprendizajeUI: headerTareasAprendizajeUIList){
                            for (TareasUI tareasUI : headerTareasAprendizajeUI.getTareasUIList()){
                                if(tareasUI.getKeyTarea().equals(tareaId)){
                                    tareasUI.setNota(nota);
                                    tareasUI.setTipoNotaId(tipoNotaId);
                                    if(view!=null)view.updateTarea(headerTareasAprendizajeUI, tareasUI);
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onChangeTarea(List<TareasUI> tareasUIList) {
                        for (HeaderTareasAprendizajeUI headerTareasAprendizajeUI: headerTareasAprendizajeUIList){
                            List<TareasUI> tareasUIModificadaList  = new ArrayList<>();
                            for (TareasUI tareasUI : headerTareasAprendizajeUI.getTareasUIList()){
                                for (TareasUI newtareasUI: tareasUIList){
                                    if(tareasUI.getKeyTarea().equals(newtareasUI.getKeyTarea())){
                                        tareasUI.setNota(newtareasUI.getNota());
                                        tareasUI.setTipoNotaId(newtareasUI.getTipoNotaId());
                                        tareasUIModificadaList.add(tareasUI);
                                    }
                                }
                            }
                            if(view!=null)view.updateTarea(headerTareasAprendizajeUI, tareasUIModificadaList);
                        }
                    }
                });
            }else {
                getTareasCurso(idCargaCurso);
            }
        });
    }

    @Override
    public void onClickUnidadAprendizaje(HeaderTareasAprendizajeUI unidadAprendizajeUi) {
        if(unidadAprendizajeUi.isToogle()){
            unidadAprendizajeUi.setToogle(false);
        }else {
            unidadAprendizajeUi.setToogle(true);
        }
        if(view !=null) view.updateItem(unidadAprendizajeUi);
    }


}
