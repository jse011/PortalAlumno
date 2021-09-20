package com.consultoraestrategia.ss_portalalumno.main;

import androidx.fragment.app.Fragment;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.NuevaVersionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;

import java.util.ArrayList;
import java.util.List;

public interface MainView extends BaseView<MainPresenter> {
    void showMenuList(ArrayList<Object> objects);

    void showActivityLogin();

    void showTabCursoActivity();

    void changeNombreUsuario(String nombre);

    void changeFotoUsuario(String foto);

    void mostrarDialogoCerrarSesion();

    void cerrarSesion();

    void setNameAnioAcademico(String nombre);

    void changeFotoUsuarioApoderado(String fotoApoderado);

    void validateFirebase(String usuarioFirebase, String passwordFirebase);

    void showActivtyBloqueo();

    void initBloqueo();

    void showNuevaversion(NuevaVersionUi nuevaVersionUi);

    void showVinculo(String titulo);

    void startCompartirEvento(EventoUi eventoUi);

    boolean isInternetAvailable();

    void showDialogListaBannerEvento();

    void showDialogAdjuntoEvento();

    void showDialogEventoDownload();

    void showPreviewArchivo();

    void showMultimediaPlayer();

    void setChangeIconoPortal(String url);

    void servicePasarAsistencia(int silaboEventoId);

    void accederGoogle();

    interface TabEvento {

        void setTiposList(List<TipoEventoUi> tipoEventoUiList);

        void setEventoList(List<EventoUi> eventoUiList);

        void setPresenter(MainPresenter presenter);

        void showProgress();

        void shideProgress();

        void showFloatingButton();

        void hideFloatingButton();

        void showListEventos(boolean posicionIncial, List<EventoUi> eventoUiList);
    }

    interface InformacionEvento {
        void showInformacionEvento(EventoUi eventosUi, EventoUi.AdjuntoUi adjuntoUi, boolean mostrarRecursos);
        void changeLike(EventoUi eventosUi);
        void showCompartirEvento(EventoUi eventosUi);

        void hideInformacionEvento();

        void setPresenter(MainPresenter presenter);
    }

    interface AdjuntoPreviewEvento {
        void showInformacionEventoAjunto(EventoUi eventosUi, EventoUi.AdjuntoUi adjuntoUi);
        void changeLike(EventoUi eventosUi);
        void setPresenter(MainPresenter presenter);
    }

    interface AdjuntoEventoDownload{
        void setPresenter(MainPresenter presenter);

        void setList(List<EventoUi.AdjuntoUi> adjuntoUiList);
    }

     interface TabCursos {

         void showListCurso(List<CursosUi> cursosUiList);
         void setPresenter(MainPresenter presenter);
    }

    interface TabFamilia {
        void setPresenter(MainPresenter presenter);
        void showFamilia(AlumnoUi usuario, List<Object> familia);
        void updateFamiliar(Object personUi);
    }
}
