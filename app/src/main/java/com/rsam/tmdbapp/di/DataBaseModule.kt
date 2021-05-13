package com.rsam.tmdbapp.di

import android.content.Context
import androidx.room.Room
import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.db.AppDataBase
import com.rsam.tmdbapp.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideArticlesDao(appDataBase: AppDataBase): MoviesDao {
        return appDataBase.getMoviesDao()
    }

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            appContext.getString(R.string.db_name_label)
        ).fallbackToDestructiveMigration().build()
    }
}