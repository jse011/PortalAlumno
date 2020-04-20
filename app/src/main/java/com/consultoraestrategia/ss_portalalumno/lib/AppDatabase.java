package com.consultoraestrategia.ss_portalalumno.lib;


import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, insertConflict = ConflictAction.REPLACE)
public class AppDatabase {
    public static final String NAME = "iCRMEAlumno";
    public static final int VERSION = 1;//Pasar a la vercion 9
}
