package com.consultoraestrategia.ss_portalalumno.login2.principal.dni;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DniFragment extends Fragment implements DniView, TextView.OnEditorActionListener {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.edittext_dni)
    TextInputEditText edittextDni;
    @BindView(R.id.btn_siguiente_dni)
    Button btnSiguienteDni;
    @BindView(R.id.imageView17)
    ImageView imageView17;
    @BindView(R.id.btn_atras_dni)
    ImageView btnAtrasDni;
    @BindView(R.id.imageView8)
    ImageView imageView8;

    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_dni, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   initAdapter();
        edittextDni.setOnEditorActionListener(this);

        if(getResources().getString(R.string.app_name).equals("Educar Student")){

            Glide.with(imageView17)
                    .load(R.drawable.logo_educar)
                    .into(imageView17);
            imageView8.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.docente_mentor)
                    .into(imageView8);


        }else{
            Glide.with(imageView17)
                    .load(R.drawable.docente_mentor)
                    .into(imageView17);
            imageView8.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onInvalitedDni(String error) {
        edittextDni.requestFocus();
        edittextDni.setSelected(true);
        edittextDni.setError(error);
    }

    @Override
    public void setDni(String dni) {
        edittextDni.setText(dni);
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

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            int i = textView.getId();
            if (i == R.id.edittext_dni) {
                String dni = edittextDni.getText()==null?"":edittextDni.getText().toString();
                presenter.onClickDniSiguiente(dni);
            }
            handled = true;
        }
        return handled;
    }

    @OnClick({R.id.btn_siguiente_dni, R.id.btn_atras_dni})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_siguiente_dni:
                String dni = edittextDni.getText()==null?"":edittextDni.getText().toString();
                presenter.onClickDniSiguiente(dni);
                break;
            case R.id.btn_atras_dni:
                presenter.onClickDniAtras();
                break;
        }
    }

}
