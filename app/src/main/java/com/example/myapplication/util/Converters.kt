package com.dots.restapp.util

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()
    private val listAdapter = moshi.adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java))

    @TypeConverter
    fun fromJson(value: String?): List<String> =
        value?.let { listAdapter.fromJson(it)!! } ?: emptyList()

    @TypeConverter
    fun toJson(list: List<String>?): String =
        list?.let { listAdapter.toJson(it) } ?: "[]"
}
