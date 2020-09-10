package com.consultoraestrategia.ss_portalalumno.firebase.online;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FirebaseOnlineImpl implements Online {
    private static FirebaseOnlineImpl mInstance;
    private List<Callback> callbackList = new ArrayList<>();
    private int count;
    private FirebaseOnlineImpl() {
    }

    public static FirebaseOnlineImpl getInstance() {
        if (mInstance == null) {
            mInstance = new FirebaseOnlineImpl();
        }
        return mInstance;
    }

    @Override
    public void online(Callback callback) {
        callbackList.add(callback);
        Log.d("FirebaseOnlineImpl", "online");
        new Handler().postDelayed(() -> {
            if(!callbackList.isEmpty()){
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Boolean connected = snapshot.getValue(Boolean.class);
                        Iterator<Callback> iteratorBand = callbackList.iterator();
                        while(iteratorBand.hasNext()){
                            Log.d("FirebaseOnlineImpl", "onLoad: "+true);
                            iteratorBand.next().onLoad(connected!=null?connected:false);
                            iteratorBand.remove();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Iterator<Callback> iteratorBand = callbackList.iterator();
                        while(iteratorBand.hasNext()){
                            Log.d("FirebaseOnlineImpl", "onLoad: "+false);
                            iteratorBand.next().onLoad(false);
                            iteratorBand.remove();
                        }

                    }
                });
            }
        },1000);
    }

    @Override
    public void restarOnline(Callback callback) {

    }
}
