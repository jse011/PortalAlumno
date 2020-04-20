package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import com.consultoraestrategia.ss_portalalumno.base.activity.BasePresenter;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;


/**
 * Created by kike on 29/03/2018.
 */

public interface TabCursoPresenter extends BasePresenter<TabCursoView> {

    void onPeriodoSelected(PeriodoUi periodoUi);
}
