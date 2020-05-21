package com.consultoraestrategia.ss_portalalumno.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.global.applife.ActivityLifecycleHandler;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;

public class iCRMEdu extends Application implements ActivityLifecycleHandler.LifecycleListener {

    public static VariblesGlobales variblesGlobales = new VariblesGlobales();
    private static final String TAG = "iCRMEduTAG";

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler(this));
        variblesGlobales = variblesGlobales.getData(getApplicationContext());

    }

    @Override
    public void onApplicationStopped() {
    }

    @Override
    public void onApplicationStarted() {
    }

    @Override
    public void onApplicationPaused() {
        if(variblesGlobales!=null)variblesGlobales.saveData(getApplicationContext());
    }

    @Override
    public void onApplicationResumed() {
    }


    //variables globales
    public static class VariblesGlobales{
        GbCursoUi gbCursoUi;
        GbCalendarioPerioUi gbCalendarioPerioUi;
        GbSesionAprendizajeUi gbSesionAprendizajeUi;
        GbTareaUi gbTareaUi;
        private int anioAcademicoId;
        private int programEducativoId;
        private int instrumentoId;

        public GbCursoUi getGbCursoUi() {
            return gbCursoUi;
        }

        public void setGbCursoUi(GbCursoUi gbCursoUi) {
            this.gbCursoUi = gbCursoUi;
        }

        public int getAnioAcademicoId() {
            return anioAcademicoId;
        }

        public void setAnioAcademicoId(int anioAcademicoId) {
            this.anioAcademicoId = anioAcademicoId;
        }

        public int getProgramEducativoId() {
            return programEducativoId;
        }

        public void setProgramEducativoId(int programEducativoId) {
            this.programEducativoId = programEducativoId;
        }

        public GbCalendarioPerioUi getGbCalendarioPerioUi() {
            return gbCalendarioPerioUi;
        }

        public void setGbCalendarioPerioUi(GbCalendarioPerioUi gbCalendarioPerioUi) {
            this.gbCalendarioPerioUi = gbCalendarioPerioUi;
        }

        public GbSesionAprendizajeUi getGbSesionAprendizajeUi() {
            return gbSesionAprendizajeUi;
        }

        public void setGbSesionAprendizajeUi(GbSesionAprendizajeUi gbSesionAprendizajeUi) {
            this.gbSesionAprendizajeUi = gbSesionAprendizajeUi;
        }

        public GbTareaUi getGbTareaUi() {
            return gbTareaUi;
        }

        public void setGbTareaUi(GbTareaUi gbTareaUi) {
            this.gbTareaUi = gbTareaUi;
        }

        public void setInstrumentoId(int instrumentoId) {
            this.instrumentoId = instrumentoId;
        }

        public int getInstrumentoId() {
            return instrumentoId;
        }

        public void saveData(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences VariblesGlobales", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(this);
            editor.putString("VariblesGlobales", json);
            editor.apply();

        }
        public VariblesGlobales getData(Context context) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences VariblesGlobales", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("VariblesGlobales", null);
            VariblesGlobales variblesGlobales = gson.fromJson(json, this.getClass());
            if (variblesGlobales == null) {
                variblesGlobales = new VariblesGlobales();
            }
            return variblesGlobales;

        }



    }


}
