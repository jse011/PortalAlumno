package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.entities.TareasC;
import com.consultoraestrategia.ss_portalalumno.entities.UnidadAprendizaje;
import com.consultoraestrategia.ss_portalalumno.gadgets.autoColumnGrid.AutoColumnGridLayoutManager;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder.SesionHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnidadAdapter2 extends RecyclerView.Adapter<UnidadAdapter2.ViewHolder> {

    private List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList;
    TareasUIListener tareasUIListener;
    RecyclerView recyclerView;
    public UnidadAdapter2(TareasUIListener tareasUIListener, RecyclerView recyclerView) {
        this.tareasUIListener = tareasUIListener;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas_unidades_2, parent, false);
        return new ViewHolder(v, tareasUIListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(headerTareasAprendizajeUIList.get(position));
    }

    @Override
    public int getItemCount() {
        return headerTareasAprendizajeUIList!=null?headerTareasAprendizajeUIList.size():0;
    }

    public void setList(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList) {
        //this.headerTareasAprendizajeUIList.clear();
        this.headerTareasAprendizajeUIList = new ArrayList<>(headerTareasAprendizajeUIList);
        notifyDataSetChanged();
    }

    public void update(HeaderTareasAprendizajeUI unidadAprendizajeUi, TareasUI tareasUI) {
        try {

            for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if(holder instanceof  UnidadAdapter2.ViewHolder){
                    UnidadAdapter2.ViewHolder viewHolder = (UnidadAdapter2.ViewHolder) holder;
                    HeaderTareasAprendizajeUI item = viewHolder.getUnidadAprendizaje();
                    if(item.getIdUnidadAprendizaje() == unidadAprendizajeUi.getIdUnidadAprendizaje()){
                        viewHolder.updateRecyclerTarea(tareasUI);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(HeaderTareasAprendizajeUI unidadAprendizajeUi, List<TareasUI> tareasUIModificadaList) {
        try {

            for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if(holder instanceof  UnidadAdapter2.ViewHolder){
                    UnidadAdapter2.ViewHolder viewHolder = (UnidadAdapter2.ViewHolder) holder;
                    HeaderTareasAprendizajeUI item = viewHolder.getUnidadAprendizaje();
                    if(item.getIdUnidadAprendizaje() == unidadAprendizajeUi.getIdUnidadAprendizaje()){
                        viewHolder.updateRecyclerTarea(tareasUIModificadaList);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(HeaderTareasAprendizajeUI unidadAprendizajeUi) {
        int position = this.headerTareasAprendizajeUIList.indexOf(unidadAprendizajeUi);
        if(position!=-1){
            this.headerTareasAprendizajeUIList.set(position, unidadAprendizajeUi);
            notifyItemChanged(position);
        }
    }



    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TareasUIListener tareasUIListener;
        @BindView(R.id.txt_ver_mas)
        ImageView txtVerMas;
        @BindView(R.id.txt_titulo)
        TextView txtTitulo;
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
        @BindView(R.id.txt_ver_mas_tareas)
        TextView txtVerMasTareas;
        private TareaColumnCountProvider columnCountProvider;
        private TareasAdapter adapter;
        private HeaderTareasAprendizajeUI unidadAprendizaje;
        private ParametroDisenioUi parametroDisenioUi;
        private int columnas;
        private SesionHolder.GridSpacingItemDecoration addItemDecoration;

        public ViewHolder(@NonNull View itemView, TareasUIListener tareasUIListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.tareasUIListener = tareasUIListener;
            setListColumanas();
        }

        public void bind(HeaderTareasAprendizajeUI unidadAprendizaje) {
            this.unidadAprendizaje = unidadAprendizaje;
            this.parametroDisenioUi = unidadAprendizaje.getParametroDisenioUi();

            String titulo = "U"+unidadAprendizaje.getNroUnidad()+": "+unidadAprendizaje.getTituloSesionAprendizaje();
            txtTitulo.setText(titulo);
            try {
                view3.setBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));
                txtTitulo.setTextColor(Color.parseColor(parametroDisenioUi.getColor1()));
            }catch (Exception e){
                e.printStackTrace();
            }


            if (unidadAprendizaje.getTareasUIList().size() <= 0){
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
            boolean isVisibleVerMas = cantidad < unidadAprendizaje.getTareasUIList().size();
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
                    txtVerMasTareas.setText("Ver solo las últimas tareas");
                }
                adapter.setList(unidadAprendizaje.getTareasUIList());
            }else {
                if(isVisibleVerMas){
                    setViewMore();
                    txtVerMasTareas.setText("Ver más tareas");
                    adapter.setList(unidadAprendizaje.getTareasUIList(), cantidad);
                }else {
                    adapter.setList(unidadAprendizaje.getTareasUIList());
                }

            }
        }

        private void setListColumanas(){
            AutoColumnGridLayoutManager autoColumnGridLayoutManager = new AutoColumnGridLayoutManager(itemView.getContext(), OrientationHelper.VERTICAL, false);
            columnCountProvider = new TareaColumnCountProvider(itemView.getContext());
            autoColumnGridLayoutManager.setColumnCountProvider(columnCountProvider);
            rvSesiones.setNestedScrollingEnabled(false);
            rvSesiones.setHasFixedSize(false);
            ((SimpleItemAnimator) rvSesiones.getItemAnimator()).setSupportsChangeAnimations(false);
            rvSesiones.setLayoutManager(autoColumnGridLayoutManager);
            adapter = new TareasAdapter(tareasUIListener);
            rvSesiones.setAdapter(adapter);

        }

        private void setViewMore(){
            try{
                Drawable mDrawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.view);
                mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(parametroDisenioUi.getColor1()), PorterDuff.Mode.SRC_IN));
                txtVerMas.setBackground(mDrawable);
                txtVerMas.setRotation(0);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        private void setViewLess(){
            try{
                Drawable mDrawable = ContextCompat.getDrawable( itemView.getContext() ,R.drawable.view);
                mDrawable.setColorFilter(new PorterDuffColorFilter(Color.parseColor(parametroDisenioUi.getColor1()), PorterDuff.Mode.SRC_IN));
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
            tareasUIListener.onClickUnidadAprendizaje(unidadAprendizaje);
        }

        public HeaderTareasAprendizajeUI getUnidadAprendizaje() {
            return unidadAprendizaje;
        }

        public void updateRecyclerTarea(TareasUI tareasUI) {
            adapter.update(tareasUI);
        }

        public void updateRecyclerTarea(List<TareasUI> tareasUIModificadaList) {
            for (TareasUI tareasUI : tareasUIModificadaList)adapter.update(tareasUI);
        }
    }

    class PlanetDiffUtilCallback extends DiffUtil.Callback {

        private List<HeaderTareasAprendizajeUI> oldList;
        private List<HeaderTareasAprendizajeUI> newList;

        public PlanetDiffUtilCallback(List<HeaderTareasAprendizajeUI> oldList, List<HeaderTareasAprendizajeUI> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getIdUnidadAprendizaje() == newList.get(oldItemPosition).getIdUnidadAprendizaje();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return equals(oldList.get(oldItemPosition), newList.get(oldItemPosition));
        }

        private boolean equals(HeaderTareasAprendizajeUI o1, HeaderTareasAprendizajeUI o2){
            if (o1.idUnidadAprendizaje != o2.idUnidadAprendizaje) return false;
            if (o1.idSesionAprendizaje != o2.idSesionAprendizaje) return false;
            if (o1.idSilaboEvento != o2.idSilaboEvento) return false;
            if (o1.getNroUnidad() != o2.getNroUnidad()) return false;
            return o1.tituloSesionAprendizaje != null ? !o1.tituloSesionAprendizaje.equals(o2.tituloSesionAprendizaje) : o2.tituloSesionAprendizaje != null;
        }
    }

}
