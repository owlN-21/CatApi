package com.example.myapplication.data.remote.di


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.data.remote.CatInformationApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val network = cm.activeNetwork ?: run {
                    notifyNoInternet(context); throw IOException("Нет интернета")
                }
                val caps = cm.getNetworkCapabilities(network) ?: run {
                    notifyNoInternet(context); throw IOException("Нет интернета")
                }
                if (!caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    notifyNoInternet(context)
                    throw IOException("Нет интернета")
                }
                chain.proceed(chain.request())
            }
            .addInterceptor { chain ->
                val requestWithToken = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
                chain.proceed(requestWithToken)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): CatInformationApi =
        retrofit.create(CatInformationApi::class.java)

    private fun notifyNoInternet(context: Context) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Нет интернета", Toast.LENGTH_SHORT).show()
        }
    }
}
