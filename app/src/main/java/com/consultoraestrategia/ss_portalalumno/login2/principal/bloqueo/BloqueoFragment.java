package com.consultoraestrategia.ss_portalalumno.login2.principal.bloqueo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class BloqueoFragment extends Fragment implements BloqueoView {
    @BindView(R.id.imageView12)
    CircleImageView imageView12;
    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_bloqueo, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.btn_volver_login, R.id.btn_pago_linea, R.id.btn_atras_bloqueo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_volver_login:
                if(presenter!=null)presenter.onClickVolverLogin();
                break;
            case R.id.btn_pago_linea:
                if(presenter!=null)presenter.onClickPagoLinea();
                break;
            case R.id.btn_atras_bloqueo:
                if(presenter!=null)presenter.onClickBloqueoAtras();
                break;
        }
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showFoto(String imagenUrl) {
        Glide.with(imageView12)
                .load(imagenUrl)
                .apply(UtilsGlide.getGlideRequestOptions())
                .into(imageView12);
    }
}
