package com.consultoraestrategia.ss_portalalumno.previewDrive.useCase;

import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;

public class GetDriveTareaTemporal {
    PreviewDriveRepository repository;

    public GetDriveTareaTemporal(PreviewDriveRepository repository) {
        this.repository = repository;
    }

    public DriveUi execute(String tareaId, int sesionId, String nombreArchivo){
        String id = tareaId;
        if(sesionId>0){
            id = "ses_"+sesionId;
        }
        return repository.getIdDriveTemporal(id, nombreArchivo);
    }
}
