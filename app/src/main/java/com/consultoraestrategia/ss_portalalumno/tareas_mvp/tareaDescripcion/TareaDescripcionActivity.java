package com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.local.TareasLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote.RemoteMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetRecuros;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TareaDescripcionActivity extends BaseActivity<TareasDecripcionView, TareaDescripcionPresenter> implements TareasDecripcionView, DownloadItemListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    NestedScrollView viewpager;
    @BindView(R.id.txt_fecha_tarea)
    TextView txtFechaTarea;
    @BindView(R.id.txt_titulo_tarea)
    TextView txtTituloTarea;
    @BindView(R.id.btn_comentario)
    ConstraintLayout btnComentario;
    @BindView(R.id.txt_descripcion_tarea)
    TextView txtDescripcionTarea;
    @BindView(R.id.rvRecurso)
    RecyclerView rvRecurso;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    private DownloadAdapter recursosTareaAdapter;
    private BottomSheetBehavior<LinearLayout> sheetBehavior;

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
        TareasMvpRepository tareasMvpRepository = new TareasMvpRepository(new TareasLocalDataSource(), new RemoteMvpDataSource(this));
        return new TareaDescripcionPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetRecuros(tareasMvpRepository),
                new DowloadImageUseCase(tareasMvpRepository),
                new UpdateSuccesDowloadArchivo(tareasMvpRepository),
                new MoverArchivosAlaCarpetaTarea(tareasMvpRepository));
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

    private void setupBoothonSheet() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);

        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            double SLIDEOFFSETHIDEN = -0.9f;
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(getTag(), "STATE_HIDDEN");
                        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        Log.d(getTag(), "Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        Log.d(getTag(), "Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(getTag(), "slideOffset: "+slideOffset);
                if (SLIDEOFFSETHIDEN >= slideOffset) sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    private void setupAdapter() {
        rvRecurso.setLayoutManager(new LinearLayoutManager(this));
        recursosTareaAdapter = new DownloadAdapter(this, rvRecurso);
        rvRecurso.setAdapter(recursosTareaAdapter);
        rvRecurso.setHasFixedSize(false);
        rvRecurso.setNestedScrollingEnabled(false);
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
        return null;
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
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al abrir el vínculo", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showYoutube(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "Error al abrir el vínculo de youtube", Toast.LENGTH_SHORT).show();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
