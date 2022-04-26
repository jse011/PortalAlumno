package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;


import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetDatosInicioSesion;
import com.consultoraestrategia.ss_portalalumno.login2.entities.DatosProgressUi;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class GetCursos {
    private MainRepositorio mainRepositorio;
    private LoginDataRepository loginDataRepository;

    public GetCursos(MainRepositorio mainRepositorio) {
        this.mainRepositorio = mainRepositorio;
        loginDataRepository = new LoginDataRepositoryImpl();
    }

    public RetrofitCancel execute(Callback callback){
        AlumnoUi alumnoUi = mainRepositorio.getAlumno();
        AnioAcademicoUi anioAcademicoUi = mainRepositorio.getAnioAcademico(alumnoUi.getPersonaId());
        callback.getAlumno(alumnoUi);
        callback.getAnioAcademico(anioAcademicoUi);
        List<RetrofitCancel> retrofitCancelList = new ArrayList<>();
        return  loginDataRepository.getDatosInicioSesion(alumnoUi.getUsuarioId(), true, new LoginDataRepository.CallBackSucces<DatosProgressUi>() {
            @Override
            public void onLoad(boolean success, DatosProgressUi item) {

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
                callback.onResponse(true, response);
            }

            @Override
            public void onRequestProgress(int progress) {

            }

            @Override
            public void onResponseProgress(int progress) {

            }
        });
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

    public interface Callback{
        void getAlumno(AlumnoUi alumnoUi);
        void getAnioAcademico(AnioAcademicoUi alumnoUi);
        void onResponse(boolean success, Response response);
    }
}
