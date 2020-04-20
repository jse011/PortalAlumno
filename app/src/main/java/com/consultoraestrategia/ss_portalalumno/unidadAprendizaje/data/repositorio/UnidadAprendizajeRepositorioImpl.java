package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PlanCursos;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO;
import com.consultoraestrategia.ss_portalalumno.entities.T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos_Table;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsDBFlow;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.From;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.ArrayList;
import java.util.List;

public class UnidadAprendizajeRepositorioImpl implements UnidadAprendizajeRepositorio {


    private static final String TAG = UnidadAprendizajeRepositorioImpl.class.getSimpleName();

    @Override
    public List<UnidadAprendizajeUi> getUnidadesList(int idCargaCurso, int idCalendarioPeriodo, int idAnioAcademico, int planCursoId) {
        List<UnidadAprendizajeUi> listObject= new ArrayList<>();

        List<UnidadAprendizaje> vlst_unidadAprendizaje = SQLite
                .select(UtilsDBFlow.f_allcolumnTable(UnidadAprendizaje_Table.ALL_COLUMN_PROPERTIES))
                .from(UnidadAprendizaje.class)
                .innerJoin(SilaboEvento.class)
                .on(UnidadAprendizaje_Table.silaboEventoId.withTable().eq(SilaboEvento_Table.silaboEventoId.withTable()))
                .innerJoin(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO.class)
                .on(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.unidadaprendizajeId.withTable()
                        .eq(UnidadAprendizaje_Table.unidadAprendizajeId.withTable()))
                .innerJoin(Tipos.class)
                .on(Tipos_Table.tipoId.withTable()
                        .eq(T_GC_REL_UNIDAD_APREN_EVENTO_TIPO_Table.tipoid.withTable()))
                .innerJoin(CalendarioPeriodo.class)
                .on(CalendarioPeriodo_Table.tipoId.withTable()
                        .eq(Tipos_Table.tipoId.withTable()))
                .where(SilaboEvento_Table.cargaCursoId.withTable().eq(idCargaCurso))
                .and(CalendarioPeriodo_Table.calendarioPeriodoId.withTable().eq(idCalendarioPeriodo))
                .and(SilaboEvento_Table.anioAcademicoId.withTable().eq(idAnioAcademico))
                .and(SilaboEvento_Table.planCursoId.withTable().eq(planCursoId))
                .queryList();

        for(UnidadAprendizaje unidadAprendizaje:vlst_unidadAprendizaje ){
            //sesionAprendizajeUiList.clear();

            UnidadAprendizajeUi unidad= new UnidadAprendizajeUi();
            unidad.setUnidadAprendizajeId(unidadAprendizaje.getUnidadAprendizajeId());
            unidad.setNroUnidad(unidadAprendizaje.getNroUnidad());
            unidad.setTitulo(unidadAprendizaje.getTitulo());
            unidad.setSituacionSignificativa(unidadAprendizaje.getSituacionSignificativa());
            unidad.setNroHoras(unidadAprendizaje.getNroHoras());
            unidad.setNroSesiones(unidadAprendizaje.getNroSesiones());
            unidad.setNroSemanas(unidadAprendizaje.getNroSemanas());
            unidad.setEstadoId(unidadAprendizaje.getEstadoId());
            unidad.setSilaboEventoId(unidadAprendizaje.getSilaboEventoId());
            unidad.setSituacionSignificativaComplementaria(unidadAprendizaje.getSituacionSignificativaComplementaria());
            unidad.setDesafio(unidadAprendizaje.getDesafio());
            unidad.setReto(unidadAprendizaje.getReto());
            unidad.setToogle(unidadAprendizaje.isToogle());

            List<SesionAprendizaje> sesionAprendizajes = SQLite.select()
                    .from(SesionAprendizaje.class)
                    .where(SesionAprendizaje_Table.unidadAprendizajeId.eq(unidadAprendizaje.getUnidadAprendizajeId()))
                    .and(SesionAprendizaje_Table.rolId.eq(6))
                    .and(SesionAprendizaje_Table.estadoId.in(SesionAprendizaje.CREADO_ESTADO,SesionAprendizaje.AUTORIZADO_ESTADO))//297
                    .orderBy(SesionAprendizaje_Table.fechaEjecucion.asc())
                    .queryList();

            List<SesionAprendizajeUi>sesionAprendizajeUiList= new ArrayList<>();

            for(SesionAprendizaje sesion:sesionAprendizajes ){

                SesionAprendizajeUi sesionAprendizajeUi = new SesionAprendizajeUi();
                sesionAprendizajeUi.setSesionAprendizajeId(sesion.getSesionAprendizajeId());
                sesionAprendizajeUi.setUnidadAprendizajeId(sesion.getUnidadAprendizajeId());
                sesionAprendizajeUi.setTitulo(sesion.getTitulo());
                sesionAprendizajeUi.setHoras(sesion.getHoras());
                sesionAprendizajeUi.setContenido(sesion.getContenido());
                sesionAprendizajeUi.setUsuarioCreador(sesion.getUsuarioCreador());
                sesionAprendizajeUi.setUsuarioAccion(sesion.getUsuarioAccion());
                sesionAprendizajeUi.setEstadoId(sesion.getEstadoId());
                sesionAprendizajeUi.setFechaEjecucion(sesion.getFechaEjecucion());
                sesionAprendizajeUi.setFechaReprogramacion(sesion.getFechaReprogramacion());
                sesionAprendizajeUi.setFechaPublicacion(sesion.getFechaPublicacion());
                sesionAprendizajeUi.setNroSesion(sesion.getNroSesion());
                sesionAprendizajeUi.setRolId(sesion.getRolId());
                sesionAprendizajeUi.setFechaRealizada(sesion.getFechaRealizada());
                sesionAprendizajeUi.setEstadoEjecucionId(sesion.getEstadoEjecucionId());
                sesionAprendizajeUi.setProposito(sesion.getProposito());
                sesionAprendizajeUi.setCantidad_recursos(0);
                sesionAprendizajeUiList.add(sesionAprendizajeUi);
            }

            unidad.setObjectListSesiones(sesionAprendizajeUiList);
            listObject.add(unidad);
        }
        return listObject;
    }

}
