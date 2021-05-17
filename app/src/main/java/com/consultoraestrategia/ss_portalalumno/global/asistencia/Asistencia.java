package com.consultoraestrategia.ss_portalalumno.global.asistencia;

import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancelImpl;

public interface Asistencia {

    void desconectarAsistencia();
    void sinInternet(Callback callback);

    interface Callback{
        void onSuccess();
        void onError();
    }

    FirebaseCancelImpl f_getAccionAcistenciaDocente(int silaboEventoId, Callback callback);

    void f_SaveAsistenciaAlumno(int silaboEventoId, boolean activo, boolean pen_sesion, Callback callback);
}
