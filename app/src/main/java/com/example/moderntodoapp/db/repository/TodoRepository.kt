package com.example.moderntodoapp.db.repository

import androidx.lifecycle.LiveData
import com.example.moderntodoapp.db.TodoDAO
import com.example.moderntodoapp.db.models.TodoData

class TodoRepository(private val todoDAO: TodoDAO) {

    val getAllData: LiveData<List<TodoData>> = todoDAO.getAllData()

    suspend fun insertData(todoData: TodoData) {
        todoDAO.insertData(todoData)
    }

    suspend fun updateData(todoData: TodoData) {
        todoDAO.updateData(todoData)
    }

}