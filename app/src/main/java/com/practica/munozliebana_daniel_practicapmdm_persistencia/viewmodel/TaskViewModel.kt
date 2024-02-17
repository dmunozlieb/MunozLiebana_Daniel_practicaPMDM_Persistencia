package com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos.AsignatureDao
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Asignature
import kotlinx.coroutines.flow.Flow

class TaskViewModel(private val asignatureDao: AsignatureDao): ViewModel() {
    fun getAllAsignature(): LiveData<List<Asignature>>  = asignatureDao.getAllAsignature()


}
class TaskViewModelFactory(private val asignatureDao: AsignatureDao):
    ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(asignatureDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}