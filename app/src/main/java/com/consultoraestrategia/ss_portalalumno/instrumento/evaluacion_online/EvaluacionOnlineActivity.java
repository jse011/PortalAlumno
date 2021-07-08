package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion_online;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluacionOnlineActivity extends AppCompatActivity {
    private String TAG = "EvaluacionOnlineActivity";

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar11)
    ProgressBar progressBar11;
    @BindView(R.id.background)
    FrameLayout background;
    @BindView(R.id.root_layout)
    FrameLayout root_layout;
    @BindView(R.id.card_view)
    FrameLayout card_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_online);
        ButterKnife.bind(this);
        setupWebView();
        card_view.setVisibility(View.GONE);
        KeyboardVisibilityEvent.setEventListener(
               this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        Log.d(TAG, "open: " + isOpen);
                        card_view.setVisibility(isOpen?View.VISIBLE:View.GONE);
                    }
                });
    }

    JavaScriptInterface jsInterface;

    private String getUrlServidor(){
        String serverUrl = GlobalSettings.getServerUrl();
        return !TextUtils.isEmpty(serverUrl)?serverUrl+"/?insmovil":"";
    }
    private void setupWebView() {
        try {
            iCRMEdu.variblesGlobales.setUpdateInstrumento(true);
        }catch (Exception e){
            e.printStackTrace();
        }

        int alumnoId = SessionUser.getCurrentUser().getPersonaId();

        try {
            GbCursoUi gbCursoUi = iCRMEdu.variblesGlobales.getGbCursoUi();
            GbSesionAprendizajeUi gbSesionAprendizajeUi = iCRMEdu.variblesGlobales.getGbSesionAprendizajeUi();

            int silaboEventoId = gbCursoUi.getSilaboEventoId();
            int sesionId = gbSesionAprendizajeUi.getSesionAprendizajeId();
            int instrumentoId = iCRMEdu.variblesGlobales.getInstrumentoId();


            String postData = "inst=" + URLEncoder.encode(String.valueOf(instrumentoId), "UTF-8")+
                    "&sila=" + URLEncoder.encode(String.valueOf(silaboEventoId), "UTF-8")+
                    "&Sesi=" + URLEncoder.encode(String.valueOf(sesionId), "UTF-8")+
                    "&alum=" + URLEncoder.encode(String.valueOf(alumnoId), "UTF-8");
            webView.postUrl(getUrlServidor(), postData.getBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //webView.clearCache(true);
        //webView.setInitialScale(1);
        //webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setGeolocationEnabled(true);
        //webView.getSettings().setAppCacheEnabled(true);
        //webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //webView.getSettings().getCacheMode();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            ///webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            boolean se_oculto_fondo = false;
            final String url = getUrlServidor();
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if(view.getUrl().equals(url)){
                    progressBar11.setVisibility(View.VISIBLE);
                    background.setVisibility(View.VISIBLE);
                }else {
                    if(progress>=90&&!se_oculto_fondo){
                        se_oculto_fondo = true;
                        background.animate()
                                //.translationY(view.getHeight())
                                .alpha(0.0f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        progressBar11.setVisibility(View.GONE);

                                    }
                                });

                        progressBar11.animate()
                                //.translationY(view.getHeight())
                                .alpha(0.0f)
                                .setDuration(500)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        progressBar11.setVisibility(View.GONE);

                                    }
                                });

                    }



                }



            }

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });


    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(jsInterface!=null) webView.removeJavascriptInterface("JSInterface");
        jsInterface = new JavaScriptInterface(this);
        webView.addJavascriptInterface(jsInterface, "JSInterface");
    }

    public static class JavaScriptInterface {
        private Activity activity;

        public JavaScriptInterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void finishActivity(){
            activity.finish();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }


    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController windowInsetsController = getWindow().getInsetsController();
            // Default behavior is that if navigation bar is hidden, the system will "steal" touches
            // and show it again upon user's touch. We just want the user to be able to show the
            // navigation bar by swipe, touches are handled by custom code -> change system bar behavior.
            // Alternative to deprecated SYSTEM_UI_FLAG_IMMERSIVE.
            windowInsetsController.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            // make navigation bar translucent (alternative to deprecated
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            // - do this already in hideSystemUI() so that the bar
            // is translucent if user swipes it up
            getWindow().setNavigationBarColor( getColor(R.color.internal_black_semitransparent_light));

            // Finally, hide the system bars, alternative to View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            // and SYSTEM_UI_FLAG_FULLSCREEN.
            windowInsetsController.hide(WindowInsets.Type.systemBars());
        }else {
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // show app content in fullscreen, i. e. behind the bars when they are shown (alternative to
            // deprecated View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            getWindow().setDecorFitsSystemWindows(false);
            // finally, show the system bars
            getWindow().getInsetsController().show(WindowInsets.Type.systemBars());
        }else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

    }



}
