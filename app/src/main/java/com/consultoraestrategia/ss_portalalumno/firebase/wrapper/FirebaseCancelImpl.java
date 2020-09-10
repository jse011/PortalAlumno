package com.consultoraestrategia.ss_portalalumno.firebase.wrapper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseCancelImpl implements FirebaseCancel {
    private DatabaseReference databaseReference;
    private Query query;
    private ChildEventListener childEventListener;
    private ValueEventListener valueEventListener;

    public FirebaseCancelImpl(DatabaseReference databaseReference, ChildEventListener childEventListener) {
        this.databaseReference = databaseReference;
        this.childEventListener = databaseReference.addChildEventListener(childEventListener);
    }

    public FirebaseCancelImpl(DatabaseReference databaseReference, ValueEventListener valueEventListener) {
        this.databaseReference = databaseReference;
        this.valueEventListener = databaseReference.addValueEventListener(valueEventListener);
    }

    public FirebaseCancelImpl(Query query, ChildEventListener childEventListener) {
        this.query = query;
        this.childEventListener = query.addChildEventListener(childEventListener);
    }

    public FirebaseCancelImpl(Query query, ValueEventListener valueEventListener) {
        this.query = query;
        this.valueEventListener = query.addValueEventListener(valueEventListener);
    }

    @Override
    public void cancel() {
        if(childEventListener!=null&&databaseReference!=null)databaseReference.removeEventListener(childEventListener);
        if(valueEventListener!=null&&databaseReference!=null)databaseReference.removeEventListener(valueEventListener);
        if(childEventListener!=null&&query!=null)query.getRef().removeEventListener(childEventListener);
        if(valueEventListener!=null&&query!=null)query.getRef().removeEventListener(valueEventListener);
    }
}
