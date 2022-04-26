package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.gadgets.staticProgressBar.CustomProgress;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareaArchivoUi;
import com.consultoraestrategia.ss_portalalumno.util.LinkUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjuntoAdapter extends RecyclerView.Adapter<AdjuntoAdapter.ViewHolder> {

    private List<TareaArchivoUi> tareaArchivoUiList = new ArrayList<>();
    private Listener listener;

    public AdjuntoAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_archivo_adjunto, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tareaArchivoUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return tareaArchivoUiList.size();
    }

    public void setList(List<TareaArchivoUi> list) {
        this.tareaArchivoUiList.clear();
        this.tareaArchivoUiList.addAll(list);
        notifyDataSetChanged();
    }

    public List<TareaArchivoUi> getList() {
        return tareaArchivoUiList;
    }

    public void add(TareaArchivoUi tareaArchivoUi) {
        this.tareaArchivoUiList.add(tareaArchivoUi);
        notifyItemInserted(tareaArchivoUiList.size()-1);
    }

    public void update(TareaArchivoUi tareaArchivoUi) {
        int position = tareaArchivoUiList.indexOf(tareaArchivoUi);
        if(position!=-1){
            tareaArchivoUiList.set(position, tareaArchivoUi);
            notifyItemChanged(position);
        }
    }

    public void remove(TareaArchivoUi tareaArchivoUi) {
        int position = tareaArchivoUiList.indexOf(tareaArchivoUi);
        if(position!=-1){
            tareaArchivoUiList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, LinkUtils.OnClickListener {
        @BindView(R.id.imgRecurso)
        ImageView imgRecurso;
        @BindView(R.id.conten_recurso)
        ConstraintLayout contenRecurso;
        @BindView(R.id.txtNombreRecurso)
        TextView txtNombreRecurso;
        @BindView(R.id.txtdescripcion)
        TextView txtdescripcion;
        @BindView(R.id.btn_action)
        ImageView btnAction;
        @BindView(R.id.btn_recurso)
        ImageView btnRecurso;
        @BindView(R.id.customProgress)
        CustomProgress customProgress;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.progress)
        FrameLayout progress;
        @BindView(R.id.progressBar17)
        ProgressBar progressBarForceUpload;

        private Listener listener;
        private TareaArchivoUi tareaArchivoUi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TareaArchivoUi tareaArchivoUi, Listener listener) {
            this.listener = listener;
            this.tareaArchivoUi = tareaArchivoUi;
            cardView.setOnClickListener(this);
            btnAction.setOnClickListener(this);
            btnRecurso.setOnClickListener(this);
            txtNombreRecurso.setText(tareaArchivoUi.getNombre());
            txtdescripcion.setText(tareaArchivoUi.getDescripcion());
            LinkUtils.autoLink(txtdescripcion, this);
            float progress = (float) tareaArchivoUi.getProgress()/(float) 100;
            customProgress.setMaximumPercentage(progress);
            customProgress.setDisabledMovementProgress(true);
            customProgress.setProgressBackgroundColor(Color.TRANSPARENT);
            try {
                customProgress.setProgressColor(Color.parseColor(tareaArchivoUi.getColor()));
            }catch (Exception e){
                e.printStackTrace();
            }

            Log.d("AdjuntoAdapter", "progress: "+progress);

            if(tareaArchivoUi.isDisabled()){
                this.progress.setVisibility(View.VISIBLE);
                btnRecurso.setVisibility(View.GONE);
                btnAction.setVisibility(View.GONE);
                customProgress.setVisibility(View.GONE);
                progressBarForceUpload.setVisibility(View.GONE);
            }else {
                this.progress.setVisibility(View.GONE);
                 if(tareaArchivoUi.getFile()!=null){

                     if(tareaArchivoUi.getForceUpload()){
                         btnRecurso.setVisibility(View.GONE);
                         btnAction.setVisibility(View.GONE);
                         customProgress.setVisibility(View.GONE);
                         progressBarForceUpload.setVisibility(View.VISIBLE);
                         customProgress.updateView();
                     }else{
                         if(tareaArchivoUi.getState()==1){
                             btnAction.setImageResource(R.drawable.ic_pause_youtube);
                         }else {
                             btnAction.setImageResource(R.drawable.ic_play_recurso);
                         }
                         btnRecurso.setVisibility(View.VISIBLE);
                         btnAction.setVisibility(View.VISIBLE);
                         customProgress.setVisibility(View.VISIBLE);
                         progressBarForceUpload.setVisibility(View.GONE);
                         customProgress.updateView();
                     }

                }else {
                    btnRecurso.setVisibility(View.VISIBLE);
                    btnAction.setVisibility(View.GONE);
                    customProgress.setVisibility(View.GONE);
                     progressBarForceUpload.setVisibility(View.GONE);
                }
            }

            btnRecurso.setVisibility(tareaArchivoUi.getEntregado()?View.GONE:View.VISIBLE);

            switch (tareaArchivoUi.getTipo()) {
                case PDF:
                    imgRecurso.setImageResource(R.drawable.ext_pdf);
                    break;
                case AUDIO:
                    imgRecurso.setImageResource(R.drawable.ext_aud);
                    break;
                case VIDEO:
                    imgRecurso.setImageResource(R.drawable.ext_vid);
                    break;
                case IMAGEN:
                    imgRecurso.setImageResource(R.drawable.ext_img);
                    break;
                case PRESENTACION:
                    imgRecurso.setImageResource(R.drawable.ext_ppt);
                    break;
                case DOCUMENTO:
                    imgRecurso.setImageResource(R.drawable.ext_doc);
                    break;
                case HOJACALCULO:
                    imgRecurso.setImageResource(R.drawable.ext_xls);
                    break;
                case OTROS:
                    imgRecurso.setImageResource(R.drawable.not_av);
                    break;
                case YOUTUBE:
                    imgRecurso.setImageResource(R.drawable.youtube);
                    break;
                case DRIVE:
                    imgRecurso.setImageResource(R.drawable.ic_google_drive_logo);
                    break;
                case LINK:
                    imgRecurso.setImageResource(R.drawable.ext_link);
                    break;
                default:
                    imgRecurso.setImageResource(R.drawable.not_av);
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_action:
                    listener.onClickActionTareaArchivo(tareaArchivoUi);
                    break;
                case R.id.btn_recurso:
                    listener.onClickRemoveTareaArchivo(tareaArchivoUi);
                    break;
                case R.id.card_view:
                    if(tareaArchivoUi.getTipo() != TareaArchivoUi.Tipo.LINK){
                        listener.onClickOpenTareaArchivo(tareaArchivoUi);
                    }else {
                        List<String> urls = UtilsPortalAlumno.extractUrls(txtdescripcion.getText().toString());
                        if(urls.size()> 0){
                            listener.oClickOpenLink(tareaArchivoUi, urls.get(0));
                        }
                    }
                    break;
            }
        }

        @Override
        public void onLinkClicked(View v, String link) {
            if(tareaArchivoUi.getTipo() != TareaArchivoUi.Tipo.LINK){
                listener.onClickOpenTareaArchivo(tareaArchivoUi);
            }else {
                listener.oClickOpenLink(tareaArchivoUi, link);
            }

        }

        @Override
        public void onClicked(View v) {
            if(tareaArchivoUi.getTipo() != TareaArchivoUi.Tipo.LINK){
                listener.onClickOpenTareaArchivo(tareaArchivoUi);
            }else {
                List<String> urls = UtilsPortalAlumno.extractUrls(txtdescripcion.getText().toString());
                if(urls.size()> 0){
                    listener.oClickOpenLink(tareaArchivoUi, urls.get(0));
                }
            }
        }
    }


    public interface Listener{
        void onClickActionTareaArchivo(TareaArchivoUi tareaArchivoUi);
        void onClickRemoveTareaArchivo(TareaArchivoUi tareaArchivoUi);
        void onClickOpenTareaArchivo(TareaArchivoUi tareaArchivoUi);
        void oClickOpenLink(TareaArchivoUi tareaArchivoUi, String clickedLink);
    }
}
