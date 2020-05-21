package com.consultoraestrategia.ss_portalalumno.tabsSesiones;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.ui.ActividadesFragment;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.ViewpagerAdapter;
import com.consultoraestrategia.ss_portalalumno.instrumento.lista.InstrumentoListaFragment;
import com.consultoraestrategia.ss_portalalumno.sincronizar.instrumentos.SyncInstrumento;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.ui.FragmentTareasSesiones;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabSesionesActivity extends BaseActivity<TabSesionView, TabSesionPresenter> implements TabSesionView, BaseFragmentListener {
    @BindView(R.id.imgBackgroundAppbar)
    ImageView imgBackgroundAppbar;
    @BindView(R.id.toolbarprogress)
    ProgressBar toolbarprogress;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtNombreSesion)
    TextView txtNombreSesion;
    @BindView(R.id.txtDetalleSesion)
    TextView txtDetalleSesion;
    @BindView(R.id.constraintLayoutSessiones)
    ConstraintLayout constraintLayoutSessiones;
    @BindView(R.id.tab_curso)
    TabLayout tabCurso;
    @BindView(R.id.ctl_tabcursos)
    CollapsingToolbarLayout ctlTabcursos;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.vp_Curso)
    ViewPager vpCurso;

    @Override
    protected String getTag() {
        return "TabSesionesActivity";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected TabSesionPresenter getPresenter() {
        return new TabSesionPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources());
    }

    @Override
    protected TabSesionView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tabs_sesiones);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupToolbar();
        setupAdapter();
    }

    @Override
    protected ViewGroup getRootLayout() {
        return null;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
    }

    public void setupToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this)
                .load(R.drawable.sesion_portada)
                .apply(new RequestOptions().centerCrop())
                .into(imgBackgroundAppbar);

        try {
            Typeface mFont = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/coloredcrayons.ttf");
            txtNombreSesion.setTypeface(mFont);
            txtDetalleSesion.setTypeface(mFont);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                appBar.setTransitionName("view");
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.color_sesion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupAdapter() {
        ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), 0, null);
        viewpagerAdapter.addFragment(new ActividadesFragment(), "ACTIVIDADES");
        viewpagerAdapter.addFragment(FragmentTareasSesiones.newInstanceSesion(), "TAREA");
        viewpagerAdapter.addFragment(new InstrumentoListaFragment(), "EVALUACION");

        vpCurso.setOffscreenPageLimit(4);
        vpCurso.setAdapter(viewpagerAdapter);
        tabCurso.setupWithViewPager(vpCurso);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(getTag(), "onOffsetChanged: " + verticalOffset);
                if (scrollRange == -1) {
                    Log.d(getTag(), "scrollRange: " + scrollRange);
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabCurso.getLayoutParams();
                    params.bottomMargin = 0;
                    params.topMargin = (int) UtilsPortalAlumno.convertDpToPixel(8f, getApplicationContext());

                    ViewGroup.MarginLayoutParams paramsContent = (ViewGroup.MarginLayoutParams) vpCurso.getLayoutParams();
                    paramsContent.topMargin = 4;

                } else if (isShow) {
                    isShow = false;
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabCurso.getLayoutParams();
                    params.bottomMargin = (int) UtilsPortalAlumno.convertDpToPixel(20f, getApplicationContext());
                    params.topMargin = 0;

                    ViewGroup.MarginLayoutParams paramsContent = (ViewGroup.MarginLayoutParams) vpCurso.getLayoutParams();
                    paramsContent.topMargin = (int) UtilsPortalAlumno.convertDpToPixel(-16f, getApplicationContext());
                }
                Log.d(getTag(), "scrollRange: " + scrollRange);
                Log.d(getTag(), "isShow: " + isShow);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNombreSesion(String nombreApredizaje) {
        Log.d(getTag(), "nombreApredizaje: " + nombreApredizaje);
        txtDetalleSesion.setText(UtilsPortalAlumno.limpiarAcentos(nombreApredizaje));
    }

    @Override
    public void setNumeroSession(int numero) {
        Log.d(getTag(), "setNumeroSession: " + numero);
        txtNombreSesion.setText(UtilsPortalAlumno.limpiarAcentos("Sesión " + numero));
    }
}
