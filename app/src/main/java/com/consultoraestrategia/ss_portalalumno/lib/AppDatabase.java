package com.consultoraestrategia.ss_portalalumno.lib;


import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, insertConflict = ConflictAction.REPLACE)
public class AppDatabase {
    public static final String NAME = "iCRMEAlumno";
    //public static final int VERSION = 1;
    //public static final int VERSION = 4;
    //public static final int VERSION = 5;//Agrgaront tablas para la tarea
    //public static final int VERSION = 6;Agregaron Evidencias
    public static final int VERSION = 7;

}
