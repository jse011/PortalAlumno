package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;


import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

import java.util.List;

public interface MainRepositorio {
    AlumnoUi getAlumno();
    AnioAcademicoUi getAnioAcademico(int personaId);
    List<CursosUi> getCursos(int personaId, int anioAcademicoId);

    RetrofitCancel updateCalendarioPeriodo(int anioAcademico, SuccessCallback callback);

    interface SuccessCallback{
        void onLoad(boolean success);
    }

    void pdateFirebaseTipoNota(int programaEducativoId, SuccessCallback callback);


}
