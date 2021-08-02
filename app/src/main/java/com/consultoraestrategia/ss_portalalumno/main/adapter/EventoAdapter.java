package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi;
import com.consultoraestrategia.ss_portalalumno.util.AdjuntoEventoBuild;
import com.consultoraestrategia.ss_portalalumno.util.LinkUtils;
import com.consultoraestrategia.ss_portalalumno.util.PreviewMorePlaceholder;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventoAdapter  extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private List<EventoUi> eventoUiList = new ArrayList<>();
    private Listener listener;

    public EventoAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colegio_eventos_clean, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventoUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return eventoUiList.size();
    }

    public void setList(List<EventoUi> eventoUiList) {
        this.eventoUiList.clear();
        this.eventoUiList.addAll(eventoUiList);
        notifyDataSetChanged();
    }

    public void update(EventoUi eventoUi) {
        int psotion = eventoUiList.indexOf(eventoUi);
        if(psotion!=-1){
            eventoUiList.set(psotion, eventoUi);
            notifyItemChanged(psotion);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, LinkUtils.OnClickListener {
        private static final String TAG = ViewHolder.class.getSimpleName();
        @BindView(R.id.nombrePersonaEvento)
        TextView textNombre;
        @BindView(R.id.textView5)
        TextView textContenido;
        @BindView(R.id.nombre_directivo)
        TextView nombreDirectivo;
        @BindView(R.id.directivo)
        TextView directivo;

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
        @BindView(R.id.txt_megusta)
        TextView txtMegusta;
        @BindView(R.id.img_megusta)
        ImageView imgMegusta;
        @BindView(R.id.contentEvento)
        View contenImage;
        @BindView(R.id.preview_1)
        PreviewMorePlaceholder preview1;
        @BindView(R.id.preview_2)
        PreviewMorePlaceholder preview2;
        @BindView(R.id.preview_3)
        PreviewMorePlaceholder preview3;
        @BindView(R.id.preview_4)
        PreviewMorePlaceholder preview4;
        @BindView(R.id.separador_vertical_1)
        View separadorVertical1;
        @BindView(R.id.separador_vertical_2)
        View separadorVertical2;
        @BindView(R.id.separador_horizontal)
        View separadorHorizontal;
        @BindView(R.id.content_preview_1)
        LinearLayout contentPreview1;
        @BindView(R.id.content_preview_2)
        LinearLayout contentPreview2;
        @BindView(R.id.rc_recursos)
        RecyclerView rc_recursos;
        @BindView(R.id.line_recurso)
        View line_recurso;
        @BindView(R.id.content_recurso)
        View content_recurso;

        @BindView(R.id.cont_tipo)
        CardView contTipo;
        @BindView(R.id.img_tipo_ico)
        ImageView imgTipoIco;
        @BindView(R.id.txt_tipo_evento)
        TextView txtTipoEvento;

        @BindView(R.id.cont_fecha)
        CardView contFecha;
        @BindView(R.id.txt_fecha)
        TextView txtFecha;
        @BindView(R.id.rc_encuesta)
        RecyclerView rcEncuesta;

        public EventoUi getEventoUi() {
            return eventoUi;
        }

        private Listener listener;
        private EventoUi eventoUi;
        private EventoUi.AdjuntoUi adjuntoUiPreview1;
        private EventoUi.AdjuntoUi adjuntoUiPreview2;
        private EventoUi.AdjuntoUi adjuntoUiPreview3;
        private EventoUi.AdjuntoUi adjuntoUiPreview4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            preview1.setOnClickListener(this);
            preview2.setOnClickListener(this);
            preview3.setOnClickListener(this);
            preview4.setOnClickListener(this);
            btnMeGusta.setOnClickListener(this);
            btnMeCompartir.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }


        public void bind(final EventoUi eventoUi, Listener listener) {
            this.eventoUi = eventoUi;
            this.listener = listener;
            textContenido.setText(eventoUi.getDescripcion());
            txtTitulo.setText(eventoUi.getTitulo());

            switch (eventoUi.getTipoEventoUi().getTipo()){
                case EVENTO:
                    contFecha.setCardBackgroundColor(Color.parseColor("#bfca52"));
                    contTipo.setCardBackgroundColor(Color.parseColor("#bfca52"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_rocket));
                    break;
                case NOTICIA:
                    contTipo.setCardBackgroundColor(Color.parseColor("#ffc107"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#ffc107"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_newspaper_o));
                    break;
                case ACTIVIDAD:
                    contTipo.setCardBackgroundColor(Color.parseColor("#ff6b9d"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#ff6b9d"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_gif));
                    break;
                case TAREA:
                    contTipo.setCardBackgroundColor(Color.parseColor("#ff9800"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#ff9800"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_tasks));
                    break;
                case CITA:
                    contTipo.setCardBackgroundColor(Color.parseColor("#00bcd4"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#00bcd4"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_calendar));
                    break;
                case AGENDA:
                    contTipo.setCardBackgroundColor(Color.parseColor("#71bb74"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#71bb74"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_graduation_cap));
                    break;
                default:
                    contTipo.setCardBackgroundColor(Color.parseColor("#00BCD4"));
                    contFecha.setCardBackgroundColor(Color.parseColor("#00BCD4"));
                    imgTipoIco.setImageDrawable(ContextCompat.getDrawable(imgTipoIco.getContext(), R.drawable.ic_calendar));
                    break;
            }
            txtTipoEvento.setText(eventoUi.getTipoEventoUi().getNombre());
            String fecha = eventoUi.getTipoEventoUi().getNombre() + " " +eventoUi.getNombreFecha();
            txtFecha.setText(fecha);
            contFecha.setVisibility(TextUtils.isEmpty(eventoUi.getNombreFecha())?View.GONE:View.VISIBLE);
            List<EventoUi.AdjuntoUi> previewAdjuntoUiList = eventoUi.getAdjuntoUiPreviewList()!=null?eventoUi.getAdjuntoUiPreviewList():new ArrayList<>();
            listener.renderCountEvento(eventoUi, this);
/*
            if (eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.NOTICIA ||
                    eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.EVENTO||(eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA && !TextUtils.isEmpty(eventoUi.getFoto()))){
                if(!TextUtils.isEmpty(eventoUi.getFoto())){
                    contenImage.setVisibility(View.VISIBLE);
                    preview1.bindMore(eventoUi.getFoto(), 4);
                }else {
                    contenImage.setVisibility(View.GONE);
                    preview1.unbind();
                }

            }else{
                contenImage.setVisibility(View.GONE);
                preview1.unbind();
            }*/

            textNombre.setText(eventoUi.getNombreEntidad());
            //imagenEntidad.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                    .load(eventoUi.getFotoEntidad())
                    .apply(UtilsGlide.getGlideRequestOptionsSimple())
                    .into(imagenEntidad);

            if (eventoUi.getTipoEventoUi().getTipo()== TipoEventoUi.EventoIconoEnumUI.AGENDA){
                String fechaPublicacion = eventoUi.getNombreCalendario() + " " + eventoUi.getNombreFechaPublicion();
                directivo.setText(fechaPublicacion);
            }else {
                //" - Publicado jue 1 jun 2021"
                String fechaPublicacion = eventoUi.getRolEmisor() + " " + eventoUi.getNombreFechaPublicion();
                directivo.setText(fechaPublicacion );
            }

            nombreDirectivo.setText(eventoUi.getNombreEmisor());
            String megusta = "me gusta";
            if(eventoUi.getCantLike()!=0){
                megusta =  eventoUi.getCantLike() + " me gusta";
            }else if(eventoUi.getCantLike()>1000){
                megusta += "1k me gusta" ;
            }

            if(eventoUi.isLike()){
                imgMegusta.setImageDrawable(ContextCompat.getDrawable(imgMegusta.getContext(),R.drawable.ic_like ));
            }else {
                imgMegusta.setImageDrawable(ContextCompat.getDrawable(imgMegusta.getContext(),R.drawable.ic_like_disabled ));
            }
            txtMegusta.setText(megusta);

            String tipo = eventoUi.getTipoEventoUi().getNombre();
            LinkUtils.autoLink(textContenido, this);


            if (eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.NOTICIA ||
                    eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.EVENTO||(eventoUi.getTipoEventoUi().getTipo() == TipoEventoUi.EventoIconoEnumUI.AGENDA)){

                if(!previewAdjuntoUiList.isEmpty()){
                    contenImage.setVisibility(View.VISIBLE);
                    switch (previewAdjuntoUiList.size()){
                        case 1:
                            confPreviewOne();
                            preview1.bindPreview(previewAdjuntoUiList.get(0).getImagePreview(), previewAdjuntoUiList.get(0).isVideo());
                            adjuntoUiPreview1 = previewAdjuntoUiList.get(0);

                            break;
                        case 2:
                            confPreviewTwo();
                            preview1.bindPreview(previewAdjuntoUiList.get(0).getImagePreview(), previewAdjuntoUiList.get(0).isVideo());
                            preview2.bindPreview(previewAdjuntoUiList.get(1).getImagePreview(), previewAdjuntoUiList.get(1).isVideo());
                            adjuntoUiPreview1 = previewAdjuntoUiList.get(0);
                            adjuntoUiPreview2 = previewAdjuntoUiList.get(1);

                            break;
                        case 3:
                            confPreviewThree();
                            preview1.bindPreview(previewAdjuntoUiList.get(0).getImagePreview(), previewAdjuntoUiList.get(0).isVideo());
                            preview3.bindPreview(previewAdjuntoUiList.get(1).getImagePreview(), previewAdjuntoUiList.get(1).isVideo());
                            preview4.bindPreview(previewAdjuntoUiList.get(2).getImagePreview(), previewAdjuntoUiList.get(2).isVideo());
                            adjuntoUiPreview1 = previewAdjuntoUiList.get(0);
                            adjuntoUiPreview3 = previewAdjuntoUiList.get(1);
                            adjuntoUiPreview4 = previewAdjuntoUiList.get(2);

                            break;
                        case 4:
                            confPreviewFour();
                            preview1.bindPreview(previewAdjuntoUiList.get(0).getImagePreview(), previewAdjuntoUiList.get(0).isVideo());
                            preview2.bindPreview(previewAdjuntoUiList.get(1).getImagePreview(), previewAdjuntoUiList.get(1).isVideo());
                            preview3.bindPreview(previewAdjuntoUiList.get(2).getImagePreview(), previewAdjuntoUiList.get(2).isVideo());
                            preview4.bindPreview(previewAdjuntoUiList.get(3).getImagePreview(), previewAdjuntoUiList.get(3).isVideo());
                            adjuntoUiPreview1 = previewAdjuntoUiList.get(0);
                            adjuntoUiPreview2 = previewAdjuntoUiList.get(1);
                            adjuntoUiPreview3 = previewAdjuntoUiList.get(2);
                            adjuntoUiPreview4 = previewAdjuntoUiList.get(3);

                            break;
                        default:
                            confPreviewFour();
                            preview1.bindPreview(previewAdjuntoUiList.get(0).getImagePreview(), previewAdjuntoUiList.get(0).isVideo());
                            preview2.bindPreview(previewAdjuntoUiList.get(1).getImagePreview(), previewAdjuntoUiList.get(1).isVideo());
                            preview3.bindPreview(previewAdjuntoUiList.get(2).getImagePreview(), previewAdjuntoUiList.get(2).isVideo());
                            preview4.bindMore(previewAdjuntoUiList.get(3).getImagePreview(),previewAdjuntoUiList.get(3).isVideo() ,previewAdjuntoUiList.size() - 4);
                            adjuntoUiPreview1 = previewAdjuntoUiList.get(0);
                            adjuntoUiPreview2 = previewAdjuntoUiList.get(1);
                            adjuntoUiPreview3 = previewAdjuntoUiList.get(2);
                            adjuntoUiPreview4 = previewAdjuntoUiList.get(3);

                            break;
                    }

                }else {
                    contenImage.setVisibility(View.GONE);
                    previewUnBindAll();
                }

            }else{
                contenImage.setVisibility(View.GONE);
                previewUnBindAll();
            }
            List<EventoUi.AdjuntoUi> adjuntoUiList = eventoUi.getAdjuntoUiList()!=null?eventoUi.getAdjuntoUiList():new ArrayList<>();
            int count;
            switch (adjuntoUiList.size()){
                case 1:
                    count = 2;
                    break;
                case 2:
                    count = 2;
                    break;
                case 3:
                    count = 3;
                    break;
                default:
                    count = 4;
                    break;
            }
            AdjuntoEventoBuild.AdjuntoEventoAdapterMore adjuntoEventoAdapter = new AdjuntoEventoBuild.AdjuntoEventoAdapterMore(adjuntoUiList, eventoUi,listener);
            rc_recursos.setAdapter(adjuntoEventoAdapter);
            rc_recursos.setLayoutManager(new GridLayoutManager(rc_recursos.getContext(), count));

            if(adjuntoUiList.isEmpty()){
                line_recurso.setVisibility(View.GONE);
                content_recurso.setVisibility(View.GONE);
            }else {
                line_recurso.setVisibility(View.VISIBLE);
                content_recurso.setVisibility(View.VISIBLE);
            }

            rcEncuesta.setLayoutManager(new LinearLayoutManager(rcEncuesta.getContext()));
            rcEncuesta.setAdapter(new AdjuntoEventoEncuesta(eventoUi.getAdjuntoUiEncuestaList(), new AdjuntoEventoEncuesta.Listener() {
                @Override
                public void onLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi) {
                    listener.onLinkEncuesta(adjuntoUi);
                }
            }));
            rcEncuesta.setVisibility(eventoUi.getAdjuntoUiEncuestaList().isEmpty()?View.GONE:View.VISIBLE);

        }

        void previewUnBindAll(){
            preview1.unbind();
            preview2.unbind();
            preview3.unbind();
            preview4.unbind();
        }

        void confPreviewOne(){
            LinearLayout.LayoutParams contentPreview1Params = ( LinearLayout.LayoutParams)contentPreview1.getLayoutParams();
            contentPreview1Params.weight = 1f;
            LinearLayout.LayoutParams preview1Params = ( LinearLayout.LayoutParams)preview1.getLayoutParams();
            preview1Params.weight = 1f;
            contentPreview1.setVisibility(View.VISIBLE);
            preview1.setVisibility(View.VISIBLE);
            preview2.setVisibility(View.GONE);
            contentPreview2.setVisibility(View.GONE);

            separadorVertical1.setVisibility(View.GONE);
            separadorVertical2.setVisibility(View.GONE);
            separadorHorizontal.setVisibility(View.GONE);

            preview2.unbind();
            preview3.unbind();
            preview4.unbind();
        }

        void confPreviewTwo(){
            LinearLayout.LayoutParams contentPreview1Params = ( LinearLayout.LayoutParams)contentPreview1.getLayoutParams();
            contentPreview1Params.weight = 1f;
            LinearLayout.LayoutParams preview1Params = ( LinearLayout.LayoutParams)preview1.getLayoutParams();
            preview1Params.weight = 0.5f;
            LinearLayout.LayoutParams preview2Params = ( LinearLayout.LayoutParams)preview2.getLayoutParams();
            preview2Params.weight = 0.5f;

            contentPreview1.setVisibility(View.VISIBLE);
            preview1.setVisibility(View.VISIBLE);
            preview2.setVisibility(View.VISIBLE);
            contentPreview2.setVisibility(View.GONE);


            separadorVertical1.setVisibility(View.VISIBLE);
            separadorVertical2.setVisibility(View.GONE);
            separadorHorizontal.setVisibility(View.GONE);
            preview3.unbind();
            preview4.unbind();
        }

        void confPreviewThree(){
            LinearLayout.LayoutParams contentPreview1Params = ( LinearLayout.LayoutParams)contentPreview1.getLayoutParams();
            contentPreview1Params.weight = 0.5f;
            LinearLayout.LayoutParams preview1Params = ( LinearLayout.LayoutParams)preview1.getLayoutParams();
            preview1Params.weight = 1f;
            LinearLayout.LayoutParams contentPreview2Params = ( LinearLayout.LayoutParams)contentPreview2.getLayoutParams();
            contentPreview2Params.weight = 0.5f;
            LinearLayout.LayoutParams preview3Params = ( LinearLayout.LayoutParams)preview3.getLayoutParams();
            preview3Params.weight = 0.5f;
            LinearLayout.LayoutParams preview4Params = ( LinearLayout.LayoutParams)preview4.getLayoutParams();
            preview4Params.weight = 0.5f;

            contentPreview1.setVisibility(View.VISIBLE);
            preview1.setVisibility(View.VISIBLE);
            preview2.setVisibility(View.GONE);
            contentPreview2.setVisibility(View.VISIBLE);
            preview3.setVisibility(View.VISIBLE);
            preview4.setVisibility(View.VISIBLE);

            separadorVertical1.setVisibility(View.GONE);
            separadorVertical2.setVisibility(View.VISIBLE);
            separadorHorizontal.setVisibility(View.VISIBLE);
            preview2.unbind();
        }

        void confPreviewFour(){
            LinearLayout.LayoutParams contentPreview1Params = ( LinearLayout.LayoutParams)contentPreview1.getLayoutParams();
            contentPreview1Params.weight = 0.5f;
            LinearLayout.LayoutParams preview1Params = ( LinearLayout.LayoutParams)preview1.getLayoutParams();
            preview1Params.weight = 0.5f;
            LinearLayout.LayoutParams preview2Params = ( LinearLayout.LayoutParams)preview2.getLayoutParams();
            preview2Params.weight = 0.5f;
            LinearLayout.LayoutParams contentPreview2Params = ( LinearLayout.LayoutParams)contentPreview2.getLayoutParams();
            contentPreview2Params.weight = 0.5f;
            LinearLayout.LayoutParams preview3Params = ( LinearLayout.LayoutParams)preview3.getLayoutParams();
            preview3Params.weight = 0.5f;
            LinearLayout.LayoutParams preview4Params = ( LinearLayout.LayoutParams)preview4.getLayoutParams();
            preview4Params.weight = 0.5f;

            contentPreview1.setVisibility(View.VISIBLE);
            preview1.setVisibility(View.VISIBLE);
            preview2.setVisibility(View.VISIBLE);
            contentPreview2.setVisibility(View.VISIBLE);
            preview3.setVisibility(View.VISIBLE);
            preview4.setVisibility(View.VISIBLE);

            separadorVertical1.setVisibility(View.VISIBLE);
            separadorVertical2.setVisibility(View.VISIBLE);
            separadorHorizontal.setVisibility(View.VISIBLE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_me_gusta){
                Log.d(TAG, "btn_me_gusta");
                btnMeGusta.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onClickLike(eventoUi);
                    }
                }, 400);
            }else if(v.getId() == R.id.btn_me_compartir){
                listener.onclikCompartir(eventoUi);
            }else if(v.getId() == R.id.preview_1){
                listener.onClickAdjuntoPreview(eventoUi, adjuntoUiPreview1);
            }else if(v.getId() == R.id.preview_2){
                listener.onClickAdjuntoPreview(eventoUi, adjuntoUiPreview2);
            }else if(v.getId() == R.id.preview_3){
                listener.onClickAdjuntoPreview(eventoUi, adjuntoUiPreview3);
            }else if(v.getId() == R.id.preview_4){
                listener.onClickAdjuntoPreview(eventoUi, adjuntoUiPreview4);
            }

        }

        @Override
        public void onLinkClicked(View v, String link) {

        }

        @Override
        public void onClicked(View v) {

        }

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

    }

    public interface Listener {
        void onClickLike(EventoUi eventosUi);
        void renderCountEvento(EventoUi eventosUi, RecyclerView.ViewHolder viewHolder);
        void onclikCompartir(EventoUi eventoUi);
        void onClickAdjuntoPreview(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi);
        void onClickAdjunto(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi, boolean more);

        void onLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi);
    }
}
