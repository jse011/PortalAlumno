package com.consultoraestrategia.ss_portalalumno.retrofit.response;

/**
 * Created by SCIEV on 24/07/2018.
 */

public class RestApiResponse<T> {

    boolean Successful;

    T Value;

    public RestApiResponse() {
    }

    public boolean isSuccessful() {
        return Successful;
    }

    public void setSuccessful(boolean successful) {
        Successful = successful;
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T value) {
        Value = value;
    }
}
