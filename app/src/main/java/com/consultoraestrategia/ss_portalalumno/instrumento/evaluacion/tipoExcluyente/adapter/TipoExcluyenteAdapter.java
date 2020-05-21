package com.consultoraestrategia.ss_portalalumno.instrumento.evaluacion.tipoExcluyente.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class TipoExcluyenteAdapter extends RecyclerView.Adapter<TipoExcluyenteAdapter.ViewHolder> {
    private ViewHolder viewHolder;
    private List<ValorUi> valorUiList = new ArrayList<>();
    private Listener listener;

    public TipoExcluyenteAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tipo_inst_excluyente, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ValorUi valorUi = valorUiList.get(position);
        if(valorUi.isSelected())viewHolder = holder;
        holder.bind(valorUi, new Listener() {
            @Override
            public void onClickValorExcluyente(ValorUi valorUi) {
                if(viewHolder!=null)viewHolder.radioButton.setChecked(false);
                viewHolder = holder;
                for (ValorUi item : valorUiList)item.setSelected(false);
                valorUi.setSelected(true);
                holder.radioButton.setChecked(true);
                listener.onClickValorExcluyente(valorUi);
            }
        });
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
        @BindView(R.id.radioButton)
        public RadioButton radioButton;
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
                        .load("http://icrmedu.consultoraestrategia.com/prueba/Images/Img_preguntasInstrumento/"+valorUi.getPath())
                        .apply(new RequestOptions()
                                .centerInside()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.ic_error_outline_black))
                        .into(imgValor);
                imgValor.setVisibility(View.VISIBLE);
            }else {
                imgValor.setVisibility(View.GONE);
            }

            radioButton.setChecked(valorUi.isSelected());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickValorExcluyente(valorUi);
        }
    }

    public interface Listener {
        void onClickValorExcluyente(ValorUi valorUi);
    }
}
