package com.rsam.tmdbapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
abstract class MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertFavouriteMovie(movie: DbMovie)

    @Query("SELECT * FROM  DbMovie")
    abstract suspend fun getAllFavorites(): List<DbMovie>

    @Query("DELETE FROM DbMovie")
    abstract suspend fun deleteAllFavorites()
}