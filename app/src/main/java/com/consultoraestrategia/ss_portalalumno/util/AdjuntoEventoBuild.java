package com.consultoraestrategia.ss_portalalumno.util;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.adapter.EventoAdapter;
import com.consultoraestrategia.ss_portalalumno.main.entities.EventoUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdjuntoEventoBuild {


    public static class AdjuntoEventoAdapterMore extends RecyclerView.Adapter<AdjuntoEventoBuild.AdjuntoEventoAdapterMore.ViewHolder> {

        private List<EventoUi.AdjuntoUi> adjuntoUiList;
        private EventoAdapter.Listener listener;
        private EventoUi eventoUi;
        public AdjuntoEventoAdapterMore(List<EventoUi.AdjuntoUi> adjuntoUiList, EventoUi eventoUi, EventoAdapter.Listener listener) {
            this.adjuntoUiList = adjuntoUiList;
            this.eventoUi = eventoUi;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colegio_adjunto_more_clean, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bindMore(position==3&&adjuntoUiList.size()>4,adjuntoUiList.size()-4, position==3||adjuntoUiList.size()-1==position,adjuntoUiList.get(position),eventoUi, listener);
        }

        @Override
        public int getItemCount() {
            return Math.min(adjuntoUiList.size(), 4);
        }

        static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.ic_icono)
            ImageView icIcono;
            @BindView(R.id.txt_documento)
            TextView txtDocumento;
            @BindView(R.id.btn_download)
            View btnDownload;
            @BindView(R.id.more_preview)
            View morePreview;
            @BindView(R.id.more_preview_count)
            TextView morePreviewCount;
            @BindView(R.id.linea)
            View linea;
            @BindView(R.id.linea1)
            View linea1;


            EventoAdapter.Listener listener;
            EventoUi.AdjuntoUi adjuntoUi;
            boolean more;
            private EventoUi eventoUi;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindMore(boolean more, int countMore, boolean ultimoItem, EventoUi.AdjuntoUi adjuntoUi, EventoUi eventoUi, EventoAdapter.Listener listener) {
                this.listener = listener;
                this.adjuntoUi = adjuntoUi;
                this.eventoUi = eventoUi;
                this.more = more;
                init();
                linea1.setVisibility(View.GONE);
                if(more){
                    morePreview.setVisibility(View.VISIBLE);
                    String textCount = "+"+countMore;
                    morePreviewCount.setText(textCount);
                }else {
                    morePreview.setVisibility(View.GONE);
                }

                linea.setVisibility(ultimoItem?View.GONE:View.VISIBLE);

            }

            private void init(){
                Drawable drawable;
                switch (adjuntoUi.getTipoArchivo()){
                    case PDF:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_pdf_ico);
                        break;
                    case PRESENTACION:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_ppt_ico);
                        break;
                    case HOJACALCULO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_xls_ico);
                        break;
                    case DOCUMENTO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_doc_ico);
                        break;
                    case AUDIO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_aud_ico);
                        break;
                    case OTROS:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_not_av_ico);
                        break;
                    default:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_not_av_ico);
                        break;
                }

                icIcono.setImageDrawable(drawable);
                txtDocumento.setText(adjuntoUi.getTitulo());
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                ViewHolder.this.listener.onClickAdjunto(eventoUi,adjuntoUi, more);
            }
        }

    }

    public static class AdjuntoEventoAdapter extends RecyclerView.Adapter<AdjuntoEventoBuild.AdjuntoEventoAdapter.ViewHolder> {

        private List<EventoUi.AdjuntoUi> adjuntoUiList;
        private Listener listener;

        public AdjuntoEventoAdapter(List<EventoUi.AdjuntoUi> adjuntoUiList, Listener listener) {
            this.adjuntoUiList = adjuntoUiList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colegio_adjunto_clean, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(adjuntoUiList.get(position), listener);
        }

        @Override
        public int getItemCount() {
            return adjuntoUiList.size();
        }

        public void setList(List<EventoUi.AdjuntoUi> adjuntoUiList) {
            if(adjuntoUiList==null)adjuntoUiList=new ArrayList<>();
            this.adjuntoUiList.clear();
            this.adjuntoUiList.addAll(adjuntoUiList);
            notifyDataSetChanged();
        }

        static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            @BindView(R.id.ic_icono)
            ImageView icIcono;
            @BindView(R.id.txt_documento)
            TextView txtDocumento;
            @BindView(R.id.btn_download)
            View btnDownload;
            @BindView(R.id.more_preview)
            View morePreview;
            @BindView(R.id.more_preview_count)
            TextView morePreviewCount;
            @BindView(R.id.linea)
            View linea;
            @BindView(R.id.linea1)
            View linea1;

            EventoUi.AdjuntoUi adjuntoUi;
            private Listener listener;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            private void init(){
                Drawable drawable;
                switch (adjuntoUi.getTipoArchivo()){
                    case PDF:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_pdf_ico);
                        break;
                    case PRESENTACION:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_ppt_ico);
                        break;
                    case HOJACALCULO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_xls_ico);
                        break;
                    case DOCUMENTO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_doc_ico);
                        break;
                    case AUDIO:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_aud_ico);
                        break;
                    case OTROS:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_not_av_ico);
                        break;
                    default:
                        drawable = ContextCompat.getDrawable(icIcono.getContext(), R.drawable.ext_not_av_ico);
                        break;
                }

                icIcono.setImageDrawable(drawable);
                txtDocumento.setText(adjuntoUi.getTitulo());
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                listener.onClickAdjunto(adjuntoUi);
            }

            public void bind(EventoUi.AdjuntoUi adjuntoUi, Listener listener) {
                this.listener = listener;
                this.adjuntoUi = adjuntoUi;
                init();
                linea1.setVisibility(View.VISIBLE);
                linea.setVisibility(View.GONE);
            }
        }

        public interface Listener {
            void onClickAdjunto(EventoUi.AdjuntoUi adjuntoUi);
        }
    }


}
