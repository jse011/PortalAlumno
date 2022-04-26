package com.consultoraestrategia.ss_portalalumno.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aghajari.emojiview.AXEmojiManager;
import com.aghajari.emojiview.emoji.iosprovider.AXIOSEmojiProvider;
import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.lib.bottomnavigationviewex.BottomNavigationViewEx;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Activity;
import com.consultoraestrategia.ss_portalalumno.login2.service.BloqueoRealTime;
import com.consultoraestrategia.ss_portalalumno.main.adapter.MenuAdapter;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorio;
import com.consultoraestrategia.ss_portalalumno.main.data.repositorio.MainRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.main.dialog.DialogAdjuntoDownload;
import com.consultoraestrategia.ss_portalalumno.main.dialog.DialogListaBannerEvento;
import com.consultoraestrategia.ss_portalalumno.main.dialog.DialogPreviewAdjuntoEvento;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetColorTarjetaQR;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetCursos;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetEventoAgenda;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetIconoPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetLikeSaveCountLike;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.GetNombreServidor;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.SaveLike;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateCalendarioPeriodo;
import com.consultoraestrategia.ss_portalalumno.main.domain.usecase.UpdateFirebaseTipoNota;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.NuevaVersionUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;
import com.consultoraestrategia.ss_portalalumno.main.nuevaVersion.NuevaVersionDisponible;
import com.consultoraestrategia.ss_portalalumno.previewDrive.PreviewArchivoActivity;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.view.activities.TabsCursoActivity;
import com.consultoraestrategia.ss_portalalumno.userbloqueo.UserBloqueoActivity;
import com.consultoraestrategia.ss_portalalumno.util.FabBottomNavigationView;
import com.consultoraestrategia.ss_portalalumno.util.FloatingButtonGradiente;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.robohorse.gpversionchecker.GPVersionChecker;
import com.robohorse.gpversionchecker.domain.Version;
import com.robohorse.gpversionchecker.domain.VersionInfoListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends BaseActivity<MainView, MainPresenter> implements MainView, MenuListener, LifecycleImpl.LifecycleListener {
    @BindView(R.id.btn_menu)
    CircleImageView btnMenu;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.bnve)
    FabBottomNavigationView bnve;
    @BindView(R.id.fab)
    FloatingButtonGradiente fab;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.imageView11)
    ImageView imageView11;
    @BindView(R.id.txt_nombre_app)
    TextView txtNombreApp;
    @BindView(R.id.imageView10)
    ImageView imageView10;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.nav_bar_rc_menu)
    RecyclerView navBarRcMenu;
    @BindView(R.id.nav_bar_content_profile_hijo)
    ConstraintLayout navBarContentProfileHijo;
    @BindView(R.id.nav_bar_imagen_profile_hijo)
    CircleImageView navBarImagenProfileHijo;
    @BindView(R.id.nav_bar_imagen_profile)
    CircleImageView navBarImagenProfile;
    @BindView(R.id.txt_nombre_usuario)
    TextView txtNombreUsuario;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private MenuAdapter menuAdapter;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static  final int RC_SIGN_IN = 15264;

    @Override
    protected String getTag() {
        return MainActivity2.class.getSimpleName();
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
                new AndroidOnlineImpl(this),
                new GetEventoAgenda(mainRepositorio),
                new GetLikeSaveCountLike(mainRepositorio),
                new SaveLike(mainRepositorio),
                new GetIconoPortalAlumno(mainRepositorio),
                new GetColorTarjetaQR(mainRepositorio),
                new GetNombreServidor(mainRepositorio));
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
        setContentView(R.layout.activity_main_two);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        AXEmojiManager.install(this,new AXIOSEmojiProvider(this));
        initView();
        setupToolbar();
        desbloqOrientation();
        setupVersion();
        setupTabMenu();
        setupRecyclerProgramas();
        initializingFirebase();
        ValidarLogueoFirebase();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_host_fragment);
        NavigationUI.setupWithNavController(bnve, navHostFragment.getNavController(), false);

        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new LifecycleImpl(0,this),true);
    }

    private void ValidarLogueoFirebase() {
        // active listen to user logged in or not.
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(getTag(), "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(getTag(), "onAuthStateChanged:signed_out");
                }
            }
        };


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
        tabLayout.setScrollPosition(2,0f,true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 2:
                        presenter.onIbtnProgramaClicked();
                        break;
                    case 1:
                        presenter.onClickBtnAcceso();
                        break;
                    case 0:
                        presenter.onClickBtnConfiguracion();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 2:
                        presenter.onIbtnProgramaClicked();
                        break;
                    case 1:
                        presenter.onClickBtnAcceso();
                        break;
                    case 0:
                        presenter.onClickBtnConfiguracion();
                        break;
                }
            }
        });
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
                            MainActivity2.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    presenter.nuevaVersionDisponible(version.getNewVersionCode(), version.getChanges());

                                }
                            });
                        }

                    }
                })
                .create();
    }

    private void initView() {

        ViewCompat.setLayoutDirection(navView, ViewCompat.LAYOUT_DIRECTION_RTL);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        // disable all animations
        //bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setItemIconTintList(null);
        // bnve.setTextSize(9);
        int iconSize = 45;
        //bnve.setIconSize(iconSize, iconSize);
        // set item height
        bnve.setItemHeight(BottomNavigationViewEx.dp2px(this, 48));
        //bnve.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf"));
        //drawerLayout.setScrimColor(Color.parseColor("#00FFFFFF"));
        // add badge
        fab.setBackgroundResource(R.drawable.btn_menu_center);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //fab.setForeground(ContextCompat.getDrawable(this, R.drawable.btn_menu_center));
        }else {

        }
    }

    void toggle() {
        int drawerLockMode = drawerLayout.getDrawerLockMode(GravityCompat.END);
        if (drawerLayout.isDrawerVisible(GravityCompat.END)
                && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    private void setupToolbar() {
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_lolipop));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void setupRecyclerProgramas() {
        navBarRcMenu.setLayoutManager(new LinearLayoutManager(this));
        menuAdapter = new MenuAdapter(new ArrayList<Object>(), this);
        navBarRcMenu.setAdapter(menuAdapter);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return drawerLayout;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
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
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Log.d(getTag(), "current: "+currentUser.getDisplayName());
            //Eliminar el usuario antiguo y vover a logearse anonimamente
            if(!currentUser.isAnonymous()){
                currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            initializingFirebase();
                            Toast.makeText(MainActivity2.this, "Success connect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(getTag(),"Ocurrio un error durante la eliminación del usuario", e);
                    }
                });
            }
        }
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void showMessage(CharSequence error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
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

        Glide.with(btnMenu)
                .load(foto)
                .apply(UtilsGlide.getGlideRequestOptions())
                .into(btnMenu);

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

            Intent intent = new Intent(this, MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser!=null) currentUser.delete();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void setNameAnioAcademico(String nombre) {

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
    @Deprecated
    public void validateFirebase(String usuarioFirebase, String passwordFirebase) {
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


    private void initializingFirebase(String email, String password, OnCompleteListener<AuthResult> callback){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        presenter.onInitCuentaFirebase();
                        if(callback!=null)callback.onComplete(task);
                    }
                });

    }

    private void initializingFirebase(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            presenter.onErrorCuentaFirebase();
                        } else {
                            presenter.onInitCuentaFirebase();
                        }

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

    @Override
    public void showNuevaversion(NuevaVersionUi nuevaVersionUi) {
        NuevaVersionDisponible.newInstance(nuevaVersionUi.getNewVersionCode(), nuevaVersionUi.getChange())
                .show(getSupportFragmentManager(),"NuevaVersionDisponible");
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
    public void startCompartirEvento(EventoUi eventoUi) {

    }

    @Override
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null)
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void showDialogListaBannerEvento() {
        new DialogListaBannerEvento().show(getSupportFragmentManager(), "infoEvento");
    }

    @Override
    public void showDialogAdjuntoEvento() {
        new DialogPreviewAdjuntoEvento()
                .show(getSupportFragmentManager(), "dialogPreviewAdjuntoEvento");
    }

    @Override
    public void showDialogEventoDownload() {
        new DialogAdjuntoDownload()
                .show(getSupportFragmentManager(), "dialogEventoDownload");
    }

    @Override
    public void showPreviewArchivo() {
        startActivity(new Intent(this, PreviewArchivoActivity.class));
    }

    @Override
    public void showMultimediaPlayer() {
        startActivity(new Intent(this, PreviewArchivoActivity.class));
    }

    @Override
    public void setChangeIconoPortal(String url) {
        Glide.with(logo)
                .load(url)
                .apply(UtilsGlide.getGlideRequestOptions())
                .into(logo);
    }

    @Override
    public void servicePasarAsistencia(int silaboEventoId) {
        iCRMEdu.getiCRMEdu(this).pasarAsistencia(silaboEventoId);
    }

    @Override
    public void accederGoogle() {
/*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        signInGoogle();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity2.this, "Error session", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            signInGoogle();
        }*/
    }

    @Override
    public void mostrarDialogoForzarConexion() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setTitle("Solución de conexión");

        builder.setMessage("Solucionar el problema que origina que no envíen los archivos.");
        builder.setPositiveButton("Solucionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iCRMEdu.getiCRMEdu(MainActivity2.this).forzarConexion();
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

    void signInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(getTag(), "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(getTag(), "Google sign in failed", e);
                Toast.makeText(MainActivity2.this, "Error connect google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(getTag(), "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity2.this, "Success connect", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(getTag(), "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity2.this, "Error auth", Toast.LENGTH_SHORT).show();;
                        }
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
        if(f instanceof TabEvento){
            presenter.onTabEventoDestroy();
        }else if(f instanceof InformacionEvento){
            presenter.onInformacionEventoDestroy();
        }else if(f instanceof AdjuntoPreviewEvento){
            presenter.onAdjuntoPreviewEventoDestroy();
        }else if(f instanceof AdjuntoEventoDownload){
            presenter.onAdjuntoEventoDownloadDestroy();
        }else if(f instanceof TabCursos){
            presenter.onTabCursosDestroy();
        }else if(f instanceof TabFamilia){
            presenter.onTabFamiliaDestroy();
        }else if(f instanceof TabQR){
            presenter.onTabQRDestroy();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if(f instanceof TabEvento){
            presenter.attachView((TabEvento)f);
            ((TabEvento)f).setPresenter(presenter);
        }else if(f instanceof InformacionEvento){
            presenter.attachView((InformacionEvento)f);
            ((InformacionEvento)f).setPresenter(presenter);
        }else if(f instanceof AdjuntoPreviewEvento){
            presenter.attachView((AdjuntoPreviewEvento)f);
            ((AdjuntoPreviewEvento)f).setPresenter(presenter);
        }else if(f instanceof AdjuntoEventoDownload){
            presenter.attachView((AdjuntoEventoDownload)f);
            ((AdjuntoEventoDownload)f).setPresenter(presenter);
        }else if(f instanceof TabCursos){
            presenter.attachView((TabCursos)f);
            ((TabCursos)f).setPresenter(presenter);
        }else if(f instanceof TabFamilia){
            presenter.attachView((TabFamilia)f);
            ((TabFamilia)f).setPresenter(presenter);
        }else if(f instanceof TabQR){
            presenter.attachView((TabQR)f);
            ((TabQR)f).setPresenter(presenter);
        }
    }

}
