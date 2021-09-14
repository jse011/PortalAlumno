package com.consultoraestrategia.ss_portalalumno.colaborativa.data.source;

import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GrabacionSalaVirtual;
import com.consultoraestrategia.ss_portalalumno.entities.GrabacionSalaVirtual_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualServidor;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualServidor_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

public class ColaborativaRepositorioImpl implements ColaborativaRepositorio{

    @Override
    public List<ColaborativaUi> geListaColobaorativa(int sesionAprendizajeId) {
        List<ColaborativaUi> colaborativaUiList = new ArrayList<>();
        for (ReunionVirtualPA reunionVirtualPA : SQLite.select()
                .from(ReunionVirtualPA.class)
                .where(ReunionVirtualPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .queryList()){
            ColaborativaUi colaborativaUi = new ColaborativaUi();
            colaborativaUi.setId(reunionVirtualPA.getKey());
            colaborativaUi.setNombre(reunionVirtualPA.getNombreReunion());
            colaborativaUi.setDescripcion(!TextUtils.isEmpty(reunionVirtualPA.getUrl()) ?reunionVirtualPA.getUrl().trim():"");
            switch (reunionVirtualPA.getTipoCanalId()){
                case 613:
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.ZOOM);
                    break;
                case 612:
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.MEET);
                    break;

            }
            colaborativaUiList.add(colaborativaUi);
        }


        for (ColborativaPA colborativaPA : SQLite.select()
                .from(ColborativaPA.class)
                .where(ColborativaPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .queryList()){
            ColaborativaUi colaborativaUi = new ColaborativaUi();
            colaborativaUi.setId(colborativaPA.getKey());
            colaborativaUi.setNombre(colborativaPA.getNombre());
            colaborativaUi.setDescripcion(!TextUtils.isEmpty(colborativaPA.getDescripcion()) ?colborativaPA.getDescripcion().trim():"");
            switch (colborativaPA.getTipo()){
                case "1":
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.GOOGLEDRIVE);
                    break;
                case "2":
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.GOOGLEDOCS);
                    break;
                case "3":
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.JAMBOARD);
                    break;
                case "4":
                    colaborativaUi.setTipo(ColaborativaUi.Tipo.KAHOOT);
                    break;

            }
            colaborativaUiList.add(colaborativaUi);
        }
        return colaborativaUiList;
    }

    @Override
    public List<ColaborativaUi> geListaColobaorativaBaseDatos(int sesionAprendizajeId, int entidadId, int georeferenciaId) {
        List<ColaborativaUi> colaborativaUiList = new ArrayList<>();
        for (ReunionVirtualServidor reunionVirtualServidor : SQLite.select()
                .from(ReunionVirtualServidor.class)
                .where(ReunionVirtualServidor_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .and(ReunionVirtualServidor_Table.entidadId.eq(entidadId))
                .and(ReunionVirtualServidor_Table.georeferenciaId.eq(georeferenciaId))
                .queryList()){

            ColaborativaUi colaborativaUi = new ColaborativaUi();
            colaborativaUi.setId("rev_"+reunionVirtualServidor.getReunionVirtualId());
            colaborativaUi.setNombre(reunionVirtualServidor.getNombreReunion());
            colaborativaUi.setDescripcion(reunionVirtualServidor.getUrl());
            if(reunionVirtualServidor.getTipoCanalId() == 613){
                colaborativaUi.setTipo(ColaborativaUi.Tipo.ZOOM);
            }else {
                colaborativaUi.setTipo(ColaborativaUi.Tipo.MEET);
            }
            colaborativaUiList.add(colaborativaUi);
        }
        return colaborativaUiList;
    }

    @Override
    public List<ColaborativaUi> getListGrabaciones(int sesionAprendizajeId) {
        List<ColaborativaUi> colaborativaUiList = new ArrayList<>();
        for (GrabacionSalaVirtual grabacionSalaVirtual : SQLite.select()
                .from(GrabacionSalaVirtual.class)
                .where(GrabacionSalaVirtual_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .queryList()){
            ColaborativaUi colaborativaUi = new ColaborativaUi();
            colaborativaUi.setId("Grba_"+grabacionSalaVirtual.getGrabacionId());
            colaborativaUi.setNombre(grabacionSalaVirtual.getNombreGrabacion());
            colaborativaUi.setDescripcion(grabacionSalaVirtual.getUrlGrabacion());
            colaborativaUi.setTipo(ColaborativaUi.Tipo.GRABACION);
            colaborativaUiList.add(colaborativaUi);
        }
        return colaborativaUiList;
    }
}
