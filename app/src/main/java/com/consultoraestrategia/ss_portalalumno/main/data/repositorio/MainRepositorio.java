package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;


import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;

import java.util.List;

public interface MainRepositorio {
    AlumnoUi getAlumno();
    AnioAcademicoUi getAnioAcademico(int personaId);
    List<CursosUi> getCursos(int personaId, int anioAcademicoId);
}
