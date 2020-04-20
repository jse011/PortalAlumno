package com.consultoraestrategia.ss_portalalumno.main.adapter.holders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.UsuarioAccesoUI;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irvinmarin on 09/10/2017.
 */

public class AccesosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.txtAccesoName)
    TextView txtAccesoName;
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    private MenuListener listener;
    private UsuarioAccesoUI usuarioAccesoUI;
    public AccesosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(final UsuarioAccesoUI usuarioAccesoUI, final MenuListener periodoListener) {
        this.listener = periodoListener;
        this.usuarioAccesoUI = usuarioAccesoUI;
        txtAccesoName.setText(usuarioAccesoUI.getNombreAcceso());
        itemView.setOnClickListener(this);
        Drawable drawable = ContextCompat.getDrawable(itemView.getContext(),R.drawable.ic_acceso);
        imgIcon.setImageDrawable(drawable);
        //itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_blue_50));
    }

    @Override
    public void onClick(View v) {
        listener.onMenuSelected(usuarioAccesoUI);

    }
}
