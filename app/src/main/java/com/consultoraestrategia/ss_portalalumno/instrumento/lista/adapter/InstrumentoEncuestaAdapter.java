package com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.gadgets.staticProgressBar.CustomProgress;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.EncuestaUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.InstrumentoUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstrumentoEncuestaAdapter extends RecyclerView.Adapter<InstrumentoEncuestaAdapter.ViewHolder> {

    private List<EncuestaUi> encuestaUiList = new ArrayList<>();
    private Callback callback;

    public InstrumentoEncuestaAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encuesta_sesion, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(encuestaUiList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return encuestaUiList.size();
    }

    public void setList(List<EncuestaUi> instrumentoUiList) {
        this.encuestaUiList.clear();
        this.encuestaUiList.addAll(instrumentoUiList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_nombre)
        TextView txtNombre;
        @BindView(R.id.tipo)
        TextView tipo;
        @BindView(R.id.txt_preguntas)
        TextView txtPreguntas;
        @BindView(R.id.txt_cant_puntos)
        TextView txtCantPuntos;
        @BindView(R.id.btn_encuesta_tipo_2)
        View btnEncuestaTipo2;
        @BindView(R.id.btn_encuesta_tipo_1)
        View btnEncuestaTipo1;


        private Callback callback;
        private EncuestaUi encuestaUi;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            btnEncuestaTipo2.setOnClickListener(this);
        }

        public void bind(EncuestaUi encuestaUi, Callback callback) {
            this.callback = callback;
            this.encuestaUi = encuestaUi;
            txtNombre.setText(encuestaUi.getNombre());
            String cantidad = encuestaUi.getCantidadPregunta() == 1 ? "1 pregunta" : encuestaUi.getCantidadPregunta() + " preguntas";
            txtPreguntas.setText(cantidad);
            txtCantPuntos.setText(String.valueOf(encuestaUi.getPuntos()));
            if(encuestaUi.isVerResultados()){
                btnEncuestaTipo2.setVisibility(View.VISIBLE);
                btnEncuestaTipo1.setVisibility(View.GONE);
            }else {
                btnEncuestaTipo2.setVisibility(View.GONE);
                btnEncuestaTipo1.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_encuesta_tipo_2){
                callback.onClickEncuesta(encuestaUi);
            }else {
                callback.onClickEncuestaVerResultados(encuestaUi);
            }
        }
    }

    public interface Callback {
        void onClickEncuesta(EncuestaUi instrumentoUi);
        void onClickEncuestaVerResultados(EncuestaUi instrumentoUi);
    }
}
