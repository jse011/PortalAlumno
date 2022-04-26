package com.consultoraestrategia.ss_portalalumno.retrofit.service;

import com.consultoraestrategia.ss_portalalumno.entities.AdminService;
import com.consultoraestrategia.ss_portalalumno.entities.BEListaPadre;
import com.consultoraestrategia.ss_portalalumno.entities.GrabacionSalaVirtual;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEncuestaEval;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualServidor;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDatosAnioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDrive;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEEventoAgenda;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroAgendaEvento;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeAdminService;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeUser;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroIdDrive;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroInstrumentoEncustaAlumno;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroLogin;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroPortalEvaFirebase;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroSalaVirtual;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroUpdateCalenPeriodo;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.util.JSONFirebase;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by SCIEV on 24/07/2018.
 */

public interface Service {
    @POST(" ")
    Call<RestApiResponse<AdminService>> f_BuscarUsuarioCent(@Body ApiRetrofit.ApiRequestBody<ParametroChangeAdminService> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<Usuario>> fobj_ObtenerUsuario(@Body ApiRetrofit.ApiRequestBody<ParametroChangeUser> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<Persona>> flst_ObtenerPersona(@Body ApiRetrofit.ApiRequestBody<ParametroChangeUser> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<BEListaPadre>> flst_getDatosPortalAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroLogin> apiRequestBody);
    @Streaming
    @GET
    Call<ResponseBody> downloadFileByUrl(@Url String fileUrl);
    @POST(" ")
    Call<RestApiResponse<BEDatosAnioAcademico>> flst_getDatosCalendarioPeriodo(@Body ApiRetrofit.ApiRequestBody<ParametroUpdateCalenPeriodo> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<BEDrive>> f_SynckTareaAlumDrive(@Body ApiRetrofit.ApiRequestBody<ParametroIdDrive> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<BEDrive>> f_SynckEviSesDrive(@Body ApiRetrofit.ApiRequestBody<ParametroIdDrive> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<Boolean>> f_isHabilitadoUsuario(@Body ApiRetrofit.ApiRequestBody<ParametroChangeUser> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<BEEventoAgenda>> getEventoAgendaFlutter(@Body ApiRetrofit.ApiRequestBody<ParametroAgendaEvento> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<List<GrabacionSalaVirtual>>> getGrabacionesSalaVirtual(@Body ApiRetrofit.ApiRequestBody<ParametroSalaVirtual> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<List<ReunionVirtualServidor>>> getReunionVirtualAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroSalaVirtual> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<List<InstrumentoEncuestaEval>>> getInstrumentoEncuestaEval(@Body ApiRetrofit.ApiRequestBody<ParametroInstrumentoEncustaAlumno> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<JsonObject>> getUnidadAprendizajeAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<List<JsonObject>>> getTareasAlumnoEvaluacion2(@Body ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> apiRequestBody);
    @Multipart
    @POST(" ")
    Call<RestApiResponse<String>> uploadFileTareaAlumno(@Part("body") ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> description, @Part MultipartBody.Part file);
    @POST(" ")
    Call<RestApiResponse<Boolean>> deleteFileTareaAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<Object>> entregarTareaAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> apiRequestBody);
    @POST(" ")
    Call<RestApiResponse<String>> uploadLinkEvidenciaAlumno(@Body ApiRetrofit.ApiRequestBody<ParametroPortalEvaFirebase> apiRequestBody);
}
