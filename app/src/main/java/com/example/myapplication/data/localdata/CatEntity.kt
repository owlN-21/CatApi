package com.example.myapplication.data.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_database")
data class CatEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val url: String
)