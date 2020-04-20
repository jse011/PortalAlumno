package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.UnidadesAdapter;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.EstadoSesiones;
import com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.entities.SesionAprendizajeUi;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SesionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.cant_Recursos)
    TextView cantRecursos;
    @BindView(R.id.fondo)
    TextView fondo;
    @BindView(R.id.txt_tiempo)
    TextView txttiempo;
    @BindView(R.id.txt_tiempo_medida)
    TextView txttiempo_medida;
    @BindView(R.id.lin_titulo_sesion)
    View lin_titulosesion;
    @BindView(R.id.txt_titulo_sesion)
    TextView txt_titulosesion;
    @BindView(R.id.txt_num_sesion)
    TextView txt_numsesion;
    @BindView(R.id.txt_fecha_sesion)
    TextView txt_fechasesion;
    @BindView(R.id.cardv_sesiones)
    CardView cardvSesiones;
    @BindView(R.id.contItemView)
    ConstraintLayout contItemView;
    private SesionAprendizajeUi sesionAprendizaje;
    private EstadoSesiones estadoSesiones=EstadoSesiones.CREADO;
    private UnidadesAdapter.UnidadListener listener;


    // Cada uno de los elementos de mi vista
    public SesionHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }

    public void bind(SesionAprendizajeUi sesionAprendizaje, UnidadesAdapter.UnidadListener listener){
        itemView.setOnClickListener(this);
        this.sesionAprendizaje = sesionAprendizaje;
        this.listener = listener;
        //region Cambiar el Color
        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(itemView.getContext(), R.drawable.background_border_bottom_cian);
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.gradientDrawble);
        gradientDrawable.setColor(ContextCompat.getColor(itemView.getContext(), R.color.md_blue_700));

        lin_titulosesion.setBackground(layerDrawable);

        txttiempo.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_blue_700));
        txttiempo_medida.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_blue_700));
        //endregion

        txt_numsesion.setText(sesionAprendizaje.getNroSesion()+"");
        txt_titulosesion.setText(sesionAprendizaje.getTitulo());
        txttiempo.setText(sesionAprendizaje.getHoras()+"");
        txttiempo_medida.setText("min.");

        //region Ocultar si es menor de cero
        if(sesionAprendizaje.getCantidad_recursos() <= 0){
            cantRecursos.setVisibility(View.GONE);
        }else{
            cantRecursos.setVisibility(View.VISIBLE);
        }
        //endregion

        Calendar fechaEjecucion = Calendar.getInstance();
        fechaEjecucion.setTimeInMillis(sesionAprendizaje.getFechaEjecucion());
        txt_fechasesion.setText(f_fecha_letras(fechaEjecucion));
        cantRecursos.setText(sesionAprendizaje.getCantidad_recursos()+"");
        cambiarTagColor();
        itemView.setOnClickListener(this);
    }

    public String f_fecha_letras(Calendar timesTamp) {
        String mstr_fecha = "";
        String[] vobj_days = {"Dom", "Lun", "Mart", "Mié", "Jue", "Vie", "Sáb"};
        String[] vobj_Meses = {"Ene.", "Feb.", "Mar.", "Abr.", "May.", "Jun.", "Jul.", "Ago.", "Sept.", "Oct.", "Nov.", "Dic."};
        int year = timesTamp.get(Calendar.YEAR);
        int month = timesTamp.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = timesTamp.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = timesTamp.get(Calendar.DAY_OF_WEEK);
        mstr_fecha = vobj_days[dayOfWeek - 1] + " " + dayOfMonth + " de " + vobj_Meses[month];
        return mstr_fecha;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            default:
                listener.onClickSesionAprendizaje(sesionAprendizaje);
                break;
        }
    }

    private void cambiarTagColor(){
        String nombreEstadoSession = "";
        Drawable drawable = null;
        Drawable drawables = null;
        @ColorInt
        int colorEstado = 0;
        switch (estadoSesiones){
            case HECHO:
                nombreEstadoSession = "Hecho";//Verde
                colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_green_600);
                drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_red);
                drawables = ContextCompat.getDrawable(itemView.getContext(), R.drawable.border_session_verde);
                //fondo.setBackgroundColor(R.drawable.border_session_verde);
                break;
            case CREADO:
                nombreEstadoSession = "Creado";//Azul
                colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_blue_600);
                drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_blue);
                drawables = ContextCompat.getDrawable(itemView.getContext(), R.drawable.border_session_azul);
                break;
            case PENDIENTE:
                nombreEstadoSession = "Pendiente";//Rojo
                colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_red_500);
                drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_red_600);
                drawables = ContextCompat.getDrawable(itemView.getContext(), R.drawable.border_session_rojo);
                break;
            case PROGRAMADO:
                nombreEstadoSession = "Programado";//Magenta
                colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_orange_600);
                drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_orange);
                drawables = ContextCompat.getDrawable(itemView.getContext(), R.drawable.border_session_magenta);
                break;
        }
        //corner_bg_orange
        lin_titulosesion.setBackgroundColor(colorEstado);
        cardvSesiones.setCardBackgroundColor(colorEstado);
        txttiempo_medida.setTextColor(colorEstado);
        txttiempo.setTextColor(colorEstado);
        Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_circle_unidades);
        circle.mutate().setColorFilter(colorEstado, PorterDuff.Mode.SRC_ATOP);
        cantRecursos.setBackground(circle);

    }

}