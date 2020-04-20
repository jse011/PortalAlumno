package com.consultoraestrategia.ss_portalalumno.retrofit.wrapper;

public interface RetrofitCancel<T> {

    void enqueue(Callback<T> callback);

    boolean isExecuted();

    void cancel();

    boolean isCanceled();

    interface Callback<T> {
        void onResponse(T response);
        void onFailure(Throwable t);
    }
}
