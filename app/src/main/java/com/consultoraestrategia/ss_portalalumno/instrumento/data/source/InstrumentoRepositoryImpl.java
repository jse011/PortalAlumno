package com.consultoraestrategia.ss_portalalumno.instrumento.data.source;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.consultoraestrategia.ss_portalalumno.entities.BaseEntity;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacionObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.InstrumentoEvaluacion_Table;
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
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrumentoRepositoryImpl implements InstrumentoRepository {

    private String TAG = InstrumentoRepositoryImpl.class.getSimpleName();

    @Override
    public void updateFirebaseInstrumento(int sesionAprendizajeId, int cargaCursoId, int alumnoId, CallbackSimple callbackSimple) {
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


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.child("/AV_Instrumento/Pregunta/silid_"+silaboEventoId+"/sesid_" + sesionAprendizajeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<InstrumentoEvaluacion> instrumentoEvaluacionList = new ArrayList<>();
                        List<Variable> variableList = new ArrayList<>();
                        List<Valor> valorList = new ArrayList<>();
                        for (DataSnapshot instrumentoSnapshot : dataSnapshot.getChildren()){
                            FBInstrumento fbInstrumento = instrumentoSnapshot.getValue(FBInstrumento.class);
                            if(fbInstrumento==null) continue;
                            InstrumentoEvaluacion instrumentoEvaluacion = new InstrumentoEvaluacion();
                            instrumentoEvaluacion.setInstrumentoEvalId(fbInstrumento.getInstrumentoEvalId());
                            instrumentoEvaluacion.setCantidadPreguntas(fbInstrumento.getCantidadPreguntas());
                            instrumentoEvaluacion.setIcono(fbInstrumento.getIcono());
                            instrumentoEvaluacion.setImagen(fbInstrumento.getImagen());
                            instrumentoEvaluacion.setNombre(fbInstrumento.getNombre());
                            instrumentoEvaluacion.setSesionId(fbInstrumento.getSesionAprendizajeId());
                            instrumentoEvaluacion.setSilaboId(fbInstrumento.getSilaboEventoId());
                            instrumentoEvaluacionList.add(instrumentoEvaluacion);
                            if(fbInstrumento.getVariables()!=null)
                                for (Map.Entry<String, FBInstrumento.FBVariables> mapFbVariable: fbInstrumento.getVariables().entrySet()) {
                                    FBInstrumento.FBVariables fbVariables = mapFbVariable.getValue();
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

                        SessionUser sessionUser = SessionUser.getCurrentUser();
                        int alumnoIdFinal =  sessionUser!=null?sessionUser.getPersonaId():0;
                        Log.d(TAG, "alumnoId: "+alumnoIdFinal);

                        mDatabase.child("/AV_Instrumento/Respuesta/silid_"+silaboEventoId+"/sesid_" + sesionAprendizajeId+"/aluid_"+alumnoIdFinal+"/Instrumentos")
                             .addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     List<InstrumentoEvaluacionObservado> instrumentoEvaluacionObsList = new ArrayList<>();
                                     List<VariableObservado> variableObservadoList = new ArrayList<>();
                                     List<VariableObservado> variableObservadosRemove = SQLite.select()
                                             .from(VariableObservado.class)
                                             .where(VariableObservado_Table.sesionAprendizajeId.eq(sesionAprendizajeId))
                                             .and(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED, BaseEntity.FLAG_UPDATED))
                                             .queryList();

                                     for (DataSnapshot instrumentoObservadoSnapshot : dataSnapshot.getChildren()){
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
                                 public void onCancelled(@NonNull DatabaseError databaseError) {
                                     callbackSimple.onLoad(false);
                                 }
                             });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callbackSimple.onLoad(false);
                    }
                });
    }

    @Override
    public void saveFirebaseInstrumento(int cargaCursoId, int sesionAprensizajeId, VariableUi variableUi, CallbackSimple callbackSimple) {
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

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        Map<String, Object> variableObservadaMap = new HashMap<>();
        variableObservadaMap.put("Respondida", 1);
        variableObservadaMap.put("personaId", alumnoIdFinal);
        variableObservadaMap.put("puntaje", variableUi.getPuntaje());
        variableObservadaMap.put("time", new Date().getTime());
        variableObservadaMap.put("variableId", variableUi.getVariableId());


        int puntajeObtenido = 0;
        String respuestaActual = null;
        int valorId = 0;
        switch (variableUi.getTipoRespuestaId()){
            case 10://Texto
                boolean correcto = false;
                String inputRespuesta = !TextUtils.isEmpty(variableUi.getInputRespuesta())?variableUi.getInputRespuesta():"";
                String[] respuestas = inputRespuesta.split("&?,?&");
                for (String respuesta : respuestas){
                    if(!TextUtils.isEmpty(respuesta)&&respuesta.equals(variableUi.getRespuestaActual())){
                        correcto = true;
                        break;
                    }
                }

                respuestaActual = variableUi.getRespuestaActual();
                puntajeObtenido = correcto?variableUi.getPuntaje():0;
                variableObservadaMap.put("respuestaActual", respuestaActual);
                variableObservadaMap.put("puntajeObtenido", puntajeObtenido);

                break;
            case 11://Excluyente
                ValorUi respuesta = null;
                for (ValorUi valorUi: variableUi.getValores()){
                    if(valorUi.isSelected())respuesta = valorUi;
                }
                valorId = respuesta!=null?respuesta.getValorId():0;
                puntajeObtenido = respuesta!=null?respuesta.getPuntaje():0;
                variableObservadaMap.put("valorId", valorId);
                variableObservadaMap.put("puntajeObtenido", puntajeObtenido);
                break;
            case 12://Check
                int puntaje = 0;
                for (ValorUi valorUi: variableUi.getValores()){
                    if(valorUi.isSelected())puntaje += valorUi.getPuntaje();
                }
                puntajeObtenido = puntaje;
                variableObservadaMap.put("puntajeObtenido", puntajeObtenido);
                break;
        }

        Log.d(TAG,"Respuesta");
        int finalPuntajeObtenido = puntajeObtenido;
        String finalRespuestaActual = respuestaActual;
        int finalValorId = valorId;


        Log.d(TAG,"Respuesta onComplete");
        VariableObservado variableObservada = SQLite.select()
                .from(VariableObservado.class)
                .where(VariableObservado_Table.InstrumentoObservadoId.eq(variableUi.getInstrumentoObservadoId()))
                .and(VariableObservado_Table.VariableId.eq(variableUi.getVariableId()))
                .querySingle();

        if(variableObservada==null){
            variableObservada = new VariableObservado();
            variableObservada.setKey(IdGenerator.generateId());
            variableObservada.setInstrumentoObservadoId(variableUi.getInstrumentoObservadoId());
            variableObservada.setVariableObservadaId(variableObservada.getKey());
            variableObservada.setVariableId(variableUi.getVariableId());
            variableObservada.setPuntajeObtenido(finalPuntajeObtenido);
            variableObservada.setRespuestaActual(finalRespuestaActual);
            variableObservada.setValorId(finalValorId);
            variableObservada.setSesionAprendizajeId(sesionAprensizajeId);
            variableObservada.setSilaboEventoId(silaboEventoId);
            variableObservada.setInstrumentoEvalId(variableUi.getInstrumentoEvalId());
            variableObservada.setSyncFlag(BaseEntity.FLAG_ADDED);
        }else {
            variableObservada.setPuntajeObtenido(finalPuntajeObtenido);
            variableObservada.setRespuestaActual(finalRespuestaActual);
            variableObservada.setValorId(finalValorId);
            variableObservada.setSesionAprendizajeId(sesionAprensizajeId);
            variableObservada.setSilaboEventoId(silaboEventoId);
            variableObservada.setInstrumentoEvalId(variableUi.getInstrumentoEvalId());
            variableObservada.setSyncFlag(BaseEntity.FLAG_UPDATED);
        }
        variableObservada.save();

        mDatabase.child("/AV_Instrumento/Respuesta/silid_"+silaboEventoId+"/sesid_" + sesionAprensizajeId+"/aluid_"+alumnoIdFinal+"/Instrumentos/ins_"+variableUi.getInstrumentoEvalId()+"/Variables/varid_"+variableUi.getVariableId())
                .setValue(variableObservadaMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                Log.d(TAG,"Respuesta onComplete");
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                    callbackSimple.onLoad(false);
                } else {
                    variableUi.setEnviado(true);
                    VariableObservado variableObservada = SQLite.select()
                            .from(VariableObservado.class)
                            .where(VariableObservado_Table.InstrumentoObservadoId.eq(variableUi.getInstrumentoObservadoId()))
                            .and(VariableObservado_Table.VariableId.eq(variableUi.getVariableId()))
                            .querySingle();
                    if(variableObservada!=null){
                        variableObservada.setSyncFlag(BaseEntity.FLAG_EXPORTED);
                        variableObservada.save();
                    }

                    System.out.println("Data saved successfully.");
                    callbackSimple.onLoad(true);
                }
            }
        });
    }

    @Override
    public List<InstrumentoUi> getInstrumentos(int sesionAprendizajeId) {
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int alumnoId =  sessionUser!=null?sessionUser.getPersonaId():0;

        List<InstrumentoUi> instrumentoUiList =  new ArrayList<>();
        List<InstrumentoEvaluacion> instrumentoEvaluacionList = SQLite.select()
                .from(InstrumentoEvaluacion.class)
                .where(InstrumentoEvaluacion_Table.SesionId.eq(sesionAprendizajeId))
                .queryList();


        for (InstrumentoEvaluacion instrumentoEvaluacion : instrumentoEvaluacionList){
            InstrumentoUi instrumentoUi = new InstrumentoUi();
            instrumentoUi.setInstrumentoEvalId(instrumentoEvaluacion.getInstrumentoEvalId());

            instrumentoUi.setNombre(instrumentoEvaluacion.getNombre());
            InstrumentoEvaluacionObservado instrumentoEvaluacionObservado = SQLite.select()
                    .from(InstrumentoEvaluacionObservado.class)
                    .where(InstrumentoEvaluacionObservado_Table.InstrumentoEvalId.eq(instrumentoEvaluacion.getInstrumentoEvalId()))
                    .and(InstrumentoEvaluacionObservado_Table.PersonaId.eq(alumnoId))
                    .querySingle();






            int countVAO = 0;
            int puntajeObtenido = 0;
            List<VariableObservado> variableObservadoList = SQLite.select()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.InstrumentoObservadoId.eq(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getInstrumentoObservadoId():""))
                    .queryList();

            for(VariableObservado variableObservado: variableObservadoList){
                countVAO++;
                puntajeObtenido += variableObservado.getPuntajeObtenido();
            }
            int countVA = 0;
            int puntaje = 0;
             List<Variable> variableList = SQLite.select()
                    .from(Variable.class)
                    .where(Variable_Table.InstrumentoEvalId.eq(instrumentoEvaluacion.getInstrumentoEvalId()))
                    .queryList();

             for (Variable variable: variableList){
                 countVA++;
                 puntaje += variable.getPuntaje();
             }
            long countOVASinEviar = SQLite.selectCountOf()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.InstrumentoObservadoId.eq(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getInstrumentoObservadoId():""))
                    .and(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED,BaseEntity.FLAG_UPDATED))
                    .count();
            int porcentaje = -1;
             if(countVA<=countVAO){
                 porcentaje = (int)((double)(puntajeObtenido*100)/(double)puntaje);
             }

            instrumentoUi.setPorcentaje(porcentaje);
            instrumentoUi.setCantidadPregunta(countVA);
            instrumentoUi.setCantidadPreguntaResueltas(countVAO);
            instrumentoUi.setCatidadPreguntasSinEnviar((int)countOVASinEviar);
            instrumentoUiList.add(instrumentoUi);
        }

        return instrumentoUiList;
    }

    @Override
    public InstrumentoUi getInstrumento(int instrumentoId) {
        List<Variable> variableList = SQLite.select()
                .from(Variable.class)
                .where(Variable_Table.InstrumentoEvalId.eq(instrumentoId))
                .queryList();
        SessionUser sessionUser =  SessionUser.getCurrentUser();

        InstrumentoEvaluacionObservado instrumentoEvaluacionObservado = SQLite.select()
                .from(InstrumentoEvaluacionObservado.class)
                .where(InstrumentoEvaluacionObservado_Table.InstrumentoEvalId.eq(instrumentoId))
                .and(InstrumentoEvaluacionObservado_Table.PersonaId.eq(sessionUser!=null?sessionUser.getPersonaId():0))
                .querySingle();
        InstrumentoUi instrumentoUi = new InstrumentoUi();
        instrumentoUi.setInstrumentoEvalId(instrumentoId);
        List<VariableUi> variableUiList = new ArrayList<>();

        for (Variable variable: variableList){
            VariableObservado variableObservado = SQLite.select()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.VariableId.eq(variable.getVariableId()))
                    .and(VariableObservado_Table.InstrumentoObservadoId.eq(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getKey():""))
                    .querySingle();

            VariableUi variableUi = new VariableUi();
            variableUi.setVariableId(variable.getVariableId());
            variableUi.setInputRespuesta(variable.getInputRespuesta());
            variableUi.setNombre(variable.getNombre());
            variableUi.setTipoInputRespuestaId(variable.getTipoInputRespuestaId());
            //variableUi.setTipoRespuestaId(variable.getTipoRespuestaId()==11?12:variable.getTipoRespuestaId());
            variableUi.setTipoRespuestaId(variable.getTipoRespuestaId());
            variableUi.setPuntaje(variable.getPuntaje());
            variableUi.setPath(variable.getPath());
            if(variableObservado!=null){
                variableUi.setPuntajeObtenido(variableObservado.getPuntajeObtenido());
                variableUi.setVariableObservadaId(variableObservado.getVariableObservadaId());
            }
            variableUi.setInstrumentoEvalId(instrumentoId);
            variableUi.setInstrumentoObservadoId(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getKey():"");
            List<Valor> valorList = SQLite.select()
                    .from(Valor.class)
                    .where(Valor_Table.VariableId.eq(variable.getVariableId()))
                    .queryList();
            List<ValorUi> valorUiList = new ArrayList<>();
            for (Valor valor: valorList){
                ValorUi valorUi =  new ValorUi();
                valorUi.setValorId(valor.getValorId());
                valorUi.setValor(valor.getValor());
                valorUi.setDescripcion(valor.getDescripcion());
                valorUi.setEtiqueta(valor.getEtiqueta());
                valorUi.setIconoValor(valor.getIconoValor());
                valorUi.setEtiqueta(valor.getEtiqueta());
                valorUi.setInputRespuesta(valor.getInputRespuesta());
                valorUi.setPath(valor.getPath());
                valorUi.setPuntaje(valor.getPuntaje());
                valorUi.setTipoInputRespuestaId(valor.getTipoInputRespuestaId());
                valorUi.setVariableId(valor.getVariableId());
                valorUiList.add(valorUi);
            }

            variableUi.setValores(valorUiList);
            variableUiList.add(variableUi);
        }
        instrumentoUi.setVariables(variableUiList);

        return instrumentoUi;
    }
}
