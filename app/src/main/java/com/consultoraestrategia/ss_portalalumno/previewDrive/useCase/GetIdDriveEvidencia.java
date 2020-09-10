package com.consultoraestrategia.ss_portalalumno.previewDrive.useCase;

import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public class GetIdDriveEvidencia {
  private PreviewDriveRepository repository;

    public GetIdDriveEvidencia(PreviewDriveRepository repository) {
        this.repository = repository;
    }

    public RetrofitCancel execute(int sesionAprendizajeId,String nombreArchivo, Callback callback){
        return repository.getIdDriveEvidencia(sesionAprendizajeId, nombreArchivo, new PreviewDriveRepository.Callback<DriveUi>(){
            @Override
            public void onLoad(boolean success, DriveUi item) {
                callback.onLoad(success, item);
            }
        });
    }

    public interface Callback{
        void onLoad(boolean success, DriveUi driveUi);
    }
}
