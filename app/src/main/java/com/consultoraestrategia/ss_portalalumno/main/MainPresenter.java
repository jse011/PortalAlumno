package com.consultoraestrategia.ss_portalalumno.main;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.entities.Valor;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;

public interface MainPresenter extends BasePresenter<MainView> {
    void onProgramaEducativoUIClicked(ProgramaEduactivoUI programaEduactivosUI);
    void onAccesoSelected(UsuarioAccesoUI usuarioAccesoUI);
    void onClickConfiguracion(ConfiguracionUi configuracionUi);

    void onIbtnProgramaClicked();

    void onClickBtnAcceso();

    void onClickBtnConfiguracion();

    void onClickCurso(CursosUi cursosUi);

    void onClickDialogoCerrarSesion();

    void nuevaVersionDisponible(String newVersionCode, String changes);

    void onInitCuentaFirebase();

    void onClickTipoEvento(TipoEventoUi tiposEventosUi);

    void renderCountEvento(EventoUi eventoUi, Callback<EventoUi> callback);

    void onClikLike(EventoUi eventosUi);

    void onClickCompartir(EventoUi eventoUi);

    void onClickAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi);

    void onClickAdjunto(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi, boolean more);

    void itemLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi);

    void changeFirsthPostion(int lastVisibleItem);

    void onRefreshEventos();

    void attachView(MainView.TabEvento tabEventoView);

    void onTabEventoDestroy();

    void onClikLikeInfoEvento();

    void onClikLikeInfoCompartir();

    void onClickDialogListBannerAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi);

    void attachView(MainView.InformacionEvento informacionEventoView);

    void onInformacionEventoDestroy();

    void attachView(MainView.AdjuntoPreviewEvento adjuntoPreviewEventoView);

    void onAdjuntoPreviewEventoDestroy();

    void onClickDialogAdjunto(EventoUi.AdjuntoUi adjuntoUi);

    void attachView(MainView.AdjuntoEventoDownload adjuntoEventoDownloadView);

    void onAdjuntoEventoDownloadDestroy();

    void attachView(MainView.TabCursos tabCursosView);

    void onTabCursosDestroy();

    void onClickTooglePersona(Object personUi);

    void attachView(MainView.TabFamilia tabFamiliaView);

    void onTabFamiliaDestroy();

    void onErrotCuentaFirebase();


    interface Callback<T> {
        void onSuccess(T item);
    }
}
