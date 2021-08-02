package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion_online;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.entities.SessionUser;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbSesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.lib.cardviewGesture.MovableCardView;
import com.consultoraestrategia.ss_portalalumno.util.KeyboardUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.annotations.NotNull;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class EvaluacionOnlineActivity extends AppCompatActivity implements LifecycleImpl.LifecycleListener {
    private String TAG = "EvaluacionOnlineActivity";

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar11)
    ProgressBar progressBar11;
    @BindView(R.id.background)
    FrameLayout background;
    @BindView(R.id.root_layout)
    FrameLayout root_layout;
    @BindView(R.id.contentPlayer)
    FrameLayout contentPlayer;
    @BindView(R.id.youtube_layout)
    FrameLayout youtubeLayout;

    private KeyBoardView keyBoardView;
    private YoutubeConfig youtubeConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_online);
        ButterKnife.bind(this);
        setupWebView();

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new LifecycleImpl(0,this),true);
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
                                        background.setVisibility(View.GONE);

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
        jsInterface = new JavaScriptInterface(this, keyBoardView);
        webView.addJavascriptInterface(jsInterface, "JSInterface");
    }

    @Override
    public void onChildsFragmentViewCreated() {

    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ConstraintLayout.LayoutParams layoutLayoutParams = (ConstraintLayout.LayoutParams) youtubeLayout.getLayoutParams();
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            layoutLayoutParams.dimensionRatio = null;
            layoutLayoutParams.bottomMargin = (int)UtilsPortalAlumno.convertDpToPixel(0, this);
            layoutLayoutParams.leftMargin = (int)UtilsPortalAlumno.convertDpToPixel(55, this);
            layoutLayoutParams.rightMargin = (int)UtilsPortalAlumno.convertDpToPixel(55, this);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutLayoutParams.dimensionRatio = "12:9";
            layoutLayoutParams.bottomMargin = (int)UtilsPortalAlumno.convertDpToPixel(45, this);
            layoutLayoutParams.leftMargin = (int)UtilsPortalAlumno.convertDpToPixel(0, this);
            layoutLayoutParams.rightMargin = (int)UtilsPortalAlumno.convertDpToPixel(0, this);
        }
    }

    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {
        if(f instanceof KeyBoardView){
            keyBoardView = (KeyBoardView)f;
            keyBoardView.setInputText(getTextoInput());
            keyBoardView.setPresenter(this);
        }
    }

    @Override
    public void onBackPressed() {
        if(background.getVisibility() == View.VISIBLE){
            super.onBackPressed();
        }else if( contentPlayer.getVisibility()!=View.GONE){
            salirYoutube();
        }else {
            webView.loadUrl("javascript:(function(){var input = document.querySelector('.btn-eq-pause'); input.dispatchEvent(new Event('click'));})()");
        }
    }

    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        keyBoardView = null;
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if(f instanceof KeyBoardView){
            keyBoardView = (KeyBoardView)f;
            keyBoardView.setInputText(getTextoInput());
            keyBoardView.setPresenter(this);
        }
    }

    public static class JavaScriptInterface {
        private final EvaluacionOnlineActivity activity;
        private final KeyBoardView keyBoardView;
        public JavaScriptInterface(EvaluacionOnlineActivity activity, KeyBoardView keyBoardView) {
            this.activity = activity;
            this.keyBoardView = keyBoardView;
        }

        @JavascriptInterface
        public void finishActivity(){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    activity.finish();
                }
            });

        }

        @JavascriptInterface
        public void openkeyboard(String texto){
            activity.setTextoInput(texto);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    if(keyBoardView==null)new Dialogkeyboard().show(activity.getSupportFragmentManager(),"Dialogkeyboard");
                }
            });
        }

        @JavascriptInterface
        public void showYoutubeAndroid(String url){

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                   activity.showYoutube(url);
                }
            });

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

    public static class Dialogkeyboard extends BottomSheetDialogFragment implements KeyBoardView {

        private Unbinder unbinder;
        @BindView(R.id.txt_titulo)
        TextView txtTitulo;
        @BindView(R.id.editText)
        EditText editText;
        @BindView(R.id.btn_guardar)
        Button btnGuardar;
        @BindView(R.id.btn_cancelar)
        Button btnCancelar;



        private EvaluacionOnlineActivity activity;

        @Override
        public void setupDialog(@NonNull Dialog dialog, int style) {


            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
            bottomSheetDialog.setContentView(R.layout.dialog_keyboard_evalaucion_online);
            unbinder = ButterKnife.bind(this, dialog.getWindow().getDecorView());
            txtTitulo.setText("Escribe tu respuesta");
            editText.requestFocus(); //Asegurar que editText tiene focus
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

            try {
                Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
                behaviorField.setAccessible(true);
                final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(activity!=null)activity.setTextoInput(editText.getText().toString());
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogKeyBoardStyle);
        }


        @Override
        public void onDismiss(DialogInterface dialog)
        {
            editText.clearFocus();
            Log.d(getClass().getSimpleName(), "hideKeyboard");
            KeyboardUtils.hideKeyboard(editText.getContext(), editText);
            super.onDismiss(dialog);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                    FrameLayout bottomSheet = (FrameLayout)
                            dialog.findViewById(R.id.design_bottom_sheet);
                    BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    behavior.setPeekHeight(0);
                }
            });
        }
        @Override
        public void onDestroyView() {
            if(activity!=null){
                activity.clearFocusWebView();

                KeyboardUtils.hideKeyboard(activity, activity.webView);
            }
            super.onDestroyView();
            activity=null;
            unbinder.unbind();
        }

        @OnClick({R.id.btn_guardar, R.id.btn_cancelar})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.btn_cancelar:
                    dismiss();
                    break;
                case R.id.btn_guardar:
                    if(activity!=null)activity.setTextoInput(editText.getText().toString());
                    dismiss();
                    break;
            }
        }


        @Override
        public void setInputText(String texto) {
            editText.setText(texto);
            editText.setSelection(editText.getText().length());
        }

        @Override
        public void salir() {
            dismiss();
        }

        @Override
        public void setPresenter(EvaluacionOnlineActivity activity) {
            this.activity = activity;
        }
    }



    interface KeyBoardView{

        void setInputText(String texto);

        void salir();

        void setPresenter(EvaluacionOnlineActivity activity);
    }

    private String textoInput;

    public String getTextoInput() {
        return textoInput;
    }

    public void setTextoInput(String textoInput) {
        this.textoInput = (TextUtils.isEmpty(textoInput)?"":textoInput);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:(function(){var input = document.querySelector('.textarea_eq_respuesta');  input.value = '"+textoInput+"';  input.dispatchEvent(new Event('keypress')); input.dispatchEvent(new Event('keydown')); })()");
            }
        });
    }

    private void clearFocusWebView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:(function(){var input = document.querySelector('.textarea_eq_respuesta');  input.dispatchEvent(new Event('clearKeyBoad')); })()");
            }
        });
    }


    public void showYoutube(String url) {
        contentPlayer.setVisibility(View.VISIBLE);
        setupYoutubePlayer(url);
    }

    private void setupYoutubePlayer(String url) {
        if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(this);
        Log.d(TAG, url);
        if (youtubeConfig != null) {
            youtubeConfig.setDisabledRotation(true);
            youtubeConfig.initialize(YouTubeUrlParser.getVideoId(url), getSupportFragmentManager(), R.id.youtube_layout, new YoutubeConfig.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_pause_youtube));
                }

                @Override
                public void onPaused() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_play_youtube));
                }

                @Override
                public void onLandscape() {

                }

                @Override
                public void onPortrait() {

                }
            });
        }
    }

    @OnClick(R.id.btn_close_youtube)
    public void onbtnCloseYoutubeClicked() {
       salirYoutube();
    }

    @OnClick(R.id.contentPlayer)
    public void onbtnContentPlayerClicked() {
        salirYoutube();
    }

    void  salirYoutube(){
        contentPlayer.setVisibility(View.GONE);
        if (youtubeConfig != null) youtubeConfig.closeVideo(getSupportFragmentManager(), this);
    }



}
