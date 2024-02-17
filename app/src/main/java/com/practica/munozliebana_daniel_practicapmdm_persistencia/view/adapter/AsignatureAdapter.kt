package com.practica.munozliebana_daniel_practicapmdm_persistencia.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practica.munozliebana_daniel_practicapmdm_persistencia.R
import com.practica.munozliebana_daniel_practicapmdm_persistencia.databinding.AsignaturaTaskItemBinding
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Asignature
import kotlinx.coroutines.flow.Flow



class AsignatureAdapter : ListAdapter<Asignature, AsignatureAdapter.AsignatureViewHolder>(DiffCallback()) {

    class AsignatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txtAsigName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignatureViewHolder {
        return AsignatureViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.asignatura_task_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsignatureViewHolder, position: Int) {
        val asignatura = getItem(position)
        holder.itemView.apply {
            holder.textView.text = asignatura.asignaturaName
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Asignature>() {
        override fun areItemsTheSame(oldItem: Asignature, newItem: Asignature): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asignature, newItem: Asignature): Boolean {
            return oldItem == newItem
        }
    }
}