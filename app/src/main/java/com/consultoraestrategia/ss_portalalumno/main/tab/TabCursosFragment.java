package com.consultoraestrategia.ss_portalalumno.main.tab;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.MainPresenter;
import com.consultoraestrategia.ss_portalalumno.main.MainView;
import com.consultoraestrategia.ss_portalalumno.main.adapter.CursosAdapter;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.util.AppBarStateChangeListener;
import com.consultoraestrategia.ss_portalalumno.util.ColorTransparentUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TabCursosFragment extends Fragment implements MainView.TabCursos, CursosAdapter.Listener {
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.expanded)
    RoundRectView expanded;
    @BindView(R.id.content_expanded)
    ConstraintLayout contentExpanded;
    @BindView(R.id.texto_expanded)
    TextView textoExpanded;
    @BindView(R.id.rvListaClases)
    RecyclerView rvListaClases;
    @BindView(R.id.progressBar18)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private MainPresenter presenter;
    private CursosAdapter adapter;

    @Override
    public void showListCurso(List<CursosUi> cursosUiList) {
        adapter.setList(cursosUiList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_cursos, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupAdapter();
        setupToolbar();
        return view;
    }

    private void setupAdapter() {
        adapter = new CursosAdapter(this);
        rvListaClases.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListaClases.setAdapter(adapter);

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
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(CursosUi cursosUi) {
        presenter.onClickCurso(cursosUi);
    }
}
