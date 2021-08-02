package com.consultoraestrategia.ss_portalalumno.main.domain.usecase;

import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.Date;
import java.util.List;

public class GetEventoAgenda {
    MainRepositorio repositorio;

    public GetEventoAgenda(MainRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public RetrofitCancel execute(GetEventoAgendaParams params, Callback callback){

        callback.onPreload(new GetEventoAgendaResponse(getTipos(params.getSelectedTipoEventoUi()), null));

        return repositorio.getEventoAgenda(params.getUsuarioId(), params.getAlumnoId(), params.getSelectedTipoEventoUi().getId(), new MainRepositorio.Callback<List<EventoUi>>() {
            @Override
            public void onLoad(boolean success, List<EventoUi> eventoUIList) {
                List<TipoEventoUi> tiposUiList = getTipos(params.getSelectedTipoEventoUi());

                for(EventoUi eventosUi : eventoUIList){
                    Date fechaEntrega =  eventosUi.getFecha();
                    if(fechaEntrega!=null && fechaEntrega.getTime()>912402000000L){
                        switch (eventosUi.getTipoEventoUi().getTipo()){
                            case EVENTO:
                                //tipo += eventosUi.getFechaEvento() > 0 ?" " + :"";
                                eventosUi.setNombreFecha(UtilsPortalAlumno.tiempoFechaCreacion(eventosUi.getFecha().getTime()));
                                break;
                            case NOTICIA:
                                eventosUi.setNombreFecha(UtilsPortalAlumno.getFechaDiaMesAnho(eventosUi.getFecha().getTime()));
                                break;
                            default:
                                eventosUi.setNombreFecha(UtilsPortalAlumno.tiempoFechaCreacion(eventosUi.getFecha().getTime()));
                                break;
                        }
                    }else{
                        eventosUi.setNombreFecha("");
                    }
                    if(eventosUi.getFechaPublicacion()!=null && eventosUi.getFechaPublicacion().getTime()>912402000000L){
                        String fecha_ = "Publicado " + UtilsPortalAlumno.getFechaDiaMesAnho(eventosUi.getFechaPublicacion().getTime());
                        eventosUi.setNombreFechaPublicion(fecha_);
                    }else{
                        eventosUi.setNombreFechaPublicion("");
                    }



                }



                callback.onLoad(new GetEventoAgendaResponse(tiposUiList, eventoUIList));
            }
        });

    }

    List<TipoEventoUi> getTipos(TipoEventoUi selectedTipoEventoUi){
        List<TipoEventoUi> tiposUiList = repositorio.getTiposEvento();
        for (TipoEventoUi item : tiposUiList){
            if(item.getId()==selectedTipoEventoUi.getId()){
                item.setToogle(true);
            }
        }
        return tiposUiList;
    }

    public interface Callback{
        void onPreload(GetEventoAgendaResponse response);

        void onLoad(GetEventoAgendaResponse response);
    }

    public static class GetEventoAgendaResponse{
        List<TipoEventoUi> tipoEventoUiList;
        List<EventoUi> eventoUiList;

        public GetEventoAgendaResponse(List<TipoEventoUi> tipoEventoUiList, List<EventoUi> eventoUiList) {
            this.tipoEventoUiList = tipoEventoUiList;
            this.eventoUiList = eventoUiList;
        }

        public List<TipoEventoUi> getTipoEventoUiList() {
            return tipoEventoUiList;
        }


        public List<EventoUi> getEventoUiList() {
            return eventoUiList;
        }

    }


    public static class GetEventoAgendaParams {
        int usuarioId;
        int alumnoId;
        TipoEventoUi selectedTipoEventoUi;

        public GetEventoAgendaParams(int usuarioId, int alumnoId, TipoEventoUi selectedTipoEventoUi) {
            this.usuarioId = usuarioId;
            this.alumnoId = alumnoId;
            this.selectedTipoEventoUi = selectedTipoEventoUi;
        }


        public int getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(int usuarioId) {
            this.usuarioId = usuarioId;
        }

        public int getAlumnoId() {
            return alumnoId;
        }

        public void setAlumnoId(int alumnoId) {
            this.alumnoId = alumnoId;
        }

        public TipoEventoUi getSelectedTipoEventoUi() {
            return selectedTipoEventoUi;
        }

        public void setSelectedTipoEventoUi(TipoEventoUi selectedTipoEventoUi) {
            this.selectedTipoEventoUi = selectedTipoEventoUi;
        }
    }
}
