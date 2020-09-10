package com.consultoraestrategia.ss_portalalumno.firebase.wrapper;

public interface StorageCancel {
    void onPause();
    void onResume();
    void onCancel();

    boolean isComplete();
    boolean isSuccessful();
    boolean isCanceled();
    boolean isInProgress();
    boolean isPaused();

}
