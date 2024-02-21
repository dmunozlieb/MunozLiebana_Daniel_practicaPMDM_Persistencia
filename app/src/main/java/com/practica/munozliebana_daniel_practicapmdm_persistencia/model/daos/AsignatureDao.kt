package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Asignature

@Dao
interface AsignatureDao {
    @Insert
    suspend fun insertAll(asignatures:List<Asignature>)
    @Query("select * from asignature")
    fun getAllAsignature():LiveData<List<Asignature>>
}