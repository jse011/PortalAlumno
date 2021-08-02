package com.consultoraestrategia.ss_portalalumno.main.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnStaggeredGridLayoutManager;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.adapter.EventoAdapter;
import com.consultoraestrategia.ss_portalalumno.main.adapter.EventoColumnCountProvider;
import com.consultoraestrategia.ss_portalalumno.main.adapter.EventosTipoAdapter;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.main.listeners.ChangePostionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TabEventoFragment extends Fragment implements  MainView.TabEvento, EventoAdapter.Listener, ChangePostionListener.CallBack, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.eventosRv)
    RecyclerView eventosRv;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.root)
    MotionLayout root;
    @BindView(R.id.progressBar12)
    ProgressBar progressBar12;
    @BindView(R.id.rc_tipo_eventos)
    RecyclerView rcTipoEventos;
    @BindView(R.id.btn_bajar)
    FloatingActionButton btnBajar;

    private Unbinder unbinder;
    private EventosTipoAdapter tipoEventoAdapter;
    private MainPresenter presenter;
    EventoAdapter eventosAdapter;
    private ChangePostionListener firsthChangePostionListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_evento_clean, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupAdapter();
        setupToolbar();
        setHasOptionsMenu(true);
        swipeContainer.setOnRefreshListener(this);
        return view;
    }

    private void setupToolbar() {

        /*appBar.addOnOffsetChangedListener(new EventosFragment.AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        swipeContainer.setEnabled(false);
                        break;
                    case EXPANDED:
                        swipeContainer.setEnabled(true);
                        break;
                    case COLLAPSED:
                        swipeContainer.setEnabled(false);
                        break;
                }
            }
        });*/
    }

    private void setupAdapter() {
        tipoEventoAdapter = new EventosTipoAdapter(new Function1<TipoEventoUi, Unit>() {
            @Override
            public Unit invoke(TipoEventoUi tiposEventosUi) {
                if(presenter!=null)presenter.onClickTipoEvento(tiposEventosUi);
                return null;
            }
        });
        rcTipoEventos.setAdapter(tipoEventoAdapter);
        EventoColumnCountProvider eventoColumnCountProvider = new EventoColumnCountProvider(getContext());
        AutoColumnStaggeredGridLayoutManager autoColumnStaggeredGridLayoutManager = new AutoColumnStaggeredGridLayoutManager(OrientationHelper.VERTICAL, 4, getContext());
        autoColumnStaggeredGridLayoutManager.setColumnCountProvider(eventoColumnCountProvider);
        rcTipoEventos.setLayoutManager(autoColumnStaggeredGridLayoutManager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        eventosRv.setLayoutManager(layoutManager);
        eventosAdapter = new EventoAdapter(this);
        eventosRv.setAdapter(eventosAdapter);


        if (firsthChangePostionListener == null) {
            firsthChangePostionListener = new ChangePostionListener(this);
        }

        eventosRv.removeOnScrollListener(firsthChangePostionListener);
        eventosRv.removeOnLayoutChangeListener(firsthChangePostionListener);

        eventosRv.addOnScrollListener(firsthChangePostionListener);
        eventosRv.addOnLayoutChangeListener(firsthChangePostionListener);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter = null;
    }

    @Override
    public void setTiposList(List<TipoEventoUi> tipoEventoUiList) {
        tipoEventoAdapter.setList(tipoEventoUiList);
    }

    @Override
    public void setEventoList(List<EventoUi> eventoUiList) {
        eventosAdapter.setList(eventoUiList);
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        progressBar12.setVisibility(View.VISIBLE);
    }

    @Override
    public void shideProgress() {
        progressBar12.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onClickLike(EventoUi eventosUi) {
        presenter.onClikLike(eventosUi);
    }

    @Override
    public void renderCountEvento(EventoUi eventoUi, RecyclerView.ViewHolder viewHolder) {
        presenter.renderCountEvento(eventoUi, new MainPresenter.Callback<EventoUi>() {
            @Override
            public void onSuccess(EventoUi eventoUi) {
                if(viewHolder instanceof EventoAdapter.ViewHolder){
                    EventoAdapter.ViewHolder holder = (EventoAdapter.ViewHolder)viewHolder;
                    if(holder.getEventoUi().getId().equals(eventoUi.getId())){
                        holder.changeLike(eventoUi);
                    }else {
                        eventosAdapter.update(eventoUi);
                    }
                }
            }
        });
    }


    @Override
    public void onclikCompartir(EventoUi eventoUi) {
        presenter.onClickCompartir(eventoUi);
    }

    @Override
    public void onClickAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi) {
        presenter.onClickAdjuntoPreview(eventoUi, adjuntoUi);
    }

    @Override
    public void onClickAdjunto(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi, boolean more) {
        presenter.onClickAdjunto(eventoUi, adjuntoUi, more);
    }

    @Override
    public void onLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi) {
        presenter.itemLinkEncuesta(adjuntoUi);
    }

    @Override
    public void changeFirsthPostion(int lastVisibleItem) {
        presenter.changeFirsthPostion(lastVisibleItem);
    }

    @Override
    public void onKeyboardOpens(int lastVisibleItem) {

    }

    @Override
    public void onKeyboardClose() {

    }

    @Override
    public void showFloatingButton() {
        btnBajar.show();
    }

    @Override
    public void hideFloatingButton() {
        btnBajar.hide();
    }

    @Override
    public void showListEventos(boolean positionInicial, List<EventoUi> eventoUiList) {
        if (positionInicial) eventosRv.scrollToPosition(0);
        eventosAdapter.setList(eventoUiList);
    }

    @OnClick(R.id.btn_bajar)
    public void onViewClicked() {
        eventosRv.postDelayed( new Runnable() {
            @Override
            public void run() {
                eventosRv.scrollToPosition(0);
                root.transitionToStart();
                /* appBar.setExpanded(true);*/
            }
        }, 100);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        presenter.onRefreshEventos();

    }
}
