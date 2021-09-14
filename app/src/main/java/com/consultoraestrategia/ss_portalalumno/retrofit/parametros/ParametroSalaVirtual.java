package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

public class ParametroSalaVirtual extends ApiRetrofit.Parameters {
    @SerializedName("vint_SesionAprendizajeId")
    private int sesionAprendizajeId;
    @SerializedName("vint_EntidadId")
    private int entidadId;
    @SerializedName("vint_GeoreferenciaId")
    private int georeferenciaId;

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }
}
