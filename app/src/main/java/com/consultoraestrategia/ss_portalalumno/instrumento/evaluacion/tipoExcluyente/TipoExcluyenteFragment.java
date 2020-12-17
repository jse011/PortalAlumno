package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoExcluyente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.InstrumentoEvaluacionPresenter;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo.TipoEvaluacionView;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoExcluyente.adapter.TipoExcluyenteAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TipoExcluyenteFragment extends Fragment implements TipoEvaluacionView, TipoExcluyenteAdapter.Listener {
    @BindView(R.id.txt_progreso)
    TextView txtProgreso;
    @BindView(R.id.txt_pregunta)
    TextView txtPregunta;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.rc_valores)
    RecyclerView rcValores;
    @BindView(R.id.img_titulo)
    ImageView imgTitulo;

    private Unbinder unbinder;
    private InstrumentoEvaluacionPresenter presenter;
    private TipoExcluyenteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instrumento_tipo_check_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupAdater();
        return view;
    }

    private void setupAdater() {
        rcValores.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TipoExcluyenteAdapter(this);
        rcValores.setAdapter(adapter);
        rcValores.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(InstrumentoEvaluacionPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setTitulo(String nombre) {
        txtPregunta.setText(nombre);
    }

    @Override
    public void showImage(String path) {
        Glide.with(imgTitulo)
                .load(path)
                .apply(new RequestOptions()
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_error_outline_black))
                .into(imgTitulo);
        imgTitulo.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        imgTitulo.setVisibility(View.GONE);
    }

    @Override
    public void setProgress(String progress) {
        txtProgreso.setText(progress);
    }

    @Override
    public void setInputEntero() {

    }

    @Override
    public void setInputDecimal() {

    }

    @Override
    public void setInputString() {

    }

    @Override
    public void setListValores(List<ValorUi> valores) {
        adapter.setList(valores);
    }

    @Override
    public void changeButtonFinsh(String texto) {
        button.setText(texto);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        presenter.siguientePregunta(this, null);
    }

    @Override
    public void onClickValorExcluyente(ValorUi valorUi) {

    }
}
