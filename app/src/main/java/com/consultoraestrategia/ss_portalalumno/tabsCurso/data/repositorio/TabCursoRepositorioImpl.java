package com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioAcademico_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodoDetalle_Table;
import com.consultoraestrategia.ss_portalalumno.entities.CalendarioPeriodo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos;
import com.consultoraestrategia.ss_portalalumno.entities.Tipos_Table;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TabCursoRepositorioImpl implements TabCursoRepositorio {

    public static final String TAG =  TabCursoRepositorioImpl.class.getSimpleName();
    @Override
    public List<PeriodoUi> getPerioList(int anioAcademicoId, int cargaCursoId, int programaEduId) {
        List<PeriodoUi> periodoUiList = new ArrayList<>();
        List<CalendarioPeriodo> calendarioPeriodoList = SQLite.select()
                .from(CalendarioPeriodo.class)
                .innerJoin(CalendarioAcademico.class)
                .on(CalendarioPeriodo_Table.calendarioAcademicoId.withTable()
                        .eq(CalendarioAcademico_Table.calendarioAcademicoId.withTable()))
                .where(CalendarioAcademico_Table.idAnioAcademico.withTable().eq(anioAcademicoId))
                .and(CalendarioAcademico_Table.programaEduId.withTable().eq(programaEduId))
                .queryList();

        Log.d(TAG, "SIZEPERIODO1 : " + calendarioPeriodoList.size());

        boolean seleccionado = false;
        for (CalendarioPeriodo periodo :
                calendarioPeriodoList) {
            Log.d(TAG, "COUNT : " + calendarioPeriodoList.size() + " /  periodoid : " + periodo.getCalendarioPeriodoId() + " / " + periodo.getCalendarioPeriodoId()+ " / " +periodo.getEstadoId());

            Tipos tipo = SQLite.select()
                    .from(Tipos.class)
                    .where(Tipos_Table.tipoId.eq(periodo.getTipoId()))
                    .querySingle();
            if(tipo==null)continue;
            //Log.d(TAG, "PeriodoUi : " + tipo.getTipoId() + "")

            PeriodoUi periodoUi = new PeriodoUi(periodo.getCalendarioPeriodoId(), tipo.getNombre(), "", false);

            boolean isvigente = isVigenteCalendarioCursoPeriodo(periodo.getCalendarioPeriodoId(), cargaCursoId, false);
            periodoUi.setVigente(isvigente);
            periodoUi.setFechaFin(periodo.getFechaFin());

            switch (periodo.getEstadoId()){
                case CalendarioPeriodo.CALENDARIO_PERIODO_CREADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Creado);
                    periodoUi.setEditar(true);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_VIGENTE:
                    periodoUi.setEstado(PeriodoUi.Estado.Vigente);
                    periodoUi.setEditar(isvigente);
                    break;
                case CalendarioPeriodo.CALENDARIO_PERIODO_CERRADO:
                    periodoUi.setEstado(PeriodoUi.Estado.Cerrado);
                    periodoUi.setEditar(isvigente);
                    break;
            }
            periodoUiList.add(periodoUi);
        }
        Log.d(TAG, "SIZEPERIODO1 : " + periodoUiList.size());
        return periodoUiList;
    }

    public boolean isVigenteCalendarioCursoPeriodo(int calendarioPeridoId, int cargaCursoId, boolean isRubroResultado) {
        boolean status = false;
        boolean statusCargaCuros = false;
        try {

            Log.d(TAG, "calendarioPeridoId :"+ calendarioPeridoId);
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.set(Calendar.MILLISECOND, 0);
            fechaActual.set(Calendar.SECOND, 0);
            fechaActual.set(Calendar.MINUTE, 0);
            fechaActual.set(Calendar.HOUR_OF_DAY, 0);

            Where<CalendarioPeriodo> calendarioPeriodoWhere = SQLite.select()
                    .from(CalendarioPeriodo.class)
                    .where(CalendarioPeriodo_Table.calendarioPeriodoId.eq(calendarioPeridoId));

            CalendarioPeriodo calendarioPeriodo = calendarioPeriodoWhere.querySingle();

            Where<CalendarioPeriodoDetalle> where = SQLite.select()
                    .from(CalendarioPeriodoDetalle.class)
                    .where(CalendarioPeriodoDetalle_Table.calendarioPeriodoId.eq(calendarioPeridoId));

            if(isRubroResultado){
                where.and(CalendarioPeriodoDetalle_Table.tipoId.eq(493));
            }else {
                where.and(CalendarioPeriodoDetalle_Table.tipoId.eq(494));
            }

            CalendarioPeriodoDetalle calendarioPeriodoDetalle =  where.querySingle();
            if (calendarioPeriodo == null||calendarioPeriodoDetalle==null){
                Log.d(TAG, "No tiene calendario detalle");
                return false;
            }
            Log.d(TAG, "calendarioPeriodo.getEstadoId() "+ calendarioPeriodo.getEstadoId());
            if(calendarioPeriodo.getEstadoId()== CalendarioPeriodo.CALENDARIO_PERIODO_VIGENTE ||
                    calendarioPeriodo.getEstadoId()== CalendarioPeriodo.CALENDARIO_PERIODO_CERRADO ) {

                Calendar fechaDetalleInicio = Calendar.getInstance();
                fechaDetalleInicio.setTimeInMillis(calendarioPeriodoDetalle.getFechaInicio());
                fechaDetalleInicio.set(Calendar.MILLISECOND, 0);
                fechaDetalleInicio.set(Calendar.SECOND, 0);
                fechaDetalleInicio.set(Calendar.MINUTE, 0);
                fechaDetalleInicio.set(Calendar.HOUR_OF_DAY, 0);

                Calendar fechaDetalleFin = Calendar.getInstance();
                fechaDetalleFin.setTimeInMillis(calendarioPeriodoDetalle.getFechaFin());
                fechaDetalleFin.set(Calendar.MILLISECOND, 0);
                fechaDetalleFin.set(Calendar.SECOND, 0);
                fechaDetalleFin.set(Calendar.MINUTE, 0);
                fechaDetalleFin.set(Calendar.HOUR_OF_DAY, 0);

                int resutDetalleInicio = fechaActual.compareTo(fechaDetalleInicio);
                int resutDetalleFin = fechaActual.compareTo(fechaDetalleFin);

                Log.d(TAG, "cargaCursoId" + cargaCursoId + " " + " calendarioPeriodoId: " + calendarioPeriodoDetalle.getCalendarioPeriodoDetId());

                if((resutDetalleInicio == 0 || resutDetalleInicio > 0) &&
                        (resutDetalleFin == 0 || resutDetalleFin < 0)
                ){
                    status = true;
                }



            }
        } catch (Exception e){
            e.printStackTrace();
            status = false;
        }finally {
        }
        return status;
    }
}
