package com.consultoraestrategia.ss_portalalumno.actividades.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.ActividadListener;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateFirebaseActividades;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.global.offline.OfflineFirebase;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.ActividadesPresenter;
import com.consultoraestrategia.ss_portalalumno.actividades.ActividadesPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.ActividadesAdapter;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.local.ActividadesLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.remote.ActividadesRemoteDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetActividadesList;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateActividad;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by kike on 07/02/2018.
 */

public class ActividadesFragment extends Fragment implements ActividadesView, ActividadListener, DownloadItemListener {
    private static String TAG = ActividadesFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.rv_actividades)
    RecyclerView recyclerView;
    ActividadesPresenter presenter;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txtmensaje)
    TextView txtmensaje;

    ActividadesAdapter actividadesAdapter;


    public static ActividadesFragment newInstance(Bundle args) {
        ActividadesFragment fragment = new ActividadesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.actividades_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    private void initPresenter() {
        ActividadesRepository repository = new ActividadesRepository(new ActividadesLocalDataSource(), new ActividadesRemoteDataSource());
        presenter = new ActividadesPresenterImpl(
                new UseCaseHandler(new UseCaseThreadPoolScheduler()),
                new GetActividadesList(repository),
                new UpdateActividad(repository),
                new DowloadImageUseCase(repository),
                new UpdateSuccesDowloadArchivo(repository),
                new UpdateFirebaseActividades(repository),
                new OfflineFirebase(getContext())
        );
        setPresenter(presenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initView();
        presenter.onViewCreated();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        actividadesAdapter = new ActividadesAdapter(new ArrayList<Object>(), this, this, recyclerView);
        recyclerView.setAdapter(actividadesAdapter);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(CharSequence message) {

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
    public void showListObject(List<Object> objectList) {
        Log.d(TAG, "showListObject :" + objectList.size());
        if (actividadesAdapter != null) actividadesAdapter.setActividadList(objectList);
    }

    @Override
    public void addActividades(Object item) {
        actividadesAdapter.addActividades(item);
    }

    @Override
    public void clearActividades() {
        actividadesAdapter.clearActividades();
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Log.d(TAG, "MOSTRARMENSAJE" + mensaje);
        Snackbar.make(recyclerView, mensaje, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showMessage() {
        txtmensaje.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMessage() {
        txtmensaje.setVisibility(View.GONE);
    }

    @Override
    public void showVinculo(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir el vínculo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showYoutube(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al abrir el vínculo de youtube", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void leerArchivo(String path) {
        try {
                if (!TextUtils.isEmpty(path))
                OpenIntents.openFile(FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", new File(path)), getContext());

        }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), getContext().getString(R.string.cannot_open_file), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setUpdate(RecursosUi repositorioFileUi) {
        if (actividadesAdapter != null) actividadesAdapter.update(repositorioFileUi);
    }

    @Override
    public void setUpdateProgress(RecursosUi repositorioFileUi, int count) {
        if (actividadesAdapter != null) actividadesAdapter.updateProgress(repositorioFileUi, count);
    }


    @Override
    public void setPresenter(ActividadesPresenter presenter) {
        presenter.attachView(this);
        presenter.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onClickActividad(ActividadesUi actividadesUi) {
        presenter.onSelectActividad(actividadesUi);
    }

    @Override
    public void onClickDownload(RecursosUi repositorioFileUi) {
        Log.d(TAG, "onClickDownload: " + repositorioFileUi.getUrl());
        presenter.onClickDownload(repositorioFileUi);
    }

    @Override
    public void onClickClose(RecursosUi repositorioFileUi) {
        presenter.onClickClose(repositorioFileUi);
    }

    @Override
    public void onClickArchivo(RecursosUi repositorioFileUi) {
        presenter.onClickArchivo(repositorioFileUi);
    }
}
