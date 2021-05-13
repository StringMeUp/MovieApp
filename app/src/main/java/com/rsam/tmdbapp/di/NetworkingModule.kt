package com.rsam.tmdbapp.di

import com.rsam.tmdbapp.BuildConfig
import com.rsam.tmdbapp.network.NetworkApi
import com.rsam.tmdbapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideOkClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Provides
    fun provideNewsApi(): NetworkApi = RetrofitApiFactory(
        provideBaseUrl(),
        provideOkClient()
    ).buildApi(NetworkApi::class.java)
}