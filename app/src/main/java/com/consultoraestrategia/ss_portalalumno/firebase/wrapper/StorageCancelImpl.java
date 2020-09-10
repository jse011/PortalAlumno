package com.consultoraestrategia.ss_portalalumno.firebase.wrapper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

public class StorageCancelImpl implements StorageCancel{

    private final UploadTask uploadTask;

    public StorageCancelImpl(UploadTask uploadTask) {
        this.uploadTask = uploadTask;
    }

    public StorageCancelImpl addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> onProgressListener){
        uploadTask.addOnProgressListener(onProgressListener);
        return this;
    }

    public StorageCancelImpl addOnPausedListener(OnPausedListener<UploadTask.TaskSnapshot> onPausedListener){
        uploadTask.addOnPausedListener(onPausedListener);
        return this;
    }

    public StorageCancelImpl addOnFailureListener(OnFailureListener onFailureListener){
        uploadTask.addOnFailureListener(onFailureListener);
        return this;
    }

    public StorageCancelImpl addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener){
        uploadTask.addOnSuccessListener(onSuccessListener);
        return this;
    }

    @Override
    public void onPause() {
        if(uploadTask!=null)uploadTask.pause();
    }

    @Override
    public void onResume() {
        if(uploadTask!=null)uploadTask.resume();
    }

    @Override
    public void onCancel() {
        if(uploadTask!=null)uploadTask.cancel();
    }

    @Override
    public boolean isComplete() {
        return uploadTask != null && uploadTask.isComplete();
    }

    @Override
    public boolean isSuccessful() {
        return uploadTask != null && uploadTask.isSuccessful();
    }

    @Override
    public boolean isCanceled() {
        return uploadTask == null || uploadTask.isCanceled();//Si es null entonce esta cancelado
    }

    @Override
    public boolean isInProgress() {
        return uploadTask != null && uploadTask.isInProgress();
    }

    @Override
    public boolean isPaused() {
        return uploadTask != null && uploadTask.isPaused();
    }

}
