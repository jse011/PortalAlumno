package com.consultoraestrategia.ss_portalalumno.previewDrive.data.source;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;

public interface PreviewDriveRepository {
    RetrofitCancel getIdDrive(String tareaId, String nombreArchivo, Callback<DriveUi> driveUiCallback);

    RetrofitCancel getIdDriveEvidencia(int sesionAprendizajeId, String nombreArchivo, Callback<DriveUi> driveUiCallback);

    interface Callback <T>{
        void onLoad(boolean success, T item);
    }
}
