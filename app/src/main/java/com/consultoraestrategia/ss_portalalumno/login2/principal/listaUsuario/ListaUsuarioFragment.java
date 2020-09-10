package com.consultoraestrategia.ss_portalalumno.login2.principal.listaUsuario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.adapter.PersonaAdapter;
import com.consultoraestrategia.ss_portalalumno.login2.entities.PersonaUi;
import com.consultoraestrategia.ss_portalalumno.login2.listener.PersonaListener;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListaUsuarioFragment extends Fragment implements ListaUsuarioView, PersonaListener {

    @BindView(R.id.rc_contactos)
    RecyclerView rcContactos;
    @BindView(R.id.btn_quitar_usuario)
    TextView btnQuitarUsuario;
    private Unbinder unbinder;
    private Login2Presenter presenter;
    private PersonaAdapter usuarioAdapter;
    @BindView(R.id.imageView13)
    ImageView imageView13;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_lista_usuario, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   initAdapter();
        rcContactos.setLayoutManager(new LinearLayoutManager(getContext()));
        this.usuarioAdapter = new PersonaAdapter(new ArrayList<PersonaUi>(), this);
        rcContactos.setAdapter(this.usuarioAdapter);
        if(getResources().getString(R.string.app_name).equals("Educar Student")){

            Glide.with(imageView13)
                    .load(R.drawable.logo_educar)
                    .into(imageView13);

        }else{
            Glide.with(imageView13)
                    .load(R.drawable.docente_mentor)
                    .into(imageView13);
        }
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void listaUsuarioView(List<PersonaUi> usuarioUiList, boolean quitarUsuario) {
        usuarioAdapter.setUsuarios(usuarioUiList, quitarUsuario);
    }

    @Override
    public void setTextoBtnQuitarUsuario(String texto) {
        btnQuitarUsuario.setText(texto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_quitar_usuario)
    public void onViewClicked() {
        presenter.onClickQuitarUsuario();
    }

    @Override
    public void onClickPersona(PersonaUi personaUi) {
        presenter.onClickPersona(personaUi);
    }
}
