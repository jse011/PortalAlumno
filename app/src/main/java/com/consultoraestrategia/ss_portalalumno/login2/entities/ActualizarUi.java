package com.consultoraestrategia.ss_portalalumno.login2.entities;

public class ActualizarUi {

    private String id;
    private String nombre;
    private ActualizarTipoUi tipo = ActualizarTipoUi.Rubros;
    private int calendarioPeriodoId;
    private int cargacursoId;
    private int anioAcademicoId;
    private int programaEducativoId;
    private int docenteId;
    private int usuarioId;
    private long fecha;
    private boolean activo;
    private boolean encoloa;
    private int success;
    private int uploadProgress;
    private int dowloadProgress;
    private int georeferenciaId;
    private int entidadId;
    private int silaboEventoId;
    private int cursoId;
    private int cargaAcademicaId;

    public void setCargaAcademicaId(int cargaAcademicaId) {
        this.cargaAcademicaId = cargaAcademicaId;
    }

    public int getCargaAcademicaId() {
        return cargaAcademicaId;
    }
    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setEntidadId(int entidadId) {
        this.entidadId = entidadId;
    }

    public int getEntidadId() {
        return entidadId;
    }

    public void setGeoreferenciaId(int georeferenciaId) {
        this.georeferenciaId = georeferenciaId;
    }

    public int getGeoreferenciaId() {
        return georeferenciaId;
    }

    public void setUploadProgress(int uploadProgress) {
        this.uploadProgress = uploadProgress;
    }

    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setDowloadProgress(int dowloadProgress) {
        this.dowloadProgress = dowloadProgress;
    }

    public int getDowloadProgress() {
        return dowloadProgress;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public ActualizarTipoUi getTipo() {
        return tipo;
    }

    public void setTipo(ActualizarTipoUi tipo) {
        this.tipo = tipo;
    }

    public int getCalendarioPeriodoId() {
        return calendarioPeriodoId;
    }

    public void setCalendarioPeriodoId(int calendarioPeriodoId) {
        this.calendarioPeriodoId = calendarioPeriodoId;
    }

    public int getCargacursoId() {
        return cargacursoId;
    }

    public void setCargacursoId(int cargacursoId) {
        this.cargacursoId = cargacursoId;
    }

    public int getAnioAcademicoId() {
        return anioAcademicoId;
    }

    public void setAnioAcademicoId(int anioAcademicoId) {
        this.anioAcademicoId = anioAcademicoId;
    }

    public int getProgramaEducativoId() {
        return programaEducativoId;
    }

    public void setProgramaEducativoId(int programaEducativoId) {
        this.programaEducativoId = programaEducativoId;
    }

    public int getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(int docenteId) {
        this.docenteId = docenteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void generarId() {
        switch (tipo){
            case Unidades:
                setId("Unidades_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case TipoNota:
                setId("TipoNota_" + programaEducativoId);
                break;
            case Estudiantes:
                setId("Estudiantes_" + cargacursoId);
                break;
            case Rubros:
                setId("Rubro_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case Resultado:
                setId("Resultado_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case Grupos:
                setId("Grupos_" + cargacursoId);
                break;
            case Tareas:
                setId("Tareas_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case Casos:
                setId("Casos_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case Asistencias:
                setId("Asistencia_" + cargacursoId + "_" + calendarioPeriodoId);
                break;
            case Docente:
                setId("Docente_" + georeferenciaId);
                break;
            case Dimencion_Desarrollo:
                setId("DimensionDes_" + cargacursoId);
                break;
        }
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isEncoloa() {
        return encoloa;
    }

    public void setEncoloa(boolean encoloa) {
        this.encoloa = encoloa;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActualizarUi)) return false;

        ActualizarUi that = (ActualizarUi) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}
