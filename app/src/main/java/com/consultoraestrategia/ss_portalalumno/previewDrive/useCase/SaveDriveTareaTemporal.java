package com.consultoraestrategia.ss_portalalumno.previewDrive.useCase;

import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;

public class SaveDriveTareaTemporal {
    PreviewDriveRepository repository;

    public SaveDriveTareaTemporal(PreviewDriveRepository repository) {
        this.repository = repository;
    }

    public void execute(String tareaId, int sesionId, String nombreArchivo, String nombreAchivoLocal, String driveId,long idDownload){
        String id = tareaId;
        if(sesionId>0){
            id = "ses_"+sesionId;
        }
         repository.saveIdDriveTemporal(id, nombreArchivo, nombreAchivoLocal, idDownload, driveId);
    }
}
