package com.consultoraestrategia.ss_portalalumno.estadocuenta2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.entities.GlobalSettings;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineSimpleImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.Online;
import com.consultoraestrategia.ss_portalalumno.lib.nestedScrollWebView.NestedScrollWebView;
import com.google.android.material.appbar.AppBarLayout;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstadoCuenta2Activity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.webView)
    NestedScrollWebView webView;
    @BindView(R.id.progressBar11)
    ProgressBar progressBar11;
    private Online online;
    private String TAG = "EstadoCuenta2ActivityTAG";

    public static void start(Context context, int alumnoId,String numeroDocumento){
        Intent intent = new Intent(context, EstadoCuenta2Activity.class);
        intent.putExtra("alumnoId", alumnoId);
        intent.putExtra("numeroDocumento", numeroDocumento);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_cuenta_2);
        ButterKnife.bind(this);
        setupToolbar();
        setupWebView();
        setupOnline();
        openLink();
    }

    private void openLink() {
        String serverUrl = GlobalSettings.getServerUrl();
        String url = !TextUtils.isEmpty(serverUrl)?serverUrl+"/?misaldo":"";
        try {
            int alumnoId = getIntent().getIntExtra("alumnoId", 0);
            String numeroDoc = getIntent().getStringExtra("numeroDocumento");
            String postData = "nrodocumento=" + URLEncoder.encode(numeroDoc, "UTF-8")+ "&password=" + URLEncoder.encode(String.valueOf(alumnoId), "UTF-8");
            webView.postUrl(url, postData.getBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void setupOnline() {
        online = new AndroidOnlineSimpleImpl(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //change color of status bar
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#173766"));
        }
    }

    private void setupWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().getCacheMode();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                Log.d(TAG, "progress: " + progress);
                if(progress==100){
                    progressBar11.setVisibility(View.GONE);
                }else {
                    progressBar11.setVisibility(View.VISIBLE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.estado_cuenta_menu, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final View view = findViewById(R.id.action_refresh);

                if (view != null) {
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            openLink();
                            return true;
                        }
                    });
                }
            }
        });
        return true;
    }

    private long mLastClickTime = 0;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            Toast.makeText(this, "Mantenga presionado el botón para refrescar", Toast.LENGTH_SHORT).show();
        }else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
