package com.consultoraestrategia.ss_portalalumno.evidencia.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.evidencia.enities.ArchivoSesEvidenciaUi;
import com.consultoraestrategia.ss_portalalumno.gadgets.staticProgressBar.CustomProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvidenciaSesAdapter extends RecyclerView.Adapter<EvidenciaSesAdapter.ViewHolder> {
    private List<ArchivoSesEvidenciaUi> archivoSesEvidenciaUiList = new ArrayList<>();
    private Listener listener;

    public EvidenciaSesAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EvidenciaSesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ses_evi_archivo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EvidenciaSesAdapter.ViewHolder holder, int position) {
        holder.bind(archivoSesEvidenciaUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return archivoSesEvidenciaUiList.size();
    }

    public void setList(List<ArchivoSesEvidenciaUi> list) {
        this.archivoSesEvidenciaUiList.clear();
        this.archivoSesEvidenciaUiList.addAll(list);
        notifyDataSetChanged();
    }

    public List<ArchivoSesEvidenciaUi> getList() {
        return archivoSesEvidenciaUiList;
    }

    public void add(ArchivoSesEvidenciaUi tareaArchivoUi) {
        this.archivoSesEvidenciaUiList.add(tareaArchivoUi);
        notifyItemInserted(archivoSesEvidenciaUiList.size()-1);
    }

    public void update(ArchivoSesEvidenciaUi tareaArchivoUi) {
        int position = archivoSesEvidenciaUiList.indexOf(tareaArchivoUi);
        if(position!=-1){
            archivoSesEvidenciaUiList.set(position, tareaArchivoUi);
            notifyItemChanged(position);
        }
    }

    public void remove(ArchivoSesEvidenciaUi tareaArchivoUi) {
        int position = archivoSesEvidenciaUiList.indexOf(tareaArchivoUi);
        if(position!=-1){
            archivoSesEvidenciaUiList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        private Listener listener;
        private ArchivoSesEvidenciaUi archivoSesEvidenciaUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ArchivoSesEvidenciaUi archivoSesEvidenciaUi, Listener listener) {
            this.listener = listener;
            this.archivoSesEvidenciaUi = archivoSesEvidenciaUi;
            cardView.setOnClickListener(this);
            btnAction.setOnClickListener(this);
            btnRecurso.setOnClickListener(this);
            txtNombreRecurso.setText(archivoSesEvidenciaUi.getNombre());
            txtdescripcion.setText(archivoSesEvidenciaUi.getDescripcion());
            float progress = (float) archivoSesEvidenciaUi.getProgress()/(float) 100;
            customProgress.setMaximumPercentage(progress);
            customProgress.setDisabledMovementProgress(true);
            customProgress.setProgressBackgroundColor(Color.TRANSPARENT);
            try {
                customProgress.setProgressColor(Color.parseColor(archivoSesEvidenciaUi.getColor()));
            }catch (Exception e){
                e.printStackTrace();
            }

            Log.d("AdjuntoAdapter", "progress: "+progress);

            if(archivoSesEvidenciaUi.isDisabled()){
                this.progress.setVisibility(View.VISIBLE);
                btnRecurso.setVisibility(View.GONE);
                btnAction.setVisibility(View.GONE);
                customProgress.setVisibility(View.GONE);
            }else {
                this.progress.setVisibility(View.GONE);
                if(archivoSesEvidenciaUi.getFile()!=null){
                    if(archivoSesEvidenciaUi.getState()==1){
                        btnAction.setImageResource(R.drawable.ic_pause_youtube);
                    }else {
                        btnAction.setImageResource(R.drawable.ic_play_recurso);
                    }
                    btnRecurso.setVisibility(View.VISIBLE);
                    btnAction.setVisibility(View.VISIBLE);
                    customProgress.setVisibility(View.VISIBLE);
                    customProgress.updateView();
                }else {
                    btnRecurso.setVisibility(View.VISIBLE);
                    btnAction.setVisibility(View.GONE);
                    customProgress.setVisibility(View.GONE);
                }
            }

            btnRecurso.setVisibility(archivoSesEvidenciaUi.getEntregado()?View.GONE:View.VISIBLE);

            switch (archivoSesEvidenciaUi.getTipo()) {
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
                    listener.onClickActionArchivo(archivoSesEvidenciaUi);
                    break;
                case R.id.btn_recurso:
                    listener.onClickRemoverchivo(archivoSesEvidenciaUi);
                    break;
                case R.id.card_view:
                    listener.onClickOpenArchivo(archivoSesEvidenciaUi);
                    break;
            }
        }
    }


    public interface Listener{
        void onClickActionArchivo(ArchivoSesEvidenciaUi evidenciaUi);
        void onClickRemoverchivo(ArchivoSesEvidenciaUi evidenciaUi);
        void onClickOpenArchivo(ArchivoSesEvidenciaUi evidenciaUi);
    }
}
