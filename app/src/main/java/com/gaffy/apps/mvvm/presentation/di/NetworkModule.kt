package com.gaffy.apps.mvvm.presentation.di

import com.gaffy.apps.mvvm.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideUnsplashService(): com.gaffy.apps.mvvm.data.api.ApiService {
        return com.gaffy.apps.mvvm.data.api.ApiService.create()
    }
}
