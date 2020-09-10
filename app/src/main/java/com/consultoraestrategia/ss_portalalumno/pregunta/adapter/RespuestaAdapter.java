package com.consultoraestrategia.ss_portalalumno.pregunta.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.RespuestaUi;
import com.consultoraestrategia.ss_portalalumno.pregunta.entities.SubRespuestaUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RespuestaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int RESPUESTA = 1, SUBRESPUESTA = 2;
    private List<Object> objectList = new ArrayList<>();
    private Listener listener;

    public RespuestaAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RESPUESTA) {
            return new RespuestaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_respuesta, parent, false));
        }
        return new SubRespuestaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subrespuesta, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object o = objectList.get(position);
        switch (holder.getItemViewType()) {
            case RESPUESTA:
                ((RespuestaViewHolder) holder).bind((RespuestaUi) o, listener);
                break;
            case SUBRESPUESTA:
                ((SubRespuestaViewHolder) holder).bind((SubRespuestaUi) o, listener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof RespuestaUi) {
            return RESPUESTA;
        } else if (objectList.get(position) instanceof SubRespuestaUi) {
            return SUBRESPUESTA;
        } else {
            return 0;
        }

    }

    public void updateSave(RespuestaUi respuestaUi) {
        int position = objectList.indexOf(respuestaUi);
        if (position != -1) {
            objectList.set(position, respuestaUi);
            notifyItemChanged(position);
        } else {
            objectList.add(respuestaUi);
            notifyItemInserted(objectList.size() - 1);
        }

    }

    public void setList(List<SubRespuestaUi> list) {
        this.objectList.clear();
        this.objectList.addAll(list);
        notifyDataSetChanged();
    }

    public void removePregunta(String preguntaRespuestaId) {
        RespuestaUi respuestaUi = new RespuestaUi();
        respuestaUi.setId(preguntaRespuestaId);
        int position = objectList.indexOf(respuestaUi);
        if (position != -1) {
            objectList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public int addRespuesta(RespuestaUi respuestaUi) {

        this.objectList.add(respuestaUi);
        int position = objectList.size() - 1;
        notifyItemInserted(position);
        return position;
    }

    public void addSubRespuesta(SubRespuestaUi subRespuestaUi) {
        RespuestaUi respuestaUi = new RespuestaUi();
        respuestaUi.setId(subRespuestaUi.getParentId());
        int position = objectList.indexOf(respuestaUi);
        if (position != -1) {
            respuestaUi = (RespuestaUi) objectList.get(position);
            respuestaUi.getSubrecursoList().add(subRespuestaUi);
            notifyItemChanged(position);
        }
    }

    public void setListRespuesta(List<RespuestaUi> listRespuesta) {
        this.objectList.clear();
        this.objectList.addAll(listRespuesta);
        notifyDataSetChanged();
    }

    public void clear() {
        this.objectList.clear();
        notifyDataSetChanged();
    }

    public static class RespuestaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_profile)
        CircleImageView imgProfile;
        @BindView(R.id.txt_persona)
        TextView txtPersona;
        @BindView(R.id.txt_fecha)
        TextView txtFecha;
        @BindView(R.id.txt_contenido)
        TextView txtContenido;
        @BindView(R.id.rc_subrespuesta)
        RecyclerView rcSubrespuesta;
        @BindView(R.id.img_profile2)
        CircleImageView imgProfile2;
        @BindView(R.id.img_send)
        ImageView imgSend;
        @BindView(R.id.cont_send)
        CardView contSend;
        @BindView(R.id.cont_subrecurso)
        ConstraintLayout contSubrecurso;
        @BindView(R.id.btn_responde)
        Button btnResponde;
        @BindView(R.id.btn_opciones)
        ImageView btnOpciones;
        private RespuestaUi respuestaUi;
        private Listener listener;


        public RespuestaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(RespuestaUi respuestaUi, Listener listener) {
            this.respuestaUi = respuestaUi;
            this.listener = listener;
            Glide.with(imgProfile)
                    .load(respuestaUi.getFoto())
                    .apply(UtilsGlide.getGlideRequestOptions(R.drawable.ic_usuario))
                    .into(imgProfile);

            Glide.with(imgProfile2)
                    .load(respuestaUi.getFoto2())
                    .apply(UtilsGlide.getGlideRequestOptions(R.drawable.ic_usuario))
                    .into(imgProfile2);

            txtContenido.setText(respuestaUi.getContenido());
            txtFecha.setText(respuestaUi.getFecha());
            txtPersona.setText(respuestaUi.getNombre());

            btnResponde.setOnClickListener(this);
            imgSend.setOnClickListener(this);
            contSend.setOnClickListener(this);
            RespuestaAdapter respuestaAdapter = new RespuestaAdapter(listener);
            respuestaAdapter.setList(respuestaUi.getSubrecursoList());
            rcSubrespuesta.setAdapter(respuestaAdapter);
            Drawable send = ContextCompat.getDrawable(imgSend.getContext(), R.drawable.ic_send);
            try {
                btnResponde.setTextColor(Color.parseColor(respuestaUi.getColor1()));
                send.mutate().setColorFilter(Color.parseColor(respuestaUi.getColor2()), PorterDuff.Mode.SRC_ATOP);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imgSend.setImageDrawable(send);
            rcSubrespuesta.setLayoutManager(new LinearLayoutManager(rcSubrespuesta.getContext()));
            ((SimpleItemAnimator) rcSubrespuesta.getItemAnimator()).setSupportsChangeAnimations(false);
            rcSubrespuesta.setHasFixedSize(false);
            rcSubrespuesta.setNestedScrollingEnabled(false);
            btnOpciones.setVisibility(respuestaUi.getEditar()?View.VISIBLE:View.GONE);
            btnOpciones.setOnClickListener(this);
            if(respuestaUi.getOffline()){
                contSubrecurso.setVisibility(View.GONE);
                btnResponde.setVisibility(View.INVISIBLE);
            }else {
                contSubrecurso.setVisibility(respuestaUi.isResponder() ? View.VISIBLE : View.GONE);
                btnResponde.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_responde:
                    listener.onClickResponder(respuestaUi);
                    break;
                case R.id.img_send:
                    listener.onClickRespuesta(respuestaUi);
                    break;
                case R.id.cont_send:
                    listener.onClickRespuesta(respuestaUi);
                    break;
                case R.id.btn_opciones:
                    PopupMenu popup = new PopupMenu(itemView.getContext(), btnOpciones);
                    // Inflate the menu from xml
                    popup.getMenu().add(Menu.NONE, 1, 1, "Editar");
                    popup.getMenu().add(Menu.NONE, 2, 2, "Eliminar");
                    // Setup menu item selection
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case 1:
                                    listener.onClickEditarRespuesta(respuestaUi);
                                    return true;
                                case 2:
                                    listener.onClickEliminarRespuesta(respuestaUi);
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
        }
    }

    public static class SubRespuestaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_profile2)
        CircleImageView imgProfile2;
        @BindView(R.id.txt_persona)
        TextView txtPersona;
        @BindView(R.id.txt_fecha)
        TextView txtFecha;
        @BindView(R.id.txt_contenido)
        TextView txtContenido;
        @BindView(R.id.btn_opciones)
        ImageView btnOpciones;
        private Listener listener;
        private SubRespuestaUi subRespuestaUi;

        public SubRespuestaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(SubRespuestaUi subRespuestaUi, Listener listener) {
            this.subRespuestaUi = subRespuestaUi;
            this.listener = listener;
            Glide.with(imgProfile2)
                    .load(subRespuestaUi.getFoto())
                    .apply(UtilsGlide.getGlideRequestOptions(R.drawable.ic_usuario))
                    .into(imgProfile2);
            txtPersona.setText(subRespuestaUi.getNombre());
            txtFecha.setText(subRespuestaUi.getFecha());
            txtContenido.setText(subRespuestaUi.getContenido());
            btnOpciones.setVisibility(subRespuestaUi.getEditar()?View.VISIBLE:View.GONE);
            btnOpciones.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btn_opciones){
                PopupMenu popup = new PopupMenu(itemView.getContext(), btnOpciones);
                // Inflate the menu from xml
                popup.getMenu().add(Menu.NONE, 1, 1, "Editar");
                popup.getMenu().add(Menu.NONE, 2, 2, "Eliminar");
                // Setup menu item selection
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case 1:
                                listener.onClickEditarSubRespuesta(subRespuestaUi);
                                return true;
                            case 2:
                                listener.onClickEliminarSubRespuesta(subRespuestaUi);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                // Show the menu
                popup.show();
            }

        }
    }

    public interface Listener {
        void onClickResponder(RespuestaUi respuestaUi);

        void onClickRespuesta(RespuestaUi respuestaUi);

        void onClickEditarSubRespuesta(SubRespuestaUi subRespuestaUi);

        void onClickEliminarSubRespuesta(SubRespuestaUi subRespuestaUi);

        void onClickEditarRespuesta(RespuestaUi respuestaUi);

        void onClickEliminarRespuesta(RespuestaUi respuestaUi);
    }
}
