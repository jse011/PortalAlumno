package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

import java.io.File;

public class TareaArchivoUi {
    private String descripcion;
    private String color;
    private int state = 0;//pause 0 ,  progresso 1
    private boolean disabled;
    private boolean entregado;

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public boolean getEntregado() {
        return entregado;
    }

    public enum Tipo{DOCUMENTO("Documento"), IMAGEN("Imagen"), PDF("Documento Portátil"), PRESENTACION("Presentación"), HOJACALCULO("Hoja de cálculo"), VIDEO("Video"), AUDIO("Múscia"), YOUTUBE(""), DRIVE(""), LINK(""), OTROS("Desconocido");
        private String nombre;

        Tipo(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }
    private String id;
    private String nombre;
    private String path;
    private Tipo tipo = Tipo.OTROS;
    private double progress;
    private Object file;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public static Tipo getType(String path){
        String extencion = path.toLowerCase();
        if (extencion.contains(".doc") || extencion.contains(".docx")) {
            // Word document
            return Tipo.DOCUMENTO;
        } else if (extencion.contains(".pdf")) {
            // PDF file
            return Tipo.PDF;
        } else if (extencion.contains(".ppt") || extencion.contains(".pptx")) {
            // Powerpoint file
            return Tipo.PRESENTACION;
        } else if (extencion.contains(".xls") || extencion.contains(".xlsx")||extencion.contains(".csv")) {
            // Excel file
            return Tipo.HOJACALCULO;
        } else if (extencion.contains(".zip") || extencion.contains(".rar")) {
            // WAV audio file
            return Tipo.HOJACALCULO;
        } else if (extencion.contains(".rtf")) {
            // RTF file
            return Tipo.OTROS;
        } else if (extencion.contains(".wav") || extencion.contains(".mp3")) {
            // WAV audio file
            return Tipo.AUDIO;
        } else if (extencion.contains(".gif")) {
            // GIF file
            return Tipo.IMAGEN;
        } else if (extencion.contains(".jpg") || extencion.contains(".jpeg") || extencion.contains(".png")) {
            // JPG file
            return Tipo.IMAGEN;
        } else if (extencion.contains(".txt")) {
            // Text file
            return Tipo.DOCUMENTO;
        } else if (extencion.contains(".3gp") || extencion.contains(".mpg") || extencion.contains(".mpeg") || extencion.contains(".mpe") || extencion.contains(".mp4") || extencion.contains(".avi")) {
            // Video files
            return Tipo.VIDEO;
        } else {
            // Other files
            return Tipo.OTROS;
        }
    }

    public Object getFile() {
        return file;
    }

    public void setFile(Object file) {
        this.file = file;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
