package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;


import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GetCursos {
    private MainRepositorio mainRepositorio;

    public GetCursos(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
    }

    public Response execute(){
        AlumnoUi alumnoUi = mainRepositorio.getAlumno();
        AnioAcademicoUi anioAcademicoUi = mainRepositorio.getAnioAcademico(alumnoUi.getPersonaId());
        List<CursosUi> cursosUiList = mainRepositorio.getCursos(alumnoUi.getPersonaId(),anioAcademicoUi.getAnioAcademicoId());
        Set<ProgramaEduactivoUI> programaEduactivoUISet = new LinkedHashSet<>();
        for (CursosUi cursosUi : cursosUiList){
            programaEduactivoUISet.add(cursosUi.getProgramaEduactivoUI());
        }

        Response response = new Response();
        response.setAlumnoUi(alumnoUi);
        response.setAnioAcademicoUi(anioAcademicoUi);
        response.setProgramaEduactivoUIList(new ArrayList<>(programaEduactivoUISet));
        response.setCursosUiList(cursosUiList);
        return response;
    }

    public static class Response {
        private AlumnoUi alumnoUi;
        private AnioAcademicoUi anioAcademicoUi;
        private List<ProgramaEduactivoUI> programaEduactivoUIList;
        private List<CursosUi> cursosUiList;

        public List<ProgramaEduactivoUI> getProgramaEduactivoUIList() {
            return programaEduactivoUIList;
        }

        public void setProgramaEduactivoUIList(List<ProgramaEduactivoUI> programaEduactivoUIList) {
            this.programaEduactivoUIList = programaEduactivoUIList;
        }

        public List<CursosUi> getCursosUiList() {
            return cursosUiList;
        }

        public void setCursosUiList(List<CursosUi> cursosUiList) {
            this.cursosUiList = cursosUiList;
        }

        public AlumnoUi getAlumnoUi() {
            return alumnoUi;
        }

        public void setAlumnoUi(AlumnoUi alumnoUi) {
            this.alumnoUi = alumnoUi;
        }

        public AnioAcademicoUi getAnioAcademicoUi() {
            return anioAcademicoUi;
        }

        public void setAnioAcademicoUi(AnioAcademicoUi anioAcademicoUi) {
            this.anioAcademicoUi = anioAcademicoUi;
        }
    }
}
