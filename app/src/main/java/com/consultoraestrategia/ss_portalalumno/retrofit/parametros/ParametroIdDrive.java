package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

public class ParametroIdDrive extends ApiRetrofit.Parameters {

    @SerializedName("vstr_TareaEventoId")
    private String tareaEventoId;
    @SerializedName("vstr_PersonaId")
    private int personaId;
    @SerializedName("vstr_Nombre")
    private String nombre;
    @SerializedName("vstr_Url")
    private String url;
    @SerializedName("vint_SilaboEventoId")
    private int silaboEventoId;
    @SerializedName("vint_UnidadAprendizajeId")
    private int unidadAprendizajeId;
    @SerializedName("vint_SesionAprendizajeId")
    private int sesionAprendizajeId;

    public String getTareaEventoId() {
        return tareaEventoId;
    }

    public void setTareaEventoId(String tareaEventoId) {
        this.tareaEventoId = tareaEventoId;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public int getUnidadAprendizajeId() {
        return unidadAprendizajeId;
    }

    public void setUnidadAprendizajeId(int unidadAprendizajeId) {
        this.unidadAprendizajeId = unidadAprendizajeId;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        this.sesionAprendizajeId = sesionAprendizajeId;
    }
}
