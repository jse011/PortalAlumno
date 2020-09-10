package com.consultoraestrategia.ss_portalalumno.tareas_mvp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
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
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.ui.FragmentTareas;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public class TareasMvpPresenterImpl implements TareasMvpPresenter {
    private static final String TAG = TareasMvpPresenterImpl.class.getSimpleName();
    private TareasMvpView view;
    private UseCaseHandler handler;
    private GetTareasUIList getTareasUIList;
    int tipoTarea = 0;
    int idCargaCurso = 0;
    int mSesionAprendizajeId = 0;
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

    private void getTareasCurso(int tipoTarea,int idCargaCurso, int mSesionAprendizajeId) {
        if (view!=null)view.hideMessage();
        if (view!=null)view.showProgress();
        int calendarioPeriodoId = 0;
        if(idCalendarioPeriodo != 0)calendarioPeriodoId = idCalendarioPeriodo;

        getTareasUIList.executeUseCase(new GetTareasUIList.RequestValues(0, idCargaCurso, tipoTarea, mSesionAprendizajeId, calendarioPeriodoId, anioAcademicoId, planCursoId), new GetTareasUIList.Callback() {
            @Override
            public void onSuccess(GetTareasUIList.ResponseValue response) {
                //#region
                try {
                    for(HeaderTareasAprendizajeUI newheaderTareasAprendizajeUI: response.getHeaderTareasAprendizajeUIList()){

                        for(HeaderTareasAprendizajeUI headerTareasAprendizajeUI: headerTareasAprendizajeUIList){

                            if(headerTareasAprendizajeUI.getIdUnidadAprendizaje()==newheaderTareasAprendizajeUI.getIdUnidadAprendizaje()){

                                for (TareasUI newtareasUI : newheaderTareasAprendizajeUI.getTareasUIList()){

                                    for (TareasUI tareasUI : headerTareasAprendizajeUI.getTareasUIList()){

                                        if(tareasUI.getKeyTarea().equals(newtareasUI.getKeyTarea())){

                                            int pocision =0;
                                            for (RecursosUI newrepositorioFileUi : newtareasUI.getRecursosUIList()){

                                                for (RecursosUI repositorioFileUi : tareasUI.getRecursosUIList()){
                                                    String recurso1 = !TextUtils.isEmpty(repositorioFileUi.getArchivoId())?repositorioFileUi.getArchivoId():"";
                                                    String recurso2 = !TextUtils.isEmpty(newrepositorioFileUi.getArchivoId())?newrepositorioFileUi.getArchivoId():"";
                                                    if(recurso1.equals(recurso2)&&!TextUtils.isEmpty(recurso1)&&!TextUtils.isEmpty(recurso2)){
                                                        newtareasUI.getRecursosUIList().set(pocision, repositorioFileUi);
                                                    }

                                                }
                                                pocision++;
                                            }

                                        }

                                    }

                                }

                            }

                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                headerTareasAprendizajeUIList.clear();
                headerTareasAprendizajeUIList.addAll(response.getHeaderTareasAprendizajeUIList());
                if (view!=null)view.showTareasUIList(headerTareasAprendizajeUIList, idCurso, parametroDisenioUi);
                if(view!=null)view.hideProgress();
            }

            @Override
            public void onError(String error) {
                if (view!=null)view.hideTareasUIList();
                if (view!=null)view.showMessage();
                Log.d(TAG, "Error");
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
        this.tipoTarea = extras.getInt(FragmentTareas.tipoTareas,0);
        setData();
        online.online(success -> {
            if(!success){
                getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
            }
        });
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

        if(this.tipoTarea!=0){
            GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
            this.mSesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        }
    }


    public void getTareas(int mIdCargaCurso, int mIdCurso, int mSesionAprendizajeId, int tipoTareas) {
        Log.d(TAG, "mIdCargaCurso : " + mIdCargaCurso);
        Log.d(TAG, "mIdCurso : " + mIdCurso);
        Log.d(TAG, "mSesionAprendizajeId : " + mSesionAprendizajeId);
        getTareasCurso(tipoTareas, mIdCargaCurso, mSesionAprendizajeId);
    }

    @Override
    public void deleteTarea(TareasUI tareasUI) {
        TareasC tareas = SQLite.select()
                .from(TareasC.class)
                .where(TareasC_Table.key.is(tareasUI.getKeyTarea()))
                .querySingle();
        if (tareas != null) {
            tareas.setEstadoId(265);
            tareas.setSyncFlag(TareasC.FLAG_UPDATED);
            tareas.setEstadoExportado(0);
            tareas.save();
            if(view!=null)view.exportarTareasEliminadas(programaEducativoId);
            if(view!=null)view.exportarTareasEliminadas(programaEducativoId);

            Log.d(TAG, "Tarea ELiminar");
        } else {
            Log.d(TAG, "No hay Tarea para ELiminar");
        }

        cancelDowload(tareasUI);

        getTareasCurso(tipoTarea, idCargaCurso, mSesionAprendizajeId);

    }



    @Override
    public void onResumeFragment() {
        getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
    }

    @Override
    public void onClickedCrearTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int mIdCurso) {

        if(view!=null)view.showActivityCrearTareas(headerTareasAprendizajeUI,0,idSilaboEvento,mSesionAprendizajeId, idCurso, parametroDisenioUi.getColor1(), programaEducativoId);
    }

    @Override
    public void onClickedOpTareaEdit(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        if(view!=null)view.showlActivityEditTareas(tareasUI,headerTareasAprendizajeUI, 0,headerTareasAprendizajeUI.getIdSilaboEvento(),mSesionAprendizajeId, idCurso, parametroDisenioUi.getColor1(), programaEducativoId);
        cancelDowload(tareasUI);
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
        if(view!=null)view.showActivityCrearRubro(tareasUI, headerTareasAprendizajeUI.getIdSilaboEvento(), idCalendarioPeriodo, programaEducativoId,mSesionAprendizajeId, parametroDisenioUi.getColor1(), idCurso);
    }

    @Override
    public void onCrearRubrica(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        if(view!=null)view.showActivityCrearRubrica(tareasUI, idCalendarioPeriodo, idCargaCurso, idCurso, programaEducativoId,mSesionAprendizajeId, parametroDisenioUi.getColor1());
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
                if(view!=null)view.showEvaluacionRubroGrupal(tareasUI,rubroEvalProcesoUi, idCargaCurso, mSesionAprendizajeId, idCurso, parametroDisenioUi.getColor1());
                break;
            case INDIVIDUAL:
                if(view!=null)view.showEvaluacionRubroIndividual(tareasUI,rubroEvalProcesoUi, idCargaCurso, mSesionAprendizajeId, idCurso, parametroDisenioUi.getColor1());
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
        cancelAllDowload();
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
        cancelAllDowload();
        setData();
        if(view!=null)view.showProgress();
        if(firebasTarea!=null)firebasTarea.cancel();
        online.online(success -> {
            if(success&&finishUpdateUnidadFb){
                List<TareasUI> tareasUIList = new ArrayList<>();
                for (HeaderTareasAprendizajeUI headerTareasAprendizajeUIList: headerTareasAprendizajeUIList){
                    tareasUIList.addAll(headerTareasAprendizajeUIList.getTareasUIList());
                }
                if(tipoTarea==0){

                   firebasTarea = updateFireBaseTareaSilabo.execute(idCargaCurso, idCalendarioPeriodo, tareasUIList, new UpdateFireBaseTareaSilabo.CallBack() {
                        @Override
                        public void onSucces() {
                            getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
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
                                        if(view!=null)view.updateTarea(tareasUI);
                                        break;
                                  }
                              }
                          }
                       }
                   });
                }else {
                    updateFireBaseTareaSesion.execute(idCargaCurso, idCalendarioPeriodo, mSesionAprendizajeId, tareasUIList, new UpdateFireBaseTareaSesion.CallBack() {
                        @Override
                        public void onSucces() {
                            getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
                            if(view!=null)view.hideProgress();
                            if(headerTareasAprendizajeUIList.isEmpty()){
                                if(view!=null)view.showMessage();
                            }else {
                                if(view!=null)view.hideMessage();
                            }
                        }

                        @Override
                        public void onError(String error) {
                            if(view!=null)view.hideProgress();
                        }
                    });
                }
            }else {
                if(tipoTarea==0){
                    getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
                }else {
                    getTareas(idCargaCurso, idCurso, mSesionAprendizajeId, tipoTarea);
                    if(view!=null)view.hideProgress();
                    if(headerTareasAprendizajeUIList.isEmpty()){
                        if(view!=null)view.showMessage();
                    }else {
                        if(view!=null)view.hideMessage();
                    }
                }
            }
        });
    }

    private void cancelAllDowload() {
        try {
            for(HeaderTareasAprendizajeUI headerTareasAprendizajeUI: headerTareasAprendizajeUIList){
                for (TareasUI tareasUI : headerTareasAprendizajeUI.getTareasUIList()){
                    for (RepositorioFileUi repositorioFileUi : tareasUI.getRecursosUIList()){
                        repositorioFileUi.setCancel(true);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void cancelDowload(TareasUI tareasUI) {
        try {
            for (RepositorioFileUi repositorioFileUi : tareasUI.getRecursosUIList()){
                repositorioFileUi.setCancel(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
