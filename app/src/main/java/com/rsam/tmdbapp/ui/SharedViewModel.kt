package com.rsam.tmdbapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rsam.tmdbapp.data.Movie
import com.rsam.tmdbapp.data.MovieDetails
import com.rsam.tmdbapp.data.PopularMovies
import com.rsam.tmdbapp.db.MoviesDao
import com.rsam.tmdbapp.repository.MainRepository
import com.rsam.tmdbapp.ui.base.BaseViewModel
import com.rsam.tmdbapp.util.SingleLiveEvent
import com.rsam.tmdbapp.util.State
import com.rsam.tmdbapp.util.toDbMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dao: MoviesDao
) : BaseViewModel() {
    val moviesList = MutableLiveData<ArrayList<Movie>>()
    val singleMovie = MutableLiveData<MovieDetails>()
    val error = SingleLiveEvent<Int>()
    val progress = MutableLiveData<Boolean>()
    val shouldNavigate = SingleLiveEvent<Boolean>()

    fun fetchMovieData() {
        progress.value = true
        viewModelScope.launch {
            repository.getMovies()
                .onCompletion { progress.postValue(false) }
                .collect { moviesList ->
                    evaluateState(moviesList)
                }
        }
    }

    fun getDetailsInfo(movieId: Int) {
        viewModelScope.launch {
            repository.getDetails(movieId)
                .onCompletion { progress.postValue(false) }
                .collect { singleMovie ->
                    evaluateState(singleMovie)
                }
        }
    }

    private fun evaluateState(movieData: State) {
        when (movieData) {
            is State.Success<*> -> {
                if (movieData.response is PopularMovies) {
                    moviesList.postValue(movieData.response.movies as ArrayList<Movie>)
                }
                if (movieData.response is MovieDetails) {
                    singleMovie.postValue(movieData.response!!)
                    shouldNavigate.postValue(true)
                }
            }
            is State.Failure -> {
                error.postValue(movieData.error)
            }
        }
    }

    fun saveToFavorites(favoriteMovie: Movie) {
        viewModelScope.launch {
            dao.insertFavouriteMovie(favoriteMovie.toDbMovie())
        }
    }
}