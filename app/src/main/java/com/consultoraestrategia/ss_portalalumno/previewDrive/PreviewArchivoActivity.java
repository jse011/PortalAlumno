package com.consultoraestrategia.ss_portalalumno.previewDrive;


import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
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
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineSimpleImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewFragment;
import com.consultoraestrategia.ss_portalalumno.previewDrive.archivo.ArchivoPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepository;
import com.consultoraestrategia.ss_portalalumno.previewDrive.data.source.PreviewDriveRepositoyImpl;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreview2Fragment;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreviewView;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetDriveTareaTemporal;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveEvidencia;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.GetIdDriveTarea;
import com.consultoraestrategia.ss_portalalumno.previewDrive.useCase.SaveDriveTareaTemporal;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.util.IdGenerator;
import com.consultoraestrategia.ss_portalalumno.util.RoundedView;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewArchivoActivity extends BasePrevArchivoActivity implements PreviewArchivoView, LifecycleImpl.LifecycleListener {
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
                new GetDriveTareaTemporal(previewDriveRepository),
                new SaveDriveTareaTemporal(previewDriveRepository),
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
        archivoPreview = archivoPreview.replaceAll(" ", "%20");
        archivoPreview = archivoPreview.replaceAll("/", "");
        String download = "https://drive.google.com/uc?export=download&id=" + idDrive;
        try {

            archivoPreview = IdGenerator.generateId()+"."+ UtilsStorage.getExtencion(archivoPreview);
            presenter.onStarDownload(downloadManager(download, archivoPreview), archivoPreview);

            //presenter.onStarDownload(downloadManager(download, archivoPreview), archivoPreview);
        }catch (Exception e) {
            e.printStackTrace();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(download)));
        }
    }

    private long downloadManager(String download, String archivoPreview){
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(download));
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
        return dm.enqueue(r);
    }

    @Override
    public void hideDownloadComplete() {
        msgSuccesProgress.setVisibility(View.GONE);
    }

    @Override
    public void openDownloadFile(long downloadID) {
        /*DownloadManager.Query query = new DownloadManager.Query();
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
                        Uri localUri = Uri.parse(fileUri);

                        OpenIntents.openFile(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", ), this);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, getString(R.string.cannot_open_file), Toast.LENGTH_SHORT).show();
                }
            }
        }*/

        openDownloadedAttachment( this, downloadID);

    }

    /**
     * Used to open the downloaded attachment.
     *
     * @param context    Content.
     * @param downloadId Id of the downloaded file to open.
     */
    private void openDownloadedAttachment(final Context context, final long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
                openDownloadedAttachment(context, downloadLocalUri, downloadMimeType);
            }
        }
        cursor.close();
    }

    /**
     * Used to open the downloaded attachment.
     * <p/>
     * 1. Fire intent to open download file using external application.
     *
     * 2. Note:
     * 2.a. We can't share fileUri directly to other application (because we will get FileUriExposedException from Android7.0).
     * 2.b. Hence we can only share content uri with other application.
     * 2.c. We must have declared FileProvider in manifest.
     * 2.c. Refer - https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     *
     * @param context            Context.
     * @param path      Uri of the downloaded attachment to be opened.
     * @param attachmentMimeType MimeType of the downloaded attachment.
     */
    private void openDownloadedAttachment(final Context context, String path, final String attachmentMimeType) {
        if(!TextUtils.isEmpty(path)) {
            // Get Content Uri.
            Uri attachmentUri = getUriFromPath(path);

            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            openAttachmentIntent.setDataAndType(attachmentUri,attachmentMimeType);
            openAttachmentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //openAttachmentIntent.putExtra(Intent.EXTRA_STREAM, attachmentUri);

            //openAttachmentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
                context.startActivity(openAttachmentIntent);
            } catch (ActivityNotFoundException e) {
                //Toast.makeText(context, context.getString(R.string.unable_to_open_file), Toast.LENGTH_LONG).show();
                try {

                    /*Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                            .setText(UtilsStorage.getNombre(attachmentUri.getPath()))
                            .setType(UtilsStorage.getMimeType(attachmentUri.getPath()))
                            .setStream(newUri)
                            .getIntent()
                            .setPackage("com.google.android.apps.docs");

                    startActivity(shareIntent);*/
                    startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                }catch (Exception e1){
                    e1.printStackTrace();
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
    public void openForceDrive(String driveId, String archivoPreview) {
        try {
            /*String download = "";
            Uri pdfUri = Uri.parse(download);
            Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                    .setText(archivoPreview)
                    .setType(UtilsStorage.getMimeType(archivoPreview))
                    .setStream(pdfUri )
                    .getIntent()
                    .setPackage("com.google.android.apps.docs");
            startActivity(shareIntent);*/

            /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://drive.google.com/file/d/" + driveId + "/preview"));
            intent.setPackage("com.google.android.apps.docs");
            startActivity(intent);*/

            // location = "/sdcard/my_folder";
            //startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

            dowloadArchivo(driveId, archivoPreview);



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void openDownloadFile(String nombreArchivoLocal) {

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + nombreArchivoLocal;
        openDownloadedAttachment(this, path, UtilsStorage.getMimeType(nombreArchivoLocal));
        Log.d(getTag(), "openDownloadFile: " + path);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
