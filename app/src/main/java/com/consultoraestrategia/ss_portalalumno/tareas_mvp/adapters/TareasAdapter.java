package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by irvinmarin on 27/02/2017.
 */

public class TareasAdapter extends RecyclerView.Adapter<ViewHolderTarea2> {
    private static final String TAG = TareasAdapter.class.getSimpleName();


    private List<TareasUI> tareasUIList = new ArrayList<>();
    private TareasUIListener listener;
    private HeaderTareasAprendizajeUI headerTareasAprendizajeUI;
    private ParametroDisenioUi parametroDisenioUi;
    private RecyclerView recyclerView;

    public TareasAdapter(HeaderTareasAprendizajeUI headerTareasAprendizajeUI,
                         TareasUIListener listener,
                         ParametroDisenioUi parametroDisenioUi,
                         RecyclerView recyclerView) {
        this.listener = listener;
        this.headerTareasAprendizajeUI = headerTareasAprendizajeUI;
        this.parametroDisenioUi = parametroDisenioUi;
        this.recyclerView = recyclerView;

    }

    public void setParametroDisenioUi(ParametroDisenioUi parametroDisenioUi) {
        this.parametroDisenioUi = parametroDisenioUi;
    }


    public interface SpinnerListener {
        void onEliminarListener(TareasUI tareasUI);
        void onAddTareaListener(TareasUI tareasUI);
        void onUpdateTareaListener(TareasUI tareasUI);
    }

    @Override
    public ViewHolderTarea2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas_unidades, parent, false);
        return new ViewHolderTarea2(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolderTarea2 vh, final int position) {
        //vh.bind(tareasUIList.get(position), listener, imageLoader, position, spinnerListener, headerTareasAprendizajeUI, parametroDisenioUi);
        TareasUI tareasUI = tareasUIList.get(position);
        int total = headerTareasAprendizajeUI.getTareasUIList().size();
        vh.bind(tareasUI, listener, total - position, headerTareasAprendizajeUI, parametroDisenioUi, true);
    }

    @Override
    public int getItemCount() {
        return tareasUIList.size();
    }

    public void setTareasUIList(HeaderTareasAprendizajeUI headerTareasAprendizajeUI) {
        this.tareasUIList.clear();
        if(headerTareasAprendizajeUI==null)return;
        List<TareasUI> tareasUIList = headerTareasAprendizajeUI.getTareasUIList();
        if(tareasUIList==null)return;
        this.headerTareasAprendizajeUI = headerTareasAprendizajeUI;
        Log.d(TAG, "setTareasUIList: " + tareasUIList.size());
        this.tareasUIList.addAll(tareasUIList);
        notifyDataSetChanged();
    }

    public void setTareasUIList(List<TareasUI> tareasUIList) {
        this.tareasUIList.clear();
        this.tareasUIList.addAll(tareasUIList);
        notifyDataSetChanged();
    }

    public void delete(TareasUI TareasUI) {
        Log.d(TAG, "elimimna");
        int position = tareasUIList.indexOf(TareasUI);
        if (position != -1) {
            tareasUIList.remove(TareasUI);
            notifyItemRemoved(position);
        }
    }

    public void add(TareasUI tareasUI) {
        Log.d(TAG, "agregar");
        if (!this.tareasUIList.contains(tareasUI)) {
            this.tareasUIList.add(tareasUI);
            notifyItemInserted(getItemCount()-1);
            scrollToLastItem();
        }
    }
    public int update(TareasUI tareasUI) {
        int position = this.tareasUIList.indexOf(tareasUI);
        if (position != -1) {
            this.tareasUIList.set(position, tareasUI);
            notifyItemChanged(position);
        }
        return position;

    }

    private void scrollToLastItem() {
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    public List<DownloadAdapter> getListDownloadAdapter(RepositorioFileUi repositorioFileUi) {
        List<DownloadAdapter> downloadAdapters = new ArrayList<>();
        try {

            RecursosUI recursosUI = (RecursosUI)repositorioFileUi;
            for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if(holder instanceof  ViewHolderTareas){
                    ViewHolderTareas viewHolderTareas = (ViewHolderTareas) holder;
                    List<RecursosUI> repositorioFileUiList = viewHolderTareas.getRecursosUIList();
                    int posicion = repositorioFileUiList.indexOf(recursosUI);
                    if(posicion!=-1){
                        DownloadAdapter downloadAdapter = viewHolderTareas.getRecursosTareaAdapter();
                        downloadAdapters.add(downloadAdapter);
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return downloadAdapters;
    }

    public void update(RepositorioFileUi repositorioFileUi){
        for (DownloadAdapter downloadAdapter : getListDownloadAdapter(repositorioFileUi)){
            downloadAdapter.update(repositorioFileUi);
        }
    }

    public void updateProgress(RepositorioFileUi repositorioFileUi, int count) {
        for (DownloadAdapter downloadAdapter : getListDownloadAdapter(repositorioFileUi)){
            downloadAdapter.updateProgress(repositorioFileUi, count);
        }
    }

}
