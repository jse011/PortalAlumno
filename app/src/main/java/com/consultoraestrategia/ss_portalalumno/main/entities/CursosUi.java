package com.consultoraestrategia.ss_portalalumno.main.entities;

public class CursosUi {
    private int cursoId;
    private String nombre;
    private String seccionyperiodo;
    private String salon;
    private ProgramaEduactivoUI programaEduactivoUI;
    private int cargaCursoId;
    private String backgroundSolidColor;
    private String urlBackgroundItem;
    private String backgroundSolidColor2;
    private String backgroundSolidColor3;
    private int planCursoId;
    private String profesor;
    private String fotoProfesor;
    private int silaboEventoId;

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccionyperiodo() {
        return seccionyperiodo;
    }

    public void setSeccionyperiodo(String seccionyperiodo) {
        this.seccionyperiodo = seccionyperiodo;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public ProgramaEduactivoUI getProgramaEduactivoUI() {
        return programaEduactivoUI;
    }

    public void setProgramaEduactivoUI(ProgramaEduactivoUI programaEduactivoUI) {
        this.programaEduactivoUI = programaEduactivoUI;
    }

    public void setCargaCursoId(int cargaCursoId) {
        this.cargaCursoId = cargaCursoId;
    }

    public int getCargaCursoId() {
        return cargaCursoId;
    }

    public String getBackgroundSolidColor() {
        return backgroundSolidColor;
    }

    public void setBackgroundSolidColor(String backgroundSolidColor) {
        this.backgroundSolidColor = backgroundSolidColor;
    }

    public String getUrlBackgroundItem() {
        return urlBackgroundItem;
    }

    public void setUrlBackgroundItem(String urlBackgroundItem) {
        this.urlBackgroundItem = urlBackgroundItem;
    }

    public void setBackgroundSolidColor2(String backgroundSolidColor2) {
        this.backgroundSolidColor2 = backgroundSolidColor2;
    }

    public String getBackgroundSolidColor2() {
        return backgroundSolidColor2;
    }

    public void setBackgroundSolidColor3(String backgroundSolidColor3) {
        this.backgroundSolidColor3 = backgroundSolidColor3;
    }

    public String getBackgroundSolidColor3() {
        return backgroundSolidColor3;
    }

    public void setPlanCursoId(int planCursoId) {
        this.planCursoId = planCursoId;
    }

    public int getPlanCursoId() {
        return planCursoId;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setFotoProfesor(String fotoProfesor) {
        this.fotoProfesor = fotoProfesor;
    }

    public String getFotoProfesor() {
        return fotoProfesor;
    }

    public int getSilaboEventoId() {
        return silaboEventoId;
    }

    public void setSilaboEventoId(int silaboEventoId) {
        this.silaboEventoId = silaboEventoId;
    }
}
