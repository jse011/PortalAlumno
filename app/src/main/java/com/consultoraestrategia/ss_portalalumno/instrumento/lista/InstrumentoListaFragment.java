package com.consultoraestrategia.ss_portalalumno.instrumento.lista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.EncuestaUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.InstrumentoEvaluacionActivity;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion_online.EvaluacionOnlineActivity;
import com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter.InstrumentoEncuestaAdapter;
import com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter.InstrumentoListAdapter;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumentoEncuesta;
import com.consultoraestrategia.ss_portalalumno.instrumento.useCase.GetInstrumentoList;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionInstrumentoView;

import java.util.List;

import butterknife.BindView;

public class InstrumentoListaFragment extends BaseFragment<InstrumentoListaView, InstrumentoListaPresenter, BaseFragmentListener> implements InstrumentoListaView, InstrumentoListAdapter.Callback, TabSesionInstrumentoView, InstrumentoEncuestaAdapter.Callback {

    @BindView(R.id.rc_lista_instrumento)
    RecyclerView rcListaInstrumento;
    @BindView(R.id.progressBar14)
    ProgressBar progressBar14;
    private InstrumentoListAdapter adapter;
    @BindView(R.id.progressBar15)
    ProgressBar progressBar15;
    @BindView(R.id.rc_lista_encuesta)
    RecyclerView rcListaEncuesta;
    InstrumentoEncuestaAdapter adapterEncuesta;

    @Override
    protected String getLogTag() {
        return "InstrumentoListaFragmentTAG";
    }

    @Override
    protected InstrumentoListaPresenter getPresenter() {
        InstrumentoRepository repository = new InstrumentoRepositoryImpl();
        return new InstrumentoListaPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetInstrumentoList(repository),
                new GetInstrumentoEncuesta(repository),
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

        adapterEncuesta = new InstrumentoEncuestaAdapter(this);
        rcListaEncuesta.setAdapter(adapterEncuesta);
        rcListaEncuesta.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) rcListaEncuesta.getItemAnimator()).setSupportsChangeAnimations(false);

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
        startActivity(new Intent(getContext(), EvaluacionOnlineActivity.class));
    }

    @Override
    public void showListaInstrumentoEncuesta(List<EncuestaUi> encuestaUiList) {
        adapterEncuesta.setList(encuestaUiList);
    }

    @Override
    public void changeList() {
        if (presenter != null) presenter.notifyChangeFragment();
    }

    @Override
    public void showProgress2() {
        progressBar15.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress2() {
        progressBar15.setVisibility(View.GONE);
    }

    @Override
    public void changeInstrumentoEncuestaList() {
        presenter.changeInstrumentoEncuestaList();
    }

    @Override
    public void showProgress() {
        super.showProgress();
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
    }

    @Override
    public void onClickEncuesta(EncuestaUi instrumentoUi) {

    }

    @Override
    public void onClickEncuestaVerResultados(EncuestaUi instrumentoUi) {

    }
}
