package com.consultoraestrategia.ss_portalalumno.pregunta.data.source;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.Persona_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaEvaluacionPA;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaEvaluacionPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaPA;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaRespuestaPA;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaRespuestaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancelImpl;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreguntaRepositorioImpl implements PreguntaRepositorio {

    @Override
    public PreguntaUi getPregunta(String preguntaId) {

        PreguntaPA preguntaPA = SQLite.select()
                .from(PreguntaPA.class)
                .where(PreguntaPA_Table.preguntaPAId.eq(preguntaId))
                .querySingle();

        return tranformacion(preguntaPA);
    }

    private PreguntaUi tranformacion(PreguntaPA preguntaPA){

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlExpresiones"))
                .querySingle();

        String pathValores = webconfig!=null?webconfig.getContent():"";

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId = sessionUser!=null?sessionUser.getPersonaId():0;

        PreguntaEvaluacionPA preguntaEvaluacionPA = SQLite.select()
                .from(PreguntaEvaluacionPA.class)
                .where(PreguntaEvaluacionPA_Table.PreguntaId.eq(preguntaPA!=null?preguntaPA.getPreguntaPAId():""))
                .and(PreguntaEvaluacionPA_Table.AlumnoId.eq(alumnoId))
                .querySingle();

        PreguntaUi preguntaUi = new PreguntaUi();
        if(preguntaPA!=null){
            preguntaUi.setId(preguntaPA.getPreguntaPAId());
            preguntaUi.setTitulo(preguntaPA.getPregunta());
            preguntaUi.setTipoId(preguntaPA.getTipoId());
            preguntaUi.setBloqueado(preguntaPA.getBloqueado());
            if(preguntaEvaluacionPA!=null){
                if(preguntaPA.getVersion()==1){

                    TipoNotaC tipoNotaC = SQLite.select()
                            .from(TipoNotaC.class)
                            .where(TipoNotaC_Table.tipoNotaId.eq(preguntaPA.getTipoNotaId()))
                            .querySingle();

                    ValorTipoNotaC valorTipoNotaC = SQLite.select()
                            .from(ValorTipoNotaC.class)
                            .where(ValorTipoNotaC_Table.valorTipoNotaId.eq(preguntaEvaluacionPA.getVariableId()))
                            .and(ValorTipoNotaC_Table.tipoNotaId.eq(preguntaPA.getTipoNotaId()))
                            .querySingle();

                    int tipoId = tipoNotaC!=null?tipoNotaC.getTipoId():0;
                    if (tipoId == 412) {
                        preguntaUi.setNotaTitulo(valorTipoNotaC!=null?valorTipoNotaC.getTitulo():"");
                    } else {
                        preguntaUi.setNotaIcono(valorTipoNotaC!=null?pathValores+valorTipoNotaC.getIcono():"");
                    }
                }else {
                    switch (preguntaPA.getTipoId()){
                        case "1":
                            switch (preguntaEvaluacionPA.getVariableId()){
                                case "1":
                                    preguntaUi.setNotaIcono("Contento.png");
                                    break;
                                case "2":
                                    preguntaUi.setNotaIcono("Neutral.png");
                                    break;
                                case "3":
                                    preguntaUi.setNotaIcono("Triste.png");
                                    break;
                                case "4":
                                    preguntaUi.setNotaIcono("Muy Triste.png");
                                    break;
                            }
                            preguntaUi.setNotaIcono(pathValores+preguntaUi.getNotaIcono());
                            break;
                        case "2":
                            switch (preguntaEvaluacionPA.getVariableId()){
                                case "1":
                                    preguntaUi.setNotaIcono("Destacado_Pollitos.pngg");
                                    break;
                                case "2":
                                    preguntaUi.setNotaIcono("Previsto_Pollitos.png");
                                    break;
                                case "3":
                                    preguntaUi.setNotaIcono("Proceso_Pollitos.png");
                                    break;
                                case "4":
                                    preguntaUi.setNotaIcono("Inicio_Pollitos.png");
                                    break;
                            }
                            preguntaUi.setNotaIcono(pathValores+preguntaUi.getNotaIcono());
                            break;
                        case "3":
                            switch (preguntaEvaluacionPA.getVariableId()){
                                case "1":
                                    preguntaUi.setNotaTitulo("AD");
                                    break;
                                case "2":
                                    preguntaUi.setNotaTitulo("A");
                                    break;
                                case "3":
                                    preguntaUi.setNotaTitulo("B");
                                    break;
                                case "4":
                                    preguntaUi.setNotaTitulo("C");
                                    break;
                            }
                            break;
                    }

                }
            }
        }

        return preguntaUi;
    }

    @Override
    public FirebaseCancel updateRespuesta(int cargaCursoId, int sesionAprendizajeId, String preguntaId, CallbackRespuesta callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        PreguntaPA preguntaPA = SQLite.select()
                .from(PreguntaPA.class)
                .where(PreguntaPA_Table.preguntaPAId.eq(preguntaId))
                .querySingle();
        String tipoId = preguntaPA!=null?preguntaPA.getTipoId():"";

       SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;

        SQLite.delete()
                .from(PreguntaRespuestaPA.class)
                .where(PreguntaRespuestaPA_Table.preguntaPAId.eq(preguntaId))
                .execute();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        DatabaseReference databaseReference = mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/" + preguntaId);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                callback.addRespuesta(transform(dataSnapshot, personaId, preguntaId));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                callback.updateRespuesta(transform(dataSnapshot, personaId, preguntaId));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                SQLite.delete()
                        .from(PreguntaRespuestaPA.class)
                        .where(PreguntaRespuestaPA_Table.preguntaRespuestaPAId.eq(dataSnapshot.getKey()))
                        .execute();
                callback.removeRespuesta(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        if(tipoId.equals("2")){
             return new FirebaseCancelImpl(databaseReference,childEventListener);
        }else {
            return new FirebaseCancelImpl(databaseReference.orderByChild("PersonaId").equalTo(String.valueOf(personaId)),childEventListener);
            //return new FirebaseCancelImpl(databaseReference,childEventListener);
        }


    }

    @Override
    public PreguntaUi getPersona(String preguntaPaId) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlFotos"))
                .querySingle();
        String urlFoto = webconfig!=null?webconfig.getContent():"";
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;


        Persona persona = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(personaId))
                .querySingle();
        PreguntaPA preguntaPA = SQLite.select()
                .from(PreguntaPA.class)
                .where(PreguntaPA_Table.preguntaPAId.eq(preguntaPaId))
                .querySingle();

        //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
        String fileName = persona!=null?persona.getFoto():"";
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        fileName = fileName.substring(p + 1);

        PreguntaUi preguntaUi =  new PreguntaUi();
        preguntaUi.setFoto(persona!=null?(urlFoto+persona.getPersonaId()+"/"+fileName):"");
        preguntaUi.setId(preguntaPaId);
        preguntaUi.setTitulo(preguntaPA!=null?preguntaPA.getPregunta():"");
        preguntaUi.setTipoId(preguntaPA!=null?preguntaPA.getTipoId():"");
        return preguntaUi;
    }

    @Override
    public void saveRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, String preguntaRespuestaId, String contenido, CallbackSaveRespuesta callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlFotos"))
                .querySingle();
        String urlFoto = webconfig!=null?webconfig.getContent():"";

        webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;

        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        DatabaseReference databaseReference = mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/"+preguntaId);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Enunciado",contenido);
        respuesta.put("FechaHora", ServerValue.TIMESTAMP);
        respuesta.put("PersonaId", String.valueOf(personaId));

        Persona persona = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(personaId))
                .querySingle();

        //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
        String fileName = persona!=null?persona.getFoto():"";
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        fileName = fileName.substring(p + 1);

        if(TextUtils.isEmpty(preguntaRespuestaId)){
            databaseReference = databaseReference.push();
            RespuestaUi respuestaUi = new RespuestaUi();
            respuestaUi.setContenido(contenido);
            respuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
            respuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
            respuestaUi.setEditar(false);
            respuestaUi.setResponder(false);
            respuestaUi.setOffline(true);
            respuestaUi.setSubrecursoList(new ArrayList<>());
            respuestaUi.setFecha("cargando ...");
            respuestaUi.setId(databaseReference.getKey());
            callback.onPreLoad(respuestaUi, null);

            databaseReference.setValue(respuesta, (databaseError, databaseReference1) -> {
                if(databaseError!=null){
                    callback.onLoad(false);
                }else {
                    callback.onLoad(true);
                }
            });
        }else {
            databaseReference = databaseReference.child(preguntaRespuestaId+"/Respuestas").push();
            SubRespuestaUi subRespuestaUi = new SubRespuestaUi();
            subRespuestaUi.setContenido(contenido);
            subRespuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
            subRespuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
            subRespuestaUi.setFecha("cargando ...");
            subRespuestaUi.setParentId(preguntaRespuestaId);
            subRespuestaUi.setEditar(false);
            subRespuestaUi.setOffline(true);
            subRespuestaUi.setId(databaseReference.getKey());
            callback.onPreLoad(null, subRespuestaUi);

            databaseReference.setValue(respuesta, (databaseError, databaseReference12) -> {
                if(databaseError!=null){
                    callback.onLoad(false);
                }else {
                    callback.onLoad(true);
                }
                    });
        }




    }

    @Override
    public void updateRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, RespuestaUi respuestaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Enunciado",respuestaUi.getContenido());

       mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/"+preguntaId+"/"+respuestaUi.getId())
               .updateChildren(respuesta, (databaseError, databaseReference) -> {
                   if(databaseError!=null){
                       callback.onLoad(false);
                   }else {
                       callback.onLoad(true);
                   }
               });

    }

    @Override
    public void updateSubRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Enunciado",subRespuestaUi.getContenido());

        mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/"+preguntaId+"/"+subRespuestaUi.getParentId() + "/Respuestas/"+subRespuestaUi.getId())
                .updateChildren(respuesta, (databaseError, databaseReference) -> {
                    if(databaseError!=null){
                        callback.onLoad(false);
                    }else {
                        callback.onLoad(true);
                    }
                });
    }

    @Override
    public void eiminarRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, RespuestaUi respuestaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);

        mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/"+preguntaId+"/"+respuestaUi.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if(databaseError!=null){
                        callback.onLoad(false);
                    }else {
                        callback.onLoad(true);
                    }
                });
    }

    @Override
    public void eliminarSubRespuestaFB(int cargaCursoId, int sesionAprendizajeId, String preguntaId, SubRespuestaUi subRespuestaUi, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Respuestas/"+preguntaId+"/"+subRespuestaUi.getParentId() + "/Respuestas/"+subRespuestaUi.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if(databaseError!=null){
                        callback.onLoad(false);
                    }else {
                        callback.onLoad(true);
                    }
                });
    }

    @Override
    public List<RespuestaUi> getListaRespuesta(String preguntaId) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlFotos"))
                .querySingle();
        String urlFoto = webconfig!=null?webconfig.getContent():"";

        List<RespuestaUi> respuestaUiList = new ArrayList<>();
        List<PreguntaRespuestaPA> list = SQLite.select()
                .from(PreguntaRespuestaPA.class)
                .where(PreguntaRespuestaPA_Table.preguntaPAId.eq(preguntaId))
                .and(PreguntaRespuestaPA_Table.parentId.isNull())
                .queryList();
        for (PreguntaRespuestaPA preguntaRespuestaPA : list){
            RespuestaUi respuestaUi = new RespuestaUi();
            respuestaUi.setId(preguntaRespuestaPA.getPreguntaRespuestaPAId());
            Persona persona = SQLite.select()
                    .from(Persona.class)
                    .where(Persona_Table.personaId.eq(preguntaRespuestaPA.getPersonaId()))
                    .querySingle();

            int personaId = persona!=null?persona.getPersonaId():0;
            //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
            String fileName = persona!=null?persona.getFoto():"";
            int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
            fileName = fileName.substring(p + 1);

            respuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
            respuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
            respuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(preguntaRespuestaPA.getFechaHora()));
            respuestaUi.setContenido(preguntaRespuestaPA.getEnunciado());
            respuestaUi.setEditar(false);
            respuestaUi.setOffline(true);
            respuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(preguntaRespuestaPA.getFechaHora()));
            List<SubRespuestaUi> subRespuestaUiList = new ArrayList<>();
            for (PreguntaRespuestaPA preguntaRespuestaPA1 : SQLite.select()
                    .from(PreguntaRespuestaPA.class)
                    .where(PreguntaRespuestaPA_Table.parentId
                            .eq(preguntaRespuestaPA.getPreguntaRespuestaPAId())).queryList()){

                SubRespuestaUi subRespuestaUi = new SubRespuestaUi();
                respuestaUi.setId(preguntaRespuestaPA.getPreguntaRespuestaPAId());
                persona = SQLite.select()
                        .from(Persona.class)
                        .where(Persona_Table.personaId.eq(preguntaRespuestaPA1.getPersonaId()))
                        .querySingle();

                personaId = persona!=null?persona.getPersonaId():0;
                //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
                fileName = persona!=null?persona.getFoto():"";
                p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
                fileName = fileName.substring(p + 1);

                subRespuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
                subRespuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
                subRespuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(preguntaRespuestaPA1.getFechaHora()));
                subRespuestaUi.setEditar(false);
                subRespuestaUi.setContenido(preguntaRespuestaPA1.getEnunciado());
                subRespuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(preguntaRespuestaPA1.getFechaHora()));
                subRespuestaUi.setOffline(true);
                subRespuestaUiList.add(subRespuestaUi);
            }

            respuestaUi.setResponder(!subRespuestaUiList.isEmpty());
            respuestaUi.setSubrecursoList(subRespuestaUiList);
            respuestaUiList.add(respuestaUi);
        }
        return respuestaUiList;
    }

    @Override
    public List<PreguntaUi> getListPreguntas(int sesionAprendizajeId) {

        List<PreguntaPA> preguntaPAList = SQLite.select()
                .from(PreguntaPA.class)
                .where(PreguntaPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .queryList();
        List<PreguntaUi> preguntaUiList =  new ArrayList<>();
        for (PreguntaPA preguntaPA : preguntaPAList){
            preguntaUiList.add(tranformacion(preguntaPA));
        }

        return preguntaUiList;
    }

    @Override
    public FirebaseCancel updatebloquePregunta(int cargaCursoId, int sesionAprendizajeId, String preguntaId, CallbackPregunta callbackPregunta) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        SilaboEvento silaboEvento = SQLite.select()
                .from(SilaboEvento.class)
                .where(SilaboEvento_Table.cargaCursoId.eq(cargaCursoId))
                .querySingle();

        int silaboEventoId = silaboEvento!=null?silaboEvento.getSilaboEventoId():0;

        SesionAprendizaje sesionAprendizaje = SQLite.select()
                .from(SesionAprendizaje.class)
                .where(SesionAprendizaje_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .querySingle();

        int unidadAprendizajeid = sesionAprendizaje!=null?sesionAprendizaje.getUnidadAprendizajeId():0;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        return new FirebaseCancelImpl(mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Preguntas/"+preguntaId),new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if("Pregunta".equals(dataSnapshot.getKey())){
                    callbackPregunta.onChangeNombre(UtilsFirebase.convert(dataSnapshot.getValue(), ""));
                }else if("Bloqueado".equals(dataSnapshot.getKey())){
                    if(UtilsFirebase.convert(dataSnapshot.getValue(), false)){
                        callbackPregunta.onDesbloqueado();
                    }else {
                        callbackPregunta.onBloqueado();
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private RespuestaUi transform(DataSnapshot respuestaSnapshot, int personaActualId, String preguntaId){
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlFotos"))
                .querySingle();
        String urlFoto = webconfig!=null?webconfig.getContent():"";

        List<SubRespuestaUi> subRespuestaUiList = new ArrayList<>();
        PreguntaRespuestaPA preguntaRespuestaPA = new PreguntaRespuestaPA();
        preguntaRespuestaPA.setPreguntaRespuestaPAId(respuestaSnapshot.getKey());
        preguntaRespuestaPA.setEnunciado(UtilsFirebase.convert(respuestaSnapshot.child("Enunciado").getValue(), ""));
        preguntaRespuestaPA.setFechaHora(UtilsFirebase.convert(respuestaSnapshot.child("FechaHora").getValue(), 0L));
        preguntaRespuestaPA.setPersonaId(UtilsFirebase.convert(respuestaSnapshot.child("PersonaId").getValue(), 0));
        preguntaRespuestaPA.setPreguntaPAId(preguntaId);
        preguntaRespuestaPA.save();

        RespuestaUi respuestaUi = new RespuestaUi();
        respuestaUi.setId(preguntaRespuestaPA.getPreguntaRespuestaPAId());
        respuestaUi.setContenido(preguntaRespuestaPA.getEnunciado());
        Persona persona = SQLite.select()
                .from(Persona.class)
                .where(Persona_Table.personaId.eq(preguntaRespuestaPA.getPersonaId()))
                .querySingle();

        int personaId = persona!=null?persona.getPersonaId():0;
        //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
        String fileName = persona!=null?persona.getFoto():"";
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        fileName = fileName.substring(p + 1);

        respuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
        respuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
        respuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(preguntaRespuestaPA.getFechaHora()));
        respuestaUi.setEditar(personaActualId==personaId);

        SQLite.delete()
                .from(PreguntaRespuestaPA.class)
                .where(PreguntaRespuestaPA_Table.parentId.eq(respuestaSnapshot.getKey()))
                .execute();

        if(respuestaSnapshot.child("Respuestas").exists()){
            for (DataSnapshot subrespuestaSnapshot : respuestaSnapshot.child("Respuestas").getChildren()){
                PreguntaRespuestaPA subpreguntaRespuestaPA = new PreguntaRespuestaPA();
                subpreguntaRespuestaPA.setPreguntaRespuestaPAId(subrespuestaSnapshot.getKey());
                subpreguntaRespuestaPA.setEnunciado(UtilsFirebase.convert(subrespuestaSnapshot.child("Enunciado").getValue(), ""));
                subpreguntaRespuestaPA.setFechaHora(UtilsFirebase.convert(subrespuestaSnapshot.child("FechaHora").getValue(), 0L));
                subpreguntaRespuestaPA.setPersonaId(UtilsFirebase.convert(subrespuestaSnapshot.child("PersonaId").getValue(), 0));
                subpreguntaRespuestaPA.setParentId(respuestaSnapshot.getKey());
                subpreguntaRespuestaPA.save();

                SubRespuestaUi subrespuestaUi = new SubRespuestaUi();
                subrespuestaUi.setId(subpreguntaRespuestaPA.getPreguntaRespuestaPAId());
                subrespuestaUi.setParentId(respuestaSnapshot.getKey());
                subrespuestaUi.setContenido(subpreguntaRespuestaPA.getEnunciado());
                persona = SQLite.select()
                        .from(Persona.class)
                        .where(Persona_Table.personaId.eq(subpreguntaRespuestaPA.getPersonaId()))
                        .querySingle();

                personaId = persona!=null?persona.getPersonaId():0;
                //* Se obtine el archivo dela ruta por que alcutilzar los alumno del firebase no tinen ruta solo el nombre archivo*//
                fileName = persona!=null?persona.getFoto():"";
                p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
                fileName = fileName.substring(p + 1);

                subrespuestaUi.setFoto(persona!=null?(urlFoto+personaId+"/"+fileName):"");
                subrespuestaUi.setNombre(persona!=null?UtilsPortalAlumno.capitalize(UtilsPortalAlumno.getFirstWord(persona.getNombres()))+" "+UtilsPortalAlumno.capitalize(persona.getApellidoPaterno())+" "+UtilsPortalAlumno.capitalize(persona.getApellidoMaterno()):"");
                subrespuestaUi.setFecha(UtilsPortalAlumno.f_fecha_Pregunta(subpreguntaRespuestaPA.getFechaHora()));
                subrespuestaUi.setEditar(personaActualId==personaId);
                subRespuestaUiList.add(subrespuestaUi);
            }
        }

        respuestaUi.setSubrecursoList(subRespuestaUiList);
        respuestaUi.setResponder(!subRespuestaUiList.isEmpty());
        return respuestaUi;
    }
}
