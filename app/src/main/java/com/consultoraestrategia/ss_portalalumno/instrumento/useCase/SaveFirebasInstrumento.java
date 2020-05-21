package com.consultoraestrategia.ss_portalalumno.instrumento.useCase;

import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;

public class SaveFirebasInstrumento {
    private InstrumentoRepository repository;

    public SaveFirebasInstrumento(InstrumentoRepository repository) {
        this.repository = repository;
    }

    public void execute(int cargaCursoId, int sesionAprensizajeId, VariableUi variableUi, CallBack callBack){
        repository.saveFirebaseInstrumento(cargaCursoId, sesionAprensizajeId, variableUi, new InstrumentoRepository.CallbackSimple() {
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
