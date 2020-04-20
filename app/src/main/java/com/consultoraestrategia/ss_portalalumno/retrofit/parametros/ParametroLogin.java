package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SCIEV on 26/07/2018.
 */

public class ParametroLogin extends ApiRetrofit.Parameters {
    @SerializedName("vint_UsuarioId")
    int usuarioId;
    @SerializedName("vint_anioAcademicoId")
    int anioAcademicoId;

    public ParametroLogin() {
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }
}
