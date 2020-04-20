package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import org.zakariya.stickyheaders.SectioningAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

class ViewHolderRecurso extends SectioningAdapter.ItemViewHolder{
    private static final String TAG = ViewHolderRecurso.class.getSimpleName();

    @BindView(R.id.fabRemove)
    ImageView fabRemove;
    @BindView(R.id.imgRecurso)
    ImageView imgRecurso;
    @BindView(R.id.txtNombreRecurso)
    TextView txtNombreRecurso;
    @BindView(R.id.txtdescripcion)
    TextView txtdescripcion;

    public ViewHolderRecurso(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        imageLoader = new GlideImageLoader(itemView.getContext());
    }

    public void bind(final RecursosUI recursosUI, final TareasUI tareasUI, final int tipoVista, final UnidadesAdapter.RecursoRemoveListener recursoRemoveListener) {

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
