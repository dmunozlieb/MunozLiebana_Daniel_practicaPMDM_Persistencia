package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data

import androidx.room.Embedded
import androidx.room.Relation

data class AsignatureWithTasks (
    @Embedded val asignature: Asignature,
    @Relation(
        parentColumn = "id",
        entityColumn = "id_asignature"
    )
    val tasks: List<Todo>
)
