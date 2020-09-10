package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;

import java.util.List;

public interface TabCursoView extends BaseView<TabCursoPresenter> {

    void showTitle(String title);

    void showSubtitle(String subtitle);

    void showAppbarBackground(String bg, String bgColor);

    void showPeriodoList(List<PeriodoUi> periodoAcademicoList, String parametroColor3);

    void changeColorToolbar(String parametroColor1);

    void changeColorFloatButon(String parametroColor2);

    void changePeriodo();

    void modoOffline();

    void modoOnline();
}