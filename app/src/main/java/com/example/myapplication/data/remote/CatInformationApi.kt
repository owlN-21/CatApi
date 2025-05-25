package com.example.myapplication.data.remote

import com.example.myapplication.data.model.CatImage
import com.example.myapplication.data.model.CatInformation
import retrofit2.http.GET
import retrofit2.http.Path


interface CatInformationApi {
    @GET("breeds") // Добавляем эндпоинт для всех пород
    suspend fun getAllBreeds(): List<CatInformation>

    @GET("breeds/{breed}")
    suspend fun getCatInformationId(@Path("breed") breed: String): CatInformation

    @GET("images/{image_id}")
    suspend fun getCatImageId(@Path("image_id") imageId: String): CatImage
}

