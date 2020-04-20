package com.consultoraestrategia.ss_portalalumno.base.filterChooser;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;

import java.util.List;

public class FilterChoserAdapter extends RecyclerView.Adapter<FilterChoserAdapter.FilterChoserHolder> {

    private List<String> stringList;
    private int positionSelected;
    private OnItemClickListener listener;

    public FilterChoserAdapter(List<String> stringList, int positionSelected, OnItemClickListener listener) {
        this.stringList = stringList;
        this.positionSelected = positionSelected;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilterChoserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialogfilter_item, parent, false);
        return new FilterChoserHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FilterChoserHolder holder, int position) {
        String titulo = stringList.get(position);
        holder.bind(titulo,position,positionSelected);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class FilterChoserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView textfiltro;
        private RadioButton checkBox;
        private int posicion;

        public FilterChoserHolder(View itemView) {
            super(itemView);
            checkBox = (RadioButton) itemView.findViewById(R.id.checkTipos);
            textfiltro = (TextView)itemView.findViewById(R.id.textfiltrotipo);
            itemView.setOnClickListener(this);

        }

        public void bind(String titulo, int position, int positionSelected) {
            this.posicion = position;
            textfiltro.setText(titulo);
            if(position == positionSelected){
                checkBox.setChecked(true);
                listener.onItemSelect(itemView,posicion);
            }else {
                checkBox.setChecked(false);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                default:
                    listener.onItemClick(itemView,posicion);
                    break;
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int itemIndex);
        void onItemSelect(View view, int itemIndex);
    }
}
