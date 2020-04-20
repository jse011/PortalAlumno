package com.consultoraestrategia.ss_portalalumno.login2.adapter;

import android.animation.ObjectAnimator;
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
import com.consultoraestrategia.ss_portalalumno.login2.entities.ServiceEnvioUi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterServicioEnvio extends RecyclerView.Adapter<AdapterServicioEnvio.ViewHolder> {

    private List<ServiceEnvioUi> serviceEnvioUiList = new ArrayList<>();
    private  EnviarListener listener;

    public AdapterServicioEnvio(EnviarListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services_enviar, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ServiceEnvioUi serviceEnvioUi = serviceEnvioUiList.get(i);
        viewHolder.bind(serviceEnvioUi, listener);
    }

    @Override
    public int getItemCount() {
        return serviceEnvioUiList.size();
    }

    public void setList(List<ServiceEnvioUi> serviceEnvioUiList) {
        this.serviceEnvioUiList.clear();
        this.serviceEnvioUiList.addAll(serviceEnvioUiList);
        notifyDataSetChanged();
    }

    public void updateItem(ServiceEnvioUi serviceEnvioUi) {
        int position = serviceEnvioUiList.indexOf(serviceEnvioUi);
        if(position!=-1){
            serviceEnvioUiList.set(position, serviceEnvioUi);
            notifyItemChanged(position);
        }
    }

    public void removeItem(ServiceEnvioUi serviceEnvioUi) {
        int position = serviceEnvioUiList.indexOf(serviceEnvioUi);
        if(position!=-1){
            serviceEnvioUiList.remove(position);
            notifyDataSetChanged();
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ObjectAnimator animationBtnRevisionDatos;
        @BindView(R.id.img_service_envio)
        ImageView imgServiceEnvio;
        @BindView(R.id.txt_titulo_service_envio)
        TextView txtTituloServiceEnvio;
        @BindView(R.id.txt_descripcion_service_envio)
        TextView txtDescripcionServiceEnvio;
        @BindView(R.id.img_start_service_envio)
        ImageView imgStartServiceEnvio;
        @BindView(R.id.cont_progress)
        ConstraintLayout contProgress;
        @BindView(R.id.prog_dowload)
        ProgressBar progDowload;
        @BindView(R.id.prog_upload)
        ProgressBar progUpload;
        @BindView(R.id.txt_upload)
        TextView txtUpload;
        @BindView(R.id.txt_dowload)
        TextView txtDowload;
        private  EnviarListener listener;
        private ServiceEnvioUi serviceEnvioUi;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgStartServiceEnvio.setOnClickListener(this);
            animationBtnRevisionDatos = ObjectAnimator.ofFloat(imgStartServiceEnvio, "rotation", 360f, 0.0f);
            animationBtnRevisionDatos.setDuration(2500);
            animationBtnRevisionDatos.setRepeatCount(ObjectAnimator.INFINITE);
            //animation.setRepeatMode(ObjectAnimator.RESTART);
            animationBtnRevisionDatos.setInterpolator(new AccelerateDecelerateInterpolator());
        }

        public void bind(ServiceEnvioUi serviceEnvioUi, EnviarListener listener) {
            this.listener = listener;
            this.serviceEnvioUi = serviceEnvioUi;
            int recurso;
            switch (serviceEnvioUi.getTipo()){
                case Casos:
                    recurso = R.drawable.ic_datos_carga_academica;
                    break;
                case Grupos:
                    recurso = R.drawable.ic_datos_grupo;
                    break;
                case Rubro:
                    recurso = R.drawable.ic_datos_evaluacion;
                    break;
                case Tareas:
                    recurso = R.drawable.ic_datos_silabo;
                    break;
                case Rubrica:
                    recurso = R.drawable.ic_datos_evaluacion;
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
                default:
                    recurso = R.drawable.ic_datos_evaluacion;
                    break;
            }
            Glide.with(imgServiceEnvio)
                    .load(recurso)
                    .into(imgServiceEnvio);

            txtTituloServiceEnvio.setText(serviceEnvioUi.getNombre());


            if(serviceEnvioUi.isEncoloa()){
                if(!animationBtnRevisionDatos.isStarted()) animationBtnRevisionDatos.start();
            }else {
                animationBtnRevisionDatos.end();
            }


            if(serviceEnvioUi.getUploadProgress()!=0||serviceEnvioUi.getDowloadProgress()!=0){

                if(serviceEnvioUi.getDowloadProgress()==100){
                    if(contProgress.getVisibility()== View.VISIBLE){
                        contProgress.setVisibility(View.GONE);
                    }
                }else {
                    if(contProgress.getVisibility()== View.GONE){
                        contProgress.setVisibility(View.VISIBLE);
                    }
                }

            }else {
                if(contProgress.getVisibility()== View.VISIBLE){
                    contProgress.setVisibility(View.GONE);
                }
            }

            switch (serviceEnvioUi.getSuccess()){
                case 1:
                    txtDescripcionServiceEnvio.setText("Se actualizó correctamente");
                    break;
                case -1:
                    txtDescripcionServiceEnvio.setText("Ocurrió un error inesperado");
                    break;
                default:
                    txtDescripcionServiceEnvio.setText(serviceEnvioUi.getDescripcion());
                    break;
            }
            progDowload.setProgress(serviceEnvioUi.getDowloadProgress());
            progUpload.setProgress(serviceEnvioUi.getUploadProgress());
            String progressDowload = serviceEnvioUi.getDowloadProgress() + "%";
            String progressUpload = serviceEnvioUi.getUploadProgress() + "%";
            txtDowload.setText(progressDowload);
            txtUpload.setText(progressUpload);
        }

        @Override
        public void onClick(View v) {
            listener.onClickEnviarItem(serviceEnvioUi);
        }
    }

    public interface EnviarListener {
        void onClickEnviarItem(ServiceEnvioUi serviceEnvioUi);
    }
}
