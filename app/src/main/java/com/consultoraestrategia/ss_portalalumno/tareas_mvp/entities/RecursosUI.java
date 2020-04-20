package com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities;


/**
 * Created by irvinmarin on 21/11/2017.
 */
public class RecursosUI extends RepositorioFileUi {

    public TareasUI tarea;

    public void setTarea(TareasUI tarea) {
        this.tarea = tarea;
    }

    public TareasUI getTarea() {
        return tarea;
    }
}
