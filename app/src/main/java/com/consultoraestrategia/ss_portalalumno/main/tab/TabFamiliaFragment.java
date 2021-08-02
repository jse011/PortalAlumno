package com.consultoraestrategia.ss_portalalumno.main.tab;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.adapter.FamiliaAdapter;
import com.consultoraestrategia.ss_portalalumno.main.entities.AlumnoUi;
import com.consultoraestrategia.ss_portalalumno.util.AppBarStateChangeListener;
import com.consultoraestrategia.ss_portalalumno.util.ColorTransparentUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TabFamiliaFragment extends Fragment implements MainView.TabFamilia, FamiliaAdapter.Callback {
    private Unbinder unbinder;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.expanded)
    RoundRectView expanded;
    @BindView(R.id.content_expanded)
    ConstraintLayout contentExpanded;
    @BindView(R.id.texto_expanded)
    TextView textoExpanded;
    @BindView(R.id.nombre_padre)
    TextView nombrePadre;
    @BindView(R.id.img_padre)
    ImageView imgPadre;
    @BindView(R.id.edad_padre)
    TextView edadPadre;
    @BindView(R.id.telefono_padre)
    TextView telefonoPadre;
    @BindView(R.id.correo_padre)
    TextView correoPadre;
    @BindView(R.id.rc_familia)
    RecyclerView rcFamilia;

    private MainPresenter presenter;
    private FamiliaAdapter familia2Adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_familia, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupToolbar();
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        rcFamilia.setLayoutManager(new LinearLayoutManager(getContext()));
        familia2Adapter = new FamiliaAdapter(this);
        rcFamilia.setAdapter(familia2Adapter);
        rcFamilia.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) rcFamilia.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void setupToolbar() {
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int offset) {
                if(getContext()!=null)
                    switch (state){
                        case IDLE:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                expanded.setElevation(UtilsPortalAlumno.convertDpToPixel(0, getContext()));
                            }

                            if(offset<-60){
                                offset = 0;
                            }
                            int tansparencia = 100 + offset;

                            contentExpanded.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(ContextCompat.getColor(getContext(), R.color.md_white_1000), tansparencia)));
                            break;
                        case EXPANDED:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                expanded.setElevation(UtilsPortalAlumno.convertDpToPixel(0, getContext()));
                            }
                            textoExpanded.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);;
                            contentExpanded.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(ContextCompat.getColor(getContext(), R.color.md_white_1000), 0)));
                            break;
                        case COLLAPSED:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                expanded.setElevation(UtilsPortalAlumno.convertDpToPixel(8, getContext()));
                            }
                            textoExpanded.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                            contentExpanded.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(ContextCompat.getColor(getContext(), R.color.md_white_1000), 100)));
                            break;
                    }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter=null;
    }

    @Override
    public void showFamilia(AlumnoUi usuario, List<Object> familia) {
        nombrePadre.setText(usuario.getNombre());
        Glide.with(imgPadre.getContext())
                .load(usuario.getFoto())
                .apply(UtilsGlide.getGlideRequestOptionsSimple()
                        .centerCrop())
                .into(imgPadre);
        edadPadre.setText(usuario.getFechaNacimiento());
        telefonoPadre.setText(TextUtils.isEmpty(usuario.getCelular())?"Sin teléfono":usuario.getCelular());
        correoPadre.setText(TextUtils.isEmpty(usuario.getCorreo())?"Sin correo":usuario.getCorreo());
        familia2Adapter.setList(familia);


    }

    @Override
    public void updateFamiliar(Object personUi) {
        familia2Adapter.update(personUi);
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickTooglePersona(Object personUi) {
        presenter.onClickTooglePersona(personUi);
    }

}

