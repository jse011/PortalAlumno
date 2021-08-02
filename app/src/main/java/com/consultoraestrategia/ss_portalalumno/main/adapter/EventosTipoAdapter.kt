package com.consultoraestrategia.ss_portalalumno.main.adapter

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.consultoraestrategia.ss_portalalumno.R
import com.consultoraestrategia.ss_portalalumno.main.entities.TipoEventoUi
import kotlinx.android.synthetic.main.item_tipo_evento_docente.view.*

class EventosTipoAdapter(val listener: (TipoEventoUi) -> Unit): RecyclerView.Adapter<EventosTipoAdapter.ViewHolder>() {

    private var tipoEventosList:MutableList<TipoEventoUi> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tipo_evento_docente, parent,false))

    override fun getItemCount(): Int = tipoEventosList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) = viewHolder.bind(tipoEventosList[position],listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(tiposEventosUi: TipoEventoUi, listener: (TipoEventoUi) -> Unit) = with(itemView){
            txt_tipo.text = tiposEventosUi.nombre
            //EVENTOS, NOTICIA, ACTIVIDADES, TAREAS, CITAS, DEFAULT,TODOS;
           val (recurso,rgb) = when(tiposEventosUi.tipo){
               TipoEventoUi.EventoIconoEnumUI.EVENTO->Pair(R.drawable.ic_rocket, "#bfca52")
               TipoEventoUi.EventoIconoEnumUI.NOTICIA->Pair(R.drawable.ic_newspaper_o,"#ffc107")
               TipoEventoUi.EventoIconoEnumUI.ACTIVIDAD->Pair(R.drawable.ic_gif,"#ff6b9d")
               TipoEventoUi.EventoIconoEnumUI.TAREA->Pair(R.drawable.ic_tasks,"#ff9800")
               TipoEventoUi.EventoIconoEnumUI.CITA->Pair(R.drawable.ic_calendar,"#00bcd4")
               TipoEventoUi.EventoIconoEnumUI.DEFAULT->Pair(R.drawable.ic_calendar,"#00BCD4")
               TipoEventoUi.EventoIconoEnumUI.AGENDA->Pair(R.drawable.ic_graduation_cap,"#71bb74")
               TipoEventoUi.EventoIconoEnumUI.TODOS->Pair(R.drawable.ic_check_square,"#0091EA")
               else -> {
                   Pair(R.drawable.ic_calendar,"#00BCD4")
               }
           }

            Glide.with(img_tipo)
                    .load(recurso)
                    .into(img_tipo)

            var color = Color.RED
            try {
                color = Color.parseColor(rgb)
            }catch (e:Exception){
                e.printStackTrace()
            }

            card_view_eventos.setCardBackgroundColor(color)

            if(tiposEventosUi.isToogle){
                itemView.alpha = 1f
            }else{
                itemView.alpha = .6f
            }

            setOnClickListener{listener(tiposEventosUi)}
        }
    }

    fun setList(tiposEventosList: MutableList<TipoEventoUi>){
        this.tipoEventosList.clear()
        this.tipoEventosList.addAll(tiposEventosList)
        notifyDataSetChanged()
    }

}