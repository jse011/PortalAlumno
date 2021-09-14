package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.principal.ActividadesFragment;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.ViewpagerAdapter;
import com.consultoraestrategia.ss_portalalumno.colaborativa.ColaborativaFragment;
import com.consultoraestrategia.ss_portalalumno.evidencia.EvidenciaFragment;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.global.ICRMEduListener;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.instrumento.lista.InstrumentoListaFragment;
import com.consultoraestrategia.ss_portalalumno.pregunta.lista.PreguntaFragment;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorio;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.data.source.TabSesionesRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionActividadView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionCallback;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionColaborativaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionEvidenciaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionInstrumentoView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionPreguntaView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp.SlidingUpPanelViewHolder;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp.exoplayer.ExoPlayerCallback;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.sildingUp.exoplayer.ExoPlayerView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseColaborativa;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseInstrumento;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebasePreguntas;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateFirebaseReunionVirtual;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateGrabacionesSalaVirtual;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateInstrumentoEncuestaSesion;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.usecase.UpdateReunionVirtualAlumno;
import com.consultoraestrategia.ss_portalalumno.userbloqueo.UserBloqueoActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabSesionesActivity2 extends BaseActivity<TabSesionView, TabSesionPresenter> implements TabSesionView, BaseFragmentListener, TabSesionCallback, LifecycleImpl.LifecycleListener, ExoPlayerCallback, ICRMEduListener {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.img_curso)
    ImageView imgCurso;
    @BindView(R.id.con_curso)
    CardView conCurso;
    @BindView(R.id.txt_subtitulo)
    TextView txtSubtitulo;
    @BindView(R.id.txt_titulo)
    TextView txtTitulo;
    @BindView(R.id.imageView3)
    ImageView imageView3;

    @BindView(R.id.txt_sin_senial)
    ImageView txtSinSenial;

    @BindView(R.id.msg_error)
    CardView msgError;
    @BindView(R.id.txt_error)
    TextView txtError;


    private ToolsTitleToolbar toolsTitleToolbar;
    private SlidingUpPanelViewHolder slidingUp;


    @Override
    protected String getTag() {
        return "TabSesionesActivity2TAG";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected TabSesionPresenter getPresenter() {
        TabSesionesRepositorio tabSesionesRepositorio = new TabSesionesRepositorioImpl();
        return new TabSesionPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new UpdateFirebaseInstrumento(tabSesionesRepositorio),
                new UpdateFirebasePreguntas(tabSesionesRepositorio),
                new AndroidOnlineImpl(this),
                new UpdateFirebaseColaborativa(tabSesionesRepositorio),
                new UpdateFirebaseReunionVirtual(tabSesionesRepositorio),
                new UpdateInstrumentoEncuestaSesion(tabSesionesRepositorio),
                new UpdateReunionVirtualAlumno(tabSesionesRepositorio),
                new UpdateGrabacionesSalaVirtual(tabSesionesRepositorio));
    }

    @Override
    protected TabSesionView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    private String video_url = "https://drive.google.com/open?id=1ThYBj7gG3cwSxYvpbcq-Bu44ezXrn6CK";

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tabs_sesiones_2);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupToolbar();
        setupAdapter();
        desbloqOrientation();
        setupSilingPanel();
        iCRMEdu.getiCRMEdu(this).addiCRMEduListener(this);
        /*
        webview.requestFocus();

        webview.getSettings().setJavaScriptEnabled(true);
        //webview.getSettings().setGeolocationEnabled(true);
        //webview.setSoundEffectsEnabled(true);
        // Settings so page loads zoomed-out all the way.
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setBuiltInZoomControls(true);
        String imgView = "<iframe src='" + "https://drive.google.com/file/d/" + "12vQQvb58X9OzGOpGMov6lLepTs6Jdtmo"+ "/preview' width='100%' height='100%' frameborder='0' allowfullscreen></iframe>";

        webview.loadData("<html><body>"+imgView+"</body></html>",
                "text/html", "UTF-8");
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    //progressDialog.show();
                }
                if (progress == 100) {
                    //progressDialog.dismiss();
                }
            }
        });*/
    }

    private void setupSilingPanel() {
        slidingUp = new SlidingUpPanelViewHolder(this);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidingUp.setPresenter(presenter);
    }

    private void setupAdapter() {
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), 0, this);
        viewpagerAdapter.addFragment(new ActividadesFragment(), "ACTIVIDADES");
        viewpagerAdapter.addFragment(new ColaborativaFragment(), "COLABORATIVA");
        //viewpagerAdapter.addFragment(FragmentTareasSesiones.newInstanceSesion(), "TAREA");
        viewpagerAdapter.addFragment(new InstrumentoListaFragment(), "EVALUACIÓN");
        viewpagerAdapter.addFragment(new PreguntaFragment(), "PREGUNTAS");
        viewpagerAdapter.addFragment(new EvidenciaFragment(), "EVIDENCIA");

        viewpager.setOffscreenPageLimit(7);
        viewpager.setAdapter(viewpagerAdapter);
        tabs.setupWithViewPager(viewpager);
        toolsTitleToolbar = new ToolsTitleToolbar(viewpager, collapsingToolbar);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        collapsingToolbar.setBackgroundColor(Color.parseColor("#FAFAFA"));
        collapsingToolbar.setContentScrimColor(Color.parseColor("#FAFAFA"));
        collapsingToolbar.setStatusBarScrimColor(Color.parseColor("#FAFAFA"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
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
    public void onChildsFragmentViewCreated() {

    }

    @Override
    protected void onDestroy() {
        slidingUp.onDestroy();
        iCRMEdu.getiCRMEdu(this).removeCore2Listener(this);
        super.onDestroy();
    }

    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {
        if (f instanceof TabSesionActividadView) {
            presenter.attachView((TabSesionActividadView) f);
        } else if (f instanceof TabSesionInstrumentoView) {
            presenter.attachView((TabSesionInstrumentoView) f);
        } else if (f instanceof TabSesionPreguntaView) {
            presenter.attachView((TabSesionPreguntaView) f);
        } else if (f instanceof TabSesionColaborativaView) {
            presenter.attachView((TabSesionColaborativaView) f);
        }else if(f instanceof ExoPlayerView){
            slidingUp.attachView((ExoPlayerView)f);
        }else if(f instanceof TabSesionEvidenciaView){
            presenter.attachView((TabSesionEvidenciaView)f);
        }

    }

    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        if (f instanceof TabSesionActividadView) {
            presenter.onTabSesionActividadDestroy();
        } else if (f instanceof TabSesionInstrumentoView) {
            presenter.onTabSesionInstrumentoViewDestroy();
        } else if (f instanceof TabSesionPreguntaView) {
            presenter.onTabSesionPreguntaViewDestroy();
        } else if (f instanceof TabSesionColaborativaView) {
            presenter.onTabSesionColaborativaViewDestroy();
        } else if(f instanceof ExoPlayerView){
            slidingUp.onDestroyView((ExoPlayerView)f);
        }else if(f instanceof TabSesionEvidenciaView){
            presenter.onTabSesionEvidenciaViewDestroy();
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        if (slidingUp.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void setNombreSesion(String nombreApredizaje) {

    }

    @Override
    public void setNumeroSession(int numero) {

    }

    @Override
    public void setStatusBarColor(String color) {
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
                window.setStatusBarColor(Color.parseColor(color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setToolbarColor(String color, String nombre) {
        try {
            toolsTitleToolbar.change(nombre);
            toolbar.setTitleTextColor(Color.parseColor(color));
            collapsingToolbar.setCollapsedTitleTextColor(Color.parseColor(color));
            collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setTabColor(String indicadorColor, String textColor, String selectedTextColor) {
        try {
            tabs.setSelectedTabIndicatorColor(Color.parseColor(indicadorColor));
            tabs.setTabTextColors(Color.parseColor(textColor), Color.parseColor(selectedTextColor));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setDescripcionSesion(String color, String titulo, String subtitulo, String fondo) {
        try {
            conCurso.setCardBackgroundColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtTitulo.setText(titulo);
        txtSubtitulo.setText(subtitulo);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_error_outline_black)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(imgCurso)
                .load(fondo)
                .apply(options)
                .into(imgCurso);
    }

    @Override
    public void modoOnline() {
        msgError.setVisibility(View.GONE);
    }

    @Override
    public void modoOffline() {
        msgError.setVisibility(View.VISIBLE);
        msgError.setVisibility(View.VISIBLE);
        txtError.setPaintFlags(txtError.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtError.setText("Volver a cargar.");
    }

    @Override
    public void servicePasarAsistencia(int silaboEventoId) {
        iCRMEdu.getiCRMEdu(this).pasarAsistencia(silaboEventoId);
    }

    @Override
    public void showYoutubeActividad(String url, String nombre) {
        slidingUp.showYoutubeActividad(url, nombre);
    }

    @Override
    public void showPreviewArchivo(String driveId, String nombreRecurso) {
        slidingUp.showPreviewArchivo(driveId, nombreRecurso);
    }

    @Override
    public void showMultimediaPlayer(String driveId, String nombreRecurso) {
        slidingUp.showMultimediaPlayer(driveId, nombreRecurso);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        slidingUp.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.msg_error)
    public void onViewClicked() {
        presenter.onVolverCargar();
    }

    @Override
    public void onPlaying() {
        slidingUp.onPlaying();
    }

    @Override
    public void onPused() {
        slidingUp.onPused();
    }

    @Override
    public void onChangeBloqueo() {

    }

    @Override
    public void onConetadoAsistencia() {
        txtSinSenial.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_circle_asistencia_conec));
    }

    @Override
    public void onDesconetadoAsistencia() {
        txtSinSenial.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_circle_asistencia_desconec));
    }

    public static class ToolsTitleToolbar {
        private final ViewPager viewPager;
        private CollapsingToolbarLayout collapsingToolbarLayout;
        private String titulo;

        ToolsTitleToolbar(ViewPager viewPager, CollapsingToolbarLayout collapsingToolbarLayout) {
            this.viewPager = viewPager;
            this.collapsingToolbarLayout = collapsingToolbarLayout;
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    PagerAdapter pagerAdapter = viewPager.getAdapter();
                    collapsingToolbarLayout.setTitle(pagerAdapter != null ? titulo + " - " + capitalize(viewPager.getAdapter().getPageTitle(position)) : titulo);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        private void change(String titulo) {
            this.titulo = titulo;
            collapsingToolbarLayout.setTitle(
                    viewPager.getAdapter() != null &&
                            viewPager.getAdapter().getCount() > 0
                            ? titulo + " - " + capitalize(viewPager.getAdapter().getPageTitle(viewPager.getCurrentItem())) : titulo);

        }

        public static String capitalize(CharSequence x) {
            if (TextUtils.isEmpty(x)) return "";
            StringBuilder result = new StringBuilder();
            if (TextUtils.isEmpty(x)) return "";
            int count = 0;
            for (String ws : x.toString().split(" ")) {
                count++;
                if (count > 1) result.append(" ");
                if (ws.length() < 2) {
                    result.append(ws);
                } else {
                    result.append(ws.substring(0, 1).toUpperCase()).append(ws.substring(1).toLowerCase());
                }
            }

            return result.toString();
        }

    }

}
