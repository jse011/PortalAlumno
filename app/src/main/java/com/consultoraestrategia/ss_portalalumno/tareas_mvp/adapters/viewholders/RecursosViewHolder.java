package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.viewholders;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.RecursosTareaAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irvinmarin on 06/11/2017.
 */

public class RecursosViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = RecursosViewHolder.class.getSimpleName();

    @BindView(R.id.fabRemove)
    ImageView fabRemove;
    @BindView(R.id.imgRecurso)
    ImageView imgRecurso;
    @BindView(R.id.txtNombreRecurso)
    TextView txtNombreRecurso;
    @BindView(R.id.txtdescripcion)
    TextView txtdescripcion;

    public RecursosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        imageLoader = new GlideImageLoader(itemView.getContext());
    }

    public void bind(final RecursosUI recursosUI, final TareasUI tareasUI, final int tipoVista, final RecursosTareaAdapter.RecursoRemoveListener recursoRemoveListener) {


        if(TextUtils.isEmpty(recursosUI.getDescripcion())){
            txtdescripcion.setText(recursosUI.getUrl());
        }else {
            txtdescripcion.setText(recursosUI.getDescripcion());
        }

        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tareasUI == null) {
                    recursoRemoveListener.onbtnDeleteRecursoFromList(recursosUI);
                } else {
                    recursoRemoveListener.onbtnDeleteRecursoDataBase(tareasUI, recursosUI);
                }
            }
        });
    }



}
