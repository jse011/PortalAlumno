package com.consultoraestrategia.ss_portalalumno.main;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetCursos;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateFirebaseTipoNota;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.NuevaVersionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter{
    private ProgramaEduactivoUI programaEducativo;
    private List<ProgramaEduactivoUI> programaEduactivosUIList=new ArrayList<>();
    private List<CursosUi> cursosUiList = new ArrayList<>();
    private GetCursos getCursos;
    private AnioAcademicoUi anioAcademicoUi;
    private String usuario;
    private String firebaseNode;
    private UpdateCalendarioPeriodo calendarioPeriodo;
    private UpdateFirebaseTipoNota updateFirebaseTipoNota;
    private RetrofitCancel cancel;
    private Online online;
    private AlumnoUi alumnoUi;
    private NuevaVersionUi nuevaVersionUi = null;
    private boolean mainPause;

    public MainPresenterImpl(UseCaseHandler handler, Resources res, GetCursos getCursos, UpdateCalendarioPeriodo calendarioPeriodo, UpdateFirebaseTipoNota updateFirebaseTipoNota,
                             Online online) {
        super(handler, res);
        this.getCursos = getCursos;
        this.calendarioPeriodo = calendarioPeriodo;
        this.updateFirebaseTipoNota = updateFirebaseTipoNota;
        this.online = online;
    }

    @Override
    protected String getTag() {
        return null;
    }

    @Override
    public void onSingleItemSelected(Object singleItem, int selectedPosition) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        boolean dataImported = SessionUser.getCurrentUser() != null && SessionUser.getCurrentUser().isDataImported();
        if(!dataImported){
            if(view!=null)view.showActivityLogin();
            return;
        }

        usuario = SessionUser.getCurrentUser().getUsername();
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();
        firebaseNode = webconfig!=null?webconfig.getContent():"sinServer";

        GetCursos.Response response = getCursos.execute();
        alumnoUi = response.getAlumnoUi();
        //iCRMEdu.variblesGlobales.setHabilitarAcceso(alumnoUi.getHabilitarAcceso());
        //iCRMEdu.variblesGlobales.setBloqueoAcceso(!alumnoUi.getHabilitarAcceso());
        //Por si se crea una actividad es remplasada se crea la actidad bloque de usuario
        if(view!=null)view.initBloqueo();
        //if(!alumnoUi.getHabilitarAcceso()){
           // if(view!=null)view.showActivtyBloqueo();
        //}


        if(view!=null)view.changeNombreUsuario(alumnoUi.getNombre());
        if(view!=null)view.changeFotoUsuario(alumnoUi.getFoto());
        if(view!=null)view.changeFotoUsuarioApoderado(alumnoUi.getFotoApoderado());

        AnioAcademicoUi anioAcademicoUi = response.getAnioAcademicoUi();
        if(view!=null)view.setNameAnioAcademico("Año Academ. " + anioAcademicoUi.getNombre());

        this.anioAcademicoUi = response.getAnioAcademicoUi();
        setupProgramaEducativo(response.getProgramaEduactivoUIList());
        this.cursosUiList.addAll(response.getCursosUiList());
        setupCurso();
        updateFirebaseTipoNota();
        cancel = calendarioPeriodo.execute(anioAcademicoUi.getAnioAcademicoId(), new UpdateCalendarioPeriodo.Callback() {
            @Override
            public void onSucess() {

            }

            @Override
            public void onError() {

            }
        });

    }

    private void updateFirebaseTipoNota() {
        if(programaEducativo!=null)
            updateFirebaseTipoNota.execute(programaEducativo.getIdPrograma(), new UpdateFirebaseTipoNota.CallBack() {
                @Override
                public void onSucces() {

                }

                @Override
                public void onError(String error) {

                }
            });
    }

    private void setupCurso(){
        hideProgress();
        Log.d("setupCurso", this.cursosUiList.size()+"");
        List<CursosUi> cursosUiList = new ArrayList<>();
        for (CursosUi cursosUi : this.cursosUiList){
            if(programaEducativo!=null&&
                    cursosUi.getProgramaEduactivoUI()!=null&&
                    cursosUi.getProgramaEduactivoUI().getIdPrograma()==programaEducativo.getIdPrograma()){
                cursosUiList.add(cursosUi);
            }
        }
        if(view!=null)view.showListCurso(cursosUiList);
    }

    private void setupProgramaEducativo(List<ProgramaEduactivoUI> programaEduactivoUIList) {

        for (ProgramaEduactivoUI programaEduactivoUI: programaEduactivoUIList){
            if(programaEduactivoUI.isSeleccionado()){
                programaEducativo = programaEduactivoUI;
            }
        }

        if(programaEducativo==null&&!programaEduactivoUIList.isEmpty()){
            ProgramaEduactivoUI programaEduactivoUI = programaEduactivoUIList.get(0);
            programaEduactivoUI.setSeleccionado(true);
            this.programaEducativo = programaEduactivoUI;
        }

        programaEduactivosUIList.clear();
        programaEduactivosUIList.addAll(programaEduactivoUIList);
        if(view!=null)view.showMenuList(new ArrayList<Object>(programaEduactivosUIList));
    }


    @Override
    public void onCLickAcceptButtom() {

    }

    @Override
    public void onProgramaEducativoUIClicked(ProgramaEduactivoUI programaEduactivosUI) {
        if( this.programaEducativo != null){
            this.programaEducativo.setSeleccionado(false);
        }
        this.programaEducativo = programaEduactivosUI;
        programaEduactivosUI.setSeleccionado(true);

        if(view!=null)view.showMenuList(new ArrayList<Object>(programaEduactivosUIList));
        setupCurso();
    }

    @Override
    public void onAccesoSelected(UsuarioAccesoUI usuarioAccesoUI) {
        //Log.d(TAG, "onAccesoSelected : " + usuarioAccesoUI.getIdAcceso());

    }

    @Override
    public void onClickConfiguracion(ConfiguracionUi configuracionUi) {
        switch (configuracionUi){
            case INFORMACION:
                //getInformacionApp() ;
                break;
            case CONTACTOS:
                //BEVariables beVariables = new BEVariables();
                //if(view!=null) view.viewActivityImportLogin(beVariables);
                break;
            case CERRAR_SESION:
                mostrarDialogoCerrarSesion();
                break;
            case BORRAR_CACHE:
                //if(view!=null) view.mostrarDialogoBorrarMemoriaCahe();
                break;
            case ALARMA:
                //if(view!=null) view.mostrarDialogoConfigAlarma();
                break;


        }
    }

    private void mostrarDialogoCerrarSesion() {

        if(view!=null) view.mostrarDialogoCerrarSesion();
    }

    @Override
    public void onIbtnProgramaClicked() {
        if(view!=null)view.showMenuList(new ArrayList<Object>(programaEduactivosUIList));
    }

    @Override
    public void onClickBtnAcceso() {
        UsuarioAccesoUI usuarioAccesoUI = new UsuarioAccesoUI();
        usuarioAccesoUI.setIdAcceso(1);
        usuarioAccesoUI.setNombreAcceso("Perfil");
        List<UsuarioAccesoUI> usuarioAccesoUIList = new ArrayList<>();
        usuarioAccesoUIList.add(usuarioAccesoUI);
        if(view != null)view.showMenuList(new ArrayList<Object>(usuarioAccesoUIList));
    }

    @Override
    public void onClickBtnConfiguracion() {
        List<ConfiguracionUi> configuracionUiList = new ArrayList<>();
        //configuracionUiList.add(ConfiguracionUi.INFORMACION);
        //configuracionUiList.add(ConfiguracionUi.BORRAR_CACHE);
        //configuracionUiList.add(ConfiguracionUi.CONTACTOS);
        //configuracionUiList.add(ConfiguracionUi.ALARMA);
        configuracionUiList.add(ConfiguracionUi.CERRAR_SESION);


        if(view != null)view.showMenuList(new ArrayList<Object>(configuracionUiList));
    }

    @Override
    public void onClickCurso(CursosUi cursosUi) {
        GbCursoUi gbCursoUi = new GbCursoUi();
        gbCursoUi.setCursoId(cursosUi.getCursoId());
        gbCursoUi.setCargaCursoId(cursosUi.getCargaCursoId());
        gbCursoUi.setSilaboEventoId(cursosUi.getSilaboEventoId());
        gbCursoUi.setParametroDisenioColor1(cursosUi.getBackgroundSolidColor());
        gbCursoUi.setParametroDisenioColor2(cursosUi.getBackgroundSolidColor2());
        gbCursoUi.setParametroDisenioColor3(cursosUi.getBackgroundSolidColor3());
        gbCursoUi.setNombre(cursosUi.getNombre());
        gbCursoUi.setSalon(cursosUi.getSalon());
        gbCursoUi.setParametroDisenioPath(cursosUi.getUrlBackgroundItem());
        gbCursoUi.setSeccionyperiodo(cursosUi.getSeccionyperiodo());
        gbCursoUi.setPlanCursoId(cursosUi.getPlanCursoId());
        iCRMEdu.variblesGlobales.setGbCursoUi(gbCursoUi);
        iCRMEdu.variblesGlobales.setAnioAcademicoId(anioAcademicoUi.getAnioAcademicoId());
        iCRMEdu.variblesGlobales.setProgramEducativoId(programaEducativo.getIdPrograma());
        //iCRMEdu.variblesGlobales.setProgramEducativoId(idPrograma);
        if(programaEducativo!=null)if(view!=null)view.showTabCursoActivity();
    }

    @Override
    public void onClickDialogoCerrarSesion() {
        if(view!=null)view.cerrarSesion();
    }

    @Override
    public void nuevaVersionDisponible(String newVersionCode, String changes) {
        nuevaVersionUi = new NuevaVersionUi();
        nuevaVersionUi.setNewVersionCode(newVersionCode);
        nuevaVersionUi.setChange(changes);
        if(!mainPause){
            if(view!=null)view.showNuevaversion(nuevaVersionUi);
            nuevaVersionUi = null;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mainPause = false;
        if(nuevaVersionUi!=null){
            if(view!=null)view.showNuevaversion(nuevaVersionUi);
            nuevaVersionUi = null;
        }
        String usuarioFirebase = firebaseNode + "_" + (!TextUtils.isEmpty(usuario)?usuario.replaceAll(" ","_"):usuario) +"@gmail.com";
        if(view!=null)view.validateFirebase(usuarioFirebase, usuarioFirebase);
    }



    @Override
    public void onPause() {
        mainPause = true;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(cancel!=null)cancel.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        online.restarOnline(success -> {

        });
    }
}
