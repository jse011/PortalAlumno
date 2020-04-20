package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

public class ParametrosExportar<R>  extends ApiRetrofit.Parameters {
    @SerializedName("vobj_InsertarVariasEntidades")
    R insertarVariasEntidades;

    public ParametrosExportar() {
    }

    public R getInsertarVariasEntidades() {
        return insertarVariasEntidades;
    }

    public void setInsertarVariasEntidades(R insertarVariasEntidades) {
        this.insertarVariasEntidades = insertarVariasEntidades;
    }
}
