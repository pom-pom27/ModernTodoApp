package com.example.moderntodoapp.db

import androidx.room.TypeConverter
import com.example.moderntodoapp.db.models.PriorityModel

class RoomTypeConverter {
    @TypeConverter
    fun fromPriority(priorityModel: PriorityModel): String = priorityModel.name

    @TypeConverter
    fun toPriority(priority: String): PriorityModel = PriorityModel.valueOf(priority)
}