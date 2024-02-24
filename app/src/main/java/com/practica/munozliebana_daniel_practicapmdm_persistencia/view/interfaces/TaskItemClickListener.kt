package com.practica.munozliebana_daniel_practicapmdm_persistencia.view.interfaces

import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo

interface TaskItemClickListener {
    fun onCheckboxClicked(task: Todo, isChecked: Boolean)
    fun showDialogUpdate(task: Todo)
}