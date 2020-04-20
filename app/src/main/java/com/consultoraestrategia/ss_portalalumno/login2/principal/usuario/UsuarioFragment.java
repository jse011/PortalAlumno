package com.consultoraestrategia.ss_portalalumno.login2.principal.usuario;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UsuarioFragment extends Fragment implements UsuarioView, TextView.OnEditorActionListener {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.edittext_username)
    TextInputEditText edittextUsername;
    @BindView(R.id.edittext_password_user)
    TextInputEditText edittextPasswordUser;
    @BindView(R.id.btn_siguiente_user)
    Button btnSiguienteUser;
    @BindView(R.id.imageView17)
    ImageView imageView17;
    @BindView(R.id.btn_atras_lst_usu)
    ImageView btnAtrasLstUsu;
    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_usuario, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(imageView17)
                .load(R.drawable.docente_mentor)
                .into(imageView17);

        edittextPasswordUser.setOnEditorActionListener(this);
        //   initAdapter();
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onErrorPassword(String error) {
        edittextPasswordUser.requestFocus();
        edittextPasswordUser.setSelected(true);
        edittextPasswordUser.setError(error);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void hideAtras() {
        btnAtrasLstUsu.setVisibility(View.GONE);
    }

    @Override
    public void showAtras() {
        btnAtrasLstUsu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCredencialesIncorrectos(String error) {
        edittextUsername.requestFocus();
        edittextUsername.setSelected(true);
        edittextUsername.setError(error);
    }

    @Override
    public void setUsuario(String usuario) {
        edittextUsername.setText(usuario);
    }

    @Override
    public void setPassword(String password) {
        edittextPasswordUser.setText(password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            int i = textView.getId();
            if (i == R.id.edittext_password_user) {
                String usuario = edittextUsername.getText()==null?"":edittextUsername.getText().toString();
                String password = edittextPasswordUser.getText()==null?"":edittextPasswordUser.getText().toString();
                presenter.onClickUsuarioSiguiente(usuario,password);
            }
            handled = true;
        }
        return handled;

    }

    @OnClick({R.id.btn_siguiente_user, R.id.btn_atras_lst_usu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_siguiente_user:
                String usuario = edittextUsername.getText()==null?"":edittextUsername.getText().toString();
                String password = edittextPasswordUser.getText()==null?"":edittextPasswordUser.getText().toString();
                presenter.onClickUsuarioSiguiente(usuario,password);
                break;
            case R.id.btn_atras_lst_usu:
                presenter.onClickUsuarioAtras();
                break;
        }
    }
}
