package com.example.moderntodoapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moderntodoapp.db.models.TodoData

@Database(entities = [TodoData::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDAO(): TodoDAO


    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java, "todo_table"
            ).build()
    }

}