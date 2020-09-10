package com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments;

public interface TabSesionPreguntaView {
    void addPregunta(String preguntaPAId);

    void updatePregunta(String preguntaPAId);

    void removePregunta(String preguntaPAId);

    void updatePreguntaAlumno(String preguntaPAId, int alumnoId);

    void onInitPreguntaList(boolean online);
}
