package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.ViewpagerAdapter;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.FirebaseOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorio;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.GetCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.UpdateFireBasePersona;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.UpdateFireBaseUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.listener.PeriodoListener;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoTareaView;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoUnidadView;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.view.adapters.PeriodoAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.ui.FragmentTareas;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.UnidadAprendizajeFragment;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabsCursoActivity extends BaseActivity<TabCursoView, TabCursoPresenter> implements TabCursoView, PeriodoListener, BaseFragmentListener, LifecycleImpl.LifecycleListener {

    private static final String TAG = TabsCursoActivity.class.getSimpleName();
    @BindView(R.id.imgBackgroundAppbar)
    ImageView imgBackgroundAppbar;
    @BindView(R.id.toolbarprogress)
    ProgressBar toolbarprogress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNombreCurso)
    TextView txtNombreCurso;
    @BindView(R.id.txtPeriodoSeccion)
    TextView txtPeriodoSeccion;
    @BindView(R.id.layoutProfile)
    ConstraintLayout layoutProfile;
    @BindView(R.id.tab_curso)
    TabLayout tabCurso;
    @BindView(R.id.ctl_tabcursos)
    CollapsingToolbarLayout ctlTabcursos;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.vp_Curso)
    ViewPager vpCurso;
    @BindView(R.id.recyclerPeriodo)
    RecyclerView recyclerPeriodo;
    @BindView(R.id.content)
    ConstraintLayout content;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.txt_offline)
    TextView txtOffline;
    @BindView(R.id.txt_error)
    TextView txtError;
    @BindView(R.id.msg_error)
    CardView msgError;

    private PeriodoAdapter periodoAdapter;
    private ToolsTitleToolbar toolsTitleToolbar;


    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected TabCursoPresenter getPresenter() {
        TabCursoRepositorio tabCursoRepositorio = new TabCursoRepositorioImpl();
        return new TabCursoPresenteImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetCalendarioPeriodo(tabCursoRepositorio),
                new UpdateFireBaseUnidadAprendizaje(tabCursoRepositorio),
                new UpdateFireBasePersona(tabCursoRepositorio),
                new AndroidOnlineImpl(this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        desbloqOrientation();
    }

    @Override
    protected TabCursoView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tabs_curso);
        ButterKnife.bind(this);
        setupToolbar();
        setupImagenColap();
        setupAdapterPeriodo();
        setTitle("");
        showFragments();
    }


    private void setupImagenColap() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;
        String urlBackgroudResource = bundle.getString("backgroudResource");
        String bgColor = bundle.getString("fondo");
        showAppbarBackground(urlBackgroudResource, bgColor);

    }

    private void setupAdapterPeriodo() {
        recyclerPeriodo.setVisibility(View.VISIBLE);
        periodoAdapter = new PeriodoAdapter(new ArrayList<PeriodoUi>(), this);
        recyclerPeriodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPeriodo.setHasFixedSize(true);
        recyclerPeriodo.setAdapter(periodoAdapter);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return root;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progress;
    }


    public void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setTransitionName("view");
            //change color of status bar
            try {
                String bgColor = getIntent().getExtras().getString("fondo");
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.parseColor(bgColor));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void showFragments() {
        Log.d(TAG, "idCalendarioPeriodo fragmentAdapter null");
        ViewpagerAdapter fragmentAdapter = new ViewpagerAdapter(getSupportFragmentManager(), 0, this);
        fragmentAdapter.addFragment(new UnidadAprendizajeFragment(), "UNIDADES");
        fragmentAdapter.addFragment(FragmentTareas.newInstance(), "TAREAS");
        vpCurso.setOffscreenPageLimit(2);
        tabCurso.setupWithViewPager(vpCurso);
        toolsTitleToolbar = new ToolsTitleToolbar(vpCurso, ctlTabcursos);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;

                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabCurso.getLayoutParams();
                    params.bottomMargin = 0;
                    params.topMargin = (int) UtilsPortalAlumno.convertDpToPixel(8f, getApplicationContext());

                    ViewGroup.MarginLayoutParams paramsContent = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
                    paramsContent.topMargin = 4;
                    content.requestLayout();
                    tabCurso.requestLayout();

                } else if (isShow) {
                    isShow = false;

                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabCurso.getLayoutParams();
                    params.bottomMargin = (int) UtilsPortalAlumno.convertDpToPixel(20f, getApplicationContext());
                    params.topMargin = 0;

                    ViewGroup.MarginLayoutParams paramsContent = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
                    paramsContent.topMargin = (int) UtilsPortalAlumno.convertDpToPixel(-16f, getApplicationContext());

                    content.requestLayout();
                    tabCurso.requestLayout();
                }
            }
        });
        vpCurso.setSaveFromParentEnabled(false);
        vpCurso.setAdapter(fragmentAdapter);
    }


    @Override
    public void showTitle(String title) {
        txtNombreCurso.setText(title);
        toolsTitleToolbar.change(title);
    }

    @Override
    public void showSubtitle(String subtitle) {
        txtPeriodoSeccion.setText(subtitle);
    }

    @Override
    public void showAppbarBackground(String bg, String bgColor) {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.ic_error_outline_black)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(imgBackgroundAppbar)
                .load(bg)
                .apply(options)
                .into(imgBackgroundAppbar);
        try {
            appBar.setBackgroundColor(Color.parseColor(bgColor));
            ctlTabcursos.setCollapsedTitleTextColor(Color.WHITE);
            ctlTabcursos.setExpandedTitleColor(Color.TRANSPARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPeriodoList(List<PeriodoUi> periodoAcademicoList, String parametroColor3) {
        periodoAdapter.setColor(parametroColor3);
        periodoAdapter.setPeriodoList(periodoAcademicoList);
    }

    @Override
    public void changeColorToolbar(String parametroColor1) {

        try {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.parseColor(parametroColor1));
            }
            ctlTabcursos.setContentScrimColor(Color.parseColor(parametroColor1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeColorFloatButon(String parametroColor2) {
        try {
            //fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color2)));
            //fabgrupo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changePeriodo() {
        periodoAdapter.notifyDataSetChanged();
    }

    @Override
    public void modoOffline() {
        txtOffline.setText(" Sin señal");
        msgError.setVisibility(View.VISIBLE);
        txtError.setPaintFlags(txtError.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtError.setText("Volver a cargar.");
    }

    @Override
    public void modoOnline() {
        txtOffline.setText("");
        msgError.setVisibility(View.GONE);
    }

    @Override
    public void onPeriodoSelected(PeriodoUi periodoUi) {
        presenter.onPeriodoSelected(periodoUi);
    }

    @OnClick(R.id.msg_error)
    public void onViewClicked() {
        presenter.onVolverCargar();
    }

    @Override
    public void onChildsFragmentViewCreated() {

    }

    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {
        if(f instanceof TabCursoTareaView){
            presenter.attachView((TabCursoTareaView)f);
        }else if(f instanceof TabCursoUnidadView){
            presenter.attachView((TabCursoUnidadView)f);
        }
    }

    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        if(f instanceof TabCursoTareaView){
            presenter.onTabCursoTareaViewDestroyed();
        }else if(f instanceof TabCursoUnidadView){
            presenter.onTabCursoUnidadViewDestroyed();
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {

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



