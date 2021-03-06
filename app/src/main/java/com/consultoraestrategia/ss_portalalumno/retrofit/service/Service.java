package com.consultoraestrategia.ss_portalalumno.retrofit.service;

import com.consultoraestrategia.ss_portalalumno.entities.AdminService;
import com.consultoraestrategia.ss_portalalumno.entities.BEListaPadre;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDatosAnioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.servidor.BEDrive;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeAdminService;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeUser;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroIdDrive;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroLogin;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroUpdateCalenPeriodo;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}
