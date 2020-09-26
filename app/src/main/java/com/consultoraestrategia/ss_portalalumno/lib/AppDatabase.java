package com.consultoraestrategia.ss_portalalumno.lib;


import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.Usuario;
import com.consultoraestrategia.ss_portalalumno.entities.Usuario_Table;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION, insertConflict = ConflictAction.REPLACE)
public class AppDatabase {
    public static final String NAME = "iCRMEAlumno";
    //public static final int VERSION = 1;
    //public static final int VERSION = 4;
    //public static final int VERSION = 5;//Agrgaront tablas para la tarea
    //public static final int VERSION = 6;Agregaron Evidencias
    //public static final int VERSION = 7;//Aregar el campo habilitar acceso
    public static final int VERSION = 8;


    @Migration(version = 8, database = AppDatabase.class)
    public static class AlterUsuario extends AlterTableMigration<Usuario> {

        public AlterUsuario(Class<Usuario> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            addColumn(SQLiteType.INTEGER, "habilitarAcceso");
        }

        @Override
        public void onPostMigrate() {
            super.onPostMigrate();
        }


    }

    @Migration(version = 8, database = AppDatabase.class)
    public static class MigrationUsuario extends BaseMigration {

        @Override
        public void migrate(@NonNull DatabaseWrapper database) {
            // run some code here
            SQLite.update(Usuario.class)
                    .set(Usuario_Table.habilitarAcceso.eq(true))
                    .execute(database);
        }
    }



}
