package com.consultoraestrategia.ss_portalalumno.global;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.consultoraestrategia.ss_portalalumno.global.applife.ActivityLifecycleHandler;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

public class iCRMEdu extends Application implements ActivityLifecycleHandler.LifecycleListener {
    public static final String ACTION_START_ALERT_BLOQUEO ="com.consultoraestrategia.ss_portalalumno.global.intent.action.ACTION_START_ALERT_BLOQUEO";

    public static VariblesGlobales variblesGlobales = new VariblesGlobales();
    private static final String TAG = "iCRMEduTAG";
    private ProgressReceiver progressReceiver;
    private List<ICRMEduListener> icrmEduListenerList = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler(this));
        variblesGlobales = new VariblesGlobales().getData(getApplicationContext());
        registerProgressReceiver();
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

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if(variblesGlobales==null)variblesGlobales = new VariblesGlobales().getData(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void registerProgressReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_START_ALERT_BLOQUEO);
        LocalBroadcastManager.getInstance(this).registerReceiver(getProgressReceiver(), filter);
    }
    private ProgressReceiver getProgressReceiver() {
        if (progressReceiver == null)
            progressReceiver = new ProgressReceiver();

        return progressReceiver;
    }
    //variables globales
    public static class VariblesGlobales{
        GbCursoUi gbCursoUi;
        GbCalendarioPerioUi gbCalendarioPerioUi;
        GbSesionAprendizajeUi gbSesionAprendizajeUi;
        GbTareaUi gbTareaUi;
        GbPreview gbPreview;
        private int anioAcademicoId;
        private int programEducativoId;
        private int instrumentoId;
        private boolean habilitarAcceso;
        private boolean bloqueoAcceso;

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

        public GbPreview getGbPreview() {
            return gbPreview;
        }

        public void setGbPreview(GbPreview gbPreview) {
            this.gbPreview = gbPreview;
        }

        public void setHabilitarAcceso(boolean habilitarAcceso) {
            this.habilitarAcceso = habilitarAcceso;
        }

        public boolean getHabilitarAcceso() {
            return habilitarAcceso;
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


        public void setBloqueoAcceso(boolean bloqueoAcceso) {
            this.bloqueoAcceso = bloqueoAcceso;
        }

        public boolean getBloqueoAcceso() {
            return bloqueoAcceso;
        }
    }

    public class ProgressReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ACTION_START_ALERT_BLOQUEO)) {
               for (ICRMEduListener listener : icrmEduListenerList)listener.onChangeBloqueo();
            }
        }
    }

    public void addiCRMEduListener(ICRMEduListener icrmEduListener) {
        icrmEduListenerList.remove(icrmEduListener);
        icrmEduListenerList.add(icrmEduListener);
    }

    public void removeCore2Listener(ICRMEduListener icrmEduListener){
        icrmEduListenerList.remove(icrmEduListener);
    }

    public static iCRMEdu getiCRMEdu(Activity activity){
        return (iCRMEdu)activity.getApplication();
    }
}
