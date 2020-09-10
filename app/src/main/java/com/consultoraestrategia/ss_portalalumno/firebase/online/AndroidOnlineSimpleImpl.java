package com.consultoraestrategia.ss_portalalumno.firebase.online;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.util.AsyncTaskExecutionHelper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class AndroidOnlineSimpleImpl implements Online {
    private final Context context;
    private static final String TAG = "AndroidOnlineImpl";
    private static Long consultTime = null;
    private static Boolean consultOnline = null;

    public AndroidOnlineSimpleImpl(Context context) {
        this.context = context;

    }

    @Override
    public void online(Callback callback) {
        Log.d(TAG, "online");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

       if (networkInfo!=null&&(networkInfo .isAvailable()) && (networkInfo .isConnected())) {
            AsyncTaskExecutionHelper.executeParallel(new SimpleCounterAsync(),callback );
        } else {
            Log.d(TAG, "No network available!");
            Log.d(TAG, "onLoad: "+false);
            callback.onLoad(false);
        }

    }

    @Override
    public void restarOnline(Callback callback) {
        Log.d(TAG, "regcargar");
        online(callback);
    }


    private static class SimpleCounterAsync extends AsyncTask<Callback, Boolean, Void> {
        private Callback callback;
        private boolean success;
        private HttpURLConnection urlc;

        @Override
        protected Void doInBackground(Callback... voids) {
            this.callback = voids[0];
             long time = new Date().getTime();
             long result = time - (consultTime!=null?consultTime:0);
             if(result>1000){
                 consultTime = time;
                 success = false;
                 //autoCancel();
                 try {
                     urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                     urlc.setRequestProperty("User-Agent", "Test");
                     urlc.setRequestProperty("Connection", "close");
                     urlc.setConnectTimeout(1500);
                     urlc.connect();
                     consultOnline = (urlc.getResponseCode() == 200);
                     Log.d(TAG, "onLoad: "+consultOnline);
                     if(!success) publishProgress(consultOnline);
                 } catch (Exception e) {
                     Log.e(TAG, "Error checking internet connection", e);
                     consultOnline= false;
                     Log.d(TAG, "onLoad: "+false);
                     publishProgress(false);
                 }
             }else {
                 boolean online = consultOnline!=null?consultOnline:false;
                 Log.d(TAG, "onLoad cache: "+online);
                 publishProgress(online);
             }
            success = true;
            return null;
        }

        private void autoCancel(){
            Thread threadTime = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        if (!success) {
                            if(urlc!=null)urlc.getErrorStream();
                            if(urlc!=null)urlc.disconnect();
                            urlc=null;
                            success = true;
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "onLoad stop: "+false);
                                    if(AndroidOnlineSimpleImpl.SimpleCounterAsync.this.callback!=null)
                                        AndroidOnlineSimpleImpl.SimpleCounterAsync.this.callback.onLoad(false);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            threadTime.start();
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            callback.onLoad(values[0]);
        }
    };

}


