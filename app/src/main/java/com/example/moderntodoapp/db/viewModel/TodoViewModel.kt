package com.example.moderntodoapp.db.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moderntodoapp.db.TodoDatabase
import com.example.moderntodoapp.db.models.TodoData
import com.example.moderntodoapp.db.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoDao = TodoDatabase.getDatabase(application).todoDAO()
    private val repository: TodoRepository

    val getAllData: LiveData<List<TodoData>>
    val sortByLowPriority: LiveData<List<TodoData>>
    val sortByHighPriority: LiveData<List<TodoData>>


    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
        sortByLowPriority = repository.sortByLowPriority
        sortByHighPriority = repository.sortByHighPriority
    }

    //TODO Experimental
    fun insertData(todoData: TodoData) =
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertData(todoData)
            }

    fun updateData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(todoData)
        }
    }

    fun deleteData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(todoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchQuery(searchQuery: String) = repository.searchQuery(searchQuery)

}