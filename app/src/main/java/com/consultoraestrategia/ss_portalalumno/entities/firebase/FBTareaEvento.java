package com.consultoraestrategia.ss_portalalumno.entities.firebase;

import java.util.Map;

public class FBTareaEvento {
    private String FechaEntrega;
    private String HoraEntrega;
    private String Instrucciones;
    private int Numero;
    private int SesionAprendizajeId;
    private String SesionNombre;
    private String TareaId;
    private String Titulo;
    private Map<String,FBRecursoTarea> RecursosTarea;

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    public String getHoraEntrega() {
        return HoraEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        HoraEntrega = horaEntrega;
    }

    public String getInstrucciones() {
        return Instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        Instrucciones = instrucciones;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public int getSesionAprendizajeId() {
        return SesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        SesionAprendizajeId = sesionAprendizajeId;
    }

    public String getSesionNombre() {
        return SesionNombre;
    }

    public void setSesionNombre(String sesionNombre) {
        SesionNombre = sesionNombre;
    }

    public String getTareaId() {
        return TareaId;
    }

    public void setTareaId(String tareaId) {
        TareaId = tareaId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public Map<String, FBRecursoTarea> getRecursosTarea() {
        return RecursosTarea;
    }

    public void setRecursosTarea(Map<String, FBRecursoTarea> recursosTarea) {
        RecursosTarea = recursosTarea;
    }

    public static class FBRecursoTarea{
        private String ArchivoId;
        private String Descripcion;
        private String RecursoDidacticoId;
        private String TareaId;
        private int TipoId;
        private String Titulo;
        private String Url;

        public String getArchivoId() {
            return ArchivoId;
        }

        public void setArchivoId(String archivoId) {
            ArchivoId = archivoId;
        }

        public String getDescripcion() {
            return Descripcion;
        }

        public void setDescripcion(String descripcion) {
            Descripcion = descripcion;
        }

        public String getRecursoDidacticoId() {
            return RecursoDidacticoId;
        }

        public void setRecursoDidacticoId(String recursoDidacticoId) {
            RecursoDidacticoId = recursoDidacticoId;
        }

        public String getTareaId() {
            return TareaId;
        }

        public void setTareaId(String tareaId) {
            TareaId = tareaId;
        }

        public int getTipoId() {
            return TipoId;
        }

        public void setTipoId(int tipoId) {
            TipoId = tipoId;
        }

        public String getTitulo() {
            return Titulo;
        }

        public void setTitulo(String titulo) {
            Titulo = titulo;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }
}
