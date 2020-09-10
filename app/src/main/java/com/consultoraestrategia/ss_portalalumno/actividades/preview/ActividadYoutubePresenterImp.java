package com.consultoraestrategia.ss_portalalumno.actividades.preview;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetActividadesList;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

import java.util.ArrayList;
import java.util.List;

public class ActividadYoutubePresenterImp extends BaseFragmentPresenterImpl<ActividadYoutubeView> implements ActividadYoutubePresenter {
    private GetActividadesList getActividadesList;
    private int cargaCursoId;
    private String color3;
    private String color2;
    private String color1;
    private int sesionAprendizajeId;
    private String recursoId;
    private int actividadId;

    public ActividadYoutubePresenterImp(UseCaseHandler handler, Resources res, GetActividadesList getActividadesList) {
        super(handler, res);
        this.getActividadesList = getActividadesList;
    }

    @Override
    protected String getTag() {
        return "ActividadYoutubePresenterImp";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            this.cargaCursoId = gbCursoUi.getCargaCursoId();
            this.color1 = gbCursoUi.getParametroDisenioColor1();
            this.color2 = gbCursoUi.getParametroDisenioColor2();
            this.color3 = gbCursoUi.getParametroDisenioColor3();
        }
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        if(gbSesionAprendizajeUi!=null){
            this.sesionAprendizajeId = gbSesionAprendizajeUi.getSesionAprendizajeId();
            this.recursoId = gbSesionAprendizajeUi.getRecursoId();
            this.actividadId = gbSesionAprendizajeUi.getActividadId();
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        GetActividadesList.ResponseValue responseValue = getActividadesList.execute(new GetActividadesList.RequestValues(cargaCursoId, sesionAprendizajeId));
        if(responseValue!=null){

            ActividadesUi actividadesUi = null;
            int position = 0;
            RecursosUi recursosUi = null;
            for (ActividadesUi item : responseValue.getActividadesUiList()){
                if(item.getId()==actividadId){
                    actividadesUi = item;
                    for (RecursosUi itemRecursosUi : item.getRecursosUiList()){
                        if(itemRecursosUi.getRecursoId().equals(recursoId)){
                            recursosUi = itemRecursosUi;
                            break;
                        }
                    }
                    item.setRecursosUiList(new ArrayList<>());
                    for (SubRecursosUi subRecursosUi : item.getSubRecursosUiList()){
                        subRecursosUi.setColor(color1);
                        for (RecursosUi itemRecursosUi: subRecursosUi.getRecursosUiList()){
                            if(itemRecursosUi.getRecursoId().equals(recursoId)){
                                recursosUi = itemRecursosUi;
                                break;
                            }
                        }
                        subRecursosUi.setRecursosUiList(new ArrayList<>());
                    }
                    break;
                }
                position++;
            }
            if(recursosUi!=null){
                if(!TextUtils.isEmpty(recursosUi.getDriveId())){
                    //if(view!=null)view.showButonsDrive();
                    String url = "https://drive.google.com/file/d/"+recursosUi.getDriveId();
                    if(view!=null)view.setDescripcionVideo(recursosUi.getNombreRecurso(),url, color2);
                }else {
                    if(view!=null)view.setDescripcionVideo(recursosUi.getNombreRecurso(), recursosUi.getUrl(), color2);
                }
            }

            if(actividadesUi!=null){
                actividadesUi.setColor1(color1);
                actividadesUi.setColor2(color2);
                actividadesUi.setColor3(color3);
                if(view!=null)view.setListActividad(actividadesUi, position);
            }

        }
    }


}
