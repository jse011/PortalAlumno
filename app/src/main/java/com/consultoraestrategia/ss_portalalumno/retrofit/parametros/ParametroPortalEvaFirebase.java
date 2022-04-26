package com.consultoraestrategia.ss_portalalumno.retrofit.parametros;

import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParametroPortalEvaFirebase extends ApiRetrofit.Parameters {
    @SerializedName("vint_SilaboEventoId")
    private int silaboEventoId;
    @SerializedName("vint_TipoPeriodoId")
    private int tipoPeriodoId;
    @SerializedName("vint_UnidadAprendizajeid")
    private int unidadAprendizajeid;
    @SerializedName("vint_SesionAprendizajeId")
    private int sesionAprendizajeId;
    @SerializedName("vint_alumnoId")
    private int alumnoId;
    @SerializedName("vint_ProgramaEducativoId")
    private int programaEducativoId;
    @SerializedName("vint_TareaId")
    private String tareaId;
    @SerializedName("vint_UnidadAprendizajeIdList")
    private List<Integer> unidadAprendizajeIdList;
    @SerializedName("vstr_ArchivoId")
    private String archivoId;
    @SerializedName("vstr_NombreLink")
    private String nombreLink;
    @SerializedName("vstr_Link")
    private String decripcionLink;
    @SerializedName("vbol_entregado")
    private boolean entregadarTarea;

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

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public int getProgramaEducativoId() {
        return programaEducativoId;
    }

    public void setProgramaEducativoId(int programaEducativoId) {
        this.programaEducativoId = programaEducativoId;
    }

    public void setTareaId(String tareaId) {
        this.tareaId = tareaId;
    }

    public String getTareaId() {
        return tareaId;
    }

    public void setUnidadAprendizajeIdList(List<Integer> unidadAprendizajeIdList) {
        this.unidadAprendizajeIdList = unidadAprendizajeIdList;
    }

    public List<Integer> getUnidadAprendizajeIdList() {
        return unidadAprendizajeIdList;
    }

    public void setArchivoId(String archivoId) {
        this.archivoId = archivoId;
    }

    public String getArchivoId() {
        return archivoId;
    }

    public boolean getEntregadarTarea() {
        return entregadarTarea;
    }

    public void setEntregadarTarea(boolean entregadarTarea) {
        this.entregadarTarea = entregadarTarea;
    }

    public String getNombreLink() {
        return nombreLink;
    }

    public void setNombreLink(String nombreLink) {
        this.nombreLink = nombreLink;
    }

    public String getDecripcionLink() {
        return decripcionLink;
    }

    public void setDecripcionLink(String decripcionLink) {
        this.decripcionLink = decripcionLink;
    }
}
