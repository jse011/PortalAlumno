package com.consultoraestrategia.ss_portalalumno.main.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.consultoraestrategia.ss_portalalumno.R;
import com.consultoraestrategia.ss_portalalumno.main.entities.FamiliaUi;
import com.consultoraestrategia.ss_portalalumno.main.entities.HijoUi;
import com.consultoraestrategia.ss_portalalumno.util.UtilsGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamiliaAdapter extends RecyclerView.Adapter<FamiliaAdapter.Holder> {
    private Callback callback;
    private List<Object> personUiList = new ArrayList<>();

    public FamiliaAdapter(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person_familia2, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Object o = personUiList.get(position);
        holder.bind(o, callback);
    }

    public void setList(List<Object> personUiList){
        this.personUiList.clear();
        this.personUiList.addAll(personUiList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return personUiList.size();
    }

    public void update(Object item) {
        int postion = this.personUiList.indexOf(item);
        if(postion!=-1){
            this.personUiList.set(postion, item);
            notifyItemChanged(postion);
        }
    }

    static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imagen_familiar)
        ImageView imagenFamiliar;
        @BindView(R.id.nombre_familiar)
        TextView nombreFamiliar;
        @BindView(R.id.tipo_familiar)
        TextView tipoFamiliar;
        @BindView(R.id.edad_familiar)
        TextView edadFamiliar;
        @BindView(R.id.telefono_familiar)
        TextView telefonoFamiliar;
        @BindView(R.id.correo_familiar)
        TextView correoFamiliar;
        @BindView(R.id.img_arrow)
        ImageView imgArrow;
        @BindView(R.id.content_familiar)
        ConstraintLayout content_familiar;
        @BindView(R.id.content_cabecera)
        ConstraintLayout contentCabecera;
        private Object personUi;
        private Callback callback;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(Object personUi, Callback callback) {
            this.personUi = personUi;
            this.callback = callback;
            String foto = "", nombre = "", tipo = "", edad = "", telefono = "", correo = "";
            boolean toogle = false;

            if(personUi instanceof HijoUi){
                foto = ((HijoUi)personUi).getFoto();
                nombre =  ((HijoUi)personUi).getNombre();
                tipo =  "Hijo";
                edad =  ((HijoUi)personUi).getFechaNacimiento();
                telefono =  ((HijoUi)personUi).getCelular();
                correo = ((HijoUi)personUi).getCorreo();
                toogle =  ((HijoUi)personUi).getToogle();
            }else if(personUi instanceof FamiliaUi){
                foto = ((FamiliaUi)personUi).getFoto();
                nombre =  ((FamiliaUi)personUi).getNombre();
                tipo =  "Familiar";
                edad =  ((FamiliaUi)personUi).getFechaNacimiento();
                telefono =  ((FamiliaUi)personUi).getCelular();
                correo = ((FamiliaUi)personUi).getCorreo();
                toogle =  ((FamiliaUi)personUi).getToogle();
            }


            Glide.with(itemView.getContext())
                    .load(foto)
                    .apply(UtilsGlide.getGlideRequestOptionsSimple()
                            .override(250)
                            .centerCrop())
                    .into(imagenFamiliar);

            nombreFamiliar.setText(nombre);
            tipoFamiliar.setText(tipo);
            edadFamiliar.setText(edad);
            telefonoFamiliar.setText(TextUtils.isEmpty(telefono)?"Sin teléfono":telefono);
            correoFamiliar.setText(TextUtils.isEmpty(correo)?"Sin Correo":correo);
            contentCabecera.setOnClickListener(this);
            content_familiar.setVisibility(toogle?View.VISIBLE:View.GONE);
            Drawable drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.ic_arrow_down);
            if(toogle){
                drawable.setColorFilter( ContextCompat.getColor(itemView.getContext(), R.color.colorAccent), PorterDuff.Mode.MULTIPLY );
            }
            imgArrow.setImageDrawable(drawable);
            imgArrow.animate().rotation(toogle?180:0).start();

        }

        @Override
        public void onClick(View v) {
            callback.onClickTooglePersona(personUi);
        }
    }

    public interface Callback{
        void onClickTooglePersona(Object personUi);
    }

}
