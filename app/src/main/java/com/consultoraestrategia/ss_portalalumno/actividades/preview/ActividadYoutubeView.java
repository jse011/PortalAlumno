package com.consultoraestrategia.ss_portalalumno.actividades.preview;

import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseView;

public interface ActividadYoutubeView extends BaseView<ActividadYoutubePresenter> {
    void setDescripcionVideo(String nombreRecurso, String url, String color);

    void setListActividad(ActividadesUi actividadesUi, int position);

    void showButonsDrive();
}
