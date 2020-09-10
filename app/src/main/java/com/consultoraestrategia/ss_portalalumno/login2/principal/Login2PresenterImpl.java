package com.consultoraestrategia.ss_portalalumno.login2.principal;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.login2.data.preferent.LoginPreferentRepository;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.SaveUrlSevidorLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetDatosInicioSesion;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetPersonaLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetUsuarioLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioExterno;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioPorCorreoLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioPorDniLocal;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ActualizarUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ProgramaEducativoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioExternoUi;
import com.consultoraestrategia.ss_portalalumno.login2.entities.UsuarioUi;
import com.consultoraestrategia.ss_portalalumno.login2.principal.correo.CorreoView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.dni.DniView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario.ListaUsuarioView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.password.PasswordView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.usuario.UsuarioView;
import com.consultoraestrategia.ss_portalalumno.retrofit.UseCaseLoginSincrono;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

import java.util.ArrayList;
import java.util.List;

public class Login2PresenterImpl extends BasePresenterImpl<Login2View> implements Login2Presenter {
    private UsuarioView usuarioView;
    private CorreoView correoView;
    private DniView dniView;
    private ListaUsuarioView listaUsuarioView;
    private PasswordView passwordView;
    private GetUsuarioExterno getUsuarioExterno;
    private GetUsuarioLocal getUsuarioLocal;
    private GetPersonaLocal getPersonaLocal;
    private GetUsuarioPorCorreoLocal getUsuarioPorCorreoLocal;
    private GetUsuarioPorDniLocal getUsuarioPorDniLocal;
    private SaveUrlSevidorLocal saveUrlSevidorLocal;
    private LoginPreferentRepository loginPreferentRepository;
    private GetDatosInicioSesion getDatosServidorLocal;
    private String usuario;
    private String password;
    private String correo;
    private String dni;
    private boolean quitarUsuario = false;
    private List<PersonaUi> personaUiList = new ArrayList<>();
    private PersonaUi personaUiSelected;
    private List<ActualizarUi> actualizarUiList;
    private ActualizarUi actualizarUiSelected;
    private RetrofitCancel retrofitCancel;
    private String nombreColegio;
    private String nombreAnio;
    private List<ProgramaEducativoUi> cursoUiList;
    private int cantidadCursos;

    public Login2PresenterImpl(UseCaseHandler handler, Resources res, GetUsuarioExterno getUsuarioExterno, SaveUrlSevidorLocal saveUrlSevidorLocal, GetUsuarioPorCorreoLocal getUsuarioPorCorreoLocal, GetUsuarioPorDniLocal getUsuarioPorDniLocal, GetUsuarioLocal getUsuarioLocal, GetPersonaLocal getPersonaLocal, LoginPreferentRepository loginPreferentRepository, GetDatosInicioSesion getDatosServidorLocal) {
        super(handler, res);
        this.getUsuarioExterno = getUsuarioExterno;
        this.saveUrlSevidorLocal = saveUrlSevidorLocal;
        this.getUsuarioPorCorreoLocal = getUsuarioPorCorreoLocal;
        this.getUsuarioPorDniLocal = getUsuarioPorDniLocal;
        this.getUsuarioLocal = getUsuarioLocal;
        this.getPersonaLocal = getPersonaLocal;
        this.loginPreferentRepository = loginPreferentRepository;
        this.getDatosServidorLocal = getDatosServidorLocal;
    }

    @Override
    protected String getTag() {
        return getClass().getSimpleName();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        personaUiList.clear();
        personaUiList.addAll(loginPreferentRepository.getTodosUsuarios());
        if(personaUiList.size()>0){
            PersonaUi personaUi= new PersonaUi();
            personaUi.setNombres("Usar Otra Cuenta");
            personaUi.setApellidos("");
            personaUi.setUsarOtraCuenta(true);
            personaUiList.add(0,personaUi);
            if(view!=null)view.showListaUsuario(false);
        }else {
            if(view!=null)view.showUser(true);
        }
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void attachView(UsuarioView usuarioView) {
        this.usuarioView = usuarioView;
        if(loginPreferentRepository.getTodosUsuarios().isEmpty()){
            usuarioView.hideAtras();
        }else {
            usuarioView.showAtras();
        }

        if(!TextUtils.isEmpty(usuario)){
            usuarioView.setUsuario(usuario);
        }

        if (!TextUtils.isEmpty(password)){
            usuarioView.setPassword(password);
        }
    }

    @Override
    public void onUsuarioViewDestroyed() {
        usuarioView = null;
    }

    @Override
    public void onCorreoViewDestroyed() {
        correoView = null;
    }

    @Override
    public void onDniViewDestroyed() {
        dniView = null;
    }

    @Override
    public void onListaUsuarioViewDestroyed() {
        listaUsuarioView = null;
    }

    @Override
    public void attachView(CorreoView correoView) {
        this.correoView = correoView;
        if (!TextUtils.isEmpty(correo)){
            correoView.setCorreo(correo);
        }
    }

    @Override
    public void attachView(DniView dniView) {
        this.dniView = dniView;
        if (!TextUtils.isEmpty(dni)){
            dniView.setDni(dni);
        }
    }

    @Override
    public void attachView(ListaUsuarioView listaUsuarioView) {
        this.listaUsuarioView = listaUsuarioView;
        if(listaUsuarioView!=null)listaUsuarioView.listaUsuarioView(personaUiList, quitarUsuario);

    }

    @Override
    public void onPasswordViewDestroyed() {
        passwordView = null;
    }

    @Override
    public void attachView(PasswordView passwordView) {
        this.passwordView = passwordView;
        if(personaUiSelected!=null)passwordView.setPersona(personaUiSelected);
    }

    @Override
    public void onClickUsuarioSiguiente(final String usuario, String password) {
        if (TextUtils.isEmpty(usuario)) {
            showMessage(res.getString(R.string.login_username_empty));
            return;
        }

        if (TextUtils.isEmpty(password)) {
            if(view!=null)usuarioView.onErrorPassword(res.getString(R.string.login_password_empty));
            return;
        }

        this.usuario = usuario;
        this.password = password;
        //loginNewUser(userName,22 ,"http://192.168.1.104:3000/PortalAcadMovil.ashx", 1, "", "", true);
        //loginNewUser(userName,22 ,"http://192.168.1.151/portalmovil/PortalAcadMovil.ashx", 1, "", "", true);
       // setGetAdminService(1, userName, userPassword, "","", res.getString(R.string.admin_service), true);
        if(usuarioView!=null)usuarioView.showProgress();
        if(passwordView!=null)passwordView.showProgress();
        if(view!=null)view.disabledOnClick();
        getUsuarioExterno.execute(new GetUsuarioExterno.Request(res.getString(R.string.admin_service), usuario, password), new UseCaseLoginSincrono.Callback<UsuarioExternoUi>() {
            @Override
            public void onResponse(boolean success, UsuarioExternoUi value) {
                if(success){
                    if(value!=null){
                        if(value.getUsuarioExternoId()==-1){
                            if(view!=null)view.enableddOnClick();
                            if(usuarioView!=null)usuarioView.onCredencialesIncorrectos(res.getString(R.string.login_credentiales_invilite));
                            if(passwordView!=null)passwordView.onInvalitedPassword(res.getString(R.string.login_password_invilite));
                            if(usuarioView!=null)usuarioView.hideProgress();
                            if(passwordView!=null)passwordView.hideProgress();
                        }else {
                            if (value.getUsuarioIdLocal()>0){
                                loginServidorLocal(value);
                            }else {
                                if(usuarioView!=null)usuarioView.hideProgress();
                                if(passwordView!=null)passwordView.hideProgress();
                                if(view!=null)view.enableddOnClick();
                                if(view!=null)view.showCorreo(true);
                            }
                        }
                    }else {
                        showMessage("Usuario: error desconocido");
                        if(usuarioView!=null)usuarioView.hideProgress();
                        if(passwordView!=null)passwordView.hideProgress();
                        if(view!=null)view.enableddOnClick();
                    }
                }else {
                    showMessage("Usuario: error de conexión");
                    if(usuarioView!=null)usuarioView.hideProgress();
                    if(passwordView!=null)passwordView.hideProgress();
                    if(view!=null)view.enableddOnClick();
                }
            }
        });

    }

    private void loginServidorLocal(UsuarioExternoUi usuarioLocalUi) {
        saveUrlSevidorLocal.execute(usuarioLocalUi.getUrlServiceLocal());
        //Cambiar por el de un alumno
        //usuarioLocalUi.setUsuarioIdLocal(7921);
        getUsuarioLocal.execute(new GetUsuarioLocal.Request(usuarioLocalUi.getUsuarioIdLocal()), new UseCaseLoginSincrono.Callback<UsuarioUi>() {
            @Override
            public void onResponse(boolean success, final UsuarioUi usuarioUi) {
                if(usuarioView!=null)usuarioView.hideProgress();
                if(passwordView!=null)passwordView.hideProgress();
                if(dniView!=null)dniView.hideProgress();
                if(correoView!=null)correoView.hideProgress();
                if(view!=null)view.enableddOnClick();
                if(success){
                    boolean rolapp = false;
                    for (Integer rolId: usuarioUi.getRolIdList()){
                        if(rolId== 6)rolapp=true;
                        Log.d(getTag(),"rolId :" + rolId);
                    }

                    if(rolapp){
                        getPersonaLocal.execute(new GetPersonaLocal.Request(usuarioUi.getUserName()), new UseCaseLoginSincrono.Callback<PersonaUi>() {
                            @Override
                            public void onResponse(boolean success, PersonaUi personaUi) {
                                if(success){
                                    if(personaUi == null){
                                        showImportantMessage("Error al guardar persona");
                                    }else {
                                        personaUi.setUsuario(usuario);
                                        personaUi.setCorreo(correo);
                                        personaUi.setDni(dni);
                                        personaUi.setInstitucionUrl(usuarioUi.getInstitucionUrl());
                                        saveUsuarioPreferents(personaUi);
                                        if(view!=null)view.showDialogProgress();


                                        getDatosServidorLocal.execute(new GetDatosInicioSesion.Request(usuarioUi.getUsuarioId()), new UseCaseLoginSincrono.Callback<GetDatosInicioSesion.Response>() {
                                            @Override
                                            public void onResponse(boolean success, GetDatosInicioSesion.Response value) {
                                                if(success){
                                                    if(value instanceof GetDatosInicioSesion.RequestRetrofitCancel){

                                                    }else {
                                                        //if(view!=null)view.goToActivity(usuarioUi.getUsuarioId());
                                                        //getInformacionPreFastData(usuarioUi.getUsuarioId(), value.getAnioAcademicoId());
                                                        //if(view!=null)view.showFastData();
                                                        //getTodosCurso(usuarioUi.getUsuarioId(), value.getAnioAcademicoId());
                                                        if(view!=null)view.goToActivity(usuarioUi.getUsuarioId());
                                                    }

                                                }else {
                                                    if(view!=null)view.showUser(false);
                                                    showImportantMessage(res.getString(R.string.login_msg_red_error));
                                                }
                                            }
                                        });

                                    }
                                }else {
                                    showImportantMessage("PersonaUi: " + res.getString(R.string.login_msg_red_error));
                                }
                            }
                        });

                    }else {
                        hideProgress();
                        showImportantMessage("Usuario sin acceso");
                    }

                }else {
                    showImportantMessage("Usuario local no existe");
                }



            }
        });

    }

    private void saveUsuarioPreferents(PersonaUi personaUi) {
        loginPreferentRepository.guardarUsuario(personaUi, new LoginPreferentRepository.Callback<Boolean>() {
            @Override
            public void onLoad(boolean success, Boolean item) {
                //Log.d(TAG, "saveUsuarioPreferents: " + true);
            }
        });
    }
    @Override
    public void onClickUsuarioAtras() {
        if(view!=null)view.onBackPressed();
    }

    @Override
    public void onClickCorreoSiguiente(String correo) {
        this.correo = correo;
        if (TextUtils.isEmpty(correo)) {
            if(correoView!=null)correoView.onInvalitedCorreo(res.getString(R.string.login_correo_empty));
            return;
        }
        if(correoView!=null)correoView.showProgress();
        if(view!=null)view.disabledOnClick();
        getUsuarioPorCorreoLocal.execute(new GetUsuarioPorCorreoLocal.Request(res.getString(R.string.admin_service), usuario, password, correo), new UseCaseLoginSincrono.Callback<UsuarioExternoUi>() {
            @Override
            public void onResponse(boolean success, UsuarioExternoUi value) {
                if(success){
                    if(value!=null){
                        if(value.getUsuarioExternoId()==-1){
                            if(view!=null)view.enableddOnClick();
                            if(correoView!=null)correoView.hideProgress();
                            if(correoView!=null)correoView.onInvalitedCorreo(res.getString(R.string.login_correo_invilite));
                        }else {
                            if (value.getUsuarioIdLocal()>0){
                                loginServidorLocal(value);
                            }else {
                                if(correoView!=null)correoView.hideProgress();
                                if(view!=null)view.enableddOnClick();
                                if(view!=null)view.showDni(true);
                            }
                        }

                    }else {
                        if(correoView!=null)correoView.hideProgress();
                        showMessage("Correo: error desconocido");
                        if(view!=null)view.enableddOnClick();
                    }
                }else {
                    if(correoView!=null)correoView.hideProgress();
                    showMessage("Correo: error de conexión");
                    if(view!=null)view.enableddOnClick();
                }
            }
        });
    }

    @Override
    public void onClickCorreoAtras() {
        if(view!=null)view.onBackPressed();
    }

    @Override
    public void onClickDniSiguiente(String dni) {
        this.dni = dni;
        if (TextUtils.isEmpty(dni)) {
            if(dniView!=null)dniView.onInvalitedDni(res.getString(R.string.login_dni_empty));
            return;
        }
        if(dniView!=null)dniView.showProgress();
        if(view!=null)view.disabledOnClick();
        getUsuarioPorDniLocal.execute(new GetUsuarioPorDniLocal.Request(res.getString(R.string.admin_service), usuario, password, correo, dni), new UseCaseLoginSincrono.Callback<UsuarioExternoUi>() {
            @Override
            public void onResponse(boolean success, UsuarioExternoUi value) {
                if(success){
                    if(value!=null){

                        if(value.getUsuarioExternoId()==-1){
                            if(view!=null)view.enableddOnClick();
                            if(dniView!=null)dniView.hideProgress();
                            if(dniView!=null)dniView.onInvalitedDni(res.getString(R.string.login_dni_invilite));
                        }else {
                            if (value.getUsuarioIdLocal()>0){
                                loginServidorLocal(value);
                            }else {
                                showMessage("Usuario desactivado");
                                if(view!=null)view.enableddOnClick();
                                if(dniView!=null)dniView.hideProgress();
                            }
                        }
                    }else {
                        if(dniView!=null)dniView.hideProgress();
                        if(view!=null)view.enableddOnClick();
                        showMessage("Documento: error desconocido");
                    }
                }else {
                    if(dniView!=null)dniView.hideProgress();
                    if(view!=null)view.enableddOnClick();
                    showMessage("Documento: error de conexión");
                }
            }
        });
    }

    @Override
    public void onClickDniAtras() {
        if(view!=null)view.onBackPressed();
    }

    @Override
    public void onClickQuitarUsuario() {
        this.quitarUsuario = !this.quitarUsuario;
        if(listaUsuarioView!=null)listaUsuarioView.setTextoBtnQuitarUsuario(quitarUsuario?"Finalizar":"Quitar una cuenta");
        if(listaUsuarioView!=null)listaUsuarioView.listaUsuarioView(personaUiList, quitarUsuario);
    }

    @Override
    public void onClickPersona(final PersonaUi personaUi) {
        if(personaUi.isUsarOtraCuenta()){
            this.usuario = "";
            this.correo = "";;
            this.dni = "";
            this.password ="";
           if(view!=null)view.showUser(true);
        }else {
            if(quitarUsuario){
                loginPreferentRepository.eliminarUsuario(personaUi, new LoginPreferentRepository.Callback<Boolean>() {
                    @Override
                    public void onLoad(boolean success, Boolean item) {
                        if(success){
                            personaUiList.remove(personaUi);
                            if(listaUsuarioView!=null)listaUsuarioView.listaUsuarioView(personaUiList, true);
                        }else {
                            //Log.d(TAG, "Error quitar usuario");
                        }
                    }
                });

            }else {
                this.usuario = personaUi.getUsuario();
                this.correo = personaUi.getCorreo();
                this.dni = personaUi.getDni();
                this.password="";
                personaUiSelected = personaUi;
                if(view!=null)view.showPassword();
            }
        }
    }

    @Override
    public void onClickPasswordSiguiente(String password) {
        if(!TextUtils.isEmpty(dni)){
            onClickDniSiguiente(dni);
        }else if(!TextUtils.isEmpty(correo)){
            onClickCorreoSiguiente(correo);
        }else {
            onClickUsuarioSiguiente(usuario, password);
        }
    }

    @Override
    public void onClickPasswordAtras() {
        if(view!=null)view.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
