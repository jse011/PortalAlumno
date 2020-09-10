package com.consultoraestrategia.ss_portalalumno.instrumento.lista;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragment;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepository;
import com.consultoraestrategia.ss_portalalumno.instrumento.data.source.InstrumentoRepositoryImpl;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.InstrumentoEvaluacionActivity;
import com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter.InstrumentoListAdapter;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumentoList;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionInstrumentoView;

import java.util.List;

import butterknife.BindView;

public class InstrumentoListaFragment extends BaseFragment<InstrumentoListaView, InstrumentoListaPresenter, BaseFragmentListener> implements InstrumentoListaView, InstrumentoListAdapter.Callback, TabSesionInstrumentoView {

    @BindView(R.id.rc_lista_instrumento)
    RecyclerView rcListaInstrumento;
    @BindView(R.id.progressBar14)
    ProgressBar progressBar14;
    private InstrumentoListAdapter adapter;

    @Override
    protected String getLogTag() {
        return "InstrumentoListaFragmentTAG";
    }

    @Override
    protected InstrumentoListaPresenter getPresenter() {
        InstrumentoRepository repository = new InstrumentoRepositoryImpl();
        return new InstrumentoListaPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetInstrumentoList(repository),
                new AndroidOnlineImpl(getContext()));
    }

    @Override
    protected InstrumentoListaView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_instrumento, container, false);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return rcListaInstrumento;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        adapter = new InstrumentoListAdapter(this);
        rcListaInstrumento.setAdapter(adapter);
        rcListaInstrumento.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) rcListaInstrumento.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar14;
    }

    @Override
    public void showImportantMessage(CharSequence message) {

    }

    @Override
    public void showFinalMessage(CharSequence message) {

    }

    @Override
    public void showListSingleChooser(String title, List<Object> items, int positionSelected) {

    }

    @Override
    public void showFinalMessageAceptCancel(CharSequence message, CharSequence messageTitle) {

    }

    @Override
    public void onClick(InstrumentoUi instrumentoUi) {
        presenter.onClick(instrumentoUi);
    }

    @Override
    public void showListInstrumento(List<InstrumentoUi> instrumentoUiList) {
        adapter.setList(instrumentoUiList);
    }

    @Override
    public void showActivityInstrumento() {
        startActivity(new Intent(getContext(), InstrumentoEvaluacionActivity.class));
    }

    @Override
    public void changeList() {
        if (presenter != null) presenter.notifyChangeFragment();
    }
}
