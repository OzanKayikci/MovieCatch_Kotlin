package com.example.moviecatch.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomTypeConverter {

    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toListInt(json: String): List<Int> {
        return Gson().fromJson(json, Array<Int>::class.java).toList()
    }
}