package com.consultoraestrategia.ss_portalalumno.main.entities;

public enum TipoArchivo {DOCUMENTO("Documento"), IMAGEN("Imagen"), PDF("Documento Portátil"), PRESENTACION("Presentación"), HOJACALCULO("Hoja de cálculo"), VIDEO("Video"), AUDIO("Múscia"), YOUTUBE(""), DRIVE(""), LINK(""), ENCUESTA("Encuesta"),OTROS("Desconocido");
    private String nombre;

    TipoArchivo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
