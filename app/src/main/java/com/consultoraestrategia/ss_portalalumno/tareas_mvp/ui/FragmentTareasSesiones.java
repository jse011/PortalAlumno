package com.consultoraestrategia.ss_portalalumno.tareas_mvp.ui;

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
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.firebase.online.AndroidOnlineImpl;
import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnGridLayoutManager;
import com.consultoraestrategia.ss_portalalumno.retrofit.ApiRetrofit;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFireBaseTareaSesion;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateFireBaseTareaSilabo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.tareaDescripcion.TareaDescripcionActivity;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.TareasMvpPresenter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.TareasMvpPresenterImpl;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.TareasMvpView;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.TareaColumnCountProvider;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.TareasAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.TareasMvpRepository;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.local.TareasLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.data_source.remote.RemoteMvpDataSource;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.DowloadImageUseCase;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.GetTareasUIList;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.MoverArchivosAlaCarpetaTarea;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.domain_usecase.UpdateSuccesDowloadArchivo;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RubroEvalProcesoUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.UnidadAprendizajeListener;
import com.consultoraestrategia.ss_portalalumno.util.OpenIntents;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irvinmarin on 10/11/2017.
 */

public class FragmentTareasSesiones extends Fragment implements TareasMvpView, UnidadAprendizajeListener, TareasUIListener {

    private static final String TAG = FragmentTareasSesiones.class.getSimpleName();
    View viewPadre;

    @BindView(R.id.rvSesionesMvp)
    RecyclerView rvUnidades;
    @BindView(R.id.txtmensaje)
    TextView txtmensaje;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar3;

    TareasMvpPresenter presenter;
    private TareasAdapter tareasAdapter;


    public static FragmentTareasSesiones newInstanceSesion() {
        Bundle bundle = new Bundle();
        bundle.putInt(FragmentTareas.tipoTareas, 1);
        FragmentTareasSesiones fragment = new FragmentTareasSesiones();
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPadre = inflater.inflate(R.layout.tab_item_tarea_mvp_sesiones, container, false);
        ButterKnife.bind(this, viewPadre);
        setupPresenter();
        setupRvUnidad();
        return viewPadre;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void setupPresenter() {

        TareasMvpRepository tareasMvpRepository = TareasMvpRepository.getInstace(
                new TareasLocalDataSource(),
                new RemoteMvpDataSource(ApiRetrofit.getInstance()));
        presenter = new TareasMvpPresenterImpl(
                new UseCaseHandler(
                        new UseCaseThreadPoolScheduler()),
                new GetTareasUIList(tareasMvpRepository),
                new DowloadImageUseCase(tareasMvpRepository),
                new UpdateSuccesDowloadArchivo(tareasMvpRepository),
                new MoverArchivosAlaCarpetaTarea(TareasMvpRepository.getInstace(
                        new TareasLocalDataSource(),
                        new RemoteMvpDataSource(ApiRetrofit.getInstance()))

                ),
                
                new UpdateFireBaseTareaSilabo(tareasMvpRepository),
                new UpdateFireBaseTareaSesion(tareasMvpRepository),
                new AndroidOnlineImpl(getContext()));

        setPresenter(presenter);
        Log.d(TAG, "onCreate");

    }


    private void setupRvUnidad() {
        AutoColumnGridLayoutManager autoColumnGridLayoutManager = new AutoColumnGridLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        TareaColumnCountProvider columnCountProvider = new TareaColumnCountProvider(getContext());
        autoColumnGridLayoutManager.setColumnCountProvider(columnCountProvider);
        tareasAdapter = new TareasAdapter(new HeaderTareasAprendizajeUI(), this,new ParametroDisenioUi(),rvUnidades);
        rvUnidades.setLayoutManager(autoColumnGridLayoutManager);
        rvUnidades.setAdapter(tareasAdapter);
        //presenter.getTareas(mIdCargaCurso, mIdCurso, mSesionAprendizajeId,tipoTareas);
    }


    @Override
    public void setPresenter(TareasMvpPresenter presenter) {
        //presenter.setIdsNecesaries(mIdCurso, mIdCargaCurso, mSesionAprendizajeId);
        presenter.attachView(this);
        presenter.setExtras(getArguments());
        presenter.onCreate();
        setupRvUnidad();

    }

    @Override
    public void showTareasUIList(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList, int idCurso, ParametroDisenioUi parametroDisenioUi) {
        tareasAdapter.setParametroDisenioUi(parametroDisenioUi);
        if(!headerTareasAprendizajeUIList.isEmpty())tareasAdapter.setTareasUIList(headerTareasAprendizajeUIList.get(0));
    }

    @Override
    public void hideTareasUIList() {
        rvUnidades.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar3.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar3.setVisibility(View.GONE);
    }


    @Override
    public void showActivityCrearTareas(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idUsuario, int idSilaboEvento, int mSesionAprendizajeId, int mIdCurso, String colorCurso, int programaEducativoId) {

    }

    @Override
    public void showlActivityEditTareas(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idUsuario, int idSilaboEvento, int mSesionAprendizajeId, int mIdCurso, String color, int programaEducativoId) {

    }

    @Override
    public void exportarTareasEliminadas(int programaEducativoId) {

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
    public void onShowActualizarTareas() {

    }

    @Override
    public void showServiceExportTarea(int programaEducativoId) {

    }

    @Override
    public void showActivityCrearRubro(TareasUI tareasUI, int idSilaboEvento, int idCalendarioPeriodo, int programaEducativoId, int mSesionAprendizajeId, String colorCurso, int idCurso) {

    }

    @Override
    public void showActivityCrearRubrica(TareasUI tareasUI, int idCalendarioPeriodo, int idCargaCurso, int idCurso, int programaEducativoId, int mSesionAprendizajeId, String colorCurso) {

    }

    @Override
    public void showEvaluacionRubroGrupal(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, int mSesionAprendizajeId, int idCurso, String colorCurso) {

    }

    @Override
    public void showEvaluacionRubroIndividual(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, int mSesionAprendizajeId, int idCurso, String colorCurso) {

    }

    @Override
    public void onParentFabClicked() {
        presenter.onClickParentFabClicked();
    }

    @Override
    public void setUpdateProgress(RepositorioFileUi repositorioFileUi, int count) {
        tareasAdapter.updateProgress(repositorioFileUi, count);
    }

    @Override
    public void setUpdate(RepositorioFileUi repositorioFileUi) {
        tareasAdapter.update(repositorioFileUi);
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
    public void showVinculo(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error al abrir el vínculo", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showYoutube(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }catch (Exception e){
            Toast.makeText(getContext(), "Error al abrir el vínculo de youtube", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCrearTarea(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int idCargaCurso) {
        presenter.onClickedCrearTarea(headerTareasAprendizajeUI, headerTareasAprendizajeUI.getIdSilaboEvento(), idCargaCurso);
    }

    @Override
    public void showTareaDescripcionActivity() {
        startActivity(new Intent(getContext(), TareaDescripcionActivity.class));
    }

    @Override
    public void updateTarea(TareasUI tareasUI) {

    }


    @Override
    public void showEvaluacionRubricaIndividual(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, String colorCurso) {

    }

    @Override
    public void showEvaluacionRubricaGrupal(TareasUI tareasUI, RubroEvalProcesoUi rubroEvalProcesoUi, int idCargaCurso, String colorCurso) {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
//        Toast.makeText(getContext(), "OnResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBtnCrearTareaClicked(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, int idSilaboEvento, int mIdCurso) {
        presenter.onClickedCrearTarea(headerTareasAprendizajeUI, idSilaboEvento, mIdCurso);
    }



    @Override
    public void onReloadAdapter(HeaderTareasAprendizajeUI headerTareasAprendizajeUI, HeaderTareasAprendizajeUI nuevaHeaderTareasAprendizajeUI, int opcionAccion) {

        switch (opcionAccion) {
            case 0:
                showMsjTarea("Creada");
                break;
            case 1:
                showMsjTarea("Editada");
                break;
        }
        setupRvUnidad();

    }

    @Override
    public void onClickOpcion(View view, final HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {

    }

    private void showMsjTarea(String Msj) {
        Toast.makeText(getContext(), "Tarea " + Msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOpTareaEditClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        presenter.onClickedOpTareaEdit(tareasUI, headerTareasAprendizajeUI);
       // Log.d(TAG, "tareasUI edit : " + tareasUI);
    }

    @Override
    public void onOpTareaDelteClicked(TareasUI tareasUI) {
        presenter.deleteTarea(tareasUI);
    }

    @Override
    public void onOpNotificarTareasClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        int idCargaCurso = getArguments().getInt(FragmentTareas.mIdCargaCurso);

    }

    @Override
    public void onClikEstado(TareasUI tareasUI) {
        presenter.onChangeEstado(tareasUI);
    }

    @Override
    public void onOpCrearRubricaClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        presenter.onCrearRubrica(tareasUI, headerTareasAprendizajeUI);
    }

    @Override
    public void onOpCrearRubroClicked(TareasUI tareasUI, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        presenter.onCrearRubro(tareasUI, headerTareasAprendizajeUI);
    }

    @Override
    public void onClikRubroTarea(TareasUI tareasUI) {
        presenter.onClikRubroTarea(tareasUI);
    }

    @Override
    public void onClicTarea(TareasUI tareasUI) {
        presenter.onClickTarea(tareasUI);
    }


    public void onReloadFragmentTareas() {
        Log.d(TAG, "onReloadFragmentTareas");
        setupRvUnidad();
    }

    @Override
    public void onClickDownload(RepositorioFileUi repositorioFileUi) {
        presenter.onClickDownload(repositorioFileUi);
    }

    @Override
    public void onClickClose(RepositorioFileUi repositorioFileUi) {
        presenter.onClickClose(repositorioFileUi);
    }

    @Override
    public void onClickArchivo(RepositorioFileUi repositorioFileUi) {
        presenter.onClickArchivo(repositorioFileUi);
    }

}
