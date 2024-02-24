package com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel

import android.util.Log
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

    fun addNewTask(tituloTodo:String, descripcionTodo:String, fechaTodo: String ,idasignatureTodo: Int){
        val task = getNewTodo(tituloTodo,descripcionTodo, fechaTodo,idasignatureTodo)
        insertTask(task)
    }

    private fun getNewTodo(tituloTodo:String, descripcionTodo:String, fechaTodo: String ,idasignatureTodo: Int): Todo{
        return Todo(
            titulo = tituloTodo,
            description = descripcionTodo,
            dateTask = fechaTodo,
            idAsignature = idasignatureTodo
        )
    }

    fun updateTask(todo: Todo){
        viewModelScope.launch {
            taskDao.updateTask(todo)
        }
    }

    fun deleteTask(todo: Todo){
        viewModelScope.launch {
            taskDao.deleteTask(todo)
        }
    }

    private fun insertTask(task: Todo){
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    fun isEntryValid(itemViewTitle: String):Boolean{
        if (itemViewTitle.isBlank()){return false}
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