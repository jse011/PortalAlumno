package com.consultoraestrategia.ss_portalalumno.retrofit.wrapper;

import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.retrofit.response.RestApiResponse;

import retrofit2.Call;
import retrofit2.Response;

public class RetrofitCancelImpl<T> implements RetrofitCancel<T> {

    private  Call<RestApiResponse<T>> responseCall;

    public RetrofitCancelImpl(Call<RestApiResponse<T>> responseCall) {
        this.responseCall = responseCall;
    }

    @Override
    public void enqueue(final Callback<T> callback) {
        responseCall.enqueue(new retrofit2.Callback<RestApiResponse<T>>() {
            @Override
            public void onResponse(Call<RestApiResponse<T>> call, Response<RestApiResponse<T>> response) {
                Log.d("RetrofitCancelImpl","response" + response.isSuccessful());
                if (!response.isSuccessful()){
                    callback.onResponse(null);
                }else {
                    RestApiResponse<T> body = response.body();
                    if(body == null){
                        callback.onResponse(null);
                    } else if(body.isSuccessful()){
                        callback.onResponse(body.getValue());
                    }else {
                        callback.onResponse(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestApiResponse<T>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    @Override
    public boolean isExecuted() {
        return responseCall.isExecuted();
    }

    @Override
    public void cancel() {
        responseCall.cancel();
    }

    @Override
    public boolean isCanceled() {
        return responseCall.isCanceled();
    }

}
