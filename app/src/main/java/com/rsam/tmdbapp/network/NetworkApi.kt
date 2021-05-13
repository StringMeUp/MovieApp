package com.rsam.tmdbapp.network

import com.rsam.tmdbapp.data.MovieDetails
import com.rsam.tmdbapp.data.PopularMovies
import com.rsam.tmdbapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkApi {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): Response<PopularMovies>

    @GET("{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieDetails
}