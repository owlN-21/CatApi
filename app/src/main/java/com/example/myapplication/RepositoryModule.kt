package com.example.myapplication

import com.example.myapplication.data.localdata.AppDatabase
import com.example.myapplication.data.remote.CatInformationApi
import com.example.myapplication.data.repository.CatRepository
import com.example.myapplication.data.repository.CatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCatRepository(
        impl: CatRepositoryImpl
    ): CatRepository
}
