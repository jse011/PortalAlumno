package com.consultoraestrategia.ss_portalalumno.instrumento.data.source;

import com.consultoraestrategia.ss_portalalumno.instrumento.entities.EncuestaUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;

import java.util.List;

public interface InstrumentoRepository {

    void saveFirebaseInstrumento(int cargaCursoId, int sesionAprensizajeId, VariableUi variableUi, CallbackSimple callbackSimple);
    List<InstrumentoUi> getInstrumentos(int sesionAprendizajeId);
    InstrumentoUi getInstrumento(int instrumentoId);
    List<EncuestaUi> getInstrumentoEncuesta(int sesionAprendizajeId, int personaId);

    interface CallbackSimple{
        void onLoad(boolean success);
    }
}
