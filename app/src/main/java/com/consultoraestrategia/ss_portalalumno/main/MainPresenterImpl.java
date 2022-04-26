package com.consultoraestrategia.ss_portalalumno.main;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenterImpl;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbPreview;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetColorTarjetaQR;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetCursos;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetEventoAgenda;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetIconoPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetLikeSaveCountLike;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetNombreServidor;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.SaveLike;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateFirebaseTipoNota;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.FamiliaUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.HijoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.NuevaVersionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter{
    private ProgramaEduactivoUI programaEducativo;
    private List<ProgramaEduactivoUI> programaEduactivosUIList=new ArrayList<>();
    private List<CursosUi> cursosUiList = new ArrayList<>();
    private boolean showProgresCurso = true;
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
    private MainView.TabEvento tabEventoView;
    //region Evento
    private TipoEventoUi selectedTipoEventoUi = new TipoEventoUi();
    private List<TipoEventoUi> tipoEventoList = new ArrayList<>();
    private int lastVisibleEvento;
    private List<EventoUi> eventoUiList = new ArrayList<>();
    private List<EventoUi> eventoUiFirebaseList = new ArrayList<>();
    private EventoUi eventosUiSelected;
    private EventoUi.AdjuntoUi adjuntoUiPreviewSelected;
    GetEventoAgenda getEventoAgenda;
    private RetrofitCancel cancelEventoAgenda;
    private GetLikeSaveCountLike getLikeSaveCountLike;
    private SaveLike saveLike;
    private MainView.InformacionEvento informacionEventoView;
    private MainView.AdjuntoPreviewEvento adjuntoPreviewEventoView;
    private MainView.AdjuntoEventoDownload adjuntoEventoDownloadView;
    //endregion
    private MainView.TabCursos tabCursosView;
    private GetIconoPortalAlumno getIconoPortalAlumno;
    private MainView.TabFamilia tabFamiliaView;
    private Object personToogleUi;
    private RetrofitCancel retrofitCancel;
    private MainView.TabQR tabQR;
    private GetColorTarjetaQR getColorTarjetaQR;
    private GetNombreServidor getNombreServidor;

    public MainPresenterImpl(UseCaseHandler handler, Resources res, GetCursos getCursos, UpdateCalendarioPeriodo calendarioPeriodo, UpdateFirebaseTipoNota updateFirebaseTipoNota,
                             Online online, GetEventoAgenda getEventoAgenda, GetLikeSaveCountLike getLikeSaveCountLike, SaveLike saveLike, GetIconoPortalAlumno getIconoPortalAlumno,
                             GetColorTarjetaQR getColorTarjetaQR, GetNombreServidor getNombreServidor) {
        super(handler, res);
        this.getCursos = getCursos;
        this.calendarioPeriodo = calendarioPeriodo;
        this.updateFirebaseTipoNota = updateFirebaseTipoNota;
        this.online = online;
        this.getEventoAgenda = getEventoAgenda;
        this.getLikeSaveCountLike = getLikeSaveCountLike;
        this.saveLike = saveLike;
        this.getIconoPortalAlumno = getIconoPortalAlumno;
        this.getColorTarjetaQR = getColorTarjetaQR;
        this.getNombreServidor = getNombreServidor;
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

        retrofitCancel = getCursos.execute(new GetCursos.Callback() {
            @Override
            public void getAlumno(AlumnoUi alumnoUi) {
                MainPresenterImpl.this.alumnoUi = alumnoUi;
            }

            @Override
            public void getAnioAcademico(AnioAcademicoUi anioAcademicoUi) {
                //AnioAcademicoUi anioAcademicoUi = response.getAnioAcademicoUi();
                if(view!=null)view.setNameAnioAcademico("Año Academ. " + anioAcademicoUi.getNombre());
                MainPresenterImpl.this.anioAcademicoUi = anioAcademicoUi;
                cancel = calendarioPeriodo.execute(anioAcademicoUi.getAnioAcademicoId(), new UpdateCalendarioPeriodo.Callback() {
                    @Override
                    public void onSucess() {
                        //this.getColorTarjetaQR = getColorTarjetaQR;
                        if(tabQR!=null) tabQR.colorTarjetaQr(getColorTarjetaQR.execute());
                        showQR();
                    }

                    @Override
                    public void onError() {

                    }
                });
            }

            @Override
            public void onResponse(boolean success, GetCursos.Response response) {
                showProgresCurso = false;
                MainPresenterImpl.this.anioAcademicoUi = response.getAnioAcademicoUi();
                MainPresenterImpl.this.alumnoUi = response.getAlumnoUi();
                if(view!=null)view.setNameAnioAcademico("Año Academ. " + anioAcademicoUi.getNombre());
                if(tabQR!=null) tabQR.logoEntidad(response.getAnioAcademicoUi());
                if(tabQR!=null) tabQR.fotoAlumno(response.getAlumnoUi());
                setupProgramaEducativo(response.getProgramaEduactivoUIList());
                if(tabQR!=null) tabQR.programaEval(programaEducativo);
                showQR();
                if(tabQR!=null) tabQR.colorTarjetaQr(getColorTarjetaQR.execute());
                MainPresenterImpl.this.cursosUiList.addAll(response.getCursosUiList());
                setupCurso();
                //iCRMEdu.variblesGlobales.setHabilitarAcceso(alumnoUi.getHabilitarAcceso());
                //iCRMEdu.variblesGlobales.setBloqueoAcceso(!alumnoUi.getHabilitarAcceso());
                //Por si se crea una actividad es remplasada se crea la actidad bloque de usuario
                if(view!=null)view.initBloqueo();
                //if(!alumnoUi.getHabilitarAcceso()){
                // if(view!=null)view.showActivtyBloqueo();
                //}
                if(view!=null)view.setChangeIconoPortal(getIconoPortalAlumno.execute());

                if(view!=null)view.changeNombreUsuario(alumnoUi.getNombre());
                if(view!=null)view.changeFotoUsuario(alumnoUi.getFoto());
                if(view!=null)view.changeFotoUsuarioApoderado(alumnoUi.getFotoApoderado());
            }
        });

        if(view!=null)view.changeNombreUsuario(alumnoUi.getNombre());
        if(view!=null)view.changeFotoUsuario(alumnoUi.getFoto());
        if(view!=null)view.changeFotoUsuarioApoderado(alumnoUi.getFotoApoderado());

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
        List<CursosUi> cursosUiList = new ArrayList<>();
        for (CursosUi cursosUi : this.cursosUiList){
            if(programaEducativo!=null&&
                    cursosUi.getProgramaEduactivoUI()!=null&&
                    cursosUi.getProgramaEduactivoUI().getIdPrograma()==programaEducativo.getIdPrograma()){
                cursosUiList.add(cursosUi);
            }
        }
        if(showProgresCurso){
            if(tabCursosView!=null)tabCursosView.showProgress();
        }else {
            if(tabCursosView!=null)tabCursosView.hideProgress();
        }
        if(tabCursosView!=null)tabCursosView.showListCurso(cursosUiList);
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
            case Acceso_con_Google:
                if(view!=null) view.accederGoogle();
                break;
            case Forzar_conexion:
                if(view!=null) view.mostrarDialogoForzarConexion();
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
        configuracionUiList.add(ConfiguracionUi.Forzar_conexion);
        configuracionUiList.add(ConfiguracionUi.Acceso_con_Google);
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
        iCRMEdu.variblesGlobales.setPersonaId(alumnoUi.getPersonaId());
        iCRMEdu.variblesGlobales.setEntidadId(anioAcademicoUi.getEntidadId());
        iCRMEdu.variblesGlobales.setGeoreferenciaId(anioAcademicoUi.getGeoreferenciaId());
        //iCRMEdu.variblesGlobales.setProgramEducativoId(idPrograma);
        if(programaEducativo!=null)if(view!=null)view.showTabCursoActivity();
        if(view!=null)view.servicePasarAsistencia(cursosUi.getSilaboEventoId());
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
    public void onInitCuentaFirebase() {
        updateFirebaseTipoNota();
    }

    @Override
    public void onClickTipoEvento(TipoEventoUi tiposEventosUi) {
        showProgress();
        selectedTipoEventoUi = tiposEventosUi;
        for(TipoEventoUi item : tipoEventoList)item.setToogle(false);
        tiposEventosUi.setToogle(true);
        if(tabEventoView!=null)tabEventoView.setTiposList(tipoEventoList);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getEvento( selectedTipoEventoUi, false);
            }
        }, 1000);
    }

    @Override
    public void renderCountEvento(EventoUi eventosUi, Callback<EventoUi> callback) {
        int position = eventoUiFirebaseList.indexOf(eventosUi);
        if(position==-1){
            eventoUiFirebaseList.add(eventosUi);
            getLikeSaveCountLike.execute(eventosUi, new GetLikeSaveCountLike.Callback() {
                @Override
                public void onResponse(EventoUi eventoUi) {
                    callback.onSuccess(eventosUi);
                }
            });
        }
    }

    @Override
    public void onClikLike(EventoUi eventosUi) {
        if(view!=null&&view.isInternetAvailable()){
            Log.d(getTag(), "onClikLike: "+ eventosUi.isLike());
            if(!eventosUi.isLike()){
                eventosUi.setLike(true);
            }else {
                eventosUi.setLike(false);
            }

            int cantidad = eventosUi.getCantLike();
            if(eventosUi.isLike()){
                cantidad++;
            }else {
                cantidad--;
            }

            if(cantidad<0)cantidad = 0;

            eventosUi.setCantLike(cantidad);

            if (tabEventoView!=null)tabEventoView.showListEventos(false,eventoUiList);

            saveLike.execute(eventosUi);
        }else {
            if(view!=null)view.showMessage("Por favor, revisa tu conexión a Internet.");
        }
    }

    @Override
    public void onClickCompartir(EventoUi eventoUi) {
        if(view!=null)view.startCompartirEvento(eventoUi);
    }

    @Override
    public void onClickAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi) {
        eventosUiSelected = eventoUi;
        adjuntoUiPreviewSelected = adjuntoUi;
        if(eventosUiSelected.getAdjuntoUiPreviewList().size()>1){
            if(view!=null)view.showDialogListaBannerEvento();
        }else {
            if(view!=null)view.showDialogAdjuntoEvento();
        }
    }

    @Override
    public void onClickAdjunto(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi, boolean more) {
        if(more){
            eventosUiSelected = eventoUi;
            if(view!=null)view.showDialogEventoDownload();
        }else {
            switch (adjuntoUi.getTipoArchivo()){
                case DOCUMENTO:
                case HOJACALCULO:
                case PRESENTACION:
                case IMAGEN:
                case PDF:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveDocumento(adjuntoUi.getDriveId(), adjuntoUi.getTitulo()));
                    if(view!=null)view.showPreviewArchivo();
                    break;
                case VIDEO:
                case AUDIO:
                    iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveMultimedia(adjuntoUi.getDriveId(), adjuntoUi.getTitulo()));
                    if(view!=null)view.showMultimediaPlayer();
                    break;
                default:
                    if(view!=null)view.showVinculo(adjuntoUi.getTitulo());
                    break;
            }
        }
    }

    @Override
    public void itemLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi) {
        if(view!=null)view.showVinculo(adjuntoUi.getTitulo());
    }

    @Override
    public void changeFirsthPostion(int lastVisibleItem) {
        this.lastVisibleEvento = lastVisibleItem;
        Log.d(getTag(), "lastVisibleItem: " + lastVisibleItem);

        if(lastVisibleItem>0&&eventoUiList.size()>0){
            if(tabEventoView!=null)tabEventoView.showFloatingButton();
        }else {
            if(tabEventoView!=null)tabEventoView.hideFloatingButton();
        }
    }

    @Override
    public void onRefreshEventos() {
        getEvento(selectedTipoEventoUi, true);
    }

    @Override
    public void attachView(MainView.TabEvento tabEventoView) {
        this.tabEventoView = tabEventoView;
        tabEventoView.showProgress();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getEvento(selectedTipoEventoUi, false);
            }
        },300);
    }

    @Override
    public void onTabEventoDestroy() {
        tabEventoView = null;
        if(cancelEventoAgenda!=null)cancelEventoAgenda.cancel();
    }

    @Override
    public void onClikLikeInfoEvento() {
        if(eventosUiSelected!=null){
            onClikLike(eventosUiSelected);
            if(informacionEventoView!=null)informacionEventoView.changeLike(eventosUiSelected);
            if(adjuntoPreviewEventoView!=null)adjuntoPreviewEventoView.changeLike(eventosUiSelected);
        }
    }

    @Override
    public void onClikLikeInfoCompartir() {
        if(eventosUiSelected!=null)
            if(view!=null)view.startCompartirEvento(eventosUiSelected);
    }

    @Override
    public void onClickDialogListBannerAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi) {
        eventosUiSelected = eventoUi;
        adjuntoUiPreviewSelected = adjuntoUi;
        if(view!=null)view.showDialogAdjuntoEvento();
    }

    @Override
    public void attachView(MainView.InformacionEvento informacionEventoView) {
        this.informacionEventoView = informacionEventoView;
        if(eventosUiSelected !=null && adjuntoUiPreviewSelected != null){
            informacionEventoView.showInformacionEvento(eventosUiSelected, adjuntoUiPreviewSelected, false);
        }else if(eventosUiSelected != null) {
            informacionEventoView.showInformacionEvento(eventosUiSelected, null, true);
        }
    }

    @Override
    public void onInformacionEventoDestroy() {
        informacionEventoView = null;
    }

    @Override
    public void attachView(MainView.AdjuntoPreviewEvento adjuntoPreviewEventoView) {
        this.adjuntoPreviewEventoView = adjuntoPreviewEventoView;
        adjuntoPreviewEventoView.showInformacionEventoAjunto(eventosUiSelected, adjuntoUiPreviewSelected);
    }

    @Override
    public void onAdjuntoPreviewEventoDestroy() {
        adjuntoPreviewEventoView = null;
    }

    @Override
    public void onClickDialogAdjunto(EventoUi.AdjuntoUi adjuntoUi) {
        switch (adjuntoUi.getTipoArchivo()){
            case DOCUMENTO:
            case HOJACALCULO:
            case PRESENTACION:
            case IMAGEN:
            case PDF:
                iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveDocumento(adjuntoUi.getDriveId(), adjuntoUi.getTitulo()));
                if(view!=null)view.showPreviewArchivo();
                break;
            case VIDEO:
            case AUDIO:
                iCRMEdu.variblesGlobales.setGbPreview(GbPreview.Build.setupDriveMultimedia(adjuntoUi.getDriveId(), adjuntoUi.getTitulo()));
                if(view!=null)view.showMultimediaPlayer();
                break;
            default:
                if(view!=null)view.showVinculo(adjuntoUi.getTitulo());
                break;
        }
    }

    @Override
    public void attachView(MainView.AdjuntoEventoDownload adjuntoEventoDownloadView) {
        this.adjuntoEventoDownloadView = adjuntoEventoDownloadView;
        adjuntoEventoDownloadView.setList(eventosUiSelected.getAdjuntoUiList());
    }

    @Override
    public void onAdjuntoEventoDownloadDestroy() {
        adjuntoEventoDownloadView = null;
    }

    @Override
    public void attachView(MainView.TabCursos tabCursosView) {
        this.tabCursosView = tabCursosView;
        setupCurso();
    }

    @Override
    public void onTabCursosDestroy() {
        tabCursosView = null;
    }

    @Override
    public void onClickTooglePersona(Object personUi) {
        if(personUi instanceof HijoUi){
            ((HijoUi)personUi).setToogle(!((HijoUi)personUi).getToogle());
        }else if(personUi instanceof FamiliaUi){
            ((FamiliaUi)personUi).setToogle(!((FamiliaUi)personUi).getToogle());
        }
        if (tabFamiliaView!=null)tabFamiliaView.updateFamiliar(personUi);

        if(personToogleUi!=null&&!personToogleUi.equals(personUi)){
            if(personToogleUi instanceof HijoUi){
                ((HijoUi)personToogleUi).setToogle(false);
            }else if(personToogleUi instanceof FamiliaUi){
                ((FamiliaUi)personToogleUi).setToogle(false);
            }
            if (tabFamiliaView!=null)tabFamiliaView.updateFamiliar(personToogleUi);
        }
        personToogleUi = personUi;
    }

    @Override
    public void attachView(MainView.TabFamilia tabFamiliaView) {
        this. tabFamiliaView=tabFamiliaView;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (alumnoUi!=null){
                    List<Object> familiaList = new ArrayList<>(alumnoUi.getFamiliaUiList());
                    if(tabFamiliaView!=null)tabFamiliaView.showFamilia(alumnoUi,familiaList );
                }
            }
        },300);
    }

    @Override
    public void onTabFamiliaDestroy() {
        tabFamiliaView = null;
    }

    @Override
    public void onErrorCuentaFirebase() {
        if(view!=null)view.showMessage("User is signed out");
    }

    @Override
    public void attachView(MainView.TabQR tabQR) {
        this.tabQR=tabQR;

        this.tabQR.tarjetaUsuario(alumnoUi, programaEducativo);
        if(tabQR!=null) tabQR.logoEntidad(anioAcademicoUi);
        if(tabQR!=null) tabQR.fotoAlumno(alumnoUi);
        if(tabQR!=null) tabQR.colorTarjetaQr(getColorTarjetaQR.execute());
        showQR();
        if(tabQR!=null) tabQR.programaEval(programaEducativo);
    }

    void showQR(){
        if(tabQR!=null&&alumnoUi!=null)this.tabQR.showQR(alumnoUi.getPersonaId() +'*' + getNombreServidor.execute()  + "|" + alumnoUi.getNombre2());
    }

    @Override
    public void onTabQRDestroy() {
        this.tabQR = null;
    }

    boolean actualizarUnavezTipoNota;
    @Override
    public void onStart() {
        super.onStart();
        mainPause = false;
        if(nuevaVersionUi!=null){
            if(view!=null)view.showNuevaversion(nuevaVersionUi);
            nuevaVersionUi = null;
        }
        //String usuarioFirebase = firebaseNode + "_" + (!TextUtils.isEmpty(usuario)?usuario.replaceAll(" ","_"):usuario) +"@gmail.com";
        //if(view!=null)view.validateFirebase(usuarioFirebase, usuarioFirebase);
        if(!actualizarUnavezTipoNota){
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateFirebaseTipoNota();
                }
            },2000);
        }
        actualizarUnavezTipoNota = true;
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

    void getEvento( TipoEventoUi selectedTipoEventoUi, boolean posicionIncial) {
        eventoUiFirebaseList.clear();
        if(alumnoUi==null||selectedTipoEventoUi==null)return;

        eventoUiList.clear();
        if(tabEventoView!=null)tabEventoView.setEventoList(new ArrayList<>());
        if(tabEventoView!=null)tabEventoView.showProgress();
        cancelEventoAgenda = getEventoAgenda.execute(new GetEventoAgenda.GetEventoAgendaParams(alumnoUi.getUsuarioId(), alumnoUi.getPersonaId(),selectedTipoEventoUi), new GetEventoAgenda.Callback() {
            @Override
            public void onPreload(GetEventoAgenda.GetEventoAgendaResponse response) {
                tipoEventoList.clear();
                tipoEventoList.addAll(response.getTipoEventoUiList());
                if(tabEventoView!=null)tabEventoView.setTiposList(tipoEventoList);
                eventoUiList.clear();
                //if(tabEvento!=null)tabEvento.showListEventos(posicionIncial,eventoUiList);
            }

            @Override
            public void onLoad(GetEventoAgenda.GetEventoAgendaResponse response) {
                tipoEventoList.clear();
                tipoEventoList.addAll(response.getTipoEventoUiList());
                if(tabEventoView!=null)tabEventoView.setTiposList(tipoEventoList);
                eventoUiList.clear();
                eventoUiList.addAll(response.getEventoUiList());
                if(tabEventoView!=null)tabEventoView.showListEventos(posicionIncial,eventoUiList);
                if(tabEventoView!=null)tabEventoView.shideProgress();
            }
        });
    }
}
