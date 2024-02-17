package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asignature")
data class Asignature(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @NonNull @ColumnInfo(name = "asignatura_name")
    val asignaturaName: String

)
