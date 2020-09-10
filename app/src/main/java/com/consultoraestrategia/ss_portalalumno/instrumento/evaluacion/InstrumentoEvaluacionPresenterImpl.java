package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo.TipoEvaluacionView;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumento;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.SaveFirebasInstrumento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrumentoEvaluacionPresenterImpl extends BasePresenterImpl<InstrumentoEvaluacionView> implements InstrumentoEvaluacionPresenter {
    private GetInstrumento getInstrumento;
    private SaveFirebasInstrumento saveFirebasInstrumento;
    private int sessionAprendizajeId;
    private int instrumentoEvalId;
    private List<VariableUi> variableUiList = new ArrayList<>();
    private HashMap<VariableUi, TipoEvaluacionView> tipoEvaluacionViewList = new HashMap<>();
    private TipoEvaluacionView tipoEvaluacionView;
    private boolean initFragment;
    private VariableUi selectedVariableUi;
    private int position = 0;
    private int cargaCursoId;
    private int cantpregResueltas;
    private boolean offline;
    private String color1;
    private String color2;
    private String color3;

    public InstrumentoEvaluacionPresenterImpl(UseCaseHandler handler, Resources res,
                                              GetInstrumento getInstrumento, SaveFirebasInstrumento saveFirebasInstrumento) {
        super(handler, res);
        this.getInstrumento = getInstrumento;
        this.saveFirebasInstrumento = saveFirebasInstrumento;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getInstrumento();
    }

    private void getInstrumento() {
        InstrumentoUi instrumentoUi = getInstrumento.execut(instrumentoEvalId);
        variableUiList.clear();
        int count = 0;
        cantpregResueltas = 0;
        for (VariableUi variableUi : instrumentoUi.getVariables()){
            if(TextUtils.isEmpty(variableUi.getVariableObservadaId())){
                count++;
                variableUi.setPostion(count);
                variableUiList.add(variableUi);
            }else {
                cantpregResueltas++;
            }
        }

        if(view!=null)view.showListPreguntas(variableUiList);
        if(view!=null)view.setMaxProgress(count+cantpregResueltas);
    }

    @Override
    public void setExtras(Bundle extras) {
        super.setExtras(extras);
        setupData();

    }

    private void setupData() {
        GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();
        sessionAprendizajeId =  gbSesionAprendizajeUi!=null?gbSesionAprendizajeUi.getSesionAprendizajeId():0;
        instrumentoEvalId = iCRMEdu.variblesGlobales.getInstrumentoId();
        GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
        if(gbCursoUi!=null){
            cargaCursoId = gbCursoUi.getCargaCursoId();
            color1 = gbCursoUi.getParametroDisenioColor1();
            color2 = gbCursoUi.getParametroDisenioColor2();
            color3 = gbCursoUi.getParametroDisenioColor3();
        }

    }

    @Override
    protected String getTag() {
        return "InstrumentoEvaluacionPresenterImplTAG";
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void attachViewList(HashMap<VariableUi, TipoEvaluacionView> tipoEvaluacionViewList) {
        this.tipoEvaluacionViewList.clear();
        this.tipoEvaluacionViewList.putAll(tipoEvaluacionViewList);
    }

    @Override
    public void attachView(TipoEvaluacionView tipoEvaluacionView) {
        this.tipoEvaluacionView = tipoEvaluacionView;
        int cantidad =  this.tipoEvaluacionViewList.size();
        for (Map.Entry<VariableUi, TipoEvaluacionView> mapFbView: this.tipoEvaluacionViewList.entrySet()){
            if(tipoEvaluacionView.equals(mapFbView.getValue())){
                selectedVariableUi = mapFbView.getKey();
                setupVariable(selectedVariableUi, cantidad);
            }
        }

    }

    private void setupVariable(VariableUi variableUi ,int cantidad) {
        String progress = variableUi.getPostion()+cantpregResueltas + "/" + (cantidad+cantpregResueltas);
        if(tipoEvaluacionView!=null)tipoEvaluacionView.setProgress(progress);
        if(tipoEvaluacionView!=null)tipoEvaluacionView.setTitulo(variableUi.getNombre());
        if(!TextUtils.isEmpty(variableUi.getPath())){
            if(tipoEvaluacionView!=null)tipoEvaluacionView.showImage(variableUi.getPath());
        }else {
            if(tipoEvaluacionView!=null)tipoEvaluacionView.hideImage();
        }
        switch (variableUi.getTipoInputRespuestaId()){
            case 7://Entero
                if(tipoEvaluacionView!=null)tipoEvaluacionView.setInputEntero();
                break;
            case 8://Decimal
                if(tipoEvaluacionView!=null)tipoEvaluacionView.setInputDecimal();
                break;
            default: //9://String
                if(tipoEvaluacionView!=null)tipoEvaluacionView.setInputString();
                break;
        }

        switch (variableUi.getTipoRespuestaId()){
            case 10://Texto

                break;
            case 11://Excluyente
                if(tipoEvaluacionView!=null)tipoEvaluacionView.setListValores(variableUi.getValores());
                break;
            case 12://Check
                if(tipoEvaluacionView!=null)tipoEvaluacionView.setListValores(variableUi.getValores());
                break;
        }

        if(cantidad==variableUi.getPostion())
            if(tipoEvaluacionView!=null)tipoEvaluacionView.changeButtonFinsh("Finalizar");
    }

    @Override
    public void changePage(int position) {
        this.position = position;
        if(offline){
            if(view!=null)view.setProgressPostionOffline(position+cantpregResueltas);
        }else {
            if(view!=null)view.setProgressPostionOnline(position+cantpregResueltas);
        }
    }

    @Override
    public void siguientePregunta(TipoEvaluacionView tipoEvaluacionView, String respuesta) {
        int cantidad =  this.tipoEvaluacionViewList.size();
        for (Map.Entry<VariableUi, TipoEvaluacionView> mapFbView: this.tipoEvaluacionViewList.entrySet()){
            if(tipoEvaluacionView.equals(mapFbView.getValue())){
                VariableUi variableUi = mapFbView.getKey();
                boolean validar = false;
                switch (variableUi.getTipoRespuestaId()){
                    case 10://Texto
                        validar = TextUtils.isEmpty(respuesta);
                        if(validar)if(view!=null)view.showMessage("Debe ingresar su respuesta");
                        break;
                    case 11://Excluyente
                    case 12://Check
                        int count = 0;
                        for (ValorUi valorUi : variableUi.getValores()){
                            if(valorUi.isSelected())count++;
                        }
                        validar = count==0;
                        if(validar)if(view!=null)view.showMessage("Debe seleccionar una opción");
                        break;
                }
                variableUi.setRespuestaActual(respuesta);
                setSaveFirebasInstrumento(variableUi);

                if(!validar){
                    variableUi.setRespuestaActual(respuesta);
                    if(cantidad==variableUi.getPostion()){
                        if(view!=null)view.showProgress();
                        if(view!=null)view.showContenDialog();
                    }else {
                        if(view!=null)view.changePage(position+1);
                    }


                }

            }
        }


    }

    @Override
    public void onOnline() {
        offline = false;
        if(view!=null)view.setProgressPostionOnline(position+cantpregResueltas);
    }

    @Override
    public void onOffline() {
        offline = true;
        if(view!=null)view.setProgressPostionOffline(position+cantpregResueltas);
    }

    private void setSaveFirebasInstrumento(VariableUi variableUi){
        variableUi.setEnviado(false);
        saveFirebasInstrumento.execute(cargaCursoId, sessionAprendizajeId, variableUi, new SaveFirebasInstrumento.CallBack() {
            @Override
            public void onSucces() {
                onOnline();
                int count = 0;
                for (VariableUi item: variableUiList){
                    if(item.getEnviado()){
                        count++;
                    }
                }
                if(count==variableUiList.size()){
                   if(view!=null)view.finalizar();
                }
            }

            @Override
            public void onError(String error) {
                onOffline();
            }
        });
    }



}
