package com.consultoraestrategia.ss_portalalumno.login2.principal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.estadocuenta2.EstadoCuenta2Activity;
import com.consultoraestrategia.ss_portalalumno.lib.AppDatabase;
import com.consultoraestrategia.ss_portalalumno.login2.data.preferent.LoginPreferentRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepository;
import com.consultoraestrategia.ss_portalalumno.login2.data.repositorio.LoginDataRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.SaveUrlSevidorLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetDatosInicioSesion;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetPersonaLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorData.GetUsuarioLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioExterno;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioPorCorreoLocal;
import com.consultoraestrategia.ss_portalalumno.login2.domain.useCase.servidorlogin.GetUsuarioPorDniLocal;
import com.consultoraestrategia.ss_portalalumno.login2.principal.bloqueo.BloqueoFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.bloqueo.BloqueoView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.correo.CorreoFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.correo.CorreoView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.dni.DniFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.dni.DniView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario.ListaUsuarioFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario.ListaUsuarioView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.password.PasswordFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.password.PasswordView;
import com.consultoraestrategia.ss_portalalumno.login2.principal.progress.ProgressFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.usuario.UsuarioFragment;
import com.consultoraestrategia.ss_portalalumno.login2.principal.usuario.UsuarioView;
import com.consultoraestrategia.ss_portalalumno.main.MainActivity;
import com.consultoraestrategia.ss_portalalumno.util.LifeCycleFragment;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login2Activity extends BaseActivity<Login2View, Login2Presenter> implements Login2View, LifeCycleFragment.LifecycleListener {
    @BindView(R.id.contenedor)
    FrameLayout contenedor;
    @BindView(R.id.contendoPrincipal)
    ConstraintLayout contendoPrincipal;
    private CorreoFragment correoFragment;
    private DniFragment dniFragment;
    private ListaUsuarioFragment listaUsuarioFragment;
    private PasswordFragment passwordFragment;
    private ProgressFragment progressFragment;
    private UsuarioFragment usuarioFragment;
    private BloqueoFragment bloqueoFragment;

    @Override
    protected String getTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected Login2Presenter getPresenter() {
        LoginDataRepository loginDataRepository = new LoginDataRepositoryImpl();

        return new Login2PresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetUsuarioExterno(loginDataRepository),
                new SaveUrlSevidorLocal(loginDataRepository),
                new GetUsuarioPorCorreoLocal(loginDataRepository),
                new GetUsuarioPorDniLocal(loginDataRepository),
                new GetUsuarioLocal(loginDataRepository),
                new GetPersonaLocal(loginDataRepository),
                new LoginPreferentRepositoryImpl(this),
                new GetDatosInicioSesion(loginDataRepository));
    }

    @Override
    protected Login2View getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login_2);
        ButterKnife.bind(this);
        setupToolbar();
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new LifeCycleFragment(this),true);
    }

    private void setupToolbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //change color of status bar
            Window window = this.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#f2f2f2"));
        }
    }

    @Override
    public void showMessage(CharSequence error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected ViewGroup getRootLayout() {
        return contendoPrincipal;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
    }

    @Override
    public void showUser(boolean backStack) {
        usuarioFragment = new UsuarioFragment();
        setContent(usuarioFragment, backStack);
    }

    @Override
    public void showCorreo(boolean backStack) {
        correoFragment = new CorreoFragment();
        setContent(correoFragment, backStack);
    }

    @Override
    public void showDni(boolean backStack) {
        dniFragment = new DniFragment();
        setContent(dniFragment, backStack);
    }

    @Override
    public void showListaUsuario(boolean backStack) {
        listaUsuarioFragment = new ListaUsuarioFragment();
        setContent(listaUsuarioFragment, backStack);
    }

    @Override
    public void showDialogProgress() {
        progressFragment = new ProgressFragment();
        setContent(progressFragment, false);
    }

    @Override
    public void showPassword() {
        passwordFragment = new PasswordFragment();
        setContent(passwordFragment, true);
    }


    public void setContent(@NonNull Fragment fragment, boolean addToBackStack) {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.contenedor);
        if (current == null || !current.getClass().equals(fragment.getClass())) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (addToBackStack) {
                if(current!=null){
                    transaction.hide(current);
                }
                transaction.addToBackStack(null).add(R.id.contenedor, fragment).commit();
            } else {
                transaction.replace(R.id.contenedor, fragment).commit();
            }


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void disabledOnClick() {
        enableDisableView(contenedor, false);
    }

    @Override
    public void enableddOnClick() {
        enableDisableView(contenedor, true);
    }

    @Override
    public void clearFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(usuarioFragment!=null)transaction.remove(usuarioFragment);
        if(listaUsuarioFragment!=null)transaction.remove(listaUsuarioFragment);
        if(passwordFragment!=null)transaction.remove(passwordFragment);
        if(correoFragment!=null)transaction.remove(correoFragment);
        if(dniFragment!=null)transaction.remove(dniFragment);
        if(progressFragment!=null)transaction.remove(progressFragment);
        if(bloqueoFragment!=null)transaction.remove(bloqueoFragment);
        transaction.commit();
    }

    public void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ButterKnife.bind(this);

        try {
            //SessionUser.getCurrentUser().delete();
            FlowManager.getDatabase(AppDatabase.class).reset();
            FlowManager.init(new FlowConfig.Builder(getApplicationContext()).build());
            Log.d(getTag(), "borrar BD : ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }


    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {

    }

    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        if (f instanceof UsuarioView) {
            presenter.onUsuarioViewDestroyed();
        }else if (f instanceof CorreoView) {
            presenter.onCorreoViewDestroyed();
        }else if (f instanceof DniView) {
            presenter.onDniViewDestroyed();
        }else if (f instanceof ListaUsuarioView) {
            presenter.onListaUsuarioViewDestroyed();
        }else if (f instanceof PasswordView) {
            presenter.onPasswordViewDestroyed();
        }else if (f instanceof BloqueoView) {
            presenter.onBloqueoViewDestroyed();
        }
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if (f instanceof UsuarioView) {
            presenter.attachView((UsuarioView) f);
            ((UsuarioView)f).onAttach(presenter);
        }else if (f instanceof CorreoView) {
            presenter.attachView((CorreoView) f);
            ((CorreoView)f).onAttach(presenter);
        }else if (f instanceof DniView) {
            presenter.attachView((DniView) f);
            ((DniView)f).onAttach(presenter);
        }else if (f instanceof ListaUsuarioView) {
            presenter.attachView((ListaUsuarioView) f);
            ((ListaUsuarioView)f).onAttach(presenter);
        }else if (f instanceof PasswordView) {
            presenter.attachView((PasswordView) f);
            ((PasswordView)f).onAttach(presenter);
        }else if (f instanceof BloqueoView) {
            presenter.attachView((BloqueoView) f);
            ((BloqueoView)f).onAttach(presenter);
        }

    }

    @Override
    public void goToActivity(int idUsuario) {

        Log.d(getTag(), "idUsuario : " + idUsuario);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showBloqueo(boolean backStack) {
        bloqueoFragment = new BloqueoFragment();
        setContent(bloqueoFragment, backStack);
    }

    @Override
    public void onShowPagoEnLinea(int personaId, String numDoc) {
        EstadoCuenta2Activity.start(this, personaId, numDoc);
    }

}
