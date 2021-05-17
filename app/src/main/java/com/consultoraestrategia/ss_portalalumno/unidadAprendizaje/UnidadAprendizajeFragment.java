package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragment;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.firebase.online.FirebaseOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.global.iCRMEdu;
import com.consultoraestrategia.ss_portalalumno.tabsCurso.tabs.TabCursoUnidadView;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.TabSesionesActivity2;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.SesionColumnCountProvider;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.UnidadesAdapter;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorio;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.data.repositorio.UnidadAprendizajeRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.GetUnidadAprendizajeList;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.SaveToogle;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.domain.usecase.UpdateFireBaseUnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.List;

import butterknife.BindView;

public class UnidadAprendizajeFragment extends BaseFragment<UnidadAprendizajeView, UnidadAprendizajePresenter, BaseFragmentListener> implements UnidadAprendizajeView, UnidadesAdapter.UnidadListener, TabCursoUnidadView {
    @BindView(R.id.progressBar6)
    ProgressBar progressBar6;
    @BindView(R.id.rv_Unidades)
    RecyclerView rvUnidades;
    @BindView(R.id.mensaje)
    TextView mensaje;
    private UnidadesAdapter listAdapter;

    @Override
    protected String getLogTag() {
        return "UnidadAprendizajeFragment";
    }

    @Override
    protected UnidadAprendizajePresenter getPresenter() {
        UnidadAprendizajeRepositorio repositorio = new UnidadAprendizajeRepositorioImpl();
        return new UnidadAprendizajePresenterImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new AndroidOnlineImpl(getContext()),
                new GetUnidadAprendizajeList(repositorio),
                new UpdateFireBaseUnidadAprendizaje(repositorio),
                new SaveToogle(repositorio));
    }

    @Override
    protected UnidadAprendizajeView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_unidades, container, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = super.onCreateView(inflater, container, savedInstanceState);
        setUnidadesAprendizaje();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setUnidadesAprendizaje(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvUnidades.setLayoutManager(linearLayoutManager);
        rvUnidades.setHasFixedSize(true);
        ((SimpleItemAnimator) rvUnidades.getItemAnimator()).setSupportsChangeAnimations(false);
        listAdapter = new UnidadesAdapter(this);
        rvUnidades.setAdapter(listAdapter);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return rvUnidades;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar6;
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
    public void showListUnidadAprendizaje(List<UnidadAprendizajeUi> unidadAprendizajeUiList, String color) {
        listAdapter.setList(unidadAprendizajeUiList, color);
    }

    @Override
    public int getColumnasSesionesList() {
        return SesionColumnCountProvider.columnsForWidth(getContext(), rvUnidades.getWidth());
    }

    @Override
    public void updateItem(UnidadAprendizajeUi unidadAprendizajeUi) {
        listAdapter.updateItem(unidadAprendizajeUi);
    }

    @Override
    public void showTabSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi) {
        startActivity(new Intent(getContext(), TabSesionesActivity2.class));
    }

    @Override
    public void showMensajeListaVacia() {
        mensaje.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMensajeListaVacia() {
        mensaje.setVisibility(View.GONE);
    }

    @Override
    public void servicePasarAsistencia(int silaboEventoId) {
        iCRMEdu.getiCRMEdu(getActivity()).pasarAsistencia(silaboEventoId);
    }

    @Override
    public void onClickUnidadAprendizaje(UnidadAprendizajeUi unidadAprendizajeUi) {
        presenter.onClickUnidadAprendizaje(unidadAprendizajeUi);
    }

    @Override
    public void onClickSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi) {
        presenter.onClickSesionAprendizaje(sesionAprendizajeUi);
    }

    @Override
    public void notifyChangeFragment(boolean finishUpdateUnidadFb) {
        presenter.notifyChangeFragment(finishUpdateUnidadFb);
    }
}
