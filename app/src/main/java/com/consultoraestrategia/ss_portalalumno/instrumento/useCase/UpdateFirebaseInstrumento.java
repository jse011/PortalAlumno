package com.consultoraestrategia.ss_portalalumno.instrumento.useCase;

import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;

public class UpdateFirebaseInstrumento {
    private InstrumentoRepository instrumentoRepository;

    public UpdateFirebaseInstrumento(InstrumentoRepository instrumentoRepository) {
        this.instrumentoRepository = instrumentoRepository;
    }

    public void execute(int sesionAprendizajeId, int cargaCursoId, int alumnoId, CallBack callBack){
        instrumentoRepository.updateFirebaseInstrumento(sesionAprendizajeId, cargaCursoId, alumnoId, new InstrumentoRepository.CallbackSimple() {
            @Override
            public void onLoad(boolean success) {
                if(success){
                    callBack.onSucces();
                }else {
                    callBack.onError("");
                }
            }
        });
    }

    public interface CallBack{
        void onSucces();
        void onError(String error);
    }
}
