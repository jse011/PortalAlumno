package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder.SesionHolder;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;

import java.util.List;


public class AdapterSesiones extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final UnidadesAdapter.UnidadListener listener;
    String TAG= AdapterSesiones.class.getSimpleName();
    private List<SesionAprendizajeUi> sesionesArrayList;
    private int itemCount = 0;
    // Constructor
    public AdapterSesiones(List<SesionAprendizajeUi> sesionesArrayList, boolean landscape, UnidadesAdapter.UnidadListener unidadListener) {
        this.sesionesArrayList = sesionesArrayList;
        this.listener = unidadListener;
    }

    // Create new views (invoked by the layout managxer)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SesionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sesiones_land_scape, parent, false));
    }

    // Reemplaza en contenido de la vista
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((SesionHolder)holder).bind(sesionesArrayList.get(position), listener);
    }

    // Retorna el tamano de nuestra data
    @Override
    public int getItemCount() {
        return itemCount;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<SesionAprendizajeUi> listSesiones, int size) {
        sesionesArrayList.clear();
        sesionesArrayList.addAll(listSesiones);
        itemCount = size;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<SesionAprendizajeUi> listSesiones) {
        sesionesArrayList.clear();
        sesionesArrayList.addAll(listSesiones);
        itemCount = sesionesArrayList.size();
        notifyDataSetChanged();
    }


    public interface showMessageCollback{
        void onClickAceptar();
        void onClickCancelar();
    }



}