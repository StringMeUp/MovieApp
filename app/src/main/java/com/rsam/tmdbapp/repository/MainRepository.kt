package com.rsam.tmdbapp.repository

import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.network.NetworkApi
import com.rsam.tmdbapp.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface MainRepositoryApi {
    fun getMovies(): Flow<State>
    fun getDetails(movieId: Int): Flow<State>
}

class MainRepository
@Inject constructor(
    private val networkApi: NetworkApi
) : MainRepositoryApi {

    override fun getMovies(): Flow<State> = flow {
        try {
            val response = networkApi.getPopularMovies()
            if (response.isSuccessful) {
                emit(State.Success(response.body()!!))
            } else {
                emit(State.Failure(R.string.error_message_label))
            }
        } catch (e: Exception) {
            emit(State.Failure(R.string.error_message_label))
        }
    }

    override fun getDetails(movieId: Int): Flow<State> = flow {
        try {
            emit(State.Success(networkApi.getMovieDetails(movieId)))
        } catch (e: Exception) {
            emit(State.Failure(R.string.error_message_label))
        }
    }
}