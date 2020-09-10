package com.consultoraestrategia.ss_portalalumno.pregunta.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.PreguntaUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreguntasAdapter extends RecyclerView.Adapter<PreguntasAdapter.ViewHolder> {

    private List<PreguntaUi> preguntaUiList = new ArrayList<>();
    private Listener listener;

    public PreguntasAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(preguntaUiList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return preguntaUiList.size();
    }

    public void updateOrSave(PreguntaUi preguntaUi) {
        int position = preguntaUiList.indexOf(preguntaUi);
        if (position != -1) {
            preguntaUiList.set(position, preguntaUi);
            notifyItemChanged(position);
        } else {
            preguntaUiList.add(preguntaUi);
            notifyItemChanged(preguntaUiList.size() - 1);
        }
    }

    public void remover(PreguntaUi preguntaUi) {
        int position = preguntaUiList.indexOf(preguntaUi);
        if (position != -1) {
            preguntaUiList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        preguntaUiList.clear();
        notifyDataSetChanged();
    }

    public void setList(List<PreguntaUi> preguntaUiList) {
        this.preguntaUiList.clear();
        this.preguntaUiList.addAll(preguntaUiList);
        notifyDataSetChanged();
       //preguntaUiList.
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_help)
        ImageView imgHelp;
        @BindView(R.id.txt_titulo)
        TextView txtTitulo;
        @BindView(R.id.btn_pregunta)
        FrameLayout btnPregunta;
        @BindView(R.id.img_nota)
        ImageView imgNota;
        @BindView(R.id.img_tipo)
        ImageView imgTipo;
        @BindView(R.id.img_descripcion)
        TextView imgDescripcion;
        @BindView(R.id.btn_img_pregunta)
        FrameLayout btnImgPregunta;
        @BindView(R.id.card_view)
        CardView cardView;

        private Listener listener;
        private PreguntaUi preguntaUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(PreguntaUi preguntaUi, Listener listener) {
            this.listener = listener;
            this.preguntaUi = preguntaUi;
            Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_help_pregunta);
            Drawable tipo;
            String tipoId = !TextUtils.isEmpty(preguntaUi.getTipoId())?preguntaUi.getTipoId():"";
            if(tipoId.equals("2")){
                tipo = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_people);
            }else {
                tipo = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_person);
            }
            try {
                circle.mutate().setColorFilter(Color.parseColor(preguntaUi.getColor()), PorterDuff.Mode.SRC_ATOP);
                tipo.mutate().setColorFilter(Color.parseColor(preguntaUi.getColor()), PorterDuff.Mode.SRC_ATOP);
            }catch (Exception e){
                e.printStackTrace();
            }
            imgTipo.setImageDrawable(tipo);
            imgHelp.setImageDrawable(circle);
            txtTitulo.setText(preguntaUi.getTitulo());
            if(TextUtils.isEmpty(preguntaUi.getNotaIcono())){
                imgNota.setVisibility(View.GONE);
                imgDescripcion.setVisibility(View.VISIBLE);
                imgDescripcion.setText(preguntaUi.getNotaTitulo());
            }else {
                imgNota.setVisibility(View.VISIBLE);
                imgDescripcion.setVisibility(View.GONE);
                Glide.with(imgNota)
                        .load(preguntaUi.getNotaIcono())
                        .apply(UtilsGlide.getGlideRequestOptionsSimple())
                        .into(imgNota);
            }
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClickPregunta(preguntaUi);
        }
    }

    public  interface Listener{
        void onClickPregunta(PreguntaUi preguntaUi);
    }
}
