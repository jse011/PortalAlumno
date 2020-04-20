package com.consultoraestrategia.ss_portalalumno.entities;

import java.util.List;

public class BEListaPadre {


    //#region BEDatosCargaAcademica
    public List<CargaCursos> cargaCursos;
    //public List<Archivo> archivo;//Sacar Login // Semovio a Tareas
    public List<Aula> aulas;
    public List<CalendarioAcademico> calendarioAcademicos;
    public List<CalendarioPeriodo> calendarioPeriodos;
    public List<Contrato> contratos;
    public List<DetalleContratoAcad> detalleContratoAcad;
    public List<Empleado> empleados;
    //public List<Caso> caso;//Sacar Login
    //public List<CasoArchivo> casoArchivo;//Sacar Login
    //public List<CasoReporte> casoReporte;//Sacar LoginTipos
   // public List<Comportamiento> comportamiento;//Sacar Login
   // public List<TipoEntidadGeo> tipoEntidadGeo;//Sacar Login
    public List<Cursos> cursos;
    public List<PlanCursos> planCursos;
    public List<PlanEstudios> planEstudios;
    public List<ProgramasEducativo> programasEducativos;
    public List<CargaAcademica> cargasAcademicas;
    public List<Seccion> secciones;
    public List<Periodo> periodos;
    public List<NivelAcademico> nivelesAcademicos;
    public List<CargaCursoDocente> cargaCursoDocente;
    public List<Georeferencia> georeferencias;
    private List<CargaCursoDocenteDet> cargaCursoDocenteDet;
    //#endregion BEDatosCargaAcademica


    //#region BEDatosEnvioHorario
    public List<CursosDetHorario> cursosDetHorario;//Sacar Login
    public List<DetalleHorario> detalleHorario;//Sacar Login
    public List<Dia> dia;//Sacar Login
    public List<Horario> horario;//Sacar Login
    public List<HorarioPrograma> horarioPrograma;//Sacar Login
    public List<HorarioDia> horarioDia;//Sacar Login
    //#endregion  BEDatosEnvioHorario

    //#region BEDatosEnvioTipoNota
    /*public List<TipoNotaC> tipoNota;//Sacar Login
    public List<ValorTipoNotaC> valorTipoNota;//Sacar Login
    public List<EscalaEvaluacion> escalaEvaluacion;//Sacar Login
    public List<T_RN_MAE_TIPO_EVALUACION> rn_mae_tipo_evaluacion;//Sacar Login*/
    //#endregion BEDatosEnvioTipoNota

    //#region BEDatosRubroEvaluacionProceso
   /* public List<EvaluacionProcesoC> evaluacionProceso;//Sacar Login
    public List<RubroEvalRNPFormulaC> rubroEvalProcesoFormula;//Sacar Login
    public List<RubroEvaluacionProcesoC> rubroEvaluacionProceso;//Sacar Login
    private List<RubroEvaluacionProcesoComentario> rubroEvaluacionProcesoComentario;//Sacar Login
    private List<ArchivosRubroProceso> archivoRubroProceso;//Sacar Login
    private List<RubroEvaluacionResultado> rubroEvaluacionResultado;//Sacar Login*/
    //#endregion BEDatosRubroEvaluacionProceso

    //#region GEDatosSilaboEventoEnvio

    //#region tarea
    public List<TareasC> tareas;//Sacar Login
    //public List<TareaRubroEvaluacionProceso> tareaRubroEvaluacionProceso;//Sacar Login
    public List<TareasRecursosC> tareasRecursos;//Sacar Login
    public List<RecursoDidacticoEventoC> recursoDidactico;//Sacar Login
    public List<Archivo> archivo;//Sacar Login
    public List<RecursoArchivo> recursoArchivo;//Sacar Login
    //#endregion

    //#region Silavo
    public List<Competencia> competencias;//Sacar Login
    public List<DesempenioIcd> rel_desempenio_icd;//Sacar Login
    public List<Icds> icds;//Sacar Login
    public List<CampoTematico> campoTematico;//Sacar Login
    public List<SesionAprendizaje> sesiones ;//Sacar Login
    public List<UnidadAprendizaje> unidadAprendizaje;//Sacar Login
    public List<T_GC_REL_UNIDAD_APREN_EVENTO_TIPO> rel_unidad_apren_evento_tipo;//Sacar Login
    //#endregion Silavo


    //#region cursos
    public List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD> rel_unidad_evento_competencia_desempenio_icd ;//Sacar Login
    public List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO> rel_unidad_evento_competencia_desempenio_icd_campo_tematico;//Sacar Login
    public List<SesionEventoDesempenioIcdCampotematico> sesion_desempenio_icd_campotematico ;//Sacar Login
    public List<SesionEventoCompetenciaDesempenioIcd> rel_sesion_evento_competencia_desempenioicd ;//Sacar Login
    public List<T_GC_REL_COMPETENCIA_SESION_EVENTO> competenciaSesion ;//Sacar Login
    public List<ActividadAprendizaje> actividad;//Sacar Login
    public List<RecursoReferenciaC> recursoReferencia ;//Sacar Login
    //#endregion cursos

    public List<CalendarioPeriodoDetalle> obtenerCalendarioPeriodoDetalle;
    public List<SilaboEvento> silaboEvento;

    //#endregion GEDatosSilaboEventoEnvio

    //#region BEObtenerDatosLogin
    public List<Persona> personas;
    public List<Relaciones> relaciones;
    public List<Ubicaciones> ubicaciones;
    public List<Tipos> tipos;
    public List<Evento> evento;
    public List<Calendario> calendario;
    public List<CalendarioListaUsuario> calendarioListaUsuario;
    public List<Usuario> usuariosrelacionados;
    public List<AnioAcademico> anioAcademicos;
    public List<AnioAcademicoAlumno> anioAcademicosAlumno;
    public List<ParametrosDisenio> obtener_parametros_disenio;
    public List<Directivos> directivos;
    //#endregion BEObtenerDatosLogin



    //#region BEDatosEnvioMensajeria
    /*public List<ListaUsuario> listaUsuarios;
    public List<ListaUsuarioDetalle> listUsuarioDetalle;*/
    //#endregion BEDatosEnvioMensajeria


    //#region BEDatosEnvioAsistencia
   /* public List<AsistenciaSesionAlumnoC> asistenciaAlumnos;//Sacar Login
    public List<JustificacionC> justificacion;//Sacar Login
    public List<ArchivoAsistencia> archivoAsistencia;//Sacar Login*/
    //#endregion BEDatosEnvioAsistencia


    //public List<RelProgramaEducativoTipoNota> relProgramaEducativoTipoNota;


    public List<CompetenciaUnidad> competenciaUnidad;//Sacar Login
    public List<RubroEvaluacionProcesoCampotematicoC> rubro_evaluacion_proceso_campotematico;//Sacar Login

    public long fecha_servidor;
    private List<Desempenio> desempenio;
    private List<GeoRefOrganigrama> geoRefOrganigrama;
    private List<TipoEntidadGeo> relTipoEntidadGeo;

    public List<CargaCursos> getCargaCursos() {
        return cargaCursos;
    }

    public void setCargaCursos(List<CargaCursos> cargaCursos) {
        this.cargaCursos = cargaCursos;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public List<CalendarioAcademico> getCalendarioAcademicos() {
        return calendarioAcademicos;
    }

    public void setCalendarioAcademicos(List<CalendarioAcademico> calendarioAcademicos) {
        this.calendarioAcademicos = calendarioAcademicos;
    }

    public List<CalendarioPeriodo> getCalendarioPeriodos() {
        return calendarioPeriodos;
    }

    public void setCalendarioPeriodos(List<CalendarioPeriodo> calendarioPeriodos) {
        this.calendarioPeriodos = calendarioPeriodos;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<DetalleContratoAcad> getDetalleContratoAcad() {
        return detalleContratoAcad;
    }

    public void setDetalleContratoAcad(List<DetalleContratoAcad> detalleContratoAcad) {
        this.detalleContratoAcad = detalleContratoAcad;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Cursos> getCursos() {
        return cursos;
    }

    public void setCursos(List<Cursos> cursos) {
        this.cursos = cursos;
    }

    public List<PlanCursos> getPlanCursos() {
        return planCursos;
    }

    public void setPlanCursos(List<PlanCursos> planCursos) {
        this.planCursos = planCursos;
    }

    public List<PlanEstudios> getPlanEstudios() {
        return planEstudios;
    }

    public void setPlanEstudios(List<PlanEstudios> planEstudios) {
        this.planEstudios = planEstudios;
    }

    public List<ProgramasEducativo> getProgramasEducativos() {
        return programasEducativos;
    }

    public void setProgramasEducativos(List<ProgramasEducativo> programasEducativos) {
        this.programasEducativos = programasEducativos;
    }

    public List<CargaAcademica> getCargasAcademicas() {
        return cargasAcademicas;
    }

    public void setCargasAcademicas(List<CargaAcademica> cargasAcademicas) {
        this.cargasAcademicas = cargasAcademicas;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public List<Periodo> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<Periodo> periodos) {
        this.periodos = periodos;
    }

    public List<NivelAcademico> getNivelesAcademicos() {
        return nivelesAcademicos;
    }

    public void setNivelesAcademicos(List<NivelAcademico> nivelesAcademicos) {
        this.nivelesAcademicos = nivelesAcademicos;
    }

    public List<CargaCursoDocente> getCargaCursoDocente() {
        return cargaCursoDocente;
    }

    public void setCargaCursoDocente(List<CargaCursoDocente> cargaCursoDocente) {
        this.cargaCursoDocente = cargaCursoDocente;
    }

    public List<Georeferencia> getGeoreferencias() {
        return georeferencias;
    }

    public void setGeoreferencias(List<Georeferencia> georeferencias) {
        this.georeferencias = georeferencias;
    }

    public List<CargaCursoDocenteDet> getCargaCursoDocenteDet() {
        return cargaCursoDocenteDet;
    }

    public void setCargaCursoDocenteDet(List<CargaCursoDocenteDet> cargaCursoDocenteDet) {
        this.cargaCursoDocenteDet = cargaCursoDocenteDet;
    }

    public List<CursosDetHorario> getCursosDetHorario() {
        return cursosDetHorario;
    }

    public void setCursosDetHorario(List<CursosDetHorario> cursosDetHorario) {
        this.cursosDetHorario = cursosDetHorario;
    }

    public List<DetalleHorario> getDetalleHorario() {
        return detalleHorario;
    }

    public void setDetalleHorario(List<DetalleHorario> detalleHorario) {
        this.detalleHorario = detalleHorario;
    }

    public List<Dia> getDia() {
        return dia;
    }

    public void setDia(List<Dia> dia) {
        this.dia = dia;
    }

    public List<Horario> getHorario() {
        return horario;
    }

    public void setHorario(List<Horario> horario) {
        this.horario = horario;
    }

    public List<HorarioPrograma> getHorarioPrograma() {
        return horarioPrograma;
    }

    public void setHorarioPrograma(List<HorarioPrograma> horarioPrograma) {
        this.horarioPrograma = horarioPrograma;
    }

    public List<HorarioDia> getHorarioDia() {
        return horarioDia;
    }

    public void setHorarioDia(List<HorarioDia> horarioDia) {
        this.horarioDia = horarioDia;
    }

    public List<TareasC> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareasC> tareas) {
        this.tareas = tareas;
    }

    public List<TareasRecursosC> getTareasRecursos() {
        return tareasRecursos;
    }

    public void setTareasRecursos(List<TareasRecursosC> tareasRecursos) {
        this.tareasRecursos = tareasRecursos;
    }

    public List<RecursoDidacticoEventoC> getRecursoDidactico() {
        return recursoDidactico;
    }

    public void setRecursoDidactico(List<RecursoDidacticoEventoC> recursoDidactico) {
        this.recursoDidactico = recursoDidactico;
    }

    public List<Archivo> getArchivo() {
        return archivo;
    }

    public void setArchivo(List<Archivo> archivo) {
        this.archivo = archivo;
    }

    public List<RecursoArchivo> getRecursoArchivo() {
        return recursoArchivo;
    }

    public void setRecursoArchivo(List<RecursoArchivo> recursoArchivo) {
        this.recursoArchivo = recursoArchivo;
    }

    public List<Competencia> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<Competencia> competencias) {
        this.competencias = competencias;
    }

    public List<DesempenioIcd> getRel_desempenio_icd() {
        return rel_desempenio_icd;
    }

    public void setRel_desempenio_icd(List<DesempenioIcd> rel_desempenio_icd) {
        this.rel_desempenio_icd = rel_desempenio_icd;
    }

    public List<Icds> getIcds() {
        return icds;
    }

    public void setIcds(List<Icds> icds) {
        this.icds = icds;
    }

    public List<CampoTematico> getCampoTematico() {
        return campoTematico;
    }

    public void setCampoTematico(List<CampoTematico> campoTematico) {
        this.campoTematico = campoTematico;
    }

    public List<SesionAprendizaje> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<SesionAprendizaje> sesiones) {
        this.sesiones = sesiones;
    }

    public List<UnidadAprendizaje> getUnidadAprendizaje() {
        return unidadAprendizaje;
    }

    public void setUnidadAprendizaje(List<UnidadAprendizaje> unidadAprendizaje) {
        this.unidadAprendizaje = unidadAprendizaje;
    }

    public List<T_GC_REL_UNIDAD_APREN_EVENTO_TIPO> getRel_unidad_apren_evento_tipo() {
        return rel_unidad_apren_evento_tipo;
    }

    public void setRel_unidad_apren_evento_tipo(List<T_GC_REL_UNIDAD_APREN_EVENTO_TIPO> rel_unidad_apren_evento_tipo) {
        this.rel_unidad_apren_evento_tipo = rel_unidad_apren_evento_tipo;
    }

    public List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD> getRel_unidad_evento_competencia_desempenio_icd() {
        return rel_unidad_evento_competencia_desempenio_icd;
    }

    public void setRel_unidad_evento_competencia_desempenio_icd(List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD> rel_unidad_evento_competencia_desempenio_icd) {
        this.rel_unidad_evento_competencia_desempenio_icd = rel_unidad_evento_competencia_desempenio_icd;
    }

    public List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO> getRel_unidad_evento_competencia_desempenio_icd_campo_tematico() {
        return rel_unidad_evento_competencia_desempenio_icd_campo_tematico;
    }

    public void setRel_unidad_evento_competencia_desempenio_icd_campo_tematico(List<T_GC_REL_UNIDAD_EVENTO_COMPETENCIA_DESEMPENIO_ICD_CAMPO_TEMATICO> rel_unidad_evento_competencia_desempenio_icd_campo_tematico) {
        this.rel_unidad_evento_competencia_desempenio_icd_campo_tematico = rel_unidad_evento_competencia_desempenio_icd_campo_tematico;
    }

    public List<SesionEventoDesempenioIcdCampotematico> getSesion_desempenio_icd_campotematico() {
        return sesion_desempenio_icd_campotematico;
    }

    public void setSesion_desempenio_icd_campotematico(List<SesionEventoDesempenioIcdCampotematico> sesion_desempenio_icd_campotematico) {
        this.sesion_desempenio_icd_campotematico = sesion_desempenio_icd_campotematico;
    }

    public List<SesionEventoCompetenciaDesempenioIcd> getRel_sesion_evento_competencia_desempenioicd() {
        return rel_sesion_evento_competencia_desempenioicd;
    }

    public void setRel_sesion_evento_competencia_desempenioicd(List<SesionEventoCompetenciaDesempenioIcd> rel_sesion_evento_competencia_desempenioicd) {
        this.rel_sesion_evento_competencia_desempenioicd = rel_sesion_evento_competencia_desempenioicd;
    }

    public List<T_GC_REL_COMPETENCIA_SESION_EVENTO> getCompetenciaSesion() {
        return competenciaSesion;
    }

    public void setCompetenciaSesion(List<T_GC_REL_COMPETENCIA_SESION_EVENTO> competenciaSesion) {
        this.competenciaSesion = competenciaSesion;
    }

    public List<ActividadAprendizaje> getActividad() {
        return actividad;
    }

    public void setActividad(List<ActividadAprendizaje> actividad) {
        this.actividad = actividad;
    }

    public List<RecursoReferenciaC> getRecursoReferencia() {
        return recursoReferencia;
    }

    public void setRecursoReferencia(List<RecursoReferenciaC> recursoReferencia) {
        this.recursoReferencia = recursoReferencia;
    }

    public List<CalendarioPeriodoDetalle> getObtenerCalendarioPeriodoDetalle() {
        return obtenerCalendarioPeriodoDetalle;
    }

    public void setObtenerCalendarioPeriodoDetalle(List<CalendarioPeriodoDetalle> obtenerCalendarioPeriodoDetalle) {
        this.obtenerCalendarioPeriodoDetalle = obtenerCalendarioPeriodoDetalle;
    }

    public List<SilaboEvento> getSilaboEvento() {
        return silaboEvento;
    }

    public void setSilaboEvento(List<SilaboEvento> silaboEvento) {
        this.silaboEvento = silaboEvento;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public List<Relaciones> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<Relaciones> relaciones) {
        this.relaciones = relaciones;
    }

    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<Tipos> getTipos() {
        return tipos;
    }

    public void setTipos(List<Tipos> tipos) {
        this.tipos = tipos;
    }

    public List<Evento> getEvento() {
        return evento;
    }

    public void setEvento(List<Evento> evento) {
        this.evento = evento;
    }

    public List<Calendario> getCalendario() {
        return calendario;
    }

    public void setCalendario(List<Calendario> calendario) {
        this.calendario = calendario;
    }

    public List<CalendarioListaUsuario> getCalendarioListaUsuario() {
        return calendarioListaUsuario;
    }

    public void setCalendarioListaUsuario(List<CalendarioListaUsuario> calendarioListaUsuario) {
        this.calendarioListaUsuario = calendarioListaUsuario;
    }

    public List<Usuario> getUsuariosrelacionados() {
        return usuariosrelacionados;
    }

    public void setUsuariosrelacionados(List<Usuario> usuariosrelacionados) {
        this.usuariosrelacionados = usuariosrelacionados;
    }

    public List<AnioAcademico> getAnioAcademicos() {
        return anioAcademicos;
    }

    public void setAnioAcademicos(List<AnioAcademico> anioAcademicos) {
        this.anioAcademicos = anioAcademicos;
    }

    public List<AnioAcademicoAlumno> getAnioAcademicosAlumno() {
        return anioAcademicosAlumno;
    }

    public void setAnioAcademicosAlumno(List<AnioAcademicoAlumno> anioAcademicosAlumno) {
        this.anioAcademicosAlumno = anioAcademicosAlumno;
    }

    public List<ParametrosDisenio> getObtener_parametros_disenio() {
        return obtener_parametros_disenio;
    }

    public void setObtener_parametros_disenio(List<ParametrosDisenio> obtener_parametros_disenio) {
        this.obtener_parametros_disenio = obtener_parametros_disenio;
    }

    public List<Directivos> getDirectivos() {
        return directivos;
    }

    public void setDirectivos(List<Directivos> directivos) {
        this.directivos = directivos;
    }

    public List<CompetenciaUnidad> getCompetenciaUnidad() {
        return competenciaUnidad;
    }

    public void setCompetenciaUnidad(List<CompetenciaUnidad> competenciaUnidad) {
        this.competenciaUnidad = competenciaUnidad;
    }

    public List<RubroEvaluacionProcesoCampotematicoC> getRubro_evaluacion_proceso_campotematico() {
        return rubro_evaluacion_proceso_campotematico;
    }

    public void setRubro_evaluacion_proceso_campotematico(List<RubroEvaluacionProcesoCampotematicoC> rubro_evaluacion_proceso_campotematico) {
        this.rubro_evaluacion_proceso_campotematico = rubro_evaluacion_proceso_campotematico;
    }

    public long getFecha_servidor() {
        return fecha_servidor;
    }

    public void setFecha_servidor(long fecha_servidor) {
        this.fecha_servidor = fecha_servidor;
    }

    public List<Desempenio> getDesempenio() {
        return desempenio;
    }

    public void setDesempenio(List<Desempenio> desempenio) {
        this.desempenio = desempenio;
    }

    public List<GeoRefOrganigrama> getGeoRefOrganigrama() {
        return geoRefOrganigrama;
    }

    public void setGeoRefOrganigrama(List<GeoRefOrganigrama> geoRefOrganigrama) {
        this.geoRefOrganigrama = geoRefOrganigrama;
    }

    public List<TipoEntidadGeo> getRelTipoEntidadGeo() {
        return relTipoEntidadGeo;
    }

    public void setRelTipoEntidadGeo(List<TipoEntidadGeo> relTipoEntidadGeo) {
        this.relTipoEntidadGeo = relTipoEntidadGeo;
    }
}
