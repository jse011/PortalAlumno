package com.consultoraestrategia.ss_portalalumno.util;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.entities.BaseEntity;
import com.consultoraestrategia.ss_portalalumno.entities.BaseRelEntity;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.util.List;

public class TransaccionUtils {
    private static final String TAG = TransaccionUtils.class.getSimpleName();

    private static boolean isValido(Object o, boolean notNull){
        /*if(!notNull){*/
        if(o == null)return false;
        /*}*/
        return true;
    }

    public static  <P extends BaseModel> void fastStoreListInsert(final Class<P> clazz, List<P> list, DatabaseWrapper database, boolean notNull) {
        if((!isValido(list,notNull)))return;
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);
    }

    public static  <P extends BaseRelEntity> void fastStoreListInsertRel(final Class<P> clazz, List<P> list, DatabaseWrapper database, boolean notNull) {
        if((!isValido(list,notNull)))return;
        SQLite.delete().from(clazz).execute();
        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);
    }

    public static  <P extends BaseModel> void fastStoreListSave(final Class<P> clazz, List<P> list, DatabaseWrapper database, boolean notNull) {
        if((!isValido(list,notNull)))return;
        FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);
    }


    public static  <P extends BaseEntity> void fastStoreListSyncFlagUpdate(final Class<P> clazz, List<P> list, int syncFlag, DatabaseWrapper database, boolean notNull) {
        if((!isValido(list,notNull)))return;
        for (P iterator: list){iterator.setSyncFlag(syncFlag);}
        FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);
    }

    public static  <P extends BaseRelEntity> void fastStoreListSyncFlagUpdateRel(final Class<P> clazz, List<P> list, int syncFlag, DatabaseWrapper database, boolean notNull) {
        if((!isValido(list,notNull)))return;
        for (P iterator: list){iterator.setSyncFlag(syncFlag);}
        FastStoreModelTransaction
                .updateBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);

    }

    public static  <P extends BaseEntity> void fastStoreListComprobacionSave(final Class<P> clazz, List<P> list, boolean notNull) {
        if((!isValido(list,notNull)))return;
        final Property<String> key = new Property<String>(clazz, "key");
        for (P itemServicio : list){
            double insert = itemServicio.insert();
            Log.d(TAG,clazz.getSimpleName()+": "+insert);
            if(insert != -1)continue;
            P itemLocal = SQLite.select()
                    .from(clazz)
                    .where(key.is(itemServicio.getKey()))
                    .querySingle();
            if(itemServicio.getFechaAccion() >= itemLocal.getFechaAccion()){
                itemServicio.setSyncFlag(BaseEntity.FLAG_EXPORTED);
                itemServicio.save();
            }
        }
    }

    public static  <P extends BaseRelEntity> void fastStoreListComprobacionSave(final Class<P> clazz, List<P> list, boolean notNull, SQLOperator... conditions) {
        if((!isValido(list,notNull)))return;
        for (P itemServicio : list){
            double insert = itemServicio.insert();
            if(insert != -1)continue;
            P itemLocal = SQLite.select()
                    .from(clazz)
                    .where(conditions)
                    .querySingle();
            if(itemServicio.getFechaAccion() >= itemLocal.getFechaAccion()){
                itemServicio.setSyncFlag(BaseRelEntity.FLAG_EXPORTED);
                itemServicio.save();
            }
        }
    }

    public static  <P extends BaseModel> void fastStoreListSetData(final Class<P> clazz, List<P> list, DatabaseWrapper database, boolean notNull, SQLOperator... conditions) {
        if((!isValido(list,notNull)))return;
        if(clazz.getSuperclass().equals(BaseEntity.class)){
            final Property<Integer> syncFlag = new Property<Integer>(clazz, "syncFlag");
            SQLite.delete().from(clazz).where(syncFlag.eq(0)).execute();
        }else {
            SQLite.delete().from(clazz).execute();
        }

        FastStoreModelTransaction
                .insertBuilder(FlowManager.getModelAdapter(clazz))
                .addAll(list)
                .build()
                .execute(database);
    }

    public static <P extends BaseModel> void deleteTable(Class<P> clazz, SQLOperator ... conditions){
        SQLite.delete().from(clazz).where(conditions).execute();
    }

    public static <P extends BaseModel> void deleteTable(DatabaseWrapper databaseWrapper, Class<P> clazz, SQLOperator ... conditions){
        SQLite.delete().from(clazz).where(conditions).execute(databaseWrapper);
    }
}
