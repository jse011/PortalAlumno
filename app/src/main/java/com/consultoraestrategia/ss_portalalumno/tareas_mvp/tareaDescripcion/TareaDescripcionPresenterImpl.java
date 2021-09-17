package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCase;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.CompressImage;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DeleteArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetArchivoTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetRecuros;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.IsEntragadoTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.EntregarTareaFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFireBaseTareaSesion;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFirebaseBaseTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFirebaseTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UploadArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UploadLinkFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TareaDescripcionPresenterImpl extends BasePresenterImpl<TareasDecripcionView> implements TareaDescripcionPresenter {

    private String tareaId;
    private GetRecuros getRecuros;
    private DowloadImageUseCase dowloadImageUseCase;
    private UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo;
    private MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea;
    private UpdateFirebaseTareaAlumno updateFirebaseTareaAlumno;
    private GetArchivoTareaAlumno getArchivoTareaAlumno;
    private UploadArchivoStorageFB uploadArchivoStorageFB;
    private DeleteArchivoStorageFB deleteArchivoStorageFB;
    private UpdateFirebaseBaseTarea updateFirebaseBaseTarea;
    private EntregarTareaFB entregarTareaFB;
    private CompressImage compressImage;
    private IsEntragadoTareaAlumno isEntragadoTareaAlumno;
    private Online online;
    private String color1;
    private String color2;
    private String color3;
    private List<TareaArchivoUi> tareasUIList = new ArrayList<>();
    private HashMap<TareaArchivoUi,StorageCancel> storageCancelList = new HashMap<>();
    private boolean disabledEntregado;
    private long fechaLimite;
    private String horaEntrega;
    private int cargaCursoId;
    private int calendarioPeriodoId;
    private GetTarea getTarea;
    private UploadLinkFB uploadLinkFB;
    private boolean entragadoAlumno;

    public TareaDescripcionPresenterImpl(UseCaseHandler handler, Resources res, GetRecuros getRecuros, DowloadImageUseCase dowloadImageUseCase, UpdateSuccesDowloadArchivo updateSuccesDowloadArchivo, MoverArchivosAlaCarpetaTarea moverArchivosAlaCarpetaTarea,
                                         UpdateFirebaseTareaAlumno updateFirebaseTareaAlumno, GetArchivoTareaAlumno getArchivoTareaAlumno, Online online, UploadArchivoStorageFB uploadArchivoStorageFB, DeleteArchivoStorageFB deleteArchivoStorageFB,
                                         EntregarTareaFB entregarTareaFB, IsEntragadoTareaAlumno isEntragadoTareaAlumno, GetTarea getTarea, UpdateFirebaseBaseTarea updateFirebaseBaseTarea, UploadLinkFB uploadLinkFB, CompressImage compressImage) {
        super(handler, res);
        this.getRecuros = getRecuros;
        this.dowloadImageUseCase = dowloadImageUseCase;
        this.updateSuccesDowloadArchivo = updateSuccesDowloadArchivo;
        this.moverArchivosAlaCarpetaTarea = moverArchivosAlaCarpetaTarea;
        this.updateFirebaseTareaAlumno = updateFirebaseTareaAlumno;
        this.getArchivoTareaAlumno = getArchivoTareaAlumno;
        this.online = online;
        this.uploadArchivoStorageFB = uploadArchivoStorageFB;
        this.deleteArchivoStorageFB = deleteArchivoStorageFB;
        this.entregarTareaFB = entregarTareaFB;
        this.isEntragadoTareaAlumno = isEntragadoTareaAlumno;
        this.getTarea = getTarea;
        this.updateFirebaseBaseTarea = updateFirebaseBaseTarea;
        this.uploadLinkFB = uploadLinkFB;
        this.compressImage = compressImage;
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
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            this.color1 = gbCursoUi.getParametroDisenioColor1();
            this.color2 = gbCursoUi.getParametroDisenioColor2();
            this.color3 = gbCursoUi.getParametroDisenioColor3();
            this.cargaCursoId = gbCursoUi.getCargaCursoId();

        }
        GbCalendarioPerioUi gbCalendarioPerioUi = iCRMEdu.variblesGlobales.getGbCalendarioPerioUi();
        if(gbCalendarioPerioUi!=null){
            this.calendarioPeriodoId = gbCalendarioPerioUi.getCalendarioPeriodoId();
        }
        if(view!=null)view.changeTema(color1, color2, color3);
        if(view!=null)view.setNombreTarea(gbTareaUi.getNombre());
        if(view!=null)view.setDescripcionTarea(gbTareaUi.getDescripicion());
        this.fechaLimite = gbTareaUi.getFechaLimite();
        this.horaEntrega = gbTareaUi.getHoraEntrega();

        String horaEntrega = "";
        if (!(this.fechaLimite == 0D)) {
            String hora = UtilsPortalAlumno.changeTime12Hour(gbTareaUi.getHoraEntrega());
            String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(gbTareaUi.getFechaLimite());
            if(!TextUtils.isEmpty(hora)){
                fecha_entrega = fecha_entrega + " " + hora;
            }
            horaEntrega = fecha_entrega;
        } else {
            horaEntrega = "Sin límite de entrega";
        }
        if(view!=null)view.setFechaTarea(horaEntrega);
        this.tareaId = gbTareaUi.getTareaId();

    }

    @Override
    public void onActivityCreated() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        setData();
        if(view!=null)view.showProgress();
        if(view!=null)view.showProgress2();
        isEntragadoTareaAlumno();
        online.restarOnline(success -> {
            if(success){
                if(view!=null)view.modoOnline();
                isEntragadoTareaAlumno();
                updateFirebaseTareaAlumno();
                updateFirebaseTarea();
            }else {
                getTarea();
                getArchivoTareaAlumno();
                getRecursos();
                if(view!=null)view.hideProgress();
                if(view!=null)view.hideProgress2();
                if(view!=null)view.modoOffline();
            }
        });

    }

    private void getTarea() {
        TareasUI tareasUI = getTarea.execute(tareaId);
        if(view!=null)view.setNombreTarea(tareasUI.getTituloTarea());
        if(view!=null)view.setDescripcionTarea(tareasUI.getDescripcion());
        this.fechaLimite = tareasUI.getFechaLimite();
        this.horaEntrega = tareasUI.getHoraEntrega();

        String horaEntrega = "";
        if (!(this.fechaLimite == 0D)) {
            String hora = UtilsPortalAlumno.changeTime12Hour(tareasUI.getHoraEntrega());
            String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(tareasUI.getFechaLimite());
            if(!TextUtils.isEmpty(hora)){
                fecha_entrega = fecha_entrega + " " + hora;
            }
            horaEntrega = fecha_entrega;
        } else {
            horaEntrega = "Sin límite de entrega";
        }
        if(view!=null)view.setFechaTarea(horaEntrega);
    }

    private void updateFirebaseTarea() {
        updateFirebaseBaseTarea.execute(cargaCursoId, calendarioPeriodoId, tareaId, new UpdateFireBaseTareaSesion.CallBack() {
            @Override
            public void onSucces() {
                getTarea();
                getRecursos();
                if(view!=null)view.hideProgress2();
            }

            @Override
            public void onError(String error) {
                getTarea();
                getRecursos();
                if(view!=null)view.hideProgress2();
            }
        });
    }

    private void isEntragadoTareaAlumno() {
        TareasUI tareasUI = isEntragadoTareaAlumno.execute(tareaId);
        entragadoAlumno = tareasUI.getEntregaAlumno();
        if(view!=null)view.changeFechaEntrega(tareasUI.getEntregaAlumno(), tareasUI.isRetrasoEntrega());
        for (TareaArchivoUi tareaArchivoUi : tareasUIList){
            tareaArchivoUi.setEntregado(tareasUI.getEntregaAlumno());
            if(view!=null)view.update(tareaArchivoUi);
        }
    }

    private void getArchivoTareaAlumno() {
        this.tareasUIList.clear();
        for (TareaArchivoUi tareaArchivoUi : getArchivoTareaAlumno.execute(tareaId)){
            tareaArchivoUi.setColor(color1);
            this.tareasUIList.add(tareaArchivoUi);
        }

        if(view!=null)view.showListaTareaAlumno(tareasUIList);
    }

    private void updateFirebaseTareaAlumno() {
        updateFirebaseTareaAlumno.execute(tareaId, new UpdateFirebaseTareaAlumno.CallBack() {
            @Override
            public void onSucces() {
                if(view!=null)view.hideProgress();
                isEntragadoTareaAlumno();
                getArchivoTareaAlumno();
            }

            @Override
            public void onError() {
                if(view!=null)view.hideProgress();
            }
        });
    }

    private void getRecursos() {
        List<RecursosUI> recursosUIS = getRecuros.execute(tareaId);
        for (RecursosUI recursosUI : recursosUIS){
            recursosUI.setColor1(color1);
            recursosUI.setColor2(color2);
            recursosUI.setColor3(color3);
        }
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
                    if(view!=null)view.showYoutube(repositorioFileUi.getUrl());
                }else {
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveMultimedia(repositorioFileUi.getDriveId(), repositorioFileUi.getNombreRecurso()));
                    if(view!=null)view.showMultimediaPlayer();
                }
                break;
            case MATERIALES:
                break;
            case DOCUMENTO:
            case HOJA_CALCULO:
            case DIAPOSITIVA:
            case IMAGEN:
            case PDF:
                iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveDocumento(repositorioFileUi.getDriveId(), repositorioFileUi.getNombreRecurso()));
                if(view!=null)view.showPreviewArchivo();
                break;
            case VIDEO:
            case AUDIO:
                iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveMultimedia(repositorioFileUi.getDriveId(), repositorioFileUi.getNombreRecurso()));
                if(view!=null)view.showMultimediaPlayer();
                break;
        }
    }

    @Override
    public void onResultCamara(ArrayList<String> pathFiles) {

        boolean fotoRecienTomada = false;
        if(pathFiles!=null){
            for (String pathFile : pathFiles){
                File file = new File(pathFile);
                String extencion = UtilsStorage.getMimeExtension(pathFile);
                if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("jpg") || extencion.toLowerCase().contains("jpeg") || extencion.toLowerCase().contains("png"))) {
                    //It's an image"
                    long ms = file.lastModified();
                    if(new Date().getTime()-ms<1000){
                        fotoRecienTomada = true;
                    }
                }
            }
        }
        if(fotoRecienTomada){
            if(view!=null)view.openCamera(pathFiles);
        }else {
            if(pathFiles!=null){
                List<File> fileList = new ArrayList<>();
                for (String path : pathFiles){
                    try {
                        fileList.add(new File(path));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if(view!=null){
                    compressImage.execute(fileList, new CompressImage.Callback() {
                        @Override
                        public void onload(boolean success, List<File> mFileList) {
                            if(!success){
                                Log.d(getTag(), "Error Compress");
                            }
                            for (File file : mFileList){
                                convertFileTareArchivoUi(file);
                            }
                        }
                    });
                }
            }
        }
    }

    private void convertFileTareArchivoUi(File file) {
        TareaArchivoUi tareaArchivoUi = new TareaArchivoUi();
        tareaArchivoUi.setNombre(file.getName());
        tareaArchivoUi.setColor(color1);
        tareaArchivoUi.setTipo(TareaArchivoUi.getType(file.getPath()));
        tareaArchivoUi.setDescripcion(tareaArchivoUi.getTipo().getNombre());
        boolean existe = false;

        for (TareaArchivoUi item : tareasUIList){
            if(item.getNombre().equals(tareaArchivoUi.getNombre())){
                existe = true;
                break;
            }
        }

        if (existe) {
            String extencion = UtilsStorage.getMimeExtension(file.getPath());
            String nombre = UtilsStorage.getNombre(tareaArchivoUi.getNombre());
            String _nombre = nombre + "_" + UtilsStorage.makeid(4) + "." + extencion;
            tareaArchivoUi.setNombre(_nombre);
        }

        tareaArchivoUi.setFile(file);
        tareasUIList.add(tareaArchivoUi);
        if(view!=null)view.addTareaArchivo(tareaArchivoUi);
        uploadArchivoStorageFB(tareaArchivoUi);
    }

    private void uploadArchivoStorageFB(TareaArchivoUi tareaArchivoUi) {
        StorageCancel storageCancel = this.uploadArchivoStorageFB.execute(tareaId, tareaArchivoUi, new UploadArchivoStorageFB.Callback() {
            @Override
            public void onChange(TareaArchivoUi tareaArchivoUi) {
                if(view!=null)view.update(tareaArchivoUi);
            }

            @Override
            public void onFinish(boolean success, TareaArchivoUi tareaArchivoUi) {
                if(success){
                    if(view!=null)view.update(tareaArchivoUi);
                }else {
                    tareasUIList.remove(tareaArchivoUi);
                    if(view!=null)view.remove(tareaArchivoUi);
                    if(view!=null)view.showMessage("Error al guardar el archivo");
                }
            }

            @Override
            public void onErrorMaxSize() {
                tareasUIList.remove(tareaArchivoUi);
                if(view!=null)view.remove(tareaArchivoUi);
                if(view!=null)view.showMessage("El archivo es muy grande, el límite es de 38 MB");
            }
        });

       if(storageCancel!=null){
           storageCancelList.put(tareaArchivoUi,storageCancel);
       }else {
           tareasUIList.remove(tareaArchivoUi);
           if(view!=null)view.remove(tareaArchivoUi);
       }
    }

    @Override
    public void onClickCamera() {
        if(view!=null)view.openCamera(null);
    }

    @Override
    public void onClickActionTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        StorageCancel storageCancel = storageCancelList.get(tareaArchivoUi);
        if(storageCancel!=null){
            if(storageCancel.isPaused()){
                storageCancel.onResume();
                tareaArchivoUi.setState(1);
            }else if(storageCancel.isInProgress()){
                storageCancel.onPause();
                tareaArchivoUi.setState(0);
            }
            if(view!=null)view.update(tareaArchivoUi);
        }
    }

    @Override
    public void onClickRemoveTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        StorageCancel storageCancel = storageCancelList.get(tareaArchivoUi);
        if(storageCancel!=null){
            if(storageCancel.isSuccessful()){
               //Elimininar del firebase
                deleteArchivoStorageFB(tareaArchivoUi);
            }else {
                storageCancel.onCancel();
                tareaArchivoUi.setDisabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tareaArchivoUi.setDisabled(false);
                        tareasUIList.remove(tareaArchivoUi);
                        if(view!=null)view.remove(tareaArchivoUi);
                    }
                },2000);
            }

        }else {
            //Elimininar del firebase
            deleteArchivoStorageFB(tareaArchivoUi);
        }
    }

    @Override
    public void onBtnEntregarClicked() {
        boolean state = false;
        for (TareaArchivoUi tareaArchivoUi : new ArrayList<>(tareasUIList)){
            if(tareaArchivoUi.isDisabled()){
                state=true;
                if(view!=null)view.showMessageTop("Subida de archivos en pausa, para la entrega eliminar (X) los archivos en pausa");
                break;
            }else if(tareaArchivoUi.getFile()!=null){
                state = true;
                if(tareaArchivoUi.getState()==1){
                    if(view!=null)view.showMessageTop("Subida de archivos en progreso");
                }else {
                    if(view!=null)view.showMessageTop("Subida de archivos en pausa, para la entrega eliminar (X) los archivos en pausa");
                }
                break;
            }
        }

        if(!state){
            if(tareasUIList.isEmpty()&&!entragadoAlumno){
                if(view!=null)view.showMessage("Adjunte algún archivo antes de la entrega");
                return;
            }
            showProgress();
            online.online(new Online.Callback() {
                @Override
                public void onLoad(boolean online) {
                    if(online){
                        if(!disabledEntregado){
                            if(view!=null)view.diabledButtons();

                            for (TareaArchivoUi tareaArchivoUi : new ArrayList<>(tareasUIList)){
                                tareaArchivoUi.setEntregado(true);
                                if(view!=null)view.update(tareaArchivoUi);
                            }

                            entregarTareaFB.execute(tareaId, success -> {
                                disabledEntregado =false;
                                isEntragadoTareaAlumno();
                                hideProgress();
                            });
                        }
                        disabledEntregado = true;
                    }else {
                        hideProgress();
                        if(view!=null)view.modoOffline();
                        if(view!=null)view.showMessageTop("sin conexión");
                    }

                }
            });

        }

    }

    @Override
    public void onClickOpenTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        if(tareaArchivoUi.getFile()==null){
            switch (tareaArchivoUi.getTipo()){
                case DOCUMENTO:
                case HOJACALCULO:
                case PRESENTACION:
                case IMAGEN:
                case PDF:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupFirbaseTareaDocumento(tareaId, tareaArchivoUi.getNombre()));
                    if(view!=null)view.showPreviewArchivo();
                    break;
                case LINK:
                case DRIVE:
                case OTROS:
                    if(view!=null)view.showVinculo(tareaArchivoUi.getPath());
                    break;
                case YOUTUBE:
                    if(view!=null)view.showYoutube(tareaArchivoUi.getPath());
                    break;
                case VIDEO:
                case AUDIO:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupFirbaseTareaMultimedia(tareaId, tareaArchivoUi.getNombre()));
                    if(view!=null)view.showMultimediaPlayer();
                    break;
            }
        }

    }

    @Override
    public void onClickMsgError() {
        online.restarOnline(success -> {
            if(success){
                if(view!=null)view.modoOnline();
                updateFirebaseTareaAlumno();
            }else {
                getArchivoTareaAlumno();
                isEntragadoTareaAlumno();
                if(view!=null)view.showMessage("Sin conexión");
                if(view!=null)view.hideProgress();
                if(view!=null)view.modoOffline();
            }
        });
    }

    @Override
    public void onClickAddFile() {
        if(view!=null)view.onShowPickDoc();
    }

    @Override
    public void onResultDoc(Uri uri, String nombre) {
        try {

            TareaArchivoUi tareaArchivoUi = new TareaArchivoUi();
            tareaArchivoUi.setNombre(nombre);
            tareaArchivoUi.setColor(color1);
            tareaArchivoUi.setTipo(TareaArchivoUi.getType(nombre));
            tareaArchivoUi.setDescripcion(tareaArchivoUi.getTipo().getNombre());
            boolean existe = false;

            for (TareaArchivoUi item : tareasUIList){
                if(item.getNombre().equals(tareaArchivoUi.getNombre())){
                    existe = true;
                    break;
                }
            }

            if (existe) {
                String extencion = UtilsStorage.getMimeExtension(nombre);
                String nombre2 = UtilsStorage.getNombre(tareaArchivoUi.getNombre());
                String _nombre = nombre2 + "_" + UtilsStorage.makeid(4) + "." + extencion;
                tareaArchivoUi.setNombre(_nombre);
            }

            tareaArchivoUi.setFile(uri);
            tareasUIList.add(tareaArchivoUi);
            if(view!=null){
                view.addTareaArchivo(tareaArchivoUi);
                uploadArchivoStorageFB(tareaArchivoUi);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClickAddLink(String descripcion, String vinculo) {
        TareaArchivoUi tareaArchivoUi = new TareaArchivoUi();
        tareaArchivoUi.setNombre(descripcion);
        tareaArchivoUi.setColor(color1);
        tareaArchivoUi.setTipo(TareaArchivoUi.Tipo.LINK);
        tareaArchivoUi.setDescripcion(vinculo);
        tareaArchivoUi.setPath(vinculo);
        tareaArchivoUi.setDisabled(true);
        tareasUIList.add(tareaArchivoUi);
        if(view!=null)view.addTareaArchivo(tareaArchivoUi);
        uploadLinkFB(tareaArchivoUi);
    }

    @Override
    public void onClickOpenLink(TareaArchivoUi tareaArchivoUi, String clickedLink) {
        if(view!=null)view.showVinculo(clickedLink);
    }

    @Override
    public void onClickOpenLinkArchivo(RepositorioFileUi repositorioFileUi, String clickedLink) {
        if(view!=null)view.showVinculo(clickedLink);
    }

    private void uploadLinkFB(TareaArchivoUi tareaArchivoUi) {
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    uploadLinkFB.execute(tareaId, tareaArchivoUi, new UploadLinkFB.Callback() {
                        @Override
                        public void onFinish(boolean success) {
                            if(success){
                                tareaArchivoUi.setDisabled(false);
                                if(view!=null)view.update(tareaArchivoUi);
                            }else {
                                tareaArchivoUi.setDisabled(true);
                                tareasUIList.remove(tareaArchivoUi);
                                if(view!=null)view.remove(tareaArchivoUi);
                                if(view!=null)view.showMessage("Error al guardar el vínculo");
                            }
                        }
                    });
                }else {
                    if(view!=null)view.showMessage("Sin conexión");
                }
            }
        });

    }

    private void deleteArchivoStorageFB(TareaArchivoUi tareaArchivoUi) {
        tareaArchivoUi.setDisabled(true);
        if(view!=null)view.update(tareaArchivoUi);
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean online) {
                if(online){
                    if(!TextUtils.isEmpty(tareaArchivoUi.getId())){
                        deleteArchivoStorageFB.execute(tareaId, tareaArchivoUi, new DeleteArchivoStorageFB.Callback() {
                            @Override
                            public void onLoad(boolean success) {
                                tareaArchivoUi.setDisabled(false);
                                if(success){
                                    tareasUIList.remove(tareaArchivoUi);
                                    if(view!=null)view.remove(tareaArchivoUi);
                                }else {
                                    if(view!=null)view.update(tareaArchivoUi);
                                    if(view!=null)view.showMessage("Acción cancelada");
                                }
                            }
                        });
                    }else {
                        tareasUIList.remove(tareaArchivoUi);
                        tareaArchivoUi.setDisabled(false);
                        if(view!=null)view.remove(tareaArchivoUi);
                    }
                }else {
                    tareaArchivoUi.setDisabled(false);
                    if(view!=null)view.update(tareaArchivoUi);
                    if(view!=null)view.modoOffline();
                    if(view!=null)view.showMessageTop("sin conexión");
                }

            }
        });
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
