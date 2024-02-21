package com.practica.munozliebana_daniel_practicapmdm_persistencia.model.database

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos.AsignatureDao
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.daos.TaskDao
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Asignature
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Asignature::class, Todo::class], version = 1)
abstract class TaskDataBase:RoomDatabase() {
    abstract fun asignatureDao(): AsignatureDao
    abstract fun taskDao(): TaskDao

    private class DataBaseCallBack(private val context: Context):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                insertAsignatures(context, getDatabase(context).asignatureDao())
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null
        fun getDatabase(context: Context): TaskDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TaskDataBase::class.java,
                    "TaskDatabase"
                )
                    .addCallback(DataBaseCallBack(context))
                    .build()
                INSTANCE = instance

                instance
            }

        }

        private suspend fun insertAsignatures(context: Context, asignatureDao: AsignatureDao) {
            val asignaturasIniciales = listOf(
                Asignature(asignaturaName = "Acceso a Datos"),
                Asignature(asignaturaName = "Programacion de servicios"),
                Asignature(asignaturaName = "Desarrollo Interfaces"),
                Asignature(asignaturaName = "Sistemas de gestion empresarial"),
                Asignature(asignaturaName = "Ingles"),
                Asignature(asignaturaName = "Empresa e Iniciativa emprendedora"),
                Asignature(asignaturaName = "Dispositivos Moviles"),
            )
            asignatureDao.insertAll(asignaturasIniciales)

        }

        class TaskApplication : Application() {
            val database: TaskDataBase by lazy { getDatabase(this) }
        }
    }
}