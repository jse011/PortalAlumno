package com.consultoraestrategia.ss_portalalumno.firebase.online;

public interface Online {
    void online(Callback callback);
    void restarOnline(Callback callback);
    interface Callback{
        void onLoad(boolean success);
    }
}
