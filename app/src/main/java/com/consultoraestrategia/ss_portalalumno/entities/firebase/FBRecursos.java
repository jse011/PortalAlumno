package com.consultoraestrategia.ss_portalalumno.entities.firebase;

public class FBRecursos {
    private String Descripcion;
    private String RecursoDidacticoId;
    private int TipoId;
    private String Titulo;

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getRecursoDidacticoId() {
        return RecursoDidacticoId;
    }

    public void setRecursoDidacticoId(String recursoDidacticoId) {
        RecursoDidacticoId = recursoDidacticoId;
    }

    public int getTipoId() {
        return TipoId;
    }

    public void setTipoId(int tipoId) {
        TipoId = tipoId;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
}
