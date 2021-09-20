package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.lib.cardviewGesture.MovableCardView;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.AdjuntoAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.local.TareasLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote.RemoteMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.CompressImage;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DeleteArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.EntregarTareaFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetArchivoTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetRecuros;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.IsEntragadoTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFirebaseBaseTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFirebaseTareaAlumno;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UploadArchivoStorageFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UploadLinkFB;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.filterChooser.CallbackFilterChooserBottomDialog;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.filterChooser.FilterChooserBottomSheetDialog;
import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoActivity;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.YouTubeUrlParser;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TareaDescripcionActivity extends BaseActivity<TareasDecripcionView, TareaDescripcionPresenter> implements TareasDecripcionView, DownloadItemListener, CallbackFilterChooserBottomDialog, AdjuntoAdapter.Listener {
    private final static int REQUEST_CODE_DOC_Q = 2312;
    private static final int REQUEST_CODE_IMAGE_Q = 2314;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    NestedScrollView viewpager;
    @BindView(R.id.txt_fecha_tarea)
    TextView txtFechaTarea;
    @BindView(R.id.txt_titulo_fecha)
    TextView txtTituloFecha;
    @BindView(R.id.txt_titulo_tarea)
    TextView txtTituloTarea;
    @BindView(R.id.btn_comentario)
    ConstraintLayout btnComentario;
    @BindView(R.id.btn_comentario_private)
    ConstraintLayout btn_comentarioPrivate;
    @BindView(R.id.txt_descripcion_tarea)
    TextView txtDescripcionTarea;
    @BindView(R.id.rvRecurso)
    RecyclerView rvRecurso;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.card_view2)
    CardView cardView2;
    @BindView(R.id.img_spin)
    ImageView imgSpin;
    @BindView(R.id.txt_linea)
    TextView txtLinea;
    @BindView(R.id.txt_titulo_tu_trabajo)
    TextView txtTituloTuTrabajo;
    @BindView(R.id.img_comentario_clase)
    ImageView imgComentarioClase;
    @BindView(R.id.txt_comentario_clase)
    TextView txtComentarioClase;
    @BindView(R.id.txt_sin_entregar)
    TextView txtSinEntregar;
    @BindView(R.id.conten_entregar_archivo)
    NestedScrollView contenEntregarArchivo;
    @BindView(R.id.rc_adjunto)
    RecyclerView rcAdjunto;
    @BindView(R.id.progressBar9)
    ProgressBar progressBar9;
    @BindView(R.id.btn_adjuntar)
    ConstraintLayout btnAdjuntar;
    @BindView(R.id.txt_entrgado)
    TextView txtEntrgado;
    @BindView(R.id.cont_entrgado)
    CardView contEntrgado;
    @BindView(R.id.btn_entregar)
    CardView btnEntregar;
    @BindView(R.id.tipo_anular_entrega)
    CardView tipoAnularEntrega;
    @BindView(R.id.tipo_entrega)
    TextView tipoEntrega;
    @BindView(R.id.txt_anular_entrega)
    TextView txtAnularEntrega;
    @BindView(R.id.msg_error)
    CardView msgError;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.content_comentario_privado)
    ConstraintLayout contentComentarioPrivado;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.youtube_layout)
    FrameLayout youtubeLayout;
    @BindView(R.id.contentPlayer)
    MovableCardView contentPlayer;
    @BindView(R.id.progressBar13)
    ProgressBar progressBar13;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.conten_enviar)
    CardView contenEnviar;
    @BindView(R.id.cont_online)
    ConstraintLayout contOnline;

    private DownloadAdapter recursosTareaAdapter;
    private BottomSheetBehavior<LinearLayout> sheetBehavior;
    private AdjuntoAdapter adjuntoAdapter;
    private YoutubeConfig youtubeConfig;

    @Override
    protected String getTag() {
        return "TareaDescripcionActivity";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected TareaDescripcionPresenter getPresenter() {
        TareasMvpRepository tareasMvpRepository = new TareasMvpRepository(new TareasLocalDataSource(), new RemoteMvpDataSource(ApiRetrofit.getInstance()));
        return new TareaDescripcionPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetRecuros(tareasMvpRepository),
                new DowloadImageUseCase(tareasMvpRepository),
                new UpdateSuccesDowloadArchivo(tareasMvpRepository),
                new MoverArchivosAlaCarpetaTarea(tareasMvpRepository),
                new UpdateFirebaseTareaAlumno(tareasMvpRepository),
                new GetArchivoTareaAlumno(tareasMvpRepository),
                new AndroidOnlineImpl(this),
                new UploadArchivoStorageFB(tareasMvpRepository),
                new DeleteArchivoStorageFB(tareasMvpRepository),
                new EntregarTareaFB(tareasMvpRepository),
                new IsEntragadoTareaAlumno(tareasMvpRepository),
                new GetTarea(tareasMvpRepository),
                new UpdateFirebaseBaseTarea(tareasMvpRepository),
                new UploadLinkFB(tareasMvpRepository),
                new CompressImage(this));
    }

    @Override
    protected TareasDecripcionView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tarea_descripcion);
        ButterKnife.bind(this);
        setupToolbar();
        setupAdapter();
        setupBoothonSheet();
        desbloqOrientation();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupBoothonSheet() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            double SLIDEOFFSETHIDEN = 0.5f;

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(getTag(), "STATE_HIDDEN");
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(getTag(), "Close Sheet");
                        //toolbar.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(getTag(), "Expand Sheet");
                        //toolbar.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset == 0) {
                    imgSpin.setRotation(0);
                    cardView.setRadius(UtilsPortalAlumno.convertDpToPixel(16, TareaDescripcionActivity.this));
                    cardView2.setRadius(UtilsPortalAlumno.convertDpToPixel(16, TareaDescripcionActivity.this));
                    btn_comentarioPrivate.setVisibility(View.VISIBLE);
                    contenEntregarArchivo.setVisibility(View.GONE);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (slideOffset == 1) {
                    cardView.setRadius(UtilsPortalAlumno.convertDpToPixel(0, TareaDescripcionActivity.this));
                    cardView2.setRadius(UtilsPortalAlumno.convertDpToPixel(0, TareaDescripcionActivity.this));
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    imgSpin.setRotation(180);
                    btn_comentarioPrivate.setVisibility(View.GONE);
                    contenEntregarArchivo.setVisibility(View.VISIBLE);
                } else if (slideOffset > 0.1) {
                    btn_comentarioPrivate.setVisibility(View.GONE);
                    contenEntregarArchivo.setVisibility(View.VISIBLE);
                }
                //0.5 >= 0.8

            }
        });

    }

    private void setupAdapter() {
        rvRecurso.setLayoutManager(new LinearLayoutManager(this));
        recursosTareaAdapter = new DownloadAdapter(this, rvRecurso);
        rvRecurso.setAdapter(recursosTareaAdapter);
        rvRecurso.setHasFixedSize(false);
        rvRecurso.setNestedScrollingEnabled(false);

        rcAdjunto.setLayoutManager(new LinearLayoutManager(this));
        adjuntoAdapter = new AdjuntoAdapter(this);
        rcAdjunto.setAdapter(adjuntoAdapter);
        rcAdjunto.setHasFixedSize(false);
        rcAdjunto.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) rcAdjunto.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //change color of status bar
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#FAFAFA"));
        }
    }

    @Override
    protected ViewGroup getRootLayout() {
        return null;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar9;
    }

    @Override
    public void setNombreTarea(String nombre) {
        txtTituloTarea.setText(nombre);
    }

    @Override
    public void setDescripcionTarea(String descripicion) {
        txtDescripcionTarea.setText(descripicion);
    }

    @Override
    public void setFechaTarea(String horaEntrega) {
        txtFechaTarea.setText(horaEntrega);
    }

    @Override
    public void showListRecursos(List<RecursosUI> recursosUIS) {
        recursosTareaAdapter.setList(new ArrayList<>(recursosUIS));
    }

    @Override
    public void setUpdateProgress(RepositorioFileUi repositorioFileUi, int count) {
        recursosTareaAdapter.updateProgress(repositorioFileUi, count);
    }

    @Override
    public void setUpdate(RepositorioFileUi repositorioFileUi) {
        recursosTareaAdapter.update(repositorioFileUi);
    }

    @Override
    public void leerArchivo(String path) {
        try {
            if (!TextUtils.isEmpty(path))
                OpenIntents.openFile(FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(path)), this);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.cannot_open_file), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showVinculo(String url) {
        try {
            if(!TextUtils.isEmpty(url))url = url.trim();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al abrir el vínculo", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showYoutube(String url) {
        contentPlayer.setVisibility(View.VISIBLE);
        setupYoutubePlayer(url);
    }

    private void setupYoutubePlayer(String url) {
        if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(this);
        Log.d(getTag(), url);
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

    @Override
    public void changeTema(String color1, String color2, String color3) {
        //Drawable comentarioClase = ContextCompat.getDrawable(imgComentarioClase.getContext(), R.drawable.ic_comment);
        try {
            txtLinea.setBackgroundColor(Color.parseColor(color1));
            txtTituloTuTrabajo.setTextColor(Color.parseColor(color2));
            //comentarioClase.mutate().setColorFilter(Color.parseColor(color2), PorterDuff.Mode.SRC_ATOP);
            //imgComentarioClase.setImageDrawable(comentarioClase);
            //txtComentarioClase.setTextColor(Color.parseColor(color2));
            txtTituloTarea.setTextColor(Color.parseColor(color2));
            cardView.setCardBackgroundColor(Color.parseColor(color1));
            //txtSinEntregar.setTextColor(Color.parseColor(color3));
            btnEntregar.setCardBackgroundColor(Color.parseColor(color1));
            contEntrgado.setCardBackgroundColor(Color.parseColor(color1));
            txtAnularEntrega.setTextColor(Color.parseColor(color1));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showListaTareaAlumno(List<TareaArchivoUi> tareasUIList) {
        adjuntoAdapter.setList(tareasUIList);
    }

    @Override
    public void onClickDownload(RepositorioFileUi repositorioFileUi) {
        presenter.onClickDownload(repositorioFileUi);
    }

    @Override
    public void onClickClose(RepositorioFileUi repositorioFileUi) {
        presenter.onClickClose(repositorioFileUi);
    }

    @Override
    public void onClickArchivo(RepositorioFileUi repositorioFileUi) {
        presenter.onClickArchivo(repositorioFileUi);
    }

    @Override
    public void onClickOpenLinkArchivo(RepositorioFileUi repositorioFileUi, String clickedLink) {
        presenter.onClickOpenLinkArchivo(repositorioFileUi, clickedLink);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.btn_adjuntar)
    public void onViewClicked() {
        FilterChooserBottomSheetDialog filterChooserBottomSheetDialog = new FilterChooserBottomSheetDialog();
        filterChooserBottomSheetDialog.show(getSupportFragmentManager(), "FilterChooserBottomSheetDialog");
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClickAddLink() {
        showDialogAddLink();
    }

    @Override
    public void onClickAddFile() {
        presenter.onClickAddFile();
    }

    @Override
    public void onClickCamera() {
        presenter.onClickCamera();
    }

    @Override
    public void openCamera(ArrayList<String> photoSelected) {

        Options options = Options.init()
                .setCount(6)                                                   //Number of images to restict selection count
                .setFrontfacing(false);                                      //Front Facing camera on start
        if (photoSelected != null && !photoSelected.isEmpty()) {
            options
                    .setPreSelectedUrls(photoSelected);                             //Pre selected Image Urls
        }
        options
                .setMode(Options.Mode.All)                                      //Option to exclude videos
                //.setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath(getResources().getString(R.string.app_name) + "/images")//Custom Path For media Storage
                .setRequestCode(REQUEST_CODE_IMAGE_Q);                            //Request code for activity results

        Pix.start(this, options);
    }

    @Override
    public void addTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        adjuntoAdapter.add(tareaArchivoUi);
    }

    @Override
    public void remove(TareaArchivoUi tareaArchivoUi) {
        adjuntoAdapter.remove(tareaArchivoUi);
    }

    @Override
    public void update(TareaArchivoUi tareaArchivoUi) {
        adjuntoAdapter.update(tareaArchivoUi);
    }

    @Override
    public void changeFechaEntrega(boolean entregaAlumno, boolean retrasoEntrega) {
        if (entregaAlumno) {
            contEntrgado.setVisibility(View.VISIBLE);
            txtSinEntregar.setVisibility(View.GONE);
            tipoAnularEntrega.setVisibility(View.VISIBLE);
            tipoEntrega.setVisibility(View.GONE);
            btnAdjuntar.setVisibility(View.GONE);
            if (retrasoEntrega) {
                txtEntrgado.setText("Entregado con retraso");
            } else {
                txtEntrgado.setText("Entregado");
            }
        } else {
            contEntrgado.setVisibility(View.GONE);
            txtSinEntregar.setVisibility(View.VISIBLE);
            tipoAnularEntrega.setVisibility(View.GONE);
            tipoEntrega.setVisibility(View.VISIBLE);
            btnAdjuntar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void diabledButtons() {
        btnAdjuntar.setVisibility(View.GONE);
    }

    @Override
    public void showMessageTop(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    @Override
    public void showPreviewArchivo() {
       startActivity(new Intent(this, PreviewArchivoActivity.class));
    }

    @Override
    public void modoOnline() {
        msgError.setVisibility(View.GONE);
    }

    @Override
    public void modoOffline() {
        msgError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMultimediaPlayer() {
        startActivity(new Intent(this, PreviewArchivoActivity.class));
    }

    @Override
    public void showProgress2() {
        progressBar13.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress2() {
        progressBar13.setVisibility(View.GONE);
    }

    @Override
    public void onShowPickDoc() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent();
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_DOC_Q);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onClickCamera();
                } else {
                    Toast.makeText(this, "Aceptar los permisos para abrir la camara", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_IMAGE_Q) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            presenter.onResultCamara(returnValue);
        }else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DOC_Q){
            Uri uri = data.getData();
            presenter.onResultDoc(uri, getNombre(uri, this));
        }
    }


    @Override
    public void onClickActionTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        presenter.onClickActionTareaArchivo(tareaArchivoUi);
    }

    @Override
    public void onClickRemoveTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        presenter.onClickRemoveTareaArchivo(tareaArchivoUi);
    }

    @Override
    public void onClickOpenTareaArchivo(TareaArchivoUi tareaArchivoUi) {
        presenter.onClickOpenTareaArchivo(tareaArchivoUi);
    }

    @Override
    public void oClickOpenLink(TareaArchivoUi tareaArchivoUi, String clickedLink) {
        presenter.onClickOpenLink(tareaArchivoUi, clickedLink);
    }

    @OnClick(R.id.btn_comentario_private)
    public void onBtnComentarioPrivateClicked() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.btn_entregar)
    public void onBtnEntregarClicked() {
        presenter.onBtnEntregarClicked();
    }

    @OnClick(R.id.msg_error)
    public void onMsgErrorClicked() {
        presenter.onClickMsgError();
    }

    @OnClick(R.id.btn_close_youtube)
    public void onbtnCloseYoutubeClicked() {
        contentPlayer.setVisibility(View.GONE);
        if (youtubeConfig != null) youtubeConfig.closeVideo(getSupportFragmentManager(), this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        youtubeConfig = null;
    }

    public static String getNombre(Uri uri, Context context) {
        String displayName = null;
        try {
            Cursor cursor = context.getContentResolver()
                    .query(uri, null, null, null, null, null);
            // moveToFirst() returns false if the cursor has 0 rows.  Very handy for
            // "if there's anything to look at, look at it" conditionals.
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
                cursor.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return displayName;
    }

    public void showDialogAddLink(){
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.alert_dialog_add_link, null);
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        aBuilder.setView(view);

        EditText editText = (EditText)view.findViewById(R.id.edi_link);
        EditText editTextDescrip = (EditText)view.findViewById(R.id.edi_nombre);

        aBuilder.setCancelable(false)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText!=null){
                    String desripcion = editTextDescrip.getText().toString();
                    String vinculo = editText.getText().toString();
                    if(TextUtils.isEmpty(desripcion)){
                        Toast.makeText(editText.getContext(), "Ingrese un título valido", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(vinculo)||!isURL(vinculo)){
                        Toast.makeText(editText.getContext(), "Ingrese un vínculo valido", Toast.LENGTH_SHORT).show();
                    }else {
                        presenter.onClickAddLink(desripcion, vinculo);
                        if(alertDialog!=null)alertDialog.dismiss();
                    }
                }else {
                    if(alertDialog!=null)alertDialog.dismiss();
                }
            }
        });
    }

    public boolean isURL(String text) {
        if (!text.startsWith("http")) {
            return false;
        }
        return true;
    }
}
