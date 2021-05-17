package com.consultoraestrategia.ss_portalalumno.global.asistencia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig;
import com.consultoraestrategia.ss_portalalumno.entities.Webconfig_Table;
import com.consultoraestrategia.ss_portalalumno.firebase.wrapper.FirebaseCancelImpl;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAsistenciaImpl implements Asistencia {

    FirebaseCancelImpl getAccionAcistenciaDocente;
    FirebaseCancelImpl sinInternet;

    @Override
    public void desconectarAsistencia() {
        if(getAccionAcistenciaDocente!=null)getAccionAcistenciaDocente.cancel();
        if(sinInternet!=null)sinInternet.cancel();
    }

    @Override
    public void sinInternet(Callback callback) {
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        if(sinInternet!=null)sinInternet.cancel();
        sinInternet = new FirebaseCancelImpl(offsetRef, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
               if(connected){
                   callback.onSuccess();
               }else {
                   callback.onError();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError();
            }
        });

    }

    @Override
    public FirebaseCancelImpl f_getAccionAcistenciaDocente( int silaboEventoId, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig!=null?webconfig.getContent():"sinServer";

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        DatabaseReference databaseReference = mDatabase.child("/AV_Asistencia/silid_"+silaboEventoId+"/Docente");
        databaseReference.keepSynced(false);
        if(getAccionAcistenciaDocente!=null)getAccionAcistenciaDocente.cancel();
        getAccionAcistenciaDocente = new FirebaseCancelImpl(databaseReference, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError();
            }
        });

        return  getAccionAcistenciaDocente;
    }

    @Override
    public void f_SaveAsistenciaAlumno( int silaboEventoId, boolean activo, boolean open_sesion, Callback callback) {
        Webconfig webconfig = SQLite.select()
                .from(Webconfig.class)
                .where(Webconfig_Table.nombre.eq("wstr_Servidor"))
                .querySingle();

        String nodeFirebase = webconfig != null ? webconfig.getContent() : "sinServer";
        SessionUser sessionUser = SessionUser.getCurrentUser();
        int personaId = sessionUser!=null?sessionUser.getPersonaId():0;

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("PersonaId", personaId);
        objectMap.put("Snapshot", ServerValue.TIMESTAMP);
        objectMap.put("Activo", activo);
        objectMap.put("SilaboId", silaboEventoId);

        if(open_sesion)objectMap.put("OpenSesion", ServerValue.TIMESTAMP);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("/"+nodeFirebase);
        mDatabase.keepSynced(false);
        mDatabase.child("/AV_Asistencia/silid_"+silaboEventoId+"/Alumno/pers_"+personaId)
                .updateChildren(objectMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError==null){
                            callback.onSuccess();
                        }else {
                            callback.onError();
                        }
                    }
                });
    }




}
