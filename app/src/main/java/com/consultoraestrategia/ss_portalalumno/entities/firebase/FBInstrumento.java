package com.consultoraestrategia.ss_portalalumno.entities.firebase;

import java.util.HashMap;

public class FBInstrumento {
    private int CantidadPreguntas;
    private String FechaCierre;
    private String FechaLanzamiento;
    private String Icono;
    private String Imagen;
    private int InstrumentoEvalId;
    private String Nombre;
    private HashMap<String, FBVariables> Variables;
    private String InstrumentoObservadoId;
    private int SesionAprendizajeId;
    private int SilaboEventoId;

    @Override
    public String toString() {
        return "FBInstrumento{" +
                "CantidadPreguntas=" + CantidadPreguntas +
                ", FechaCierre='" + FechaCierre + '\'' +
                ", FechaLanzamiento='" + FechaLanzamiento + '\'' +
                ", Icono='" + Icono + '\'' +
                ", Imagen='" + Imagen + '\'' +
                ", InstrumentoEvalId=" + InstrumentoEvalId +
                ", Nombre='" + Nombre + '\'' +
                ", Variables=" + Variables +
                ", InstrumentoObservadoId='" + InstrumentoObservadoId + '\'' +
                ", SesionAprendizajeId=" + SesionAprendizajeId +
                ", SilaboEventoId=" + SilaboEventoId +
                '}';
    }

    public int getCantidadPreguntas() {
        return CantidadPreguntas;
    }

    public void setCantidadPreguntas(int cantidadPreguntas) {
        CantidadPreguntas = cantidadPreguntas;
    }

    public String getFechaCierre() {
        return FechaCierre;
    }

    public void setFechaCierre(String fechaCierre) {
        FechaCierre = fechaCierre;
    }

    public String getFechaLanzamiento() {
        return FechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        FechaLanzamiento = fechaLanzamiento;
    }

    public String getIcono() {
        return Icono;
    }

    public void setIcono(String icono) {
        Icono = icono;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getInstrumentoEvalId() {
        return InstrumentoEvalId;
    }

    public void setInstrumentoEvalId(int instrumentoEvalId) {
        InstrumentoEvalId = instrumentoEvalId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public HashMap<String, FBVariables> getVariables() {
        return Variables;
    }

    public void setVariables(HashMap<String, FBVariables> variables) {
        Variables = variables;
    }

    public String getInstrumentoObservadoId() {
        return InstrumentoObservadoId;
    }

    public void setInstrumentoObservadoId(String instrumentoObservadoId) {
        InstrumentoObservadoId = instrumentoObservadoId;
    }

    public int getSesionAprendizajeId() {
        return SesionAprendizajeId;
    }

    public void setSesionAprendizajeId(int sesionAprendizajeId) {
        SesionAprendizajeId = sesionAprendizajeId;
    }

    public int getSilaboEventoId() {
        return SilaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        SilaboEventoId = silaboEventoId;
    }

    public static class FBVariables{
        private int Respondida;
        private int bancoPreguntaId;
        private int dimensionId;
        private int disenioPreguntaId;
        private String etiqueta;
        private String iconoVariable;
        private String inputRespuesta;
        private int instrumentoEvalId;
        private int instrumentoVariableId;
        private int longitudPaso;
        private int medidaId;
        private int nivelPreguntaId;
        private String nombre;
        private int orden;
        private int parentId;
        private String path;
        private double peso;
        private int puntaje;
        private int puntajeObtenido;
        private int tipoInputRespuestaId;
        private int tipoPreguntaId;
        private int tipoRespuestaId;
        private int tipoVariableId;
        private int valorMaximo;
        private int valorMinimo;
        private int variableId;
        private int valorId;
        private String respuestaActual;
        private HashMap<String, FBValores> valores;
        public int getRespondida() {
            return Respondida;
        }

        public void setRespondida(int respondida) {
            Respondida = respondida;
        }

        public int getBancoPreguntaId() {
            return bancoPreguntaId;
        }

        public void setBancoPreguntaId(int bancoPreguntaId) {
            this.bancoPreguntaId = bancoPreguntaId;
        }

        public int getDimensionId() {
            return dimensionId;
        }

        public void setDimensionId(int dimensionId) {
            this.dimensionId = dimensionId;
        }

        public int getDisenioPreguntaId() {
            return disenioPreguntaId;
        }

        public void setDisenioPreguntaId(int disenioPreguntaId) {
            this.disenioPreguntaId = disenioPreguntaId;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public void setEtiqueta(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public String getIconoVariable() {
            return iconoVariable;
        }

        public void setIconoVariable(String iconoVariable) {
            this.iconoVariable = iconoVariable;
        }

        public String getInputRespuesta() {
            return inputRespuesta;
        }

        public void setInputRespuesta(String inputRespuesta) {
            this.inputRespuesta = inputRespuesta;
        }

        public int getInstrumentoEvalId() {
            return instrumentoEvalId;
        }

        public void setInstrumentoEvalId(int instrumentoEvalId) {
            this.instrumentoEvalId = instrumentoEvalId;
        }

        public int getInstrumentoVariableId() {
            return instrumentoVariableId;
        }

        public void setInstrumentoVariableId(int instrumentoVariableId) {
            this.instrumentoVariableId = instrumentoVariableId;
        }

        public int getLongitudPaso() {
            return longitudPaso;
        }

        public void setLongitudPaso(int longitudPaso) {
            this.longitudPaso = longitudPaso;
        }

        public int getMedidaId() {
            return medidaId;
        }

        public void setMedidaId(int medidaId) {
            this.medidaId = medidaId;
        }

        public int getNivelPreguntaId() {
            return nivelPreguntaId;
        }

        public void setNivelPreguntaId(int nivelPreguntaId) {
            this.nivelPreguntaId = nivelPreguntaId;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getOrden() {
            return orden;
        }

        public void setOrden(int orden) {
            this.orden = orden;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }

        public int getPuntaje() {
            return puntaje;
        }

        public void setPuntaje(int puntaje) {
            this.puntaje = puntaje;
        }

        public int getTipoInputRespuestaId() {
            return tipoInputRespuestaId;
        }

        public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
            this.tipoInputRespuestaId = tipoInputRespuestaId;
        }

        public int getTipoPreguntaId() {
            return tipoPreguntaId;
        }

        public void setTipoPreguntaId(int tipoPreguntaId) {
            this.tipoPreguntaId = tipoPreguntaId;
        }

        public int getTipoRespuestaId() {
            return tipoRespuestaId;
        }

        public void setTipoRespuestaId(int tipoRespuestaId) {
            this.tipoRespuestaId = tipoRespuestaId;
        }

        public int getTipoVariableId() {
            return tipoVariableId;
        }

        public void setTipoVariableId(int tipoVariableId) {
            this.tipoVariableId = tipoVariableId;
        }

        public int getValorMaximo() {
            return valorMaximo;
        }

        public void setValorMaximo(int valorMaximo) {
            this.valorMaximo = valorMaximo;
        }

        public int getValorMinimo() {
            return valorMinimo;
        }

        public void setValorMinimo(int valorMinimo) {
            this.valorMinimo = valorMinimo;
        }

        public int getVariableId() {
            return variableId;
        }

        public void setVariableId(int variableId) {
            this.variableId = variableId;
        }

        public HashMap<String, FBValores> getValores() {
            return valores;
        }

        public void setValores(HashMap<String, FBValores> valores) {
            this.valores = valores;
        }

        public int getPuntajeObtenido() {
            return puntajeObtenido;
        }

        public void setPuntajeObtenido(int puntajeObtenido) {
            this.puntajeObtenido = puntajeObtenido;
        }

        public int getValorId() {
            return valorId;
        }

        public void setValorId(int valorId) {
            this.valorId = valorId;
        }

        public String getRespuestaActual() {
            return respuestaActual;
        }

        public void setRespuestaActual(String respuestaActual) {
            this.respuestaActual = respuestaActual;
        }
    }

    public static class FBValores{
        private String descripcion;
        private String etiqueta;
        private String inputRespuesta;
        private String path;
        private int puntaje;
        private int tipoInputRespuestaId;
        private int valor;
        private int valorId;
        private int variableId;

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public void setEtiqueta(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public String getInputRespuesta() {
            return inputRespuesta;
        }

        public void setInputRespuesta(String inputRespuesta) {
            this.inputRespuesta = inputRespuesta;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPuntaje() {
            return puntaje;
        }

        public void setPuntaje(int puntaje) {
            this.puntaje = puntaje;
        }

        public int getTipoInputRespuestaId() {
            return tipoInputRespuestaId;
        }

        public void setTipoInputRespuestaId(int tipoInputRespuestaId) {
            this.tipoInputRespuestaId = tipoInputRespuestaId;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public int getValorId() {
            return valorId;
        }

        public void setValorId(int valorId) {
            this.valorId = valorId;
        }

        public int getVariableId() {
            return variableId;
        }

        public void setVariableId(int variableId) {
            this.variableId = variableId;
        }
    }
}
