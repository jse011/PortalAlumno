package com.consultoraestrategia.ss_portalalumno.evidencia;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.EvidenciaSesionUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.DeleteArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.EntregarSesEvidenciaFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.GetEvidenciaSesArchivosUi;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.IsEntregado;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UpdateEvidenciaSesion;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UploadArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.evidencia.usecase.UploadLinkFB;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.StorageCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.util.DriveUrlParser;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class EvidenciaPresenterImpl extends BaseFragmentPresenterImpl<EvidenciaView> implements EvidenciaPresenter {
    private GetEvidenciaSesArchivosUi getEvidenciaSesArchivosUi;
    private UpdateEvidenciaSesion updateEvidenciaSesion;
    private IsEntregado isEntregado;
    private EntregarSesEvidenciaFB entregarSesEvidenciaFB;
    private UploadArchivoStorageFB uploadArchivoStorageFB;
    private DeleteArchivoStorageFB deleteArchivoStorageFB;
    private UploadLinkFB uploadLinkFB;
    private Online online;
    private int sesionAprendizajeId;
    private int cargaCusoId;
    private String color1;
    private String color2;
    private String color3;
    private List<ArchivoSesEvidenciaUi> archivoSesEvidenciaUiList = new ArrayList<>();
    private HashMap<ArchivoSesEvidenciaUi,StorageCancel> storageCancelList = new HashMap<>();
    private boolean disabledEntregado;
    private boolean entragadoAlumno;

    public EvidenciaPresenterImpl(UseCaseHandler handler, Resources res, GetEvidenciaSesArchivosUi getEvidenciaSesArchivosUi,
                                  UpdateEvidenciaSesion updateEvidenciaSesion, IsEntregado isEntregado, EntregarSesEvidenciaFB entregarSesEvidenciaFB,UploadArchivoStorageFB uploadArchivoStorageFB,
                                  UploadLinkFB uploadLinkFB,DeleteArchivoStorageFB deleteArchivoStorageFB,
                                  Online online) {
        super(handler, res);
        this.getEvidenciaSesArchivosUi = getEvidenciaSesArchivosUi;
        this.updateEvidenciaSesion = updateEvidenciaSesion;
        this.online = online;
        this.isEntregado = isEntregado;
        this.entregarSesEvidenciaFB = entregarSesEvidenciaFB;
        this.uploadArchivoStorageFB = uploadArchivoStorageFB;
        this.deleteArchivoStorageFB =deleteArchivoStorageFB;
        this.uploadLinkFB = uploadLinkFB;
    }

    @Override
    protected String getTag() {
        return "EvidenciaPreseTag";
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        showProgress();
        if(view!=null)view.setTema(color1, color2, color3);
        isEntregado();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    updateEvidenciaSesion();
                }else {
                    hideProgress();
                    getEvidenciaSesArchivosUi();
                }
            }
        });
    }

    private void isEntregado() {
        EvidenciaSesionUi evidenciaSesionUi = isEntregado.execute(sesionAprendizajeId);
        this.entragadoAlumno = evidenciaSesionUi.getEntregaAlumno();
        if(view!=null)view.changeFechaEntrega(evidenciaSesionUi.getEntregaAlumno(), evidenciaSesionUi.isRetrasoEntrega());
        for (ArchivoSesEvidenciaUi archivoSesEvidenciaUi : archivoSesEvidenciaUiList){
            archivoSesEvidenciaUi.setEntregado(evidenciaSesionUi.getEntregaAlumno());
            if(view!=null)view.update(archivoSesEvidenciaUi);
        }
    }

    private void updateEvidenciaSesion() {
        updateEvidenciaSesion.execute(cargaCusoId, sesionAprendizajeId, new UpdateEvidenciaSesion.Callback() {
            @Override
            public void onLoad(boolean success) {
                isEntregado();
                getEvidenciaSesArchivosUi();
                hideProgress();
            }
        });
    }

    private void getEvidenciaSesArchivosUi() {
        archivoSesEvidenciaUiList.clear();
        archivoSesEvidenciaUiList.addAll(getEvidenciaSesArchivosUi.execute(sesionAprendizajeId));
        for (ArchivoSesEvidenciaUi archivoSesEvidenciaUi : archivoSesEvidenciaUiList){
            archivoSesEvidenciaUi.setColor(color1);
        }
        if(view!=null)view.setListEvidencia(archivoSesEvidenciaUiList);
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(gbSesionAprendizajeUi!=null){
            this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        }
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            this.cargaCusoId = gbCursoUi.getCargaCursoId();
            this.color1 = gbCursoUi.getParametroDisenioColor1();
            this.color2 = gbCursoUi.getParametroDisenioColor2();
            this.color3 = gbCursoUi.getParametroDisenioColor3();
        }
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void notifyChangeFragment() {
        if(view!=null)view.showProgress();
        online.online(success -> {
            if(success){
                updateEvidenciaSesion();
            }else {
                if(view!=null)view.hideProgress();
            }
        });
    }

    @Override
    public void onBtnEntregarClicked() {
        boolean state = false;

        for (ArchivoSesEvidenciaUi archivoSesEvidenciaUi : archivoSesEvidenciaUiList){
            if(archivoSesEvidenciaUi.isDisabled()){
                state=true;
                if(view!=null)view.showMessage("Subida de archivos en pausa, para la entrega eliminar (X) los archivos en pausa");
                break;
            }else if(archivoSesEvidenciaUi.getFile()!=null){
                state = true;
                if(archivoSesEvidenciaUi.getState()==1){
                    if(view!=null)view.showMessage("Subida de archivos en progreso");
                }else {
                    if(view!=null)view.showMessage("Subida de archivos en pausa, para la entrega eliminar (X) los archivos en pausa");
                }
                break;
            }
        }

        if(!state){
            if(archivoSesEvidenciaUiList.isEmpty()&&!entragadoAlumno){
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

                            for (ArchivoSesEvidenciaUi archivoSesEvidenciaUi : archivoSesEvidenciaUiList){
                                archivoSesEvidenciaUi.setEntregado(true);
                                if(view!=null)view.update(archivoSesEvidenciaUi);
                            }

                            entregarSesEvidenciaFB.execute(cargaCusoId, sesionAprendizajeId, success -> {
                                disabledEntregado =false;
                                isEntregado();
                                hideProgress();
                            });
                        }
                        disabledEntregado = true;
                    }else {
                        hideProgress();
                        if(view!=null)view.showMessage("sin conexión");
                    }

                }
            });

        }
    }

    @Override
    public void onClickOpenArchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        if(evidenciaUi.getFile()==null){
            switch (evidenciaUi.getTipo()){
                case DOCUMENTO:
                case HOJACALCULO:
                case PRESENTACION:
                case IMAGEN:
                case PDF:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupFirbaseEvidenciaDocumento(sesionAprendizajeId, evidenciaUi.getNombre()));
                    if(view!=null)view.showPreviewArchivo();
                    break;
                case LINK:
                case DRIVE:
                case OTROS:
                    if(view!=null)view.showVinculo(evidenciaUi.getPath());
                    break;
                case YOUTUBE:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupYoutube(evidenciaUi.getPath()));
                    if(view!=null)view.showPreviewArchivo();
                    break;
                case VIDEO:
                case AUDIO:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupFirbaseEvidenciaMultimedia(sesionAprendizajeId, evidenciaUi.getNombre()));
                    if(view!=null)view.showPreviewArchivo();
                    break;
            }
        }
    }

    @Override
    public void onClickRemoverchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        StorageCancel storageCancel = storageCancelList.get(evidenciaUi);
        if(storageCancel!=null){
            if(storageCancel.isSuccessful()){
                //Elimininar del firebase
                deleteArchivoStorageFB(evidenciaUi);
            }else {
                storageCancel.onCancel();
                evidenciaUi.setDisabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        evidenciaUi.setDisabled(false);
                        archivoSesEvidenciaUiList.remove(evidenciaUi);
                        if(view!=null)view.remove(evidenciaUi);
                    }
                },2000);
            }

        }else {
            //Elimininar del firebase
            deleteArchivoStorageFB(evidenciaUi);
        }
    }

    private void deleteArchivoStorageFB(ArchivoSesEvidenciaUi evidenciaUi) {
        evidenciaUi.setDisabled(true);
        if(view!=null)view.update(evidenciaUi);
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean online) {
                if(online){
                    if(!TextUtils.isEmpty(evidenciaUi.getId())){
                        deleteArchivoStorageFB.execute(cargaCusoId, sesionAprendizajeId, evidenciaUi, new DeleteArchivoStorageFB.Callback() {
                            @Override
                            public void onLoad(boolean success) {
                                evidenciaUi.setDisabled(false);
                                if(success){
                                    archivoSesEvidenciaUiList.remove(evidenciaUi);
                                    if(view!=null)view.remove(evidenciaUi);
                                }else {
                                    if(view!=null)view.update(evidenciaUi);
                                    if(view!=null)view.showMessage("Acción cancelada");
                                }
                            }
                        });
                    }else {
                        archivoSesEvidenciaUiList.remove(evidenciaUi);
                        evidenciaUi.setDisabled(false);
                        if(view!=null)view.remove(evidenciaUi);
                    }
                }else {
                    evidenciaUi.setDisabled(false);
                    if(view!=null)view.update(evidenciaUi);
                    if(view!=null)view.showMessage("sin conexión");
                }

            }
        });
    }

    @Override
    public void onClickActionArchivo(ArchivoSesEvidenciaUi evidenciaUi) {
        StorageCancel storageCancel = storageCancelList.get(evidenciaUi);
        if(storageCancel!=null){
            if(storageCancel.isPaused()){
                storageCancel.onResume();
                evidenciaUi.setState(1);
            }else if(storageCancel.isInProgress()){
                storageCancel.onPause();
                evidenciaUi.setState(0);
            }
            if(view!=null)view.update(evidenciaUi);
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
                for (String path : pathFiles){
                    try {
                        File file = new File(path);
                        ArchivoSesEvidenciaUi archivoSesEvidenciaUi = new ArchivoSesEvidenciaUi();
                        archivoSesEvidenciaUi.setNombre(file.getName());
                        archivoSesEvidenciaUi.setColor(color1);
                        archivoSesEvidenciaUi.setTipo(TareaArchivoUi.getType(file.getPath()));
                        archivoSesEvidenciaUi.setDescripcion(archivoSesEvidenciaUi.getTipo().getNombre());
                        boolean existe = false;

                        for (ArchivoSesEvidenciaUi item : archivoSesEvidenciaUiList){
                            if(item.getNombre().equals(archivoSesEvidenciaUi.getNombre())){
                                existe = true;
                                break;
                            }
                        }

                        if (existe) {
                            String extencion = UtilsStorage.getMimeExtension(file.getPath());
                            String nombre = UtilsStorage.getNombre(archivoSesEvidenciaUi.getNombre());
                            String _nombre = nombre + "_" + UtilsStorage.makeid(4) + "." + extencion;
                            archivoSesEvidenciaUi.setNombre(_nombre);
                        }

                        archivoSesEvidenciaUi.setFile(file);
                        archivoSesEvidenciaUiList.add(archivoSesEvidenciaUi);
                        if(view!=null)view.addTareaArchivo(archivoSesEvidenciaUi);
                        uploadArchivoStorageFB(archivoSesEvidenciaUi);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void uploadArchivoStorageFB(ArchivoSesEvidenciaUi archivoSesEvidenciaUi) {
        StorageCancel storageCancel = this.uploadArchivoStorageFB.execute(cargaCusoId, sesionAprendizajeId, archivoSesEvidenciaUi, new UploadArchivoStorageFB.Callback() {
            @Override
            public void onChange(ArchivoSesEvidenciaUi tareaArchivoUi) {
                if(view!=null)view.update(tareaArchivoUi);
            }

            @Override
            public void onFinish(boolean success, ArchivoSesEvidenciaUi tareaArchivoUi) {
                if(success){
                    if(view!=null)view.update(tareaArchivoUi);
                }else {
                    archivoSesEvidenciaUiList.remove(tareaArchivoUi);
                    if(view!=null)view.remove(tareaArchivoUi);
                    if(view!=null)view.showMessage("Error al guardar el archivo");
                }
            }

            @Override
            public void onErrorMaxSize() {
                archivoSesEvidenciaUiList.remove(archivoSesEvidenciaUi);
                if(view!=null)view.remove(archivoSesEvidenciaUi);
                if(view!=null)view.showMessage("El archivo es muy grande, el límite es de 38 MB");
            }
        });

        if(storageCancel!=null){
            storageCancelList.put(archivoSesEvidenciaUi,storageCancel);
        }else {
            archivoSesEvidenciaUiList.remove(archivoSesEvidenciaUi);
            if(view!=null)view.remove(archivoSesEvidenciaUi);
        }
    }

    @Override
    public void onResultDoc(Uri uri, String nombre) {
        try {

            ArchivoSesEvidenciaUi archivoSesEvidenciaUi = new ArchivoSesEvidenciaUi();
            archivoSesEvidenciaUi.setNombre(nombre);
            archivoSesEvidenciaUi.setColor(color1);
            archivoSesEvidenciaUi.setTipo(TareaArchivoUi.getType(nombre));
            archivoSesEvidenciaUi.setDescripcion(archivoSesEvidenciaUi.getTipo().getNombre());
            boolean existe = false;

            for (ArchivoSesEvidenciaUi item : archivoSesEvidenciaUiList){
                if(item.getNombre().equals(archivoSesEvidenciaUi.getNombre())){
                    existe = true;
                    break;
                }
            }

            if (existe) {
                String extencion = UtilsStorage.getMimeExtension(nombre);
                String nombre2 = UtilsStorage.getNombre(archivoSesEvidenciaUi.getNombre());
                String _nombre = nombre2 + "_" + UtilsStorage.makeid(4) + "." + extencion;
                archivoSesEvidenciaUi.setNombre(_nombre);
            }

            archivoSesEvidenciaUi.setFile(uri);
            archivoSesEvidenciaUiList.add(archivoSesEvidenciaUi);
            if(view!=null)view.addTareaArchivo(archivoSesEvidenciaUi);
            uploadArchivoStorageFB(archivoSesEvidenciaUi);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClickAddFile() {
        if(view!=null)view.onShowPickDoc();
    }

    @Override
    public void onClickCamera() {
        if(view!=null)view.openCamera(null);
    }

    @Override
    public void onClickAddLink(String descripcion, String vinculo) {
        ArchivoSesEvidenciaUi archivoSesEvidenciaUi = new ArchivoSesEvidenciaUi();
        archivoSesEvidenciaUi.setNombre(descripcion);
        archivoSesEvidenciaUi.setColor(color1);

        String idYoutube = YouTubeUrlParser.getVideoId(vinculo);
        String idDrive = DriveUrlParser.getDocumentId(vinculo);
        if(!TextUtils.isEmpty(idYoutube)){
            archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.YOUTUBE);
        }else if(!TextUtils.isEmpty(idDrive)){
            archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.DRIVE);
        }else {
            archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.LINK);
        }

        //archivoSesEvidenciaUi.setTipo(TareaArchivoUi.Tipo.LINK);
        archivoSesEvidenciaUi.setDescripcion(vinculo);
        archivoSesEvidenciaUi.setPath(vinculo);
        archivoSesEvidenciaUi.setDisabled(true);
        archivoSesEvidenciaUiList.add(archivoSesEvidenciaUi);
        if(view!=null)view.addTareaArchivo(archivoSesEvidenciaUi);
        uploadLinkFB(archivoSesEvidenciaUi);
    }

    private void uploadLinkFB(ArchivoSesEvidenciaUi archivoSesEvidenciaUi) {
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    uploadLinkFB.execute(cargaCusoId, sesionAprendizajeId, archivoSesEvidenciaUi, new UploadLinkFB.Callback() {
                        @Override
                        public void onFinish(boolean success) {
                            if(success){
                                archivoSesEvidenciaUi.setDisabled(false);
                                if(view!=null)view.update(archivoSesEvidenciaUi);
                            }else {
                                archivoSesEvidenciaUi.setDisabled(true);
                                archivoSesEvidenciaUiList.remove(archivoSesEvidenciaUi);
                                if(view!=null)view.remove(archivoSesEvidenciaUi);
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
}
