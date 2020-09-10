package com.consultoraestrategia.ss_portalalumno.actividades.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.RecursosHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.adapter.holder.SubRecursosHolder;
import com.consultoraestrategia.ss_portalalumno.actividades.adapterDownload.adapter.DownloadItemListener;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.RecursosUi;
import com.consultoraestrategia.ss_portalalumno.actividades.entidades.SubRecursosUi;

import java.util.List;

/**
 * Created by kike on 07/02/2018.
 */

public class RecursosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int RECURSO_ACTIVIDAD = 0, RECURSO_HIJO = 1;
    private List<Object> objectList;
    private int positionActividad;
    private DownloadItemListener downloadItemListener;

    public RecursosAdapter(List<Object> objectList, int positionActividad, DownloadItemListener downloadItemListener) {
        this.objectList = objectList;
        this.positionActividad = positionActividad;
        this.downloadItemListener = downloadItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case RECURSO_ACTIVIDAD:
                View view1 = layoutInflater.inflate(R.layout.item_download_repositorio_actividad, viewGroup, false);
                viewHolder = new RecursosHolder(view1);
                break;
            case RECURSO_HIJO:
                View view2 = layoutInflater.inflate(R.layout.layout_item_sub_recursos, viewGroup, false);
                viewHolder = new SubRecursosHolder(view2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RECURSO_ACTIVIDAD:
                RecursosUi recursosUi = (RecursosUi) objectList.get(position);
                RecursosHolder vh1 = (RecursosHolder) holder;
                vh1.bind(recursosUi, downloadItemListener);
                break;
            case RECURSO_HIJO:
                SubRecursosUi subRecursosUi = (SubRecursosUi) objectList.get(position);
                SubRecursosHolder vh2 = (SubRecursosHolder) holder;
                vh2.bind(subRecursosUi, positionActividad, downloadItemListener);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (objectList == null) return 0;
        return objectList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList == null) return -1;
        Object items = objectList.get(position);
        if (items instanceof RecursosUi) {
            return RECURSO_ACTIVIDAD;
        } else if (items instanceof SubRecursosUi) {
            return RECURSO_HIJO;
        }
        return -1;
    }
}
