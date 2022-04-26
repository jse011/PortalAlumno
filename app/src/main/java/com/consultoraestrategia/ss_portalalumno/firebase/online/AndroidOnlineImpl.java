package com.consultoraestrategia.ss_portalalumno.firebase.online;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.util.AsyncTaskExecutionHelper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class AndroidOnlineImpl implements Online {
    private final Context context;
    private static final String TAG = "AndroidOnlineImpl";
    private static Long consultTime = null;
    private static Boolean consultOnline = null;

    public AndroidOnlineImpl(Context context) {
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

        if(consultOnline!=null&& !consultOnline){
            //Si es falso siempre devolver falso asta que reaciative
            callback.onLoad(false);
            Log.d(TAG, "modo offline");
        }else if (networkInfo!=null&&(networkInfo .isAvailable()) && (networkInfo .isConnected())) {
            //AsyncTaskExecutionHelper.executeParallel(new SimpleCounterAsync(),new Response(callback, false)  );
            //SimpleCounterAsync simpleCounterAsync = new SimpleCounterAsync(callback, new Request(context.getString(R.string.admin_service)),new Response(false));
            //simpleCounterAsync.execute();
            callback.onLoad(true);
        } else {
            Log.d(TAG, "No network available!");
            Log.d(TAG, "onLoad: "+false);
            callback.onLoad(false);
        }

    }

    @Override
    public void restarOnline(Callback callback) {
        Log.d(TAG, "regcargar");
        Log.d(TAG, "online");
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo!=null&&(networkInfo .isAvailable()) && (networkInfo .isConnected())) {
            //SimpleCounterAsync simpleCounterAsync = new SimpleCounterAsync(callback, new Request(context.getString(R.string.admin_service)) ,new Response(true));
            //simpleCounterAsync.execute();
            //AsyncTaskExecutionHelper.executeParallel(new SimpleCounterAsync(),new Response(callback, true) );
            callback.onLoad(true);
        } else {
            Log.d(TAG, "No network available!");
            Log.d(TAG, "onLoad: "+false);
            callback.onLoad(false);
        }
    }

    private static class SimpleCounterAsync extends CourtineAsync<Boolean>{
        private Callback callback;;
        private Response response;
        private Request request;
        //private boolean success;

        public SimpleCounterAsync(Callback callback, Request request, Response response) {
            this.callback = callback;
            this.response = response;
            this.request = request;
        }

        @Override
        public Boolean onExecute() {
            boolean status = false;
            long time = new Date().getTime();
            long result = time - (consultTime!=null?consultTime:0);
            if((result>5000)||response.isRestart()){
                consultTime = time;
                //success = false;
                //autoCancel();
                try {
                   String url = TextUtils.isEmpty(request.getUrl())?"https://www.google.com": request.getUrl();
                    HttpURLConnection urlc = (HttpURLConnection) (new URL(url).openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    consultOnline = (urlc.getResponseCode() == 200);
                    Log.d(TAG, "onLoad: "+consultOnline);
                    status = consultOnline;
                } catch (Exception e) {
                    Log.e(TAG, "Error checking internet connection", e);
                    consultOnline= false;
                    Log.d(TAG, "onLoad: "+false);
                   //return false;
                }
            }else {
                boolean online = consultOnline!=null?consultOnline:false;
                Log.d(TAG, "onLoad cache: "+online);
                status = online;
            }
            //success = true;
            return status;
        }

        @Override
        public void onPreExecute() {

        }

        @Override
        public void onPostExecute(Boolean result) {
            callback.onLoad(consultOnline);
        }
    }
    /*
    private static class SimpleCounterAsync extends AsyncTask<Response, Boolean, Void> {
        private Callback callback;
        private boolean success;
        private HttpURLConnection urlc;

        @Override
        protected Void doInBackground(Response... voids) {
            this.callback = voids[0].callback;
             long time = new Date().getTime();
             long result = time - (consultTime!=null?consultTime:0);
             if((result>1000)||voids[0].restart){
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
                                    if(SimpleCounterAsync.this.callback!=null)SimpleCounterAsync.this.callback.onLoad(false);
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

*/
    private static  class Request{
        String url;

        public Request(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    private static class Response{
        //private Callback callback;;//
        private boolean restart;

        public Response(/*Callback callback,*/ boolean restart) {
            //this.callback = callback;
            this.restart = restart;
        }
        /*
        public Callback getCallback() {
            return callback;
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
        }*/

        public boolean isRestart() {
            return restart;
        }

        public void setRestart(boolean restart) {
            this.restart = restart;
        }
    }
}


