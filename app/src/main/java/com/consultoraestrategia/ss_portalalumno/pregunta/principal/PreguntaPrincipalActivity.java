package com.consultoraestrategia.ss_portalalumno.pregunta.principal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.activity.BaseActivity;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.FirebaseOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.pregunta.adapter.RespuestaAdapter;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.pregunta.dialog.DialogkeyBoardView;
import com.consultoraestrategia.ss_portalalumno.pregunta.dialog.Dialogkeyboard;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.EliminarRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.EliminarSubRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetListaRespuestaOffline;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetPregunta;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.SaveRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateListRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.UpdateSubRespuestaFB;
import com.consultoraestrategia.ss_portalalumno.util.KeyboardUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PreguntaPrincipalActivity extends BaseActivity<PreguntaPrincipalView, PreguntaPrincipalPresenter> implements PreguntaPrincipalView, RespuestaAdapter.Listener, Dialogkeyboard.CallBack, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_help)
    ImageView imgHelp;
    @BindView(R.id.txt_titulo)
    TextView txtTitulo;
    @BindView(R.id.img_tipo)
    ImageView imgTipo;
    @BindView(R.id.rc_cometarios)
    RecyclerView rcCometarios;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.progressBar5)
    ProgressBar progressBar5;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.circleImageView)
    CircleImageView circleImageView;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.conten_enviar)
    CardView contenEnviar;
    @BindView(R.id.scroll_cometario)
    NestedScrollView scrollCometario;
    @BindView(R.id.textView11)
    TextView textView11;
    @BindView(R.id.cont_online)
    ConstraintLayout contOnline;
    @BindView(R.id.txt_sin_senial)
    TextView txtSinSenial;
    @BindView(R.id.cont_offline)
    ConstraintLayout contOffline;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private RespuestaAdapter respuestaAdapter;

    @Override
    protected String getTag() {
        return "PreguntaActivityTAG";
    }

    @Override
    protected AppCompatActivity getActivity() {
        return this;
    }

    @Override
    protected PreguntaPrincipalPresenter getPresenter() {
        PreguntaRepositorio preguntaRepositorio = new PreguntaRepositorioImpl();
        return new PreguntaPrincipalPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new UpdateListRespuestaFB(preguntaRepositorio),
                new GetPregunta(preguntaRepositorio),
                new SaveRespuestaFB(preguntaRepositorio),
                new UpdateRespuestaFB(preguntaRepositorio),
                new UpdateSubRespuestaFB(preguntaRepositorio),
                new EliminarRespuestaFB(preguntaRepositorio),
                new EliminarSubRespuestaFB(preguntaRepositorio),
                new GetListaRespuestaOffline(preguntaRepositorio),
                new AndroidOnlineImpl(this));
    }

    @Override
    protected PreguntaPrincipalView getBaseView() {
        return this;
    }

    @Override
    protected Bundle getExtrasInf() {
        return getIntent().getExtras();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pregunta_principal);
        ButterKnife.bind(this);
        setupToolbar();
        setupAdapter();
        refresh.setOnRefreshListener(this);
    }

    private void setupAdapter() {
        respuestaAdapter = new RespuestaAdapter(this);
        rcCometarios.setLayoutManager(new LinearLayoutManager(this));
        rcCometarios.setAdapter(respuestaAdapter);
        ((SimpleItemAnimator) rcCometarios.getItemAnimator()).setSupportsChangeAnimations(false);
        rcCometarios.setHasFixedSize(false);
        rcCometarios.setNestedScrollingEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return root;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar5;
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
    public void updateRespuesta(RespuestaUi respuestaUi) {
        respuestaAdapter.updateSave(respuestaUi);
    }

    @Override
    public void removePregunta(String preguntaRespuestaId) {
        respuestaAdapter.removePregunta(preguntaRespuestaId);
    }

    @Override
    public void setImgeAlumno(String foto) {
        Glide.with(circleImageView)
                .load(foto)
                .apply(UtilsGlide.getGlideRequestOptions(R.drawable.ic_usuario))
                .into(circleImageView);
    }

    @Override
    public void setTituloPregunta(String titulo, String tipoId, String color) {
        txtTitulo.setText(titulo);
        Drawable circle = ContextCompat.getDrawable(imgHelp.getContext(), R.drawable.ic_help_pregunta);
        Drawable send = ContextCompat.getDrawable(imgHelp.getContext(), R.drawable.ic_send);
        Drawable tipo;
        if (tipoId.equals("2")) {
            tipo = ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_people);
        } else {
            tipo = ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_person);
        }
        try {
            circle.mutate().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
            tipo.mutate().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
            send.mutate().setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgTipo.setImageDrawable(tipo);
        imgHelp.setImageDrawable(circle);
        imgSend.setImageDrawable(send);
    }

    @Override
    public void showDialogKey() {
        Dialogkeyboard dialogkeyboard = new Dialogkeyboard();
        dialogkeyboard.show(getSupportFragmentManager(), "dialogkeyboard");
    }

    @Override
    public void addRespuesta(RespuestaUi respuestaUi) {
        rcCometarios.scrollToPosition(respuestaAdapter.addRespuesta(respuestaUi));
        scrollCometario.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int scrollViewHeight = scrollCometario.getHeight();
                if (scrollViewHeight > 0) {
                    scrollCometario.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    final View lastView = scrollCometario.getChildAt(scrollCometario.getChildCount() - 1);
                    final int lastViewBottom = lastView.getBottom() + scrollCometario.getPaddingBottom();
                    final int deltaScrollY = lastViewBottom - scrollViewHeight - scrollCometario.getScrollY();
                    /* If you want to see the scroll animation, call this. */
                    scrollCometario.smoothScrollBy(0, deltaScrollY);
                    /* If you don't want, call this. */
                    scrollCometario.scrollBy(0, deltaScrollY);
                }
            }
        });
    }

    @Override
    public void addSubRespuesta(SubRespuestaUi subRespuestaUi) {
        respuestaAdapter.addSubRespuesta(subRespuestaUi);
    }

    @Override
    public void setListRespuesta(List<RespuestaUi> listRespuesta) {
        respuestaAdapter.setListRespuesta(listRespuesta);
    }

    @Override
    public void clearListRespuesta() {
        respuestaAdapter.clear();
    }

    @Override
    public void modoOnline() {
        refresh.setEnabled(false);
        contOnline.setVisibility(View.VISIBLE);
        contOffline.setVisibility(View.GONE);
    }

    @Override
    public void modoOffline(String color) {
        refresh.setEnabled(true);
        contOnline.setVisibility(View.GONE);
        contOffline.setVisibility(View.VISIBLE);
        try {
            txtSinSenial.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_send, R.id.conten_enviar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_send:
                presenter.onClicNuevaRespuesta();
                break;
            case R.id.conten_enviar:
                presenter.onClicNuevaRespuesta();
                break;
        }
    }

    @Override
    public void onClickResponder(RespuestaUi respuestaUi) {
        presenter.onClickResponder(respuestaUi);
    }

    @Override
    public void onClickRespuesta(RespuestaUi respuestaUi) {
        presenter.onClicNuevaRespuestaRespuesta(respuestaUi);
    }

    @Override
    public void onClickEditarSubRespuesta(SubRespuestaUi subRespuestaUi) {
        presenter.onClickEditarSubRespuesta(subRespuestaUi);
    }

    @Override
    public void onClickEliminarSubRespuesta(SubRespuestaUi subRespuestaUi) {
        presenter.onClickEliminarSubRespuesta(subRespuestaUi);
    }

    @Override
    public void onClickEditarRespuesta(RespuestaUi respuestaUi) {
        presenter.onClickEditarRespuesta(respuestaUi);
    }

    @Override
    public void onClickEliminarRespuesta(RespuestaUi respuestaUi) {
        presenter.onClickEliminarRespuesta(respuestaUi);
    }

    @Override
    public void onClickAceptarDialogkeyboard(String contenido) {
        presenter.onClickAceptarDialogkeyboard(contenido);
    }

    @Override
    public void onDismissDialogkeyboard() {
        presenter.onDismissDialogkeyboard();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.hideKeyboard(getActivity());
            }
        }, 200);
    }

    @Override
    public void onCreateDialogKeyBoard(DialogkeyBoardView view) {
        presenter.onCreateDialogKeyBoard(view);
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void showProgress() {
        refresh.post(() -> refresh.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        refresh.post(() -> refresh.setRefreshing(false));
    }
}
