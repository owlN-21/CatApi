package com.example.myapplication.data.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dots.restapp.util.Converters

@Database(entities = [CatEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao
}
