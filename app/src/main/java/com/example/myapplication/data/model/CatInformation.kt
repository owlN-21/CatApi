package com.example.myapplication.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatInformation(
    val id: String,
    val name: String,
    val description: String,
    val reference_image_id: String?
)
