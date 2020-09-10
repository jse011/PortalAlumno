package com.consultoraestrategia.ss_portalalumno.actividades.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.ActividadListener;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.ActividadesHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.RecursosHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.SubRecursosHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.ViewHolderRecursosTitulo;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.ActividadesUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kike on 07/02/2018.
 */

public class ActividadesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TIPO_ACTIVIDAD = 1, TIPO_RECURSO = 0;
    private List<Object> itemObjectList;
    private ActividadListener actividadListener;
    private DownloadItemListener downloadItemListener;
    private RecyclerView recyclerView;

    public ActividadesAdapter(List<Object> itemObjectList, ActividadListener actividadListener, DownloadItemListener downloadItemListener, RecyclerView recyclerView) {
        this.itemObjectList = itemObjectList;
        this.actividadListener = actividadListener;
        this.downloadItemListener = downloadItemListener;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TIPO_ACTIVIDAD:
                View view1 = layoutInflater.inflate(R.layout.layout_item_actividades, parent, false);
                viewHolder = new ActividadesHolder(view1);
                break;
            case TIPO_RECURSO:
                View view2 = layoutInflater.inflate(R.layout.layout_item_recursos, parent, false);
                viewHolder = new RecursosHolder(view2);
                break;
            default:
                View view3 = layoutInflater.inflate(R.layout.layout_item_recursos_titulo, parent, false);
                viewHolder = new ViewHolderRecursosTitulo(view3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TIPO_ACTIVIDAD:
                ActividadesUi actividadesUi = (ActividadesUi) itemObjectList.get(position);
                ActividadesHolder vh1 = (ActividadesHolder) holder;
                vh1.bind(actividadesUi, position, actividadListener, downloadItemListener);
                break;
            case TIPO_RECURSO:
                RecursosUi recursosUi = (RecursosUi) itemObjectList.get(position);
                RecursosHolder vh2 = (RecursosHolder) holder;
                vh2.bind(recursosUi, downloadItemListener);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        Object items = itemObjectList.get(position);
        if (items instanceof ActividadesUi) {
            return TIPO_ACTIVIDAD;
        } else if (items instanceof RecursosUi) {
            return TIPO_RECURSO;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return itemObjectList.size();
    }


    public void setActividadList(List<Object> itemObjectList) {
        this.itemObjectList.clear();//Limpiamos la memoria (Objeto)
        this.itemObjectList.addAll(itemObjectList);//Agrega toda lista
        notifyDataSetChanged(); // Pinta otra ves la lista
    }

    public List<RecursosHolder> getListDownloadAdapter() {
        List<RecursosHolder> downloadAdapters = new ArrayList<>();
        try {

            for (int childCount = recyclerView.getChildCount(), i = 0; i < childCount; ++i) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if (holder instanceof RecursosHolder) {
                    downloadAdapters.add((RecursosHolder) holder);
                } else if (holder instanceof ActividadesHolder) {
                    ActividadesHolder actividadesHolder = (ActividadesHolder) holder;
                    RecyclerView recyclerView2 = actividadesHolder.getRvActRecursos();
                    for (int childCount2 = recyclerView2.getChildCount(), j = 0; j < childCount2; ++j) {
                        RecyclerView.ViewHolder holder2 = recyclerView2.getChildViewHolder(recyclerView2.getChildAt(j));
                        if (holder2 instanceof RecursosHolder) {
                            downloadAdapters.add((RecursosHolder) holder2);
                        } else if (holder2 instanceof SubRecursosHolder) {
                            SubRecursosHolder subRecursosHolder = (SubRecursosHolder) holder2;
                            RecyclerView recyclerView3 = subRecursosHolder.getRecyclerView();
                            for (int childCount3 = recyclerView3.getChildCount(), h = 0; h < childCount3; ++h) {
                                RecyclerView.ViewHolder holder3 = recyclerView3.getChildViewHolder(recyclerView3.getChildAt(h));
                                if (holder3 instanceof RecursosHolder) {
                                    downloadAdapters.add((RecursosHolder) holder3);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downloadAdapters;
    }

    public void update(RecursosUi repositorioFileUi) {
        for (RecursosHolder downloadAdapter : getListDownloadAdapter()) {
            if (repositorioFileUi.equals(downloadAdapter.getRepositorioFileUi())) {
                downloadAdapter.bind(repositorioFileUi, downloadItemListener);
            }
        }
    }

    public void updateProgress(RecursosUi repositorioFileUi, int count) {
        for (RecursosHolder downloadAdapter : getListDownloadAdapter()) {
            if (repositorioFileUi.equals(downloadAdapter.getRepositorioFileUi())) {
                downloadAdapter.count(count);
            }
        }
    }

    public void updateList(ActividadesUi actividadesUi) {
        int postion =  itemObjectList.indexOf(actividadesUi);
        if(postion!=-1){
            itemObjectList.set(postion, actividadesUi);
            notifyItemChanged(postion);
        }
    }
}
