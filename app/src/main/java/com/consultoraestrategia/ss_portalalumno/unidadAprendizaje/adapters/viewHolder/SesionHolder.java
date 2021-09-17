package com.consultoraestrategia.ss_portalalumno.unidadAprendizaje.adapters.viewHolder;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.Rect;
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
    @BindView(R.id.txt_tag)
    TextView txtTag;

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
        txt_fechasesion.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.md_blue_700));
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
        cambiarTagColor(sesionAprendizaje);
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

    private void cambiarTagColor(SesionAprendizajeUi sesionAprendizajeUi){

        if(sesionAprendizajeUi.getEstadoEjecucionId()==317){
            int colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_green_600);
            txtTag.setVisibility(View.VISIBLE);
            txtTag.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_red));
            txtTag.setTextColor(colorEstado);
            //txtTag.setText("Hecho");
            txt_fechasesion.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.md_grey_500));
            lin_titulosesion.setBackgroundColor(colorEstado);
            cardvSesiones.setCardBackgroundColor(colorEstado);
            txttiempo_medida.setTextColor(colorEstado);
            txttiempo.setTextColor(colorEstado);
            txt_fechasesion.setTextColor(colorEstado);
            Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_circle_unidades);
            circle.mutate().setColorFilter(colorEstado, PorterDuff.Mode.SRC_ATOP);
            cantRecursos.setBackground(circle);
        }else {
            int colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_blue_600);
            txtTag.setVisibility(View.GONE);
            txtTag.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_blue));
            txtTag.setTextColor(colorEstado);
            //txtTag.setText("Creado");
            txt_fechasesion.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.md_grey_500));
            lin_titulosesion.setBackgroundColor(colorEstado);
            cardvSesiones.setCardBackgroundColor(colorEstado);
            txttiempo_medida.setTextColor(colorEstado);
            txttiempo.setTextColor(colorEstado);
            txt_fechasesion.setTextColor(colorEstado);
            Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_circle_unidades);
            circle.mutate().setColorFilter(colorEstado, PorterDuff.Mode.SRC_ATOP);
            cantRecursos.setBackground(circle);
        }


        if(sesionAprendizajeUi.isActual()){
            int colorEstado = ContextCompat.getColor(itemView.getContext(),R.color.md_red_500);
            txtTag.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.corner_bg_red_600));
            txtTag.setTextColor(colorEstado);
            //txtTag.setText("Hoy");
            txt_fechasesion.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.md_blue_600));
            lin_titulosesion.setBackgroundColor(colorEstado);
            cardvSesiones.setCardBackgroundColor(colorEstado);
            txttiempo_medida.setTextColor(colorEstado);
            txttiempo.setTextColor(colorEstado);
            txt_fechasesion.setTextColor(colorEstado);
            Drawable circle = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_circle_unidades);
            circle.mutate().setColorFilter(colorEstado, PorterDuff.Mode.SRC_ATOP);
            cantRecursos.setBackground(circle);
        }

    }

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}