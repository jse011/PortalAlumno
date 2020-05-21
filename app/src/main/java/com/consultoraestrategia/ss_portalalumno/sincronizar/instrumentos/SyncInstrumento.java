package com.consultoraestrategia.ss_portalalumno.sincronizar.instrumentos;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.consultoraestrategia.ss_portalalumno.entities.BaseEntity;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento;
import com.consultoraestrategia.ss_portalalumno.entities.SilaboEvento_Table;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado;
import com.consultoraestrategia.ss_portalalumno.entities.VariableObservado_Table;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class SyncInstrumento extends Worker {
    protected final static String NAME_SERVICE= "SyncInstrumento";
    public SyncInstrumento(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Result result = Result.failure();
        try {

            CountDownLatch latch = new CountDownLatch(1);
            GlobalSettings globalSettings = GlobalSettings.getCurrentSettings();
            String nodeFirebase = "ups-prueba";//globalSettings!=null?globalSettings.getFirebaseNode():"sinServer";
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
            SessionUser sessionUser = SessionUser.getCurrentUser();
            int alumnoId =  sessionUser!=null?sessionUser.getPersonaId():0;

            Map<String, Object> childUpdates = new HashMap<>();
            for (VariableObservado variableObservado : SQLite.select()
                    .from(VariableObservado.class)
                    .where(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED, BaseEntity.FLAG_UPDATED))
                    .queryList()){


                Map<String, Object> variableObservadaMap = new HashMap<>();
                variableObservadaMap.put("Respondida", 1);
                variableObservadaMap.put("personaId", alumnoId);
                variableObservadaMap.put("puntaje", variableObservado.getPuntajeObtenido());
                variableObservadaMap.put("time", variableObservado.getFechaAccion());
                variableObservadaMap.put("variableId", variableObservado.getVariableId());

                childUpdates.put("/AV_Instrumento/Respuesta/silid_"+variableObservado.getSilaboEventoId()+"/sesid_" + variableObservado.getSesionAprendizajeId()+"/aluid_"+alumnoId+"/Instrumentos/ins_"+variableObservado.getInstrumentoEvalId()+"/Variables/varid_"+variableObservado.getVariableId(), variableObservadaMap);

            }

            if(!childUpdates.isEmpty()){
                mDatabase.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        SQLite.update(VariableObservado.class)
                                .set(VariableObservado_Table.syncFlag.eq(BaseEntity.FLAG_EXPORTED))
                                .where(VariableObservado_Table.syncFlag.in(BaseEntity.FLAG_ADDED, BaseEntity.FLAG_UPDATED))
                                .execute();
                        latch.countDown();
                    }
                });
            } else {
                latch.countDown();
            }

            latch.await();

            result = Result.success();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void start(Context context){
        Data data = new Data.Builder()
                .putInt("iteraciones", 10)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(SyncInstrumento.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).beginUniqueWork(
                NAME_SERVICE,
                ExistingWorkPolicy.APPEND,
                oneTimeWorkRequest)
                .enqueue();

    }
}
