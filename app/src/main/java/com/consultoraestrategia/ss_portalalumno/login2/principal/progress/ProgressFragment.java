package com.consultoraestrategia.ss_portalalumno.login2.principal.progress;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.principal.Login2Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProgressFragment extends Fragment implements ProgressView {
    @BindView(R.id.animation_view)
    LottieAnimationView animationView;
    @BindView(R.id.txt_app)
    TextView txtApp;

    private Unbinder unbinder;
    private Login2Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_progress, container, false);
        unbinder = ButterKnife.bind(this, view);
        txtApp.setText(getResources().getString(R.string.app_name));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //   initAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        animationView.setAnimation("blobbyloader.json");
        animationView.setRepeatCount(ValueAnimator.INFINITE);
        animationView.playAnimation();
    }

    @Override
    public void onAttach(Login2Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animationView.cancelAnimation();
        animationView.setImageDrawable(null);
        unbinder.unbind();
    }

}
