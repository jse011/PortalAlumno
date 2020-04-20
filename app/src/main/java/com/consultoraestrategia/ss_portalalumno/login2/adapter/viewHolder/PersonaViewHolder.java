package com.consultoraestrategia.ss_portalalumno.login2.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.listener.PersonaListener;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jse on 25/09/2018.
 */

public class PersonaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView imgUsuario;
    TextView txtNombres;
    TextView txtUsuario;
    TextView textView82;
    ImageView btnUsuarioClose;
    private PersonaUi personaUi;
    private PersonaListener usuarioListener;

    public PersonaViewHolder(View view) {
        super(view);
        imgUsuario = view.findViewById(R.id.img_usuario);
        txtNombres = view.findViewById(R.id.txt_nombres);
        txtUsuario = view.findViewById(R.id.txt_usuario);
        textView82 = view.findViewById(R.id.textView82);
        btnUsuarioClose = view.findViewById(R.id.btn_usuario_close);
        ButterKnife.bind(this, view);
    }

    public void bind(PersonaUi personaUi, boolean elimiarUsuario, PersonaListener usuarioListener) {
        this.personaUi = personaUi;
        this.usuarioListener = usuarioListener;
        setUrlImgenPassword(personaUi.getImagenUrl());
        String nombre = personaUi.getNombres() + " " + personaUi.getApellidos();
        txtNombres.setText(nombre);
        txtUsuario.setText(personaUi.getUsuario());
        itemView.setOnClickListener(this);

        if (personaUi.isUsarOtraCuenta()) {
            txtUsuario.setVisibility(View.GONE);
            textView82.setVisibility(View.GONE);
        } else {
            txtUsuario.setVisibility(View.VISIBLE);
            textView82.setVisibility(View.VISIBLE);
        }

        if (elimiarUsuario && !personaUi.isUsarOtraCuenta()) {
            btnUsuarioClose.setVisibility(View.VISIBLE);
        } else {
            btnUsuarioClose.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                usuarioListener.onClickPersona(personaUi);
                break;
        }
    }

    private void setUrlImgenPassword(String urlImgenPassword) {
                Glide.with(imgUsuario.getContext())
                        .load(urlImgenPassword)
                        .apply(UtilsGlide.getGlideRequestOptions(R.drawable.ic_account_circle))
                        .into(imgUsuario);

    }

}
