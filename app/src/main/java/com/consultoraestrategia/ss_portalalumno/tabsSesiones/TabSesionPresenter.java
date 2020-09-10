package com.consultoraestrategia.ss_portalalumno.tabsSesiones;


import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionActividadView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionColaborativaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionEvidenciaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionInstrumentoView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionPreguntaView;

public interface TabSesionPresenter extends BasePresenter<TabSesionView> {

    void attachView(TabSesionPreguntaView tabSesionPreguntaView);

    void attachView(TabSesionInstrumentoView tabSesionInstrumentoView);

    void attachView(TabSesionActividadView tabSesionActividadView);

    void onTabSesionActividadDestroy();

    void onTabSesionInstrumentoViewDestroy();

    void onTabSesionPreguntaViewDestroy();

    void attachView(TabSesionColaborativaView tabSesionColaborativaView);

    void onTabSesionColaborativaViewDestroy();

    void onVolverCargar();

    void attachView(TabSesionEvidenciaView tabSesionEvidenciaView);

    void onTabSesionEvidenciaViewDestroy();
}
