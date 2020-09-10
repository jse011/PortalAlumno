package com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments;

import androidx.fragment.app.Fragment;

public interface TabSesionCallback {
    void showYoutubeActividad(String url, String nombre);

    void showPreviewArchivo(String driveId, String nombreRecurso);

    void showMultimediaPlayer(String driveId, String nombreRecurso);
}
