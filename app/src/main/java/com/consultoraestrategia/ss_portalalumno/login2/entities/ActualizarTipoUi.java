package com.consultoraestrategia.ss_portalalumno.login2.entities;

public enum  ActualizarTipoUi {
    Resultado("Resultado evaluación"), Rubros("Rubro evaluación"), Grupos("Grupos"), Tareas("Tarea"), Casos("Casos"), Asistencias("Asistencia"), TipoNota("Nivel de logro"), Estudiantes("Estudiantes y sus familias"), Unidades("Unidades"), Docente("Docente"), Dimencion_Desarrollo("Dimensión Desarrollo");
    private String nombre;

    ActualizarTipoUi(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }


}
