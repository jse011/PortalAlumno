package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

public class ParametroAgendaEvento extends ApiRetrofit.Parameters {
    @SerializedName("vint_UsuarioId")
    private int usuarioId;
    @SerializedName("vint_AlumnoId")
    private int alumnoId;
    @SerializedName("vint_TipoEventoId")
    private int tipoEventoId;

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setTipoEventoId(int tipoEventoId) {
        this.tipoEventoId = tipoEventoId;
    }

    public int getTipoEventoId() {
        return tipoEventoId;
    }
}
