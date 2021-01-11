package com.example.moderntodoapp.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoData(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val priorityModel: PriorityModel,
        val desc: String
)
