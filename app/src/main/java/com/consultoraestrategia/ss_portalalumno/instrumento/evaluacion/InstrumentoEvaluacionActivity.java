package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.ViewpagerAdapter;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.VariableUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.component.InstrumentoEvalViewPager;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo.TipoEvaluacionView;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoCheck.TipoCheckFragment;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoExcluyente.TipoExcluyenteFragment;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoTexto.TipoTextoFragment;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumento;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.SaveFirebasInstrumento;
import com.consultoraestrategia.ss_portalalumno.sincronizar.instrumentos.SyncInstrumento;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstrumentoEvaluacionActivity extends BaseActivity<InstrumentoEvaluacionView, InstrumentoEvaluacionPresenter> implements InstrumentoEvaluacionView, LifecycleImpl.LifecycleListener {
    @BindView(R.id.viewpager)
    InstrumentoEvalViewPager viewpager;
    @BindView(R.id.btn_close)
    ImageView btnClose;
    @BindView(R.id.mf_progress_bar)
    ProgressBar mfProgressBar;
    @BindView(R.id.txt_sin_senial)
    TextView txtSinSenial;
    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.progressBar4)
    ProgressBar progressBar4;
    @BindView(R.id.lottie_image)
    LottieAnimationView lottieImage;
    @BindView(R.id.btn_enviar_despues)
    Button btnEnviarDespues;
    @BindView(R.id.conten_enviar)
    ConstraintLayout contenEnviar;
    private ViewpagerAdapter viewpagerAdapter;

    @Override
    protected String getTag() {
        return "InstrumentoEvaluacionTAG";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected InstrumentoEvaluacionPresenter getPresenter() {
        InstrumentoRepository repository = new InstrumentoRepositoryImpl();
        return new InstrumentoEvaluacionPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetInstrumento(repository),
                new SaveFirebasInstrumento(repository));
    }

    @Override
    protected InstrumentoEvaluacionView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_instrumento);
        ButterKnife.bind(this);
        setupAdapter();
        setupOffline();
    }

    private void setupOffline() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    presenter.onOnline();
                } else {
                    presenter.onOffline();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                presenter.onOffline();
            }
        });
    }

    private void setupAdapter() {
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(), 0, this);
        viewpager.setAdapter(viewpagerAdapter);
        viewpager.setPagingEnabled(false);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                presenter.changePage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected ViewGroup getRootLayout() {
        return root;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar4;
    }


    @OnClick(R.id.btn_close)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showListPreguntas(List<VariableUi> variableUiList) {
        HashMap<VariableUi, TipoEvaluacionView> tipoEvaluacionViewList = new HashMap<>();
        List<Fragment> fragmentList = new ArrayList<>();
        for (VariableUi variableUi : variableUiList) {
            switch (variableUi.getTipoRespuestaId()) {
                case 10://Texto
                    TipoTextoFragment tipoTextoFragment = new TipoTextoFragment();
                    fragmentList.add(tipoTextoFragment);
                    tipoEvaluacionViewList.put(variableUi, tipoTextoFragment);
                    break;
                case 11://Excluyente
                    TipoExcluyenteFragment tipoExcluyenteFragment = new TipoExcluyenteFragment();
                    fragmentList.add(tipoExcluyenteFragment);
                    tipoEvaluacionViewList.put(variableUi, tipoExcluyenteFragment);
                    break;
                case 12://Check
                    TipoCheckFragment tipoCheckFragment = new TipoCheckFragment();
                    fragmentList.add(tipoCheckFragment);
                    tipoEvaluacionViewList.put(variableUi, tipoCheckFragment);
                    break;
            }

        }
        viewpagerAdapter.setListFragment(fragmentList);
        presenter.attachViewList(tipoEvaluacionViewList);

    }

    @Override
    public void setMaxProgress(int size) {
        mfProgressBar.setProgress(1);
        mfProgressBar.setSecondaryProgress(1);
        mfProgressBar.setMax(size);
    }

    private void setupProgressBarr(int position) {
        mfProgressBar.setProgress(position);
    }

    @Override
    public void changePage(int position) {
        viewpager.setCurrentItem(position, true);
    }

    @Override
    public void setProgressPostionOffline(int position) {
        txtSinSenial.setVisibility(View.VISIBLE);
        mfProgressBar.setProgress(0);
        mfProgressBar.setSecondaryProgress(position + 1);

    }

    @Override
    public void setProgressPostionOnline(int position) {
        txtSinSenial.setVisibility(View.GONE);
        mfProgressBar.setProgress(position + 1);
        mfProgressBar.setSecondaryProgress(0);
    }

    @Override
    public void finalizar() {
        finish();
    }

    @Override
    public void showContenDialog() {
        disableEnableControls(false, root);
        lottieImage.setAnimation("wifi-outline-icon.json");
        lottieImage.setRepeatCount(ValueAnimator.INFINITE);
        lottieImage.playAnimation();
        disableEnableControls(true, contenEnviar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if(contenEnviar!=null)contenEnviar.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 4000);
    }

    private void disableEnableControls(boolean enable, ViewGroup vg){
        for (int i = 0; i < vg.getChildCount(); i++){
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup){
                disableEnableControls(enable, (ViewGroup)child);
            }
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

    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if (f instanceof TipoEvaluacionView) {
            TipoEvaluacionView tipoEvaluacionView = (TipoEvaluacionView) f;
            tipoEvaluacionView.setPresenter(presenter);
            presenter.attachView(tipoEvaluacionView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SyncInstrumento.start(this);
    }

    @OnClick({R.id.btn_enviar_despues})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_enviar_despues:
                finish();
                break;
        }
    }
}
