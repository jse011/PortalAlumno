package com.consultoraestrategia.ss_portalalumno.global.offline;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class OfflineFirebase implements Offline {

    private final Context context;
    private String TAG = OfflineFirebase.class.getSimpleName();

    public OfflineFirebase(Context context) {
        this.context = context;
    }

    @Override
    public boolean isConnect() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null)
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
