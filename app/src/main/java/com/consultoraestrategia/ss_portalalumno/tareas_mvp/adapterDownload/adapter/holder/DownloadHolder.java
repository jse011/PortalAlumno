package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.holder;

import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioEstadoFileU;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioTipoFileU;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class DownloadHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private final ProgressBar progressBar2;
    private final ImageView imgFondoprogres;
    private final ImageView imgClose;
    private final CircularProgressBar progressSucces;
    private final ImageView imgDownload;
    private final TextView txtNombreRecurso;
    private final ImageView imgRecurso;
    private final TextView txtdescripcion;
    private RepositorioFileUi repositorioFileUi;
    private DownloadItemListener listener;

    public DownloadHolder(View itemView) {
        super(itemView);
        txtNombreRecurso = (TextView)itemView.findViewById(R.id.txtNombreRecurso);
        imgRecurso = (ImageView)itemView.findViewById(R.id.imgRecurso);
        progressBar2 = (ProgressBar)itemView.findViewById(R.id.progressBar2);
        imgFondoprogres = (ImageView)itemView.findViewById(R.id.img_fondo_progres);
        imgClose = (ImageView)itemView.findViewById(R.id.img_close);
        progressSucces = (CircularProgressBar)itemView.findViewById(R.id.progress_succes);
        imgDownload = (ImageView)itemView.findViewById(R.id.img_download);
        txtdescripcion = (TextView)itemView.findViewById(R.id.txtdescripcion);
        imgDownload.setOnClickListener(this);
        imgClose.setOnClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void bind(RepositorioFileUi repositorioFileUi, DownloadItemListener repositorioItemListener) {
        this.repositorioFileUi = repositorioFileUi;
        this.listener = repositorioItemListener;
        txtNombreRecurso.setText(repositorioFileUi.getNombreRecurso());
        txtdescripcion.setText(repositorioFileUi.getNombreArchivo());
        setupEstado(repositorioFileUi.getEstadoFileU());
        setupIcono(repositorioFileUi.getTipoFileU());
    }


    private void setupEstado(RepositorioEstadoFileU estadoFileU) {
        hideDowload();
        hiProgress();
        hiProgressSuccess();
        switch (estadoFileU){
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
        }
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
                Glide.with(itemView.getContext())
                        .asBitmap()
                        .load(repositorioFileUi.getUrl())
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .centerCrop()
                        ).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        imgRecurso.setImageDrawable(ContextCompat.getDrawable(itemView.getContext(), R.drawable.ext_img));
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(imgRecurso);

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
        imgFondoprogres.setVisibility(View.VISIBLE);
    }

    private void hiProgress(){
        progressBar2.setVisibility(View.GONE);
        imgClose.setVisibility(View.GONE);
        imgFondoprogres.setVisibility(View.GONE);
    }

    private void showProgressSuccess(){
        progressSucces.setVisibility(View.VISIBLE);
        imgClose.setVisibility(View.VISIBLE);
        imgFondoprogres.setVisibility(View.VISIBLE);
    }

    private void hiProgressSuccess(){
        progressSucces.setVisibility(View.GONE);
        imgClose.setVisibility(View.GONE);
        imgFondoprogres.setVisibility(View.GONE);
    }

    private void showDowload(){
        imgFondoprogres.setVisibility(View.VISIBLE);
        imgDownload.setVisibility(View.VISIBLE);
    }

    private void hideDowload(){
        imgFondoprogres.setVisibility(View.GONE);
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
        listener.onClickArchivo(repositorioFileUi);
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
        if (imgFondoprogres.getVisibility() == View.GONE) {
            imgFondoprogres.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            default:
                PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);
                // Inflate the menu from xml
                popup.getMenu().add(Menu.NONE, 1, 1, "volver a descargar");
                // Setup menu item selection
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 1:
                                switch (repositorioFileUi.getTipoFileU()){
                                    case VINCULO:
                                        break;
                                    case YOUTUBE:
                                        repositorioFileUi.setPath("");
                                        listener.onClickArchivo(repositorioFileUi);
                                        break;
                                    default:
                                        repositorioFileUi.setEstadoFileU(RepositorioEstadoFileU.SIN_DESCARGAR);
                                        onClickDownload();
                                        break;
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // Show the menu
                popup.show();
                break;
        }

        return false;
    }
}