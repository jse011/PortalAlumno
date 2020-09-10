package com.consultoraestrategia.ss_portalalumno.global.entities;

public class GbPreview {
    public static final int DOCUMETO = 0, MULTIMEDIA = 1;
    private String tareaId;
    private String nombreArchivoPreview;
    private String driveId;
    private int tipo;
    private int sesionAprendizajeId;
    private String youtube;

    private GbPreview() {
    }

    public static class Build {
        public static GbPreview setupYoutube(String url){
            GbPreview gbPreview = new GbPreview();
            gbPreview.youtube = url;
            return gbPreview;
        }

        public static GbPreview setupDriveDocumento(String driveId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.driveId = driveId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = DOCUMETO;
            return gbPreview;
        }

        public static GbPreview setupDriveMultimedia(String driveId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.driveId = driveId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = MULTIMEDIA;
            return gbPreview;
        }

        public static GbPreview setupFirbaseTareaDocumento(String tareaId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.tareaId = tareaId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = DOCUMETO;
            return gbPreview;
        }

        public static GbPreview setupFirbaseTareaMultimedia(String tareaId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.tareaId = tareaId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = MULTIMEDIA;
            return gbPreview;
        }

        public static GbPreview setupFirbaseEvidenciaDocumento(int sesionAprendizajeId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.sesionAprendizajeId = sesionAprendizajeId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = DOCUMETO;
            return gbPreview;
        }

        public static GbPreview setupFirbaseEvidenciaMultimedia(int sesionAprendizajeId, String nombreArchivo){
            GbPreview gbPreview = new GbPreview();
            gbPreview.sesionAprendizajeId = sesionAprendizajeId;
            gbPreview.nombreArchivoPreview = nombreArchivo;
            gbPreview.tipo = MULTIMEDIA;
            return gbPreview;
        }
    }

    public String getTareaId() {
        return tareaId;
    }

    public String getNombreArchivoPreview() {
        return nombreArchivoPreview;
    }

    public String getDriveId() {
        return driveId;
    }

    public int getTipo() {
        return tipo;
    }

    public int getSesionAprendizajeId() {
        return sesionAprendizajeId;
    }

    public String getYoutube() {
        return youtube;
    }
}
