package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoTexto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.InstrumentoEvaluacionPresenter;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.component.PEditText;
import com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipo.TipoEvaluacionView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TipoTextoFragment extends Fragment implements TipoEvaluacionView {
    @BindView(R.id.txt_titulo)
    TextView txtTitulo;
    @BindView(R.id.img_varaible)
    ImageView imgVaraible;
    @BindView(R.id.editText)
    PEditText editText;
    @BindView(R.id.txt_progress)
    TextView txtProgress;
    @BindView(R.id.button)
    Button button;
    private Unbinder unbinder;
    private InstrumentoEvaluacionPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.instrumento_tipo_text_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
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
        txtTitulo.setText(nombre);
    }

    @Override
    public void showImage(String path) {
        Glide.with(imgVaraible)
                .load(path)
                .apply(new RequestOptions()
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_error_outline_black))
                .into(imgVaraible);
        imgVaraible.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        imgVaraible.setVisibility(View.GONE);
    }

    @Override
    public void setProgress(String progress) {
        txtProgress.setText(progress);
    }

    @Override
    public void setInputEntero() {
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    @Override
    public void setInputDecimal() {
        editText.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    public void setInputString() {
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
    }

    @Override
    public void setListValores(List<ValorUi> valores) {

    }

    @Override
    public void changeButtonFinsh(String texto) {
        button.setText(texto);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        presenter.siguientePregunta(this, editText.getText()!=null?editText.getText().toString():"");
    }
}
