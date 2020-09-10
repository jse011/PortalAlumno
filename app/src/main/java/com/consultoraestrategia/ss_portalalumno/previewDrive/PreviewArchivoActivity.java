package com.consultoraestrategia.ss_portalalumno.previewDrive;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineSimpleImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewFragment;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepositoyImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreview2Fragment;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveEvidencia;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveTarea;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewArchivoActivity extends BaseActivity<PreviewArchivoView, PreviewArchivoPresenter> implements PreviewArchivoView, LifecycleImpl.LifecycleListener {
    @BindView(R.id.backPrincipal)
    FrameLayout backPrincipal;
    @BindView(R.id.btnDownload)
    FrameLayout btnDownload;
    @BindView(R.id.btnOpen)
    FrameLayout btnOpen;
    @BindView(R.id.msg_succes_progress)
    CardView msgSuccesProgress;
    @BindView(R.id.msg_progress)
    CardView msgProgress;
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    private YoutubeConfig youtubeConfig;

    @Override
    protected String getTag() {
        return "PreviewArchivoActivity";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected PreviewArchivoPresenter getPresenter() {
        PreviewDriveRepository previewDriveRepository = new PreviewDriveRepositoyImpl(ApiRetrofit.getInstance());
        return new PreviewArchivoPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetIdDriveTarea(previewDriveRepository),
                new GetIdDriveEvidencia(previewDriveRepository),
                new AndroidOnlineSimpleImpl(this));
    }

    @Override
    protected PreviewArchivoView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.preview_tarea_alumno);
        ButterKnife.bind(this);
        setupToolbar();
        desbloqOrientation();
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new LifecycleImpl(0, this), true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return null;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    private void setupToolbar() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = this.getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.parseColor("#FAFAFA"));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.BLACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openBrowser(String idDrive) {
        // open rest of URLS in default browser
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/" + idDrive + "/preview"));
        startActivity(intent);
    }

    @Override
    public void dowloadArchivo(String idDrive, String archivoPreview) {
        String dowload = "https://drive.google.com/uc?export=download&id=" + idDrive;
        try {
            DownloadManager.Request r = new DownloadManager.Request(Uri.parse(dowload));
            r.setTitle(archivoPreview);
            r.setDescription(getResources().getString(R.string.app_name));
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
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            presenter.onStarDownload(dm.enqueue(r));

        } catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dowload)));
        }
    }

    @Override
    public void hideDownloadComplete() {
        msgSuccesProgress.setVisibility(View.GONE);
    }

    @Override
    public void openDownloadFile(long downloadID) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadID);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
            if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                int fileUriIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String fileUri = c.getString(fileUriIdx);
                try {
                    if (!TextUtils.isEmpty(fileUri)) {
                        OpenIntents.openFile(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(Uri.parse(fileUri).getPath())), this);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, getString(R.string.cannot_open_file), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void showYoutube(String youtube) {
        if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(this);
        Log.d(getTag(), youtube);
        if (youtubeConfig != null) {
            contentContainer.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            contentContainer.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            youtubeConfig.initialize(YouTubeUrlParser.getVideoId(youtube), getSupportFragmentManager(), R.id.contentContainer, new YoutubeConfig.PlaybackEventListener() {
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
                    backPrincipal.setVisibility(View.GONE);
                }

                @Override
                public void onPortrait() {
                    backPrincipal.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void showMultimendia() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, new MultimediaPreview2Fragment())
                .commit();
    }

    @Override
    public void showDocument() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentContainer, new ArchivoPreviewFragment())
                .commit();
    }

    @Override
    public void showButons() {
        btnDownload.setVisibility(View.VISIBLE);
        btnOpen.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButons() {
        btnDownload.setVisibility(View.GONE);
        btnOpen.setVisibility(View.GONE);
    }

    @Override
    public void showDowloadProgress() {
        msgProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDowloadProgress() {
        msgProgress.setVisibility(View.GONE);
    }

    @Override
    public void showDownloadComplete() {
        msgSuccesProgress.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.backPrincipal, R.id.btnDownload, R.id.btnOpen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backPrincipal:
                finish();
                break;
            case R.id.btnDownload:
                presenter.onClickBtnDownload();
                break;
            case R.id.btnOpen:
                presenter.onClickBtnOpen();
                break;
        }
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                //Fetching the download id received with the broadcast
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //Checking if the received broadcast is for our enqueued download by matching download id
                CheckDwnloadStatus(id);
            }

        }
    };

    private void CheckDwnloadStatus(long id) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(id);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        Cursor cursor = null;
        if (downloadManager != null) {
            cursor = downloadManager.query(query);
        }
        if (cursor == null || cursor.getCount() == 0) {
            presenter.canceledDownload(id);
        } else {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int status = cursor.getInt(columnIndex);
                int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                int reason = cursor.getInt(columnReason);

                switch (status) {
                    case DownloadManager.STATUS_FAILED:
                        String failedReason = "";
                        switch (reason) {
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
                        Log.d(getTag(), "FAILED: " + failedReason);
                        downloadManager.remove(id);
                        presenter.finishedDownload(id);
                        break;
                    case DownloadManager.STATUS_PAUSED:
                        String pausedReason = "";
                        switch (reason) {
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
                        Log.d(getTag(), "PAUSED: " + pausedReason);
                        break;
                    case DownloadManager.STATUS_PENDING:
                        Log.d(getTag(), "PENDING");
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        Log.d(getTag(), "RUNNING");
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        Log.d(getTag(), "SUCCESSFUL");
                        presenter.finishedDownload(id);
                        break;
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }

    @OnClick(R.id.msg_succes_progress)
    public void onMsgSuccesslicked() {
        presenter.onClickMsgSucess();
    }

    @Override
    public void onChildsFragmentViewCreated() {

    }

    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        if (f instanceof ArchivoPreviewView) {
            presenter.onDestroyArchivoPreviewView();
        } else if (f instanceof MultimediaPreviewView) {
            presenter.onDestroyMultimediaPreviewView();
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if (f instanceof ArchivoPreviewView) {
            presenter.attachView((ArchivoPreviewView) f);
            ((ArchivoPreviewView) f).setPresenter(presenter);
        } else if (f instanceof MultimediaPreviewView) {
            presenter.attachView((MultimediaPreviewView) f);
            ((MultimediaPreviewView) f).setPresenter(presenter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
