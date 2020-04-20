package com.consultoraestrategia.ss_portalalumno.main.adapter.holders;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.ProgramaEduactivoUI;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irvinmarin on 09/10/2017.
 */

public class ProgramaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.txtAccesoName)
    TextView txtAccesoName;
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    private MenuListener listener;
    private ProgramaEduactivoUI programaEduactivosUI;

    public ProgramaViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(final ProgramaEduactivoUI programaEduactivosUI, final MenuListener periodoListener) {
        this.listener = periodoListener;
        this.programaEduactivosUI = programaEduactivosUI;
        txtAccesoName.setText(programaEduactivosUI.getNombrePrograma());
        itemView.setOnClickListener(this);
        if(programaEduactivosUI.isSeleccionado()){
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_blue_50));
        }else {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View view) {
        listener.onMenuSelected(programaEduactivosUI);
    }

}
