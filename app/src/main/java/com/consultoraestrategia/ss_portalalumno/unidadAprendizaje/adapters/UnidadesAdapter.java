package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder.UnidadViewHolder;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UnidadesAdapter extends RecyclerView.Adapter<UnidadViewHolder>  {

    private List<UnidadAprendizajeUi> unidadAprendizajes = new ArrayList<>();
    private String color;
    private UnidadListener unidadListener;

    public UnidadesAdapter(UnidadListener unidadListener) {
        this.unidadListener = unidadListener;
    }

    @NonNull
    @Override
    public UnidadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UnidadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_unidades, parent, false),unidadListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UnidadViewHolder holder, int position) {
        holder.bind(unidadAprendizajes.get(position),color);
    }

    @Override
    public int getItemCount() {
        return unidadAprendizajes.size();
    }

    public void setList(List<UnidadAprendizajeUi> unidadAprendizajeUiList, String color) {
        this.color = color;
        unidadAprendizajes.clear();
        unidadAprendizajes.addAll(unidadAprendizajeUiList);
        notifyDataSetChanged();
    }

    public void updateItem(UnidadAprendizajeUi unidadAprendizajeUi) {
        int position = unidadAprendizajes.indexOf(unidadAprendizajeUi);
        if(position!=-1){
            unidadAprendizajes.set(position, unidadAprendizajeUi);
            notifyItemChanged(position);
        }
    }

    public void remover(UnidadAprendizajeUi unidadAprendizajeUi) {
        int position = unidadAprendizajes.indexOf(unidadAprendizajeUi);
        if(position!=-1){
            unidadAprendizajes.remove(position);
            notifyItemRemoved(position);
        }
    }


    public interface UnidadListener {
        void onClickUnidadAprendizaje(UnidadAprendizajeUi unidadAprendizajeUi);
        void onClickSesionAprendizaje(SesionAprendizajeUi sesionAprendizajeUi);
    }
}