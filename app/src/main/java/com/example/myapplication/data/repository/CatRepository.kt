package com.example.myapplication.data.repository

import com.example.myapplication.data.localdata.CatEntity
import kotlinx.coroutines.flow.Flow

interface CatRepository {
    fun getCatBreeds(): Flow<List<CatEntity>>
    fun getCachedCats(): Flow<List<CatEntity>>
}
