package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.UnidadAprendizajeListener;

import org.zakariya.stickyheaders.SectioningAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

class ViewHolderUnidades extends SectioningAdapter.HeaderViewHolder implements  View.OnClickListener {
    private static final String TAG = ViewHolderUnidades.class.getSimpleName();

    @BindView(R.id.txtTituloSesion)
    TextView txtTituloSesion;
    @BindView(R.id.addTarea)
    ImageView fabNuevaTarea;
    @BindView(R.id.img_opcion)
    ImageView imgOpcion;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.card)
    CardView cardView;
    UnidadAprendizajeListener listener;
    HeaderTareasAprendizajeUI headerTareasAprendizajeUI;
    UnidadesAdapter unidadesAdapter;

    public ViewHolderUnidades(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        imgOpcion.setOnClickListener(this);
//        tareasAdapter = ;

    }

    public void bind(final HeaderTareasAprendizajeUI headerTareasAprendizajeUI, TareasUIListener tareasUIListener, final UnidadAprendizajeListener listener, int position, final int mIdCurso, final ParametroDisenioUi parametroDisenioUi, final boolean status ) {
        Log.d(TAG, "TareasUI : " + headerTareasAprendizajeUI.toString());
        Log.d("ParametroDisenioDocente", "TareasUI : " + parametroDisenioUi.toString());
        this.headerTareasAprendizajeUI = headerTareasAprendizajeUI;
        this.listener = listener;
        String titulo = "Unidad "+headerTareasAprendizajeUI.getNroUnidad()+": "+headerTareasAprendizajeUI.getTituloSesionAprendizaje();
        txtTituloSesion.setText(titulo);

        try {
           txtTituloSesion.setTextColor(Color.parseColor(parametroDisenioUi.getColor1()));
           view3.setBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));


        if (headerTareasAprendizajeUI.isDocente()) {
            if (headerTareasAprendizajeUI.isCalendarioEditar()){
                //fabNuevaTarea.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                Drawable icons = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_circle_big);
                icons.mutate().setColorFilter(Color.parseColor(parametroDisenioUi.getColor1()), PorterDuff.Mode.SRC_ATOP);
                add.setBackground(icons);
                cardView.setCardBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));
                Drawable icon = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_add_black);
                icon.mutate().setColorFilter(Color.parseColor(parametroDisenioUi.getColor1()), PorterDuff.Mode.SRC_ATOP);
                //fabNuevaTarea.setBackground(icon);
            }else add.setVisibility(View.GONE);

        } else {
            //fabNuevaTarea.setVisibility(View.GONE);
            add.setVisibility(View.GONE);
        }

        if (status){
            constraintLayout.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayout.setVisibility(View.GONE);
            //fabNuevaTarea.setVisibility(View.GONE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "headerTareasAprendizajeUI : " + headerTareasAprendizajeUI.toString());
                listener.onBtnCrearTareaClicked(headerTareasAprendizajeUI, headerTareasAprendizajeUI.getIdSilaboEvento(), mIdCurso);
            }
        });
        }
        catch (Exception e){e.getStackTrace();}

    }

    /*
    @Override
    public void onEliminarListener(TareasUI tareasUI) {
        Log.d(TAG, "listener eliminar");
        unidadesAdapter.delete(tareasUI);
    }

    @Override
    public void onAddTareaListener(TareasUI tareasUI) {
        unidadesAdapter.add(tareasUI);
    }

    @Override
    public void onUpdateTareaListener(TareasUI tareasUI) {
        unidadesAdapter.update(tareasUI);

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_opcion:
                onClickOpcion(view,headerTareasAprendizajeUI);
                break;
        }
    }

    private void onClickOpcion(View view, HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        listener.onClickOpcion(view,headerTareasAprendizajeUI);
    }
}
