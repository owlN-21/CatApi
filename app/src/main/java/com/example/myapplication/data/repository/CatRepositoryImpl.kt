package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.localdata.CatDao
import com.example.myapplication.data.localdata.CatEntity
import com.example.myapplication.data.model.CatInformation
import com.example.myapplication.data.remote.CatInformationApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val api: CatInformationApi,
    private val catDao: CatDao
) : CatRepository {

    override fun getCachedCats(): Flow<List<CatEntity>> = catDao.getAllCats()

    override fun getCatBreeds(): Flow<List<CatEntity>> = flow {
        // 1. Эмитим данные из БД (кэш)
        emit(catDao.getAllCats().first())

        // 2. Получаем из API
        val response = api.getAllBreeds()

        val entities = response.map { it.toCatEntity() }
        catDao.insertAll(entities)

        // 3. Эмитим обновленные данные
        emit(entities)
    }.catch { e ->
        Log.e("CatRepository", "Ошибка получения пород", e)
    }
}


fun CatInformation.toCatEntity(): CatEntity {
    val imageUrl = reference_image_id?.let {
        "https://cdn2.thecatapi.com/images/$it.jpg"
    } ?: "" // или заглушку

    return CatEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        url = imageUrl
    )
}
