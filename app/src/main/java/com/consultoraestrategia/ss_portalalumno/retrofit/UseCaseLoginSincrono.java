package com.consultoraestrategia.ss_portalalumno.retrofit;

import com.consultoraestrategia.ss_portalalumno.retrofit.wrapper.RetrofitCancel;

public interface UseCaseLoginSincrono<J,S>  {
    RetrofitCancel execute(J request, Callback<S> callback);

    interface Callback<S>{
        void onResponse(boolean success, S value);
    }
}
