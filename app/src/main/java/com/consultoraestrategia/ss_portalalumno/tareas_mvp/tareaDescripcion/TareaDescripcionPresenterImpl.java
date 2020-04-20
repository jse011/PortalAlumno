package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadYoutube;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetRecuros;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.ArrayList;
import java.util.List;

public class TareaDescripcionPresenterImpl extends BasePresenterImpl<TareasDecripcionView> implements TareaDescripcionPresenter {

    private String tareaId;
    private GetRecuros getRecuros;
    private DowloadImageUseCase dowloadImageUseCase;
    private UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo;
    private MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea;
    private DowloadYoutube dowloadYoutube;

    public TareaDescripcionPresenterImpl(UseCaseHandler handler, Resources res, GetRecuros getRecuros, DowloadImageUseCase dowloadImageUseCase, UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo, MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea, DowloadYoutube dowloadYoutube) {
        super(handler, res);
        this.getRecuros = getRecuros;
        this.dowloadImageUseCase = dowloadImageUseCase;
        this.updateSuccesDowloadArchivo = updateSuccesDowloadArchivo;
        this.moverArchivosAlaCarpetaTarea = moverArchivosAlaCarpetaTarea;
        this.dowloadYoutube = dowloadYoutube;
    }

    @Override
    protected String getTag() {
        return "TareaDescripcionPresenterImpl";
    }

    @Override
    public void onAttach() {

    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void onViewCreated() {

    }

    private void setData() {
        GbTareaUi gbTareaUi = iCRMEdu.variblesGlobales.getGbTareaUi();
        Log.d(getTag(), gbTareaUi.toString());
        if(view!=null)view.setNombreTarea(gbTareaUi.getNombre());
        if(view!=null)view.setDescripcionTarea(gbTareaUi.getDescripicion());
        String horaEntrega = "";
        if (!(gbTareaUi.getFechaLimite() == 0D)) {
            String hora = UtilsPortalAlumno.changeTime12Hour(gbTareaUi.getHoraEntrega());
            String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(gbTareaUi.getFechaLimite());
            if(!TextUtils.isEmpty(hora)){
                fecha_entrega = fecha_entrega + " " + hora;
            }
            horaEntrega = fecha_entrega;
        } else {
            horaEntrega = "Sin límite de entrega";
        }
        this.tareaId = gbTareaUi.getTareaId();
        if(view!=null)view.setFechaTarea(horaEntrega);
    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        setData();
        setupRecursos();
    }

    private void setupRecursos() {
        List<RecursosUI> recursosUIS = getRecuros.execute(tareaId);
        if(view!=null)view.showListRecursos(recursosUIS);
    }

    @Override
    public void onDestroyView() {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onClickDownload(RepositorioFileUi repositorioFileUi) {
        handler.execute(dowloadImageUseCase, new DowloadImageUseCase.RequestValues(repositorioFileUi),
                new UseCase.UseCaseCallback<UseCase.ResponseValue>() {
                    @Override
                    public void onSuccess(UseCase.ResponseValue response) {
                        if(response instanceof DowloadImageUseCase.ResponseProgressValue){
                            DowloadImageUseCase.ResponseProgressValue responseProgressValue = (DowloadImageUseCase.ResponseProgressValue) response;
                            if(view!=null)view.setUpdateProgress(responseProgressValue.getRepositorioFileUi(), responseProgressValue.getCount());
                            Log.d(getTag(),":( :" + repositorioFileUi.getNombreArchivo() +" = " + responseProgressValue.getRepositorioFileUi().getNombreArchivo());
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
                                        Log.d(getTag(),"error al actualizar archivoId: " + repositorioFileUi.getArchivoId()+ " con el pathLocal:" + responseValue.getRepositorioFileUi().getPath());
                                        if(view!=null)view.setUpdate(responseValue.getRepositorioFileUi());
                                    }
                                }
                            });

                            Log.d(getTag(),"pathLocal:" + responseValue.getRepositorioFileUi().getPath());
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
                if(TextUtils.isEmpty(repositorioFileUi.getPath())){
                    dowloadYoutube.execute(repositorioFileUi);
                }else {
                    if (view != null) view.leerArchivo(repositorioFileUi.getPath());
                }
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

    private void saveRegistorRecursos(RepositorioFileUi repositorioFileUi,UpdateSuccesDowloadArchivo.Callback callback ) {
        updateSuccesDowloadArchivo.execute(new UpdateSuccesDowloadArchivo.Request(repositorioFileUi.getArchivoId(), repositorioFileUi.getPath()), callback);
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
}
