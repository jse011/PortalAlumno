package com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter;

import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstrumentoListAdapter extends RecyclerView.Adapter<InstrumentoListAdapter.ViewHolder> {

    private List<InstrumentoUi> instrumentoUiList = new ArrayList<>();
    private Callback callback;

    public InstrumentoListAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_instrumento_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(instrumentoUiList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return instrumentoUiList.size();
    }

    public void setList(List<InstrumentoUi> instrumentoUiList) {
        this.instrumentoUiList.clear();
        this.instrumentoUiList.addAll(instrumentoUiList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_nombre)
        TextView txtNombre;
        @BindView(R.id.txt_preguntas)
        TextView txtPreguntas;
        @BindView(R.id.progressBar5)
        ProgressBar progressBar5;
        @BindView(R.id.anim_alert)
        LottieAnimationView animAlert;
        @BindView(R.id.img_porcentaje)
        ImageView imgPorcentaje;

        private Callback callback;
        private InstrumentoUi instrumentoUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(InstrumentoUi instrumentoUi, Callback callback) {
            this.callback = callback;
            this.instrumentoUi = instrumentoUi;
            txtNombre.setText(instrumentoUi.getNombre());
            String cantidad = instrumentoUi.getCantidadPregunta() == 1 ? "1 pregunta" : instrumentoUi.getCantidadPregunta() + " preguntas";
            txtPreguntas.setText(cantidad);

            Drawable progressDrawable = progressBar5.getProgressDrawable().mutate();
            progressDrawable.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.color_sesion), PorterDuff.Mode.SRC_IN);
            progressBar5.setProgressDrawable(progressDrawable);

            progressBar5.setMax(instrumentoUi.getCantidadPregunta());
            progressBar5.setProgress(instrumentoUi.getCantidadPreguntaResueltas());
            animAlert.setAnimation("error-icon.json");
            animAlert.setRepeatCount(ValueAnimator.INFINITE);
            animAlert.playAnimation();
            animAlert.setVisibility(instrumentoUi.getCatidadPreguntasSinEnviar()>0?View.VISIBLE:View.GONE);
            if(instrumentoUi.getPorcentaje()==-1){
                imgPorcentaje.setVisibility(View.GONE);
            }else {
                imgPorcentaje.setVisibility(View.VISIBLE);
                if(instrumentoUi.getPorcentaje()<25){
                    Glide.with(imgPorcentaje)
                            .load(R.drawable.ic_triste)
                            .apply(UtilsGlide.getGlideRequestOptionsSimple())
                            .into(imgPorcentaje);
                }else if(instrumentoUi.getPorcentaje()<50){
                    Glide.with(imgPorcentaje)
                            .load(R.drawable.ic_neutral)
                            .apply(UtilsGlide.getGlideRequestOptionsSimple())
                            .into(imgPorcentaje);
                }else if(instrumentoUi.getPorcentaje()<75){
                    Glide.with(imgPorcentaje)
                            .load(R.drawable.ic_contento)
                            .apply(UtilsGlide.getGlideRequestOptionsSimple())
                            .into(imgPorcentaje);
                }else{
                    Glide.with(imgPorcentaje)
                            .load(R.drawable.ic_muy_contento)
                            .apply(UtilsGlide.getGlideRequestOptionsSimple())
                            .into(imgPorcentaje);
                }
            }
        }

        @Override
        public void onClick(View v) {
            callback.onClick(instrumentoUi);
        }
    }

    public interface Callback {
        void onClick(InstrumentoUi instrumentoUi);
    }
}
