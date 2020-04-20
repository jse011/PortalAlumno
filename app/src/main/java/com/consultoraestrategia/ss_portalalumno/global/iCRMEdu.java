package com.consultoraestrategia.ss_portalalumno.global;

import android.app.Application;

import com.consultoraestrategia.ss_portalalumno.global.entities.GbCalendarioPerioUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbTareaUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.List;

public class iCRMEdu extends Application {

    public static final VariblesGlobales variblesGlobales = new VariblesGlobales();

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());

    }

    //variables globales
    public static class VariblesGlobales{
        GbCursoUi gbCursoUi;
        GbCalendarioPerioUi gbCalendarioPerioUi;
        GbSesionAprendizajeUi gbSesionAprendizajeUi;
        GbTareaUi gbTareaUi;
        private int anioAcademicoId;
        private int programEducativoId;

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
    }

}
