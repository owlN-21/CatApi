package com.example.myapplication.data.repository

import com.example.myapplication.data.model.CatImage
import com.example.myapplication.data.model.CatInformation
import com.example.myapplication.data.remote.RetrofitInstance

class CatRepositoryGet {
    suspend fun getCatInformation(id: String): CatInformation =
        RetrofitInstance.api.getCatInformationId(id)

    suspend fun getCatImage(imageId: String): CatImage =
        RetrofitInstance.api.getCatImageId(imageId)
}
