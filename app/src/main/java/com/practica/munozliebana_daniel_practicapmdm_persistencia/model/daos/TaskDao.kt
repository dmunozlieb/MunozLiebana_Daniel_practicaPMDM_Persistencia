package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.AsignatureWithTasks
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(todo: Todo)
    @Delete
    suspend fun deleteTask(todo: Todo)
    @Update
    suspend fun updateTask(todo: Todo)

    @Query("select * from task where id_asignature = :idasignature order by titulo asc")
    fun getAllTaskFromAsignature(idasignature: Int): LiveData<List<Todo>>
}