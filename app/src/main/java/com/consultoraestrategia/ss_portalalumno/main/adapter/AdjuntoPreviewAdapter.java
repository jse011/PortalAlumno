package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;
import com.consultoraestrategia.ss_portalalumno.util.PreviewMorePlaceholder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjuntoPreviewAdapter extends RecyclerView.Adapter<AdjuntoPreviewAdapter.ViewHolder> {
    private final List<EventoUi.AdjuntoUi> adjuntoUiList = new ArrayList<>();
    private EventoUi eventosUi;
    Listener listener;
    RecyclerView recyclerView;

    public AdjuntoPreviewAdapter(Listener listener, RecyclerView recyclerView) {
        this.listener = listener;
        this.recyclerView =  recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colegio_adjunto_preview_clean, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventosUi, adjuntoUiList.get(position), listener);
    }

    public void setList(EventoUi eventosUi, List<EventoUi.AdjuntoUi> adjuntoUiList){
        this.eventosUi = eventosUi;
        this.adjuntoUiList.clear();
        this.adjuntoUiList.addAll(adjuntoUiList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adjuntoUiList.size();
    }

    public void scrollToPosition(EventoUi.AdjuntoUi adjuntoUi) {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int postion = adjuntoUiList.indexOf(adjuntoUi);
                recyclerView.scrollToPosition(postion);
            }
        }, 200);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.preview_1)
        PreviewMorePlaceholder preview1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(EventoUi eventosUi, EventoUi.AdjuntoUi adjuntoUi, Listener listener) {
            preview1.bindPreview(adjuntoUi.getImagePreview(),adjuntoUi.isVideo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickDialogAdjuntoEvento(eventosUi, adjuntoUi);
                }
            });
        }

    }

    public interface Listener {
        void onClickDialogAdjuntoEvento(EventoUi eventoUi, EventoUi.AdjuntoUi adjuntoUi);
    }
}
