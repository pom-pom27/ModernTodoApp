package com.example.moderntodoapp.db.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "todo_table")
@Parcelize
data class TodoData(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val title: String,
        val priorityModel: PriorityModel,
        val desc: String
) : Parcelable
