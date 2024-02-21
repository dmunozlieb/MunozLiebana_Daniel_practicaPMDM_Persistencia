package com.practica.munozliebana_daniel_practicapmdm_persistencia.view.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.practica.munozliebana_daniel_practicapmdm_persistencia.R
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo
import com.practica.munozliebana_daniel_practicapmdm_persistencia.view.interfaces.TaskItemClickListener

class TaskAdapter(private val itemClickListener: TaskItemClickListener) : ListAdapter<Todo, TaskAdapter.TaskViewHolder>(DiffCallback()) {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.txtTaskName)
        val textViewDesc: TextView = itemView.findViewById(R.id.txtDescripcion)
        val checkBox: CheckBox = itemView.findViewById(R.id.cbDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_item,
                parent,
                false
            )
        )
    }
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.itemView.apply {
            holder.textView.text = task.titulo
            holder.textViewDesc.text = task.description
            holder.checkBox.isChecked = task.isChecked
            toggleStrikeThrough(holder.textView, task.isChecked)
            holder.checkBox.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(holder.textView, isChecked)
                task.isChecked = !task.isChecked
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}