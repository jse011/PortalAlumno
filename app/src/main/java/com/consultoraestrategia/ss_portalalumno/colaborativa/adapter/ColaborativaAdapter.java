package com.consultoraestrategia.ss_portalalumno.colaborativa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.colaborativa.entities.ColaborativaUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColaborativaAdapter extends RecyclerView.Adapter<ColaborativaAdapter.ViewHolder> {
    private List<ColaborativaUi> colaborativaUiList = new ArrayList<>();
    private Listener listener;

    public ColaborativaAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colaborativa_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(colaborativaUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return colaborativaUiList.size();
    }

    public void setListColaborativa(List<ColaborativaUi> colaborativaUiList) {
        this.colaborativaUiList.clear();
        this.colaborativaUiList.addAll(colaborativaUiList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_tipo)
        ImageView imgTipo;
        @BindView(R.id.txt_nombre)
        TextView txtNombre;
        @BindView(R.id.txt_url)
        TextView txtUrl;
        @BindView(R.id.card_view)
        CardView cardView;
        private Listener listener;
        private ColaborativaUi colaborativaUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ColaborativaUi colaborativaUi, Listener listener) {
            this.colaborativaUi = colaborativaUi;
            this.listener = listener;
            switch (colaborativaUi.getTipo()){
                case KAHOOT:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_kahoot_logo));
                    break;
                case JAMBOARD:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_jamboard_logo));
                    break;
                case GOOGLEDOCS:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_google_docs_logo));
                    break;
                case GOOGLEDRIVE:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_google_drive_logo));
                    break;
                case MEET:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_meet_logo));
                    break;
                case ZOOM:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_ico_videocall));
                    break;
                case GRABACION:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_rec));
                    break;
                default:
                    imgTipo.setImageDrawable(ContextCompat.getDrawable(imgTipo.getContext(), R.drawable.ic_ico_videocall));
                    break;
            }

            txtNombre.setText(colaborativaUi.getNombre());
            txtUrl.setText(colaborativaUi.getDescripcion());

            cardView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onClickColobarativa(colaborativaUi);
        }
    }

    public interface Listener{
        void onClickColobarativa(ColaborativaUi colaborativaUi);
    }
}
