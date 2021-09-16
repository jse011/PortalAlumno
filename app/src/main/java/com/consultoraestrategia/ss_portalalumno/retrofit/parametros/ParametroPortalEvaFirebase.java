package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

public class ParametroPortalEvaFirebase extends ApiRetrofit.Parameters {
    @SerializedName("vint_SilaboEventoId")
    private int silaboEventoId;
    @SerializedName("vint_TipoPeriodoId")
    private int tipoPeriodoId;
    @SerializedName("vint_UnidadAprendizajeid")
    private int unidadAprendizajeid;
    @SerializedName("vint_SesionAprendizajeId")
    private int sesionAprendizajeId;

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public int getTipoPeriodoId() {
        return tipoPeriodoId;
    }

    public void setTipoPeriodoId(int tipoPeriodoId) {
        this.tipoPeriodoId = tipoPeriodoId;
    }

    public int getUnidadAprendizajeid() {
        return unidadAprendizajeid;
    }

    public void setUnidadAprendizajeid(int unidadAprendizajeid) {
        this.unidadAprendizajeid = unidadAprendizajeid;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }
}
