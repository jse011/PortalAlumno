package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters.viewholders.RecursosViewHolder;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;

import java.util.List;

/**
 * Created by irvinmarin on 05/12/2017.
 */

public class RecursosTareaAdapter extends RecyclerView.Adapter<RecursosViewHolder> {

//    private TareasUIListener tareasUIListener;
    private int tipoVista;

    private List<RecursosUI> recursosUIList;
    private RecursoRemoveListener recursoRemoveListener;
    private TareasUI tareasUI;

    public RecursosTareaAdapter(List<RecursosUI> recursosUIList,
//                                TareasUIListener tareasUIListener,
                                TareasUI tareasUI,
                                int tipoVista,
                                RecursoRemoveListener recursoRemoveListener) {

        this.recursosUIList = recursosUIList;
        this.tareasUI = tareasUI;
        this.tipoVista = tipoVista;
        this.recursoRemoveListener = recursoRemoveListener;


    }

    public interface RecursoRemoveListener {
        void onbtnDeleteRecursoDataBase(TareasUI tareasUI, RecursosUI recursosUI);

        void onbtnDeleteRecursoFromList(RecursosUI recursosUI);
    }

    @Override
    public RecursosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recursos_mvp, parent, false);

        return new RecursosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecursosViewHolder holder, int position) {
        holder.bind(recursosUIList.get(position), tareasUI, tipoVista, recursoRemoveListener);
    }

    @Override
    public int getItemCount() {
        return recursosUIList.size();
    }

    public void setRecursos(List<RecursosUI> recursosUIList) {
        this.recursosUIList.clear();
        this.recursosUIList.addAll(recursosUIList);
        notifyDataSetChanged();
    }

    public void deleteRecurso(RecursosUI recursosUI) {
        int position = recursosUIList.indexOf(recursosUI);
        if (position != -1) {
            recursosUIList.remove(recursosUI);
            notifyItemRemoved(position);
        }
    }
}
