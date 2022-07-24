package com.example.vwntask.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.ArrayList

object Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<ByteArray> {
        val listType: Type = object : TypeToken<ArrayList<ByteArray?>?>() {}.type
        return Gson().fromJson<ArrayList<ByteArray>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<ByteArray?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
