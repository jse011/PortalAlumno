package com.consultoraestrategia.ss_portalalumno.login2.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ProgramaEducativoUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFastData extends RecyclerView.Adapter<AdapterFastData.ViewHolder> {

    private List<ProgramaEducativoUi> programaEducativoUiList = new ArrayList<>();
    private RecyclerView recyclerView;
    private int textColor = Color.WHITE;

    public AdapterFastData(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_login_fast_data, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ProgramaEducativoUi programaEducativoUi = programaEducativoUiList.get(i);
        viewHolder.bind(programaEducativoUi, textColor);
    }

    @Override
    public int getItemCount() {
        return programaEducativoUiList.size();
    }

    public void setList(List<ProgramaEducativoUi> programaEducativoUiList) {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
        this.programaEducativoUiList.clear();
        this.programaEducativoUiList.addAll(programaEducativoUiList);
        notifyDataSetChanged();
    }

    public void updateItem(ProgramaEducativoUi programaEducativoUi) {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        int position = programaEducativoUiList.indexOf(programaEducativoUi);
        if(position!=-1){
            programaEducativoUiList.set(position, programaEducativoUi);
            notifyItemChanged(position);
        }
    }

    public void updateOrAddItem(ProgramaEducativoUi programaEducativoUi) {
        int position = programaEducativoUiList.indexOf(programaEducativoUi);
        if(position!=-1){
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            programaEducativoUiList.set(position, programaEducativoUi);
            notifyItemChanged(position);
            recyclerView.scrollToPosition(position);

        }else {
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
            programaEducativoUiList.add(programaEducativoUi);
            notifyDataSetChanged();
            recyclerView.scrollToPosition(position+1);
        }
    }

    public void clear() {
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
        programaEducativoUiList.clear();
        notifyDataSetChanged();
    }

    public void setTexColor(int texColor) {
        this.textColor = texColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_contenido)
        TextView txtContenido;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(ProgramaEducativoUi programaEducativoUi, int textColor) {
            String contenido = "";
            if (programaEducativoUi.getSuccess() == -1) {
                contenido = "" + programaEducativoUi.getNombre() + " error";
            } else {
                contenido = "" + programaEducativoUi.getNombre() + " " + (programaEducativoUi.getUploadProgress() > 0 || programaEducativoUi.getDowloadProgress() > 0 ? programaEducativoUi.getDowloadProgress() + "%" : "");
            }

            txtContenido.setText(contenido);
            txtContenido.setTextColor(textColor);
        }
    }
}
