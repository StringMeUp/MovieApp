package com.rsam.tmdbapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rsam.tmdbapp.util.Converters

@TypeConverters(Converters::class)
@Database(entities = [DbMovie::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getMoviesDao(): MoviesDao
}