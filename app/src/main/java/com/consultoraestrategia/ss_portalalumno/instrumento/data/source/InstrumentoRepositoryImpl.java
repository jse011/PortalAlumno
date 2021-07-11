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
import com.consultoraestrategia.ss_portalalumno.entities.ProgramasEducativo;
import com.consultoraestrategia.ss_portalalumno.entities.RelProgramaEducativoTipoNota;
import com.consultoraestrategia.ss_portalalumno.entities.RelProgramaEducativoTipoNota_Table;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.TipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Valor;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC;
import com.consultoraestrategia.ss_portalalumno.entities.ValorTipoNotaC_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Valor_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Variable_Table;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.entities.firebase.FBInstrumento;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.RubroDetalleUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.consultoraestrategia.ss_portalalumno.util.TransaccionUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.internal.Utils;

public class InstrumentoRepositoryImpl implements InstrumentoRepository {

    private String TAG = InstrumentoRepositoryImpl.class.getSimpleName();

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

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_PathImgPreguntasInstrumento"))
                .querySingle();

        String pathValores = webconfig!=null?webconfig.getContent():"";

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

            instrumentoUi.setTipoNotaId(instrumentoEvaluacion.getTipoNotaId());
            instrumentoUi.setRubroEvaluacionId(instrumentoEvaluacion.getRubroEvaluacionId());
            instrumentoUiList.add(instrumentoUi);

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
                variableUi.setPath(!TextUtils.isEmpty(variable.getPath())?pathValores+variable.getPath():"");
                if(variableObservado!=null){
                    variableUi.setPuntajeObtenido(variableObservado.getPuntajeObtenido());
                    variableUi.setVariableObservadaId(variableObservado.getVariableObservadaId());
                }
                variableUi.setInstrumentoEvalId(instrumentoEvaluacion.getInstrumentoEvalId());
                variableUi.setInstrumentoObservadoId(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getKey():"");
                variableUi.setDesempenioIcd(variable.getDesempenioIcd());
                variableUi.setTituloRubroDetalle(variable.getTituloRubroDetalle());
                variableUi.setTipoCompetenciaId(variable.getTipoCompetenciaId());
                variableUi.setTipoDecempenioId(variable.getTipoDecempenioId());

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
                    valorUi.setPath(!TextUtils.isEmpty(valor.getPath())?pathValores+valor.getPath():"");
                    valorUi.setPuntaje(valor.getPuntaje());
                    valorUi.setTipoInputRespuestaId(valor.getTipoInputRespuestaId());
                    valorUi.setVariableId(valor.getVariableId());
                    valorUiList.add(valorUi);
                }

                variableUi.setValores(valorUiList);
                variableUiList.add(variableUi);
            }
            instrumentoUi.setVariables(variableUiList);
            transformarNotasANotasRubrica(instrumentoUi);
        }

        return instrumentoUiList;
    }

    @Override
    public InstrumentoUi getInstrumento(int instrumentoId) {

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_PathImgPreguntasInstrumento"))
                .querySingle();

        String pathValores = webconfig!=null?webconfig.getContent():"";

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

        InstrumentoEvaluacion instrumentoEvaluacion = SQLite.select()
                .from(InstrumentoEvaluacion.class)
                .where(InstrumentoEvaluacion_Table.InstrumentoEvalId.eq(instrumentoId))
                .querySingle();


        InstrumentoUi instrumentoUi = new InstrumentoUi();
        instrumentoUi.setInstrumentoEvalId(instrumentoId);
        if(instrumentoEvaluacion!=null){
            instrumentoUi.setTipoNotaId(instrumentoEvaluacion.getTipoNotaId());
            instrumentoUi.setRubroEvaluacionId(instrumentoEvaluacion.getRubroEvaluacionId());
        }

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
            variableUi.setPath(!TextUtils.isEmpty(variable.getPath())?pathValores+variable.getPath():"");
            if(variableObservado!=null){
                variableUi.setPuntajeObtenido(variableObservado.getPuntajeObtenido());
                variableUi.setVariableObservadaId(variableObservado.getVariableObservadaId());
            }
            variableUi.setInstrumentoEvalId(instrumentoId);
            variableUi.setInstrumentoObservadoId(instrumentoEvaluacionObservado!=null?instrumentoEvaluacionObservado.getKey():"");
            variableUi.setDesempenioIcd(variable.getDesempenioIcd());
            variableUi.setTituloRubroDetalle(variable.getTituloRubroDetalle());
            variableUi.setTipoCompetenciaId(variable.getTipoCompetenciaId());
            variableUi.setTipoDecempenioId(variable.getTipoDecempenioId());

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
                valorUi.setPath(!TextUtils.isEmpty(valor.getPath())?pathValores+valor.getPath():"");
                valorUi.setPuntaje(valor.getPuntaje());
                valorUi.setTipoInputRespuestaId(valor.getTipoInputRespuestaId());
                valorUi.setVariableId(valor.getVariableId());
                valorUiList.add(valorUi);
            }

            variableUi.setValores(valorUiList);
            variableUiList.add(variableUi);
        }
        instrumentoUi.setVariables(variableUiList);
        transformarNotasANotasRubrica(instrumentoUi);
        return instrumentoUi;
    }

    void transformarNotasANotasRubrica(InstrumentoUi instrumentoUi){

        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_UrlExpresiones"))
                .querySingle();

        String pathValores = webconfig!=null?webconfig.getContent():"";

        List<RubroDetalleUi> rubroDetalleUiList = new ArrayList<>();

        for (VariableUi variableUi : instrumentoUi.getVariables()){
            RubroDetalleUi rubroDetalleUi = new RubroDetalleUi();
            rubroDetalleUi.setDesempenioIcd(variableUi.getDesempenioIcd());
            int position = rubroDetalleUiList.indexOf(rubroDetalleUi);
            if(position==-1){
                rubroDetalleUi.setTipoCompetenciaId(variableUi.getTipoCompetenciaId());
                rubroDetalleUi.setTipoDecempenioId(variableUi.getTipoDecempenioId());
                rubroDetalleUi.setTituloRubroDetalle(variableUi.getTituloRubroDetalle());
                rubroDetalleUi.setVariableUiList(new ArrayList<>());
                rubroDetalleUiList.add(rubroDetalleUi);
            }else {
                rubroDetalleUi = rubroDetalleUiList.get(position);
            }
            rubroDetalleUi.getVariableUiList().add(variableUi);
        }

        TipoNotaC tipoNotaC = SQLite.select()
                .from(TipoNotaC.class)
                .where(TipoNotaC_Table.tipoNotaId.eq(instrumentoUi.getTipoNotaId()))
                .querySingle();

        List<ValorTipoNotaC> valorTipoNotaCList = SQLite.select()
                .from(ValorTipoNotaC.class)
                .where(ValorTipoNotaC_Table.tipoNotaId.eq(instrumentoUi.getTipoNotaId()))
                .queryList();

        for (RubroDetalleUi rubroDetalleUi : rubroDetalleUiList){
            if (tipoNotaC!=null) {
                int mint_resultado = 0;
                for (VariableUi variableUi : rubroDetalleUi.getVariableUiList()){
                    int mint_puntaje = variableUi.getPuntaje();
                    int mint_puntajeObtenido = variableUi.getPuntajeObtenido();

                    mint_resultado += mint_puntaje > 0 ? UtilsPortalAlumno.transformacionInvariante(0, mint_puntaje, mint_puntajeObtenido,tipoNotaC.getValorMinino(), tipoNotaC.getValorMaximo()) : 0;

                }

                if (!rubroDetalleUi.getVariableUiList().isEmpty()) {

                    mint_resultado /= rubroDetalleUi.getVariableUiList().size();

                    rubroDetalleUi.setNota(mint_resultado);
                    rubroDetalleUi.setTipoNotaTipoId(tipoNotaC.getTipoId());
                    ValorTipoNotaC valorTipoNotaC = getValorTipoNotaCalculado(tipoNotaC, valorTipoNotaCList, mint_resultado);
                    if(valorTipoNotaC!=null){
                        rubroDetalleUi.setAliasValorTipoNota(valorTipoNotaC.getAlias());
                        rubroDetalleUi.setIconoValorTipoNota(pathValores + "" +valorTipoNotaC.getIcono());
                        rubroDetalleUi.setTituloValorTipoNota(valorTipoNotaC.getTitulo());
                    }

                }

            }


        }

        int vint_resultadoAlumno = 0;
        int vint_count_decempenioIcd = 0;
        for (RubroDetalleUi rubroDetalleUi : rubroDetalleUiList){
            vint_resultadoAlumno += rubroDetalleUi.getNota();
            vint_count_decempenioIcd++;
        }

        if (vint_count_decempenioIcd > 0) vint_resultadoAlumno /= vint_count_decempenioIcd;
        instrumentoUi.setRubroDetalleUiList(rubroDetalleUiList);
        instrumentoUi.setNota(vint_resultadoAlumno);

        if (tipoNotaC!=null) {
            instrumentoUi.setTipoIdTipoNota(tipoNotaC.getTipoId());
            ValorTipoNotaC valorTipoNotaC = getValorTipoNotaCalculado(tipoNotaC, valorTipoNotaCList, vint_resultadoAlumno);
            if(valorTipoNotaC!=null){
                instrumentoUi.setAliasValorTipoNota(valorTipoNotaC.getAlias());
                instrumentoUi.setIconoValorTipoNota(pathValores + "" +valorTipoNotaC.getIcono());
                instrumentoUi.setTituloValorTipoNota(valorTipoNotaC.getTitulo());
            }

            double vint_puntoBase = (tipoNotaC.getValorMaximo() - tipoNotaC.getValorMinino()) * vint_count_decempenioIcd;

            double vint_puntosActuales = UtilsPortalAlumno.transformacionInvariante((tipoNotaC.getValorMinino()), tipoNotaC.getValorMaximo(), vint_resultadoAlumno,0, vint_puntoBase);

            instrumentoUi.PuntoBase = vint_puntoBase;
            instrumentoUi.PuntoActuales = vint_puntosActuales;
            instrumentoUi.Porcentaje = 0;
            try {
                instrumentoUi.Porcentaje = ((vint_puntosActuales / vint_puntoBase) * 100);

            } catch (Exception e) {
               e.printStackTrace();
            }

            Integer[] vlst_peso = getPercentParts(100, instrumentoUi.getRubroDetalleUiList().size());

            for (int i = 0; i < instrumentoUi.getRubroDetalleUiList().size(); i++) {
                try {
                    instrumentoUi.getRubroDetalleUiList().get(i).setPeso(vlst_peso[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


    Integer[] getPercentParts(int vint_totalPeso, int vint_cantidad) {
        if (vint_cantidad == 0) return null;
        Integer[] vlst_percentParts = new Integer[vint_cantidad];

        int vint_subtotalPeso = vint_totalPeso / vint_cantidad;
        int vint_diferencia = vint_totalPeso - (vint_subtotalPeso * vint_cantidad);

        for (int i = 0; i < vint_cantidad; i++) {
            vlst_percentParts[i] = vint_subtotalPeso;
        }

        for (int i = 0; i < vint_diferencia; i++) {
            vlst_percentParts[i] += 1;
        }

        return vlst_percentParts;
    }
    ValorTipoNotaC getValorTipoNotaCalculado(TipoNotaC vobj_NivelLogro, List<ValorTipoNotaC> valorTipoNotaCList, double vint_nota){
        ValorTipoNotaC mobj_result = null;
        if (vobj_NivelLogro.isIntervalo()) {
            for(ValorTipoNotaC valorTipoNota : valorTipoNotaCList){
                boolean mbol_valorInferior = false;
                boolean mbol_valorSuperior = false;

                if (valorTipoNota.isIncluidoLInferior()) {
                    if (valorTipoNota.getLimiteSuperior() >= vint_nota) {
                        mbol_valorSuperior = true;
                    }
                }
                else {
                    if (valorTipoNota.getLimiteSuperior() > vint_nota) {
                        mbol_valorSuperior = true;
                    }
                }

                if (valorTipoNota.isIncluidoLInferior()) {
                    if (valorTipoNota.getLimiteInferior() <= vint_nota) {
                        mbol_valorInferior = true;
                    }
                }
                else {
                    if (valorTipoNota.getLimiteInferior() < vint_nota) {
                        mbol_valorInferior = true;
                    }
                }

                if (mbol_valorInferior && mbol_valorSuperior) {
                    mobj_result = valorTipoNota;
                    break;
                }

            }

        } else {
            long mint_notaEntera = Math.round(vint_nota);
            if (valorTipoNotaCList.size() == 2) {
                int mint_valorintermedio = (vobj_NivelLogro.getValorMaximo() + vobj_NivelLogro.getValorMinino()) / 2;

                List<ValorTipoNotaC> mlst_valoresOrdenados = new ArrayList<>(valorTipoNotaCList);
                Collections.sort(mlst_valoresOrdenados, new Comparator<ValorTipoNotaC>() {
                    @Override
                    public int compare(ValorTipoNotaC o1, ValorTipoNotaC o2) {
                        return Double.compare(o1.getValorNumerico(), o2.getValorNumerico());
                    }
                });

                if (mint_valorintermedio >= mint_notaEntera) {
                    mobj_result = mlst_valoresOrdenados.get(0);
                }
                else {
                    mobj_result = mlst_valoresOrdenados.get(0);
                }
            }
            else {

                List<ValorTipoNotaC> mlst_valoresOrdenados = new ArrayList<>(valorTipoNotaCList);
                Collections.sort(mlst_valoresOrdenados, new Comparator<ValorTipoNotaC>() {
                    @Override
                    public int compare(ValorTipoNotaC o1, ValorTipoNotaC o2) {
                        return Double.compare(o1.getValorNumerico(), o2.getValorNumerico());
                    }
                });

              for(ValorTipoNotaC valorTipoNota : valorTipoNotaCList){
                    if (valorTipoNota.getValorNumerico() == mint_notaEntera) {
                        mobj_result = valorTipoNota;
                        break;
                    }
                }

                if (mobj_result == null) {
                    mobj_result = mlst_valoresOrdenados.get(0);
                }

            }

        }
        return mobj_result;
    }
}
