package com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.entities.Archivo;
import com.consultoraestrategia.ss_portalalumno.entities.Archivo_Table;
import com.consultoraestrategia.ss_portalalumno.entities.BaseEntity;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA;
import com.consultoraestrategia.ss_portalalumno.entities.ColborativaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.GrabacionSalaVirtual;
import com.consultoraestrategia.ss_portalalumno.entities.GrabacionSalaVirtual_Table;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEncuestaEval;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEncuestaEval_Table;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Persona;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaEvaluacionPA;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaPA;
import com.consultoraestrategia.ss_portalalumno.entities.PreguntaPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoArchivo;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoDidacticoEventoC;
import com.consultoraestrategia.ss_portalalumno.entities.RecursoReferenciaC;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualPA_Table;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualServidor;
import com.consultoraestrategia.ss_portalalumno.entities.ReunionVirtualServidor_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje;
import com.consultoraestrategia.ss_portalalumno.entities.SesionAprendizaje_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Valor;
import com.consultoraestrategia.ss_portalalumno.entities.Valor_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBInstrumento;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBRecursos;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancel;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancelImpl;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;
import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancelImpl;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.consultoraestrategia.ss_portalalumno.util.JSONFirebase;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsFirebase;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeHelper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TabSesionesRepositorioImpl implements TabSesionesRepositorio {
    public static final String TAG = TabSesionesRepositorioImpl.class.getSimpleName();

    @Override
    public FirebaseCancel updateFirebaseInstrumento(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple) {
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
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoIdFinal =  sessionUser!=null?sessionUser.getPersonaId():0;

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getInstrumentosAlumno(silaboEventoId, sesionAprendizajeId, alumnoIdFinal));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                List<InstrumentoEvaluacion> instrumentoEvaluacionList = new ArrayList<>();
                List<Variable> variableList = new ArrayList<>();
                List<Valor> valorList = new ArrayList<>();

                List<InstrumentoEvaluacionObservado> instrumentoEvaluacionObsList = new ArrayList<>();
                List<VariableObservado> variableObservadoList = new ArrayList<>();

                if(response==null){

                }else {

                    if(response.has("preguntas")){
                        JsonElement jsonElement = response.get("preguntas");
                        for (JSONFirebase instrumentoSnapshot : JSONFirebase.d(jsonElement).getChildren()){
                            //FBInstrumento fbInstrumento = instrumentoSnapshot.getValue(FBInstrumento.class);
                            //if(fbInstrumento==null) continue;
                            InstrumentoEvaluacion instrumentoEvaluacion = new InstrumentoEvaluacion();
                            instrumentoEvaluacion.setInstrumentoEvalId(UtilsFirebase.convert(instrumentoSnapshot.child("InstrumentoEvalId").getValue(), 0));
                            instrumentoEvaluacion.setCantidadPreguntas(UtilsFirebase.convert(instrumentoSnapshot.child("CantidadPreguntas").getValue(), 0));
                            instrumentoEvaluacion.setIcono(UtilsFirebase.convert(instrumentoSnapshot.child("Icono").getValue(), ""));
                            instrumentoEvaluacion.setImagen(UtilsFirebase.convert(instrumentoSnapshot.child("Imagen").getValue(), ""));
                            instrumentoEvaluacion.setNombre(UtilsFirebase.convert(instrumentoSnapshot.child("Nombre").getValue(), ""));
                            instrumentoEvaluacion.setSesionId(UtilsFirebase.convert(instrumentoSnapshot.child("SesionAprendizajeId").getValue(), 0));
                            instrumentoEvaluacion.setRubroEvaluacionId(UtilsFirebase.convert(instrumentoSnapshot.child("RubroEvaluacionId").getValue(), ""));
                            instrumentoEvaluacion.setTipoNotaId(UtilsFirebase.convert(instrumentoSnapshot.child("TipoNotaId").getValue(), ""));
                            instrumentoEvaluacionList.add(instrumentoEvaluacion);
                            if(instrumentoSnapshot.child("Variables").exists()){
                                for (JSONFirebase variablesSnapshot : instrumentoSnapshot.child("Variables").getChildren()){
                                    FBInstrumento.FBVariables fbVariables = variablesSnapshot.getValue(FBInstrumento.FBVariables.class);

                                    Variable variable = new Variable();
                                    variable.setVariableId(fbVariables.getVariableId());
                                    variable.setBancoPreguntaId(fbVariables.getBancoPreguntaId());
                                    variable.setDimensionId(fbVariables.getDimensionId());
                                    variable.setDisenioPreguntaId(fbVariables.getDisenioPreguntaId());
                                    variable.setEtiqueta(fbVariables.getEtiqueta());
                                    variable.setIconoVariable(fbVariables.getIconoVariable());
                                    variable.setInputRespuesta(fbVariables.getInputRespuesta());
                                    variable.setInstrumentoEvalId(fbVariables.getInstrumentoEvalId());
                                    variable.setLongitudPaso(fbVariables.getLongitudPaso());
                                    variable.setMedidaId(fbVariables.getMedidaId());
                                    variable.setNivelPreguntaId(fbVariables.getNivelPreguntaId());
                                    variable.setNombre(fbVariables.getNombre());
                                    //variable.setOrden(fbVariables.getOrden());
                                    variable.setParentId(fbVariables.getParentId());
                                    variable.setPath(fbVariables.getPath());
                                    variable.setPeso(fbVariables.getPeso());
                                    variable.setPuntaje(fbVariables.getPuntaje());
                                    variable.setTipoInputRespuestaId(fbVariables.getTipoInputRespuestaId());
                                    variable.setTipoPreguntaId(fbVariables.getTipoPreguntaId());
                                    variable.setTipoRespuestaId(fbVariables.getTipoRespuestaId());
                                    variable.setTipoVariableId(fbVariables.getTipoVariableId());
                                    variable.setValorMaximo(fbVariables.getValorMaximo());
                                    variable.setTipoCompetenciaId(UtilsFirebase.convert(variablesSnapshot.child("TipoCompetenciaId").getValue(), 0));
                                    variable.setTipoDecempenioId(UtilsFirebase.convert(variablesSnapshot.child("TipoDecempenioId").getValue(), 0));
                                    variable.setTituloRubroDetalle(UtilsFirebase.convert(variablesSnapshot.child("TituloRubroDetalle").getValue(), ""));
                                    variable.setDesempenioIcd(UtilsFirebase.convert(variablesSnapshot.child("DesempenioIcd").getValue(), 0));
                                    variable.setCapacidadId(UtilsFirebase.convert(variablesSnapshot.child("CapacidadId").getValue(), 0));
                                    variable.setCampoTematicoId(UtilsFirebase.convert(variablesSnapshot.child("CampoTematicoId").getValue(), 0));

                                    variable.setValorMinimo(fbVariables.getValorMinimo());
                                    variableList.add(variable);
                                    if(fbVariables.getValores()!=null)
                                        for (Map.Entry<String, FBInstrumento.FBValores> mapFbValores: fbVariables.getValores().entrySet()){
                                            FBInstrumento.FBValores fbValores = mapFbValores.getValue();
                                            Valor valor = new Valor();
                                            valor.setValorId(fbValores.getValorId());
                                            valor.setDescripcion(fbValores.getDescripcion());
                                            valor.setEtiqueta(fbValores.getEtiqueta());
                                            valor.setInputRespuesta(fbValores.getInputRespuesta());
                                            valor.setPath(fbValores.getPath());
                                            valor.setPuntaje(fbValores.getPuntaje());
                                            valor.setInputRespuesta(fbValores.getInputRespuesta());
                                            valor.setValor(fbValores.getValor());
                                            valor.setVariableId(fbValores.getVariableId());
                                            valorList.add(valor);
                                        }
                                }
                            }




                        }
                    }

                    if(response.has("respuestas")){
                        JsonElement jsonElement = response.get("respuestas");

                        List<VariableObservado> variableObservadosRemove = SQLite.select()
                                .from(VariableObservado.class)
                                .where(VariableObservado_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                .and(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED, BaseEntity.FLAG_UPDATED))
                                .queryList();

                        for (JSONFirebase instrumentoObservadoSnapshot : JSONFirebase.d(jsonElement).getChildren()){
                            FBInstrumento fbInstrumentoObservado = instrumentoObservadoSnapshot.getValue(FBInstrumento.class);
                            if(fbInstrumentoObservado==null) continue;
                            InstrumentoEvaluacionObservado instrumentoEvaluacionObservado = new InstrumentoEvaluacionObservado();
                            instrumentoEvaluacionObservado.setKey(fbInstrumentoObservado.getInstrumentoObservadoId());
                            instrumentoEvaluacionObservado.setInstrumentoObservadoId(fbInstrumentoObservado.getInstrumentoObservadoId());
                            instrumentoEvaluacionObservado.setInstrumentoEvalId(fbInstrumentoObservado.getInstrumentoEvalId());
                            instrumentoEvaluacionObservado.setPersonaId(alumnoIdFinal);
                            instrumentoEvaluacionObsList.add(instrumentoEvaluacionObservado);
                            Log.d(TAG, fbInstrumentoObservado.toString());

                            if(fbInstrumentoObservado.getVariables()!=null)
                                for (Map.Entry<String, FBInstrumento.FBVariables> mapFbVariable: fbInstrumentoObservado.getVariables().entrySet()){
                                    FBInstrumento.FBVariables fbVariablesObserda = mapFbVariable.getValue();
                                    VariableObservado variableObservadoRemove = null;
                                    for (VariableObservado item: variableObservadosRemove){
                                        if(item.getInstrumentoObservadoId().equals(fbInstrumentoObservado.getInstrumentoObservadoId())
                                                &&item.getVariableId()==fbVariablesObserda.getVariableId()){
                                            variableObservadoRemove = item;
                                            break;
                                        }
                                    }
                                    if(variableObservadoRemove!=null){
                                        variableObservadoList.add(variableObservadoRemove);
                                    }else if(fbVariablesObserda.getRespondida()==1){
                                        VariableObservado variableObservado = new VariableObservado();
                                        variableObservado.setKey(IdGenerator.generateId());
                                        variableObservado.setVariableObservadaId(variableObservado.getKey());
                                        variableObservado.setInstrumentoObservadoId(fbInstrumentoObservado.getInstrumentoObservadoId());
                                        variableObservado.setVariableId(fbVariablesObserda.getVariableId());
                                        variableObservado.setPuntajeObtenido(fbVariablesObserda.getPuntajeObtenido());
                                        variableObservado.setValorId(fbVariablesObserda.getValorId());
                                        variableObservado.setRespuestaActual(fbVariablesObserda.getRespuestaActual());
                                        variableObservado.setSesionAprendizajeId(sesionAprendizajeId);
                                        variableObservado.setSilaboEventoId(silaboEventoId);
                                        variableObservado.setInstrumentoEvalId(fbInstrumentoObservado.getInstrumentoEvalId());
                                        variableObservadoList.add(variableObservado);
                                    }

                                }
                        }
                    }

                }

                DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        List<InstrumentoEvaluacion> instrumentoEvaluacions = SQLite.select()
                                .from(InstrumentoEvaluacion.class)
                                .where(InstrumentoEvaluacion_Table.SesionId.eq(sesionAprendizajeId))
                                .queryList(databaseWrapper);

                        List<Integer> instrumentoEvaluacionIdList = new ArrayList<>();
                        for (InstrumentoEvaluacion instrumentoEvaluacion : instrumentoEvaluacions)instrumentoEvaluacionIdList.add(instrumentoEvaluacion.getInstrumentoEvalId());
                        List<Variable> variables = SQLite.select()
                                .from(Variable.class)
                                .where(Variable_Table.InstrumentoEvalId.in(instrumentoEvaluacionIdList))
                                .queryList(databaseWrapper);
                        List<Integer> variableIdList = new ArrayList<>();
                        for (Variable variable : variables)variableIdList.add(variable.getVariableId());

                        TransaccionUtils.deleteTable(InstrumentoEvaluacion.class, InstrumentoEvaluacion_Table.InstrumentoEvalId.in(instrumentoEvaluacionIdList));
                        TransaccionUtils.deleteTable(Variable.class, Variable_Table.VariableId.in(variableIdList));
                        TransaccionUtils.deleteTable(Valor.class, Valor_Table.ValorId.in(variableIdList));

                        List<InstrumentoEvaluacionObservado> instrumentoEvaluacionObservados = SQLite.select()
                                .from(InstrumentoEvaluacionObservado.class)
                                .where(InstrumentoEvaluacionObservado_Table.InstrumentoEvalId.in(instrumentoEvaluacionIdList))
                                .queryList(databaseWrapper);

                        List<String> instrumentoEvaluacionObservadaIdList = new ArrayList<>();
                        for (InstrumentoEvaluacionObservado instrumentoEvaluacionObs : instrumentoEvaluacionObservados)instrumentoEvaluacionObservadaIdList.add(instrumentoEvaluacionObs.getInstrumentoObservadoId());

                        TransaccionUtils.deleteTable(InstrumentoEvaluacionObservado.class, InstrumentoEvaluacionObservado_Table.InstrumentoObservadoId.in(instrumentoEvaluacionObservadaIdList));
                        TransaccionUtils.deleteTable(VariableObservado.class, VariableObservado_Table.InstrumentoObservadoId.in(instrumentoEvaluacionObservadaIdList));


                        TransaccionUtils.fastStoreListInsert(InstrumentoEvaluacion.class, instrumentoEvaluacionList, databaseWrapper, false);
                        TransaccionUtils.fastStoreListInsert(Variable.class, variableList, databaseWrapper, false);
                        TransaccionUtils.fastStoreListInsert(Valor.class, valorList, databaseWrapper, false);
                        TransaccionUtils.fastStoreListInsert(InstrumentoEvaluacionObservado.class, instrumentoEvaluacionObsList, databaseWrapper, false);
                        TransaccionUtils.fastStoreListInsert(VariableObservado.class, variableObservadoList, databaseWrapper, false);
                    }
                }).success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        callbackSimple.onLoad(true);
                    }
                }).error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        error.printStackTrace();
                        callbackSimple.onLoad(false);
                    }
                }).build();

                transaction.execute();
            }

            @Override
            public void onFailure(Throwable t) {
                callbackSimple.onLoad(false);
            }
        });


        return null;
    }

    @Override
    public void updateFirebasePregunta(int sesionAprendizajeId, int cargaCursoId, PreguntaCallback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
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

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getPreguntasEvaluacionAlumno(silaboEventoId, unidadAprendizajeid, sesionAprendizajeId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {

                if(response==null){

                }else {
                    for (JSONFirebase instrumentoSnapshot : JSONFirebase.d(response).getChildren()){
                        for (JSONFirebase alumnoSnapshot : instrumentoSnapshot.getChildren()){
                            PreguntaEvaluacionPA preguntaEvaluacionPA = new PreguntaEvaluacionPA();
                            preguntaEvaluacionPA.setPreguntaId(UtilsFirebase.convert(alumnoSnapshot.child("PreguntaId").getValue(), ""));
                            preguntaEvaluacionPA.setAlumnoId(UtilsFirebase.convert(alumnoSnapshot.child("AlumnoId").getValue(), 0));
                            preguntaEvaluacionPA.setVariableId(UtilsFirebase.convert(alumnoSnapshot.child("VariableId").getValue(), ""));
                            if(personaId==preguntaEvaluacionPA.getAlumnoId()){
                                preguntaEvaluacionPA.save();
                            }
                        }
                    }
                }

                SQLite.delete()
                        .from(PreguntaPA.class)
                        .where(PreguntaPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                        .execute();


                ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
                apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
                RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getPreguntasAlumno(silaboEventoId, unidadAprendizajeid, sesionAprendizajeId));
                retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
                        if(response==null){

                        }else {
                            for (JSONFirebase alumnoSnapshot : JSONFirebase.d(response).getChildren()){
                                PreguntaEvaluacionPA preguntaEvaluacionPA = new PreguntaEvaluacionPA();
                                if(alumnoSnapshot.child("PreguntaId").exists()){
                                    preguntaEvaluacionPA.setPreguntaId(UtilsFirebase.convert(alumnoSnapshot.child("PreguntaId").getValue(), ""));
                                    preguntaEvaluacionPA.setAlumnoId(UtilsFirebase.convert(alumnoSnapshot.child("AlumnoId").getValue(), 0));
                                    preguntaEvaluacionPA.setVariableId(UtilsFirebase.convert(alumnoSnapshot.child("VariableId").getValue(), ""));
                                    if(personaId==preguntaEvaluacionPA.getAlumnoId()){
                                        preguntaEvaluacionPA.save();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

                FirebaseCancel firebaseCancel =  new FirebaseCancelImpl(mDatabase.child("/AV_Preguntas/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId + "/Preguntas"),
                        new ChildEventListener() {

                            private PreguntaPA convert(DataSnapshot instrumentoSnapshot, int sesionAprendizajeId) {
                                PreguntaPA preguntaPA = new PreguntaPA();
                                preguntaPA.setPreguntaPAId(instrumentoSnapshot.getKey());
                                preguntaPA.setBloqueado(UtilsFirebase.convert(instrumentoSnapshot.child("Bloqueado").getValue(), 0));
                                preguntaPA.setPregunta(UtilsFirebase.convert(instrumentoSnapshot.child("Pregunta").getValue(), ""));
                                preguntaPA.setDesempenioIcdId(UtilsFirebase.convert(instrumentoSnapshot.child("DesempenioIcdId").getValue(), 0));
                                preguntaPA.setPregunta(UtilsFirebase.convert(instrumentoSnapshot.child("Pregunta").getValue(), ""));
                                preguntaPA.setRubroEvalProcesoId(UtilsFirebase.convert(instrumentoSnapshot.child("RubroEvalProcesoId").getValue(), ""));
                                preguntaPA.setVersion(UtilsFirebase.convert(instrumentoSnapshot.child("Version").getValue(), 0));
                                preguntaPA.setCountEvaluado(UtilsFirebase.convert(instrumentoSnapshot.child("countEvaluado").getValue(), 0));
                                preguntaPA.setCountSinEvaluar(UtilsFirebase.convert(instrumentoSnapshot.child("countSinEvaluar").getValue(), 0));
                                preguntaPA.setUsuarioId(UtilsFirebase.convert(instrumentoSnapshot.child("UsuarioId").getValue(), 0));
                                preguntaPA.setTipoNotaId(UtilsFirebase.convert(instrumentoSnapshot.child("NivelLogroId").getValue(), ""));
                                preguntaPA.setTipoId(UtilsFirebase.convert(instrumentoSnapshot.child("TipoId").getValue(), ""));
                                preguntaPA.setSesionAprendizajeId(sesionAprendizajeId);
                                return preguntaPA;
                            }

                            @Override
                            public void onChildAdded(@NonNull DataSnapshot instrumentoSnapshot, @Nullable String s) {
                                PreguntaPA preguntaPA = convert(instrumentoSnapshot, sesionAprendizajeId);
                                preguntaPA.save();
                                callback.addPregunta(preguntaPA.getPreguntaPAId());
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot instrumentoSnapshot, @Nullable String s) {
                                PreguntaPA preguntaPA = convert(instrumentoSnapshot, sesionAprendizajeId);
                                preguntaPA.save();
                                int alumnoId = UtilsFirebase.convert(instrumentoSnapshot.child("AlumnoIdModicacion").getValue(),0);
                                //long time = UtilsFirebase.convert(instrumentoSnapshot.child("VariableId").getValue(),0L);
                                //String nivelLogroId = UtilsFirebase.convert(instrumentoSnapshot.child("NivelLogroId").getValue(),"");
                                String variableId = UtilsFirebase.convert(instrumentoSnapshot.child("VariableId").getValue(),"");

                                if(personaId==alumnoId){
                                    PreguntaEvaluacionPA preguntaEvaluacionPA = new PreguntaEvaluacionPA();
                                    preguntaEvaluacionPA.setAlumnoId(alumnoId);
                                    preguntaEvaluacionPA.setVariableId(variableId);
                                    preguntaEvaluacionPA.setPreguntaId(preguntaPA.getPreguntaPAId());
                                    preguntaEvaluacionPA.save();
                                    callback.updatePreguntaAlumno(preguntaPA.getPreguntaPAId(), preguntaEvaluacionPA.getAlumnoId());
                                }
                                callback.updatePregunta(preguntaPA.getPreguntaPAId());
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot instrumentoSnapshot) {
                                SQLite.delete()
                                        .from(PreguntaPA.class)
                                        .where(PreguntaPA_Table.preguntaPAId.eq(instrumentoSnapshot.getKey()))
                                        .execute();
                                callback.removePregunta(instrumentoSnapshot.getKey());
                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                callback.onPreLoad(firebaseCancel);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });



    }

    @Override
    public FirebaseCancel updateFirebaseColaborativa(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
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

        SQLite.delete()
                .from(ColborativaPA.class)
                .where(ColborativaPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .execute();

        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<JsonObject> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getActividadColaborativaAlumno(silaboEventoId, unidadAprendizajeid, sesionAprendizajeId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                if(response == null){

                }else {
                    for (Map.Entry<String,JSONFirebase> entry: JSONFirebase.d(response).getChildren2().entrySet()){
                        ColborativaPA colborativaPA = new ColborativaPA();
                        colborativaPA.setKey(entry.getKey());
                        JSONFirebase colaborativaSnapshot = entry.getValue();
                        colborativaPA.setDescripcion(UtilsFirebase.convert(colaborativaSnapshot.child("descripcion").getValue(), ""));
                        colborativaPA.setNombre(UtilsFirebase.convert(colaborativaSnapshot.child("nombre").getValue(), ""));
                        colborativaPA.setTipo(UtilsFirebase.convert(colaborativaSnapshot.child("tipo").getValue(), ""));
                        colborativaPA.save();
                    }
                }
                callbackSimple.onLoad(true);
            }

            @Override
            public void onFailure(Throwable t) {
                callbackSimple.onLoad(false);
            }
        });

        FirebaseCancel firebaseCancel = new FirebaseCancelImpl(mDatabase.child("/AV_ActividadColaborativa/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId),
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot colaborativaSnapshot : dataSnapshot.getChildren()){
                            ColborativaPA colborativaPA = new ColborativaPA();
                            colborativaPA.setKey(colaborativaSnapshot.getKey());
                            colborativaPA.setDescripcion(UtilsFirebase.convert(colaborativaSnapshot.child("descripcion").getValue(), ""));
                            colborativaPA.setNombre(UtilsFirebase.convert(colaborativaSnapshot.child("nombre").getValue(), ""));
                            colborativaPA.setTipo(UtilsFirebase.convert(colaborativaSnapshot.child("tipo").getValue(), ""));
                            colborativaPA.save();
                        }
                        callbackSimple.onLoad(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbackSimple.onLoad(false);
                    }
                });

        return () -> {
            firebaseCancel.cancel();
            retrofitCancel.cancel();
        };


    }

    @Override
    public FirebaseCancel updateFirebaseReunionVirtual(int sesionAprendizajeId, int cargaCursoId, CallbackSimple callbackSimple) {
       /* Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;
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

        SQLite.delete()
                .from(ReunionVirtualPA.class)
                .where(ReunionVirtualPA_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                .execute();

        return new FirebaseCancelImpl(mDatabase.child("/AV_ReunionVirtual/silid_" + silaboEventoId + "/unid_" + unidadAprendizajeid + "/sesid_" + sesionAprendizajeId),
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot colaborativaSnapshot : dataSnapshot.getChildren()){
                            ReunionVirtualPA reunionVirtualPA = new ReunionVirtualPA();
                            reunionVirtualPA.setKey(colaborativaSnapshot.getKey());
                            reunionVirtualPA.setCorreo(UtilsFirebase.convert(colaborativaSnapshot.child("correo").getValue(), ""));
                            reunionVirtualPA.setDescripcion(UtilsFirebase.convert(colaborativaSnapshot.child("descripcion").getValue(), ""));
                            reunionVirtualPA.setFechaInicio(UtilsFirebase.convert(colaborativaSnapshot.child("fechaInicio").getValue(), ""));
                            reunionVirtualPA.setHoraInicio(UtilsFirebase.convert(colaborativaSnapshot.child("horaInicio").getValue(), ""));
                            reunionVirtualPA.setNombreReunion(UtilsFirebase.convert(colaborativaSnapshot.child("nombreReunion").getValue(), ""));
                            reunionVirtualPA.setSalaReunionId(UtilsFirebase.convert(colaborativaSnapshot.child("salaReunionId").getValue(), ""));
                            reunionVirtualPA.setTiempoDuracion(UtilsFirebase.convert(colaborativaSnapshot.child("tiempoDuracion").getValue(), 0));
                            reunionVirtualPA.setUrl(UtilsFirebase.convert(colaborativaSnapshot.child("url").getValue(), ""));
                            reunionVirtualPA.setUrl2(UtilsFirebase.convert(colaborativaSnapshot.child("url2").getValue(), ""));
                            reunionVirtualPA.setUsuario(UtilsFirebase.convert(colaborativaSnapshot.child("usuario").getValue(), ""));
                            reunionVirtualPA.setSesionAprendizajeId(sesionAprendizajeId);
                            reunionVirtualPA.save();
                        }
                        callbackSimple.onLoad(true);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbackSimple.onLoad(false);
                    }
                });*/
        callbackSimple.onLoad(true);
        return null;
    }

    @Override
    public RetrofitCancel getGrabacionesSalaVirtual(int sesionAprendizajeId, CallbackSimple callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<List<GrabacionSalaVirtual>> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getGrabacionesSalaVirtual(sesionAprendizajeId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<List<GrabacionSalaVirtual>>() {
            @Override
            public void onResponse(List<GrabacionSalaVirtual> list) {

                DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {

                        SQLite.delete()
                                .from(GrabacionSalaVirtual.class)
                                .where(GrabacionSalaVirtual_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                .execute(databaseWrapper);

                        TransaccionUtils.fastStoreListSave(GrabacionSalaVirtual.class, list, databaseWrapper, false);
                    }
                }).success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        callback.onLoad(true);
                    }
                }).error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        error.printStackTrace();
                        callback.onLoad(false);
                    }
                }).build();

                transaction.execute();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
                Log.d(TAG,"onFailure");
            }
        });
        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getReunionVirtualAlumno(int sesionAprendizajeId, int entidadId, int georeferenciaId, CallbackSimple callback) {
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<List<ReunionVirtualServidor>> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getReunionVirtualAlumno(sesionAprendizajeId, entidadId, georeferenciaId));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<List<ReunionVirtualServidor>>() {
            @Override
            public void onResponse(List<ReunionVirtualServidor> list) {

                DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        Log.d(TAG, "getReunionVirtualAlumno: "+list.size());
                        SQLite.delete()
                                .from(ReunionVirtualServidor.class)
                                .where(ReunionVirtualServidor_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                .and(ReunionVirtualServidor_Table.entidadId.eq(entidadId))
                                .and(ReunionVirtualServidor_Table.georeferenciaId.eq(georeferenciaId))
                                .execute(databaseWrapper);


                        TransaccionUtils.fastStoreListSave(ReunionVirtualServidor.class, list, databaseWrapper, false);
                    }
                }).success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        callback.onLoad(true);
                    }
                }).error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        error.printStackTrace();
                        Log.d(TAG,"getReunionVirtualAlumno onError");
                        callback.onLoad(false);
                    }
                }).build();

                transaction.execute();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
                Log.d(TAG,"onFailure");
            }
        });
        return retrofitCancel;
    }

    @Override
    public RetrofitCancel getInstrumentoEncuestaEval(int sesionAprendizajeId, int personaId, CallbackSimple callback) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        ApiRetrofit apiRetrofit = ApiRetrofit.getInstance();
        apiRetrofit.changeSetTime(10,15,15, TimeUnit.SECONDS);
        RetrofitCancel<List<InstrumentoEncuestaEval>> retrofitCancel = new RetrofitCancelImpl<>(apiRetrofit.getInstrumentoEncuestaEval(sesionAprendizajeId, personaId, sessionUser.getUserId()));
        retrofitCancel.enqueue(new RetrofitCancel.Callback<List<InstrumentoEncuestaEval>>() {
            @Override
            public void onResponse(List<InstrumentoEncuestaEval> list) {

                DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                Transaction transaction = database.beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        SQLite.delete()
                                .from(InstrumentoEncuestaEval.class)
                                .where(InstrumentoEncuestaEval_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                .and(InstrumentoEncuestaEval_Table.personaId.eq(personaId))
                                .execute(databaseWrapper);


                        TransaccionUtils.fastStoreListSave(InstrumentoEncuestaEval.class, list, databaseWrapper, false);
                    }
                }).success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NonNull Transaction transaction) {
                        callback.onLoad(true);
                    }
                }).error(new Transaction.Error() {
                    @Override
                    public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                        error.printStackTrace();
                        callback.onLoad(false);
                    }
                }).build();

                transaction.execute();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onLoad(false);
                Log.d(TAG,"onFailure");
            }
        });
        return retrofitCancel;
    }

    private void getRecursosFirebase(int actividadAprendizajeId, FBRecursos fbRecursos, List<RecursoDidacticoEventoC> recursoDidacticoEventoList, List<RecursoReferenciaC> recursoReferenciaCList, List<Archivo> archivoList, List<RecursoArchivo> recursoArchivoList){
        RecursoDidacticoEventoC recursoDidacticoEventoC = new RecursoDidacticoEventoC();
        recursoDidacticoEventoC.setRecursoDidacticoId(fbRecursos.getRecursoDidacticoId());
        recursoDidacticoEventoC.setKey(fbRecursos.getRecursoDidacticoId());
        recursoDidacticoEventoC.setTipoId(fbRecursos.getTipoId());
        recursoDidacticoEventoC.setUrl(fbRecursos.getDescripcion());
        recursoDidacticoEventoC.setTitulo(fbRecursos.getTitulo());
        recursoDidacticoEventoC.setDescripcion(fbRecursos.getDescripcion());
        recursoDidacticoEventoList.add(recursoDidacticoEventoC);

        RecursoReferenciaC recursoReferenciaC = new RecursoReferenciaC();
        recursoReferenciaC.setActividadAprendizajeId(actividadAprendizajeId);
        recursoReferenciaC.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
        recursoReferenciaCList.add(recursoReferenciaC);

        boolean isYoutube = false;
        if(recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO){
            isYoutube = !TextUtils.isEmpty(YouTubeHelper.extractVideoIdFromUrl(recursoDidacticoEventoC.getUrl()));
        }

        if (recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_AUDIO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_DIAPOSITIVA ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_DOCUMENTO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_HOJA_CALCULO ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_IMAGEN ||
                recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_PDF ||
                (recursoDidacticoEventoC.getTipoId() == RecursoDidacticoEventoC.TIPO_VIDEO
                        && !isYoutube)
        ) {

            Archivo archivo = SQLite.select()
                    .from(Archivo.class)
                    .where(Archivo_Table.archivoId.eq(fbRecursos.getRecursoDidacticoId()))
                    .querySingle();
            if(archivo==null)archivo = new Archivo();
            archivo.setKey(fbRecursos.getRecursoDidacticoId());
            archivo.setArchivoId(fbRecursos.getRecursoDidacticoId());
            archivo.setNombre("");
            archivo.setPath(fbRecursos.getDescripcion());
            archivoList.add(archivo);

            RecursoArchivo recursoArchivo = new RecursoArchivo();
            recursoArchivo.setArchivoId(archivo.getKey());
            recursoArchivo.setRecursoDidacticoId(recursoDidacticoEventoC.getRecursoDidacticoId());
            recursoArchivoList.add(recursoArchivo);

        }
    }
}
