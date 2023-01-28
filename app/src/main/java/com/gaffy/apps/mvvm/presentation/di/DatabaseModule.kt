package com.gaffy.apps.mvvm.presentation.di

import android.content.Context
import com.gaffy.apps.mvvm.data.room.AppDatabase
import com.gaffy.apps.mvvm.data.room.ExerciseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): ExerciseDao {
        return appDatabase.plantDao()
    }
}
