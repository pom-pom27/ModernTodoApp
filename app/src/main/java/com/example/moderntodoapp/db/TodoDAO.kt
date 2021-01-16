package com.example.moderntodoapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moderntodoapp.db.models.TodoData

@Dao
interface TodoDAO {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(todoData: TodoData)

    @Update
    suspend fun updateData(todoData: TodoData)

    @Delete
    suspend fun deleteData(todoData: TodoData)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

}