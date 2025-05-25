package com.example.myapplication.data.localdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert
    suspend fun insertAll(catEntities: List<CatEntity>)

    @Query("SELECT * FROM cats")
    suspend fun getAllCats(): Flow<List<CatEntity>>

    @Query("SELECT * FROM cats WHERE id = :id")
    suspend fun getById(id: Int): Flow<CatEntity?>


}