package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnGridLayoutManager;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.AdapterSesiones;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.SesionColumnCountProvider;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.UnidadesAdapter;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.UnidadAprendizajeUi;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnidadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final String TAG = "UnidadViewHolder";
    @BindView(R.id.txt_ver_mas)
    ImageView txtVerMas;
    @BindView(R.id.aprendizaje)
    ImageView aprendizaje;
    @BindView(R.id.txt_tituloUnidad)
    TextView txtTituloUnidad;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.rv_sesiones)
    RecyclerView rvSesiones;
    @BindView(R.id.txt_vacio)
    LinearLayout txtVacio;
    @BindView(R.id.card_view_2)
    CardView cardView2;
    @BindView(R.id.txt_ver_mas_sesiones)
    TextView txtVerMasSesiones;

    private UnidadAprendizajeUi unidadAprendizaje;
    private String color;
    private UnidadesAdapter.UnidadListener unidadListener;
    private AdapterSesiones adapter;
    private int columnas;
    private SesionColumnCountProvider columnCountProvider;
    private SesionHolder.GridSpacingItemDecoration addItemDecoration;

    public UnidadViewHolder(@NonNull View itemView, UnidadesAdapter.UnidadListener unidadListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.unidadListener = unidadListener;
        setListColumanas();
    }

    public void bind(UnidadAprendizajeUi unidadAprendizaje, String color) {
        this.unidadAprendizaje = unidadAprendizaje;
        this.color = color;


        String titulo = "U"+unidadAprendizaje.getNroUnidad()+": "+unidadAprendizaje.getTitulo();
        txtTituloUnidad.setText(titulo);
        try {
            view3.setBackgroundColor(Color.parseColor(color));
            txtTituloUnidad.setTextColor(Color.parseColor(color));
        }catch (Exception e){
            e.printStackTrace();
        }


        if (unidadAprendizaje.getObjectListSesiones().size() <= 0){
            txtVerMas.setVisibility(View.GONE);
            txtVacio.setVisibility(View.VISIBLE);
        }else{
            txtVacio.setVisibility(View.GONE);
            txtVerMas.setVisibility(View.VISIBLE);
        }

        int spacing = 15; // 50px
        columnas = columnCountProvider.getColumnCount(rvSesiones.getWidth());
        if(addItemDecoration!=null)rvSesiones.removeItemDecoration(addItemDecoration);
        addItemDecoration = new SesionHolder.GridSpacingItemDecoration(columnas, spacing,false);
        rvSesiones.addItemDecoration(addItemDecoration);

        int cantidad = columnas * 2;
        boolean isVisibleVerMas = cantidad < unidadAprendizaje.getObjectListSesiones().size();
        if(unidadAprendizaje.getCantidadUnidades()==1){
            isVisibleVerMas = false;
        }
        if(isVisibleVerMas){
            cardView.setOnClickListener(this);
            cardView2.setOnClickListener(this);
            txtVerMas.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.VISIBLE);
        }else {
            cardView.setOnClickListener(null);
            cardView2.setOnClickListener(null);
            txtVerMas.setVisibility(View.GONE);
            cardView2.setVisibility(View.GONE);
        }



        if(unidadAprendizaje.isToogle()){
            if(isVisibleVerMas){
                setViewLess();
                txtVerMasSesiones.setText("Ver solo las últimas sesiones");
            }
            adapter.setList(unidadAprendizaje.getObjectListSesiones());
        }else {
            if(isVisibleVerMas){
                setViewMore();
                txtVerMasSesiones.setText("Ver más sesiones");
                adapter.setList(unidadAprendizaje.getObjectListSesiones(), cantidad);
            }else {
                adapter.setList(unidadAprendizaje.getObjectListSesiones());
            }

        }


    }

    private void setViewMore(){
        try{
            Drawable mDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.view);
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN));
            txtVerMas.setBackground(mDrawable);
            txtVerMas.setRotation(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setListColumanas(){
        Log.d(TAG, "setListColumanas");
        AutoColumnGridLayoutManager autoColumnGridLayoutManager = new AutoColumnGridLayoutManager(itemView.getContext(), OrientationHelper.VERTICAL, false);
        columnCountProvider = new SesionColumnCountProvider(itemView.getContext());
        autoColumnGridLayoutManager.setColumnCountProvider(columnCountProvider);
        rvSesiones.setNestedScrollingEnabled(false);
        rvSesiones.setHasFixedSize(false);
        rvSesiones.setLayoutManager(autoColumnGridLayoutManager);
        adapter = new AdapterSesiones( new ArrayList<>(), false, unidadListener);
        rvSesiones.setAdapter(adapter);

    }

    private void setViewLess(){
        try{
            Drawable mDrawable = ContextCompat.getDrawable( itemView.getContext() ,R.drawable.view);
            mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_IN));
            txtVerMas.setBackground(mDrawable);
            txtVerMas.setRotation(180);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.card_view:
                onClickVerMas();
                break;
            case R.id.card_view_2:
                onClickVerMas();
                break;
        }

    }

    private void onClickVerMas() {
        unidadListener.onClickUnidadAprendizaje(unidadAprendizaje);
    }

}
