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
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.UnidadAprendizajeListener;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

public class UnidadesAdapter  extends SectioningAdapter {
    private static final String TAG= UnidadesAdapter.class.getSimpleName();
    private final RecyclerView recyclerView;
    private List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList;
    private RecyclerView rvSesionesTareas;
    private UnidadAprendizajeListener listener;
    private int cantidadItems;
    TareasUIListener tareasUIListener;
    int mIdCurso;
    private ParametroDisenioUi parametroDisenioUi;

    public UnidadesAdapter(RecyclerView recyclerView, List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList,
                           TareasUIListener tareasUIListener,
                           UnidadAprendizajeListener listener,
                           RecyclerView rvSesionesTareas,
                           int cantidadItems
    ) {
        this.recyclerView = recyclerView;
        this.headerTareasAprendizajeUIList = headerTareasAprendizajeUIList;
        this.rvSesionesTareas = rvSesionesTareas;
        this.listener = listener;
        this.cantidadItems = cantidadItems;
        this.tareasUIListener = tareasUIListener;
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerUserType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_header_unidades, parent, false);
        return new ViewHolderUnidades(v);
    }

    @Override
    public GhostHeaderViewHolder onCreateGhostHeaderViewHolder(ViewGroup parent) {
        final View ghostView = new View(parent.getContext());
        ghostView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new GhostHeaderViewHolder(ghostView);
    }


    public interface RecursoRemoveListener {
        void onbtnDeleteRecursoDataBase(TareasUI tareasUI, RecursosUI recursosUI);

        void onbtnDeleteRecursoFromList(RecursosUI recursosUI);
    }

    RecursoRemoveListener recursoRemoveListener;

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        Log.d(TAG,"onBindItemViewHolder"+sectionIndex+itemIndex);
        HeaderTareasAprendizajeUI headerTareasAprendizajeUI = headerTareasAprendizajeUIList.get(sectionIndex);
        TareasUI tareasUI = headerTareasAprendizajeUI.getTareasUIList().get(itemIndex);
        int total = headerTareasAprendizajeUI.getTareasUIList().size();
        ((ViewHolderTareas) viewHolder).bind(tareasUI, tareasUIListener, total - itemIndex, headerTareasAprendizajeUI, parametroDisenioUi, false);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemUserType) {
        ItemViewHolder holder;
        View viewTareas = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tareas_unidades, parent, false);
        holder = new ViewHolderTareas(viewTareas);
        return holder;
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        Log.d(TAG, "onBindHeaderViewHolder"+headerTareasAprendizajeUIList.size());
        HeaderTareasAprendizajeUI headerTareasAprendizajeUI = headerTareasAprendizajeUIList.get(sectionIndex);
        ViewHolderUnidades hvh = (ViewHolderUnidades) viewHolder;
        hvh.bind(headerTareasAprendizajeUI, tareasUIListener, listener, 0, mIdCurso, parametroDisenioUi, true);
    }

    @Override
    public int getNumberOfSections() {
        return headerTareasAprendizajeUIList.size();
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return headerTareasAprendizajeUIList.get(sectionIndex).getTareasUIList().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public int getSectionItemUserType(int sectionIndex, int itemIndex) {
        return headerTareasAprendizajeUIList.get(sectionIndex).getTareasUIList().size();
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


    public void setTareasUIList(List<HeaderTareasAprendizajeUI> headerTareasAprendizajeUIList, ParametroDisenioUi parametroDisenioUi) {
        Log.d(TAG, "setTareasUIList"+headerTareasAprendizajeUIList.size());
        this.headerTareasAprendizajeUIList.clear();
        this.headerTareasAprendizajeUIList.addAll(headerTareasAprendizajeUIList);
        this.parametroDisenioUi = parametroDisenioUi;
        notifyAllSectionsDataSetChanged();
    }



    public void setRecyclerView(RecyclerView recyclerView) {
        this.rvSesionesTareas = recyclerView;
    }

    public void setmIdCurso(int mIdCurso) {
        this.mIdCurso = mIdCurso;
    }

}
