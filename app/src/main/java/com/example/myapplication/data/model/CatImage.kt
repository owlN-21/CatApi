package com.example.myapplication.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatImage(
    val id: String,
    val url: String
)
