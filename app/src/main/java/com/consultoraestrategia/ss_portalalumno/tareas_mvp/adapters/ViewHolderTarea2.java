package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import org.zakariya.stickyheaders.SectioningAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolderTarea2 extends SectioningAdapter.ItemViewHolder {
    int SELECTOR_ICONOS = 409;

    @BindView(R.id.txtNroTarea)
    ImageView txtNroTarea;
    @BindView(R.id.txtCreador)
    TextView txtCreador;
    @BindView(R.id.txtFechaCreacion)
    TextView txtFechaCreacion;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.img_pregunta)
    CircleImageView imgPregunta;
    @BindView(R.id.btn_img_pregunta)
    FrameLayout btnImgPregunta;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.img_descripcion)
    TextView imgDescripcion;


    public ViewHolderTarea2(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(TareasUI tareasUI, TareasUIListener tareasUIListener, int position, HeaderTareasAprendizajeUI headerTareasAprendizajeUI, ParametroDisenioUi parametroDisenioUi, boolean state) {
        String titulo = "Tarea "  + (position );
        txtCreador.setText(titulo);
        textView8.setText(tareasUI.getTituloTarea());
        txtNroTarea.setImageResource(R.drawable.report);
        if (state) txtNroTarea.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_teal_800));
        else try {
            txtNroTarea.setBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));
        }catch (Exception e){e.getStackTrace();}

        if (!(tareasUI.getFechaLimite() == 0D)) {
            String hora = UtilsPortalAlumno.changeTime12Hour(tareasUI.getHoraEntrega());
            String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(tareasUI.getFechaLimite());
            if(!TextUtils.isEmpty(hora)){
                fecha_entrega = fecha_entrega + " " + hora;
            }
            txtFechaCreacion.setText(fecha_entrega);
        } else {
            txtFechaCreacion.setText("Sin límite de entrega");
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tareasUIListener.onClicTarea(tareasUI);
            }
        });

        if(!TextUtils.isEmpty(tareasUI.getNota())){
            if(tareasUI.getTipoNotaId()==SELECTOR_ICONOS){
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

    }
}
