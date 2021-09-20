package com.consultoraestrategia.ss_portalalumno.previewDrive.useCase;

import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.entities.DriveUi;

public class GetDriveTareaTemporal {
    PreviewDriveRepository repository;

    public GetDriveTareaTemporal(PreviewDriveRepository repository) {
        this.repository = repository;
    }

    public String execute(String tareaId, String nombreArchivo){
        return repository.getIdDriveTemporal(tareaId, nombreArchivo);
    }
}
