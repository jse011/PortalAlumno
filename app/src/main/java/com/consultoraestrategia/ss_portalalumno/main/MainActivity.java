package com.consultoraestrategia.ss_portalalumno.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Activity;
import com.consultoraestrategia.ss_portalalumno.main.adapter.CursosAdapter;
import com.consultoraestrategia.ss_portalalumno.main.adapter.MenuAdapter;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetCursos;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities.TabsCursoActivity;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView, MenuListener, CursosAdapter.Listener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.progresBar)
    ProgressBar progresBar;
    @BindView(R.id.nav_bar_rc_menu)
    RecyclerView navBarRcMenu;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rvListaClases)
    RecyclerView rvListaClases;
    @BindView(R.id.nav_bar_imagen_profile)
    CircleImageView navBarImagenProfile;
    @BindView(R.id.txt_nombre_usuario)
    TextView txtNombreUsuario;
    @BindView(R.id.picker_text_view)
    TextView pickerTextView;
    @BindView(R.id.nav_bar_content_profile_hijo)
    ConstraintLayout navBarContentProfileHijo;
    @BindView(R.id.nav_bar_imagen_profile_hijo)
    CircleImageView navBarImagenProfileHijo;

    private MenuAdapter menuAdapter;
    private CursosAdapter adapter;

    @Override
    protected String getTag() {
        return "MainActivityTAG";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected MainPresenter getPresenter() {
        MainRepositorio mainRepositorio = new MainRepositorioImpl();
        return new MainPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(), new GetCursos(mainRepositorio));
    }

    @Override
    protected MainView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main_final);
        ButterKnife.bind(this);
        desbloqOrientation();
        setupTabMenu();
        setupToolbar();
        setupRecyclerProgramas();
        setupAdapter();
    }

    private void setupAdapter() {
        adapter = new CursosAdapter(this);
        rvListaClases.setLayoutManager(new LinearLayoutManager(this));
        rvListaClases.setAdapter(adapter);

    }

    @Override
    protected ViewGroup getRootLayout() {
        return null;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progresBar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //change color of status bar
            Window window = this.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.md_white_1000));
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle mToogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mToogle.setHomeAsUpIndicator(R.drawable.ic_menu_main);
        drawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        final PorterDuffColorFilter colorFilter
                = new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.md_light_blue_400), PorterDuff.Mode.MULTIPLY);

        for (int i = 0; i < toolbar.getChildCount(); i++) {
            final View v = toolbar.getChildAt(i);

            //Step 1 : Changing the color of back button (or open drawer button).
            if (v instanceof ImageButton) {
                //Action Bar back button
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }
        }

        //toolbar.setNavigationIcon(R.drawable.ic_menu_main);
//        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));

    }

    private void setupRecyclerProgramas() {
        navBarRcMenu.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(new ArrayList<Object>(), this);
        navBarRcMenu.setAdapter(menuAdapter);
    }

    private void setupTabMenu() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        presenter.onIbtnProgramaClicked();
                        break;
                    case 1:
                        presenter.onClickBtnAcceso();
                        break;
                    case 2:
                        presenter.onClickBtnConfiguracion();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onMenuSelected(Object o) {
        if (o instanceof ProgramaEduactivoUI) {
            ProgramaEduactivoUI programaEduactivosUI = (ProgramaEduactivoUI) o;
            presenter.onProgramaEducativoUIClicked(programaEduactivosUI);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (o instanceof UsuarioAccesoUI) {
            UsuarioAccesoUI usuarioAccesoUI = (UsuarioAccesoUI) o;
            presenter.onAccesoSelected(usuarioAccesoUI);
        } else if (o instanceof ConfiguracionUi) {
            ConfiguracionUi configuracionUi = (ConfiguracionUi) o;
            presenter.onClickConfiguracion(configuracionUi);
        }
    }

    @Override
    public void showMenuList(ArrayList<Object> objects) {
        menuAdapter.setLista(objects);
    }

    @Override
    public void showActivityLogin() {
        Intent intent = new Intent(this, Login2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtras(new Bundle());
        startActivity(intent);
    }

    @Override
    public void showListCurso(List<CursosUi> cursosUiList) {
        adapter.setList(cursosUiList);
    }

    @Override
    public void showTabCursoActivity(CursosUi cursosUi, int anioAcademicoId, int idPrograma) {
        GbCursoUi gbCursoUi = new GbCursoUi();
        gbCursoUi.setCursoId(cursosUi.getCursoId());
        gbCursoUi.setCargaCursoId(cursosUi.getCargaCursoId());
        gbCursoUi.setParametroDisenioColor1(cursosUi.getBackgroundSolidColor());
        gbCursoUi.setParametroDisenioColor2(cursosUi.getBackgroundSolidColor2());
        gbCursoUi.setParametroDisenioColor3(cursosUi.getBackgroundSolidColor3());
        gbCursoUi.setNombre(cursosUi.getNombre());
        gbCursoUi.setSalon(cursosUi.getSalon());
        gbCursoUi.setParametroDisenioPath(cursosUi.getUrlBackgroundItem());
        gbCursoUi.setSeccionyperiodo(cursosUi.getSeccionyperiodo());
        gbCursoUi.setPlanCursoId(cursosUi.getPlanCursoId());
        iCRMEdu.variblesGlobales.setGbCursoUi(gbCursoUi);
        iCRMEdu.variblesGlobales.setAnioAcademicoId(anioAcademicoId);
        iCRMEdu.variblesGlobales.setProgramEducativoId(idPrograma);
        iCRMEdu.variblesGlobales.setProgramEducativoId(idPrograma);
        startActivity(new Intent(this, TabsCursoActivity.class));
    }

    @Override
    public void changeNombreUsuario(String nombre) {
       txtNombreUsuario.setText(nombre);
    }

    @Override
    public void changeFotoUsuario(String foto) {
        Glide.with(navBarImagenProfile)
                .load(foto)
                .apply(UtilsGlide.getGlideRequestOptions())
                .into(navBarImagenProfile);
    }

    @Override
    public void onClick(CursosUi cursosUi) {
        presenter.onClickCurso(cursosUi);
    }

    @Override
    public void mostrarDialogoCerrarSesion() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle(R.string.msg_dilogo_cerrar_sesion_titulo);

            builder.setMessage(R.string.msg_dilogo_cerrar_sesion_descripcion_two);
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.onClickDialogoCerrarSesion();
                    dialog.cancel();

                }
            });



        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Create AdapterExample
        builder.create().show();
    }

    @Override
    public void cerrarSesion() {
        try {
            //SessionUser.getCurrentUser().delete();
            FlowManager.getDatabase(AppDatabase.class).reset();
            FlowManager.init(new FlowConfig.Builder(getApplicationContext()).build());

            //Process.killProcess(Process.myPid());
            //activity.sendFinish();
            finishAffinity();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void setNameAnioAcademico(String nombre) {
        pickerTextView.setText(nombre);
    }

    @Override
    public void changeFotoUsuarioApoderado(String fotoApoderado) {
        if(TextUtils.isEmpty(fotoApoderado)){
            navBarContentProfileHijo.setVisibility(View.GONE);
        }else {
            Glide.with(navBarImagenProfileHijo)
                    .load(fotoApoderado)
                    .apply(UtilsGlide.getGlideRequestOptions())
                    .into(navBarImagenProfileHijo);
            navBarContentProfileHijo.setVisibility(View.VISIBLE);
        }
    }
}