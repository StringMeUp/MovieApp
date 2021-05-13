package com.rsam.tmdbapp.di

import com.rsam.tmdbapp.network.ApiFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RetrofitApiFactory
@Inject constructor(
    private val baseUrl: String,
    private val okHttpClient: OkHttpClient
) : ApiFactory {
    override fun <T> buildApi(type: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(type)
    }
}