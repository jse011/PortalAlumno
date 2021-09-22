package com.consultoraestrategia.ss_portalalumno.entities;

import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


@Table(database = AppDatabase.class)
public class DriveArchivoLocal extends BaseModel {

    @PrimaryKey
    private String driveArchivoLocalId;
    @PrimaryKey
    private String nombreArchivo;
    @Column
    private String driveId;
    @Column
    private String nombreLocal;
    @Column
    private long idDownload;

    public DriveArchivoLocal() {
    }

    public DriveArchivoLocal(String driveArchivoLocalId) {
        this.driveArchivoLocalId = driveArchivoLocalId;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getDriveArchivoLocalId() {
        return driveArchivoLocalId;
    }

    public void setDriveArchivoLocalId(String driveArchivoLocalId) {
        this.driveArchivoLocalId = driveArchivoLocalId;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public long getIdDownload() {
        return idDownload;
    }

    public void setIdDownload(long idDownload) {
        this.idDownload = idDownload;
    }
}
