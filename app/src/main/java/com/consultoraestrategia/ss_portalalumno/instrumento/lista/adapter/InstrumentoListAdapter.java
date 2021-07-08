package com.consultoraestrategia.ss_portalalumno.instrumento.lista.adapter;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.gadgets.staticProgressBar.CustomProgress;
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
        @BindView(R.id.customProgress)
        CustomProgress progressBar5;
        @BindView(R.id.anim_alert)
        LottieAnimationView animAlert;
        @BindView(R.id.img_pregunta)
        ImageView imgPregunta;
        @BindView(R.id.img_descripcion)
        TextView imgDescripcion;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.icono)
        ImageView icono;
        @BindView(R.id.content)
        CardView content;
        private float porcentajeSelected = 0;

        private Callback callback;
        private InstrumentoUi instrumentoUi;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            content.setOnClickListener(this);
        }

        public void bind(InstrumentoUi instrumentoUi, Callback callback) {
            this.callback = callback;
            this.instrumentoUi = instrumentoUi;
            txtNombre.setText(instrumentoUi.getNombre());
            String cantidad = instrumentoUi.getCantidadPregunta() == 1 ? "1 pregunta" : instrumentoUi.getCantidadPregunta() + " preguntas";
            txtPreguntas.setText(cantidad);
            float porcentaje =  0;
            if(instrumentoUi.getCantidadPregunta()>0){
                porcentaje = (float)instrumentoUi.getCantidadPreguntaResueltas()/(float)instrumentoUi.getCantidadPregunta();
            }
            progressBar5.setMaximumPercentage(porcentaje);
            progressBar5.setDisabledMovementProgress(true);
            if(porcentajeSelected!=porcentaje){
                porcentajeSelected = porcentaje;
                progressBar5.updateView();
            }
            progressBar5.setProgressBackgroundColor(Color.WHITE);
            Log.d("InstrumentoList","Progress: " + porcentaje);
            Drawable school = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_school);

            try {
                progressBar5.setProgressColor(Color.parseColor(instrumentoUi.getColor()));
                txtNombre.setTextColor(Color.parseColor(instrumentoUi.getColor2()));
                school.mutate().setColorFilter(Color.parseColor(instrumentoUi.getColor()), PorterDuff.Mode.SRC_ATOP);
            }catch (Exception e){
                e.printStackTrace();
            }

            icono.setImageDrawable(school);

            animAlert.setAnimation("error-icon.json");
            animAlert.setRepeatCount(ValueAnimator.INFINITE);
            animAlert.playAnimation();
            animAlert.setVisibility(instrumentoUi.getCatidadPreguntasSinEnviar()>0?View.VISIBLE:View.GONE);
            if(instrumentoUi.getPorcentaje()==-1){
                imgPregunta.setVisibility(View.GONE);
                imgDescripcion.setVisibility(View.GONE);
            }else {

                if (instrumentoUi.getTipoIdTipoNota() == 412) {
                    String vstr_Titulo = instrumentoUi.getTituloValorTipoNota();
                    imgPregunta.setVisibility(View.GONE);
                    imgDescripcion.setVisibility(View.VISIBLE);
                    imgDescripcion.setText(vstr_Titulo);
                }else {
                    String vstr_PathIcon = instrumentoUi.getIconoValorTipoNota();
                    Glide.with(imgPregunta)
                            .load(vstr_PathIcon)
                            .apply(UtilsGlide.getGlideRequestOptions())
                            .into(imgPregunta);
                    imgPregunta.setVisibility(View.VISIBLE);
                    imgDescripcion.setVisibility(View.GONE);
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
