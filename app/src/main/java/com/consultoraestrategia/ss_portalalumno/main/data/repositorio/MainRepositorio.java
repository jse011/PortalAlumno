package com.consultoraestrategia.ss_portalalumno.main.data.repositorio;


import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.AnioAcademicoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

import java.util.List;

public interface MainRepositorio {
    AlumnoUi getAlumno();
    AnioAcademicoUi getAnioAcademico(int personaId);
    List<CursosUi> getCursos(int personaId, int anioAcademicoId);

    RetrofitCancel updateCalendarioPeriodo(int anioAcademico, SuccessCallback callback);
    RetrofitCancel getEventoAgenda(int usuarioId, int alumnoId,int tipoEventoId, Callback<List<EventoUi>> objectCallback);

    List<TipoEventoUi> getTiposEvento();

    void updateLike(EventoUi item);

    void getLikesSaveCountLike(EventoUi request, Callback<EventoUi> callback);

    void saveLikeRealTime(EventoUi eventosUi);

    void saveLike(EventoUi eventosUi);

    String getIconoPortalAlumno();

    String getColorTarjetaId();

    String getNombreServidor();

    interface SuccessCallback{
        void onLoad(boolean success);
    }

    interface Callback<T>{
        void onLoad(boolean success, T o);
    }

    void pdateFirebaseTipoNota(int programaEducativoId, SuccessCallback callback);


}
