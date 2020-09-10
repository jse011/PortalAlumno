package com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.RecursosAdapter;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SCIEV on 20/08/2017.
 */

public class SubRecursosHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.conteo_sub_recurso)
    TextView textViewConteoSubRecurso;
    @BindView(R.id.nombre_sub_recurso)
    TextView textViewNombreSubRecurso;
    @BindView(R.id.recycler_sub_recurso)
    RecyclerView recyclerView;
    @BindView(R.id.imageView)
    ImageView imageViewRotate;
    @BindView(R.id.subRecursos)
    ConstraintLayout vistCompleta;
    @BindView(R.id.txtEmpty)
    TextView textViewEmpty;
    RecursosAdapter recursosAdapter;
    SubRecursosUi subRecursosUi;
    boolean istrue = true;
    String TAG = SubRecursosHolder.class.getSimpleName();

    public SubRecursosHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
        vistCompleta.setOnClickListener(this);
    }

    public void bind(SubRecursosUi subRecursosUi, int positionActividad, DownloadItemListener downloadItemListener) {
        this.subRecursosUi = subRecursosUi;
        int positionActual = positionActividad + 1;
        textViewConteoSubRecurso.setText(String.valueOf(positionActual + "." + subRecursosUi.getConteoSubRecurso()));
        try {
            textViewConteoSubRecurso.setTextColor(Color.parseColor(subRecursosUi.getColor()));
        }catch (Exception e){
            e.printStackTrace();
        }

        textViewNombreSubRecurso.setText(subRecursosUi.getNombreSubRecurso());


        Log.d(TAG, "subActiidades: " + subRecursosUi.getNombreSubRecurso());
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        recyclerView.setHasFixedSize(false);
        List<Object> objects = new ArrayList<>();
       if (subRecursosUi.getRecursosUiList()!=null)objects.addAll(subRecursosUi.getRecursosUiList());

        recursosAdapter = new RecursosAdapter(objects, 0, downloadItemListener);
        recyclerView.setAdapter(recursosAdapter);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);

        textViewEmpty.setVisibility(View.GONE);
        if (subRecursosUi.getRecursosUiList().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subRecursos:
                onClickItemView(subRecursosUi);
                break;
            default:
                break;
        }
    }

    private void onClickItemView(SubRecursosUi subRecursosUi) {
        if (subRecursosUi.getRecursosUiList().isEmpty()) {
            if (istrue) {
                //recyclerView.setVisibility(View.VISIBLE);
                istrue = false;
                imageViewRotate.clearAnimation();
                ViewCompat.animate(imageViewRotate).rotation(180).start();
                //textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                //textViewEmpty.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.GONE);
                istrue = true;
                imageViewRotate.clearAnimation();
                ViewCompat.animate(imageViewRotate).rotation(0).start();
            }
        } else {
            if (istrue) {
                //recyclerView.setVisibility(View.VISIBLE);
                //recyclerView.setVisibility(View.VISIBLE);
                istrue = false;
                imageViewRotate.clearAnimation();
                ViewCompat.animate(imageViewRotate).rotation(180).start();
            } else {
                //textViewEmpty.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.GONE);
                istrue = true;
                imageViewRotate.clearAnimation();
                ViewCompat.animate(imageViewRotate).rotation(0).start();
            }
        }

    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
