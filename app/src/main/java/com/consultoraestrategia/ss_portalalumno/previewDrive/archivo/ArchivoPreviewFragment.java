package com.consultoraestrategia.ss_portalalumno.previewDrive.archivo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ArchivoPreviewFragment extends Fragment implements ArchivoPreviewView {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar11)
    ProgressBar progressBar11;
    @BindView(R.id.exo_reload)
    ImageView exoReload;
    private Unbinder unbinder;
    private PreviewArchivoPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_archivo_preview, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setupWebView();
        return rootView;
    }

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
                if (presenter != null) presenter.onProgressChanged(progress);
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
                startActivity(intent);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webview.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webview != null) webview.clearCache(true);
        unbinder.unbind();
    }

    @Override
    public void uploadArchivo(String idDrive) {
        try {
            webview.loadUrl("https://drive.google.com/file/d/" + idDrive + "/view");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showProgress() {
        if (progressBar11 != null) progressBar11.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBar11 != null) progressBar11.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(PreviewArchivoPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showBtnReload() {
        exoReload.setVisibility(View.VISIBLE);
    }
}
