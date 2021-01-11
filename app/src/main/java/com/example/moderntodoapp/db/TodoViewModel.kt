package com.example.moderntodoapp.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application).todoDAO()
    private val repository: TodoRepository

    val getAllData: LiveData<List<TodoData>>


    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    //TODO Experimental
    fun insertData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertData(todoData) }
    }
}