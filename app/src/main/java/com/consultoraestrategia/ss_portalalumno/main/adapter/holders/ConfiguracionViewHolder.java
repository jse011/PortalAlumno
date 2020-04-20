package com.consultoraestrategia.ss_portalalumno.main.adapter.holders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.ConfiguracionUi;
import com.consultoraestrategia.ss_portalalumno.main.listeners.MenuListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irvinmarin on 09/10/2017.
 */

public class ConfiguracionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.txtAccesoName)
    TextView txtAccesoName;
    @BindView(R.id.imgIcon)
    ImageView imgIcon;
    private MenuListener listener;
    private ConfiguracionUi configuracionUi;

    public ConfiguracionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


    public void bind(final ConfiguracionUi configuracionUi, final MenuListener periodoListener) {
        this.listener = periodoListener;
        this.configuracionUi = configuracionUi;
        txtAccesoName.setText(configuracionUi.getName());
        itemView.setOnClickListener(this);
        Drawable drawable = ContextCompat.getDrawable(itemView.getContext(),R.drawable.ic_configuracion);
        imgIcon.setImageDrawable(drawable);
        //itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_blue_50));

    }

    @Override
    public void onClick(View view) {
        listener.onMenuSelected(configuracionUi);
    }

}
