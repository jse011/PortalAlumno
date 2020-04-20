package com.consultoraestrategia.ss_portalalumno.entities;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.Date;

/**
 * Created by @stevecampos on 3/01/2018.
 */
public class BaseEntity extends BaseModel {

    public static final int FLAG_EXPORTED = 4;
    public static final int FLAG_ADDED = 1;
    public static final int FLAG_UPDATED = 2;
    public static final int FLAG_DELETED = 3;
    public static final int FLAG_ERROREXPORTED = 5;

    @Column
    public int androidId;
    @Column
    public int syncFlag;
    @Column
    public long timestampFlag;
    @Column
    public int usuarioCreacionId;
    @Column
    public long fechaCreacion;
    @Column
    int usuarioAccionId;
    @Column
    long fechaAccion;
    @PrimaryKey
    public String key;
    @Column
    public String state; //Eliminado, Creado
    public static String STATE_DELETED = "state_deleted";
    public static final String STATE_CREATED = "state_created";



    public BaseEntity() {
        /*SessionUser user = SessionUser.getCurrentUser();
        if (user != null) {
            usuarioAccionId = user.getUserId();
            fechaAccion = getTime();
        }*/
    }


    public BaseEntity(int androidId) {
        this.androidId = androidId;
    }

    public int getAndroidId() {
        return androidId;
    }

    public void setAndroidId(int androidId) {
        this.androidId = androidId;
    }

    public int getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(int syncFlag) {
        this.syncFlag = syncFlag;
        switch (syncFlag) {
            case FLAG_ADDED:
                SessionUser user = SessionUser.getCurrentUser();
                this.fechaCreacion = getTime();
                this.fechaAccion = getTime();
                if (user != null) {
                    usuarioCreacionId = user.getUserId();
                    usuarioAccionId = user.getUserId();
                }
                break;
            case FLAG_UPDATED:
                SessionUser userUpdate = SessionUser.getCurrentUser();
                this.fechaAccion = getTime();
                if (userUpdate != null) {
                    usuarioAccionId = userUpdate.getUserId();
                }
                break;
        }

        this.timestampFlag = getTime();
    }

    public long getTimestampFlag() {
        return timestampFlag;
    }

    public void setTimestampFlag() {
        this.timestampFlag = getTime();
    }

    public int getUsuarioCreacionId() {
        return usuarioCreacionId;
    }

    public void setUsuarioCreacionId(int usuarioCreacionId) {
        this.usuarioCreacionId = usuarioCreacionId;
    }

    public int getUsuarioAccionId() {
        return usuarioAccionId;
    }

    public void setUsuarioAccionId(int usuarioAccionId) {
        this.usuarioAccionId = usuarioAccionId;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(long fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public static long getTime() {
        return new Date().getTime();
    }

    public String getKey() {
        if (TextUtils.isEmpty(key)) {
            generateKey();
        }
        return key;
    }

    public void generateKey() {
        if (TextUtils.isEmpty(key)) {
            key = IdGenerator.generateId();
        }
    }

    public void setKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            this.key = key;
        }
    }


    @Override
    public boolean save(@NonNull DatabaseWrapper databaseWrapper) {
        if (TextUtils.isEmpty(key)) {
            key = IdGenerator.generateId();
        }
        SessionUser user = SessionUser.getCurrentUser();
        if (user != null) {
            usuarioAccionId = user.getUserId();
            fechaAccion = getTime();
        }
        if (TextUtils.isEmpty(state)) {
            state = STATE_CREATED;
        }
        return super.save(databaseWrapper);
    }

    @Override
    public boolean save() {
        if (TextUtils.isEmpty(key)) {
            key = IdGenerator.generateId();
        }
        SessionUser user = SessionUser.getCurrentUser();
        if (user != null) {
            usuarioAccionId = user.getUserId();
            fechaAccion = getTime();
        }
        if (TextUtils.isEmpty(state)) {
            state = STATE_CREATED;
        }
        return super.save();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /*
    @Override
    public boolean delete() {
        if (TextUtils.isEmpty(state)) {
            state = STATE_DELETED;
        }
        return super.update();
    }*/
}
