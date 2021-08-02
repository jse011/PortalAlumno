package com.consultoraestrategia.ss_portalalumno.main.dialog;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.base.viewpager.LifecycleImpl;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoArchivo;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreview2Fragment;
import com.consultoraestrategia.ss_portalalumno.previewDrive.multimedia.MultimediaPreviewView;
import com.consultoraestrategia.ss_portalalumno.util.IntentHelper;
import com.consultoraestrategia.ss_portalalumno.util.LinkUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.consultoraestrategia.ss_portalalumno.util.UtilsStorage;
import com.consultoraestrategia.ss_portalalumno.youtube.YoutubeConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class DialogPreviewAdjuntoEvento extends DialogFragment implements MainView.AdjuntoPreviewEvento, LinkUtils.OnClickListener, LifecycleImpl.LifecycleListener {
    @SuppressLint("InlinedApi")
    private static final int PORTRAIT_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    @SuppressLint("InlinedApi")
    private static final int LANDSCAPE_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

    @BindView(R.id.nombrePersonaEvento)
    TextView textNombre;
    @BindView(R.id.textView5)
    TextView textContenido;
    @BindView(R.id.fechaPublicacion)
    TextView fechaPublicacion;
    @BindView(R.id.nombre_directivo)
    TextView nombreDirectivo;
    @BindView(R.id.content_evento)
    ViewGroup content_evento;
    @BindView(R.id.circleImageView)
    ImageView imagenEntidad;
    @BindView(R.id.btn_me_gusta)
    ConstraintLayout btnMeGusta;
    @BindView(R.id.btn_me_compartir)
    ConstraintLayout btnMeCompartir;
    @BindView(R.id.txt_titulo)
    TextView txtTitulo;
    @BindView(R.id.cont_me_gusta)
    ConstraintLayout contMeGusta;
    @BindView(R.id.directivo)
    TextView directivo;
    @BindView(R.id.txt_megusta)
    TextView txtMegusta;
    @BindView(R.id.img_megusta)
    ImageView imgMegusta;
    @BindView(R.id.pie_pagina)
    View piePagina;
    @BindView(R.id.cabecera)
    View cabecera;

    private Unbinder bind;
    private MainPresenter presenter;
    private EventoUi.AdjuntoUi adjuntoUi;
    private YoutubeConfig youtubeConfig;


    public static DialogPreviewAdjuntoEvento newInstance() {
        DialogPreviewAdjuntoEvento fragment = new DialogPreviewAdjuntoEvento();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_informacion_evento, container, false);
        bind = ButterKnife.bind(this, v);
        getChildFragmentManager().registerFragmentLifecycleCallbacks(new LifecycleImpl(0, this), true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            cabecera.setVisibility(View.VISIBLE);
            piePagina.setVisibility(View.VISIBLE);
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            cabecera.setVisibility(View.GONE);
            piePagina.setVisibility(View.GONE);
        }
        //if(youtubeConfig!=null)youtubeConfig.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.getDialog().getWindow()
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        this.getDialog().getWindow().
                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @OnClick({R.id.backPrincipal, R.id.btn_me_gusta, R.id.btn_me_compartir})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backPrincipal:
                dismiss();
                break;
            case R.id.btn_me_gusta:
                if(presenter!=null) presenter.onClikLikeInfoEvento();
                break;
            case R.id.btn_me_compartir:
                if(presenter!=null) presenter.onClikLikeInfoCompartir();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        youtubeConfig = null;
    }

    @Override
    public void showInformacionEventoAjunto(EventoUi eventosUi, EventoUi.AdjuntoUi adjuntoUi) {
        textContenido.setText(Html.fromHtml(eventosUi.getDescripcion()));
        LinkUtils.autoLink(textContenido, this);
        txtTitulo.setText(eventosUi.getTitulo());
        this.adjuntoUi = adjuntoUi;

        if(adjuntoUi.getTipoArchivo()== TipoArchivo.VIDEO){
            // Begin the transaction
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.content_youtube, new MultimediaPreview2Fragment());
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        } else if(adjuntoUi.getTipoArchivo() == TipoArchivo.YOUTUBE){
            if (youtubeConfig == null) youtubeConfig = new YoutubeConfig(getContext());
            //youtubeConfig.setDisabledRotation(true);
            youtubeConfig.initialize(adjuntoUi.getYotubeId(), getChildFragmentManager(), R.id.content_youtube, new YoutubeConfig.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_pause_youtube));
                }

                @Override
                public void onPaused() {
                    //imgActionYoutube.setImageDrawable(ContextCompat.getDrawable(imgActionYoutube.getContext(), R.drawable.ic_play_youtube));
                }

                @Override
                public void onLandscape() {

                }

                @Override
                public void onPortrait() {

                }
            });
        } else {
            // Begin the transaction
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.content_evento, PreviewFragment.newInstance(adjuntoUi.getImagePreview()));
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }


        textNombre.setText(eventosUi.getNombreEntidad());
        if (!TextUtils.isEmpty(eventosUi.getFotoEntidad())){
            //imagenEntidad.setVisibility(View.VISIBLE);
                    Glide.with(imagenEntidad)
                            .load(eventosUi.getFotoEntidad())
                            .apply(UtilsGlide.getGlideRequestOptionsSimple())
                            .into(imagenEntidad);
        } else {
            imagenEntidad.setImageDrawable(null);
            ///imagenEntidad.setVisibility(View.GONE);
        }

        directivo.setText(eventosUi.getRolEmisor());
        nombreDirectivo.setText(eventosUi.getNombreEmisor());


        changeLike(eventosUi);

        if (eventosUi.getTipoEventoUi().getTipo()== TipoEventoUi.EventoIconoEnumUI.AGENDA){
            String nombre = eventosUi.getNombreCalendario();
            directivo.setText(nombre);
        }else {
            directivo.setText(eventosUi.getRolEmisor());
        }

        String tipo = eventosUi.getTipoEventoUi().getNombre();

        String fecha = tipo + " " +eventosUi.getNombreFecha();
        fechaPublicacion.setText(fecha);
    }

    @Override
    public void changeLike(EventoUi eventosUi) {
        String megusta = "me gusta";
        if(eventosUi.getCantLike()!=0){
            megusta =  eventosUi.getCantLike() + " me gusta";
        }else if(eventosUi.getCantLike()>1000){
            megusta += "1k me gusta" ;
        }

        if(eventosUi.isLike()){
            imgMegusta.setImageDrawable(ContextCompat.getDrawable(imgMegusta.getContext(),R.drawable.ic_like ));
        }else {
            imgMegusta.setImageDrawable(ContextCompat.getDrawable(imgMegusta.getContext(),R.drawable.ic_like_disabled ));
        }
        txtMegusta.setText(megusta);

    }

    public void showCompartirEvento(EventoUi eventosUi) {
        Glide
                .with(getContext())
                .asBitmap()
                .load(eventosUi.getFoto())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        IntentHelper.sendEmail(getContext(),null,eventosUi.getTitulo(), eventosUi.getDescripcion(), null);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        IntentHelper.sendEmail(getContext(),null,eventosUi.getTitulo(), eventosUi.getDescripcion(), UtilsPortalAlumno.getImageUri(getContext(), resource));
                    }
                });
    }

    public void hideInformacionEvento() {
        dismiss();
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLinkClicked(View v, String link) {

    }

    @Override
    public void onClicked(View v) {

    }

    @Override
    public void onChildsFragmentViewCreated() {

    }

    @Override
    public void onFragmentViewCreated(Fragment f, View view, Bundle savedInstanceState) {
       getActivity().setRequestedOrientation(PORTRAIT_ORIENTATION);
    }


    @Override
    public void onFragmentResumed(Fragment f) {

    }

    @Override
    public void onFragmentViewDestroyed(Fragment f) {
        if (f instanceof MultimediaPreviewView) {

        }
        getActivity().setRequestedOrientation(PORTRAIT_ORIENTATION);
    }

    @Override
    public void onFragmentActivityCreated(Fragment f, Bundle savedInstanceState) {
        if (f instanceof MultimediaPreviewView) {
            if(adjuntoUi!=null){
                MultimediaPreviewView multimediaPreviewView = (MultimediaPreviewView)f;
                multimediaPreviewView.hideProgress();
                String extencion = UtilsStorage.getExtencion(adjuntoUi.getTitulo());
                if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp3") || extencion.toLowerCase().contains("ogg") || extencion.toLowerCase().contains("wav"))) {
                    multimediaPreviewView.uploadAudio(adjuntoUi.getDriveId());
                }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mp4"))) {
                    multimediaPreviewView.uploadMp4(adjuntoUi.getDriveId());
                }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("flv"))) {
                    multimediaPreviewView.uploadFlv(adjuntoUi.getDriveId());
                }else if (!TextUtils.isEmpty(extencion) && (extencion.toLowerCase().contains("mkv"))) {
                    multimediaPreviewView.uploadMkv(adjuntoUi.getDriveId());
                }else {
                    multimediaPreviewView.uploadArchivo(adjuntoUi.getDriveId());
                }
                multimediaPreviewView.dimensionRatio(0, 0);
            }

        }
    }

    public static class PreviewFragment extends Fragment {
        private Unbinder bind;
        @BindView(R.id.imagenEvento)
        ImageView imagenEvento;

        public static PreviewFragment newInstance(String imagenUrl) {
            Bundle args = new Bundle();
            args.putString("imagenUrl", imagenUrl);
            PreviewFragment fragment = new PreviewFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_preview_evento_image, container, false);
            bind = ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Glide.with(imagenEvento.getContext())
                    .load(getArguments().getString("imagenUrl"))
                    .apply(UtilsGlide.getGlideRequestOptionsSimple())
                    .into(imagenEvento);
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            bind.unbind();

        }

    }
}

