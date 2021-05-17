package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.GetCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.UpdateFireBasePersona;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.UpdateFireBaseUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoTareaView;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoUnidadView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TabCursoPresenteImpl extends BasePresenterImpl<TabCursoView> implements TabCursoPresenter {

    private GetCalendarioPeriodo getCalendarioPeriodo;
    private UpdateFireBaseUnidadAprendizaje updateFireBaseUnidadAprendizaje;
    private UpdateFireBasePersona updateFireBasePersona;
    private int anioAcademicoId;
    private int programaEducativoId;
    private PeriodoUi calendarioperiodo;
    private List<PeriodoUi> calendarioPeriodoList = new ArrayList<>();
    private int cargaCursoId;
    private int idCalendarioPeriodo;
    private String parametroColor1;
    private String parametroColor2;
    private String parametroColor3;
    private Online online;
    private String nombreCurso;
    private String seccionYPeriodo;
    private String fotoCurso;
    private TabCursoTareaView tabCursoTareaView;
    private TabCursoUnidadView tabCursoUnidadView;
    private boolean finishUpdateUnidadFb;
    private boolean iniciandoApp = true;
    private int silaboEventoId;


    public TabCursoPresenteImpl(UseCaseHandler handler, Resources res, GetCalendarioPeriodo getCalendarioPeriodo, UpdateFireBaseUnidadAprendizaje updateFireBaseUnidadAprendizaje, UpdateFireBasePersona updateFireBasePersona,
                                Online online) {
        super(handler, res);
        this.getCalendarioPeriodo = getCalendarioPeriodo;
        this.updateFireBaseUnidadAprendizaje = updateFireBaseUnidadAprendizaje;
        this.updateFireBasePersona = updateFireBasePersona;
        this.online = online;
    }

    @Override
    protected String getTag() {
        return "TabCursoDocentePresenteV2Impl";
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        iniciandoApp = true;
        setupVaribleGlobal();
        setupCalendarioPerio();
        if(view!=null)view.showTitle(nombreCurso);
        if(view!=null)view.showSubtitle(seccionYPeriodo);
        if(view!=null)view.showAppbarBackground(fotoCurso, parametroColor1);
        if(view!=null)view.changeColorToolbar(parametroColor1);
        if(view!=null)view.changeColorFloatButon(parametroColor2);
        online.online(success -> {
            if(success){
                updateFireBaseUnidadAprendizaje();
                if(view!=null)view.modoOnline();
            }else {
                if(view!=null)view.modoOffline();
            }
        });
    }

    private void updateFireBaseUnidadAprendizaje() {
        finishUpdateUnidadFb = false;
        updateFireBaseUnidadAprendizaje.execute(cargaCursoId, idCalendarioPeriodo, new UpdateFireBaseUnidadAprendizaje.CallBack() {
            @Override
            public void onSucces() {
                finishUpdateUnidadFb = true;
               if(tabCursoUnidadView!=null)tabCursoUnidadView.notifyChangeFragment(finishUpdateUnidadFb);
               if(tabCursoTareaView!=null)tabCursoTareaView.notifyChangeFragment(finishUpdateUnidadFb);
            }

            @Override
            public void onError(String error) {
                finishUpdateUnidadFb = true;
                if(tabCursoUnidadView!=null)tabCursoUnidadView.notifyChangeFragment(finishUpdateUnidadFb);
                if(tabCursoTareaView!=null)tabCursoTareaView.notifyChangeFragment(finishUpdateUnidadFb);
            }
        });
        updateFireBasePersona.execute(cargaCursoId, new UpdateFireBasePersona.CallBack() {
            @Override
            public void onSucces() {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setupCalendarioPerio() {
        PeriodoUi periodoSelected = null;

        List<PeriodoUi> periodoUiList = getCalendarioPeriodo.execute(anioAcademicoId, cargaCursoId, programaEducativoId);
        Log.d(getTag(), "calendario: "+periodoUiList+"");
        this.calendarioPeriodoList.clear();
        this.calendarioPeriodoList.addAll(periodoUiList);
        //#region Buscar calendario periodo con el estado vigente
        for (PeriodoUi periodo : this.calendarioPeriodoList) {
            // idCalendarioPeriodo = periodo.getIdCalendarioPeriodo();
            if (periodo.isVigente()) {
                Log.d(getTag(), "PeriodoUi.Estado.Vigente");
                periodoSelected = periodo;
                break;
            }
        }
        //#endregion

        //#region encontrar un calendario seleccionado
        for (PeriodoUi periodo : this.calendarioPeriodoList) {
            if(periodo.getIdCalendarioPeriodo() == idCalendarioPeriodo){
                periodoSelected = periodo;
            }
            periodo.setStatus(false);
        }
        //#endregion


        int size = this.calendarioPeriodoList.size();

        if (size > 0 && periodoSelected == null)
        {

            List<PeriodoUi> calendarioPeriodoList = new ArrayList<>(this.calendarioPeriodoList);
            Collections.sort(calendarioPeriodoList, new Comparator<PeriodoUi>() {
                public int compare(PeriodoUi o1, PeriodoUi o2) {
                    return new Date(o2.getFechaFin()).compareTo(new Date(o1.getFechaFin()));
                }
            });

            //#region Buscar el calendario en el estado creado proximo a estar vigente
            int count = 0;
            periodoSelected =  calendarioPeriodoList.get(0);
            for (PeriodoUi item_CalendarioPeriodo :  calendarioPeriodoList) {
                if (item_CalendarioPeriodo.getEstado() == PeriodoUi.Estado.Creado)
                {
                    periodoSelected =  item_CalendarioPeriodo;
                    if (count != 0 &&
                            calendarioPeriodoList.get(count - 1).getEstado() == PeriodoUi.Estado.Cerrado)
                    {
                        periodoSelected = calendarioPeriodoList.get(count - 1);
                        break;
                    }
                }

                count++;
            }
            //#endregion
        }

        if(periodoSelected!=null){
            Log.d(getTag(), "periodoSelected "+periodoSelected.getIdPeriodo()+" "+periodoSelected.getEstado());
            periodoSelected.setStatus(true);
            idCalendarioPeriodo = periodoSelected.getIdCalendarioPeriodo();
        }
        changeVaribleGlobaCalendarioPeriodo(periodoSelected);
        if(view!=null)view.showPeriodoList(this.calendarioPeriodoList,parametroColor3);
        Log.d(getTag(), "calendario: "+this.calendarioPeriodoList.size()+"");
    }

    private void changeVaribleGlobaCalendarioPeriodo(PeriodoUi periodoUi){
        GbCalendarioPerioUi gbCalendarioPerioUi = new GbCalendarioPerioUi();
        if(periodoUi!=null){
            gbCalendarioPerioUi.setCalendarioPeriodoId(periodoUi.getIdCalendarioPeriodo());
            gbCalendarioPerioUi.setNombre(periodoUi.getTipoName());
        }
        iCRMEdu.variblesGlobales.setGbCalendarioPerioUi(gbCalendarioPerioUi);
    }

    private void setupVaribleGlobal() {
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        this.nombreCurso = gbCursoUi.getNombre();
        this.seccionYPeriodo = gbCursoUi.getSeccionyperiodo();
        this.fotoCurso = gbCursoUi.getParametroDisenioPath();
        this.anioAcademicoId = iCRMEdu.variblesGlobales.getAnioAcademicoId();
        this.programaEducativoId = iCRMEdu.variblesGlobales.getProgramEducativoId();
        this.cargaCursoId = gbCursoUi.getCargaCursoId();
        this.silaboEventoId = gbCursoUi.getSilaboEventoId();
        this.parametroColor1 = gbCursoUi.getParametroDisenioColor1();
        this.parametroColor2 = gbCursoUi.getParametroDisenioColor2();
        this.parametroColor3 = gbCursoUi.getParametroDisenioColor3();
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onPeriodoSelected(PeriodoUi periodoUi) {

        for (PeriodoUi itemPeriodoUi: calendarioPeriodoList)itemPeriodoUi.setStatus(false);

        periodoUi.setStatus(true);
        if (view != null) view.changePeriodo();
        calendarioperiodo = periodoUi;
        idCalendarioPeriodo=periodoUi.getIdCalendarioPeriodo();
        changeVaribleGlobaCalendarioPeriodo(calendarioperiodo);
        if(tabCursoTareaView!=null)tabCursoTareaView.showProgress();
        if(tabCursoUnidadView!=null)tabCursoUnidadView.showProgress();
        online.online(success -> {
            if(success){
                updateFireBaseUnidadAprendizaje();
                if(view!=null)view.modoOnline();
            }else {
                if(view!=null)view.modoOffline();
                if(tabCursoTareaView!=null)tabCursoTareaView.notifyChangeFragment(finishUpdateUnidadFb);
                if(tabCursoUnidadView!=null)tabCursoUnidadView.notifyChangeFragment(finishUpdateUnidadFb);
            }
        });

    }

    @Override
    public void onVolverCargar() {
        online.restarOnline(success -> {
            if(success){
                updateFireBaseUnidadAprendizaje();
                if(view!=null)view.modoOnline();
            }else {
                if(view!=null)view.showMessage("Sin conexión");
                if(view!=null)view.modoOffline();
            }
        });
    }

    @Override
    public void attachView(TabCursoTareaView tabCursoTareaView) {
       this.tabCursoTareaView = tabCursoTareaView;
       tabCursoTareaView.notifyChangeFragment(finishUpdateUnidadFb);
    }

    @Override
    public void attachView(TabCursoUnidadView tabCursoUnidadView) {
        this.tabCursoUnidadView = tabCursoUnidadView;
         tabCursoUnidadView.notifyChangeFragment(finishUpdateUnidadFb);
    }

    @Override
    public void onTabCursoTareaViewDestroyed() {
        tabCursoTareaView=null;
    }

    @Override
    public void onTabCursoUnidadViewDestroyed() {
        tabCursoUnidadView =null;
    }

    @Override
    public void onDestroy() {
        if(view!=null)view.desconetarAsistenica(silaboEventoId);
        super.onDestroy();
        finishUpdateUnidadFb = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(iniciandoApp){
            online.online(success -> {
                if(success){
                    if(!finishUpdateUnidadFb){
                        updateFireBaseUnidadAprendizaje();
                    }
                    if(view!=null)view.modoOnline();
                }else {
                    if(view!=null)view.modoOffline();
                }
            });
        }
        iniciandoApp = false;

    }
}
