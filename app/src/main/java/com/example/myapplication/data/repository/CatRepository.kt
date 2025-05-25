package com.example.myapplication.data.repository

import com.example.myapplication.data.localdata.CatDao
import com.example.myapplication.data.localdata.CatEntity
import com.example.myapplication.data.model.CatInformation
import com.example.myapplication.data.remote.CatInformationApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class CatRepository(
    private val catDao: CatDao,
    private val api: CatInformationApi
) {
    // Просто грузим из API и кидаем в БД
    suspend fun getCatBreeds(): List<CatEntity> {
        val catsFromApi = api.getAllBreeds() // Допустим, эндпоинт появился
        val catEntities = catsFromApi.map { it.toCatEntity() } // Конвертируем в Entity
        catDao.insertAll(catEntities) // Сохраняем в БД
        return catEntities
    }

    // Если нужно брать из кеша (например, при старте)
    suspend fun getCachedCats(): Flow<List<CatEntity>> {
        return catDao.getAllCats() // Просто поток из БД
    }
}

fun CatInformation.toCatEntity(): CatEntity {
    return CatEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        url = "https://example.com/images/${this.id}" // Например
    )
}