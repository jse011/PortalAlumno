package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;


import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;

public class GetLikeSaveCountLike {
    private MainRepositorio repository;

    public GetLikeSaveCountLike(MainRepositorio configuracionRepository) {
        this.repository = configuracionRepository;
    }

    public void execute(EventoUi request, Callback callback) {
        repository.getLikesSaveCountLike(request, new MainRepositorio.Callback<EventoUi>() {
            @Override
            public void onLoad(boolean success, EventoUi item) {
                if(success){
                    repository.updateLike(item);
                    callback.onResponse(item);
                }

            }
        });
    }

    public interface Callback{
        void  onResponse(EventoUi eventoUi);
    }
}
