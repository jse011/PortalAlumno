package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjuntoEventoEncuesta extends RecyclerView.Adapter<AdjuntoEventoEncuesta.ViewHolder> {

    List<EventoUi.AdjuntoUi> eventoadjuntoUiList;
    Listener listener;

    public AdjuntoEventoEncuesta(List<EventoUi.AdjuntoUi> eventoadjuntoUiList, Listener listener) {
        this.eventoadjuntoUiList = eventoadjuntoUiList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento_encuesta_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventoadjuntoUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return eventoadjuntoUiList.size();
    }

    public void setList(List<EventoUi.AdjuntoUi> adjuntoUiList) {
        this.eventoadjuntoUiList.clear();
        eventoadjuntoUiList.addAll(adjuntoUiList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txt_link)
        TextView txtLink;
        private Listener listener;
        private EventoUi.AdjuntoUi adjuntoUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(EventoUi.AdjuntoUi adjuntoUi, Listener listener) {
            this.adjuntoUi = adjuntoUi;
            this.listener = listener;
            txtLink.setText(adjuntoUi.getTitulo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLinkEncuesta(adjuntoUi);
                }
            });
        }
    }

    public interface Listener{
        void onLinkEncuesta(EventoUi.AdjuntoUi adjuntoUi);
    }
}
