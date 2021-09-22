package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;
import com.consultoraestrategia.ss_portalalumno.util.ColorTransparentUtils;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by irvinmarin on 27/02/2017.
 */

public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {
    private static final String TAG = TareasAdapter.class.getSimpleName();

    private List<TareasUI> tareasUIList = new ArrayList<>();
    TareasUIListener tareasUIListener;
    private int itemCount;

    public TareasAdapter(TareasUIListener tareasUIListener) {
        this.tareasUIListener = tareasUIListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas_unidades_simple, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(tareasUIList.get(position),tareasUIListener);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<TareasUI> tareasUIList, int cantidad) {
        this.tareasUIList.clear();
        this.tareasUIList.addAll(tareasUIList);
        itemCount = cantidad;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<TareasUI> tareasUIList) {
        this.tareasUIList.clear();
        this.tareasUIList.addAll(tareasUIList);
        itemCount = tareasUIList.size();
        notifyDataSetChanged();
    }

    public void update(TareasUI tareasUI) {
        int position = tareasUIList.indexOf(tareasUI);
        if(position!=-1){
            tareasUIList.set(position, tareasUI);
            notifyItemChanged(position);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_titulo_tarea)
        TextView txtTituloTarea;
        @BindView(R.id.img_icono)
        ImageView imgIcono;
        @BindView(R.id.txt_descripcion)
        TextView txtDescripcion;
        @BindView(R.id.txt_fecha)
        TextView txtFecha;
        @BindView(R.id.img_descripcion)
        TextView imgDescripcion;
        @BindView(R.id.img_pregunta)
        CircleImageView imgPregunta;

        private  TareasUIListener tareasUIListener;
        private TareasUI tareasUI;
        ParametroDisenioUi parametroDisenioUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TareasUI tareasUI, TareasUIListener tareasUIListener) {
            this.tareasUI = tareasUI;
            this.tareasUIListener = tareasUIListener;
            this.parametroDisenioUi = tareasUI.getParametroDisenioUi();
            String titulo =  "Tarea " + tareasUI.getPosition();
            txtTituloTarea.setText(titulo);
            try {
                imgIcono.setColorFilter(Color.parseColor(parametroDisenioUi.getColor1()), android.graphics.PorterDuff.Mode.SRC_IN);
                txtTituloTarea.setTextColor(Color.parseColor(parametroDisenioUi.getColor1()));
                //card_fondo.setCardBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(Color.parseColor(parametroDisenioUi.getColor3()), 2)));
            }catch (Exception e){
                e.printStackTrace();
            }
            txtDescripcion.setText(tareasUI.getTituloTarea());

            if (!(tareasUI.getFechaLimite() == 0D)) {
                String hora = UtilsPortalAlumno.changeTime12Hour(tareasUI.getHoraEntrega());
                String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(tareasUI.getFechaLimite());
                if(!TextUtils.isEmpty(hora)){
                    fecha_entrega = "Para el "+ fecha_entrega + " " + hora;
                }
                txtFecha.setText(fecha_entrega);
            } else {
                txtFecha.setText("Sin límite de entrega");
            }

            if(!TextUtils.isEmpty(tareasUI.getNota())){
                if(tareasUI.getTipoNotaId()==409){//SELECTOR_ICONOS
                    imgPregunta.setVisibility(View.VISIBLE);
                    imgDescripcion.setVisibility(View.GONE);
                    Glide.with(imgPregunta)
                            .load(tareasUI.getNota())
                            .apply(UtilsGlide.getGlideRequestOptions())
                            .into(imgPregunta);
                }else {
                    imgDescripcion.setVisibility(View.VISIBLE);
                    imgPregunta.setVisibility(View.GONE);
                    imgDescripcion.setText(tareasUI.getNota());
                }
            }else {
                imgPregunta.setVisibility(View.GONE);
                imgDescripcion.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            tareasUIListener.onClicTarea(tareasUI);
        }
    }

}
