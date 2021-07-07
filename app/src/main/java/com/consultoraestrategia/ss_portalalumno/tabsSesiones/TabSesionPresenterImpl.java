package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import android.content.res.Resources;
import android.os.Bundle;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionActividadView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionColaborativaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionEvidenciaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionInstrumentoView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionPreguntaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseColaborativa;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseInstrumento;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebasePreguntas;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseReunionVirtual;

public class TabSesionPresenterImpl extends BasePresenterImpl<TabSesionView> implements TabSesionPresenter {
    private UpdateFirebaseInstrumento updateFirebaseInstrumento;
    private UpdateFirebasePreguntas updateFirebasePreguntas;
    private UpdateFirebaseColaborativa updateFirebaseColaborativa;
    private UpdateFirebaseReunionVirtual updateFirebaseReunionVirtual;
    private int sesionAprendizajeId;
    private int idCargaCurso;
    private FirebaseCancel preguntasCancel;
    private TabSesionPreguntaView tabSesionPreguntaView;
    private TabSesionInstrumentoView tabSesionInstrumentoView;
    private TabSesionActividadView tabSesionActividadView;
    private String color1;
    private String color2;
    private String color3;
    private String fondo;
    private Online online;
    private TabSesionColaborativaView tabSesionColaborativaView;
    private FirebaseCancel colaborativaCancel;
    private FirebaseCancel reunionVirtualCancel;
    private boolean updateInstrumento;
    private TabSesionEvidenciaView tabSesionEvidenciaView;
    private int silaboEventoId;

    public TabSesionPresenterImpl(UseCaseHandler handler, Resources res, UpdateFirebaseInstrumento updateFirebaseInstrumento, UpdateFirebasePreguntas updateFirebasePreguntas, Online online,
                                  UpdateFirebaseColaborativa updateFirebaseColaborativa, UpdateFirebaseReunionVirtual updateFirebaseReunionVirtual) {
        super(handler, res);
        this.updateFirebaseInstrumento = updateFirebaseInstrumento;
        this.updateFirebasePreguntas = updateFirebasePreguntas;
        this.online = online;
        this.updateFirebaseColaborativa = updateFirebaseColaborativa;
        this.updateFirebaseReunionVirtual = updateFirebaseReunionVirtual;
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupData();
        iCRMEdu.variblesGlobales.setUpdateInstrumento(false);
        online.online(success -> {
            if(success){
                if(view!=null)view.modoOnline();
            }else {
                if(view!=null)view.modoOffline();
            }
        });
    }

    private void setupData() {
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            this.idCargaCurso = gbCursoUi.getCargaCursoId();
            this.silaboEventoId = gbCursoUi.getSilaboEventoId();
            this.color1 = gbCursoUi.getParametroDisenioColor1();
            this.color2 = gbCursoUi.getParametroDisenioColor2();
            this.color3 = gbCursoUi.getParametroDisenioColor3();
            this.fondo = gbCursoUi.getParametroDisenioPath();
        }

        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(view!=null)view.setNombreSesion(gbSesionAprendizajeUi.getNombreApredizaje());
        if(view!=null)view.setNumeroSession(gbSesionAprendizajeUi.getNumero());
        if(view!=null)view.setStatusBarColor(color1);
        String nombre = "Sesión "+gbSesionAprendizajeUi.getNumero();
        if(view!=null)view.setToolbarColor(color1, nombre);
        if(view!=null)view.setTabColor(color3, color1, color2);
        this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
        if(view!=null)view.setDescripcionSesion(color1, gbSesionAprendizajeUi.getNombreApredizaje(), nombre, fondo);
    }

    @Override
    protected String getTag() {
        return "TabSesionPresenterImpl";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void attachView(TabSesionPreguntaView tabSesionPreguntaView) {
        this.tabSesionPreguntaView = tabSesionPreguntaView;
        updateFirebasePreguntas();
    }

    @Override
    public void attachView(TabSesionInstrumentoView tabSesionInstrumentoView) {
        this.tabSesionInstrumentoView = tabSesionInstrumentoView;
        updateFirebaseInstrumento();
    }

    @Override
    public void attachView(TabSesionActividadView tabSesionActividadView) {
        this.tabSesionActividadView = tabSesionActividadView;
    }

    @Override
    public void onTabSesionActividadDestroy() {
        tabSesionActividadView = null;
    }

    @Override
    public void onTabSesionInstrumentoViewDestroy() {
        tabSesionInstrumentoView=null;
    }

    @Override
    public void onTabSesionPreguntaViewDestroy() {
        tabSesionPreguntaView =null;
    }

    @Override
    public void attachView(TabSesionColaborativaView tabSesionColaborativaView) {
        this.tabSesionColaborativaView = tabSesionColaborativaView;
        updateFirebaseColaborativa();
    }

    private void updateFirebaseColaborativa() {
        if(colaborativaCancel!=null)colaborativaCancel.cancel();
        if(reunionVirtualCancel!=null)reunionVirtualCancel.cancel();
        online.online(new Online.Callback() {
            @Override
            public void onLoad(boolean success) {
                if(success) {
                    colaborativaCancel = updateFirebaseColaborativa.execute(sesionAprendizajeId, idCargaCurso, new UpdateFirebaseColaborativa.CallBack() {
                        @Override
                        public void onLoad(boolean success) {
                            if(success){
                                if(tabSesionColaborativaView!=null)tabSesionColaborativaView.changeColaborativaList();
                            }
                        }
                    });

                    reunionVirtualCancel = updateFirebaseReunionVirtual.execute(sesionAprendizajeId, idCargaCurso, new UpdateFirebaseReunionVirtual.CallBack() {
                        @Override
                        public void onLoad(boolean success) {
                            if(success){
                                if(tabSesionColaborativaView!=null)tabSesionColaborativaView.changeReunionVirtualList();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onTabSesionColaborativaViewDestroy() {
        tabSesionColaborativaView = null;
    }

    @Override
    public void onVolverCargar() {
        online.restarOnline(success -> {
            if(success){
                if(view!=null)view.servicePasarAsistencia(silaboEventoId);
                if(view!=null)view.modoOnline();
            }else {
                if(view!=null)view.modoOffline();
                if(view!=null)view.showMessage("Sin conexión");
            }
        });
        updateFirebaseInstrumento();
        updateFirebasePreguntas();
        updateFirebaseColaborativa();
        if(tabSesionActividadView!=null)tabSesionActividadView.changeList();
        if(tabSesionEvidenciaView!=null)tabSesionEvidenciaView.changeList();
    }

    @Override
    public void attachView(TabSesionEvidenciaView tabSesionEvidenciaView) {
        this.tabSesionEvidenciaView = tabSesionEvidenciaView;
    }

    @Override
    public void onTabSesionEvidenciaViewDestroy() {
        tabSesionEvidenciaView = null;
    }


    private void updateFirebaseInstrumento() {
        online.online(success -> {
            if(success){
                this.updateInstrumento = true;
               updateFirebaseInstrumento.execute(sesionAprendizajeId, idCargaCurso, new UpdateFirebaseInstrumento.CallBack() {
                    @Override
                    public void onSucces() {
                        if(tabSesionInstrumentoView!=null)tabSesionInstrumentoView.changeList();
                        if(tabSesionInstrumentoView!=null)tabSesionInstrumentoView.hideProgress2();
                        //if(tabSesionInstrumentoView!=null)tabSesionActividadView.changeList();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });

    }

    private void updateFirebasePreguntas() {
        if(preguntasCancel !=null) preguntasCancel.cancel();
        online.online(success -> {
            if(tabSesionPreguntaView!=null)tabSesionPreguntaView.onInitPreguntaList(success);
            updateFirebasePreguntas.execute(sesionAprendizajeId, idCargaCurso, new UpdateFirebasePreguntas.CallBack() {
                @Override
                public void onPreLoad(FirebaseCancel childfirebaseCancel) {
                    preguntasCancel = childfirebaseCancel;
                }

                @Override
                public void addPregunta(String preguntaPAId) {
                    if(tabSesionPreguntaView!=null)tabSesionPreguntaView.addPregunta(preguntaPAId);
                }

                @Override
                public void updatePregunta(String preguntaPAId) {
                    if(tabSesionPreguntaView!=null)tabSesionPreguntaView.updatePregunta(preguntaPAId);
                }

                @Override
                public void removePregunta(String preguntaPAId) {
                    if(tabSesionPreguntaView!=null)tabSesionPreguntaView.removePregunta(preguntaPAId);
                }

                @Override
                public void updatePreguntaAlumno(String preguntaPAId, int alumnoId) {
                    if(tabSesionPreguntaView!=null)tabSesionPreguntaView.updatePreguntaAlumno(preguntaPAId, alumnoId);
                }
            });
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(preguntasCancel !=null) preguntasCancel.cancel();
        if(colaborativaCancel!=null)colaborativaCancel.cancel();
        if(reunionVirtualCancel!=null)reunionVirtualCancel.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onResume() {
        super.onResume();
        online.restarOnline(success -> {
            if(success){
                if(view!=null)view.modoOnline();
                if(view!=null)view.servicePasarAsistencia(silaboEventoId);
                if(reunionVirtualCancel==null||colaborativaCancel==null){
                    updateFirebaseColaborativa();
                }
                if(preguntasCancel==null){
                    updateFirebasePreguntas();
                }
                if(!this.updateInstrumento){
                    updateFirebaseInstrumento();
                }
            }else {
                if(view!=null)view.modoOffline();
            }
        });

        if(iCRMEdu.variblesGlobales.isUpdateInstrumento()){
            iCRMEdu.variblesGlobales.setUpdateInstrumento(false);
            if(tabSesionInstrumentoView!=null)tabSesionInstrumentoView.showProgress2();
            updateFirebaseInstrumento();
        }
    }


}
