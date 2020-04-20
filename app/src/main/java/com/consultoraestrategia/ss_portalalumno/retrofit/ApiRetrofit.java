/* JSON API for android application [Web Service] */
package com.consultoraestrategia.ss_portalalumno.retrofit;

import android.text.TextUtils;


import com.consultoraestrategia.ss_portalalumno.entities.AdminService;
import com.consultoraestrategia.ss_portalalumno.entities.BEListaPadre;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeAdminService;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroChangeUser;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.ParametroLogin;
import com.consultoraestrategia.ss_portalalumno.retrofit.parametros.UsuarioAdminService;
import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;
import com.consultoraestrategia.ss_portalalumno.retrofit.service.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiRetrofit {
    private static final String TAG = "RestAPI";
    public static final String REMOTE_URL = "http://crmeducativo.consultoraestrategia.com/CRMMovil/PortalAcadMovil.ashx";

    private String url = REMOTE_URL;
    private Retrofit retrofit;
    private Service service;
    private OkHttpClient okHttpClient;

    public static ApiRetrofit getInstance() {
        ApiRetrofit instance = new ApiRetrofit();
        instance.updateServerUrl();
        instance.initialize();
        return instance;
    }

    private ApiRetrofit() {
    }

    public void updateServerUrl(){
        String serverUrl = GlobalSettings.getServerUrl();
        if (!TextUtils.isEmpty(serverUrl)) {
            this.url = serverUrl;
        }
    }


    public void initialize(){
        Log.d(TAG,url);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        setTime(30,30,30, TimeUnit.SECONDS);

        this.retrofit  = new retrofit2.Retrofit.Builder()
                .baseUrl(this.url.trim() + "/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.service = retrofit.create(Service.class);
    }


    public void setOkHttpClient(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Log.d(TAG, "url: " + this.url.trim() + "/");
        this.retrofit  = new retrofit2.Retrofit.Builder()
                .baseUrl(this.url.trim() + "/")
                .client(this.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.service = retrofit.create(Service.class);
    }

    public void changeSetTime(long connectTimeout, long writeTimeout, long readTimeout, TimeUnit unit){
        initialize();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(connectTimeout, unit) // connect timeout
                .writeTimeout(writeTimeout, unit) // write timeout
                .readTimeout(readTimeout, unit);
        // read timeout
        okHttpClient = builder.build();

        this.retrofit  = new retrofit2.Retrofit.Builder()
                .baseUrl(this.url.trim() + "/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        this.service = retrofit.create(Service.class);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


    public void setTime(long connectTimeout, long writeTimeout, long readTimeout, TimeUnit unit) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder
                .connectTimeout(connectTimeout, unit) // connect timeout
                .writeTimeout(writeTimeout, unit) // write timeout
                .readTimeout(readTimeout, unit);
                // read timeout
        okHttpClient = builder.build();
    }

    public Call<RestApiResponse<BEListaPadre>> flst_getDatosPortalAlumno(int usuarioId) {
        ParametroLogin parametroLogin = new ParametroLogin();
        parametroLogin.setUsuarioId(usuarioId);//
        Log.d(TAG,"url: " + url);
        ApiRequestBody<ParametroLogin> apiRequestBody = new ApiRequestBody<>("flst_getDatosPortalAlumno",parametroLogin);
        final Gson gsons = new Gson();
        final String representacionJSON = gsons.toJson(apiRequestBody);
        Log.d(TAG, "apiRequestBody : " + representacionJSON);
        return service.flst_getDatosPortalAlumno(apiRequestBody);
    }


    public class ApiRequestBody<T extends Parameters>{
        @SerializedName("interface")
        String _interface = "RestAPI";
        String method;
        T parameters;

        public ApiRequestBody(String method, T parameters) {
            this.method = method;
            this.parameters = parameters;
        }

        public String get_interface() {
            return _interface;
        }

        public void set_interface(String _interface) {
            this._interface = _interface;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public T getParameters() {
            return parameters;
        }

        public void setParameters(T parameters) {
            this.parameters = parameters;
        }

        @Override
        public String toString() {
            return "ApiRequestBody{" +
                    "_interface='" + _interface + '\'' +
                    ", method='" + method + '\'' +
                    ", parameters=" + parameters +
                    '}';
        }
    }

    public static abstract class Parameters{}

    public String getUrl() {
        return url;
    }

    public static class Log{
        public static void d(String TAG, String message) {
            int maxLogSize = 2000;
            for(int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.d(TAG, message.substring(start, end));
            }
        }
    }



    public Call<RestApiResponse<AdminService>> f_BuscarUsuarioCent(int opcion, String usuario, String passwordd, String correo, String numeroDocumento, String urlServidor) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Service getResponse = new Retrofit.Builder()
                .baseUrl(urlServidor+"/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(Service.class);

        UsuarioAdminService usuarioService = new UsuarioAdminService();
        usuarioService.setOpcion(opcion);
        usuarioService.setUsuario(usuario);
        usuarioService.setPasswordd(passwordd);
        usuarioService.setCorreo(correo);
        usuarioService.setNumDoc(numeroDocumento);

        ParametroChangeAdminService parametroChangeAdminService= new ParametroChangeAdminService(usuarioService);

        ApiRequestBody<ParametroChangeAdminService> apiRequestBody = new ApiRequestBody<>("f_BuscarUsuarioCent",parametroChangeAdminService);

        final Gson gsons = new Gson();
        final String representacionJSON = gsons.toJson(apiRequestBody);
        Log.d(TAG, "apiRequestBody : " + representacionJSON);
        return getResponse.f_BuscarUsuarioCent(apiRequestBody);
    }

    public Call<RestApiResponse<Usuario>> fobj_ObtenerUsuario(int usuarioId) {
        ParametroChangeUser parametroChangeUser = new ParametroChangeUser();
        parametroChangeUser.setUsuarioId(usuarioId);//
        Log.d(TAG,"url: " + url);
        Log.d(TAG, "json: " + parametroChangeUser.toString());
        ApiRequestBody< ParametroChangeUser> apiRequestBody = new ApiRequestBody<>("fobj_ObtenerUsuario_By_Id",parametroChangeUser);
        return service.fobj_ObtenerUsuario(apiRequestBody);
    }

    public  Call<RestApiResponse<Persona>> flst_ObtenerPersona(String usuario) {
        ParametroChangeUser parametroChangeUser = new ParametroChangeUser();
        parametroChangeUser.setUsuario(usuario);//
        Log.d(TAG,"url: " + url);
        Log.d(TAG, "json: " + parametroChangeUser.getUsuario());
        ApiRequestBody< ParametroChangeUser> apiRequestBody = new ApiRequestBody<>("flst_ObtenerPersona",parametroChangeUser);
        return service.flst_ObtenerPersona(apiRequestBody);
    }

    public ResponseBody downloadFileByUrl(String url) throws IOException {
        Call<ResponseBody> request = service.downloadFileByUrl(url);
        return request.execute().body();
    }

}


