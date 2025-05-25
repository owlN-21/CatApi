package com.example.myapplication.data.remote

object RetrofitInstance {
    private val moshi = com.squareup.moshi.Moshi.Builder().build()

    val api: CatInformationApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(retrofit2.converter.moshi.MoshiConverterFactory.create(moshi))
            .build()
            .create(CatInformationApi::class.java)
    }
}