package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;

import java.util.List;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public class TareasUI  {
    public boolean calendarioVigente;
    public boolean calendarioEditar;
    private boolean entregaAlumno;
    private long fechaEntregaAlumno;
    private boolean retrasoEntrega;
    private String nota;
    private int tipoNotaId;
    private ParametroDisenioUi parametroDisenioUi;
    private int position;

    public boolean isCalendarioVigente() {
        return calendarioVigente;
    }

    public void setCalendarioVigente(boolean calendarioVigente) {
        this.calendarioVigente = calendarioVigente;
    }

    public boolean isCalendarioEditar() {
        return calendarioEditar;
    }

    public void setCalendarioEditar(boolean calendarioEditar) {
        this.calendarioEditar = calendarioEditar;
    }

    public void setEntregaAlumno(boolean entregaAlumno) {
        this.entregaAlumno = entregaAlumno;
    }

    public boolean getEntregaAlumno() {
        return entregaAlumno;
    }

    public void setFechaEntregaAlumno(long fechaEntregaAlumno) {
        this.fechaEntregaAlumno = fechaEntregaAlumno;
    }

    public long getFechaEntregaAlumno() {
        return fechaEntregaAlumno;
    }

    public boolean isRetrasoEntrega() {
        return retrasoEntrega;
    }

    public void setRetrasoEntrega(boolean retrasoEntrega) {
        this.retrasoEntrega = retrasoEntrega;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNota() {
        return nota;
    }

    public void setTipoNotaId(int tipoNotaId) {
        this.tipoNotaId = tipoNotaId;
    }

    public int getTipoNotaId() {
        return tipoNotaId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //IU

    public enum Estado{Creado("Creado","#000000"), Publicado("Publicado","#0090f7"), Eliminado("Eliminado","#ff0000");
        private String nombre;
        private String color;
        Estado(String nombre, String color) {
            this.nombre = nombre;
            this.color = color;
        }

        public String getNombre() {
            return nombre;
        }

        public String getColor() {
            return color;
        }
    }
    public String keyTarea;
    public String tituloTarea;
    public String descripcion;
    public String nombreCurso;
    public long fechaCreacionTarea;
    public long fechaLimite;
    public String horaEntrega;
    public String personaPuclicacion;
    public Estado estado = Estado.Creado;
    public boolean isDocente;
    public int idUnidaddAprendizaje;
    public int idSesionAprendizaje;
    public int idSilaboEvento;
    public boolean btnCreateVisible;
    public List<RecursosUI> recursosUIList;
    public RubroEvalProcesoUi rubroEvalProcesoUi;


    public TareasUI() {
    }

    public boolean isDocente() {
        return isDocente;
    }

    public void setDocente(boolean docente) {
        isDocente = docente;
    }

    public boolean isBtnCreateVisible() {
        return btnCreateVisible;
    }

    public void setBtnCreateVisible(boolean btnCreateVisible) {
        this.btnCreateVisible = btnCreateVisible;
    }

    public int getIdSesionAprendizaje() {
        return idSesionAprendizaje;
    }

    public void setIdSesionAprendizaje(int idSesionAprendizaje) {
        this.idSesionAprendizaje = idSesionAprendizaje;
    }

    public TareasUI(String keyTarea, String tituloTarea, String descripcion, String nombreCurso, long fechaCreacionTarea, long fechaLimite, String horaEntrega, String personaPuclicacion, Estado estado, boolean isDocente, int idUnidaddAprendizaje, int idSilaboEvento, boolean btnCreateVisible, List<RecursosUI> recursosUIList) {
        this.keyTarea = keyTarea;
        this.tituloTarea = tituloTarea;
        this.descripcion = descripcion;
        this.nombreCurso = nombreCurso;
        this.fechaCreacionTarea = fechaCreacionTarea;
        this.fechaLimite = fechaLimite;
        this.horaEntrega = horaEntrega;
        this.personaPuclicacion = personaPuclicacion;
        this.estado = estado;
        this.isDocente = isDocente;
        this.idUnidaddAprendizaje = idUnidaddAprendizaje;
        this.idSilaboEvento = idSilaboEvento;
        this.btnCreateVisible = btnCreateVisible;
        this.recursosUIList = recursosUIList;
    }

    public List<RecursosUI> getRecursosUIList() {
        return recursosUIList;
    }

    public void setRecursosUIList(List<RecursosUI> recursosUIList) {
        this.recursosUIList = recursosUIList;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(long fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getPersonaPuclicacion() {
        return personaPuclicacion;
    }

    public void setPersonaPuclicacion(String personaPuclicacion) {
        this.personaPuclicacion = personaPuclicacion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getKeyTarea() {
        return keyTarea;
    }

    public void setKeyTarea(String keyTarea) {
        this.keyTarea = keyTarea;
    }

    public String getTituloTarea() {
        return tituloTarea;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public long getFechaCreacionTarea() {
        return fechaCreacionTarea;
    }

    public void setFechaCreacionTarea(long fechaCreacionTarea) {
        this.fechaCreacionTarea = fechaCreacionTarea;
    }

    public int getIdUnidaddAprendizaje() {
        return idUnidaddAprendizaje;
    }

    public void setIdUnidaddAprendizaje(int idUnidaddAprendizaje) {
        this.idUnidaddAprendizaje = idUnidaddAprendizaje;
    }

    public RubroEvalProcesoUi getRubroEvalProcesoUi() {
        return rubroEvalProcesoUi;
    }

    public void setRubroEvalProcesoUi(RubroEvalProcesoUi rubroEvalProcesoUi) {
        this.rubroEvalProcesoUi = rubroEvalProcesoUi;
    }

    public ParametroDisenioUi getParametroDisenioUi() {
        return parametroDisenioUi;
    }

    public void setParametroDisenioUi(ParametroDisenioUi parametroDisenioUi) {
        this.parametroDisenioUi = parametroDisenioUi;
    }

    @Override
    public String toString() {
        return "TareasUI{" +
                "keyTarea=" + keyTarea +
                ", tituloTarea='" + tituloTarea + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nombreCurso='" + nombreCurso + '\'' +
                ", fechaCreacionTarea='" + fechaCreacionTarea + '\'' +
                ", fechaLimite='" + fechaLimite + '\'' +
                ", horaEntrega='" + horaEntrega + '\'' +
                ", personaPuclicacion='" + personaPuclicacion + '\'' +
                ", estado='" + estado + '\'' +
                ", isDocente=" + isDocente +
                ", idUnidaddAprendizaje=" + idUnidaddAprendizaje +
                ", idSilaboEvento=" + idSilaboEvento +
                ", btnCreateVisible=" + btnCreateVisible +
                ", recursosUIList=" + recursosUIList +
                '}';
    }
}
