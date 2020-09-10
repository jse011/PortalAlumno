package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoTareaView;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoUnidadView;


/**
 * Created by kike on 29/03/2018.
 */

public interface TabCursoPresenter extends BasePresenter<TabCursoView> {

    void onPeriodoSelected(PeriodoUi periodoUi);
    void onVolverCargar();
    void attachView(TabCursoTareaView tabCursoTareaView);
    void attachView(TabCursoUnidadView tabCursoUnidadView);
    void onTabCursoTareaViewDestroyed();
    void onTabCursoUnidadViewDestroyed();
}
