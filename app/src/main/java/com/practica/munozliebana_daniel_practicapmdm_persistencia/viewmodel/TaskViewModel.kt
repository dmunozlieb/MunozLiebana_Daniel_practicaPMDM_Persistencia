package com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos.AsignatureDao
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos.TaskDao
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Asignature
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskViewModel(private val asignatureDao: AsignatureDao, private val taskDao: TaskDao): ViewModel() {
    fun getAllAsignature(): LiveData<List<Asignature>>  = asignatureDao.getAllAsignature()
    fun getAllTask(idasignature: Int): LiveData<List<Todo>> = taskDao.getAllTaskFromAsignature(idasignature)

    fun addNewTask(tituloTodo:String, descripcionTodo:String, idasignatureTodo: Int){
        val task = getNewTodo(tituloTodo,descripcionTodo,idasignatureTodo)
        insertTask(task)
    }

    fun getNewTodo(tituloTodo:String, descripcionTodo:String, idasignatureTodo: Int): Todo{
        return Todo(
            titulo = tituloTodo,
            description = descripcionTodo,
            dateTask = obtenerFechaActual(),
            idAsignature = idasignatureTodo
        )
    }

    fun deleteTask(todo: Todo){
        viewModelScope.launch {
            taskDao.deleteTask(todo)
        }
    }

    fun insertTask(task: Todo){
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }
    fun obtenerFechaActual(): String{
        val fechaActual = LocalDate.now()
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return fechaActual.format(formato)
    }

    fun isEntryValid(itemViewTitle: String):Boolean{
        if (itemViewTitle.isBlank()) return false
        return true
    }
}
class TaskViewModelFactory(private val asignatureDao: AsignatureDao, private val taskDao: TaskDao):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(asignatureDao, taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}