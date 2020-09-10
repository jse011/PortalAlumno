package com.consultoraestrategia.ss_portalalumno.actividades.preview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.RecursosAdapter;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.ActividadesRepository;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.local.ActividadesLocalDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.data.source.remote.ActividadesRemoteDataSource;
import com.consultoraestrategia.ss_portalalumno.actividades.domain.usecase.GetActividadesList;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseHandler;
import com.consultoraestrategia.ss_portalalumno.base.UseCaseThreadPoolScheduler;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragment;
import com.consultoraestrategia.ss_portalalumno.base.fragment.BaseFragmentListener;
import com.consultoraestrategia.ss_portalalumno.gadgets.justify.JustifiedTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActividadYoutubePreview extends BaseFragment<ActividadYoutubeView, ActividadYoutubePresenter, BaseFragmentListener> implements ActividadYoutubeView, DownloadItemListener {

    @BindView(R.id.txt_titulo_youtube2)
    TextView txtTituloYoutube2;
    @BindView(R.id.txt_descripcion_youtube2)
    TextView txtDescripcionYoutube2;
    @BindView(R.id.txt_actividad_det)
    TextView txtActividadDet;
    @BindView(R.id.cont_img)
    ConstraintLayout contImg;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.txt_descripcion_act_det)
    JustifiedTextView txtDescripcionActDet;
    @BindView(R.id.rv_act_recursos)
    RecyclerView rvActRecursos;
    @BindView(R.id.conten_actividad)
    ConstraintLayout contenActividad;
    @BindView(R.id.list)
    LinearLayout list;
    @BindView(R.id.textView6)
    TextView linea1;
    @BindView(R.id.textView4)
    TextView linea2;

    private RecursosAdapter recursosAdapter;

    @Override
    protected String getLogTag() {
        return "ActividadYoutubePreviewTAG";
    }

    @Override
    protected ActividadYoutubePresenter getPresenter() {
        ActividadesRepository actividadesRepository = new ActividadesRepository(
                new ActividadesLocalDataSource(),
                new ActividadesRemoteDataSource());
        return new ActividadYoutubePresenterImp(new UseCaseHandler(new UseCaseThreadPoolScheduler()), getResources(),
                new GetActividadesList(actividadesRepository));
    }

    @Override
    protected ActividadYoutubeView getBaseView() {
        return this;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_actividad_youtube, container, false);
    }

    @Override
    protected ViewGroup getRootLayout() {
        return null;
    }

    @Override
    protected ProgressBar getProgressBar() {
        return null;
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
    public void setDescripcionVideo(String nombreRecurso, String url, String color) {
        txtTituloYoutube2.setText(nombreRecurso);
        txtDescripcionYoutube2.setText(url);
        //txtDescripcionYoutube2.setTextColor(Color.parseColor(color));
        //Linkify.addLinks(txtDescripcionYoutube2, Linkify.WEB_URLS);
        txtDescripcionYoutube2.setPaintFlags(txtDescripcionYoutube2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        //
        txtDescripcionYoutube2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        //txtDescripcionYoutube2.setTextColor(txtDescripcionYoutube2.getTextColors().getDefaultColor());
    }

    @Override
    public void setListActividad(ActividadesUi actividadesUi, int position) {
        txtActividadDet.setText(actividadesUi.getNombreActividad());

        txtDescripcionActDet.setText(actividadesUi.getDescripcionActividad());

        rvActRecursos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvActRecursos.setHasFixedSize(false);
        rvActRecursos.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) rvActRecursos.getItemAnimator()).setSupportsChangeAnimations(false);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(actividadesUi.getRecursosUiList());
        objects.addAll(actividadesUi.getSubRecursosUiList());
        recursosAdapter = new RecursosAdapter(objects, position, this);
        rvActRecursos.setAdapter(recursosAdapter);
        contenActividad.setVisibility(View.VISIBLE);
        Drawable circle = ContextCompat.getDrawable(getContext(), R.drawable.circle_actividad);
        try {
            circle.mutate().setColorFilter(Color.parseColor(actividadesUi.getColor1()), PorterDuff.Mode.SRC_ATOP);
            txtActividadDet.setTextColor(Color.parseColor(actividadesUi.getColor2()));
            //linea1.setBackgroundColor(Color.parseColor(actividadesUi.getColor3()));
            // linea2.setBackgroundColor(Color.parseColor(actividadesUi.getColor3()));
        }catch (Exception e){
            e.printStackTrace();
        }
        contImg.setBackground(circle);

    }

    @Override
    public void showButonsDrive() {

    }

    @Override
    public void onClickDownload(RecursosUi repositorioFileUi) {

    }

    @Override
    public void onClickClose(RecursosUi repositorioFileUi) {

    }

    @Override
    public void onClickArchivo(RecursosUi repositorioFileUi) {

    }
}
