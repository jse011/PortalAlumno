package com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase;


import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;

public class UpdateSuccesDowloadArchivo {
    private ActividadesRepository repository;

    public UpdateSuccesDowloadArchivo(ActividadesRepository repository) {
        this.repository = repository;
    }

    public void execute(Request request, final Callback callback) {
        repository.updateSucessDowload(request.getArchivoId(), request.getPath(), new ActividadesRepository.Callback<Boolean>() {
            @Override
            public void onLoad(boolean success, Boolean item) {
                callback.onResponse(success);
            }
        });
    }

    public static class Request {
        String archivoId;
        String path;

        public Request(String archivoId, String path) {
            this.archivoId = archivoId;
            this.path = path;
        }

        public String getArchivoId() {
            return archivoId;
        }

        public void setArchivoId(String archivoId) {
            this.archivoId = archivoId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public interface Callback{
        void onResponse(boolean success);
    }
}