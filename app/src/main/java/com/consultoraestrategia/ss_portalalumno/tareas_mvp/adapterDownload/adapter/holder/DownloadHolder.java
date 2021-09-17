package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.holder;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioTipoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.util.LinkUtils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.img_fondo_progres)
    ImageView imgFondoProgres;
    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.progress_succes)
    CircularProgressBar progressSucces;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_download)
    ImageView imgDownload;
    @BindView(R.id.imgRecurso)
    ImageView imgRecurso;
    @BindView(R.id.conten_recurso)
    ConstraintLayout contenRecurso;
    @BindView(R.id.txtNombreRecurso)
    TextView txtNombreRecurso;
    @BindView(R.id.txtdescripcion)
    TextView txtdescripcion;
    @BindView(R.id.card_view)
    CardView cardView;

    private RepositorioFileUi repositorioFileUi;
    private DownloadItemListener listener;

    public DownloadHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        imgDownload.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    public void bind(RepositorioFileUi repositorioFileUi, DownloadItemListener repositorioItemListener) {
        this.repositorioFileUi = repositorioFileUi;
        this.listener = repositorioItemListener;
        txtNombreRecurso.setText(repositorioFileUi.getNombreRecurso());
        if(repositorioFileUi.getTipoFileU()==RepositorioTipoFileU.YOUTUBE&&
                repositorioFileUi.getTipoFileU()==RepositorioTipoFileU.VINCULO){
            txtdescripcion.setText(repositorioFileUi.getUrl());
        }else {
            txtdescripcion.setText(repositorioFileUi.getDescripcion());
        }

        setupEstado(repositorioFileUi.getEstadoFileU());
        setupIcono(repositorioFileUi.getTipoFileU());
        LinkUtils.autoLink(txtdescripcion, null);
        try {
            cardView.setCardBackgroundColor(Color.parseColor(((RecursosUI)repositorioFileUi).getColor1()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setupEstado(RepositorioEstadoFileU estadoFileU) {
        hideDowload();
        hiProgress();
        hiProgressSuccess();
        /*switch (estadoFileU){
            case SIN_DESCARGAR:
                showDowload();
                break;
            case CONECTANDO_SERVER:
                showProgress();
                break;
            case ENPROCESO_DESCARGA:
                showProgressSuccess();
                break;
            case ERROR_DESCARGA:
                showDowload();
                break;
            case DESCARGA_COMPLETA:

                break;
        }*/
    }

    private void setupIcono(RepositorioTipoFileU tipoFileU) {
        switch (tipoFileU){
            case PDF:
                imgRecurso.setImageResource(R.drawable.ext_pdf);
                break;
            case AUDIO:
                imgRecurso.setImageResource(R.drawable.ext_aud);
                break;
            case YOUTUBE:
                imgRecurso.setImageResource(R.drawable.youtube);
                break;
            case VIDEO:
                imgRecurso.setImageResource(R.drawable.ext_vid);
                break;
            case IMAGEN:
                imgRecurso.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ext_img));
                break;
            case VINCULO:
                imgRecurso.setImageResource(R.drawable.ext_link);
                break;
            case DOCUMENTO:
                imgRecurso.setImageResource(R.drawable.ext_doc);
                break;
            case DIAPOSITIVA:
                imgRecurso.setImageResource(R.drawable.ext_ppt);
                break;
            case HOJA_CALCULO:
                imgRecurso.setImageResource(R.drawable.ext_xls);
                break;
            case MATERIALES:
                imgRecurso.setImageResource(R.drawable.ext_other);
                break;
        }

    }

    private void showProgress(){
        progressBar2.setVisibility(View.VISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgFondoProgres.setVisibility(View.VISIBLE);
    }

    private void hiProgress(){
        progressBar2.setVisibility(View.GONE);
        imgClose.setVisibility(View.GONE);
        imgFondoProgres.setVisibility(View.GONE);
    }

    private void showProgressSuccess(){
        progressSucces.setVisibility(View.VISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgFondoProgres.setVisibility(View.VISIBLE);
    }

    private void hiProgressSuccess(){
        progressSucces.setVisibility(View.GONE);
        imgClose.setVisibility(View.GONE);
        imgFondoProgres.setVisibility(View.GONE);
    }

    private void showDowload(){
        imgFondoProgres.setVisibility(View.VISIBLE);
        imgDownload.setVisibility(View.VISIBLE);
    }

    private void hideDowload(){
        imgFondoProgres.setVisibility(View.GONE);
        imgDownload.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
       if(i == R.id.img_close){
            onClickClose();
        }else if(i == R.id.img_download){
            onClickDownload();
        }else{
            onClickArchivo();
        }
    }

    private void onClickArchivo() {
        if(repositorioFileUi.getTipoFileU() != RepositorioTipoFileU.VINCULO){
            listener.onClickArchivo(repositorioFileUi);
        }else {
            LinkUtils.SensibleLinkMovementMethod sensibleLinkMovementMethod = (LinkUtils.SensibleLinkMovementMethod)txtdescripcion.getMovementMethod();
            listener.onClickOpenLinkArchivo(repositorioFileUi, sensibleLinkMovementMethod.getClickedLink());

        }
    }

    private void onClickDownload() {
        repositorioFileUi.setEstadoFileU(RepositorioEstadoFileU.CONECTANDO_SERVER);
        listener.onClickDownload(repositorioFileUi);
        setupEstado(this.repositorioFileUi.getEstadoFileU());
    }

    private void onClickClose() {
        hiProgress();
        showDowload();
        listener.onClickClose(repositorioFileUi);
    }

    public void count(int count) {
        if (progressSucces.getVisibility() == View.GONE) {
            progressSucces.setVisibility(View.VISIBLE);
        }
        if (progressBar2.getVisibility() == View.VISIBLE) {
            progressBar2.setVisibility(View.GONE);
        }
        if (imgFondoProgres.getVisibility() == View.GONE) {
            imgFondoProgres.setVisibility(View.VISIBLE);
        }
        if (imgClose.getVisibility() == View.GONE) {
            imgClose.setVisibility(View.VISIBLE);
        }
        if (imgDownload.getVisibility() == View.VISIBLE) {
            imgDownload.setVisibility(View.GONE);
        }
        progressSucces.setProgress(count);

    }

    public RepositorioFileUi getRepositorioFileUi() {
        return repositorioFileUi;
    }

}