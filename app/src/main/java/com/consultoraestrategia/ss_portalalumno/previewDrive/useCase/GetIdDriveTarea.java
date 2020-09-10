package com.consultoraestrategia.ss_portalalumno.previewDrive.useCase;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;

public class GetIdDriveTarea {
  private PreviewDriveRepository repository;

    public GetIdDriveTarea(PreviewDriveRepository repository) {
        this.repository = repository;
    }

    public RetrofitCancel execute(String tareaId,String nombreArchivo, Callback callback){
        return repository.getIdDrive(tareaId, nombreArchivo, new PreviewDriveRepository.Callback<DriveUi>(){
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
