package com.consultoraestrategia.ss_portalalumno.pregunta.lista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.consultoraestrategia.ss_portalalumno.pregunta.adapter.PreguntasAdapter;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorio;
import com.consultoraestrategia.ss_portalalumno.pregunta.data.source.PreguntaRepositorioImpl;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.principal.PreguntaPrincipalActivity;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetListPregunta;
import com.consultoraestrategia.ss_portalalumno.pregunta.useCase.GetPreguntaEvaluacion;
import com.consultoraestrategia.ss_portalalumno.tabsSesiones.fragments.TabSesionPreguntaView;

import java.util.List;

import butterknife.BindView;

public class PreguntaFragment extends BaseFragment<PreguntaView, PreguntaPresenter, BaseFragmentListener> implements PreguntaView, TabSesionPreguntaView, PreguntasAdapter.Listener {
    private static final String TAG = "PreguntaFragmentTAG";
    @BindView(R.id.rc_pregunta)
    RecyclerView rcPregunta;
    @BindView(R.id.root)
    ConstraintLayout root;
    @BindView(R.id.progressBar7)
    ProgressBar progressBar7;
    @BindView(R.id.conten_empty)
    ViewGroup contenEmpty;
    private PreguntasAdapter preguntaAdapter;

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected PreguntaPresenter getPresenter() {
        PreguntaRepositorio preguntaRepositorio = new PreguntaRepositorioImpl();
        return new PreguntaPresenteImpl(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new AndroidOnlineImpl(getContext()),
                new GetPreguntaEvaluacion(preguntaRepositorio),
                new GetListPregunta(preguntaRepositorio));
    }

    @Override
    protected PreguntaView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_pregunta, container, false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        preguntaAdapter = new PreguntasAdapter(this);
        rcPregunta.setAdapter(preguntaAdapter);
        rcPregunta.setLayoutManager(new LinearLayoutManager(getContext()));
        ((SimpleItemAnimator) rcPregunta.getItemAnimator()).setSupportsChangeAnimations(false);

        contenEmpty.postDelayed(new Runnable() {
            @Override
            public void run() {
                validarSiEstaVacio();
            }
        }, 6000);
    }

    private void validarSiEstaVacio(){
        if(preguntaAdapter.getItemCount()==0){
            if(contenEmpty!=null)contenEmpty.setVisibility(View.VISIBLE);
        }
        else{
            if(contenEmpty!=null)contenEmpty.setVisibility(View.GONE);
        }

    }

    @Override
    protected ViewGroup getRootLayout() {
        return root;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return progressBar7;
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
    public void addPregunta(String preguntaPAId) {
        presenter.addPreguntaFirebase(preguntaPAId);
    }

    @Override
    public void updatePregunta(String preguntaPAId) {
        presenter.updatePreguntaFirebase(preguntaPAId);
    }

    @Override
    public void removePregunta(String preguntaPAId) {
        presenter.removePreguntaFirebase(preguntaPAId);
    }

    @Override
    public void updatePreguntaAlumno(String preguntaPAId, int alumnoId) {
        presenter.updatePreguntaAlumno(preguntaPAId, alumnoId);
    }

    @Override
    public void onInitPreguntaList(boolean online) {
        presenter.onInitPreguntaListFirebase(online);
    }

    @Override
    public void updatePreguntaListView(PreguntaUi preguntaUi) {
        preguntaAdapter.updateOrSave(preguntaUi);
        validarSiEstaVacio();
    }

    @Override
    public void removePreguntaListView(PreguntaUi preguntaUi) {
        preguntaAdapter.remover(preguntaUi);
        validarSiEstaVacio();
    }

    @Override
    public void clearAlumnoList() {
        preguntaAdapter.clear();
        validarSiEstaVacio();
    }

    @Override
    public void showActivityPreguntaPrincial() {
        Log.d(TAG, "showActivityPreguntaPrincial");
        startActivity(new Intent(getContext(), PreguntaPrincipalActivity.class));
    }

    @Override
    public void setListPregunta(List<PreguntaUi> preguntaUiList) {
        preguntaAdapter.setList(preguntaUiList);
        validarSiEstaVacio();
    }

    @Override
    public void onClickPregunta(PreguntaUi preguntaUi) {
        Log.d(getTag(), "onClickPregunta");
        presenter.onClickPregunta(preguntaUi);
    }

    @Override
    public void onDestroyView() {
        contenEmpty=null;
        super.onDestroyView();
    }
}
