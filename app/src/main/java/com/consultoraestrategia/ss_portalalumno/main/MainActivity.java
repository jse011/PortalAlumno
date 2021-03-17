package com.consultoraestrategia.ss_portalalumno.main;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.global.ICRMEduListener;
import com.consultoraestrategia.ss_portalalumno.global.entities.GbCursoUi;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Activity;
import com.consultoraestrategia.ss_portalalumno.login2.service.BloqueoRealTime;
import com.consultoraestrategia.ss_portalalumno.main.adapter.CursosAdapter;
import com.consultoraestrategia.ss_portalalumno.main.adapter.MenuAdapter;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetCursos;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateFirebaseTipoNota;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;
import com.consultoraestrategia.ss_portalalumno.main.nuevaVersion.NuevaVersionDisponible;
import com.consultoraestrategia.ss_portalalumno.permisos.DialogOnAnyDeniedMultiplePermissionsListener;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.sincronizar.instrumentos.SyncInstrumento;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities.TabsCursoActivity;
import com.consultoraestrategia.ss_portalalumno.userbloqueo.UserBloqueoActivity;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.domain.VersionInfoListener;

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
    @BindView(R.id.txt_nombre_app)
    TextView txtNombreApp;
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.imageView10)
    ImageView imageView10;

    private MenuAdapter menuAdapter;
    private CursosAdapter adapter;
    private DialogOnAnyDeniedMultiplePermissionsListener dialogMultiplePermissionsListener;
    private FirebaseAuth mAuth;

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
        MainRepositorio mainRepositorio = new MainRepositorioImpl(ApiRetrofit.getInstance());
        return new MainPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(), new GetCursos(mainRepositorio),
                new UpdateCalendarioPeriodo(mainRepositorio),
                new UpdateFirebaseTipoNota(mainRepositorio),
                new AndroidOnlineImpl(this));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
        setupVersion();
        limpiarVariableGlobales();
        desbloqOrientation();
        setupTabMenu();
        setupToolbar();
        setupRecyclerProgramas();
        setupAdapter();
        setupValidatePermisos(this);
        SyncInstrumento.start(this);
    }

    private void setupVersion() {
        new GPVersionChecker.Builder(this)
                .setVersionInfoListener(new VersionInfoListener() {

                    @Override
                    public void onErrorHandled(@Nullable Throwable throwable) {

                    }

                    @Override
                    public void onResulted(Version version) {
                        if(version.isNeedToUpdate()){
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    NuevaVersionDisponible.newInstance(version.getNewVersionCode(), version.getChanges())
                                            .show(getSupportFragmentManager(),"NuevaVersionDisponible");
                                }
                            });
                        }

                    }
                })
                .create();
    }

    private void limpiarVariableGlobales() {
        //iCRMEdu.variblesGlobales = new iCRMEdu.VariblesGlobales();
        //iCRMEdu.variblesGlobales.saveData(this);
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
            window.setStatusBarColor(Color.parseColor("#FAFAFAFA"));
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

        if(getResources().getString(R.string.app_name).equals("Educar Student")){
            Glide.with(imageView11)
                    .load(R.drawable.logo_educar)
                    .into(imageView11);
            imageView11.setVisibility(View.VISIBLE);
            txtNombreApp.setVisibility(View.INVISIBLE);
            imageView10.setVisibility(View.VISIBLE);
        }else{
            imageView11.setVisibility(View.GONE);
            txtNombreApp.setVisibility(View.VISIBLE);
            imageView10.setVisibility(View.GONE);
        }

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
        finish();
    }

    @Override
    public void showListCurso(List<CursosUi> cursosUiList) {
        adapter.setList(cursosUiList);
    }

    @Override
    public void showTabCursoActivity() {
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

    @Override
    public void validateFirebase(String usuarioFirebase, String passwordFirebase) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            initializingFirebase(usuarioFirebase, passwordFirebase, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(getTag(), "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();;
                    } else {
                        // If sign in fails, display a message to the user.
                        createAccountFirebase(usuarioFirebase, passwordFirebase);
                        Log.w(getTag(), "signInWithEmail:failure", task.getException());
                    }
                }
            });
        }
    }

    @Override
    public void showActivtyBloqueo() {
        Intent intent = new Intent(this, UserBloqueoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void initBloqueo() {
        BloqueoRealTime.Companion.getInstance(this);
    }

    private void initializingFirebase(String email, String password, OnCompleteListener<AuthResult> callback){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(callback!=null)callback.onComplete(task);
                    }
                });
    }

    private void  createAccountFirebase(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(getTag(), "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            initializingFirebase(email, password, null);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(getTag(), "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

    private void setupValidatePermisos(Activity activity) {
        if(dialogMultiplePermissionsListener==null)dialogMultiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(activity)
                        .withTitle("Permisos de internet, lectura y escritura y cámara.")
                        .withMessage("Tanto los permisos como el permiso de Internet y el permiso de lectura y escritura son necesarios para el correcto funcionamiento del aplicativo móvil.")
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.drawable.base_academico)
                        .build();

        Dexter.withActivity(activity)
                .withPermissions(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        //Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(dialogMultiplePermissionsListener)
                .check();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
