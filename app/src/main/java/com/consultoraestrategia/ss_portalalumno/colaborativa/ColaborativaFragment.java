package com.consultoraestrategia.ss_portalalumno.colaborativa;

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

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragment;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.colaborativa.adapter.ColaborativaAdapter;
import com.consultoraestrategia.ss_portalalumno.colaborativa.data.source.ColaborativaRepositorio;
import com.consultoraestrategia.ss_portalalumno.colaborativa.data.source.ColaborativaRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaColaborativa;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaColaborativaBaseDatos;
import com.consultoraestrategia.ss_portalalumno.colaborativa.useCase.GetListaGrabaciones;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionColaborativaView;

import java.util.List;

import butterknife.BindView;

public class ColaborativaFragment extends BaseFragment<ColaborativaView, ColaborativaPresenter, BaseFragmentListener> implements ColaborativaView, ColaborativaAdapter.Listener, TabSesionColaborativaView {
    @BindView(R.id.rc_colaborativa)
    RecyclerView rcColaborativa;
    @BindView(R.id.progressBar8)
    ProgressBar progressBar8;
    private ColaborativaAdapter adapter;
    @BindView(R.id.rc_colaborativa_servidor)
    RecyclerView rcColaborativaServidor;
    @BindView(R.id.rc_colaborativa_grabaciones)
    RecyclerView rcColaborativaGrabaciones;
    private ColaborativaAdapter adapterServidor;
    private ColaborativaAdapter adapterGrabaciones;
    @BindView(R.id.conten_empty)
    ViewGroup contenEmpty;

    @Override
    protected String getLogTag() {
        return "ColaborativaFragmentTAG";
    }

    @Override
    protected ColaborativaPresenter getPresenter() {
        ColaborativaRepositorio colaborativaRepositorio = new ColaborativaRepositorioImpl();
        return new ColaborativaPresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetListaColaborativa(colaborativaRepositorio),
                new GetListaColaborativaBaseDatos(colaborativaRepositorio),
                new GetListaGrabaciones(colaborativaRepositorio),
                new AndroidOnlineImpl(getContext()));
    }

    @Override
    protected ColaborativaView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_colaborativa, container, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        adapter = new ColaborativaAdapter(this);
        rcColaborativa.setLayoutManager(new LinearLayoutManager(getContext()));
        rcColaborativa.setAdapter(adapter);

        adapterServidor = new ColaborativaAdapter(this);
        rcColaborativaServidor.setLayoutManager(new LinearLayoutManager(getContext()));
        rcColaborativaServidor.setAdapter(adapterServidor);

        adapterGrabaciones = new ColaborativaAdapter(this);
        rcColaborativaGrabaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        rcColaborativaGrabaciones.setAdapter(adapterGrabaciones);

        contenEmpty.postDelayed(new Runnable() {
            @Override
            public void run() {
                validarSiEstaVacio();
            }
        }, 6000);
    }

    private void validarSiEstaVacio(){
        if(adapter.getItemCount()==0&&adapterServidor.getItemCount()==0&&adapterGrabaciones.getItemCount()==0)
        {
            if(contenEmpty!=null)contenEmpty.setVisibility(View.VISIBLE);
        } else{
            if(contenEmpty!=null)contenEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    protected ViewGroup getRootLayout() {
        return rcColaborativa;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar8;
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
    public void onClickColobarativa(ColaborativaUi colaborativaUi) {
        presenter.onClickColobarativa(colaborativaUi);
    }

    @Override
    public void changeColaborativaList() {
        presenter.changeColaborativaList();
    }

    @Override
    public void changeReunionVirtualList() {
        presenter.changeReunionVirtualList();
    }

    @Override
    public void changeGrabacionesSalaVirtualList() {
        presenter.changeGrabacionesSalaVirtualList();
    }

    @Override
    public void changeReunionVirtualBaseDatosList() {
        presenter.changeReunionVirtualBaseDatosList();
    }

    @Override
    public void setListColaborativa(List<ColaborativaUi> colaborativaUiList) {
        adapter.setListColaborativa(colaborativaUiList);
        validarSiEstaVacio();
    }

    @Override
    public void showVinculo(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setListGrabacionesColaborativa(List<ColaborativaUi> colaborativaUiList) {
        adapterGrabaciones.setListColaborativa(colaborativaUiList);
        validarSiEstaVacio();
    }

    @Override
    public void setListColaborativaServidor(List<ColaborativaUi> colaborativaUiList) {
        adapterServidor.setListColaborativa(colaborativaUiList);
        validarSiEstaVacio();
    }

    @Override
    public void onDestroyView() {
        contenEmpty=null;
        super.onDestroyView();

    }
}
