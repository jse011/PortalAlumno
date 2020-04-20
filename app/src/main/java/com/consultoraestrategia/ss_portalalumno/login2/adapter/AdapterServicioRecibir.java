package com.consultoraestrategia.ss_portalalumno.login2.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.login2.entities.ActualizarUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsPortalAlumno;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterServicioRecibir extends RecyclerView.Adapter<AdapterServicioRecibir.ViewHolder> {

    private List<ActualizarUi> actualizarUiList = new ArrayList<>();
    private ActualizarListener listener;

    public AdapterServicioRecibir(ActualizarListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services_recibir, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ActualizarUi serviceEnvioUi = actualizarUiList.get(i);
        viewHolder.bind(serviceEnvioUi, listener);
    }

    @Override
    public int getItemCount() {
        return actualizarUiList.size();
    }

    public void setList(List<ActualizarUi> actualizarUiList) {
        this.actualizarUiList.clear();
        this.actualizarUiList.addAll(actualizarUiList);
        notifyDataSetChanged();
    }

    public void updateItem(ActualizarUi actualizarUi) {
        int position = actualizarUiList.indexOf(actualizarUi);
        if(position!=-1){
            actualizarUiList.set(position, actualizarUi);
            notifyItemChanged(position);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ValueAnimator animationBtnRevisionDatos;
        @BindView(R.id.img_service_recivir)
        ImageView imgServiceRecivir;
        @BindView(R.id.txt_titulo_rescibir)
        TextView txtTituloRescibir;
        @BindView(R.id.txt_descripcion_rescibir)
        TextView txtDescripcionRescibir;
        @BindView(R.id.img_start_rescibir)
        ImageView imgStartRescibir;
        @BindView(R.id.prog_dowload)
        ProgressBar progDowload;
        @BindView(R.id.prog_upload)
        ProgressBar progUpload;
        @BindView(R.id.txt_upload)
        TextView txtUpload;
        @BindView(R.id.txt_dowload)
        TextView txtDowload;
        @BindView(R.id.cont_progress)
        ConstraintLayout contprogress;
        private ActualizarListener listener;
        private ActualizarUi actualizarUi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgStartRescibir.setOnClickListener(this);
            animationBtnRevisionDatos = ObjectAnimator.ofFloat(imgStartRescibir, "rotation", 360f, 0.0f);
            animationBtnRevisionDatos.setDuration(2500);
            animationBtnRevisionDatos.setRepeatCount(ObjectAnimator.INFINITE);
            //animation.setRepeatMode(ObjectAnimator.RESTART);
            animationBtnRevisionDatos.setInterpolator(new AccelerateDecelerateInterpolator());
        }

        public void bind(ActualizarUi actualizarUi, ActualizarListener actualizarListener) {
            this.actualizarUi = actualizarUi;
            this.listener = actualizarListener;
            int recurso;
            switch (actualizarUi.getTipo()){
                case Casos:
                    recurso = R.drawable.ic_datos_carga_academica;
                    break;
                case Grupos:
                    recurso = R.drawable.ic_datos_grupo;
                    break;
                case Rubros:
                    recurso = R.drawable.ic_datos_evaluacion;
                    break;
                case Tareas:
                    recurso = R.drawable.ic_datos_silabo;
                    break;
                case Estudiantes:
                    recurso = R.drawable.ic_datos_horario;
                    break;
                case Docente:
                    recurso = R.drawable.ic_datos_grupo;
                    break;
                case Unidades:
                    recurso = R.drawable.ic_datos_silabo;
                    break;
                case Resultado:
                    recurso = R.drawable.ic_datos_horario;
                    break;
                case Asistencias:
                    recurso = R.drawable.ic_datos_asistencia;
                    break;
                case TipoNota:
                    recurso = R.drawable.ic_datos_tipo_nota;
                    break;
                default:
                    recurso = R.drawable.ic_datos_evaluacion;
                    break;
            }

            Glide.with(imgServiceRecivir)
                    .load(recurso)
                    .into(imgServiceRecivir);

            txtTituloRescibir.setText(actualizarUi.getNombre());

            if(actualizarUi.isEncoloa()){
                if(!animationBtnRevisionDatos.isStarted()) animationBtnRevisionDatos.start();
            }else {
                animationBtnRevisionDatos.end();
            }

            if(actualizarUi.getUploadProgress()!=0||actualizarUi.getDowloadProgress()!=0){

                if(actualizarUi.getDowloadProgress()==100){
                    if(contprogress.getVisibility()== View.VISIBLE){
                        contprogress.setVisibility(View.GONE);
                    }
                }else {
                    if(contprogress.getVisibility()== View.GONE){
                        contprogress.setVisibility(View.VISIBLE);
                    }
                }

            }else {
                if(contprogress.getVisibility()== View.VISIBLE){
                    contprogress.setVisibility(View.GONE);
                }
            }

            switch (actualizarUi.getSuccess()){
                case 1:
                    txtDescripcionRescibir.setText("Se actualizó correctamente");
                    break;
                case -1:
                    txtDescripcionRescibir.setText("Ocurrió un error inesperado");
                    break;
                default:
                    String tiempo = "";
                    if(actualizarUi.getFecha()==0){
                        tiempo = "Pendiente";
                    }else{
                        tiempo = UtilsPortalAlumno.tiempoCreacion(actualizarUi.getFecha() );
                    }
                    txtDescripcionRescibir.setText(tiempo );
                    break;
            }
            progDowload.setProgress(actualizarUi.getDowloadProgress());
            progUpload.setProgress(actualizarUi.getUploadProgress());
            String progressDowload = actualizarUi.getDowloadProgress() + "%";
            String progressUpload = actualizarUi.getUploadProgress() + "%";
            txtDowload.setText(progressDowload);
            txtUpload.setText(progressUpload);

        }

        @Override
        public void onClick(View v) {
            listener.onClickActualizarItem(actualizarUi);
        }
    }


    public interface ActualizarListener {
        void onClickActualizarItem(ActualizarUi actualizarUi);
    }
}
