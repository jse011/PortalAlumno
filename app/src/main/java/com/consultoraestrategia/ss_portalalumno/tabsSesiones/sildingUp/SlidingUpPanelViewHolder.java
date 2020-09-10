package com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.preview.ActividadYoutubePreview;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.TabSesionPresenter;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.TabSesionesActivity2;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp.exoplayer.ExoPlayerFragment;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp.exoplayer.ExoPlayerView;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;
import com.github.vkay94.dtpv.DoubleTapPlayerView;
import com.github.vkay94.dtpv.SeekListener;
import com.github.vkay94.dtpv.youtube.YouTubeOverlay;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SlidingUpPanelViewHolder {
    
    private static final String TAG = "slidingUpPanelTAG";
    private Unbinder unbinder;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingLayout;
    @BindView(R.id.dragView)
    LinearLayout dragView;
    @BindView(R.id.btn_close_youtube)
    FrameLayout cerrarVideo;
    @BindView(R.id.youtube_layout)
    FrameLayout youtubeLayout;
    @BindView(R.id.txt_titulo_youtube)
    TextView txtTituloYoutube;
    @BindView(R.id.txt_descripcion_youtube)
    TextView txtDescripcionYoutube;
    @BindView(R.id.content_youtube_detalle)
    ConstraintLayout contentYoutubeDetalle;
    @BindView(R.id.content_youtube)
    LinearLayout contentYoutube;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.img_action_youtube)
    ImageView imgActionYoutube;
    @BindView(R.id.btn_action_youtube)
    FrameLayout btnActionYoutube;

    //#region archivo drive
    @BindView(R.id.slider_up_document)
    ConstraintLayout sliderUpDocument;
    @BindView(R.id.slider_up_multimedia)
    LinearLayout sliderUpMultimedia;
    @BindView(R.id.slider_up_youtube)
    LinearLayout sliderUpYoutube;
    @BindView(R.id.progress_bar_archivo)
    ProgressBar progressBarArchivo;
    @BindView(R.id.list)
    ScrollView list;
    @BindView(R.id.backPrincipal_document)
    FrameLayout backPrincipalDocument;
    @BindView(R.id.btnDownload_document)
    FrameLayout btnDownloadDocument;
    @BindView(R.id.btnOpen_document)
    FrameLayout btnOpenDocument;
    @BindView(R.id.msg_succes_progress_document)
    CardView msgSuccesProgressDocument;
    @BindView(R.id.msg_progress_document)
    CardView msgProgressDocument;
    //#endregion archivo drive

    @BindView(R.id.exo_player_layout)
    FrameLayout exoPlayerLayout;
    @BindView(R.id.txt_titulo_exo_player)
    TextView txtTituloExoPlayer;
    @BindView(R.id.txt_descripcion_exo_player)
    TextView txtDescripcionExoPlayer;
    @BindView(R.id.img_close_exo_player)
    ImageView imgCloseExoPlayer;
    @BindView(R.id.btn_close_exo_player)
    FrameLayout btnCloseExoPlayer;
    @BindView(R.id.img_action_exo_player)
    ImageView imgActionExoPlayer;
    @BindView(R.id.btn_action_exo_player)
    FrameLayout btnActionExoPlayer;
    @BindView(R.id.content_exo_player_detalle)
    ConstraintLayout contentExoPlayerDetalle;
    @BindView(R.id.content_exo_player)
    LinearLayout contentExoPlayer;
    @BindView(R.id.cont_preview_exo_player)
    FrameLayout contPreviewExoPlayer;
    @BindView(R.id.list_exo_player)
    ScrollView listExoPlayer;

    private YoutubeConfig youtubeConfig;

    SlidingUpPanelLayout.PanelSlideListener slideListenerYoutube = null;
    SlidingUpPanelLayout.PanelSlideListener slideListenerMultimedia = null;
    SlidingUpPanelLayout.PanelSlideListener slideListenerSeleceted = null;
    private ActividadYoutubePreview activityYoutubePreview;
    private long descargaId;
    private boolean isVideoFullscreen;
    private boolean disabledOrientation;
    private ExoPlayerFragment exoPlayerFragment;
    private ExoPlayerView exoPlayerView;

    public SlidingUpPanelViewHolder(Activity activity) {
        unbinder = ButterKnife.bind(this, activity);
        setupSlideUp();
        setupYoutube();
        setupWebView();
        setupDowload();
    }

    private void setupDowload() {
        slidingLayout.getContext().registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void setupYoutube() {
        if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(slidingLayout.getContext());
    }

    private void setupSlideUp() {
        youtubeConfig = new YoutubeConfig(slidingLayout.getContext());
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    public void onDestroy() {
        youtubeConfig.closeVideo(((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager(), (Activity) slidingLayout.getContext());
        youtubeConfig= null;
        slideListenerSeleceted = null;
        slideListenerYoutube = null;
        if (webview != null) webview.clearCache(true);
        slidingLayout.getContext().unregisterReceiver(onDownloadComplete);
        unbinder.unbind();
    }

    public boolean onBackPressed() {
        if (slidingLayout != null &&
                (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            if(sliderUpDocument.getVisibility()==View.VISIBLE){
                closeSlidingDocument();
            }else {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            return false;
        } else {
           return true;
        }
    }

    public void showYoutubeActividad(String url, String nombre) {
        closeMultimedia();
        sliderUpDocument.setVisibility(View.GONE);
        sliderUpMultimedia.setVisibility(View.GONE);
        sliderUpYoutube.setVisibility(View.VISIBLE);
        slidingLayout.setScrollableView(list);
        slidingLayout.setTouchEnabled(true);
        if(slideListenerSeleceted!=null){
            slidingLayout.removePanelSlideListener(slideListenerSeleceted);
        }

        if(slideListenerYoutube!=null){
            slidingLayout.addPanelSlideListener(slideListenerYoutube);
            slideListenerSeleceted = slideListenerYoutube;
        }else {
            slideListenerYoutube = getSildetYoube();
            slidingLayout.addPanelSlideListener(slideListenerYoutube);
            slideListenerSeleceted = slideListenerYoutube;
        }

        //slidingLayout.setPanelHeight(minheight);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        //slidingLayout.setTouchEnabled(false);
        //slidingLayout.setTouchEnabled(false);
        txtTituloYoutube.setText(nombre);
        txtDescripcionYoutube.setText(url);

        slidingLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(youtubeConfig!=null){
                    youtubeConfig.initialize(YouTubeUrlParser.getVideoId(url), ((AppCompatActivity)slidingLayout.getContext()).getSupportFragmentManager(), R.id.youtube_layout, new YoutubeConfig.PlaybackEventListener() {
                        @Override
                        public void onPlaying() {
                            imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_pause_youtube));
                        }

                        @Override
                        public void onPaused() {
                            imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_play_youtube));
                        }

                        @Override
                        public void onLandscape() {

                        }

                        @Override
                        public void onPortrait() {

                        }
                    });
                }
                activityYoutubePreview = new ActividadYoutubePreview();
                FragmentTransaction transaction = ((AppCompatActivity)slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.cont_preview, activityYoutubePreview).commit();
            }
        },300);

    }

    public void showPreviewArchivo(String driveId, String nombreRecurso) {
        youtubeConfig.closeVideo(((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager(), (Activity) slidingLayout.getContext());
        closeMultimedia();
        ((AppCompatActivity)slidingLayout.getContext()).getIntent().putExtra("driveId",driveId);
        ((AppCompatActivity)slidingLayout.getContext()).getIntent().putExtra("nombreRecurso",nombreRecurso);
        sliderUpYoutube.setVisibility(View.GONE);
        sliderUpMultimedia.setVisibility(View.GONE);
        sliderUpDocument.setVisibility(View.VISIBLE);
        slidingLayout.setTouchEnabled(false);
        slidingLayout.setScrollableView(webview);
        sliderUpDocument.post(new Runnable() {
            @Override
            public void run() {

                //slidingLayout.setPanelHeight(minheight);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                if(slideListenerSeleceted!=null){
                    slidingLayout.removePanelSlideListener(slideListenerSeleceted);
                }
                try {
                    webview.loadUrl("https://drive.google.com/file/d/" + driveId + "/preview");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showMultimediaPlayer(String driveId, String nombreRecurso) {
        youtubeConfig.closeVideo(((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager(), (Activity) slidingLayout.getContext());
        ((AppCompatActivity)slidingLayout.getContext()).getIntent().putExtra("driveId",driveId);
        ((AppCompatActivity)slidingLayout.getContext()).getIntent().putExtra("nombreRecurso",nombreRecurso);
        sliderUpYoutube.setVisibility(View.GONE);
        sliderUpDocument.setVisibility(View.GONE);
        sliderUpMultimedia.setVisibility(View.VISIBLE);
        //slidingLayout.setPanelHeight(minheight);
        slidingLayout.setScrollableView(listExoPlayer);
        slidingLayout.setTouchEnabled(true);
        if(slideListenerSeleceted!=null){
            slidingLayout.removePanelSlideListener(slideListenerSeleceted);
        }

        if(slideListenerMultimedia!=null){
            slidingLayout.addPanelSlideListener(slideListenerMultimedia);
            slideListenerSeleceted = slideListenerMultimedia;
        }else {
            slideListenerMultimedia = getSildetMultimedia();
            slidingLayout.addPanelSlideListener(slideListenerMultimedia);
            slideListenerSeleceted = slideListenerMultimedia;
        }

        //slidingLayout.setPanelHeight(minheight);
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

        txtTituloExoPlayer.setText(nombreRecurso);
        txtDescripcionExoPlayer.setText("https://drive.google.com/file/d/"+driveId);

        exoPlayerFragment = new ExoPlayerFragment();
        FragmentTransaction transaction = ((AppCompatActivity)slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exo_player_layout, exoPlayerFragment).commit();

        String extencion = UtilsStorage.getExtencion(nombreRecurso);
        if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp3") || extencion.toLowerCase().contains("ogg") || extencion.toLowerCase().contains("wav"))) {
            exoPlayerFragment.uploadAudio(driveId, slidingLayout.getContext());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp4"))) {
            exoPlayerFragment.uploadMp4(driveId,slidingLayout.getContext());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("flv"))) {
            exoPlayerFragment.uploadFlv(driveId,slidingLayout.getContext());
        }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mkv"))) {
            exoPlayerFragment.uploadMkv(driveId, slidingLayout.getContext());
        }else {
            exoPlayerFragment.uploadArchivo(driveId, slidingLayout.getContext());
        }

        activityYoutubePreview = new ActividadYoutubePreview();
        FragmentTransaction transaction2 = ((AppCompatActivity)slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.cont_preview_exo_player, activityYoutubePreview).commit();

    }

    public void onConfigurationChanged(Configuration newConfig) {
        youtubeConfig.onConfigurationChanged(newConfig);
        if(exoPlayerFragment!=null)exoPlayerFragment.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.btn_close_youtube)
    public void onbtnCloseYoutubeClicked() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @OnClick(R.id.btn_action_youtube)
    public void onBtnActionYoutubeClicked() {
        youtubeConfig.onStartOrPaused();
    }


    private SlidingUpPanelLayout.PanelSlideListener getSildetYoube(){
        return new SlidingUpPanelLayout.PanelSlideListener(){
            float slideOffset = 0;
            private int maxheigth = 0;
            private int  minheight =  slidingLayout.getContext().getResources().getDimensionPixelSize(R.dimen.player_minimized_height);//110
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                contentYoutubeDetalle.setAlpha(Math.abs(slideOffset - 1));
                slideOffset = UtilsPortalAlumno.formatearDecimales((double) slideOffset, 4).floatValue();
                if (this.slideOffset != slideOffset) {
                    Log.d(TAG, "onPanelSlide slideOffset: " + slideOffset);
                    this.slideOffset = slideOffset;
                    if (slideOffset < 1 && slideOffset > 0) {
                        float y = (slideOffset * (maxheigth - minheight)) + minheight;
                        Log.d(TAG, "onPanelSlide result : " + (int) y);
                        youtubeLayout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        youtubeLayout.getLayoutParams().height = (int) y;
                        youtubeLayout.requestLayout();

                    }
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                // Log.d(TAG, "onPanelStateChanged view "+panel.getClass().getSimpleName());
                Log.d(TAG, "onPanelStateChanged newState: " + newState);
                Log.d(TAG, "onPanelStateChanged previousState: " + previousState + "  newState: " + newState);
                if (previousState == SlidingUpPanelLayout.PanelState.HIDDEN && newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    contentYoutubeDetalle.setVisibility(View.GONE);
                } else {
                    contentYoutubeDetalle.setVisibility(View.VISIBLE);
                }
                switch (newState) {
                    case EXPANDED:
                        youtubeLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        youtubeLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        youtubeLayout.requestLayout();
                        youtubeConfig.showButton();
                        youtubeLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "onPanelSlide slideOffset height: " + youtubeLayout.getHeight());
                                maxheigth = youtubeLayout.getHeight();
                            }
                        });
                        break;
                    case COLLAPSED:
                        youtubeLayout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        youtubeLayout.getLayoutParams().height = minheight;
                        youtubeLayout.requestLayout();
                        youtubeConfig.hideButton();
                        break;
                    case DRAGGING:

                        break;
                    case HIDDEN:
                        if(activityYoutubePreview!=null){
                            FragmentTransaction trans = ((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
                            trans.remove(activityYoutubePreview);
                            trans.commit();
                        }

                        youtubeConfig.closeVideo(((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager(), (Activity) slidingLayout.getContext());
                        youtubeLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        youtubeLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        break;
                }
            }
        };
    }

    //#region Archivos Drive
    private void setupWebView() {

        //webview.getSettings().setBuiltInZoomControls(true);
        // webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
        //webview.getSettings().setAppCacheEnabled(true);
        //webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        // String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        //webview.getSettings().setUserAgentString(newUA);
        webview.clearCache(true);
        webview.setBackgroundColor(Color.parseColor("#BBBBBB"));
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressBarArchivo.setVisibility(View.VISIBLE);
                }
                if (progress == 100) {
                    progressBarArchivo.setVisibility(View.GONE);
                }
            }

        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                try {
                    String url1 = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
                    webview.loadUrl(url1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(webView, url);
                try {
                    String url1 = "javascript:(function() {" + "document.querySelector('[role=\"toolbar\"]').remove();})()";
                    webview.loadUrl(url1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // open in Webview
                if (url.contains("android_asset")) {
                    // Can be clever about it like so where myshost is defined in your strings file
                    // if (Uri.parse(url).getHost().equals(getString(R.string.myhost)))
                    return false;
                }
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                slidingLayout.getContext().startActivity(intent);
                return true;
            }

        });

    }

    @OnClick({R.id.msg_succes_progress_document, R.id.msg_progress_document, R.id.backPrincipal_document, R.id.btnOpen_document,R.id.btnDownload_document})
    public void onMsgDocumentClicked(View view) {
        Bundle bundle = ((AppCompatActivity)slidingLayout.getContext()).getIntent().getExtras();
        switch (view.getId()) {
            case R.id.msg_succes_progress_document:
                try {
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(descargaId);
                    DownloadManager downloadManager = (DownloadManager) slidingLayout.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                            String fileUri = c.getString(fileUriIdx);
                            try {
                                if (!TextUtils.isEmpty(fileUri)) {
                                    OpenIntents.openFile(FileProvider.getUriForFile(slidingLayout.getContext(), slidingLayout.getContext().getPackageName() + ".provider", new File(Uri.parse(fileUri).getPath())), slidingLayout.getContext());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(slidingLayout.getContext(), slidingLayout.getContext().getResources().getString(R.string.cannot_open_file), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case R.id.msg_progress_document:

                break;
            case R.id.backPrincipal_document:
                closeSlidingDocument();
                break;
            case R.id.btnOpen_document:
                // open rest of URLS in default browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/" + bundle.getString("driveId") + "/view"));
                ((AppCompatActivity)youtubeLayout.getContext()).startActivity(intent);
                break;
            case R.id.btnDownload_document:
                String dowload = "https://drive.google.com/uc?export=download&id=" +  bundle.getString("driveId");
                String archivoPreview = bundle.getString("nombreRecurso");
                try {
                    DownloadManager.Request r = new DownloadManager.Request(Uri.parse(dowload));
                    r.setTitle(archivoPreview);
                    r.setDescription(youtubeLayout.getContext().getResources().getString(R.string.app_name));
                    r.setMimeType(UtilsStorage.getMimeType(archivoPreview));
                    // This put the download in the same Download dir the browser uses
                    r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, archivoPreview);

                    // When downloading music and videos they will be listed in the player
                    // (Seems to be available since Honeycomb only)
                    r.allowScanningByMediaScanner();

                    // Notify user when download is completed
                    // (Seems to be available since Honeycomb only)
                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    // Start download
                    DownloadManager dm = (DownloadManager)youtubeLayout.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    descargaId = dm.enqueue(r);
                    msgProgressDocument.setVisibility(View.VISIBLE);
                    msgSuccesProgressDocument.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    ((AppCompatActivity)youtubeLayout.getContext()).startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dowload)));
                }
                break;
        }
    }
    private void closeSlidingDocument() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        webview.post(new Runnable() {
            @Override
            public void run() {
                webview.loadUrl("about:blank");
            }
        });
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                //Fetching the download id received with the broadcast
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //Checking if the received broadcast is for our enqueued download by matching download id
                if(descargaId==id){
                    CheckDwnloadStatus(id);
                }

            }

        }
    };

    private void CheckDwnloadStatus(long id){
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        DownloadManager downloadManager = (DownloadManager) slidingLayout.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor cursor = null;
        if(downloadManager!=null){
            cursor = downloadManager.query(query);
        }
        if(cursor==null||cursor.getCount()==0){
            //presenter.canceledDownload(id);
            msgProgressDocument.setVisibility(View.GONE);
            msgSuccesProgressDocument.setVisibility(View.GONE);
        }else {
            if(cursor.moveToFirst()){
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                int reason = cursor.getInt(columnReason);

                switch(status){
                    case DownloadManager.STATUS_FAILED:
                        String failedReason = "";
                        switch(reason){
                            case DownloadManager.ERROR_CANNOT_RESUME:
                                failedReason = "ERROR_CANNOT_RESUME";
                                break;
                            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                                failedReason = "ERROR_DEVICE_NOT_FOUND";
                                break;
                            case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                                failedReason = "ERROR_FILE_ALREADY_EXISTS";
                                break;
                            case DownloadManager.ERROR_FILE_ERROR:
                                failedReason = "ERROR_FILE_ERROR";
                                break;
                            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                                failedReason = "ERROR_HTTP_DATA_ERROR";
                                break;
                            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                                failedReason = "ERROR_INSUFFICIENT_SPACE";
                                break;
                            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                                failedReason = "ERROR_TOO_MANY_REDIRECTS";
                                break;
                            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                                failedReason = "ERROR_UNHANDLED_HTTP_CODE";
                                break;
                            case DownloadManager.ERROR_UNKNOWN:
                                failedReason = "ERROR_UNKNOWN";
                                break;
                        }
                        Log.d(TAG, "FAILED: " + failedReason);
                        downloadManager.remove(id);
                        //presenter.finishedDownload(id);
                        msgProgressDocument.setVisibility(View.GONE);
                        msgSuccesProgressDocument.setVisibility(View.VISIBLE);
                        break;
                    case DownloadManager.STATUS_PAUSED:
                        String pausedReason = "";
                        switch(reason){
                            case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                                pausedReason = "PAUSED_QUEUED_FOR_WIFI";
                                break;
                            case DownloadManager.PAUSED_UNKNOWN:
                                pausedReason = "PAUSED_UNKNOWN";
                                break;
                            case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                                pausedReason = "PAUSED_WAITING_FOR_NETWORK";
                                break;
                            case DownloadManager.PAUSED_WAITING_TO_RETRY:
                                pausedReason = "PAUSED_WAITING_TO_RETRY";
                                break;
                        }
                        Log.d(TAG, "PAUSED: " + pausedReason);
                        break;
                    case DownloadManager.STATUS_PENDING:
                        Log.d(TAG, "PENDING");
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        Log.d(TAG, "RUNNING");
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        Log.d(TAG, "SUCCESSFUL");
                        //presenter.finishedDownload(id);
                        msgProgressDocument.setVisibility(View.GONE);
                        msgSuccesProgressDocument.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }

    }

    //#endregion

    //#region Multimedia
    private SlidingUpPanelLayout.PanelSlideListener getSildetMultimedia(){
        return new SlidingUpPanelLayout.PanelSlideListener(){
            //float slideOffset = 0;
            private int maxheigth = 0;
            private int maxwidth = 0;
            private int  minheight =  slidingLayout.getContext().getResources().getDimensionPixelSize(R.dimen.player_minimized_height);//110
            private int  minwidth =  slidingLayout.getContext().getResources().getDimensionPixelSize(R.dimen.player_minimized_width);//110
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                contentExoPlayerDetalle.setAlpha(Math.abs(slideOffset - 1));
                Log.d(TAG, "onPanelSlide slideOffset: " + slideOffset);
                if (slideOffset < 1 && slideOffset > 0) {
                    float y = (slideOffset * (maxheigth - minheight)) + minheight;
                    float x = (slideOffset * (maxwidth - minwidth)) + minwidth;
                    Log.d(TAG, "onPanelSlide result : " + (int) y);
                    exoPlayerLayout.getLayoutParams().width = (int)x;
                    exoPlayerLayout.getLayoutParams().height = (int) y;
                    exoPlayerLayout.requestLayout();
                    if(exoPlayerView!=null)exoPlayerView.requestLayouts();
                    //if(exoPlayerFragment!=null&exoPlayerFragment.getView()!=null)exoPlayerFragment.getView().requestLayout();
                }else {

                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                // Log.d(TAG, "onPanelStateChanged view "+panel.getClass().getSimpleName());
                Log.d(TAG, "onPanelStateChanged newState: " + newState);
                Log.d(TAG, "onPanelStateChanged previousState: " + previousState + "  newState: " + newState);
                if (previousState == SlidingUpPanelLayout.PanelState.HIDDEN && newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
                    contentExoPlayerDetalle.setVisibility(View.GONE);
                } else {
                    contentExoPlayerDetalle.setVisibility(View.VISIBLE);
                }
                switch (newState) {
                    case EXPANDED:
                        exoPlayerLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        exoPlayerLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        exoPlayerLayout.requestLayout();
                       if(exoPlayerView!=null)exoPlayerView.requestLayouts();
                        if(exoPlayerView!=null)exoPlayerView.showButton();
                        //maxwidth = exoPlayerLayout.getWidth();
                        exoPlayerLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                maxwidth = exoPlayerLayout.getWidth();
                                maxheigth = exoPlayerLayout.getHeight();
                            }
                        });
                        break;
                    case COLLAPSED:
                        exoPlayerLayout.getLayoutParams().width = minwidth;
                        exoPlayerLayout.getLayoutParams().height = minheight;
                        exoPlayerLayout.requestLayout();
                        //if(exoPlayerFragment!=null&exoPlayerFragment.getView()!=null)exoPlayerFragment.getView().requestLayout();
                        if(exoPlayerView!=null)exoPlayerView.hideButton();
                        if(exoPlayerView!=null)exoPlayerView.requestLayouts();
                        break;
                    case DRAGGING:
                        //if(exoPlayerView!=null)exoPlayerView.showButton();
                        //maxwidth = exoPlayerLayout.getWidth();
                        break;
                    case HIDDEN:
                        closeMultimedia();
                        exoPlayerLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                        exoPlayerLayout.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        break;
                }
            }
        };
    }

    private void closeMultimedia() {

        if(activityYoutubePreview!=null){
            FragmentTransaction trans = ((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
            trans.remove(activityYoutubePreview);
            trans.commit();
        }
        activityYoutubePreview = null;
        //youtubeConfig.closeVideo(((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager(), (Activity) slidingLayout.getContext());
        if(exoPlayerFragment!=null){
            FragmentTransaction trans = ((AppCompatActivity) slidingLayout.getContext()).getSupportFragmentManager().beginTransaction();
            trans.remove(exoPlayerFragment);
            trans.commit();
        }
        exoPlayerFragment =null;
    }

    @OnClick(R.id.btn_close_exo_player)
    public void onbtnCloseExoPlayerClicked() {
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    @OnClick(R.id.btn_action_exo_player)
    public void onBtnActionExoPlayerClicked() {
       // youtubeConfig.onStartOrPaused();
        if(exoPlayerView!=null)exoPlayerView.onClickAction();
    }
    public void onPlaying() {
        if(imgActionExoPlayer!=null)imgActionExoPlayer.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_pause_youtube));
    }

    public void onPused() {
        if(imgActionExoPlayer!=null)imgActionExoPlayer.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_play_youtube));
    }
    public void attachView(ExoPlayerView exoPlayerView) {
        this.exoPlayerView = exoPlayerView;
    }

    public void onDestroyView(ExoPlayerView exoPlayerView) {
        this.exoPlayerView = null;
    }
    //#endregion

    public void setPresenter(TabSesionPresenter presenter) {

    }



}
