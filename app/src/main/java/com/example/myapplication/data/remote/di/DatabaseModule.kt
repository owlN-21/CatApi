package com.example.myapplication.data.remote.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.localdata.AppDatabase
import com.example.myapplication.data.localdata.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "cat_database"
        ).build()
    }

    @Provides
    fun provideCatDao(db: AppDatabase): CatDao = db.catDao()
}
