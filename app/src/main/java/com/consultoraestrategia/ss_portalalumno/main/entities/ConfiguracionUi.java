package com.consultoraestrategia.ss_portalalumno.main.entities;

/**
 * Created by SCIEV on 19/07/2018.
 */

public enum ConfiguracionUi
{
    INFORMACION ("Información"),
    BORRAR_CACHE("Borrar cache"),
    ALARMA("Hora de sincronización"),
    CONTACTOS("Actualizar Contactos"),
    CERRAR_SESION ("Cerrar Sesión");

    private final String name;

    private ConfiguracionUi(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

}
