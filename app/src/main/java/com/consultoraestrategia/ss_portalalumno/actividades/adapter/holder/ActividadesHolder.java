package com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.RecursosAdapter;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.EEstado;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kike on 08/02/2018.
 */

public class ActividadesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static String TAG = ActividadesHolder.class.getSimpleName();

    @BindView(R.id.txt_actividad_det)
    TextView txtactividad;
    @BindView(R.id.txt_descripcion_act_det)
    TextView txtDescripcionActDet;
    @BindView(R.id.rv_act_recursos)
    RecyclerView rvActRecursos;
    @BindView(R.id.conten_actividad)
    ConstraintLayout contenActividad;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout titleActividad;
    @BindView(R.id.cont_img)
    ConstraintLayout contImg;

    private RecursosAdapter recursosAdapter;
    private final static String[] estado = {"Hecho", "Pendiente"};
    private final static int[] colorId = {R.color.md_blue_500, R.color.md_red_500};
    private ActividadesUi actividadesUi;
    private ActividadListener actividadListener;

    public ActividadesHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        titleActividad.setOnClickListener(this);
    }


    public void bind(final ActividadesUi actividadesUi, int position, ActividadListener actividadListener, DownloadItemListener downloadItemListener) {
        this.actividadesUi = actividadesUi;
        this.actividadListener = actividadListener;
        txtactividad.setText(actividadesUi.getNombreActividad());

        txtDescripcionActDet.setText(actividadesUi.getDescripcionActividad());

        rvActRecursos.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        rvActRecursos.setHasFixedSize(false);
        rvActRecursos.setNestedScrollingEnabled(false);
        ((SimpleItemAnimator) rvActRecursos.getItemAnimator()).setSupportsChangeAnimations(false);
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(actividadesUi.getRecursosUiList());
        objects.addAll(actividadesUi.getSubRecursosUiList());
        recursosAdapter = new RecursosAdapter(objects, position, downloadItemListener);
        rvActRecursos.setAdapter(recursosAdapter);
        contenActividad.setVisibility(actividadesUi.isToogle()?View.VISIBLE:View.GONE);
        Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.circle_actividad);
        try {
            circle.mutate().setColorFilter(Color.parseColor(actividadesUi.getColor1()), PorterDuff.Mode.SRC_ATOP);
            txtactividad.setTextColor(Color.parseColor(actividadesUi.getColor2()));
        }catch (Exception e){
            e.printStackTrace();
        }
        contImg.setBackground(circle);

    }

    @Override
    public void onClick(View view) {
        actividadListener.onClickActividad(actividadesUi);
    }


    public RecyclerView getRvActRecursos() {
        return rvActRecursos;
    }
}
