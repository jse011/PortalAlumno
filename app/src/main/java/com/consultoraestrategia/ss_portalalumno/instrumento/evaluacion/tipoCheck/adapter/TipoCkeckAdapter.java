package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoCheck.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.instrumento.entities.ValorUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoCkeckAdapter extends RecyclerView.Adapter<TipoCkeckAdapter.ViewHolder> {
    private List<ValorUi> valorUiList = new ArrayList<>();
    private Listener listener;

    public TipoCkeckAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_inst_check, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ValorUi valorUi = valorUiList.get(position);
        holder.bind(valorUi, listener);
    }

    @Override
    public int getItemCount() {
        return valorUiList.size();
    }

    public void setList(List<ValorUi> valores) {
        this.valorUiList.clear();
        this.valorUiList.addAll(valores);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_respuesta)
        TextView txtRespuesta;
        @BindView(R.id.img_valor)
        ImageView imgValor;
        @BindView(R.id.check)
        CheckBox check;
        private ValorUi valorUi;
        private Listener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ValorUi valorUi, Listener listener) {
            this.valorUi = valorUi;
            this.listener = listener;
            txtRespuesta.setText(valorUi.getDescripcion());
            if(!TextUtils.isEmpty(valorUi.getPath())){
                Glide.with(imgValor)
                        .load(valorUi.getPath())
                        .apply(new RequestOptions()
                                .centerInside()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.ic_error_outline_black))
                        .into(imgValor);
                imgValor.setVisibility(View.VISIBLE);
            }else {
                imgValor.setVisibility(View.GONE);
            }

            check.setChecked(valorUi.isSelected());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            valorUi.setSelected(!valorUi.isSelected());
            check.setChecked(valorUi.isSelected());
            listener.onClickValorCheck(valorUi);
        }
    }

    public interface Listener {
        void onClickValorCheck(ValorUi valorUi);
    }
}
