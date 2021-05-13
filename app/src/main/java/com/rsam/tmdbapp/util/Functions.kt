package com.rsam.tmdbapp.util

import android.app.AlertDialog
import android.content.Context
import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.data.Movie
import com.rsam.tmdbapp.db.DbMovie

object Alert {
    fun displayErrorDialog(message: Int, context: Context) {
        AlertDialog.Builder(context)
            .setTitle(R.string.error_title_label)
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton(R.string.positive_button_label) { _, _ ->
            }.create().show()
    }
}

fun Movie.toDbMovie() = DbMovie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)

fun DbMovie.toMovie() = DbMovie(
    adult,
    backdropPath,
    genreIds,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    video,
    voteAverage,
    voteCount
)