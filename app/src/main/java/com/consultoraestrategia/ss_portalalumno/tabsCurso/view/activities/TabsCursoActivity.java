package com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.ViewpagerAdapter;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorio;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.data.repositorio.TabCursoRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.domain.useCase.GetCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.entities.PeriodoUi;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.listener.PeriodoListener;
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

public class TabsCursoActivity extends BaseActivity<TabCursoView, TabCursoPresenter> implements TabCursoView, PeriodoListener, BaseFragmentListener {

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
    @BindView(R.id.msg_calendario_periodo)
    TextView msgCalendarioPeriodo;
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
    private PeriodoAdapter periodoAdapter;


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
        return new TabCursoPresenteImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(), new GetCalendarioPeriodo(tabCursoRepositorio));
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
        ViewpagerAdapter fragmentAdapter = new ViewpagerAdapter(getSupportFragmentManager(), 0, null);
        fragmentAdapter.addFragment(new UnidadAprendizajeFragment(), "UNIDADES");
        fragmentAdapter.addFragment(FragmentTareas.newInstance(), "TAREAS");
        vpCurso.setOffscreenPageLimit(2);
        tabCurso.setupWithViewPager(vpCurso);
        vpCurso.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


                } else if (isShow) {
                    isShow = false;

                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabCurso.getLayoutParams();
                    params.bottomMargin = (int) UtilsPortalAlumno.convertDpToPixel(20f, getApplicationContext());
                    params.topMargin = 0;

                    ViewGroup.MarginLayoutParams paramsContent = (ViewGroup.MarginLayoutParams) content.getLayoutParams();
                    paramsContent.topMargin = (int) UtilsPortalAlumno.convertDpToPixel(-16f, getApplicationContext());
                    if (getSupportActionBar() != null)
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                }
            }
        });
        vpCurso.setSaveFromParentEnabled(false);
        root.postDelayed(new Runnable() {
            @Override
            public void run() {
                vpCurso.setAdapter(fragmentAdapter);
            }
        }, 100);
    }


    @Override
    public void showTitle(String title) {
        txtNombreCurso.setText(title);
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
    public void notifyChangeFragment() {
        UnidadAprendizajeFragment unidadAprendizajeFragment = getFragment(UnidadAprendizajeFragment.class);
        if(unidadAprendizajeFragment!=null){
            unidadAprendizajeFragment.notifyChangeFragment();
        }

        FragmentTareas fragmentTareas = getFragment(FragmentTareas.class);
        if(fragmentTareas!=null){
            fragmentTareas.notifyChangeFragment();
        }
    }

    private <T extends Fragment> T getFragment(Class<T> tClass) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment :
                fragments) {
            if (tClass.isInstance(fragment)) {
                return (T) fragment;
            }
        }
        return null;
    }

    @Override
    public void onPeriodoSelected(PeriodoUi periodoUi) {
        presenter.onPeriodoSelected(periodoUi);
    }
}



