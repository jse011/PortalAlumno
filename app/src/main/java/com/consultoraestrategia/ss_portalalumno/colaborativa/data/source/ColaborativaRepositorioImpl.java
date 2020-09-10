package com.consultoraestrategia.ss_portalalumno.colaborativa.data.source;

import android.text.TextUtils;

import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA_Table;
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
}
