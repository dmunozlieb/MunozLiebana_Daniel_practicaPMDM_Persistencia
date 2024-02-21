package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "titulo")
    val titulo: String,
    @ColumnInfo(name="description")
    val description: String?,
    @ColumnInfo (name = "date_task")
    val dateTask: String,
    @ColumnInfo(name="id_asignature")
    val idAsignature: Int,


    var isChecked: Boolean = false
)
