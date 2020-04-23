package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.CursosUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CursosAdapter extends RecyclerView.Adapter<CursosAdapter.ViewHolder> {

    private List<CursosUi> cursosUiList = new ArrayList<>();
    private Listener listener;

    public CursosAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cursosUiList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return cursosUiList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.fondo)
        ImageView fondo;
        @BindView(R.id.txtNombreCurso)
        TextView txtNombreCurso;
        @BindView(R.id.txtPeriodo)
        TextView txtPeriodo;
        @BindView(R.id.txtHorario)
        TextView txtHorario;
        @BindView(R.id.txtSalon)
        TextView txtSalon;
        @BindView(R.id.txtProfesor)
        TextView txtProfesor;
        @BindView(R.id.img_profesor)
        CircleImageView imgProfesor;
        @BindView(R.id.txtNombreDocente)
        TextView txtNombreDocente;
        @BindView(R.id.imgAccionClase)
        Button imgAccionClase;
        @BindView(R.id.imgDocente)
        ImageView imgDocente;
        @BindView(R.id.contItemView)
        ConstraintLayout contItemView;
        @BindView(R.id.cardviewCurso)
        CardView cardviewCurso;
        private Listener listener;
        private CursosUi cursosUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(CursosUi cursosUi, Listener listener) {
            this.listener = listener;
            this.cursosUi = cursosUi;
            txtNombreCurso.setText(cursosUi.getNombre());

            txtPeriodo.setText(cursosUi.getSeccionyperiodo());

            txtSalon.setText(cursosUi.getSalon());

            Glide.with(itemView.getContext())
                    .load(cursosUi.getFotoProfesor())
                    .apply(UtilsGlide.getGlideRequestOptions())
                    .into(imgProfesor);
            String docente = "Docente: "+cursosUi.getProfesor();
            txtNombreDocente.setText(docente);


            fondo.setAlpha((float) 0.2);

            try {
                contItemView.setBackgroundColor(Color.parseColor(cursosUi.getBackgroundSolidColor()));
            }catch (Exception e){
                e.printStackTrace();
            }
            RequestOptions options = new RequestOptions()
                    .error(R.drawable.ic_error_outline_black)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(itemView.getContext())
                    .load(cursosUi.getUrlBackgroundItem())
                    .apply(options)
                    .into(fondo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.listener.onClick(this.cursosUi);
        }
    }

    public void setList(List<CursosUi> cursosUis) {
        cursosUiList.clear();
        cursosUiList.addAll(cursosUis);
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClick(CursosUi cursosUi);
    }
}
