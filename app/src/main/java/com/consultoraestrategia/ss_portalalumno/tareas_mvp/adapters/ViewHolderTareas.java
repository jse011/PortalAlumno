package com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.adapterDownload.adapter.DownloadAdapter;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.HeaderTareasAprendizajeUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.ParametroDisenioUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RecursosUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.RepositorioFileUi;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.entities.TareasUI;
import com.consultoraestrategia.ss_portalalumno.tareas_mvp.listeners.TareasUIListener;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ViewHolderTareas extends SectioningAdapter.ItemViewHolder implements  View.OnClickListener{


    private static final String TAG = ViewHolderTareas.class.getSimpleName();

    //    @BindView(R.id.txtNroTarea)
//    TextView txtNroTarea;
    @BindView(R.id.txtEstadoTarea)
    TextView txtEstadoTarea;
    @BindView(R.id.txtFechaEntrege)
    TextView txtFechaEntrega;
    @BindView(R.id.txtTitulo)
    TextView txtTituloTarea;
    @BindView(R.id.txtContenidoTarea)
    TextView txtContenidoTarea;
    @BindView(R.id.btnAbrir)
    Button btnAbrir;
    @BindView(R.id.txtCreador)
    TextView txtNombreCreador;
    @BindView(R.id.txtFechaCreacion)
    TextView txtFechaCreacion;
    @BindView(R.id.rvRecurso)
    RecyclerView rvRecurso;
    @BindView(R.id.spinne)
    ImageView spinne;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.cantNoEvaluados)
    TextView cantidadNoEvaluados;
    @BindView(R.id.cantEvaluados)
    TextView cantidadEvaluados;
    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;
    @BindView(R.id.cantidad)
    ConstraintLayout constraintLayoutCantidad;
    @BindView(R.id.txtNroTarea)
    ImageView txtTarea;
    @BindView(R.id.constraintLayoutContenido)
    ConstraintLayout constraintLayoutContenido;
    @BindView(R.id.contentVacio)
    ConstraintLayout contentVacio;


    private TareasUIListener listener;
    private TareasUI tareasUI;
    private boolean status = true;
    private List<RecursosUI> recursosUIList;
    private DownloadAdapter recursosTareaAdapter;
    private ParametroDisenioUi parametroDisenioUi;

    public ViewHolderTareas(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        //txtEstadoTarea.setOnClickListener(this);
        //constraintLayout.setOnClickListener(this);
        //constraintLayoutContenido.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    public void bind(final TareasUI tareasUI,
                     final TareasUIListener listener,
                     final int position,
                     final HeaderTareasAprendizajeUI headerTareasAprendizajeUI,
                     ParametroDisenioUi parametroDisenioUi,
                     final boolean state) {
        this.listener = listener;
        this.tareasUI = tareasUI;
        this.parametroDisenioUi = parametroDisenioUi;
        if (tareasUI != null) {
//            if (tareasUI.isDocente()) {
//                spinne.setVisibility(View.VISIBLE);
//                txtEstadoTarea.setVisibility(View.VISIBLE);
//            } else {
//                spinne.setVisibility(View.GONE);
//                txtEstadoTarea.setVisibility(View.GONE);
//            }

            if(tareasUI.getRecursosUIList().size()>0){
                contentVacio.setVisibility(View.GONE);
                rvRecurso.setVisibility(View.VISIBLE);
            }else{
                contentVacio.setVisibility(View.VISIBLE);
                rvRecurso.setVisibility(View.GONE);
            }


            rvRecurso.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recursosTareaAdapter = new DownloadAdapter(listener, rvRecurso);
            rvRecurso.setAdapter(recursosTareaAdapter);
            rvRecurso.setHasFixedSize(false);
            rvRecurso.setNestedScrollingEnabled(false);
            this.recursosUIList = tareasUI.getRecursosUIList();
            if (tareasUI.getRecursosUIList() != null)recursosTareaAdapter.setList(new ArrayList<RepositorioFileUi>(tareasUI.getRecursosUIList()));
            txtTituloTarea.setText(tareasUI.getTituloTarea());
            txtTituloTarea.setTextColor(Color.parseColor("#000000"));

            if (!(tareasUI.getFechaLimite() == 0D)) {
                String hora = UtilsPortalAlumno.changeTime12Hour(tareasUI.getHoraEntrega());
                String fecha_entrega = UtilsPortalAlumno.f_fecha_letras(tareasUI.getFechaLimite());
                if(!TextUtils.isEmpty(hora)){
                    fecha_entrega = fecha_entrega + " " + hora;
                }
                txtFechaEntrega.setText(fecha_entrega);
            } else {
                txtFechaEntrega.setText("Sin límite de entrega");
            }

            String estado = tareasUI.getEstado().getNombre();
            txtEstadoTarea.setText(estado.toUpperCase());
            if (tareasUI.getEstado() == TareasUI.Estado.Publicado) {
                try {
                    txtEstadoTarea.setTextColor(Color.parseColor(parametroDisenioUi.getColor1()));
                }catch (Exception e){
                    e.printStackTrace();
                    txtEstadoTarea.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_light_blue_600));
                }

            }else {
                txtEstadoTarea.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_grey_600));
            }
            txtNombreCreador.setText("Tarea " + position);
            txtFechaCreacion.setText(UtilsPortalAlumno.f_fecha_letras(tareasUI.getFechaCreacionTarea()));

            if (!TextUtils.isEmpty(tareasUI.getDescripcion()))txtContenidoTarea.setText(tareasUI.getDescripcion());
            else txtContenidoTarea.setText("No existe descripcion de Tarea");

            if(tareasUI.getRubroEvalProcesoUi()==null){
                Log.d("tareasVacias", "vacio");
                constraintLayoutCantidad.setVisibility(View.GONE);
                //constraintLayoutContenido.setOnClickListener(null);
                txtTarea.setImageResource(R.drawable.report);
                if (state) txtTarea.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_teal_800));
                    else try {
                txtTarea.setBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));
                }catch (Exception e){e.getStackTrace();}


            }else {
                Log.d("tareasVacias", tareasUI.getRubroEvalProcesoUi().toString());
                constraintLayoutCantidad.setVisibility(View.VISIBLE);
                String string = tareasUI.getRubroEvalProcesoUi().getCantidadEvaluados();
                String[] parts = string.split("/");
                String part1 = parts[0];
                String part2 = parts[1];
                int cantidadEvaluadoss = Integer.parseInt(part1);
                int cantidadNoEval = Integer.parseInt(part2);
                //constraintLayoutContenido.setOnClickListener(this);
                cantidadEvaluados.setText(String.valueOf(cantidadEvaluadoss));
                cantidadNoEvaluados.setText(String.valueOf(cantidadNoEval));
                txtTarea.setImageResource(R.drawable.ic_icon_matrix_white);
                if (state) txtTarea.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),R.color.md_teal_800));
                else try {
                    txtTarea.setBackgroundColor(Color.parseColor(parametroDisenioUi.getColor1()));
                }catch (Exception e){e.getStackTrace();}

            }

            spinne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            itemView.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        listener.onClicTarea(tareasUI);
        /*
        switch (view.getId()) {
            case R.id.txtEstadoTarea:
                Log.d(TAG,"estado: " + tareasUI.getEstado());
                if(tareasUI.isCalendarioVigente()){
                    Log.d(TAG,"estado: " + tareasUI.getEstado());
                    String estado = "";
                    switch (tareasUI.getEstado()){
                        case Creado:
                            estado = TareasUI.Estado.Publicado.getNombre().toUpperCase();
                            txtEstadoTarea.setText(estado.toUpperCase());
                            try {
                                txtEstadoTarea.setTextColor(Color.parseColor(parametroDisenioUi.getColor1()));
                            }catch (Exception e){
                                e.printStackTrace();
                                txtEstadoTarea.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_light_blue_600));
                            }
                            break;
                        case Publicado:
                            estado =  TareasUI.Estado.Creado.getNombre().toUpperCase();
                            txtEstadoTarea.setText(estado.toUpperCase());
                            txtEstadoTarea.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_grey_600));
                            break;
                         default:
                             txtEstadoTarea.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_grey_600));
                             break;
                    }
                    listener.onClikEstado(tareasUI);
                }
                break;
            case R.id.constraintLayout:
                //verMas();
                break;
            case R.id.constraintLayoutContenido:
                listener.onClikRubroTarea(tareasUI);
                break;
            default:

                break;
        }*/
    }

    private void verMas() {
        if(status){
            status = false;
            constraintLayoutContenido.setVisibility(View.GONE);
        }else {
            status = true;
            constraintLayoutContenido.setVisibility(View.VISIBLE);
        }
    }

    public List<RecursosUI> getRecursosUIList() {
        return recursosUIList;
    }

    public DownloadAdapter getRecursosTareaAdapter() {
        return recursosTareaAdapter;
    }
}
