package com.consultoraestrategia.ss_portalalumno.login2.principal.correo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CorreoFragment extends Fragment implements CorreoView {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.edittext_correo)
    TextInputEditText edittextCorreo;
    @BindView(R.id.btn_siguiente_correo)
    Button btnSiguienteCorreo;
    @BindView(R.id.imageView17)
    ImageView imageView17;
    @BindView(R.id.btn_atras_correo)
    ImageView btnAtrasCorreo;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.textView109)
    TextView textView109;

    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_correo, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(getResources().getString(R.string.app_name).equals("Educar Student")){

            Glide.with(imageView17)
                    .load(R.drawable.educar_d_login)
                    .into(imageView17);
            Glide.with(this)
                    .load(R.drawable.docente_mentor)
                    .into(imageView9);
            textView109.setText("Centro de Aprendizaje Virtual");
            textView109.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEducarStudent));

        }else{
            Glide.with(imageView17)
                    .load(R.drawable.icrm_d_login)
                    .into(imageView17);
            textView109.setText("Social iCRM Educativo Móvil");
            textView109.setTextColor(ContextCompat.getColor(getContext(), R.color.colorEvaStudent));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   initAdapter();
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onInvalitedCorreo(String error) {
        edittextCorreo.requestFocus();
        edittextCorreo.setSelected(true);
        edittextCorreo.setError(error);
    }

    @Override
    public void setCorreo(String correo) {
        edittextCorreo.setText(correo);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_siguiente_correo, R.id.btn_atras_correo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_siguiente_correo:
                String correo = edittextCorreo.getText()==null?"":edittextCorreo.getText().toString();
                presenter.onClickCorreoSiguiente(correo);
                break;
            case R.id.btn_atras_correo:
                presenter.onClickCorreoAtras();
                break;
        }
    }
}
